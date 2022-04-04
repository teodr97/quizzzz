package client.scenes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.game.Activity;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private ServerSelectorCtrl serverSelectorCtrl;

    @FXML
    private Button dbBtn;

    @FXML
    private Button fileBtn;

    @FXML
    private Label fileText;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField textTitle;

    @FXML
    private TextField textConsumption;

    @FXML
    private TextField textSource;

    @FXML
    private TextArea textConsole;

    @FXML
    private TableView<Activity> tableActivity;

    @FXML
    private TableColumn<Activity,Integer> colAutoId;

    @FXML
    private TableColumn<Activity,String> colTitle;

    @FXML
    private TableColumn<Activity,Long> colConsumption;

    @FXML
    private TableColumn<Activity,String> colImage;

    @FXML
    private TableColumn<Activity,String> colSource;

    private Stage stage;

    private File file;

    @Inject
    public Admin(MainCtrl mainCtrl, ServerSelectorCtrl serverSelectorCtrl) {
        this.mainCtrl = mainCtrl;
        this.serverSelectorCtrl = serverSelectorCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        colAutoId.setCellValueFactory(q -> new SimpleIntegerProperty(q.getValue().getAutoId()).asObject());
        colTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        colConsumption.setCellValueFactory(q -> new SimpleLongProperty(q.getValue().getConsumption_in_wh()).asObject());
        colSource.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getSource()));
        colImage.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getImage_path()));

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
    public Response postActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverSelectorCtrl.getServer()).path("/api/v1/activity/post")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activity, APPLICATION_JSON));
    }

    /**
     * fetches the entries via get request
     */
    public void fetchActivities() {
        //fetches all the activities
        List<Activity> activityList = ClientBuilder.newClient(new ClientConfig())
                .target(serverSelectorCtrl.getServer()).path("/api/v1/activity")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});


        tableActivity.setItems(FXCollections.observableList(activityList));
    }

    /**
     * updates the activity by taking from the text field
     */
    public void updateActivity(){
        //first needs to check if an activity is selected
        Activity activity = tableActivity.getSelectionModel().getSelectedItem();

        if(activity == null){
            textConsole.setText("Please select an activity from the table to edit");
            return;
        }
        //check each field, update activity object
        if(textTitle.getText() != null && !textTitle.getText().equals("")) {
            activity.setTitle(textTitle.getText());
        }

        if(textConsumption.getText() != null && !textConsumption.getText().equals("")){
            activity.setConsumption_in_wh(Long.parseLong(textConsumption.getText()));
        }

        if(textSource.getText() != null && !textSource.getText().equals("")){
            activity.setSource(textSource.getText());
        }

        //update request to server
        Response r = ClientBuilder.newClient(new ClientConfig())
                .target("http://localhost:8080/api/v1/activity/update")
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .post(Entity.entity(activity, APPLICATION_JSON));

        //change entry on table
        tableActivity.refresh();

        //check setText
        textConsole.setText("Activity data has been changed");
    }



}
