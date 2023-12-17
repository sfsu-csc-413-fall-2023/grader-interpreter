package tests.outputAndCreation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assume;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import interpreter.bytecode.ByteCode;

public class GeneralByteCodeTests {

  private static ByteCode getInstance(String className, List<String> codeLine)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException, ClassNotFoundException {
    Class<?> codClass = Class.forName(className);
    Constructor<?> constructor = codClass.getConstructor(List.class);

    return (ByteCode) constructor.newInstance(codeLine);
  }

  @ParameterizedTest
  @MethodSource("provideTestConditions")
  public void testCanCreateInstance(String className, List<String> codeLine) {
    assertDoesNotThrow(() -> {
      getInstance(className, codeLine);
    });
  }

  @ParameterizedTest
  @MethodSource("provideTestConditions")
  public void testToString(String className, List<String> codeLine, String expectedString)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException, ClassNotFoundException {
    Assume.assumeNotNull(expectedString);

    assertEquals(expectedString, getInstance(className, codeLine).toString());
  }

  @ParameterizedTest
  @MethodSource("provideNewCodeTestConditions")
  public void testCanCreateNewCodeInstance(String className, List<String> codeLine) {
    assertDoesNotThrow(() -> {
      getInstance(className, codeLine);
    });
  }

  @ParameterizedTest
  @MethodSource("provideNewCodeTestConditions")
  public void testNewCodeToString(String className, List<String> codeLine, String expectedString)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException, ClassNotFoundException {
    Assume.assumeNotNull(expectedString);

    assertEquals(expectedString, getInstance(className, codeLine).toString());
  }

  private static Stream<Arguments> provideNewCodeTestConditions() {
    return Stream.of(
        Arguments.of("interpreter.bytecode.DmpCode", List.of("DMP", "+"), "DMP +"),
        Arguments.of("interpreter.bytecode.DmpCode", List.of("DMP", "-"), "DMP -"));
  }

  private static Stream<Arguments> provideTestConditions() {
    return Stream.of(
        Arguments.of("interpreter.bytecode.ArgsCode", List.of("ARGS", "2"), "ARGS 2"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "+"), "BOP +"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "-"), "BOP -"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "*"), "BOP *"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "/"), "BOP /"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "&"), "BOP &"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "|"), "BOP |"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "<"), "BOP <"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "<="), "BOP <="),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", ">"), "BOP >"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", ">="), "BOP >="),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "!"), "BOP !"),
        Arguments.of("interpreter.bytecode.BopCode", List.of("BOP", "!="), "BOP !="),
        Arguments.of("interpreter.bytecode.CallCode", List.of("CALL", "SoMeLaBeL"), null),
        Arguments.of("interpreter.bytecode.FalsebranchCode", List.of("FALSEBRANCH", "AbRanChLabEl"),
            "FALSEBRANCH AbRanChLabEl"),
        Arguments.of("interpreter.bytecode.GotoCode", List.of("GOTO", "aTargEt"), "GOTO aTargEt"),
        Arguments.of("interpreter.bytecode.HaltCode", List.of("HALT"), "HALT"),
        Arguments.of("interpreter.bytecode.LabelCode", List.of("LABEL", "abalamahalamatandra"),
            "LABEL abalamahalamatandra"),
        Arguments.of("interpreter.bytecode.LitCode", List.of("LIT", "0", "vArIablenAm3"),
            String.format("%-25s%s", "LIT 0 vArIablenAm3", "int vArIablenAm3 = 0")),
        Arguments.of("interpreter.bytecode.LitCode", List.of("LIT", "42"),
            String.format("%-25s%s", "LIT 42", "int 42")),
        Arguments.of("interpreter.bytecode.LoadCode", List.of("LOAD", "2", "blArg"),
            String.format("%-25s%s", "LOAD 2 blArg", "<load blArg>")),
        Arguments.of("interpreter.bytecode.PopCode", List.of("POP", "99"), "POP 99"),
        Arguments.of("interpreter.bytecode.ReadCode", List.of("READ"), "READ"),
        Arguments.of("interpreter.bytecode.ReturnCode", List.of("RETURN", "returningLaB3L"), null),
        Arguments.of("interpreter.bytecode.StoreCode", List.of("STORE", "123", "vvv"), null),
        Arguments.of("interpreter.bytecode.WriteCode", List.of("WRITE"), "WRITE"));
  }
}
