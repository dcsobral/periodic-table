package com.dcsobral.challenges.periodictable;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * @author dsobral
 * @since 8/9/16
 */
public class SymbolValidatorTest {

  private static final int ELEMENT_MAX_SIZE = 10;
  private final SecureRandom secureRandom = new SecureRandom();

  private static String getSymbolFromLetterPosition(String element, int firstLetter, int secondLetter) {
    return capitalize(String.format("%s%s", element.charAt(firstLetter), element.charAt(secondLetter)));
  }

  private static String getSymbolWithLetters(char firstLetter, char secondLetter) {
    return capitalize(String.format("%s%s", firstLetter, secondLetter));
  }

  private static String capitalize(String symbol) {
    if (!symbol.isEmpty()) {
      return Character.toUpperCase(symbol.charAt(0)) + symbol.substring(1).toLowerCase();
    } else {
      return symbol;
    }
  }

  @Test
  public void allSymbolsMustHaveTwoLetters() throws Exception {
    String element = capitalize(String.join("", Collections.nCopies(ELEMENT_MAX_SIZE, "a")));
    SymbolValidator validator = new SymbolValidator(element);

    IntStream.range(0, ELEMENT_MAX_SIZE).forEach(size -> {
      String symbol = element.substring(0, size);
      if (size == 2) {
        assertTrue("Symbols with two characters are valid", validator.validate(symbol));
      } else {
        assertFalse("Symbols must have exactly two characters (" + symbol + ")", validator.validate(symbol));
      }
    });

    assertFalse("B is not a valid symbol for Boron", new SymbolValidator("Boron").validate("B"));
  }

  @Test
  public void bothLettersMustAppearInElementName_validSymbols() throws Exception {
    String element = getRandomElement(ELEMENT_MAX_SIZE);
    int firstLetterPosition = getRandomFirstLetterPosition(element);
    int secondLetterPosition = getRandomSecondLetterPosition(element, firstLetterPosition);
    String symbol = getSymbolFromLetterPosition(element, firstLetterPosition, secondLetterPosition);

    assertTrue("Symbols (" + symbol + ") with two letters of element (" + element + ") are valid ",
      new SymbolValidator(element).validate(symbol));

    assertTrue("'Cy' is a valid symbol for Mercury", new SymbolValidator("Mercury").validate("Cy"));
  }

  @Test
  public void bothLettersMustAppearInElementName_invalidSymbols() throws Exception {
    String element = getRandomElement(ELEMENT_MAX_SIZE);
    char firstLetter = getLetterNotIn(element);
    char secondLetter = getLetterNotIn(element);

    String symbol = getSymbolWithLetters(firstLetter, secondLetter);
    assertFalse("Both letters in the symbol (" + symbol + ") must appear in the element (" + element + ")",
      new SymbolValidator(element).validate(symbol));

    String symbolFirstLetterOk = getSymbolWithLetters(element.charAt(0), secondLetter);
    assertFalse("Both letters in the symbol (" + symbolFirstLetterOk + ") must appear in the element (" + element + ")",
      new SymbolValidator(element).validate(symbolFirstLetterOk));

    String symbolSecondLetterOk = getSymbolWithLetters(element.charAt(1), firstLetter);
    assertFalse("Both letters in the symbol (" + symbolSecondLetterOk + ") must appear in the element (" + element + ")",
      new SymbolValidator(element).validate(symbolSecondLetterOk));

    assertFalse("'Hg' is not a valid symbol for Mercury", new SymbolValidator("Mercury").validate("Hg"));
  }

  @Test
  public void lettersMustAppearInOrderOnElementName_validSymbols() throws Exception {
    String element = getRandomElement(ELEMENT_MAX_SIZE);
    int firstLetterPosition = getRandomFirstLetterPosition(element);
    int secondLetterPosition = getRandomSecondLetterPosition(element, firstLetterPosition);
    String symbol = getSymbolFromLetterPosition(element, firstLetterPosition, secondLetterPosition);

    assertTrue("Symbols (" + symbol + ") with letters of element (" + element + ") in order are valid ",
      new SymbolValidator(element).validate(symbol));

    assertTrue("'Vr' is a valid symbol for Silver", new SymbolValidator("Silver").validate("Vr"));
    SymbolValidator magnesium = new SymbolValidator("Magnesium");
    assertTrue("'Ma' is a valid symbol for Magnesium", magnesium.validate("Ma"));
    assertTrue("'Am' is a valid symbol for Magnesium", magnesium.validate("Am"));
  }

