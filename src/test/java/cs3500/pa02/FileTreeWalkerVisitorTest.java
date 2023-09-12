package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the FileTreeWalkerVisitor class
 */
public class FileTreeWalkerVisitorTest {
  FileTreeWalkerVisitor fw;
  Path exPath;
  BasicFileAttributes exAttr;
  FileTime exCreation;
  FileTime exModified;
  MarkdownFile exMd;
  Path hahaPath;
  BasicFileAttributes hahaAttr;
  FileTime hahaCreation;
  FileTime hahaModified;
  MarkdownFile hahaMd;
  Path mnmPath;
  BasicFileAttributes mnmAttr;
  FileTime mnmCreation;
  FileTime mnmModified;
  MarkdownFile mnmMd;
  Path lolPath;
  BasicFileAttributes lolAttr;
  Path paPath;
  BasicFileAttributes paAttr;
  Path path404; // i.e. file does not exist
  MarkdownFile mnmMd2;
  MarkdownFile hahaMd2;
  MarkdownFile exMd2;

  /**
   * Initializes data for the tests (Paths, FileTimes, Attributes, MarkdownFiles)
   */
  @BeforeEach
  public void initData() {
    fw = new FileTreeWalkerVisitor();
    exPath = Path.of("sampleInputs/Week2/x.md");
    hahaPath = Path.of("sampleInputs/haha.md");
    mnmPath = Path.of("sampleInputs/mnm.md");
    lolPath = Path.of("sampleInputs/lmao.txt");
    paPath = Path.of("sampleInputs/pa01");
    path404 = Path.of("sampleInputs/pa01/nonexistent.md");
    try {
      exAttr = Files.readAttributes(exPath, BasicFileAttributes.class);
      hahaAttr = Files.readAttributes(hahaPath, BasicFileAttributes.class);
      mnmAttr = Files.readAttributes(mnmPath, BasicFileAttributes.class);
      lolAttr = Files.readAttributes(lolPath, BasicFileAttributes.class);
      paAttr = Files.readAttributes(paPath, BasicFileAttributes.class);
    } catch (IOException e) {
      fail();
    }
    exCreation = exAttr.creationTime();
    exModified = exAttr.lastModifiedTime();
    exMd = new MarkdownFile(exPath, exCreation, exModified);
    hahaCreation = hahaAttr.creationTime();
    hahaModified = hahaAttr.lastModifiedTime();
    hahaMd = new MarkdownFile(hahaPath, hahaCreation, hahaModified);
    mnmCreation = mnmAttr.creationTime();
    mnmModified = mnmAttr.lastModifiedTime();
    mnmMd = new MarkdownFile(mnmPath, mnmCreation, mnmModified);

    mnmMd2 = new MarkdownFile(mnmPath, FileTime.from(Instant.parse("2023-05-13T22:49:58Z")),
        FileTime.from(Instant.parse("2023-05-13T22:55:53.48Z")));
    hahaMd2 = new MarkdownFile(hahaPath, FileTime.from(Instant.parse("2023-05-13T22:39:53Z")),
        FileTime.from(Instant.parse("2023-05-15T22:39:53.91Z")));
    exMd2 = new MarkdownFile(exPath, FileTime.from(Instant.parse("2023-05-13T23:49:58Z")),
        FileTime.from(Instant.parse("2023-05-14T22:55:58.96Z")));
  }

  /**
   * Tests for visitFile() method.
   */
  @Test
  public void testVisitFile() {
    assertEquals(FileVisitResult.CONTINUE, fw.visitFile(mnmPath, mnmAttr));
    assertEquals(new ArrayList<>(Arrays.asList(mnmMd)), fw.getMdFiles());
    assertEquals(FileVisitResult.CONTINUE, fw.visitFile(lolPath, lolAttr));
    assertEquals(new ArrayList<>(Arrays.asList(mnmMd)), fw.getMdFiles());
    assertEquals(FileVisitResult.CONTINUE, fw.visitFile(hahaPath, hahaAttr));
    assertEquals(new ArrayList<>(Arrays.asList(mnmMd, hahaMd)), fw.getMdFiles());
    assertEquals(FileVisitResult.CONTINUE, fw.visitFile(exPath, exAttr));
    assertEquals(new ArrayList<>(Arrays.asList(mnmMd, hahaMd, exMd)), fw.getMdFiles());
    assertEquals(FileVisitResult.CONTINUE, fw.visitFile(paPath, paAttr));
    assertEquals(new ArrayList<>(Arrays.asList(mnmMd, hahaMd, exMd)), fw.getMdFiles());
  }

  /**
   * Test for postVisitDirectory() method.
   */
  @Test
  public void testPostVisitDirectory() {
    assertEquals(FileVisitResult.CONTINUE, fw.preVisitDirectory(Path.of("sampleInputs"),
        exAttr));
    assertEquals(FileVisitResult.CONTINUE, fw.preVisitDirectory(Path.of("sampleInputs/Week2"),
        hahaAttr));
    assertEquals(FileVisitResult.CONTINUE, fw.preVisitDirectory(Path.of("sampleInputs/pa01"),
        mnmAttr));
  }

  /**
   * Tests for preVisitDirectory() method.
   */
  @Test
  public void testPreVisitDirectory() {
    assertEquals(FileVisitResult.CONTINUE, fw.postVisitDirectory(path404, new IOException()));
    assertEquals(FileVisitResult.CONTINUE, fw.postVisitDirectory(Path.of("sampleInputs"),
        null));
    assertEquals(FileVisitResult.CONTINUE, fw.postVisitDirectory(Path.of("sampleInputs/Week2"),
        null));
    assertEquals(FileVisitResult.CONTINUE, fw.postVisitDirectory(Path.of("sampleInputs/pa01"),
        null));
  }

  /**
   * Test for visitFileFailed() method.
   */
  @Test
  public void testVisitFileFailed() {
    assertEquals(FileVisitResult.CONTINUE, fw.visitFileFailed(path404, new IOException()));
    assertEquals(FileVisitResult.CONTINUE, fw.visitFileFailed(hahaPath, new IOException()));
    assertEquals(FileVisitResult.CONTINUE, fw.visitFileFailed(exPath, new IOException()));
  }

  /**
   * Tests for orderedList() method.
   */
  @Test
  public void testOrderedList() {
    fw.addMdFile(hahaMd2);
    fw.addMdFile(exMd2);
    assertEquals(new ArrayList<>(Arrays.asList(hahaMd2, exMd2)),
        fw.orderedList(OrderingFlag.CREATED));
    fw.addMdFile(mnmMd2);
    assertEquals(new ArrayList<>(Arrays.asList(hahaMd2, mnmMd2, exMd2)),
        fw.orderedList(OrderingFlag.FILENAME));
    assertEquals(new ArrayList<>(Arrays.asList(mnmMd2, exMd2, hahaMd2)),
        fw.orderedList(OrderingFlag.MODIFIED));
  }

  /**
   * Tests for getMdFiles() method.
   */
  @Test
  public void testGetMdFiles() {
    assertEquals(new ArrayList<MarkdownFile>(), fw.getMdFiles());
    fw.addMdFile(hahaMd);
    assertEquals(new ArrayList<>(Arrays.asList(hahaMd)), fw.getMdFiles());
    fw.addMdFile(exMd);
    assertEquals(new ArrayList<>(Arrays.asList(hahaMd, exMd)), fw.getMdFiles());
    fw.addMdFile(mnmMd);
    assertEquals(new ArrayList<>(Arrays.asList(hahaMd, exMd, mnmMd)), fw.getMdFiles());
  }
}