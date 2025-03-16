import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DeleteListTravelPlan {
    private static final String LIST_TRAVEL_PLAN_FILE = "ListTravelPlan.csv";
    private static final String ACTIVITY_NOTES_FILE = "activities.csv";
    private final String userDataPath;

    public DeleteListTravelPlan(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    public void deleteTravelPlan(Scanner scanner) {
        List<String> travelPlans = loadTravelPlans();
        if (travelPlans.isEmpty()) {
            System.out.println("\nNo travel plans found.");
            return;
        }

        displayTravelPlans(travelPlans);
        int planNumber = getValidNumber(scanner, "Enter the number of the travel plan to delete: ", 1,
                travelPlans.size());

        travelPlans.remove(planNumber - 1);
        saveTravelPlans(travelPlans);
        deleteAssociatedActivities(planNumber);
        System.out.println("Travel plan and associated activities deleted successfully.");
    }

    private List<String> loadTravelPlans() {
        List<String> travelPlans = new ArrayList<>();
        Path travelPlanPath = Paths.get(userDataPath, LIST_TRAVEL_PLAN_FILE);

        if (!Files.exists(travelPlanPath)) {
            return travelPlans;
        }

        try {
            travelPlans = Files.readAllLines(travelPlanPath);
        } catch (IOException e) {
            System.out.println("Error reading travel plans: " + e.getMessage());
        }
        return travelPlans;
    }

    private void displayTravelPlans(List<String> travelPlans) {
        System.out.println("\nCurrent travel plans:");
        for (int i = 0; i < travelPlans.size(); i++) {
            System.out.println((i + 1) + ". " + travelPlans.get(i));
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

    private void saveTravelPlans(List<String> travelPlans) {
        Path travelPlanPath = Paths.get(userDataPath, LIST_TRAVEL_PLAN_FILE);
        try {
            Files.write(travelPlanPath, travelPlans);
        } catch (IOException e) {
            System.out.println("Error saving travel plans: " + e.getMessage());
        }
    }

    private void deleteAssociatedActivities(int planNumber) {
        List<String> activities = loadActivities();
        List<String> updatedActivities = new ArrayList<>();

        for (String activity : activities) {
            String[] parts = activity.split(",");
            if (parts.length >= 1) {
                int activityPlanNumber = Integer.parseInt(parts[0].trim());
                if (activityPlanNumber != planNumber) {
                    updatedActivities.add(activity);
                }
            }
        }

        saveActivities(updatedActivities);
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

    private void saveActivities(List<String> activities) {
        Path activityPath = Paths.get(userDataPath, ACTIVITY_NOTES_FILE);
        try {
            Files.write(activityPath, activities);
        } catch (IOException e) {
            System.out.println("Error saving activities: " + e.getMessage());
        }
    }
}