  @Test
  public void lettersMustAppearInOrderOnElementName_invalidSymbols() throws Exception {

    String element;
    String lowerCaseElement;
    char firstLetter;
    char secondLetter;

    do {
      element = getRandomElement(ELEMENT_MAX_SIZE);
      lowerCaseElement = element.toLowerCase();
      int firstLetterPosition = getRandomFirstLetterPosition(element);
      int secondLetterPosition = getRandomSecondLetterPosition(element, firstLetterPosition);
      firstLetter = lowerCaseElement.charAt(firstLetterPosition);
      secondLetter = lowerCaseElement.charAt(secondLetterPosition);
    } while (firstLetter == secondLetter
      || lowerCaseElement.indexOf(firstLetter, lowerCaseElement.indexOf(secondLetter) + 1) >= 0);

    String symbol = getSymbolWithLetters(secondLetter, firstLetter);

    assertFalse("Symbols (" + symbol + ") with two letters of element (" + element + ") out of order are invalid ",
      new SymbolValidator(element).validate(symbol));

    assertFalse("'Rv' is a not valid symbol for Silver", new SymbolValidator("Silver").validate("Rv"));
  }

  @Test
  public void repeatedLettersMustAppearTwiceInSymbol() throws Exception {
    SymbolValidator xenon = new SymbolValidator("Xenon");
    assertTrue("'Nn' is a valid symbol for Xenon", xenon.validate("Nn"));
    assertFalse("'Oo' is not a valid symbol for Xenon", xenon.validate("Oo"));
  }

  @Test
  public void testExamples() throws Exception {
    assertTrue(new SymbolValidator("Spenglerium").validate("Ee"));
    assertTrue(new SymbolValidator("Zeddemorium").validate("Zr"));
    assertTrue(new SymbolValidator("Venkmine").validate("Kn"));
    assertFalse(new SymbolValidator("Stantzon").validate("Zt"));
    assertFalse(new SymbolValidator("Melintzum").validate("Nn"));
    assertFalse(new SymbolValidator("Tullium").validate("Ty"));
  }

  @Test
  public void testFirstCharacterOfFirstValidSymbolInAlphabeticalOrder() throws Exception {
    assertThat(new SymbolValidator("Edcba").firstValidSymbolInAlphabeticalOrder(), startsWith("B"));
    assertThat(new SymbolValidator("Abcde").firstValidSymbolInAlphabeticalOrder(), startsWith("A"));
    assertThat(new SymbolValidator("Edcbabcde").firstValidSymbolInAlphabeticalOrder(), startsWith("A"));
  }

  @Test
  public void testSecondCharacterOfFirstValidSymbolInAlphabeticalOrder() throws Exception {
    assertThat(new SymbolValidator("Edcba").firstValidSymbolInAlphabeticalOrder(), endsWith("a"));
    assertThat(new SymbolValidator("Abcde").firstValidSymbolInAlphabeticalOrder(), endsWith("b"));
    assertThat(new SymbolValidator("Edcbabcde").firstValidSymbolInAlphabeticalOrder(), endsWith("b"));
    assertThat(new SymbolValidator("Edcbaedcde").firstValidSymbolInAlphabeticalOrder(), endsWith("c"));
  }

  @Test
  public void computeFirstValidSymbolInAlphabeticalOrder() throws Exception {
    assertEquals("Ei", new SymbolValidator("Gozerium").firstValidSymbolInAlphabeticalOrder());
    assertEquals("Ie", new SymbolValidator("Slimyrine").firstValidSymbolInAlphabeticalOrder());
  }

  @Test
  public void findNumberOfValidSymbolsForAnElement() throws Exception {
    assertEquals(11, new SymbolValidator("Zuulon").numberOfValidSymbols());
    // Zu Zl Zo Zn
    // Uu Ul Uo Un
    // Lo Ln
    // On
    assertEquals(3, new SymbolValidator("Abc").numberOfValidSymbols());
    assertEquals(4, new SymbolValidator("Aabc").numberOfValidSymbols());
    assertEquals(5, new SymbolValidator("Aabbc").numberOfValidSymbols());
    assertEquals(6, new SymbolValidator("Aabbcc").numberOfValidSymbols());
    assertEquals(7, new SymbolValidator("Ababcc").numberOfValidSymbols());
  }

  private int getRandomFirstLetterPosition(CharSequence element) {
    return secureRandom.nextInt(element.length() - 2);
  }

  private int getRandomSecondLetterPosition(CharSequence element, int firstLetterPosition) {
    return secureRandom.nextInt(element.length() - firstLetterPosition - 1) + firstLetterPosition + 1;
  }

  private char getLetterNotIn(String element) {
    char letter;
    do {
      letter = getRandomLetter();
    } while (element.indexOf(letter) >= 0);
    return letter;
  }

  private String getRandomElement(int size) {
    String randomElement = IntStream.range(2, size)
      .mapToObj(i -> String.valueOf(getRandomLetter()))
      .collect(Collectors.joining());
    return capitalize(randomElement);
  }

  private char getRandomLetter() {
    return (char) ('a' + secureRandom.nextInt(26));
  }
}
