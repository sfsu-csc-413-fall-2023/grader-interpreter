package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.junit.jupiter.api.Test;

import interpreter.RuntimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LitCode;
import interpreter.loader.Program;
import tests.helpers.TestVM;

public class VirtualMachineTest {

  @Test
  public void testLit() {
    LitCode code = new LitCode(List.of("LIT", "42"));

    Program program = new Program();
    program.addCode(code);

    TestVM vm = new TestVM(program);
    vm.executeProgram();

    RuntimeStack runStack = vm.getRuntimeStack();
    assertEquals(42, runStack.peek());
  }

  @Test
  public void testLitVariable() {
    LitCode code = new LitCode(List.of("LIT", "0", "i"));

    Program program = new Program();
    program.addCode(code);

    TestVM vm = new TestVM(program);
    vm.executeProgram();

    RuntimeStack runStack = vm.getRuntimeStack();
    assertEquals(0, runStack.peek());
  }

  @Test
  public void testLitInteger() {
    Program program = new Program();
    program.addCode(new LitCode(List.of("LIT", "5")));
    program.addCode(new HaltCode(List.of("HALT")));

    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStackContents = new Vector<>();
    Stack<Integer> returnAddresses = new Stack<>();
    RuntimeStack runStack = new RuntimeStack(framePointers, runStackContents);

    VirtualMachine vm = new VirtualMachine(program, returnAddresses, runStack);
    vm.executeProgram();
  }

  @Test
  public void testHalt() {
    Program program = new Program();
    program.addCode(new HaltCode(List.of("HALT")));

    Stack<Integer> framePointers = new Stack<>();
    Vector<Integer> runStackContents = new Vector<>();
    Stack<Integer> returnAddresses = new Stack<>();
    RuntimeStack runStack = new RuntimeStack(framePointers, runStackContents);

    VirtualMachine vm = new VirtualMachine(program, returnAddresses, runStack);
    vm.executeProgram();

    assertFalse(vm.getIsRunning());

    assertTrue(returnAddresses.empty());
    assertTrue(framePointers.empty());
    assertEquals(0, runStackContents.size());
  }
}
