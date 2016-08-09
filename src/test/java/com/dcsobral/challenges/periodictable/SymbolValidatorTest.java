package com.dcsobral.challenges.periodictable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import org.junit.Test;

/**
 * @author dsobral
 * @since 8/9/16
 */
public class SymbolValidatorTest {

  @Test
  public void allSymbolsMustHaveTwoLetters() throws Exception {
    String element = "Aaaaaaaaaaa";
    SymbolValidator validator = new SymbolValidator(element);

    IntStream.range(0, 10).forEach(size -> {
      String symbol = element.substring(0, size);
      if (size == 2) {
        assertTrue("Symbols with two characters are valid", validator.validate(symbol));
      } else {
        assertFalse("Symbols must have exactly two characters (" + symbol + ")", validator.validate(symbol));
      }
    });
  }
}
