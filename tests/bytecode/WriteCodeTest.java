package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.WriteCode;

public class WriteCodeTest {
  @Test
  public void testToString() {
    WriteCode code = new WriteCode(List.of("WRITE"));

    assertEquals("WRITE", code.toString());
  }
}
