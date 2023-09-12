package cs3500.pa02.controller;

import cs3500.pa02.FileTreeWalkerVisitor;
import cs3500.pa02.MarkdownFile;
import cs3500.pa02.OrderingFlag;
import cs3500.pa02.Summarizer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents the controller for a summarizer mode app
 */
public class SummaryController implements Controller {
  String[] args;

  public SummaryController(String[] inputs) {
    args = inputs;
  }

  /**
   * Runs the summarizer application
   */
  public void run() {
    Path startingDirectory = Path.of(args[0]);
    if (!Files.exists(startingDirectory)) {
      throw new IllegalArgumentException("Could not find starting directory.");
    }
    OrderingFlag flag;
    try {
      flag = OrderingFlag.valueOf(args[1].toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Ordering flag is not one of the three possible.");
    }
    System.out.println("\nSummarizing... \n");

    FileTreeWalkerVisitor fw = new FileTreeWalkerVisitor();
    try {
      Files.walkFileTree(startingDirectory, fw);
    } catch (IOException e) {
      e.printStackTrace();
    }
    ArrayList<MarkdownFile> sorted = fw.orderedList(flag);
    Path outputDirectory = Path.of(args[2]);
    Summarizer sum = new Summarizer(sorted);
    sum.summarize(outputDirectory);
  }
}
