package cs3500.pa02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Summarizes the given list of .md files and writes the output file.
 */
public class Summarizer {
  private final StringBuilder summary;
  private final StringBuilder questionBank;
  private final ArrayList<MarkdownFile> mdFiles;

  /**
   * Initializes a Summarizer object with a given list of MarkdownFiles
   *
   * @param files list of MarkdownFiles to summarize
   */
  public Summarizer(ArrayList<MarkdownFile> files) {
    mdFiles = files;
    summary = new StringBuilder();
    questionBank = new StringBuilder();
  }

  /**
   * Summarizes the contents from the list of files and collects the questions contained. Writes
   * content to a study guide and questions to a questionbank.
   *
   * @param path where the summary markdown file should be written
   */
  public void summarize(Path path) {
    // send each file to be read
    for (MarkdownFile md : mdFiles) {
      File file = md.getFile();
      Scanner sc;
      try {
        sc = new Scanner(new FileInputStream(file));
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      while (sc.hasNextLine()) {
        summary.append(this.importantText(sc, new StringBuilder()));
      }
    }
    // write resulting summary to file
    byte[] summaryData = summary.toString().getBytes();
    byte[] questionData = questionBank.toString().getBytes();
    Path outputMd = Path.of(path + ".md");
    Path outputSr = Path.of(path + ".sr");
    try {
      Files.write(outputMd, summaryData);
      Files.write(outputSr, questionData);
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not access output directory.");
    }
  }

  /**
   * extracts the important text and questions from the given string, returns an empty string if
   * nothing important
   *
   * @param sc          scanner to produce lines from the file
   * @param currentLine String to extract from
   * @return Important text from str (starts with #, or inside [[]])
   */
  private String importantText(Scanner sc, StringBuilder currentLine) {
    StringBuilder important = new StringBuilder();
    StringBuilder curr;
    if (currentLine.isEmpty() && sc.hasNextLine()) {
      curr = new StringBuilder(sc.nextLine());
    } else {
      curr = currentLine;
    }
    if (curr.indexOf("#") == 0) {
      if (!summary.isEmpty()) {
        important.append("\n");
      }
      important.append(curr).append("\n");
    } else if (curr.indexOf("[[") != -1) {
      if (curr.indexOf("]]") != -1) {
        if (curr.indexOf(":::") != -1) {
          StringBuilder question = new StringBuilder("[[HARD:::");
          question.append(curr.substring(curr.indexOf("[[") + 2, curr.indexOf("]]") + 2));
          questionBank.append(question).append("\n");
        } else {
          String sub = curr.substring(curr.indexOf("[[") + 2, curr.indexOf("]]"));
          important.append("- ").append(sub).append("\n");

          String rest = curr.substring(curr.indexOf("]]") + 2);
          important.append(this.importantText(sc, new StringBuilder(rest)));
        }
      } else {
        curr.append(sc.nextLine());
        String sub = curr.substring(curr.indexOf("[["));
        important.append(this.importantText(sc, new StringBuilder(sub)));
      }
    }
    return important.toString();
  }
}
