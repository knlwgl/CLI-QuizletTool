package cs3500.pa02.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a question bank
 */
public class QuestionBank {
  private final ArrayList<Question> hardQuestions;
  private final ArrayList<Question> easyQuestions;
  private final Random rng;

  /**
   * Constructor for testing questionbank randomness
   *
   * @param hard list of hard questions
   * @param easy lis to easy questions
   * @param r random used for random list
   */
  public QuestionBank(ArrayList<Question> hard, ArrayList<Question> easy, Random r) {
    this.hardQuestions = hard;
    this.easyQuestions = easy;
    this.rng = r;
  }

  public QuestionBank(ArrayList<Question> hard, ArrayList<Question> easy) {
    this(hard, easy, new Random());
  }

  /**
   * Returns all the hard questions
   *
   * @return the list of hard questions
   */
  public ArrayList<Question> getHard() {
    return this.hardQuestions;
  }

  /**
   * Returns all the easy questions
   *
   * @return the list of easy questions
   */
  public ArrayList<Question> getEasy() {
    return this.easyQuestions;
  }

  /**
   * Returns a list of questions of the desired size; choosing from hard first and easy later
   *
   * @return the list of random questions
   */
  public ArrayList<Question> getList(int size) {
    ArrayList<Question> hardTemp = new ArrayList<>(this.hardQuestions);
    ArrayList<Question> easyTemp = new ArrayList<>(this.easyQuestions);

    ArrayList<Question> questions = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      int index;
      if (!hardTemp.isEmpty()) {
        index = rng.nextInt(hardTemp.size());
        Question curr = hardTemp.remove(index);
        questions.add(curr);
      } else if (!easyTemp.isEmpty()) {
        index = rng.nextInt(easyTemp.size());
        Question curr = easyTemp.remove(index);
        questions.add(curr);
      } else {
        break;
      }
    }
    return questions;
  }
}
