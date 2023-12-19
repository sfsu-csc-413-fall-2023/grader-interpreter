package tests.helpers;

import java.lang.reflect.Field;
import java.util.Stack;
import java.util.Vector;

import interpreter.RuntimeStack;

public class TestRuntimeStack extends RuntimeStack {

  private Field runStackField;
  private Field framePointersField;

  public TestRuntimeStack() throws NoSuchFieldException, SecurityException {
    this.runStackField = this.getClass().getSuperclass().getDeclaredField("runStack");
    this.framePointersField = this.getClass().getSuperclass().getDeclaredField("framePointers");
  }

  public Vector<Integer> getRunStackValue()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    this.runStackField.setAccessible(true);

    @SuppressWarnings("unchecked")
    Vector<Integer> result = (Vector<Integer>) this.runStackField.get(this);
    return result;
  }

  public Stack<Integer> getFramePointersValue()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    this.framePointersField.setAccessible(true);

    @SuppressWarnings("unchecked")
    Stack<Integer> result = (Stack<Integer>) this.framePointersField.get(this);
    return result;
  }
}
