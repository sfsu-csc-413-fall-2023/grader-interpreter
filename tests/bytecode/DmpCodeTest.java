package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.DmpCode;
import interpreter.bytecode.HaltCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class DmpCodeTest {
  @Test
  public void testDumpOn()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = getTestProgram("+");
    TestVirtualMachine vm = new TestVirtualMachine(program);

    vm.step();

    assertTrue(vm.getIsDumping());
  }

  @Test
  public void testDumpOff()
      throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    Program program = getTestProgram("-");
    TestVirtualMachine vm = new TestVirtualMachine(program);

    vm.step();

    assertFalse(vm.getIsDumping());
  }

  private static Program getTestProgram(String flag) {
    Program program = new Program();

    program.addCode(new DmpCode(List.of("DMP", flag)));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    return program;
  }
}
