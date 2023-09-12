package cs3500.pa02;

import cs3500.pa02.controller.Controller;
import cs3500.pa02.controller.SessionController;
import cs3500.pa02.controller.SummaryController;
import java.io.InputStreamReader;

/**
 * Initializes the process of summarizing .md files in given directory or the process for doing a
 * Study session.
 */
public class Driver {
  /**
   * Initializes the process of summarizing .md files in given directory or the process for doing a
   * Study session.
   *
   * @param args directory to summarize from, flag to order files, and directory with filename to
   *             summarize to OR no args for initializing the Study Session mode.
   */
  public static void main(String[] args) {
    Controller ctrlr;
    if (args.length == 3) {
      ctrlr = new SummaryController(args);
    } else if (args.length == 0) {
      Readable input = new InputStreamReader(System.in);
      ctrlr = new SessionController(input, System.out);
    } else {
      throw new IllegalArgumentException(
          "Either no arguments or three arguments required: root, ordering flag and output path.");
    }
    ctrlr.run();
  }
}