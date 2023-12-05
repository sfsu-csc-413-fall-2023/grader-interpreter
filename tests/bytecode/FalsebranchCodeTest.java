package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.FalsebranchCode;

public class FalsebranchCodeTest {
  @Test
  public void testToString() {
    FalsebranchCode code = new FalsebranchCode(List.of("FALSEBRANCH", "gosomewherE<<77>>"));

    assertEquals("FALSEBRANCH gosomewherE<<77>>", code.toString());
  }
}
