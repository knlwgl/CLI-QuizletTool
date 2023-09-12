package cs3500.pa02.model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Writes the metadata of the questionbank to the file
 */
public class SourceWriter {
  File file;
  QuestionBank qb;

  SourceWriter(File f, QuestionBank bank) {
    file = f;
    qb = bank;
  }

  /**
   * Writes the metadata of the Questionbank to the file
   */
  public void write() {
    StringBuilder output = new StringBuilder();
    ArrayList<Question> hard = qb.getHard();
    ArrayList<Question> easy = qb.getEasy();
    for (Question q : hard) {
      output.append(q.toString());
      output.append("\n");
    }
    for (Question q : easy) {
      output.append(q.toString());
      output.append("\n");
    }
    byte[] data = output.toString().getBytes();
    Path path = file.toPath();
    try {
      Files.write(path, data);
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not access output directory.");
    }
  }
}
