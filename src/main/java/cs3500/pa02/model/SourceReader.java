package cs3500.pa02.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads the metadata from a .sr file
 */
public class SourceReader {
  File file;

  SourceReader(File f) {
    file = f;
  }

  /**
   * Reads the metadata from a .sr file to a QuestionBank
   */
  public QuestionBank read() throws FileNotFoundException {
    ArrayList<Question> hard = new ArrayList<>();
    ArrayList<Question> easy = new ArrayList<>();
    Scanner sc = new Scanner(file);
    while (sc.hasNextLine()) {
      StringBuilder current = new StringBuilder(sc.nextLine());
      int start = current.indexOf("[[");
      int end = current.indexOf("]]");
      int startQ = current.indexOf(":::");
      int startA = current.lastIndexOf(":::");
      Difficulty d = Difficulty.valueOf(current.substring(start + 2, startQ));
      Question curr = new Question(current.substring(startQ + 3, startA),
          current.substring(startA + 3, end), d);
      if (d == Difficulty.EASY) {
        easy.add(curr);
      } else {
        hard.add(curr);
      }
    }
    return new QuestionBank(hard, easy);
  }
}
