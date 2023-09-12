package cs3500.pa02.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa02.model.Difficulty;
import cs3500.pa02.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the SessionDisplay class
 */
class SessionDisplayTest {
  View display;
  View display2;
  Appendable stringBuilder;
  Appendable mock;
  Question question;

  /**
   * Initializes the data for tests
   */
  @BeforeEach
  void initData() {
    stringBuilder = new StringBuilder();
    mock = new MockAppendable();
    display = new SessionDisplay(stringBuilder);
    display2 = new SessionDisplay(mock);
    question = new Question("How are you?", "Great!", Difficulty.EASY);
  }

  /**
   * Tests for displaySrcQuery method
   */
  @Test
  void displaySrcQuery() {
    assertEquals("", stringBuilder.toString());
    display.displaySrcQuery();
    assertEquals("""

        Welcome to your study session!
        Please provide the path of the desired .sr QuestionBank file:
        """, stringBuilder.toString());
    Exception exc = assertThrows(RuntimeException.class, () -> this.display2.displaySrcQuery(),
        "Mock throwing an error");
    assertEquals("Mock throwing an error", exc.getMessage());
  }

  /**
   * Tests for displaySizeQuery method
   */
  @Test
  void displaySizeQuery() {
    assertEquals("", stringBuilder.toString());
    display.displaySizeQuery();
    assertEquals("How many questions would you like to practice today?\n",
        stringBuilder.toString());
    Exception exc = assertThrows(RuntimeException.class, () -> this.display2.displaySizeQuery(),
        "Mock throwing an error");
    assertEquals("Mock throwing an error", exc.getMessage());
  }

  /**
   * Tests for displayQorA method
   */
  @Test
  void displayQorA() {
    assertEquals("", stringBuilder.toString());
    display.displayQorA(question, 4, true);
    assertEquals("""
                        
            [#4] QUESTION:  How are you?
            Please input the number associated with your desired option:
            1. Mark Easy
            2. Mark Hard
            3. See Answer
            4. Exit
            """,
        stringBuilder.toString());
    display.displayQorA(question, 4, false);
    assertEquals("""
                        
            [#4] QUESTION:  How are you?
            Please input the number associated with your desired option:
            1. Mark Easy
            2. Mark Hard
            3. See Answer
            4. Exit
                        
            [#4] ANSWER:  Great!
            Please input the number associated with your desired option:
            1. Mark Easy
            2. Mark Hard
            3. See Question
            4. Exit
            """,
        stringBuilder.toString());
    Exception exc = assertThrows(RuntimeException.class, () -> this.display2.displayQorA(question,
            1, true),
        "Mock throwing an error");
    assertEquals("Mock throwing an error", exc.getMessage());
  }

  /**
   * Tests for displayStats method
   */
  @Test
  void displayStats() {
    assertEquals("", stringBuilder.toString());
    display.displayStats(new int[] {3, 2, 1, 4, 5});
    assertEquals("""
                        
            You answered 3 questions.
            2 questions went from hard to easy.
            1 questions went from easy to hard.

            Current Counts in Question Bank:
            4 Hard Questions
            5 Easy Questions
            """,
        stringBuilder.toString());
    Exception exc = assertThrows(RuntimeException.class, () ->
            this.display2.displayStats(new int[] {3, 2, 1, 4, 5}),
        "Mock throwing an error");
    assertEquals("Mock throwing an error", exc.getMessage());
  }

  /**
   * Tests for displayError method
   */
  @Test
  void displayError() {
    assertEquals("", stringBuilder.toString());
    display.displayError("Invalid path, please try again.");
    assertEquals("Invalid path, please try again.\n",
        stringBuilder.toString());
    Exception exc = assertThrows(RuntimeException.class, () -> this.display2.displayError("Failed"),
        "Mock throwing an error");
    assertEquals("Mock throwing an error", exc.getMessage());
  }
}