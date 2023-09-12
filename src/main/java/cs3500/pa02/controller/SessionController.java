package cs3500.pa02.controller;

import cs3500.pa02.model.Difficulty;
import cs3500.pa02.model.Model;
import cs3500.pa02.model.Question;
import cs3500.pa02.model.SessionData;
import cs3500.pa02.view.SessionDisplay;
import cs3500.pa02.view.View;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents the controller for a study session application
 */
public class SessionController implements Controller {
  Readable input;
  Appendable output;

  public SessionController(Readable in, Appendable out) {
    input = Objects.requireNonNull(in);
    output = Objects.requireNonNull(out);
  }

  /**
   * Executes a study session application using MVC framework
   */
  public void run() {
    Scanner sc = new Scanner(input);
    View display = new SessionDisplay(output);
    Model data = this.initSession(sc, display);
    display.displayQorA(data.getCurrent(), data.questionNum(), data.switchState());
    this.processInput(sc, display, data);
    display.displayStats(data.getStats());
    data.updateFile();
  }

  /**
   * Processes user input
   *
   * @param sc scanner that iterates user inputs
   * @param display display that handles showing the ui
   * @param data model that stores all information regarding the state
   */
  private void processInput(Scanner sc, View display, Model data) {
    while (sc.hasNextLine()) {
      UserInput ui = UserInput.translate(sc.nextLine());
      if (ui == UserInput.MARK_EASY || ui == UserInput.MARK_HARD) {
        Difficulty d = ((ui == UserInput.MARK_EASY) ? Difficulty.EASY : Difficulty.HARD);
        data.setDifficulty(d);
        Question next;
        try {
          next = data.nextQuestion();
        } catch (IndexOutOfBoundsException e) {
          break;
        }
        display.displayQorA(next, data.questionNum(), data.switchState());
      } else if (ui == UserInput.SEE_Q_A) {
        display.displayQorA(data.getCurrent(), data.questionNum(), data.switchState());
      } else if (ui == UserInput.EXIT) {
        break;
      } else {
        display.displayError("Invalid input, please try again.");
      }
    }
  }

  /**
   * Initializes the Model for the study session application.
   *
   * @param sc      Scanner of the readable input
   * @param display the view component of the MVC
   * @return Model component of the MVC
   */
  private Model initSession(Scanner sc, View display) {
    display.displaySrcQuery();
    String path = sc.nextLine();
    File src = new File(path);
    while (!path.endsWith(".sr") || !src.exists()) {
      display.displayError("Invalid path, please try again.");
      path = sc.nextLine();
      src = new File(path);
    }
    display.displaySizeQuery();
    int numQ = -1;
    while (numQ <= 0) {
      try {
        numQ = Integer.parseInt(sc.nextLine());
        if (numQ <= 0) {
          display.displayError("Invalid # of Questions, please try again.");
        }
      } catch (Exception e) {
        display.displayError("Invalid # of Questions, please try again.");
      }
    }
    return new SessionData(src, numQ);
  }
}
