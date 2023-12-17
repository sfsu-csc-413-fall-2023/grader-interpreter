package tests.loader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import interpreter.bytecode.*;
import interpreter.exceptions.ByteCodeLoaderException;
import interpreter.loader.ByteCodeLoader;
import interpreter.loader.Program;

public class ByteCodeLoaderTest {
  private static final String codes = String.join(System.lineSeparator(), List.of(
      /* 0 */"ARGS 0",
      /* 1 */"BOP +",
      /* 2 */"CALL fn",
      /* 3 */"DMP -",
      /* 4 */"FALSEBRANCH lbl",
      /* 5 */"GOTO other",
      /* 6 */"HALT",
      /* 7 */"LABEL lbl",
      /* 8 */"LABEL fn",
      /* 9 */"LABEL other",
      /* 10 */"LIT 0 i",
      /* 11 */"LIT 42",
      /* 12 */"LOAD 3 zzzz",
      /* 13 */"POP 7",
      /* 14 */"READ",
      /* 15 */"WRITE",
      /* 16 */"RETURN fn",
      /* 17 */"STORE 0 bbbb"));

  @Test
  public void testByteCodeLoader() throws IOException, ByteCodeLoaderException {
    Path path = Files.createTempFile("a", "b");

    try (FileWriter writer = new FileWriter(path.toFile())) {
      writer.write(codes);
    }

    ByteCodeLoader loader = new ByteCodeLoader(path.toAbsolutePath().toString());
    Program program = loader.loadCodes();

    assertTrue(program.getCode(0).getClass() == ArgsCode.class);
    assertTrue(program.getCode(1).getClass() == BopCode.class);
    assertTrue(program.getCode(2).getClass() == CallCode.class);
    assertTrue(program.getCode(3).getClass() == DmpCode.class);
    assertTrue(program.getCode(4).getClass() == FalsebranchCode.class);
    assertTrue(program.getCode(5).getClass() == GotoCode.class);
    assertTrue(program.getCode(6).getClass() == HaltCode.class);
    assertTrue(program.getCode(7).getClass() == LabelCode.class);
    assertTrue(program.getCode(8).getClass() == LabelCode.class);
    assertTrue(program.getCode(9).getClass() == LabelCode.class);
    assertTrue(program.getCode(10).getClass() == LitCode.class);
    assertTrue(program.getCode(11).getClass() == LitCode.class);
    assertTrue(program.getCode(12).getClass() == LoadCode.class);
    assertTrue(program.getCode(13).getClass() == PopCode.class);
    assertTrue(program.getCode(14).getClass() == ReadCode.class);
    assertTrue(program.getCode(15).getClass() == WriteCode.class);
    assertTrue(program.getCode(16).getClass() == ReturnCode.class);
    assertTrue(program.getCode(17).getClass() == StoreCode.class);
  }
}
