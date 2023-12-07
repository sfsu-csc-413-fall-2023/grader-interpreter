package tests.helpers;

import java.util.Stack;

import interpreter.RuntimeStack;
import interpreter.VirtualMachine;
import interpreter.loader.Program;

public class TestVM extends VirtualMachine {
  private int nextPc;

  public TestVM(Program program) {
    super(program);
  }

  public TestVM(Program program, Stack<Integer> returnAddresses, RuntimeStack runtimeStack) {
    super(program, returnAddresses, runtimeStack);
    this.nextPc = 0;
  }

  @Override
  public void executeProgram() {
    program.getCode(this.nextPc++).execute(this);
  }

  public void executeToEnd(int instructionCount) {
    for (int i = 0; i < instructionCount; i++) {
      program.getCode(i).execute(this);
    }
  }

  public int getPc() {
    return this.nextPc;
  }

  public Stack<Integer> getReturnAddresses() {
    return this.returnAddresses;
  }

  public RuntimeStack getRuntimeStack() {
    return this.runStack;
  }
}