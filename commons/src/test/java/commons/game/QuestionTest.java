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
     *
     */
    @BeforeEach
    void initTest() {
        activityList = new LinkedList<>();
        activityList.add(new Activity("Activity_1", 1));
        activityList.add(new Activity("Activity_2", 2));
        activityList.add(new Activity("Activity_3", 3));
        question = new Question(activityList);
    }

    /**
     *
     */
    @Test
    void hasCorrectAnswer() {
        question = new Question(activityList);

        assertTrue(Question.hasCorrectAnswer(question, question.getCorrectAnswer()));
    }

    /**
     *
     */
    @Test
    void setQuestion() {
        question.setQuestion("Test");
        assertEquals("Test", question.getQuestion());
    }

    /**
     *
     */
    @Test
    void getQuestion() {
        assertNotEquals("<Question>", question.getQuestion());
    }

    /**
     *
     */
    @Test
    void getActivityList() {
        assertEquals(3, question.getActivityList().size());
    }

    /**
     *
     */
    @Test
    void getCorrectAnswer() {
        String correctActivity = question.getCorrectAnswer().getActivity();
        List<String> activityStringList = new LinkedList<>();

        for (Activity act : activityList) activityStringList.add(act.getActivity());
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
     *
     */
    @Test
    void testEquals() {
        Question question1 = new Question(activityList);
        Question question2 = new Question(activityList);
        Activity activity1 = new Activity("Activity",1 );
        Activity activity2 = new Activity("Activity",1 );

        question2.setQuestion(question1.getQuestion());
        question2.setCorrectAnswer(activity1);
        question1.setCorrectAnswer(activity2);
        assertEquals(question1, question2);
    }
}