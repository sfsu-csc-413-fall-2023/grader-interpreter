package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.GotoCode;
import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LabelCode;
import interpreter.bytecode.LitCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class GotoCodeTest {
  @Test
  public void testToString() {
    GotoCode code = new GotoCode(List.of("GOTO", "aLaBeL<<9>>"));

    assertEquals("GOTO aLaBeL<<9>>", code.toString());
  }

  @Test
  public void testGoto()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new GotoCode(List.of("GOTO", "target-label")));
    for (int i = 0; i < 9; i++) {
      program.addCode(new LitCode(List.of("LIT", String.format("%d", i))));
    }
    program.addCode(new LabelCode(List.of("LABEL", "target-label")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    vm.step(); // GOTO target-label

    // 11 because the end of the VM run loop increments by 1
    assertEquals(11, vm.getProgramCounterValue());
    assertTrue(vm.getRunStackValue().isEmpty());
  }
}
