package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.ArgsCode;

public class ArgsCodeTest {
  @Test
  public void testToString() {
    ArgsCode code = new ArgsCode(List.of("ARGS", "2"));

    assertEquals("ARGS 2", code.toString());
  }
}
