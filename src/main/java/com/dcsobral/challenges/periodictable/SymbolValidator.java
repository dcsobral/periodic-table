package com.dcsobral.challenges.periodictable;

/**
 * Validates symbols for a given element.
 *
 * @author dsobral
 * @since 8 /9/16
 */
public class SymbolValidator {
  private final String element;


  /**
   * Instantiates a new Symbol validator.
   *
   * @param element Element which the symbols should be valid for.
   */
  public SymbolValidator(CharSequence element) {
    this.element = element.toString().toLowerCase();
  }

  private static boolean hasValidSize(CharSequence symbol) {
    return symbol.length() == 2;
  }

  private static boolean isLetterFromName(String element, char letter) {
    return element.indexOf(Character.toLowerCase(letter)) >= 0;
  }

  /**
   * Verify that the {@code symbol} is valid for the element of this validator.
   *
   * <p>Valid symbols must obey the following rules:</p>
   * <pre>
   *   <ol>
   *     <li>They must have exactly two letters;</li>
   *     <li>The letters of the symbol must appear in the element name;</li>
   *     <li>The letters of the symbol must be in the same order as in the element name;</li>
   *     <li>If the letters in the symbol are the same, it must appear twice in the element name.</li>
   *   </ol>
   * </pre>
   *
   * @param symbol Symbol which must be valid for the element of this validator.
   * @return true if all rules are obeyed, false otherwise.
   */
  public boolean validate(CharSequence symbol) {

    if (!hasValidSize(symbol)) {
      return false;
    }

    char firstLetter = symbol.charAt(0);
    char secondLetter = symbol.charAt(1);

    return isLetterFromElement(firstLetter)
      && isLetterFromName(elementNameAfterChar(firstLetter), secondLetter);
  }

  private boolean isLetterFromElement(char letter) {
    return isLetterFromName(element, letter);
  }

  private String elementNameAfterChar(char letter) {
    return element.substring(element.indexOf(Character.toLowerCase(letter)) + 1);
  }

  public String firstValidSymbolInAlphabeticalOrder() {
    int firstCharAt = 0;
    char firstChar = Character.toLowerCase(element.charAt(firstCharAt));
    for (int i = firstCharAt + 1; i < element.length() - 1; i++) {
      char lowerCaseLetter = Character.toLowerCase(element.charAt(i));
      if (lowerCaseLetter < firstChar) {
        firstChar = lowerCaseLetter;
        firstCharAt = i;
      }
    }

    char secondChar = element.charAt(firstCharAt + 1);
    for (int i = firstCharAt + 2; i < element.length(); i++) {
      char lowerCaseLetter = Character.toLowerCase(element.charAt(i));
      if (lowerCaseLetter < secondChar) {
        secondChar = lowerCaseLetter;
      }
    }

    return String.format("%s%s", Character.toUpperCase(firstChar), secondChar);
  }
}
