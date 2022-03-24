package client.utils;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GuiUtils {
    public static class TimedReaction extends AnimationTimer {
        // Overall time since reaction was created. Gets incremented with time.
        float progress;
        ImageView reactionImg = new ImageView();
        Text usernameText = new Text();
        AnchorPane panel = new AnchorPane();

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

        public AnchorPane getAnchorPane() { return panel; }

        //define the handle method
        @Override
        public void handle(long now) {
            //call the method
            handlee();
        }
        //method handlee
        private void handlee(){
            // making this smaller will slow down the times
            progress += .01;
            if (progress >= 3) {
                usernameText.setText("");
                reactionImg.setImage(null);
                stop();
            }
        }
    }

    public static TimedReaction createNewReaction(String username, Image reaction) {
        return new TimedReaction(username, reaction);
    }
}
