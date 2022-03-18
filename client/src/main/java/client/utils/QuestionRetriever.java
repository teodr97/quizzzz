package client.utils;

import client.scenes.MainCtrl;
import com.google.inject.Inject;
import commons.game.Activity;
import commons.models.Game;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class QuestionRetriever implements Game.QuestionGenerator {

    private MainCtrl mainCtrl;

    @Inject
    public QuestionRetriever(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public List<Activity> retrieveActivitySetFromServer() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(mainCtrl.SERVER).path("/api/v1/activity/get/randomSetActivities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }
}
