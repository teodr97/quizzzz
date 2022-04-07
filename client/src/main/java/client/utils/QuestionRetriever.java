package client.utils;

import client.scenes.ServerSelectorCtrl;
import com.google.inject.Inject;
import commons.game.Activity;
import commons.models.Game;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class QuestionRetriever implements Game.QuestionGenerator {

    private ServerSelectorCtrl serverSelector;

    @Inject
    public QuestionRetriever(ServerSelectorCtrl serverSelector) {
        this.serverSelector = serverSelector;
    }

    @Override
    public List<Activity> retrieveActivitySetFromServer() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverSelector.getServer()).path("/api/v1/activity/get/randomSetActivities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }

    @Override
    public ArrayList<Activity> retrieveAllActivitySetFromServer() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverSelector.getServer()).path("/api/v1/activity/get/Activities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<ArrayList<Activity>>() {});
    }

}
