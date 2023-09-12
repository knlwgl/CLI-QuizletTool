package cs3500.pa02.view;

import cs3500.pa02.model.Question;

/**
 * Represents the view component of the MVC pattern
 */
public interface View {
  /**
   * Welcomes the user and asks them for the path to the .sr file
   */
  void displaySrcQuery();

  /**
   * Asks the user how many questions they would like to study
   */
  void displaySizeQuery();

  /**
   * Displays the question or the answer (along with the menu)
   *
   * @param q Question whose Q or A to display
   * @param num the current question number.
   * @param questionHuh which of Q or A to show (true = Q)
   */
  void displayQorA(Question q, int num, boolean questionHuh);

  /**
   * Displays the stats from the session
   *
   * @param stats the stats of the session
   */
  void displayStats(int[] stats);

  /**
   * Displays an error message
   *
   * @param error What message to show the user.
   */
  void displayError(String error);

}
