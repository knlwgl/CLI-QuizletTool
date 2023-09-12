package cs3500.pa02.model;

/**
 * Represents the Model component of the study session
 */
public interface Model {
  /**
   * Advances the model to the next question and returns it
   *
   * @return the next question
   */
  Question nextQuestion();

  /**
   * Provides the statistics of the study session
   *
   * @return an int array of the stats of the session
   */
  int[] getStats();

  /**
   * Sets the difficulty of the current question to the provided
   *
   * @param d Difficulty to set the current question to
   */
  void setDifficulty(Difficulty d);

  /**
   * Updates the source .sr file to match the changes made during the session
   */
  void updateFile();

  /**
   * Returns the current question
   *
   * @return the current question
   */
  Question getCurrent();

  /**
   * Identifies if the user is checking the question or answer
   *
   * @return is the question on display right now?
   */
  boolean switchState();

  /**
   * What is the number of the current question?
   *
   * @return the current question number
   */
  int questionNum();
}
