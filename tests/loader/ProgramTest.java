package tests.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.GotoCode;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LabelCode;
import interpreter.bytecode.LitCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class ProgramTest {
  @Test
  public void testResolveSymbolicAddresses()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new GotoCode(List.of("GOTO", "far")));
    program.addCode(new LabelCode(List.of("LABEL", "back")));
    program.addCode(new LitCode(List.of("LIT", "2")));
    program.addCode(new LitCode(List.of("LIT", "3")));
    program.addCode(new LitCode(List.of("LIT", "4")));
    program.addCode(new LitCode(List.of("LIT", "5")));
    program.addCode(new LabelCode(List.of("LABEL", "far")));
    program.addCode(new GotoCode(List.of("GOTO", "near")));
    program.addCode(new LabelCode(List.of("LABEL", "near")));
    program.addCode(new GotoCode(List.of("GOTO", "back")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);

    vm.step(); // GOTO far
    assertEquals(7, vm.getProgramCounterValue()); // run loop increments by 1, far is at 6

    vm.step(); // GOTO near
    assertEquals(9, vm.getProgramCounterValue()); // run loop increments by 1, near is at 8

    vm.step(); // GOTO back
    assertEquals(2, vm.getProgramCounterValue()); // run loop increments by 1, back is at 1
  }
}
