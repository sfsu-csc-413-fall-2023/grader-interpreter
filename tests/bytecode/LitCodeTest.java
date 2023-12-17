package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LitCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class LitCodeTest {
  @Test
  public void testValue()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new LitCode(List.of("LIT", "42")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    vm.step();

    assertEquals(1, vm.getProgramCounterValue());
    assertEquals(42, vm.getRuntimeStackValue().peek());
  }

  @Test
  public void testVariable()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new LitCode(List.of("LIT", "0", "wow")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    vm.step();

    assertEquals(1, vm.getProgramCounterValue());
    assertEquals(0, vm.getRuntimeStackValue().peek());
  }

}
