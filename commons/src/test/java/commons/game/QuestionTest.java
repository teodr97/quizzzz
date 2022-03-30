package commons.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question question;
    private List<Activity> activityList;

    /**
     * Creates an activity list for tests
     */
    @BeforeEach
    void initTest() {
        activityList = new LinkedList<>();
        activityList.add(new Activity("Path_1","Activity_1", 1, "Source_1"));
        activityList.add(new Activity("Path_2", "Activity_2", 2, "Source_2"));
        activityList.add(new Activity("Path_3", "Activity_3", 3, "Source_3"));
        question = new Question((LinkedList<Activity>) activityList);
    }

    /**
     * tests correct answer
     */
    @Test
    void hasCorrectAnswer() {
        question = new Question((LinkedList<Activity>) activityList);

        assertTrue(Question.hasCorrectAnswer(question, question.getCorrectAnswerIndex()));
    }

    /**
     * tests question setter
     */
    @Test
    void setQuestion() {
        question.setQuestion("Test");
        assertEquals("Test", question.getQuestion());
    }

    /**
     * tests question getter
     */
    @Test
    void getQuestion() {
        assertNotEquals("<Question>", question.getQuestion());
    }

    /**
     * tests activitylist size
     */
    @Test
    void getActivityList() {
        assertEquals(3, question.getActivityList().size());
    }

    /**
     * test getting correct answer
     */
    @Test
    void getCorrectAnswer() {
        String correctActivity = question.getCorrectAnswer().getTitle();
        List<String> activityStringList = new LinkedList<>();

        for (Activity act : activityList) activityStringList.add(act.getTitle());
        assertTrue(activityStringList.contains(correctActivity));
    }

    /**
     *
     */
    @Test
    void testToString() {
        String correct = "{\nQuestion: " + question.getQuestion();
        correct += "\nOption: " + this.activityList.get(0).toString();
        correct += "\nOption: " + this.activityList.get(1).toString();
        correct += "\nOption: " + this.activityList.get(2).toString();
        correct += "\nAnswer: " + this.question.getCorrectAnswer().toString();
        correct += "\n}";

        assertEquals(correct, question.toString());
    }

    /**
     * Test equals method
     */
    @Test
    void testEquals() {
        Question question1 = new Question((LinkedList<Activity>) activityList);
        Question question2 = new Question((LinkedList<Activity>) activityList);
        Activity activity1 = new Activity("Path", "Activity",1, "Source" );
        Activity activity2 = new Activity("Path", "Activity",1, "Source" );

        question2.setQuestion(question1.getQuestion());
        question2.setCorrectAnswer(activity1);
        question1.setCorrectAnswer(activity2);
        assertEquals(question1, question2);
    }
}