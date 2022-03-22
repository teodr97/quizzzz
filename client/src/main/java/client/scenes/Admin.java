package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.game.Activity;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.glassfish.jersey.client.ClientConfig;


import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Enumeration;
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

    private Stage stage;

    private File file;

    @Inject
    public Admin(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){}


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
    public void JSONtoDatabase() throws IOException {
        List<Activity> activityList;

        //Gets the first entry of the zip file (assumed to be activities.json)
        ZipFile zf = new ZipFile(this.file);
        ZipEntry ze = zf.getEntry("activities.json");

        //converts input stream into activityList
//        ObjectMapper om = new ObjectMapper();
//        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        activityList = om.readValue(zf.getInputStream(ze), new TypeReference<ArrayList<Activity>>(){});


        Activity a = new Activity("Test Activity", 50);
        System.out.println(a);
        ServerUtils.postActivity(a);


        fileText.setText("Importing Complete");

    }
}
