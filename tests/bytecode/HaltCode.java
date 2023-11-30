package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HaltCode {
  @Test
  public void testToString() {
    HaltCode code = new HaltCode();

    assertEquals("HALT", code.toString());
  }
}
