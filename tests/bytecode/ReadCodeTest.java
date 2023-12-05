package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.ReadCode;

public class ReadCodeTest {
  @Test
  public void testToString() {
    ReadCode code = new ReadCode(List.of("READ"));

    assertEquals("READ", code.toString());
  }
}
