package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Vector;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LitCode;
import interpreter.bytecode.PopCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class PopCodeTest {
  @Test
  public void testPop()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new LitCode(List.of("LIT", "99")));
    program.addCode(new LitCode(List.of("LIT", "88")));
    program.addCode(new LitCode(List.of("LIT", "77")));
    program.addCode(new PopCode(List.of("POP", "2")));
    program.addCode(new PopCode(List.of("POP", "1")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    Vector<Integer> runStack = vm.getRunStackValue();

    vm.step(); // LIT 99
    vm.step(); // LIT 88
    vm.step(); // LIT 77

    assertEquals(3, runStack.size());

    vm.step(); // POP 2

    assertEquals(1, runStack.size());

    vm.step(); // POP 1

    assertEquals(0, runStack.size());
  }
}
