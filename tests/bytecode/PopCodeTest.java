package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.PopCode;

public class PopCodeTest {
  @Test
  public void testToString() {
    PopCode code = new PopCode(List.of("POP", "42"));

    assertEquals("POP 42", code.toString());
  }
}
