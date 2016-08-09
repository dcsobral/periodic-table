package com.dcsobral.challenges.periodictable;

/**
 * @author dsobral
 * @since 8/9/16
 */
public class SymbolValidator {
  private final CharSequence element;

  public SymbolValidator(CharSequence element) {
    this.element = element;
  }

  public boolean validate(CharSequence symbol) {
    return symbol.length() == 2;
  }
}
