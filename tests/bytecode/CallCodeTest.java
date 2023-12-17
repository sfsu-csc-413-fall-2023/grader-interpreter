package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.ArgsCode;
import interpreter.bytecode.CallCode;
import interpreter.bytecode.GotoCode;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LabelCode;
import interpreter.bytecode.LitCode;
import interpreter.bytecode.PopCode;
import interpreter.bytecode.ReturnCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class CallCodeTest {

  @Test
  public void testToStringNoArgs()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new GotoCode(List.of("GOTO", "after_function")));

    program.addCode(new LabelCode(List.of("LABEL", "start<<42>>")));
    program.addCode(new LitCode(List.of("LIT", "8")));
    program.addCode(new PopCode(List.of("POP", "1")));
    program.addCode(new ReturnCode(List.of("RETURN", "start<<42>>")));

    program.addCode(new LabelCode(List.of("LABEL", "after_function")));
    program.addCode(new LitCode(List.of("LIT", "3")));
    program.addCode(new LitCode(List.of("LIT", "2")));
    program.addCode(new LitCode(List.of("LIT", "1")));
    program.addCode(new ArgsCode(List.of("ARGS", "0")));

    CallCode testCode = new CallCode(List.of("CALL", "start<<42>>"));
    program.addCode(testCode);

    program.addCode(new LitCode(List.of("LIT", "7")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    vm.step(); // GOTO after_function
    vm.step(); // LIT 3 - run loop skips label
    vm.step(); // LIT 2
    vm.step(); // LIT 1
    vm.step(); // ARGS 0
    vm.step(); // CALL start

    String expected = String.format("%-25s%s", "CALL start<<42>>", "start()");

    assertEquals(expected, testCode.toString());
  }

  @Test
  public void testToStringOneArgs()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new GotoCode(List.of("GOTO", "after_function")));

    program.addCode(new LabelCode(List.of("LABEL", "start<<42>>")));
    program.addCode(new LitCode(List.of("LIT", "8")));
    program.addCode(new PopCode(List.of("POP", "1")));
    program.addCode(new ReturnCode(List.of("RETURN", "start<<42>>")));

    program.addCode(new LabelCode(List.of("LABEL", "after_function")));
    program.addCode(new LitCode(List.of("LIT", "3")));
    program.addCode(new LitCode(List.of("LIT", "2")));
    program.addCode(new LitCode(List.of("LIT", "1")));
    program.addCode(new ArgsCode(List.of("ARGS", "1")));

    CallCode testCode = new CallCode(List.of("CALL", "start<<42>>"));
    program.addCode(testCode);

    program.addCode(new LitCode(List.of("LIT", "7")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    vm.step(); // GOTO after_function
    vm.step(); // LIT 3 - run loop skips label
    vm.step(); // LIT 2
    vm.step(); // LIT 1
    vm.step(); // ARGS 1
    vm.step(); // CALL start

    String expected = String.format("%-25s%s", "CALL start<<42>>", "start(1)");

    assertEquals(expected, testCode.toString());
  }

  @Test
  public void testToStringThreeArgs()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new GotoCode(List.of("GOTO", "after_function")));

    program.addCode(new LabelCode(List.of("LABEL", "start<<42>>")));
    program.addCode(new LitCode(List.of("LIT", "8")));
    program.addCode(new PopCode(List.of("POP", "1")));
    program.addCode(new ReturnCode(List.of("RETURN", "start<<42>>")));

    program.addCode(new LabelCode(List.of("LABEL", "after_function")));
    program.addCode(new LitCode(List.of("LIT", "3")));
    program.addCode(new LitCode(List.of("LIT", "2")));
    program.addCode(new LitCode(List.of("LIT", "1")));
    program.addCode(new ArgsCode(List.of("ARGS", "3")));

    CallCode testCode = new CallCode(List.of("CALL", "start<<42>>"));
    program.addCode(testCode);

    program.addCode(new LitCode(List.of("LIT", "7")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    vm.step(); // GOTO after_function
    vm.step(); // LIT 3 - run loop skips label
    vm.step(); // LIT 2
    vm.step(); // LIT 1
    vm.step(); // ARGS 3
    vm.step(); // CALL start

    String expected = String.format("%-25s%s", "CALL start<<42>>", "start(3,2,1)");

    assertEquals(expected, testCode.toString());
  }

  @Test
  public void testCall()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();
    program.addCode(new GotoCode(List.of("GOTO", "after_function")));
    // 1 LABEL start
    program.addCode(new LabelCode(List.of("LABEL", "start")));
    program.addCode(new LitCode(List.of("LIT", "8")));
    program.addCode(new PopCode(List.of("POP", "1")));
    program.addCode(new ReturnCode(List.of("RETURN", "start")));
    // 5 LABEL after_function
    program.addCode(new LabelCode(List.of("LABEL", "after_function")));
    program.addCode(new LitCode(List.of("LIT", "42")));
    program.addCode(new ArgsCode(List.of("ARGS", "0")));
    program.addCode(new CallCode(List.of("CALL", "start")));
    // 9 LIT 7
    program.addCode(new LitCode(List.of("LIT", "7")));
    program.addCode(new HaltCode(List.of("HALT")));
    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    Stack<Integer> returnAddresses = vm.getReturnAddressesValue();

    vm.step(); // GOTO after_function
    vm.step(); // LIT 42 - run loop has incremented past label
    vm.step(); // ARGS 0
    vm.step(); // CALL start

    // Target is 1, but run loop increments by 1
    assertEquals(2, vm.getProgramCounterValue());
    assertEquals(9, returnAddresses.peek());
  }
}
