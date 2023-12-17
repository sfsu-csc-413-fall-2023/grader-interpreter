package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Vector;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.ArgsCode;
import interpreter.bytecode.CallCode;
import interpreter.bytecode.GotoCode;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LabelCode;
import interpreter.bytecode.LitCode;
import interpreter.bytecode.ReturnCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class ReturnCodeTest {

  @Test
  public void testToString()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = getTestProgram();
    TestVirtualMachine vm = new TestVirtualMachine(program);

    vm.step(); // GOTO skip
    vm.step(); // ARGS 0 - run loop skips label
    vm.step(); // CALL fn<<1>>
    vm.step(); // LIT 7 - run loop skips label
    vm.step(); // LIT 42
    vm.step(); // RETURN fn<<1>>
    vm.step(); // LIT 99
    vm.step(); // HALT

    String expected = String.format("%-25s%s", "RETURN fn<<1>>", "end fn: 42");
    assertEquals(expected, program.getCode(4).toString());
  }

  @Test
  public void testReturn()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = getTestProgram();
    TestVirtualMachine vm = new TestVirtualMachine(program);

    vm.step(); // GOTO skip
    vm.step(); // ARGS 0 - run loop skips label
    vm.step(); // CALL fn<<1>>
    vm.step(); // LIT 7 - run loop skips label
    vm.step(); // LIT 42
    vm.step(); // RETURN fn<<1>>
    vm.step(); // LIT 99
    vm.step(); // HALT

    Vector<Integer> runStack = vm.getRunStackValue();

    // Expect 99 at top of stack
    assertEquals(99, runStack.get(1));
    // Expect 42 (return value)
    assertEquals(42, runStack.get(0));

    // Expect stack size to be 2 - 7 cleared with frame
    assertEquals(2, runStack.size());
  }

  private static Program getTestProgram() {
    Program program = new Program();

    program.addCode(new GotoCode(List.of("GOTO", "skip")));

    program.addCode(new LabelCode(List.of("LABEL", "fn<<1>>")));
    program.addCode(new LitCode(List.of("LIT", "7")));
    program.addCode(new LitCode(List.of("LIT", "42")));
    program.addCode(new ReturnCode(List.of("RETURN", "fn<<1>>")));

    program.addCode(new LabelCode(List.of("LABEL", "skip")));
    program.addCode(new ArgsCode(List.of("ARGS", "0")));
    program.addCode(new CallCode(List.of("CALL", "fn<<1>>")));
    program.addCode(new LitCode(List.of("LIT", "99")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    return program;
  }
}
