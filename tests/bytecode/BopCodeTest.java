package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.BopCode;

public class BopCodeTest {
  @Test
  public void testToString() {
    BopCode code = new BopCode(List.of("BOP", "+"));

    assertEquals("BOP +", code.toString());
  }
}
