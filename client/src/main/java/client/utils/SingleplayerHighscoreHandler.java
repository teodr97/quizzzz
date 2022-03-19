package client.utils;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class SingleplayerHighscoreHandler {

    private static final String path = "client/data/pastgames.data";

    private Pair<String, String> allTimeBest;
    private List<Pair<String, String>> latestGames;

    private SingleplayerHighscoreHandler() {

    }

    public SingleplayerHighscoreHandler getHighscores() throws FileNotFoundException, IncorrectFileFormat {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        if (!scanner.hasNextLine()) throw new IncorrectFileFormat("The file should start with \"Best:\"");
        return null;
    }

    private static class IncorrectFileFormat extends Exception{
        public IncorrectFileFormat(String message) {
            super(message);
        }
    }
}
