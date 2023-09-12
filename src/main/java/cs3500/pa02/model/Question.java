package cs3500.pa02.model;

/**
 * Represents a question with an answer and difficulty
 */
public class Question {
  private final String question;
  private final String answer;
  private Difficulty difficulty;

  /**
   * Constructor for questiom
   *
   * @param q string representing the question
   * @param a string representing the answer
   * @param d difficulty of the question
   */
  public Question(String q, String a, Difficulty d) {
    this.question = q;
    this.answer = a;
    this.difficulty = d;
  }

  /**
   * Changes the difficulty of the question
   *
   * @param d difficulty to set the question to
   */
  public void setDifficulty(Difficulty d) {
    this.difficulty = d;
  }

  /**
   * Returns the question from this Question
   *
   * @return the question
   */
  public String getQuestion() {
    return this.question;
  }

  /**
   * Returns the answer to this Question
   *
   * @return string of the answer to the question
   */
  public String getAnswer() {
    return this.answer;
  }

  /**
   * Represents the metadata of the Question as a string
   *
   * @return the Question object represented as a string
   */
  public String toString() {
    return "[[" + this.difficulty.toString() + ":::" + this.question + ":::" + this.answer + "]]";
  }

  /**
   * Is this question hard or easy
   *
   * @return the Difficulty of this Question
   */
  public Difficulty getDifficulty() {
    return difficulty;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param other an object to compare to this one
   * @return boolean whether or not it is equal to this Question
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Question q) {
      return this.question.equals(q.getQuestion()) && this.answer.equals(q.getAnswer())
          && this.difficulty == q.getDifficulty();
    } else {
      return false;
    }
  }

  /**
   * Returns a hash code value for the Question.
   *
   * @return Returns a hash code value for the question.
   */
  @Override
  public int hashCode() {
    return (question.hashCode() * 10000000) + (answer.hashCode() * 10)
        + difficulty.ordinal();
  }
}
