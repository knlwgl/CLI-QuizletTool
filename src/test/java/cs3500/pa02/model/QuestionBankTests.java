package cs3500.pa02.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for QuestionBank class
 */
class QuestionBankTests {
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

  /**
   * Initializes data for tests
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
    qb = new QuestionBank(hard, easy, new Random(2));
    qb2 = new QuestionBank(hard, easy, new Random(5));
  }

  /**
   * Test for getHard method
   */
  @Test
  void getHard() {
    assertEquals(hard, qb.getHard());
  }

  /**
   * Test for getEasy method
   */
  @Test
  void getEasy() {
    assertEquals(easy, qb.getEasy());
  }

  /**
   * Tests for getList method
   */
  @Test
  void getList() {
    assertEquals(new ArrayList<>(Arrays.asList(q2, q1, q3, q5, q4)), qb.getList(5));
    assertEquals(new ArrayList<>(Arrays.asList(q1, q3, q2, q5, q6, q4)), qb.getList(6));
    assertEquals(new ArrayList<>(Arrays.asList(q3, q1)), qb2.getList(2));
    assertEquals(new ArrayList<>(Arrays.asList(q3, q2, q1, q6, q4, q5)), qb2.getList(10));
  }
}