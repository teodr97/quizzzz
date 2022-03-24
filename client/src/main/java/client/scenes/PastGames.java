package client.scenes;

import client.utils.SingleplayerHighscoreHandler;
import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class PastGames implements Initializable {

    private MainCtrl mainCtrl;

    @FXML private TableView<LeaderboardEntry> leaderboard;

    @FXML private TableColumn<LeaderboardEntry, String> colNickname;
    @FXML private TableColumn<LeaderboardEntry, String> colDate;
    @FXML private TableColumn<LeaderboardEntry, String> colPoints;

    @FXML private Text bestPoints;
    @FXML private Text bestDate;
    @FXML private Text bestNickname;

    @Inject
    public PastGames(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        colNickname.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().username));
        colDate.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().date));
        colPoints.setCellValueFactory(q -> new SimpleStringProperty(Integer.toString(q.getValue().points)));

        loadEntries();
    }

    /**
     * Loads the entries for the past games leaderboard.
     */
    public void loadEntries() {

        try {
            var shh = SingleplayerHighscoreHandler.getHighscores();
            List<LeaderboardEntry> entryBase = new ArrayList<>();

            var entries = shh.getEntries();
            while (entries.hasNext()) {
                var entry = entries.next();
                entryBase.add(new LeaderboardEntry(entry.nickname, entry.date, entry.points));
            }

            leaderboard.setItems(FXCollections.observableList(entryBase));

            if (shh.getAllTimeBest() != null) {
                bestPoints.setText(shh.getAllTimeBest().points + " Points");
                bestDate.setText("Achieved " + shh.getAllTimeBest().date);
                bestNickname.setText("by " + shh.getAllTimeBest().nickname);
            } else {
                bestPoints.setText("");
                bestDate.setText("No Games Have Been Played");
                bestNickname.setText("");
            }

        } catch (FileNotFoundException | SingleplayerHighscoreHandler.IncorrectFileFormat e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches the current screen to the splash screen.
     * @param event Event used for the button click
     */
    public void switchToSplash(ActionEvent event) {
        mainCtrl.switchToSplash();
    }

    private class LeaderboardEntry {
        String username;
        String date;
        Integer points;

        LeaderboardEntry(String username, String date, Integer points) {
            this.username = username;
            this.date = date;
            this.points = points;
        }
    }
}
