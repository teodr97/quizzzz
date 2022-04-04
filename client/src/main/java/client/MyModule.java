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

import client.scenes.*;
import client.utils.QuestionRetriever;
import client.utils.StatSharerSingleplayer;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    //basically configures the MainCtrl and other frontend classes so it can be used
    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(Splash.class).in(Scopes.SINGLETON);
        binder.bind(HowToPlay.class).in(Scopes.SINGLETON);
        binder.bind(Username.class).in(Scopes.SINGLETON);
        binder.bind(PastGames.class).in(Scopes.SINGLETON);
        binder.bind(SinglePlayer.class).in(Scopes.SINGLETON);
        binder.bind(MultiPlayer.class).in(Scopes.SINGLETON);
        binder.bind(EndscreenSingleplayer.class).in(Scopes.SINGLETON);
        binder.bind(EndscreenMultiplayer.class).in(Scopes.SINGLETON);
        binder.bind(StatSharerSingleplayer.class).in(Scopes.SINGLETON);
        binder.bind(QuestionRetriever.class).in(Scopes.SINGLETON);
        binder.bind(WaitingRoom.class).in(Scopes.SINGLETON);
        binder.bind(Admin.class).in(Scopes.SINGLETON);
        binder.bind(SingleplayerUsername.class).in(Scopes.SINGLETON);
        binder.bind(ServerSelectorCtrl.class).in(Scopes.SINGLETON);
    }
}