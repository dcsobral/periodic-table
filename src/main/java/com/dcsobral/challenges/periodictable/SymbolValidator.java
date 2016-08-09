package com.dcsobral.challenges.periodictable;

/**
 * @author dsobral
 * @since 8/9/16
 */
public class SymbolValidator {
  private final String element;

  public SymbolValidator(CharSequence element) {
    this.element = element.toString().toLowerCase();
  }

  private static boolean hasValidSize(CharSequence symbol) {
    return symbol.length() == 2;
  }

  private static boolean isLetterFromName(String element, char letter) {
    return element.indexOf(Character.toLowerCase(letter)) >= 0;
  }

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
}
