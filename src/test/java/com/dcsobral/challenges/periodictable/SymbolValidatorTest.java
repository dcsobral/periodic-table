package com.dcsobral.challenges.periodictable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.SecureRandom;
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
    return String.format("%s%s", element.charAt(firstLetter), element.charAt(secondLetter));
  }

  private static String getSymbolWithLetters(char firstLetter, char secondLetter) {
    return String.format("%s%s", firstLetter, secondLetter);
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
    String element = "Aaaaaaaaaaa";
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
    int firstLetterPosition = secureRandom.nextInt(element.length() - 1);
    int secondLetterPosition = secureRandom.nextInt(element.length() - firstLetterPosition) + firstLetterPosition;
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

  private char getLetterNotIn(String element) {
    char letter;
    do {
      letter = getRandomLetter();
    } while (element.indexOf(letter) >= 0);
    return letter;
  }

  private String getRandomElement(int size) {
    return IntStream.range(2, size).mapToObj(i -> String.valueOf(getRandomLetter())).collect(Collectors.joining());
  }

  private char getRandomLetter() {
    return (char) ('a' + secureRandom.nextInt(26));
  }
}
