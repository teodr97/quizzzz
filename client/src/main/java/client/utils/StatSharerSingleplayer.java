package client.utils;

import org.jvnet.hk2.annotations.Service;

@Service
public class StatSharerSingleplayer {
    public int points = 0;
    public int correctAnswers = 0;
    public int totalAnswers = 0;

    /**
     * Resets the Stat Sharer between the singleplayer screen and the singleplayer endscreen.
     */
    public void reset() {
        this.points = 0;
        this.correctAnswers = 0;
        this.totalAnswers = 0;
    }
}
