package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Driver class
 */
class DriverTest {
  String expected;
  Path testPath;
  String expected2;
  Path testPath2;

  /**
   * Initializes data for tests
   */
  @BeforeEach
  public void initData() {
    testPath = Path.of("sampleOutputs/test.md");
    testPath2 = Path.of("sampleOutputs/test2.md");
    expected = """
        # Java Arrays
        - An **array** is a collection of variables of the same type

        ## Declaring an Array
        - General Form: type[] arrayName;
        - only creates a reference
        - no array has actually been created yet

        ## Creating an Array (Instantiation)
        - General form:  arrayName = new type[numberOfElements];
        - numberOfElements must be a positive Integer.
        - Gotcha: Array size is not modifiable once instantiated.

        # Vectors
        - Vectors act like resizable arrays

        ## Declaring a vector
        - General Form: Vector<type> v = new Vector();
        - type needs to be a valid reference type

        ## Adding an element to a vector
        - v.add(object of type);
        """;
    expected2 = """
        # Java Arrays
        - An **array** is a collection of variables of the same type

        ## Declaring an Array
        - General Form: type[] arrayName;
        - only creates a reference
        - no array has actually been created yet

        ## Creating an Array (Instantiation)
        - General form:  arrayName = new type[numberOfElements];
        - numberOfElements must be a positive Integer.
        - Gotcha: Array size is not modifiable once instantiated.

        # HAHA
        - I told a good joke yesterday!

        ## Joke
        - Ducks and how weird they can be

        # M&Ms are a delicious chocolate treat!
        - do you prefer skittles or mnms?
        - I suppose its related to candy vs chocolate
        
        # I like Trivia
        - Trivia question are very cool and fun
        
        ## Geography
        
        # More Geography!
        - The cars theme song is a banger, trust me!
        
        # Vectors
        - Vectors act like resizable arrays

        ## Declaring a vector
        - General Form: Vector<type> v = new Vector();
        - type needs to be a valid reference type

        ## Adding an element to a vector
        - v.add(object of type);

        # X is cool
        - It is a nice letter

        ## Words with X
        - Xylophone, X-ray, hmm
        """;
  }

  /**
   * Deletes the files created by the tests.
   */
  @AfterEach
  public void deleteTestFiles() {
    try {
      Files.deleteIfExists(testPath);
      Files.deleteIfExists(testPath2);
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * Test for the main method
   */
  @Test
  public void testMain() {
    assertFalse(Files.exists(testPath));
    Driver.main(new String[] {"sampleInputs/pa01", "filename", "sampleOutputs/test"});
    assertTrue(Files.exists(testPath));
    try {
      assertEquals(expected, Files.readString(testPath));
    } catch (Exception e) {
      fail();
    }
    assertFalse(Files.exists(testPath2));
    Driver.main(new String[] {"sampleInputs", "filename", "sampleOutputs/test2"});
    assertTrue(Files.exists(testPath2));
    try {
      assertEquals(expected2, Files.readString(testPath2));
    } catch (Exception e) {
      fail();
    }
    assertThrows(IllegalArgumentException.class, () ->
        Driver.main(new String[] {"sampleInputs/pa01", "birthday", "sampleOutputs/test"}));
    assertThrows(IllegalArgumentException.class, () ->
        Driver.main(new String[] {"sampleInputs/pa01", "created", "sampleOutputs/test/wild"}));
    assertThrows(IllegalArgumentException.class, () ->
        Driver.main(new String[] {"sampleInputs/pa01/nonexistent", "filename", "sampleOutputs"}));
    assertThrows(IllegalArgumentException.class, () ->
        Driver.main(new String[] {"modified", "sampleOutputs/test"}));
  }
}
