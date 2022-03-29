package client.scenes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.game.Activity;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.glassfish.jersey.client.ClientConfig;


import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class Admin implements Initializable {

    private MainCtrl mainCtrl;

    @FXML
    private Button dbBtn;

    @FXML
    private Button fileBtn;

    @FXML
    private Label fileText;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableView<ActivityEntry> tableActivity;

    @FXML
    private TableColumn<ActivityEntry,String> colAutoId;

    @FXML
    private TableColumn<ActivityEntry,String> colTitle;

    @FXML
    private TableColumn<ActivityEntry,String> colConsumption;

    @FXML
    private TableColumn<ActivityEntry,String> colImage;

    @FXML
    private TableColumn<ActivityEntry,String> colSource;

    private Stage stage;

    private File file;

    @Inject
    public Admin(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        colAutoId.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().autoId));
        colTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().title));
        colConsumption.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().consumption_in_wh));
        colSource.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().source));
        colImage.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().image_path));

        fetchActivities();
    }


    /**
     * Switches the scene to Splash.
     * @param event
     * @throws IOException
     */
    public void switchToSplash(ActionEvent event) throws IOException{
        mainCtrl.switchToSplash();
    }

    /**
     * Opens file explorer, lets user choose a file
     * takes the file and stores it, changes name of button.
     */
    public void uploadFile(){
        FileChooser fileChooser = new FileChooser();
        File tempFile = fileChooser.showOpenDialog(stage);
        //if no file assigned just quit method
        if(tempFile==null){return;}
        //check if temp file is zip
        if(isExtension(tempFile.getName(), "zip")){
            this.file = tempFile;
            fileText.setText(file.getName());
            return;
        }
        fileText.setText("Please provide a zip file.");
    }

    /**
     * helper method that checks if given file is of certain extension
     * @param fileName: a string with the name of the file
     * @param extension: the string of the extension you want to check for e.g "zip"
     * @return
     */
    private Boolean isExtension(String fileName, String extension){
        String[] nameSplit = fileName.split("\\.");
        if(nameSplit[1].equals(extension)){
            return true;
        }
        return false;
    }

    /**
     * takes the zip file, takes the activities.json file, converts to a list of activities, and adds
     * each activity to the database
     * @throws IOException
     */
    public void uploadToDatabase() throws IOException {

        //Gets the first entry of the zip file (assumed to be activities.json)
        ZipFile zf = new ZipFile(this.file);
        ZipEntry ze = zf.getEntry("activities.json");

        List<Activity> activityList = jsonToActivity(zf,ze);

        for (Activity activity : activityList) {

            boolean filter = activity.getSource().length() < 255 &&
                    activity.getTitle().length() < 100;
            if (filter) {
                postActivity(activity);
            }
        }

        fileText.setText("Importing Complete");
        this.file = null;

    }

    /**
     * helper method to convert json to activityList
     */
    private List<Activity> jsonToActivity(ZipFile zf, ZipEntry ze) throws IOException {
        //converts input stream into activityList
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return om.readValue(zf.getInputStream(ze), new TypeReference<ArrayList<Activity>>(){});
    }

    /**
     * posts an activity to the database
     * @param activity
     * @return
     */
    public static Response postActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig())
                .target("http://localhost:8080").path("/api/v1/activity/post")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activity, APPLICATION_JSON));
    }

    /**
     * fetches the entries via get request
     */
    public void fetchActivities() {
        List<Activity> activityList = ClientBuilder.newClient(new ClientConfig())
                .target("http://localhost:8080").path("/api/v1/activity")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});

        List<ActivityEntry> entries = new ArrayList<>();
        for(Activity activity : activityList){
            entries.add(new ActivityEntry(Integer.toString(activity.getAutoId()),
                    activity.getTitle(),
                    activity.getSource(),
                    activity.getImage_path(),
                    Long.toString(activity.getConsumption_in_wh())));
        }

        tableActivity.setItems(FXCollections.observableList(entries));

    }

    /**
     * class for entries
     */
    private static class ActivityEntry{
        String autoId;
        String title;
        String source;
        String image_path;
        String consumption_in_wh;

        ActivityEntry(String autoId, String title, String source, String image_path, String consumption_in_wh){
            this.autoId = autoId;
            this.title = title;
            this.source = source;
            this.image_path = image_path;
            this.consumption_in_wh = consumption_in_wh;
        }
    }
}