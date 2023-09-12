package cs3500.pa02.view;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for MockAppendable class
 */
class MockAppendableTest {
  MockAppendable appendable;

  /**
   * Initializes the data for tests
   */
  @BeforeEach
  void initData() {
    appendable = new MockAppendable();
  }

  /**
   * Tests for the append method
   */
  @Test
  void append() {
    assertThrows(IOException.class, () -> appendable.append('c'));
    assertThrows(IOException.class, () -> appendable.append("String"));
    assertThrows(IOException.class, () -> appendable.append("String", 1, 2));
  }
}