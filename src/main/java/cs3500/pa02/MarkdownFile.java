package cs3500.pa02;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

/**
 * Represents a markdown file with it's path, date of creation and last modified date.
 */
public class MarkdownFile {
  private Path file;
  private FileTime createdAt;
  private FileTime lastModified;

  /**
   * Initializes a MarkdownFile to represent a file with .md extension
   *
   * @param path the path to the .md file
   * @param creation the time of creation of the file
   * @param modified the last modified time of the file
   */
  MarkdownFile(Path path, FileTime creation, FileTime modified) {
    file = path;
    createdAt = creation;
    lastModified = modified;
  }

  /**
   * What is the name of the file?
   *
   * @return name of the file
   */
  public String getName() {
    return file.getFileName().toString();
  }

  /**
   * What file is represented by the MarkdownFile?
   *
   * @return the file as a {@code File} object
   */
  public File getFile() {
    return file.toFile();
  }

  /**
   * When was the file created?
   *
   * @return time of file creation
   */
  public FileTime getCreationTime() {
    return this.createdAt;
  }


  /**
   * When was the file last modified?
   *
   * @return time of last modification to the file
   */
  public FileTime getModifiedTime() {
    return this.lastModified;
  }

  /**
   * Checks which MarkdownFile was created earlier.
   *
   * @param other the MarkdownFile to compare this one to
   * @return 0 if they were created at the same time, a value less than 0 if this MarkdownFile was
   *     created first, and a value greater than 0 if this MarkdownFile was created after the other.
   */
  public int createdFirst(MarkdownFile other) {
    return this.createdAt.compareTo(other.getCreationTime());
  }

  /**
   * Checks which MarkdownFile was modified later.
   *
   * @param other the MarkdownFile to compare this one to
   * @return 0 if they were last modified at the same time, a value less than 0 if this MarkdownFile
   *     was modified later, and a value greater than 0 if this MarkdownFile was modified earlier
   *     than the other.
   */
  public int modifiedLast(MarkdownFile other) {
    return this.lastModified.compareTo(other.getModifiedTime());
  }

  /**
   * Checks which MarkdownFile comes first alphabetically.
   *
   * @param other the MarkdownFile to compare this one to
   * @return 0 if they have the same filename, a value less than 0 if this MarkdownFile comes first
   *     alphabetically, and a value greater than 0 if this MarkdownFile comes later alphabetically.
   */
  public int filenameOrder(MarkdownFile other) {
    return file.getFileName().toString().compareTo(other.getName());
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param other an object to compare to this one
   * @return boolean whether or not it is equal to this MarkdownFile
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof MarkdownFile md) {
      return file.equals(md.file) && createdAt.compareTo(md.createdAt) == 0
          && lastModified.compareTo(md.lastModified) == 0;
    } else {
      return false;
    }
  }

  /**
   * Returns a hash code value for the MarkdownFile.
   *
   * @return Returns a hash code value for the file.
   */
  @Override
  public int hashCode() {
    return (file.hashCode() * 10000000) + createdAt.hashCode() + (lastModified.hashCode() * 10);
  }
}
