package cs3500.pa02.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the methods in the SessionController class
 */
class SessionControllerTest {
  File stream;
  File stream2;
  FileReader in;
  StringBuilder out;
  FileReader in2;
  StringBuilder out2;
  Controller sessControl;
  Controller sessControl2;
  Path inPath;
  String original;
  String original2;
  String srFile;
  String expected;
  String expected2;

  /**
   * initializes the data for tests
   */
  @BeforeEach
  void initData() throws IOException {
    stream = new File("sampleInputs/inputs.txt");
    stream2 = new File("sampleInputs/inputs2.txt");
    inPath = Path.of("sampleInputs/generic.sr");
    in = new FileReader(stream);
    out = new StringBuilder();
    in2 = new FileReader(stream2);
    out2 = new StringBuilder();
    sessControl = new SessionController(in, out);
    sessControl2 = new SessionController(in2, out2);
    // NOTE: used generic questions because randomness is tested in other classes
    srFile = """
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        [[HARD:::Generic Question string here?:::Generic Answer string here.]]
        """;
    original = """
        incorrectfilepath
        filedoesnotexist.sr
        sampleInputs/generic.sr
        -5
        not a number hehe
        5
        1
        2
        3
        3
        2
        hehe
        2
        4
        """;
    original2 = """
        sampleInputs/generic.sr
        3
        1
        2
        3
        1
        """;
    expected = """
                
        Welcome to your study session!
        Please provide the path of the desired .sr QuestionBank file:
        Invalid path, please try again.
        Invalid path, please try again.
        How many questions would you like to practice today?
        Invalid # of Questions, please try again.
        Invalid # of Questions, please try again.
                
        [#1] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        [#2] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        [#3] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        [#3] ANSWER:  Generic Answer string here.
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Question
        4. Exit
                
        [#3] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        [#4] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
        Invalid input, please try again.
                
        [#5] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        You answered 4 questions.
        1 questions went from hard to easy.
        0 questions went from easy to hard.
                
        Current Counts in Question Bank:
        12 Hard Questions
        1 Easy Questions
        """;
    expected2 = """
                
        Welcome to your study session!
        Please provide the path of the desired .sr QuestionBank file:
        How many questions would you like to practice today?
                
        [#1] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        [#2] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        [#3] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        [#3] ANSWER:  Generic Answer string here.
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Question
        4. Exit
                
        [#4] QUESTION:  Generic Question string here?
        Please input the number associated with your desired option:
        1. Mark Easy
        2. Mark Hard
        3. See Answer
        4. Exit
                
        You answered 3 questions.
        2 questions went from hard to easy.
        0 questions went from easy to hard.
                
        Current Counts in Question Bank:
        11 Hard Questions
        2 Easy Questions
        """;
  }

  /**
   * Rewrites test files for reuse in tests
   */
  @AfterEach
  void rewriteTestFiles() {
    try {
      Files.write(stream.toPath(), original.getBytes());
      Files.write(stream2.toPath(), original2.getBytes());
      Files.write(inPath, srFile.getBytes());
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Test #1 for the run method
   */
  @Test
  void runTest1() {
    assertEquals("", out.toString());
    sessControl.run();
    assertEquals(expected, out.toString());
  }

  /**
   * Test #2 for the run method
   */
  @Test
  void runTest2() {
    assertEquals("", out2.toString());
    sessControl2.run();
    assertEquals(expected2, out2.toString());
  }
}