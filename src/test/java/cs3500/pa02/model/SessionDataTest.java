package cs3500.pa02.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
 * Tests for SessionData class
 */
class SessionDataTest {
  Question q1;
  Question q2;
  Question q3;
  Question q4;
  Question q5;
  Question q6;
  Question q7;
  Question q8;
  Question q9;
  Question q10;
  ArrayList<Question> hard;
  ArrayList<Question> easy;
  QuestionBank qb;
  QuestionBank qb2;
  Path inPath;
  Path output404;
  String original;
  String expected;
  Model sessionData;

  /**
   * Initializes data for tests
   */
  @BeforeEach
  void initData() {
    q1 = new Question("What is the capital of Canada?", "The capital is Ottawa.",
        Difficulty.HARD);
    q2 = new Question("Which country is known as the Land of the Rising Sun?", "Japan.",
        Difficulty.HARD);
    q3 = new Question("What is the largest river in Africa?",
        "The largest river is the Nile River.", Difficulty.HARD);
    q4 = new Question("Which continent is the driest inhabited continent on Earth?",
        "Australia.", Difficulty.HARD);
    q5 = new Question("Which continent is known as the \"Roof of the World\"?", "Asia.",
        Difficulty.HARD);
    q6 = new Question("What is the largest desert in Asia?",
        "The largest desert is the Gobi Desert.", Difficulty.HARD);
    q7 = new Question("What is the official language of Brazil?",
        "The official language is Portuguese.", Difficulty.HARD);
    q8 = new Question("What is the largest island in the world?",
        "The largest island is Greenland.", Difficulty.HARD);
    q9 = new Question("Which country is known as the Land of Fire and Ice?", "Iceland.",
        Difficulty.HARD);
    q10 = new Question("Which country is located in both Europe and Asia?",
        "Russia.", Difficulty.HARD);
    hard = new ArrayList<>(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));
    easy = new ArrayList<>();
    qb = new QuestionBank(hard, easy, new Random(2));
    qb2 = new QuestionBank(hard, easy, new Random(3));
    inPath = Path.of("sampleInputs/questionBank.sr");
    output404 = Path.of("sampleOutputs/pa02/zelda");
    original = """
        [[HARD:::What is the capital of Canada?:::The capital is Ottawa.]]
        [[HARD:::Which country is known as the Land of the Rising Sun?:::Japan.]]
        [[HARD:::What is the largest river in Africa?:::The largest river is the Nile River.]]
        [[HARD:::Which continent is the driest inhabited continent on Earth?:::Australia.]]
        [[HARD:::Which continent is known as the "Roof of the World"?:::Asia.]]
        [[HARD:::What is the largest desert in Asia?:::The largest desert is the Gobi Desert.]]
        [[HARD:::What is the official language of Brazil?:::The official language is Portuguese.]]
        [[HARD:::What is the largest island in the world?:::The largest island is Greenland.]]
        [[HARD:::Which country is known as the Land of Fire and Ice?:::Iceland.]]
        [[HARD:::Which country is located in both Europe and Asia?:::Russia.]]
        """;
    sessionData = new SessionData(inPath.toFile(), qb, 10);
    expected = """
        [[HARD:::What is the capital of Canada?:::The capital is Ottawa.]]
        [[HARD:::Which country is known as the Land of the Rising Sun?:::Japan.]]
        [[HARD:::What is the largest river in Africa?:::The largest river is the Nile River.]]
        [[HARD:::Which continent is the driest inhabited continent on Earth?:::Australia.]]
        [[EASY:::Which continent is known as the "Roof of the World"?:::Asia.]]
        [[HARD:::What is the largest desert in Asia?:::The largest desert is the Gobi Desert.]]
        [[EASY:::What is the official language of Brazil?:::The official language is Portuguese.]]
        [[HARD:::What is the largest island in the world?:::The largest island is Greenland.]]
        [[EASY:::Which country is known as the Land of Fire and Ice?:::Iceland.]]
        [[HARD:::Which country is located in both Europe and Asia?:::Russia.]]
        """;
  }

  /**
   * Rewrites test files for reuse in tests
   */
  @AfterEach
  public void writeTestFiles() {
    try {
      Files.write(inPath, original.getBytes());
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Tests for getCurrent method
   */
  @Test
  void getCurrent() {
    assertEquals(q9, sessionData.getCurrent());
    sessionData.nextQuestion();
    assertEquals(q7, sessionData.getCurrent());
    sessionData.nextQuestion();
    assertEquals(q10, sessionData.getCurrent());
    sessionData.nextQuestion();
    assertEquals(q5, sessionData.getCurrent());
    sessionData.nextQuestion();
    assertEquals(q4, sessionData.getCurrent());
  }

  /**
   * Tests for nextQuestion method
   */
  @Test
  void nextQuestion() {
    assertEquals(q9, sessionData.getCurrent());
    assertEquals(q7, sessionData.nextQuestion());
    assertEquals(q7, sessionData.getCurrent()); // to check that nextQuestion mutates sessionData
    assertEquals(q10, sessionData.nextQuestion());
    assertEquals(q10, sessionData.getCurrent()); // to check that nextQuestion mutates sessionData
    assertEquals(q5, sessionData.nextQuestion());
    assertEquals(q5, sessionData.getCurrent()); // to check that nextQuestion mutates sessionData
    assertEquals(q4, sessionData.nextQuestion());
    assertEquals(q4, sessionData.getCurrent()); // to check that nextQuestion mutates sessionData
  }

  /**
   * Tests for switchState method
   */
  @Test
  void switchState() {
    assertTrue(sessionData.switchState()); // mutates and returns flipped bool
    assertFalse(sessionData.switchState());
    assertTrue(sessionData.switchState());
  }

  /**
   * Tests for questionNum method
   */
  @Test
  void questionNum() {
    assertEquals(1, sessionData.questionNum());
    sessionData.nextQuestion();
    assertEquals(2, sessionData.questionNum());
    sessionData.nextQuestion();
    assertEquals(3, sessionData.questionNum());
    sessionData.nextQuestion();
    assertEquals(4, sessionData.questionNum());
    sessionData.nextQuestion();
    assertEquals(5, sessionData.questionNum());
  }

  /**
   * Tests for getStats method
   */
  @Test
  void getStats() {
    sessionData.setDifficulty(Difficulty.EASY);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.EASY);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.HARD);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.EASY);
    sessionData.nextQuestion();
    int[] expected = new int[] {4, 3, 0, 7, 3};
    int[] actual = sessionData.getStats();
    for (int i = 0; i < actual.length; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  /**
   * Tests for setDifficulty method
   */
  @Test
  void setDifficulty() {
    sessionData.setDifficulty(Difficulty.EASY);
    assertEquals(Difficulty.EASY, sessionData.getCurrent().getDifficulty());
    assertEquals(1, sessionData.getStats()[1]);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.EASY);
    assertEquals(Difficulty.EASY, sessionData.getCurrent().getDifficulty());
    assertEquals(2, sessionData.getStats()[1]);
    sessionData.setDifficulty(Difficulty.HARD);
    assertEquals(Difficulty.HARD, sessionData.getCurrent().getDifficulty());
    assertEquals(1, sessionData.getStats()[2]);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.HARD);
    assertEquals(Difficulty.HARD, sessionData.getCurrent().getDifficulty());
    assertEquals(2, sessionData.getStats()[1]);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.EASY);
    assertEquals(Difficulty.EASY, sessionData.getCurrent().getDifficulty());
    assertEquals(3, sessionData.getStats()[1]);
    sessionData.setDifficulty(Difficulty.EASY);
    assertEquals(Difficulty.EASY, sessionData.getCurrent().getDifficulty());
    assertEquals(3, sessionData.getStats()[1]);
    sessionData.nextQuestion();
  }

  /**
   * Tests for updateFile method
   */
  @Test
  void updateFile() {
    sessionData.setDifficulty(Difficulty.EASY);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.EASY);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.HARD);
    sessionData.nextQuestion();
    sessionData.setDifficulty(Difficulty.EASY);
    sessionData.nextQuestion();
    assertTrue(Files.exists(inPath));
    try {
      assertEquals(original, Files.readString(inPath));
    } catch (IOException e) {
      fail();
    }
    sessionData.updateFile();
    try {
      assertEquals(expected, Files.readString(inPath));
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Tests for when the SessionData should fail to initialize a QuestionBank
   */
  @Test
  void testInitQbankFails() {
    assertThrows(IllegalArgumentException.class, () -> new SessionData(output404.toFile(), 4));
  }
}