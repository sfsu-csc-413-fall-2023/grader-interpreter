package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.LitCode;

public class LitCodeTest {
  @Test
  public void testToStringWithIdentifier() {
    LitCode code = new LitCode(List.of("LIT", "0", "zEd"));

    String expected = String.format("%-25s%s", "LIT 0 zEd", "int zEd = 0");
    assertEquals(expected, code.toString());
  }

  @Test
  public void testToStringWithLiteral() {
    LitCode code = new LitCode(List.of("LIT", "42"));

    String expected = String.format("%-25s%s", "LIT 42", "int 42");
    assertEquals(expected, code.toString());
  }
}
