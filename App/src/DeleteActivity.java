import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DeleteActivity {
    private static final String ACTIVITY_NOTES_FILE = "activities.csv";
    private final String userDataPath;

    public DeleteActivity(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    public void deleteActivity(Scanner scanner) {
        List<String> activities = loadActivities();
        if (activities.isEmpty()) {
            System.out.println("\nNo activities found.");
            return;
        }

        displayActivities(activities);
        int activityNumber = getValidNumber(scanner, "Enter the number of the activity to delete: ", 1,
                activities.size());

        activities.remove(activityNumber - 1);
        saveActivities(activities);
        System.out.println("Activity deleted successfully.");
    }

    private List<String> loadActivities() {
        List<String> activities = new ArrayList<>();
        Path activityPath = Paths.get(userDataPath, ACTIVITY_NOTES_FILE);

        if (!Files.exists(activityPath)) {
            return activities;
        }

        try {
            activities = Files.readAllLines(activityPath);
        } catch (IOException e) {
            System.out.println("Error reading activities: " + e.getMessage());
        }
        return activities;
    }

    private void displayActivities(List<String> activities) {
        System.out.println("\nCurrent activities:");
        for (int i = 0; i < activities.size(); i++) {
            System.out.println((i + 1) + ". " + activities.get(i));
        }
    }

    private int getValidNumber(Scanner scanner, String prompt, int min, int max) {
        int number;
        while (true) {
            System.out.print(prompt);
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number >= min && number <= max) {
                    break;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return number;
    }

    private void saveActivities(List<String> activities) {
        Path activityPath = Paths.get(userDataPath, ACTIVITY_NOTES_FILE);
        try {
            Files.write(activityPath, activities);
        } catch (IOException e) {
            System.out.println("Error saving activities: " + e.getMessage());
        }
    }
}