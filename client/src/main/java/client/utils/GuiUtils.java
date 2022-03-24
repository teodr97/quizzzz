package client.utils;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GuiUtils {
    // The class that contains the reaction. The actual reaction is encapsulated
    // into this class that also deals with timed display of the reaction (making it
    // disappear after approximately 3 seconds).
    public static class TimedReaction extends AnimationTimer {
        // Overall time since reaction was created. Gets incremented with time.
        float progress;
        ImageView reactionImg = new ImageView();
        Text usernameText = new Text();
        // The container panel
        AnchorPane panel = new AnchorPane();

        /**
         * Constructor method. It creates a new panel, text,
         * and image, and it sets the text and the image as
         * the children of the container panel.
         * @param username
         * @param reaction
         */
        public TimedReaction(String username, Image reaction) {
            this.usernameText.setText(username);
            this.reactionImg.setImage(reaction);
            usernameText.setStyle("-fx-font-size: 15");
            panel.getChildren().add(reactionImg);
            panel.getChildren().add(usernameText);
            usernameText.setY(30);
            reactionImg.setFitHeight(40);
            reactionImg.setFitWidth(40);
            reactionImg.setX(175);
            reactionImg.setY(5);
        }

        /**
         * Getter for the actual reaction graphics container.
         * @return the container of the username and reaction
         * image in the form of an AnchorPane.
         */
        public AnchorPane getAnchorPane() { return panel; }

        // This method deals with the time control and
        // is called every frame.
        // The progress variable gets incremented every frame.
        // When it becomes at least 3, it will make the
        // reaction "disappear" by making the username
        // text and the image empty.
        @Override
        public void handle(long now) {
            progress += .01;
            if (progress >= 3) {
                usernameText.setText("");
                reactionImg.setImage(null);
                // Stops the timer.
                stop();
            }
        }
    }

    /**
     * Creates a new instance of a reaction that should come from
     * a specific player associated with the given username to
     * be displayed on the game screen.
     * The reaction is timed, so that after approximately 3 seconds
     * it disappears from the game screen. The timer does not
     * start automatically, and should be started by calling the
     * method TimedReaction.start(). The actual reaction graphics
     * (the panel container, the text, and the image) can
     * be accessed by using TimedReaction.getAnchorPane().
     * @param username the username displayed next to the reaction
     * @param reaction the reaction image displayed
     * @return timed reaction that disappears 3 seconds after
     * starting the timer
     */
    public static TimedReaction createNewReaction(String username, Image reaction) {
        return new TimedReaction(username, reaction);
    }
}
