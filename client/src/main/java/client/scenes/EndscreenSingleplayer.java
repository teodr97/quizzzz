package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.StatSharerSingleplayer;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.google.inject.Guice.createInjector;


public class EndscreenSingleplayer implements Initializable {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Used to share information between the game and the end-screen.
     * Here it is used to display the information.
     */
    private StatSharerSingleplayer statSharer;

    @FXML private Text finalScoreTextBox;

    @Inject
    public EndscreenSingleplayer(StatSharerSingleplayer statSharer) {
        this.statSharer = statSharer;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        int text = statSharer.points;
        //int text = (int)finalScoreTextBox.getScene().getUserData();
        this.finalScoreTextBox.setText(Integer.toString(text));
    }

    public void switchToSinglePlayer(ActionEvent event) throws IOException {
        var overview = FXML.load(SinglePlayer.class, "client", "scenes", "SinglePlayer.fxml");
        var stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        var scene = new Scene(overview.getValue());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSplash(ActionEvent event) throws IOException{
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");
        var stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        System.out.println(stage.getUserData());
        var scene = new Scene(overview.getValue());
        stage.show();
    }
}
