package cs3500.pa02;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * FileVisitor implementation that is used to traverse the file tree.
 */
public class FileTreeWalkerVisitor implements FileVisitor<Path> {
  private final ArrayList<MarkdownFile> mdFiles;

  /**
   * Initializes a FileTreeWalkerVisitor with an empty Arraylist of MarkdownFile
   */
  public FileTreeWalkerVisitor() {
    mdFiles = new ArrayList<>();
  }

  /**
   * Returns the list of markdown files found so far in FileTreeVisitor execution.
   *
   * @return the markdown files currently found
   */
  public ArrayList<MarkdownFile> getMdFiles() {
    return mdFiles;
  }

  /**
   * Adds the given {@code MarkdownFile} object to the stored list.
   *
   * @param md markdown file to add to the list
   */
  public void addMdFile(MarkdownFile md) {
    mdFiles.add(md);
  }

  /**
   * Adds the file to the arraylist if it ends in .md
   *
   * @param file a reference to the file
   * @param attr the file's basic attributes
   * @return continues the file visitor execution
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
    if (attr.isRegularFile() && file.toString().endsWith(".md")) {
      mdFiles.add(new MarkdownFile(file, attr.creationTime(), attr.lastModifiedTime()));
    }
    return FileVisitResult.CONTINUE;
  }

  /**
   * Prints that the given directory has been checked.
   *
   * @param dir  a reference to the directory
   * @param exec {@code null} if the iteration of the directory completes without
   *             an error; otherwise the I/O exception that caused the iteration
   *             of the directory to complete prematurely
   * @return continues the File Visitor execution
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exec) {
    System.out.println("Checked: " + dir.toString());
    return FileVisitResult.CONTINUE;
  }


  /**
   * Prints that the given directory will be checked.
   *
   * @param dir   a reference to the directory
   * @param attrs the directory's basic attributes
   * @return continue the file visitor execution
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    System.out.println("Checking: " + dir.toString());
    return FileVisitResult.CONTINUE;
  }

  /**
   * Prints which file could not be visited.
   *
   * @param file a reference to the file
   * @param exc  the I/O exception that prevented the file from being visited
   * @return continues the FileVisitor execution
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    System.err.format("Failed to access: %s%nDue to: %s%n", file, exc);
    return FileVisitResult.CONTINUE;
  }

  /**
   * Sorts the list of .md files using the given ordering flag.
   *
   * @param flag is the method of ordering the files (filename, created, or modified)
   * @return a sorted ArrayList of MarkdownFiles
   * @throws IllegalArgumentException if flag is not one of the three ordering flags
   */
  public ArrayList<MarkdownFile> orderedList(OrderingFlag flag) {
    if (flag == OrderingFlag.FILENAME) {
      mdFiles.sort(MarkdownFile::filenameOrder);
    } else if (flag == OrderingFlag.CREATED) {
      mdFiles.sort(MarkdownFile::createdFirst);
    } else {
      mdFiles.sort(MarkdownFile::modifiedLast);
    }
    return mdFiles;
  }
}