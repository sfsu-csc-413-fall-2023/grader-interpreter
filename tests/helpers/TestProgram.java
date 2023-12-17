package tests.helpers;

import java.util.List;

import interpreter.bytecode.ByteCode;
import interpreter.bytecode.LitCode;
import interpreter.loader.Program;

public class TestProgram {
  public static Program create(ByteCode... codes) {
    Program program = new Program();

    for (int i = 0; i < codes.length; i++) {
      program.addCode(codes[i]);
    }

    program.resolveSymbolicAddresses();

    return program;
  }

  public static Program create(int argumentCount) {
    Program program = new Program();

    for (int i = 0; i < argumentCount; i++) {
      program.addCode(
          new LitCode(List.of("LIT", String.format("%d", i))));
    }

    program.resolveSymbolicAddresses();

    return program;
  }
}
