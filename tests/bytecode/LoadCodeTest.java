package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.LoadCode;

public class LoadCodeTest {
  @Test
  public void testToString() {
    LoadCode code = new LoadCode(List.of("LOAD", "9", "r"));

    String expected = String.format("%-25s%s", "LOAD 9 r", "<load r>");
    assertEquals(expected, code.toString());
  }
}
