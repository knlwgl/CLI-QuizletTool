package cs3500.pa02.view;

import cs3500.pa02.model.Question;
import java.io.IOException;

/**
 * Handles displaying the study session app
 */
public class SessionDisplay implements View {
  Appendable output;

  public SessionDisplay(Appendable out) {
    output = out;
  }

  /**
   * Welcomes the user and asks them for the path to the .sr file
   */
  @Override
  public void displaySrcQuery() {
    StringBuilder out = new StringBuilder();
    out.append(String.format("%nWelcome to your study session!%n"));
    out.append(String.format("Please provide the path of the desired .sr QuestionBank file:%n"));
    try {
      output.append(out);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Asks the user how many questions they would like to study
   */
  @Override
  public void displaySizeQuery() {
    try {
      output.append("How many questions would you like to practice today?");
      output.append("\n");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Displays the question or the answer (along with the menu)
   *
   * @param q Question whose Q or A to display
   * @param num the current question number.
   * @param questionHuh which of Q or A to show (true = Q)
   */
  @Override
  public void displayQorA(Question q, int num, boolean questionHuh) {
    StringBuilder out = new StringBuilder();
    out.append(String.format("%n[#%s] ", num));
    out.append(questionHuh ? "QUESTION:  " : "ANSWER:  ");
    out.append(questionHuh ? q.getQuestion() : q.getAnswer()).append("\n");
    out.append(SessionDisplay.displayMenu(questionHuh));
    try {
      output.append(out);
      output.append("\n");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Displays the stats from the session
   *
   * @param stats the stats of the session
   */
  @Override
  public void displayStats(int[] stats) {
    StringBuilder out = new StringBuilder();
    out.append(String.format("%nYou answered %s questions.%n", stats[0]));
    out.append(String.format("%s questions went from hard to easy.%n", stats[1]));
    out.append(String.format("%s questions went from easy to hard.%n", stats[2]));
    out.append(String.format("%nCurrent Counts in Question Bank:%n"));
    out.append(String.format("%s Hard Questions%n", stats[3]));
    out.append(String.format("%s Easy Questions%n", stats[4]));
    try {
      output.append(out);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Displays an error message
   *
   * @param error What message to show the user.
   */
  @Override
  public void displayError(String error) {
    try {
      output.append(error);
      output.append("\n");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Returns the string for the input options menu
   *
   * @param questionHuh Is the question currently being displayed?
   * @return menu of options
   */
  private static String displayMenu(boolean questionHuh) {
    StringBuilder out = new StringBuilder();
    out.append("Please input the number associated with your desired option:\n");
    out.append("1. Mark Easy\n2. Mark Hard");
    out.append(questionHuh ? "\n3. See Answer" : "\n3. See Question");
    out.append("\n4. Exit");
    return out.toString();
  }
}
