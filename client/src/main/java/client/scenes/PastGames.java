package client.scenes;

import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;


public class PastGames implements Initializable {

    private MainCtrl mainCtrl;

    @FXML private TableView<LeaderboardEntry> leaderboard;

    @FXML private TableColumn<LeaderboardEntry, String> colDate;
    @FXML private TableColumn<LeaderboardEntry, String> colPoints;

    @Inject
    public PastGames(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){
        colDate.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().date));
        colPoints.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().points));

        loadEntries();
    }

    /**
     * Loads the entries for the past games leaderboard.
     */
    public void loadEntries() {
        File file = new File("client/data/pastgames.data");
        List<LeaderboardEntry> entryBase = new ArrayList<>();
        System.out.println(file.getAbsolutePath());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] args = scanner.nextLine().split(";");
                entryBase.add(new LeaderboardEntry(args[0], args[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<LeaderboardEntry> entries = FXCollections.observableList(entryBase);
        leaderboard.setItems(entries);
    }

    /**
     * Switches the current screen to the splash screen.
     * @param event
     */
    public void switchToSplash(ActionEvent event) {
        mainCtrl.switchToSplash();
    }

    private class LeaderboardEntry {
        String date, points;

        public LeaderboardEntry(String date, String points) {
            this.date = date;
            this.points = points;
        }
    }
}
