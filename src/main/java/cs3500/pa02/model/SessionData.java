package cs3500.pa02.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents the data of a study session
 */
public class SessionData implements Model {
  File source;
  QuestionBank qb;
  ArrayList<Question> sessionQuestions;
  Question current;
  int answered;
  int hardToEasy;
  int easyToHard;
  boolean questionHuh;

  /**
   * Constructor for sessiondata
   *
   * @param src  source file (.sr) to update after session
   * @param qb   questionbank
   * @param size how many questions to study this session
   */
  public SessionData(File src, QuestionBank qb, int size) {
    this.source = src;
    this.qb = qb;
    this.sessionQuestions = initQuestions(size);
    this.questionHuh = false;
    this.current = sessionQuestions.get(0);
    this.answered = 0;
    this.hardToEasy = 0;
    this.easyToHard = 0;
  }

  public SessionData(File src, int size) {
    this(src, SessionData.initQuestionbank(src), size);
  }

  /**
   * Creates the question bank based on the given .sr file
   *
   * @param source the location of the .sr file to create the Questionbank from
   * @return a questionbank
   */
  private static QuestionBank initQuestionbank(File source) {
    QuestionBank qb;
    try {
      qb = new SourceReader(source).read();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Provided .sr file does not exist.");
    }
    return qb;
  }

  /**
   * Returns a list of questions of desired size
   *
   * @param size the desired number of questions
   * @return a randomized list of questions of given size
   */
  private ArrayList<Question> initQuestions(int size) {
    return this.qb.getList(size);
  }

  public Question getCurrent() {
    return this.current;
  }

  /**
   * returns the next question
   *
   * @return the next question
   * @throws IndexOutOfBoundsException if there are no questions remaining
   */
  @Override
  public Question nextQuestion() throws IndexOutOfBoundsException {
    int index = sessionQuestions.indexOf(current) + 1;
    this.current = sessionQuestions.get(index);
    this.questionHuh = false;
    this.answered += 1;
    return this.getCurrent();
  }

  /**
   * mutates the state and returns the new state
   *
   * @return the new state (Q or A on display)
   */
  @Override
  public boolean switchState() {
    questionHuh = !questionHuh;
    return questionHuh;
  }

  /**
   * What is the number of the current question?
   *
   * @return the current question number
   */
  @Override
  public int questionNum() {
    return this.answered + 1;
  }

  /**
   * What are the statistics from this session
   *
   * @return an array holding the stats
   */
  @Override
  public int[] getStats() {
    int[] stats = new int[5];
    stats[0] = this.answered;
    stats[1] = this.hardToEasy;
    stats[2] = this.easyToHard;
    stats[3] = this.qb.getHard().size() - hardToEasy + easyToHard;
    stats[4] = this.qb.getEasy().size() + hardToEasy - easyToHard;
    return stats;
  }

  /**
   * Set the difficulty of the current question to the given
   *
   * @param d Difficulty to set the current question to
   */
  @Override
  public void setDifficulty(Difficulty d) {
    if (this.current.getDifficulty() == Difficulty.HARD && d == Difficulty.EASY) {
      hardToEasy += 1;
    } else if (this.current.getDifficulty() == Difficulty.EASY && d == Difficulty.HARD) {
      easyToHard += 1;
    }
    this.current.setDifficulty(d);
  }

  /**
   * Updates the source .sr file to reflect the changes from the session
   */
  @Override
  public void updateFile() {
    new SourceWriter(source, qb).write();
  }
}
