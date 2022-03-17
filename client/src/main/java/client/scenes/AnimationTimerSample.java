package client.scenes;

//import all the necessary packages
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//main class
public class AnimationTimerSample extends Application {
    //declare opacity and a label
    private double op = 1;
    private Label lbel;
    @Override
    public void start(Stage st) {
        UIinit(st);
    }
    //define the UIinit method
    private void UIinit(Stage st) {
//create a stackpane
        StackPane sp = new StackPane();
//define the label
        lbel = new Label("Animation Sample");
//set the font for label
        lbel.setFont(Font.font(24));
//add the label to stack pane
        sp.getChildren().add(lbel);
//declare an animation timer
        AnimationTimer tm = new TimerMethod();
//start the timer
        tm.start();
//create a scene
        Scene sc = new Scene(sp, 310, 260);
//set the title for stage
        st.setTitle("Animation Timer Sample");
//set the scene
        st.setScene(sc);
//display the result
        st.show();
    }
    private class TimerMethod extends AnimationTimer {
        //define the handle method
        @Override
        public void handle(long now) {
//call the method
            handlee();
        }
        //method handlee
        private void handlee() {
//set the value of opacity
            op -= 0.01;
//set the opacity for label
            lbel.opacityProperty().set(op);
//checks the value of opacity
            if (op <= 0)
            {
//if it is less than zero, stop it
                stop();
//print message
                System.out.println("Animation stops here");
            }
        }
    }
    //main method
    public static void main(String[] args) {
        launch(args); } }
