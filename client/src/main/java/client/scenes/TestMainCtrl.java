package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class TestMainCtrl {

    private Stage primaryStage;

    private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    //initializes the stage and gets the scene from Splash.fxml
    //Opens/Shows the stage.
    public void initialize(Stage primaryStage, Pair<Splash, Parent> overview) {
        this.primaryStage = primaryStage;
        this.overview = new Scene(overview.getValue());

        showOverview();
        primaryStage.show();
    }

    //Sets the title and scene for the stage.
    public void showOverview() {
        primaryStage.setTitle("QUIZZ");
        primaryStage.setScene(overview);
    }
}
