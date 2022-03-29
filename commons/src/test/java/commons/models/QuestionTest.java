package commons.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    Question question;
    ArrayList<String> fakeAnswers;


    /**
     * The method that initializes each test.
     */
    @BeforeEach
    private void initialiseTests() {
        fakeAnswers = new ArrayList<>();
        fakeAnswers.add("fake1");
        fakeAnswers.add("fake2");
        question = new Question("Test", "Answer", fakeAnswers);
    }


    /**
     * Test for thew get question methode
     */
    @Test
    void testgetQuestion() {
        assertEquals("Test", question.getQuestion());
    }

    /**
     * Test for thew get Answers methode
     */
    @Test
    void testgetAnswer() {
        assertEquals("Answer", question.getAnswer());
    }

    /**
     * Test for thew get FakeAnswers methode
     */
    @Test
    void testgetFakeAnswers() {
        assertEquals(fakeAnswers, question.getQuestion());
    }


    /**
     * Test for thew get setQuestion methode
     */
    @Test
    void testsetQuestion() {
        question.setQuestion("Test2");
        assertEquals("Test2", question.getQuestion());
    }

    /**
     * Test for thew get setAnswers methode
     */
    @Test
    void testsetAnswer() {
        question.setAnswer("Answer2");
        assertEquals("Answer2", question.getAnswer());

    }

    /**
     * Test for thew get setFakeAnswers methode
     */
    @Test
    void testsetFakeAnswers() {
        ArrayList<String> fakeAnswers2 = new ArrayList<>();
        fakeAnswers2.add("fake3");
        fakeAnswers2.add("fake4");
        question.setFakeAnswers(fakeAnswers2);
        assertEquals(fakeAnswers2, question.getFakeAnswers());


    }

    /**
     * Test for the equals methode
     */
    @Test
    void testEquals() {
        Question q2 = new Question("Test", "Answer", fakeAnswers);
        assertTrue(question.equals(q2));
    }


    /**
     * Test for the to string methode 
     */
    @Test
    void testToString() {
        String s = "Question{" +
                "question='" + "Test" + '\'' +
                ", Answer='" + "Answer" + '\'' +
                ", fakeAnswers=" + fakeAnswers.toString() +
                '}';
        assertEquals(s, question.toString());
    }
}