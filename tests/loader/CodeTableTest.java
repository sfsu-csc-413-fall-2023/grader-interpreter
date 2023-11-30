package tests.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import interpreter.loader.CodeTable;

public class CodeTableTest {

  @ParameterizedTest
  @MethodSource("byteCodeProvider")
  public void testCodeTable(String byteCode, String fullyQualifiedClassName) {
    assertEquals(fullyQualifiedClassName, CodeTable.get(byteCode));
  }

  public static Stream<Arguments> byteCodeProvider() {
    return Stream.of(
        Arguments.of("ARGS", "interpreter.bytecode.ArgsCode"),
        Arguments.of("BOP", "interpreter.bytecode.BopCode"),
        Arguments.of("CALL", "interpreter.bytecode.CallCode"),
        Arguments.of("DMP", "interpreter.bytecode.DmpCode"),
        Arguments.of("FALSEBRANCH", "interpreter.bytecode.FalsebranchCode"),
        Arguments.of("GOTO", "interpreter.bytecode.GotoCode"),
        Arguments.of("HALT", "interpreter.bytecode.HaltCode"),
        Arguments.of("LABEL", "interpreter.bytecode.LabelCode"),
        Arguments.of("LIT", "interpreter.bytecode.LitCode"),
        Arguments.of("LOAD", "interpreter.bytecode.LoadCode"),
        Arguments.of("POP", "interpreter.bytecode.PopCode"),
        Arguments.of("READ", "interpreter.bytecode.ReadCode"),
        Arguments.of("RETURN", "interpreter.bytecode.ReturnCode"),
        Arguments.of("STORE", "interpreter.bytecode.StoreCode"),
        Arguments.of("WRITE", "interpreter.bytecode.WriteCode"));
  }
}
