package cs3500.pa02.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Question class
 */
class QuestionTest {
  Question q1;
  Question q2;

  /**
   * Initializes data for tests
   */
  @BeforeEach
  void intiData() {
    q1 = new Question("How are you?", "I'm alright...", Difficulty.HARD);
    q2 = new Question("Was this PA cool?", "YES!!!!!!!", Difficulty.EASY);
  }

  /**
   * Test for setDifficulty method
   */
  @Test
  void setDifficulty() {
    assertEquals(Difficulty.HARD, q1.getDifficulty());
    q1.setDifficulty(Difficulty.EASY);
    assertEquals(Difficulty.EASY, q1.getDifficulty());
    assertEquals(Difficulty.EASY, q2.getDifficulty());
    q2.setDifficulty(Difficulty.HARD);
    assertEquals(Difficulty.HARD, q2.getDifficulty());
  }

  /**
   * Tests for getQuestion method
   */
  @Test
  void getQuestion() {
    assertEquals("How are you?", q1.getQuestion());
    assertEquals("Was this PA cool?", q2.getQuestion());
  }

  /**
   * Test for getAnswer method
   */
  @Test
  void getAnswer() {
    assertEquals("I'm alright...", q1.getAnswer());
    assertEquals("YES!!!!!!!", q2.getAnswer());
  }

  /**
   * Tests for toString method
   */
  @Test
  void testToString() {
    assertEquals("[[HARD:::How are you?:::I'm alright...]]", q1.toString());
    assertEquals("[[EASY:::Was this PA cool?:::YES!!!!!!!]]", q2.toString());
  }

  /**
   * Tests for getDifficulty method
   */
  @Test
  void getDifficulty() {
    assertEquals(Difficulty.HARD, q1.getDifficulty());
    assertEquals(Difficulty.EASY, q2.getDifficulty());
  }

  /**
   * Tests for overidden .equals method
   */
  @Test
  void testEquals() {
    assertTrue(q1.equals(new Question("How are you?", "I'm alright...", Difficulty.HARD)));
    assertFalse(q1.equals("Hi"));
    assertFalse(q2.equals(q1));
    assertFalse(q1.equals(new Question("How are you?", "hmm", Difficulty.HARD)));
    assertFalse(q1.equals(new Question("What's up?", "I'm alright...", Difficulty.HARD)));
    assertFalse(q1.equals(new Question("How are you?", "I'm alright...", Difficulty.EASY)));
  }

  /**
   * Tests for overriden .hashCode method
   */
  @Test
  void testHashCode() {
    assertEquals(-75577972, q1.hashCode());
    assertEquals(434869253, q2.hashCode());
  }
}