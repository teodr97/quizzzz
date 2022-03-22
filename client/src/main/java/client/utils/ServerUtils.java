/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import commons.game.Activity;
import commons.game.Question;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Retrieves the question + answer set that are currently on-server in the form of
     * a ClientQuestion object.
     * @return the current question, together with a set of three answers
     */
    public static Question.ClientQuestion retrieveQuestionFromServer() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/retrieveQuestion")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Question.ClientQuestion>() {});
    }

    /**
     * Retrieves a set of three random, different activities from the database
     * by means of a request to the server.
     * @return List containing three random activities
     */
    public static List<Activity> retrieveActivitySetFromServer() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/v1/activity/get/randomSetActivities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }
}