package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.ArgsCode;
import interpreter.bytecode.HaltCode;
import interpreter.loader.Program;
import tests.helpers.TestProgram;
import tests.helpers.TestVirtualMachine;

import java.util.List;
import java.util.Stack;

public class ArgsCodeTest {
  @Test
  public void testArgs0()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = TestProgram.create(10);
    program.addCode(new ArgsCode(List.of("ARGS", "0")));
    program.addCode(new HaltCode(List.of("HALT")));
    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    Stack<Integer> framePointers = vm.getFramePointersValue();
    vm.executeProgram();

    assertEquals(10, framePointers.pop());
    assertEquals(0, framePointers.pop());
    assertTrue(framePointers.empty());
  }

  @Test
  public void testArgs1()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = TestProgram.create(10);
    program.addCode(new ArgsCode(List.of("ARGS", "1")));
    program.addCode(new HaltCode(List.of("HALT")));
    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    Stack<Integer> framePointers = vm.getFramePointersValue();
    vm.executeProgram();

    assertEquals(9, framePointers.pop());
    assertEquals(0, framePointers.pop());
    assertTrue(framePointers.empty());
  }

  @Test
  public void testArgs5()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = TestProgram.create(10);
    program.addCode(new ArgsCode(List.of("ARGS", "5")));
    program.addCode(new HaltCode(List.of("HALT")));
    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    Stack<Integer> framePointers = vm.getFramePointersValue();
    vm.executeProgram();

    assertEquals(5, framePointers.pop());
    assertEquals(0, framePointers.pop());
    assertTrue(framePointers.empty());
  }
}
