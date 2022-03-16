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
package client;

import client.scenes.Splash;
import client.scenes.TestMainCtrl;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {

    public static final Injector INJECTOR = createInjector(new MyModule());
    public static final MyFXML FXML = new MyFXML(INJECTOR);

    @Override
    public void start(Stage primaryStage) throws IOException {
        //gets Splash.fxml file, which has the scene/parent set up via Scene Builder
        var overview = FXML.load(Splash.class, "client", "scenes", "Splash.fxml");

        //gets the mainCtrl class
        var mainCtrl = INJECTOR.getInstance(TestMainCtrl.class);
        //passes the parameters to the mainCtrl class
        mainCtrl.initialize(primaryStage, overview, FXML);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }
}
