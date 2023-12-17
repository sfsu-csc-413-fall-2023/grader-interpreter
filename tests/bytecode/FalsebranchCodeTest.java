package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.FalsebranchCode;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LabelCode;
import interpreter.bytecode.LitCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class FalsebranchCodeTest {
  @Test
  public void testFalseFalseBranch()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = getTestProgram(0);
    TestVirtualMachine vm = new TestVirtualMachine(program);

    vm.step(); // LIT 0
    vm.step(); // FALSEBRANCH

    // LABEL is 7; run loop increments by 1
    assertEquals(8, vm.getProgramCounterValue());
  }

  @Test
  public void testTrueFalseBranch()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = getTestProgram(1);
    TestVirtualMachine vm = new TestVirtualMachine(program);

    vm.step(); // LIT 1
    vm.step(); // FALSEBRANCH

    assertEquals(2, vm.getProgramCounterValue());
  }

  private static Program getTestProgram(int testValue) {
    Program program = new Program();

    program.addCode(new LitCode(List.of("LIT", String.format("%d", testValue))));
    program.addCode(new FalsebranchCode(List.of("FALSEBRANCH", "blarg")));

    for (int i = 0; i < 5; i++) {
      program.addCode(new LitCode(List.of("LIT", String.format("%d", i))));
    }

    program.addCode(new LabelCode(List.of("LABEL", "blarg")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    return program;
  }
}
