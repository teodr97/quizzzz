package client.utils;

import org.jvnet.hk2.annotations.Service;

@Service
public class StatSharerSingleplayer {
    public int points = 0;
    public int correctAnswers = 0;
    public int totalQuestions = 0;

    public void reset() {
        this.points = 0;
        this.correctAnswers = 0;
        this.totalQuestions = 0;
    }
}
