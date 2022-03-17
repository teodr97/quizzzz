package client.scenes;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class Username implements Initializable {

    private final MainCtrl mainCtrl;

    @FXML
    private Text errorMsg;

    @Inject
    public Username(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    //no real functionality yet
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    public void onMouseUsernameBoxClick(MouseEvent event) {
        errorMsg.setVisible(false);
    }

    public void switchToSplash(ActionEvent event) {
        this.mainCtrl.switchToSplash();
    }
}
