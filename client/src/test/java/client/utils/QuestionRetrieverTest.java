package client.utils;

import client.scenes.MainCtrl;
import commons.game.Activity;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.GenericType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class QuestionRetrieverTest {

    @Mock
    MainCtrl mainControllerMock;

    @Mock
    Client clientMock;

    List<Activity> activityList = new LinkedList<>();
    QuestionRetriever questionRetriever;

    /**
     * The initializer of each test.
     */
    @BeforeEach
    private void initialiseTests() {
        MockitoAnnotations.openMocks(this);
        questionRetriever = new QuestionRetriever(mainControllerMock);
        activityList.add(new Activity("Test", 1));
    }

    /**
     * Test method.
     */
    @Test
    void retrieveActivitySetFromServer() {
        when(clientMock.target(mainControllerMock.SERVER)
                .path("/api/v1/activity/get/randomSetActivities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {})).thenReturn(activityList);
        assertEquals(new Activity("Test", 1), questionRetriever.retrieveActivitySetFromServer().get(0));
    }
}