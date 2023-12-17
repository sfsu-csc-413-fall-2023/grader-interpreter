package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.HaltCode;
import interpreter.bytecode.LabelCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class LabelCodeTest {
  @Test
  public void testLabel()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();

    program.addCode(new LabelCode(List.of("LABEL", "a-label")));
    program.addCode(new LabelCode(List.of("LABEL", "another-label")));
    program.addCode(new LabelCode(List.of("LABEL", "a-third-label")));
    program.addCode(new HaltCode(List.of("HALT")));

    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    vm.step();

    // LABEL is a no-op
    assertEquals(1, vm.getProgramCounterValue());
  }
}
