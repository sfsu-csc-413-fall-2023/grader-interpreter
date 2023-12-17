package tests.bytecode;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.HaltCode;
import interpreter.loader.Program;
import tests.helpers.TestProgram;
import tests.helpers.TestVirtualMachine;

public class HaltCodeTest {
  public static List<String> codeLine = List.of("HALT");

  @Test
  public void testEndState()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = TestProgram.create(new HaltCode(codeLine));
    TestVirtualMachine vm = new TestVirtualMachine(program);

    vm.step();

    assertEquals(1, vm.getPC());
    assertFalse(vm.getIsRunningValue());
  }
}
