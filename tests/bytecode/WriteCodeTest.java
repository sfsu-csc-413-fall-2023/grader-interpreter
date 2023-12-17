package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Vector;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LitCode;
import interpreter.bytecode.WriteCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class WriteCodeTest {
  private final PrintStream standardOut = System.out;

  @Test
  public void testWrite()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));

    Program program = new Program();

    program.addCode(new LitCode(List.of("LIT", "42")));
    program.addCode(new WriteCode(List.of("WRITE")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    Vector<Integer> runStack = vm.getRunStackValue();

    vm.step();
    vm.step();
    vm.step();

    System.setOut(standardOut);

    assertEquals(42, runStack.get(0));
    assertEquals(1, runStack.size());

    assertTrue(outputStreamCaptor.toString().contains("42"));
  }
}
