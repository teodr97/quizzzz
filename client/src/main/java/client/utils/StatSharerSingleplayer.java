package client.utils;

import org.jvnet.hk2.annotations.Service;

@Service
public class StatSharerSingleplayer {
    public int points = 0;
    public int correctAnswers = 0;
    public int totalAnswers = 0;

    public void reset() {
        this.points = this.correctAnswers = this.totalAnswers = 0;
    }
}
