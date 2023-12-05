package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.GotoCode;

public class GotoCodeTest {
  @Test
  public void testToString() {
    GotoCode code = new GotoCode(List.of("GOTO", "aLaBeL<<9>>"));

    assertEquals("GOTO aLaBeL<<9>>", code.toString());
  }
}
