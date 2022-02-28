package client.scenes;

import client.MyFXML;
import client.MyModule;
import com.google.inject.Injector;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import javafx.event.ActionEvent;
import java.io.IOException;

import static com.google.inject.Guice.createInjector;

public class TestMainCtrl {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    private Stage primaryStage;
    private Stage stage;
    private Scene scene;
    private Parent parent;

    //initializes the stage and gets the scene from Splash.fxml
    //Opens/Shows the stage.
    public void initialize(Stage primaryStage, Pair<Splash, Parent> overview) {
        this.primaryStage = primaryStage;
        this.scene = new Scene(overview.getValue());

        showOverview();
        primaryStage.show();
    }

    //Sets the title and scene for the stage.
    public void showOverview() {
        primaryStage.setTitle("QUIZZ");
        primaryStage.setScene(scene);
    }

    public void switchToSplash(ActionEvent event) throws IOException{
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(overview.getValue());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHowToPlay(ActionEvent event) throws IOException{
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "HowToPlay.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(overview.getValue());
        stage.setScene(scene);
        stage.show();
    }

}
