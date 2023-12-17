package tests.helpers;

import java.util.Stack;
import java.util.Vector;

import interpreter.RuntimeStack;

public class TestRuntimeStack extends RuntimeStack {
  public Stack<Integer> framePointers;
  public Vector<Integer> runStack;

  public TestRuntimeStack() {
    this.framePointers = new Stack<>();
    this.runStack = new Vector<>();
  }
}
