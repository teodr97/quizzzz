package client.scenes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.game.Activity;
import commons.models.FileInfo;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.tomcat.util.codec.binary.Base64;
import org.glassfish.jersey.client.ClientConfig;


import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


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

    @FXML
    private ImageView testImage;

    @FXML
    private Button testImg;

    private Stage stage;

    private File folder;

    /**
     * injects main controller
     * @param mainCtrl : main controller
     * @param serverSelectorCtrl : server selector controller
     */
    @Inject
    public Admin(MainCtrl mainCtrl, ServerSelectorCtrl serverSelectorCtrl) {
        this.mainCtrl = mainCtrl;
        this.serverSelectorCtrl = serverSelectorCtrl;
    }

    /**
     * initializes table
     * @param location : location
     * @param resources : resources
     */
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
     * @param event : button click
     * @throws IOException : if it fails to find screen to switch
     */
    public void switchToSplash(ActionEvent event) throws IOException{
        mainCtrl.switchToSplash();
    }

    /**
     * Opens file explorer, lets user choose a file
     * takes the file and stores it, changes name of button.
     */
    public void uploadFile(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File tempFile = directoryChooser.showDialog(stage);
        //if no file assigned just quit method
        if(tempFile==null){return;}
        //check if temp file is zip
        if(tempFile.isDirectory()){
            this.folder = tempFile;
            fileText.setText(folder.getName());
            return;
        }
        fileText.setText("Please provide a directory.");
    }

    /**
     * takes the zip file, takes the activities.json file, converts to a list of activities, and adds
     * each activity to the database
     * @throws IOException : if file not found
     */
    public void uploadToDatabase() throws IOException {
        List<Activity> activityList = null;

        //Gets the first entry of the zip file (assumed to be activities.json)
        for(File file : folder.listFiles()){
            System.out.println(file.getName());
            if (file.getName().contains("activities.json")){
                activityList = jsonToActivity(file);
            } else if(file.isDirectory()){

                //send the directory path so that it can create the directory first
                Response r = postFile(new FileInfo(file.getName(), null, true));
                for(File image : file.listFiles()){
                    //encode each file in the directory and send it to the server for copying
                    String encoded = encodeFileToBase64Binary(image.getPath());
                    String pathName = image.getParentFile().getName() + "\\" + image.getName();

                    //create a file info class to send path to store in and file
                    FileInfo f = new FileInfo(pathName, encoded, false);
                    postFile(f);
                }
            }
        }

        for (Activity activity : activityList) {

            boolean filter = activity.getSource().length() < 255 &&
                    activity.getTitle().length() < 100;
            if (filter) {
                postActivity(activity);
            }
        }

        fileText.setText("Importing Complete");
        tableActivity.refresh();
        this.folder = null;

    }

    /**
     * sends an encoded string of file to store
     * @param file : an instance of FileInfo containing information about the file
     * @return : a response from server
     */
    public Response postFile(FileInfo file) {
        return ClientBuilder.newClient(new ClientConfig())
                .target("http://localhost:8080/images/upload")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(file, APPLICATION_JSON));
    }

    /**
     * converts file to base64 to send
     * @param fileName : name of the file
     * @return : an encoded string of the file contents
     * @throws IOException : if file not found
     */
    private String encodeFileToBase64Binary(String fileName) throws IOException {
        File file = new File(fileName);
        //convert file to byte array
        byte[] bytes = Base64.encodeBase64(Files.readAllBytes(file.toPath()));
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    /**
     * helper method to deserialize json to a list of activities
     * @param file : the file to deserialize
     * @return : a list of activities
     * @throws IOException : if file not found
     */
    private List<Activity> jsonToActivity(File file) throws IOException {
        //converts input stream into activityList
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream is = new FileInputStream(file);

        return om.readValue(is, new TypeReference<ArrayList<Activity>>(){});
    }

    /**
     * posts an activity to the database
     * @param activity : instance of activity class
     * @return : response from server if post successful
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
                .target(serverSelectorCtrl.getServer()).path("/api/v1/activity/update")
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .post(Entity.entity(activity, APPLICATION_JSON));

        //change entry on table
        tableActivity.refresh();

        //check setText
        textConsole.setText("Activity data has been changed");
    }

    /**
     * refreshes the table
     */
    public void refreshTable(){
        fetchActivities();
        tableActivity.refresh();
        textConsole.setText("Table has been refreshed.");
    }

    /**
     * displays the image on the admin panel
     */
    public void testImage(){
        //get the activtiy from the table
        Activity a = tableActivity.getSelectionModel().getSelectedItem();
        String imagepath = a.getImage_path();

        //request its image path
        FileInfo fileInfo = ClientBuilder.newClient(new ClientConfig())
                .target(serverSelectorCtrl.getServer()).path("/images/get")
                .queryParam("image_path", imagepath )
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .get(new GenericType<>() {});

        //decode encoded string
        String s = fileInfo.getEncoding();
        InputStream in = new ByteArrayInputStream(Base64.decodeBase64(s));

        //set the test image
        testImage.setImage(new Image(in));
    }



}

