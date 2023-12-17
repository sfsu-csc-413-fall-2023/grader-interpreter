package tests.bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import interpreter.RuntimeStack;
import interpreter.bytecode.BopCode;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.LitCode;
import interpreter.loader.Program;
import tests.helpers.TestVirtualMachine;

public class BopCodeTest {

  @ParameterizedTest
  @MethodSource("provideOperators")
  public void testOperator(List<ByteCode> instructions, int expectedValue)
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Program program = new Program();
    instructions.stream().forEach(code -> program.addCode(code));
    program.resolveSymbolicAddresses();

    TestVirtualMachine vm = new TestVirtualMachine(program);
    for (int i = 0; i < instructions.size(); i++) {
      vm.step();
    }

    RuntimeStack runtimeStack = vm.getRuntimeStackValue();

    assertEquals(expectedValue, runtimeStack.peek());
  }

  private static ByteCode lit(String literal) {
    return new LitCode(List.of("LIT", literal));
  }

  private static BopCode bop(String op) {
    return new BopCode(List.of("BOP", op));
  }

  private static Stream<Arguments> provideOperators() {
    return Stream.of(
        Arguments.of(List.of(lit("12"), lit("5"), bop("+")), 12 + 5),
        Arguments.of(List.of(lit("13"), lit("5"), bop("-")), 13 - 5),
        Arguments.of(List.of(lit("8"), lit("9"), bop("*")), 8 * 9),
        Arguments.of(List.of(lit("8"), lit("3"), bop("/")), 8 / 3),
        Arguments.of(List.of(lit("9"), lit("12"), bop("==")), 0),
        Arguments.of(List.of(lit("7"), lit("7"), bop("==")), 1),
        Arguments.of(List.of(lit("42"), lit("42"), bop("!=")), 0),
        Arguments.of(List.of(lit("89"), lit("175"), bop("!=")), 1),
        Arguments.of(List.of(lit("42"), lit("5"), bop(">")), 1),
        Arguments.of(List.of(lit("5"), lit("42"), bop(">")), 0),
        Arguments.of(List.of(lit("8"), lit("7"), bop(">=")), 1),
        Arguments.of(List.of(lit("7"), lit("7"), bop(">=")), 1),
        Arguments.of(List.of(lit("99"), lit("177"), bop(">=")), 0),
        Arguments.of(List.of(lit("33"), lit("99"), bop("<")), 1),
        Arguments.of(List.of(lit("34"), lit("2"), bop("<")), 0),
        Arguments.of(List.of(lit("3"), lit("3"), bop("<=")), 1),
        Arguments.of(List.of(lit("33"), lit("42"), bop("<=")), 1),
        Arguments.of(List.of(lit("3"), lit("2"), bop("<=")), 0),
        Arguments.of(List.of(lit("0"), lit("0"), bop("&")), 0),
        Arguments.of(List.of(lit("0"), lit("1"), bop("&")), 0),
        Arguments.of(List.of(lit("1"), lit("0"), bop("&")), 0),
        Arguments.of(List.of(lit("1"), lit("1"), bop("&")), 1),
        Arguments.of(List.of(lit("0"), lit("0"), bop("|")), 0),
        Arguments.of(List.of(lit("0"), lit("1"), bop("|")), 1),
        Arguments.of(List.of(lit("1"), lit("0"), bop("|")), 1),
        Arguments.of(List.of(lit("1"), lit("1"), bop("|")), 1));
  }
}
