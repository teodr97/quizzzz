package client.scenes;

import com.google.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.glassfish.jersey.client.ClientConfig;

import java.net.URL;
import java.util.ResourceBundle;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerSelectorCtrl implements Initializable {

    private MainCtrl mainCtrl;

    @FXML private Text curServerMsg;
    @FXML private Text feedbackMsg;
    @FXML private TextField newServerField;
    @FXML private Button updateButton;

    /**
     * The server selected by the player.
     * The default value is "http://localhost:8080/"
     * Everywhere where the server uri is required this controller should be used.
     */
    private String server = "http://localhost:8080/";

    @Inject
    public ServerSelectorCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.updateServerMsg();
        this.feedbackMsg.setText("");
        this.newServerField.setText("");
    }

    /**
     * Updates the displayed text of the curServerMsg text field.
     */
    private void updateServerMsg() {
        this.curServerMsg.setText("Current Server: " + this.server);
    }

    /**
     * Updates the feedback message to a new one.
     * @param msg The message to be displayed to the user.
     * @param color The color of the message. Any value that can be passed to css is
     *              accepted, such as a hex code or the name of the color, such as
     *              red or green.
     */
    private void updateFeedbackMsg(String msg, String color) {
        this.feedbackMsg.setText(msg);
        this.feedbackMsg.setStyle("-fx-fill: " + color + ";");
    }

    /**
     * Switches to the splash screen.
     * @param event Mouse Click event
     */
    public void switchToSplash(ActionEvent event) {
        mainCtrl.switchToSplash();
    }

    /**
     * Updates the server the client talks to with a new one. This server is
     * specified in the newServerField textbox by the user.
     * @param event Mouse Click event
     */
    public void updateServer(ActionEvent event) {
        String newServer = this.newServerField.getText();
        this.updateFeedbackMsg("", "white");
        // check for an empty input
        if (newServer == null || newServer.isEmpty()) {
            this.updateFeedbackMsg("No Server Specified", "red");
            return;
        }

        //disable the button while trying to connect
        this.updateButton.setDisable(true);
        this.updateFeedbackMsg("Connecting...", "yellow");

        // check for a valid server
        String received;
        try {
            received = ClientBuilder.newClient(new ClientConfig()) //
                    .target(newServer).path("/api/testing/ping") //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get(new GenericType<String>() {});
        } catch (Exception e) {
            this.updateFeedbackMsg("Couldn't Connect To This Server", "red");
            this.updateButton.setDisable(false);
            return;
        }

        if (!received.equals("pong")) {
            System.out.println(received);
            this.updateFeedbackMsg("Wrong Response Received", "yellow");
            this.updateButton.setDisable(false);
            return;
        }

        this.updateButton.setDisable(false);
        this.server = newServer;
        this.updateFeedbackMsg("Connected Successfully!", "green");
        this.updateServerMsg();
    }

    /**
     * Gets the server currently selected by the user.
     * @return The current server.
     */
    public String getServer() {
        return this.server;
    }
}
