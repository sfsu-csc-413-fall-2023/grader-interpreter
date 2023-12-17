package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.RuntimeStack;
import interpreter.bytecode.ArgsCode;
import interpreter.bytecode.CallCode;
import interpreter.bytecode.GotoCode;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LabelCode;
import interpreter.bytecode.LitCode;
import interpreter.bytecode.ReturnCode;
import interpreter.bytecode.StoreCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class StoreCodeTest {

  @Test
  public void testToString()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new LitCode(List.of("LIT", "111")));
    program.addCode(new LitCode(List.of("LIT", "222")));

    StoreCode testCode = new StoreCode(List.of("STORE", "0", "z"));
    program.addCode(testCode);

    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    vm.step(); // LIT 111
    vm.step(); // LIT 222
    vm.step(); // STORE 0 z

    String expected = String.format("%-25s%s", "STORE 0 z", "z = 222");

    assertEquals(expected, testCode.toString().trim());
  }

  @Test
  public void testStore()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new LitCode(List.of("LIT", "111")));
    program.addCode(new LitCode(List.of("LIT", "222")));
    program.addCode(new LitCode(List.of("LIT", "333")));
    program.addCode(new LitCode(List.of("LIT", "444")));
    program.addCode(new LitCode(List.of("LIT", "555")));
    program.addCode(new LitCode(List.of("LIT", "3")));
    program.addCode(new LitCode(List.of("LIT", "1")));
    program.addCode(new StoreCode(List.of("STORE", "1", "z")));
    program.addCode(new StoreCode(List.of("STORE", "3", "k")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    RuntimeStack runtimeStack = vm.getRuntimeStackValue();

    for (int i = 0; i < 10; i++) {
      vm.step();
    }

    assertEquals(5, vm.getRunStackValue().size());

    assertEquals(555, runtimeStack.pop());
    assertEquals(3, runtimeStack.pop());
    assertEquals(333, runtimeStack.pop());
    assertEquals(1, runtimeStack.pop());
    assertEquals(111, runtimeStack.pop());
  }

  @Test
  public void testStoreInFrame()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new GotoCode(List.of("GOTO", "skip")));

    program.addCode(new LabelCode(List.of("LABEL", "fn")));
    program.addCode(new LitCode(List.of("LIT", "42")));
    program.addCode(new StoreCode(List.of("STORE", "0", "j")));
    program.addCode(new ReturnCode(List.of("RETURN", "fn")));

    program.addCode(new LabelCode(List.of("LABEL", "skip")));
    program.addCode(new LitCode(List.of("LIT", "111")));
    program.addCode(new LitCode(List.of("LIT", "222")));
    program.addCode(new LitCode(List.of("LIT", "333")));
    program.addCode(new LitCode(List.of("LIT", "444")));
    program.addCode(new LitCode(List.of("LIT", "555")));
    program.addCode(new ArgsCode(List.of("ARGS", "1")));
    program.addCode(new CallCode(List.of("CALL", "fn")));

    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    RuntimeStack runtimeStack = vm.getRuntimeStackValue();

    vm.step(); // GOTO skip
    vm.step(); // LIT 111
    vm.step(); // LIT 222
    vm.step(); // LIT 333
    vm.step(); // LIT 444
    vm.step(); // LIT 555
    vm.step(); // ARGS 1
    vm.step(); // CALL fn
    vm.step(); // LIT 42
    vm.step(); // STORE 0 j
    // 111 222 333 444 | 42

    assertEquals(42, runtimeStack.pop());
  }
}
