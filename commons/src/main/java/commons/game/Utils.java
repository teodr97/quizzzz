package commons.game;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Generates a random integer smaller than the argument specified.
     * @param smallerThan the smallest integer which will NOT be included
     *                    in the generation
     * @return random int STRICTILY smaller than smallerThan
     */
    public static int generateRandomIntSmallerThan(int smallerThan) {
        return (int) (Math.random() * smallerThan);
    }

    /**
     * Compares the activities in a given list and retrieves the index of
     * the activity (within the given list) that draws the least power.
     * @param activityList the list of activities to compare
     * @return the index of the activity which draws the least power
     */
    public static int retrieveActivityLeastEnergy(List<Activity> activityList) {
        Activity smallestEnergy = activityList.get(0);
        int index = 0;

        for (int i = 1; i < activityList.size(); i++) {
            if (smallestEnergy.getConsumption_in_wh() >
                    activityList.get(i).getConsumption_in_wh()) { smallestEnergy = activityList.get(i); index = i; }
        }
        return index;
    }

    /**
     * Compares the activities in a given list and retrieves the index of
     * the activity (within the given list) that draws the most power.
     * @param activityList the list of activities to compare
     * @return the index of the activity which draws the most power
     */
    public static int retrieveActivityMostEnergy(List<Activity> activityList) {
        Activity biggestEnergy = activityList.get(0);
        int index = 0;

        for (int i = 1; i < activityList.size(); i++) {
            if (biggestEnergy.getConsumption_in_wh()<
                    activityList.get(i).getConsumption_in_wh()) { biggestEnergy = activityList.get(i); index = i; }
        }
        return index;
    }

    /**
     * Replaces the string variables of each activity in the list with their
     * corresponding power draws (in order to make displaying options on the
     * front-end more convenient). For any option but the correct one, the
     * string variable will hold a multiple of the power draw of the correct
     * answer in order to prevent identical power draws in more options.
     * @param activityList the list of activities that will be displayed as
     *                     options
     * @param correctAnswerIndex the index of the correct answer in the list
     * @return the list of activities with their string variables holding
     * power draw values
     */
    public static ArrayList<Activity> replaceActivitiesWithPowerDraws(ArrayList<Activity> activityList, int correctAnswerIndex) {
        ArrayList<Activity> result = new ArrayList<>();
        result.addAll(activityList);

        // We replace every string containing the activity by the power it draws,
        // as that is what we need to show for the answers the player will choose from.
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setTitle(Long.toString(result.get(i).getConsumption_in_wh()) + " Wh");
            // We change the power amounts used within other answers to amounts that
            // are different to the amount in the correct answer.
            if (i != correctAnswerIndex) {
                // Generate another result if the power draw to be displayed with this options is the
                // same as the one of the correct answer.
                do {
                    result.get(i).setTitle(Long.toString(
                            result.get(correctAnswerIndex).getConsumption_in_wh() + (int) (Math.random() * 100)
                    ) + " Wh");
                } while (result.get(i).getTitle().equals(result.get(correctAnswerIndex).getTitle()));
            }
        }
        return result;

    }

}
