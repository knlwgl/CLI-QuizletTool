package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the MarkdownFile class
 */
public class MarkdownFileTest {
  Path exPath;
  FileTime exCreation;
  FileTime exModified;
  MarkdownFile exMd;
  Path hahaPath;
  FileTime hahaCreation;
  FileTime hahaModified;
  MarkdownFile hahaMd;
  Path mnmPath;
  FileTime mnmCreation;
  FileTime mnmModified;
  MarkdownFile mnmMd;

  /**
   * Initializes data for tests (Paths, Creation/Modified times, MarkdownFiles)
   */
  @BeforeEach
  public void initData() {
    exPath = Path.of("sampleInputs/Week2/x.md");
    exCreation = FileTime.from(Instant.parse("2023-05-13T22:49:58Z"));
    exModified = FileTime.from(Instant.parse("2023-05-13T22:49:58.48Z"));
    exMd = new MarkdownFile(exPath, exCreation, exModified);
    hahaPath = Path.of("sampleInputs/haha.md");
    hahaCreation = FileTime.from(Instant.parse("2023-05-13T22:39:53Z"));
    hahaModified = FileTime.from(Instant.parse("2023-05-13T22:39:53.91Z"));
    hahaMd = new MarkdownFile(hahaPath, hahaCreation, hahaModified);
    mnmPath = Path.of("sampleInputs/mnm.md");
    mnmCreation = FileTime.from(Instant.parse("2023-05-13T22:49:58Z"));
    mnmModified = FileTime.from(Instant.parse("2023-05-13T22:55:58.48Z"));
    mnmMd = new MarkdownFile(mnmPath, mnmCreation, mnmModified);
  }

  /**
   * Test for the getName() method
   */
  @Test
  public void testGetName() {
    assertEquals("x.md", exMd.getName());
    assertEquals("haha.md", hahaMd.getName());
    assertEquals("mnm.md", mnmMd.getName());
  }

  /**
   * Test for the getCreationTime() method
   */
  @Test
  public void testGetCreationTime() {
    assertEquals("2023-05-13T22:49:58Z", exMd.getCreationTime().toString());
    assertEquals("2023-05-13T22:39:53Z", hahaMd.getCreationTime().toString());
    assertEquals("2023-05-13T22:49:58Z", mnmMd.getCreationTime().toString());
  }

  /**
   * Test for the getModifiedTime() method
   */
  @Test
  public void testGetModifiedTime() {
    assertEquals("2023-05-13T22:49:58.48Z", exMd.getModifiedTime().toString());
    assertEquals("2023-05-13T22:39:53.91Z", hahaMd.getModifiedTime().toString());
    assertEquals("2023-05-13T22:55:58.48Z", mnmMd.getModifiedTime().toString());
  }

  /**
   * Test for the getFile() method
   */
  @Test
  public void testGetFile() {
    assertEquals(Path.of("sampleInputs/Week2/x.md").toFile(), exMd.getFile());
    assertEquals(Path.of("sampleInputs/haha.md").toFile(), hahaMd.getFile());
    assertEquals(Path.of("sampleInputs/mnm.md").toFile(), mnmMd.getFile());
  }

  /**
   * Test for the createdFirst() method
   */
  @Test
  public void testCreatedFirst() {
    assertEquals(1, exMd.createdFirst(hahaMd));
    assertEquals(-1, hahaMd.createdFirst(mnmMd));
    assertEquals(0, mnmMd.createdFirst(exMd));
    assertEquals(-1, hahaMd.createdFirst(exMd));
    assertEquals(0, hahaMd.createdFirst(hahaMd));
    assertEquals(1, mnmMd.createdFirst(hahaMd));
    assertEquals(0, mnmMd.createdFirst(mnmMd));
  }

  /**
   * Test for the modifiedLast() method
   */
  @Test
  public void testModifiedLast() {
    assertEquals(1, exMd.modifiedLast(hahaMd));
    assertEquals(-1, hahaMd.modifiedLast(mnmMd));
    assertEquals(1, mnmMd.modifiedLast(exMd));
    assertEquals(-1, hahaMd.modifiedLast(exMd));
    assertEquals(0, hahaMd.modifiedLast(hahaMd));
    assertEquals(1, mnmMd.modifiedLast(hahaMd));
    assertEquals(0, mnmMd.modifiedLast(mnmMd));
  }

  /**
   * Test for the filenameOrder() method
   */
  @Test
  public void testFilenameOrder() {
    assertEquals(-5, hahaMd.filenameOrder(mnmMd));
    assertEquals(-11, mnmMd.filenameOrder(exMd));
    assertEquals(-16, hahaMd.filenameOrder(exMd));
    assertEquals(0, hahaMd.filenameOrder(hahaMd));
    assertEquals(0, exMd.filenameOrder(exMd));
    assertEquals(0, mnmMd.filenameOrder(mnmMd));
    assertEquals(5, mnmMd.filenameOrder(hahaMd));
    assertEquals(11, exMd.filenameOrder(mnmMd));
    assertEquals(16, exMd.filenameOrder(hahaMd));
  }

  /**
   * Test for the .equals() overriden method
   */
  @Test
  public void testEquals() {
    assertTrue(hahaMd.equals(new MarkdownFile(hahaPath, hahaCreation, hahaModified)));
    assertFalse(hahaMd.equals(mnmMd));
    assertTrue(mnmMd.equals(new MarkdownFile(mnmPath, mnmCreation, mnmModified)));
    assertFalse(mnmMd.equals(exMd));
    assertTrue(exMd.equals(new MarkdownFile(exPath, exCreation, exModified)));
    assertFalse(exMd.equals(new MarkdownFile(exPath, hahaCreation, exModified)));
    assertFalse(exMd.equals(new MarkdownFile(exPath, exCreation, hahaModified)));
    assertFalse(exMd.equals(new MarkdownFile(hahaPath, exCreation, exModified)));
    assertFalse(exMd.equals(exPath));
    assertFalse(exMd.equals(hahaMd));
  }

  /**
   * Test for the .hashCode() overriden method
   */
  @Test
  public void testHashCode() {
    assertEquals(-1771947661, hahaMd.hashCode());
    assertEquals(-1114758270, mnmMd.hashCode());
    assertEquals(-2077727246, exMd.hashCode());
  }
}