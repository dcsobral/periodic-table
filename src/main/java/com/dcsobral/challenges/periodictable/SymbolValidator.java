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

  public boolean validate(CharSequence symbol) {
    return hasValidSize(symbol) && isLetterFromElement(symbol, 0) && isLetterFromElement(symbol, 1);
  }

  private boolean isLetterFromElement(CharSequence symbol, int index) {
    return element.indexOf(Character.toLowerCase(symbol.charAt(index))) >= 0;
  }
}
