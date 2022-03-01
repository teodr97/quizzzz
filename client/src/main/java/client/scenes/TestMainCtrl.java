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

    //initializes the stage and gets the scene from Splash.fxml
    //Opens/Shows the stage.
    public void initialize(Stage primaryStage, Pair<Splash, Parent> overview) {
        this.primaryStage = primaryStage;
        this.scene = new Scene(overview.getValue());

        showPrimaryStage();
    }

    //Sets the title and scene for the Startingstage.
    public void showPrimaryStage() {
        primaryStage.setTitle("QUIZZ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Sets and shows the scene.
    public void setAndShowScenes(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //If the event is executed then the scene switches to Splash.fxml
    public void switchToSplash(ActionEvent event) throws IOException{
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //Switches to HowToPlay.fxml
    public void switchToHowToPlay(ActionEvent event) throws IOException{
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "HowToPlay.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //Switches to PastGames.fxml
    public void switchToPastGames(ActionEvent event) throws IOException{
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "PastGames.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

    //Switches to Username.fxml
    public void switchToUsername(ActionEvent event) throws IOException{
        var overview = FXML.load(HowToPlay.class, "client", "scenes", "Username.fxml");
        scene = new Scene(overview.getValue());
        setAndShowScenes(event);
    }

}
