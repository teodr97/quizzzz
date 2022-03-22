package client.utils;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SingleplayerHighscoreHandler {

    private static final String PATH = "data\\pastgames.data";

    private Pair<String, Integer> allTimeBest;
    private LinkedList<Pair<String, Integer>> latestGames;

    private SingleplayerHighscoreHandler(Pair<String, Integer> allTimeBest) {
        this.allTimeBest = allTimeBest;
        this.latestGames = new LinkedList<>();
    }

    /**
     * Creates an SHH representing an empty file.
     * @return An SHH with values:
     *  - allTimesBest = null
     *  - latestGames = empty list
     */
    private static SingleplayerHighscoreHandler noPreviousData() {
        return new SingleplayerHighscoreHandler(null);
    }

    /**
     * Gets the entry for the best past game.
     * @return A pair representing the best past game. The key contains the date
     * and the entry contains the points.
     */
    public Pair<String, Integer> getAllTimeBest() {
        return this.allTimeBest;
    }

    /**
     * Gets the entries from all games previously played.
     * @return An iterator containing all previously played game. The iterator
     * gives key-value pairs, where the key is the date of the game and the value
     * is how many poitns were earned.
     */
    public Iterator<Pair<String, Integer>> getEntries() {
        return this.latestGames.iterator();
    }

    /**
     * Gets the information from the local highscores file.
     * @return An SHH containing the information about the highscores
     * @throws FileNotFoundException If the file could not found.
     * @throws IncorrectFileFormat If the file of the format was incorrect.
     */
    public static SingleplayerHighscoreHandler getHighscores() throws FileNotFoundException, IncorrectFileFormat {
        System.out.println("Default Path: " + new File("").getAbsolutePath());
        String absPath = new File("").getAbsolutePath();
        Scanner scanner = new Scanner(new File(absPath + "\\" + PATH));
        // The file is empty, therefore no information has been recorded yet
        if (!scanner.hasNextLine()) {
            return noPreviousData();
        }

        SingleplayerHighscoreHandler ret;
        String firstLine = scanner.nextLine();
        if (!firstLine.startsWith("Best;")) {
            throw new IncorrectFileFormat("The Past Games file should start with \"Best;\"");
        } else {
            String[] parts = firstLine.split(";");

            try {
                int bestPoints = Integer.parseInt(parts[2]);
                ret = new SingleplayerHighscoreHandler(new Pair<>(parts[1], bestPoints));
            } catch (NumberFormatException e) {
                throw new IncorrectFileFormat("The entry on line 1 should have a number as the third argument.");
            }

        }

        int fileLine = 2;
        // adds the other entries, the dates should be in descending order
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(";");
            if (parts.length != 2) throw new IncorrectFileFormat("The entry on line " + fileLine + " should have two arguments separated by a ';'.");
            try {
                int entryPoints = Integer.parseInt(parts[1]);
                ret.latestGames.add(new Pair<>(parts[0],entryPoints));
            } catch (NumberFormatException e) {
                throw new IncorrectFileFormat("The entry on line " + fileLine + " should have a number as the second argument.");
            }
            fileLine++;
        }

        try {
            ret.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Writes the information within the SHH back into the file.
     * @throws IOException If the file could not found.
     */
    private void writeToFile() throws IOException {
        FileWriter fw = new FileWriter(PATH);
        if (this.allTimeBest == null) {
            fw.close();
            return;
        }
        fw.write("Best;" + this.allTimeBest.getKey() + ";" + this.allTimeBest.getValue());
        for (var entry : this.latestGames) {
            fw.write("\n" + entry.getKey() + ";" + entry.getValue());
        }
        fw.close();
    }

    /**
     * Saves a new game entry to the SHH and also writes it to the local file.
     * @param points How many points were earned in the game.
     */
    public void saveNewEntry(int points) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String curDate = dtf.format(LocalDate.now());
        var newEntry = new Pair<>(curDate, points);
        this.latestGames.addFirst(newEntry);

        if (this.allTimeBest == null || this.allTimeBest.getValue() < newEntry.getValue()) {
            this.allTimeBest = newEntry;
        }
        try {
            this.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class IncorrectFileFormat extends RuntimeException{
        public IncorrectFileFormat(String message) {
            super(message);
        }
    }
}
