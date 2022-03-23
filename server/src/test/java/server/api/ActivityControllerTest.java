package server.api;

import commons.game.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.ActivityRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ActivityControllerTest {

    @Mock
    ActivityRepository repositoryMock;

    ActivityController spyController;
    ActivityController controller;

    /**
     * The initializer executed before every test.
     */
    @BeforeEach
    private void initialiseTest() {
        MockitoAnnotations.openMocks(this);
        controller = new ActivityController(repositoryMock);
        spyController = spy(controller);
    }

    /**
     * Test method.
     */
    @Test
    void getAllActivities() {
        controller.getAllActivities();
        verify(repositoryMock).findAll();
    }

    /**
     * Test method.
     */
    @Test
    void retrieveRandomActivitiesSet() {
        List<Activity> activityList = new LinkedList<>();

        activityList.add(new Activity("Test", "Test", 1, "Test"));
        activityList.add(new Activity("Test", "Test", 1, "Test"));
        activityList.add(new Activity("Test", "Test", 1, "Test"));
        when(repositoryMock.findAll()).thenReturn(activityList);
        controller.retrieveRandomActivitiesSet();
        verify(repositoryMock).findAll();
    }

    /**
     * Test method.
     */
    @Test
    void getById() {
        when(repositoryMock.findById(3)).thenReturn(Optional.of(new Activity("Test", "Test", 1, "Test")));
        controller.getById(3);
        verify(repositoryMock).findById(3);
    }

    /**
     * Test method.
     */
    @Test
    void createActivity() {
        Activity activity = new Activity("Test", "Test", 1, "Test");

        controller.createActivity(activity);
        verify(repositoryMock).save(activity);
    }

    /**
     * Test deleting all activities in the database
     */
    @Test
    void deleteAll(){
        Activity activity = new Activity("Test", "Test", 1, "Test");
        controller.createActivity(activity);
        controller.createActivity(activity);
        controller.createActivity(activity);

        controller.deleteAll();
        controller.getAllActivities();
        verify(repositoryMock).findAll();
    }
}