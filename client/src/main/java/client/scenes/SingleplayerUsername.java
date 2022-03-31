package client.scenes;

import commons.models.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


import javax.inject.Inject;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;



public class SingleplayerUsername implements Initializable {

    private MainCtrl mainCtrl;
    private Player player;

    @FXML
    private TextField username;

    @FXML
    private Text missingUser;

    @FXML
    private ImageView slap;

    @Inject
    public SingleplayerUsername(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public SingleplayerUsername() {

    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    /**
     * switches to the splash screen, for the leave button
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException {
        mainCtrl.switchToSplash();
    }

    /**
     * switches to singleplayer game screen and starts the game
     * @param event
     * @throws IOException
     */
    public void startGame(ActionEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(username.getText() == null || username.getText().equals("")){
            missingUser.setText("please enter a username");
        } else if(username.getText().toLowerCase(Locale.ROOT).equals("slap")){
            Path path = Paths.get("src", "main","resources","images","slap.jpg");
            slap.setImage(new Image(path.toUri().toString()));
            Path path2 = Paths.get("src", "main","resources","audio","slapedited.wav");
            System.out.println(path2);
            File audioFile = new File(path2.toAbsolutePath().toString()).getAbsoluteFile();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.start();
        }
        else {
            player = new Player(username.getText());
            mainCtrl.switchToSinglePlayer();
        }
    }

    /**
     * Returns the username of the player.
     * @return
     */
    public String getUsername() {
        return player.getNickname();
    }
}
