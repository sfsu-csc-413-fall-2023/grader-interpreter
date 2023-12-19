package tests.helpers;

import java.lang.reflect.Field;
import java.util.Stack;
import java.util.Vector;

import interpreter.RuntimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.loader.Program;

public class TestVirtualMachine extends VirtualMachine {
  private Field pcField;
  private Field programField;
  private Field isRunningField;
  private Field runStackField;
  private Field returnAddressesField;
  private Field isDumpingField;

  public TestVirtualMachine(Program program) throws NoSuchFieldException, SecurityException {
    super(program);

    this.pcField = this.getClass().getSuperclass().getDeclaredField("pc");
    this.programField = this.getClass().getSuperclass().getDeclaredField("program");
    this.isRunningField = this.getClass().getSuperclass().getDeclaredField("isRunning");
    this.isDumpingField = this.getClass().getSuperclass().getDeclaredField("isDumping");
    this.runStackField = this.getClass().getSuperclass().getDeclaredField("runStack");
    this.returnAddressesField = this.getClass().getSuperclass().getDeclaredField("returnAddresses");
  }

  public void step() throws IllegalArgumentException, IllegalAccessException {
    ByteCode code = this.getProgramValue().getCode(this.getProgramCounterValue());
    code.execute(this);

    incrementPcValue();
  }

  public int getProgramCounterValue() throws IllegalArgumentException, IllegalAccessException {
    this.pcField.setAccessible(true);
    return (int) this.pcField.get(this);
  }

  public void incrementPcValue() throws IllegalArgumentException, IllegalAccessException {
    this.pcField.setAccessible(true);
    this.pcField.set(this, getProgramCounterValue() + 1);
  }

  public Program getProgramValue() throws IllegalArgumentException, IllegalAccessException {
    this.programField.setAccessible(true);
    return (Program) this.programField.get(this);
  }

  public Stack<Integer> getReturnAddressesValue() throws IllegalArgumentException, IllegalAccessException {
    this.returnAddressesField.setAccessible(true);

    @SuppressWarnings("unchecked")
    Stack<Integer> result = (Stack<Integer>) this.returnAddressesField.get(this);
    return result;
  }

  public boolean getIsRunningValue() throws IllegalArgumentException, IllegalAccessException {
    this.isRunningField.setAccessible(true);
    return (boolean) this.isRunningField.get(this);
  }

  public boolean getIsDumpingValue() throws IllegalArgumentException, IllegalAccessException {
    this.isDumpingField.setAccessible(true);
    return (boolean) this.isRunningField.get(this);
  }

  public RuntimeStack getRuntimeStackValue() throws IllegalArgumentException, IllegalAccessException {
    this.runStackField.setAccessible(true);

    return (RuntimeStack) this.runStackField.get(this);
  }

  public Vector<Integer> getRunStackValue()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    this.runStackField.setAccessible(true);

    Field actual = this.runStackField.get(this).getClass().getDeclaredField("runStack");
    actual.setAccessible(true);

    @SuppressWarnings("unchecked")
    Vector<Integer> result = (Vector<Integer>) actual.get(this.runStackField.get(this));
    return result;
  }

  public Stack<Integer> getFramePointersValue()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    this.runStackField.setAccessible(true);

    Field actual = this.runStackField.get(this).getClass().getDeclaredField("framePointers");
    actual.setAccessible(true);

    @SuppressWarnings("unchecked")
    Stack<Integer> result = (Stack<Integer>) actual.get(this.runStackField.get(this));
    return result;
  }
}
