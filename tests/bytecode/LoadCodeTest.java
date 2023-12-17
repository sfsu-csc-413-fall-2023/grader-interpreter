package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.RuntimeStack;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LitCode;
import interpreter.bytecode.LoadCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class LoadCodeTest {
  @Test
  public void testLoadCode()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new LitCode(List.of("LIT", "88")));
    program.addCode(new LitCode(List.of("LIT", "77")));
    program.addCode(new LitCode(List.of("LIT", "66")));
    program.addCode(new LitCode(List.of("LIT", "55")));
    program.addCode(new LitCode(List.of("LIT", "44")));

    program.addCode(new LoadCode(List.of("LOAD", "3", "dm")));
    program.addCode(new LoadCode(List.of("LOAD", "1", "md")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    RuntimeStack runStack = vm.getRuntimeStackValue();

    for (int i = 0; i < 8; i++) {
      vm.step();
    }

    assertEquals(77, runStack.pop());
    assertEquals(55, runStack.pop());
  }
}
