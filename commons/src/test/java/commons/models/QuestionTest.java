package commons.models;




import commons.game.Activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


class QuestionTest {
    private Question question;
    private ArrayList<Activity> activityList;


    /**
     * The method that initializes each test.
     */
    @BeforeEach
    private void initialiseTests() {
        activityList = new ArrayList<>();
        activityList.add(new Activity("Path_1","Activity_1", 1, "Source_1"));
        activityList.add(new Activity("Path_2", "Activity_2", 2, "Source_2"));
        activityList.add(new Activity("Path_3", "Activity_3", 3, "Source_3"));
        question = new Question(activityList);
    }

    /**
     * tests correct answer
     */
    @Test
    void hasCorrectAnswer() {
        question = new Question(activityList);

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
     *
     */
    @Test
    void testToString() {
        String correct = "server" + "(QUESTION): " + question.getQuestion();

        assertEquals(correct, question.toString());
    }

    /**
     * Test equals method
     */
    @Test
    void testEquals() {
        Question question1 = new Question(activityList);
        Question question2 = new Question(activityList);
        Activity activity1 = new Activity("Path", "Activity",1, "Source" );
        Activity activity2 = new Activity("Path", "Activity",1, "Source" );

        //question2.setCorrectAnswer(activity1);
        question1 = question2;
        assertEquals(question1, question2);
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
}