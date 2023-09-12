package cs3500.pa02.controller;

/**
 * Represents a user input for the study session
 */
public enum UserInput {
  MARK_EASY,
  MARK_HARD,
  SEE_Q_A,
  EXIT,
  INVALID;

  /**
   * Translates from string input to UserInput
   *
   * @param str Input as a string
   * @return input translated to UserInput enum
   */
  public static UserInput translate(String str) {
    return switch (str) {
      case "1" -> UserInput.MARK_EASY;
      case "2" -> UserInput.MARK_HARD;
      case "3" -> UserInput.SEE_Q_A;
      case "4" -> UserInput.EXIT;
      default -> UserInput.INVALID;
    };
  }
}
