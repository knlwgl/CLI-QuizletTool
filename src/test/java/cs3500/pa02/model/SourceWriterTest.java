package cs3500.pa02.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the SourceWriter class
 */
class SourceWriterTest {
  SourceWriter sw1;
  SourceWriter sw2;
  Question q1;
  Question q2;
  Question q3;
  Question q4;
  Question q5;
  Question q6;
  ArrayList<Question> hard;
  ArrayList<Question> easy;
  QuestionBank qb;
  QuestionBank qb2;
  Path outPath;
  Path output404;
  String expected;

  /**
   * Initializes the data for tests
   */
  @BeforeEach
  void initData() {
    q1 = new Question("Example1", "yup", Difficulty.HARD);
    q2 = new Question("Example2", "yea", Difficulty.HARD);
    q3 = new Question("Example3", "hi", Difficulty.HARD);
    hard = new ArrayList<>(Arrays.asList(q1, q2, q3));
    q4 = new Question("Example4", "yup", Difficulty.EASY);
    q5 = new Question("Example5", "yea", Difficulty.EASY);
    q6 = new Question("Example6", "hi", Difficulty.EASY);
    easy = new ArrayList<>(Arrays.asList(q4, q5, q6));
    qb = new QuestionBank(hard, easy);
    qb2 = new QuestionBank(hard, easy, new Random(5));
    outPath = Path.of("sampleOutputs/writerTest.sr");
    output404 = Path.of("sampleOutputs/pa02/zelda");
    sw1 = new SourceWriter(outPath.toFile(), qb);
    sw2 = new SourceWriter(output404.toFile(), qb2);
    expected = """
        [[HARD:::Example1:::yup]]
        [[HARD:::Example2:::yea]]
        [[HARD:::Example3:::hi]]
        [[EASY:::Example4:::yup]]
        [[EASY:::Example5:::yea]]
        [[EASY:::Example6:::hi]]
        """;
  }

  /**
   * Erases the files for reuse in tests
   */
  @AfterEach
  public void eraseTestFiles() {
    try {
      Files.write(outPath, new byte[] {});
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Tests for write method
   */
  @Test
  void write() {
    assertTrue(Files.exists(outPath));
    try {
      assertNotEquals(expected, Files.readString(outPath));
    } catch (IOException e) {
      fail();
    }
    sw1.write();
    try {
      assertEquals(expected, Files.readString(outPath));
    } catch (IOException e) {
      fail();
    }
    assertThrows(IllegalArgumentException.class, () -> sw2.write());
  }
}