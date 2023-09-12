package cs3500.pa02.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for UserInput enum class
 */
class UserInputTest {

  /**
   * Tests for translate method in UserInput
   */
  @Test
  void translate() {
    assertEquals(UserInput.INVALID, UserInput.translate("haha"));
    assertEquals(UserInput.EXIT, UserInput.translate("4"));
    assertEquals(UserInput.SEE_Q_A, UserInput.translate("3"));
    assertEquals(UserInput.MARK_HARD, UserInput.translate("2"));
    assertEquals(UserInput.MARK_EASY, UserInput.translate("1"));
  }
}