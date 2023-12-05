package tests.helpers;

import java.util.Stack;

import interpreter.RuntimeStack;
import interpreter.VirtualMachine;
import interpreter.loader.Program;

public class TestVM extends VirtualMachine {

  public TestVM(Program program) {
    super(program);
  }

  public TestVM(Program program, Stack<Integer> returnAddresses, RuntimeStack runtimeStack) {
    super(program, returnAddresses, runtimeStack);
  }

  @Override
  public void executeProgram() {
    program.getCode(0).execute(this);
  }

  public Stack<Integer> getReturnAddresses() {
    return this.returnAddresses;
  }

  public RuntimeStack getRuntimeStack() {
    return this.runStack;
  }
}