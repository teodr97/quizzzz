package client.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class SingleplayerHighscoreHandler {

    private static final String PATH = "data\\pastgames.data";

    private LeaderboardEntry allTimeBest;
    private LinkedList<LeaderboardEntry> latestGames;

    /**
     * Private constructor.
     * @param allTimeBest Used when generating an SHH that has at least one entry in it and therefore has a best entry.
     */
    private SingleplayerHighscoreHandler(LeaderboardEntry allTimeBest) {
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
     * @return A leaderboard entry of the best score.
     */
    public LeaderboardEntry getAllTimeBest() {
        return this.allTimeBest;
    }

    /**
     * Gets the entries from all games previously played.
     * @return An iterator containing all previously played games.
     */
    public Iterator<LeaderboardEntry> getEntries() {
        return this.latestGames.iterator();
    }

    /**
     * Gets the information from the local highscores file.
     * @return An SHH containing the information about the highscores
     * @throws FileNotFoundException If the file could not found.
     * @throws IncorrectFileFormat If the file of the format was incorrect.
     */
    public static SingleplayerHighscoreHandler getHighscores() throws FileNotFoundException, IncorrectFileFormat {

//        var path = Path.of("", "data", "pastgames.data").toString();
//        var url = SingleplayerHighscoreHandler.class.getClassLoader().getResource(path);
//        System.out.println("file:" + url.getPath())
//        Path p = Paths.get("src","main", "resources", "data","pastgames.data");

//         Gets the filepath to the local file. Will be changed.
        System.out.println("Default Path: " + new File("").getAbsolutePath());
        String absPath = new File("").getAbsolutePath();
        Scanner scanner = new Scanner(new File(absPath + "\\" + PATH));

//        Scanner scanner = new Scanner(new File(p.toUri().toString()));

        // The file is empty, therefore no information has been recorded yet
        if (!scanner.hasNextLine()) {
            return noPreviousData();
        }

        SingleplayerHighscoreHandler ret;
        String firstLine = scanner.nextLine();

        // The file MUST start with "Best;"
        // All game entries should have format <nickname>;<date>;<points>
        if (!firstLine.startsWith("Best;")) {
            throw new IncorrectFileFormat("The Past Games file should start with \"Best;\"");
        } else {
            String[] parts = firstLine.split(";");
            if (parts.length != 4) throw new IncorrectFileFormat("The entry on line 1 should have four arguments separated by a ';'.");
            try {
                int bestPoints = Integer.parseInt(parts[3]);
                ret = new SingleplayerHighscoreHandler(new LeaderboardEntry(parts[1], parts[2], bestPoints));
            } catch (NumberFormatException e) {
                throw new IncorrectFileFormat("The entry on line 1 should have a number as the fourth argument.");
            }

        }

        int fileLine = 2; // Used for the exception error message
        // Adds the other entries, the dates should be in descending order
        // All game entries should have format <nickname>;<date>;<points>
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(";");
            if (parts.length != 3) throw new IncorrectFileFormat(
                    "The entry on line " + fileLine + " should have three arguments separated by a ';'.");
            try {
                int entryPoints = Integer.parseInt(parts[2]);
                ret.latestGames.add(new LeaderboardEntry(parts[0], parts[1], entryPoints));
            } catch (NumberFormatException e) {
                throw new IncorrectFileFormat("The entry on line " + fileLine + " should have a number as the third argument.");
            }
            fileLine++;
        }

        try {
            // immediately writes the updated SHH to the file
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
        fw.write("Best;" + this.allTimeBest.nickname + ";" + this.allTimeBest.date + ";" + this.allTimeBest.points);
        for (var entry : this.latestGames) {
            fw.write("\n" + entry.nickname + ";" + entry.date + ";" + entry.points);
        }
        fw.close();
    }

    /**
     * Saves a new game entry to the SHH and also writes it to the local file.
     * @param nickname The nickname of the player who got the score.
     * @param points How many points were earned in the game.
     */
    public void saveNewEntry(String nickname, int points) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String curDate = dtf.format(LocalDate.now());
        var newEntry = new LeaderboardEntry(nickname, curDate, points);
        this.latestGames.addFirst(newEntry);

        if (this.allTimeBest == null || this.allTimeBest.points < newEntry.points) {
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

    public static class LeaderboardEntry {
        public String nickname;
        public String date;
        public int points;

        public LeaderboardEntry(String nickname, String date, int points) {
            this.nickname = nickname;
            this.date = date;
            this.points = points;
        }
    }
}
