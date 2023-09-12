package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Summarizer class
 */
public class SummarizerTest {
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
  Summarizer summarizer1;
  Path outPath;
  Summarizer summarizer2;
  Path path404; // i.e. file does not exist
  MarkdownFile md404; // i.e. nonexistent markdown file
  Path output404;
  String expected;
  Path outPath2;
  Summarizer summarizer3;


  /**
   * Initializes data for tests.
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
    summarizer1 = new Summarizer(new ArrayList<>(Arrays.asList(hahaMd, mnmMd, exMd)));
    outPath = Path.of("sampleOutputs/important.md");
    path404 = Path.of("sampleInputs/pa01/nonexistent.md");
    md404 = new MarkdownFile(path404, exCreation, mnmModified);
    output404 = Path.of("sampleOutputs/pa02/zelda");
    summarizer2 = new Summarizer(new ArrayList<>(Arrays.asList(hahaMd, md404, exMd)));
    outPath2 = Path.of("sampleOutputs/testing345.md");
    summarizer3 = new Summarizer(new ArrayList<>(Arrays.asList(hahaMd, mnmMd, exMd)));
    expected = """
        # HAHA
        - I told a good joke yesterday!

        ## Joke
        - Ducks and how weird they can be

        # M&Ms are a delicious chocolate treat!
        - do you prefer skittles or mnms?
        - I suppose its related to candy vs chocolate

        # X is cool
        - It is a nice letter

        ## Words with X
        - Xylophone, X-ray, hmm
        """;
  }

  /**
   * Deletes files created during tests.
   */
  @AfterEach
  public void deleteTestFiles() {
    try {
      Files.deleteIfExists(outPath);
      Files.write(outPath2, new byte[]{});
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Tests for summarize() method.
   */
  @Test
  public void testSummarize() {
    // case where file is created
    assertFalse(Files.exists(outPath));
    summarizer1.summarize(Path.of("sampleOutputs/important"));
    assertTrue(Files.exists(outPath));
    try {
      assertEquals(expected, Files.readString(outPath));
    } catch (IOException e) {
      fail();
    }
    // case where file is overwritten
    assertTrue(Files.exists(outPath2));
    try {
      assertNotEquals(expected, Files.readString(outPath2));
    } catch (IOException e) {
      fail();
    }
    summarizer3.summarize(Path.of("sampleOutputs/testing345"));
    try {
      assertEquals(expected, Files.readString(outPath2));
    } catch (IOException e) {
      fail();
    }
    assertThrows(RuntimeException.class, () -> summarizer2.summarize(outPath));
    assertThrows(RuntimeException.class, () -> summarizer1.summarize(output404));
  }
}
