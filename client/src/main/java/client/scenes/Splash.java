package client.scenes;

import commons.Activity;
import jakarta.ws.rs.client.ClientBuilder;
import javafx.event.EventHandler;
import javafx.event.EventType;
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

import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


public class Splash {

    private Stage primaryStage;

    private Scene overview;

    public void initialize(Stage primaryStage, Color sceneColor) throws IOException {
        this.primaryStage = primaryStage;
        Group root = new Group();
        this.overview = new Scene(root, 1720, 920);
        primaryStage.setFullScreen(true);
        this.primaryStage.setTitle("Quizzz");
        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(1,27,55)), new Stop(1, Color.rgb(16,27,37))};
        LinearGradient linear = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);
        overview.setFill(linear);

        Image image = new Image(new File("./client/resources/images/background.jpg").getCanonicalPath());
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

        //javafx.scene.control.Button button = new javafx.scene.control.Button("Send REST request");
        //button.setPrefSize(100, 100);

        var client = ClientBuilder.newClient();
        client.target("http://localhost:8080/testmapping/post").request().post(null);
        var x = client.target("http://localhost:8080/testmapping/get").request().get(String.class);
        System.out.println(x);

        var activity = client.target("http://localhost:8080/activity/get").request().get(Activity.class);
        System.out.println(activity.toString());
        activity = client.target("http://localhost:8080/activity/get").request().get(Activity.class);
        System.out.println(activity.toString());

        root.getChildren().add(text);

        //root.getChildren().add(button);
        primaryStage.setScene(overview);
        primaryStage.show();

    }

}
