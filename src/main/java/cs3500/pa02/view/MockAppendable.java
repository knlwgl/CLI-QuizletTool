package cs3500.pa02.view;

import java.io.IOException;

/**
 * Mock appendable to throw IO exception (for testing)
 */
public class MockAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throwInOut();
    return null;
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throwInOut();
    return null;
  }

  @Override
  public Appendable append(char c) throws IOException {
    throwInOut();
    return null;
  }

  private void throwInOut() throws IOException {
    throw new IOException("Mock throwing an error");
  }
}
