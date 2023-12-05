package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

import interpreter.bytecode.HaltCode;

public class HaltCodeTest {
  @Test
  public void testToString() {
    HaltCode code = new HaltCode(List.of("HALT"));

    assertEquals("HALT", code.toString());
  }

}
