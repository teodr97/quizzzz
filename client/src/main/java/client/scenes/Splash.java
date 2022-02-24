package client.scenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;


public class Splash {

    private Stage primaryStage;

    private Scene overview;

    public void initialize(Stage primaryStage, Color sceneColor){
        this.primaryStage = primaryStage;
        Group root = new Group();
        this.overview = new Scene(root, 1720, 920);
        primaryStage.setFullScreen(true);
        this.primaryStage.setTitle("Quizz");
        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(1,27,55)), new Stop(1, Color.rgb(16,27,37))};
        LinearGradient linear = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);
        overview.setFill(linear);

        Image image = new Image("D:\\Program Files (x86)\\Java projects\\repository-template\\client\\src\\337afabe49c9ad1e0dcf554fb3b642ff.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1980);
        imageView.setY(220);
        root.getChildren().add(imageView);

        Text text = new Text();
        text.setX(80);
        text.setY(150);
        text.setFill(Color.rgb(253,236,250));
        text.setText("QUIZZ");
        text.setFont(Font.font("Monomaniac One", FontWeight.BOLD, FontPosture.REGULAR, 96));

        root.getChildren().add(text);
        primaryStage.setScene(overview);
        primaryStage.show();

    }

}
