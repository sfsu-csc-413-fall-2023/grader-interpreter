package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.LabelCode;

public class LabelCodeTest {
  @Test
  public void testToString() {
    LabelCode code = new LabelCode(List.of("LABEL", "thisIsALabel<<33>>"));

    assertEquals("LABEL thisIsALabel<<33>>", code.toString());
  }
}
