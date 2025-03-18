import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ShareTravelPlan {
    private static final String LIST_TRAVEL_PLAN_FILE = "ListTravelPlan.csv";
    private static final String SHARED_TRAVEL_PLAN_FILE = "shared_travel_plans.csv";
    private static final String ACTIVITY_NOTES_FILE = "activities.csv";
    private static final String SHARED_ACTIVITY_FILE = "shared_activities.csv";
    private final String userDataPath;
    private final String currentUser;

    public ShareTravelPlan(String userDataPath, String currentUser) {
        this.userDataPath = userDataPath;
        this.currentUser = currentUser;
    }

    public void sharePlan(Scanner scanner) {
        List<String> travelPlans = loadTravelPlans();
        if (travelPlans.isEmpty()) {
            System.out.println("\nNo travel plans found.");
            return;
        }

        displayTravelPlans(travelPlans);
        int planNumber = getValidNumber(scanner, "Enter the number of the travel plan to share: ", 1,
                travelPlans.size());

        String selectedPlan = travelPlans.get(planNumber - 1).trim(); // Do not remove from list
        saveSharedTravelPlan(selectedPlan);
        shareActivities(selectedPlan);
        System.out.println("Travel plan shared successfully by " + currentUser + "!");
    }

    private List<String> loadTravelPlans() {
        Path travelPlanPath = Paths.get(userDataPath, LIST_TRAVEL_PLAN_FILE);
        if (!Files.exists(travelPlanPath)) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(travelPlanPath);
        } catch (IOException e) {
            System.out.println("Error reading travel plans: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void displayTravelPlans(List<String> travelPlans) {
        System.out.println("\nYour travel plans:");
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

    private void saveSharedTravelPlan(String plan) {
        Path sharedPlanPath = Paths.get(SHARED_TRAVEL_PLAN_FILE);
        List<String> lines = new ArrayList<>();

        // Read existing shared plans
        if (Files.exists(sharedPlanPath)) {
            try {
                lines = Files.readAllLines(sharedPlanPath);
            } catch (IOException e) {
                System.out.println("Error reading shared travel plans: " + e.getMessage());
            }
        }

        // Check if the plan is already shared
        boolean planExists = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith(plan + ",")) {
                lines.set(i, plan + ",Shared by: " + currentUser);
                planExists = true;
                break;
            }
        }

        // If the plan is not already shared, add it
        if (!planExists) {
            lines.add(plan + ",Shared by: " + currentUser);
        }

        // Write updated shared plans
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sharedPlanPath.toFile()))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving shared travel plan: " + e.getMessage());
        }
    }

    private void shareActivities(String plan) {
        Path activityPath = Paths.get(userDataPath, ACTIVITY_NOTES_FILE);
        Path sharedActivityPath = Paths.get(SHARED_ACTIVITY_FILE);

        System.out.println("Checking for activities in: " + activityPath);

        if (!Files.exists(activityPath)) {
            System.out.println("No activities file found at: " + activityPath);
            return;
        }

        try {
            // Load the travel plans to get the plan number
            List<String> travelPlans = loadTravelPlans();
            int selectedPlanNumber = -1;

            // Find the index (plan number) of the selected plan
            for (int i = 0; i < travelPlans.size(); i++) {
                if (travelPlans.get(i).trim().equals(plan)) {
                    selectedPlanNumber = i + 1; // Plan numbers start at 1
                    break;
                }
            }

            if (selectedPlanNumber == -1) {
                System.out.println("Could not determine plan number for: " + plan);
                return;
            }

            System.out.println("Found plan number: " + selectedPlanNumber + " for plan: " + plan);

            // Read all activities from the user's file
            List<String> userActivities = Files.readAllLines(activityPath);
            System.out.println("Found " + userActivities.size() + " activities in total");

            // Read all existing shared activities
            List<String> existingSharedActivities = new ArrayList<>();
            if (Files.exists(sharedActivityPath)) {
                existingSharedActivities = Files.readAllLines(sharedActivityPath);
                System.out.println("Found " + existingSharedActivities.size() + " existing shared activities");
            }

            List<String> updatedActivities = new ArrayList<>(existingSharedActivities);
            int activitiesAdded = 0;

            // Add activities for the selected plan
            for (String activity : userActivities) {
                String[] parts = activity.split(",");
                if (parts.length >= 6) {
                    try {
                        int planNumber = Integer.parseInt(parts[0].trim());
                        if (planNumber == selectedPlanNumber) {
                            // Build the shared activity format:
                            // season,year,continent,day,city,country,activity,time,Shared by: user
                            String[] planParts = plan.split(",");
                            if (planParts.length >= 3) {
                                String season = planParts[0];
                                String year = planParts[1];
                                String continent = planParts[2];

                                String day = parts[1].trim();
                                String city = parts[2].trim();
                                String country = parts[3].trim();
                                String activityName = parts[4].trim();
                                String time = parts[5].trim();

                                String sharedActivity = String.format("%s,%s,%s,%s,%s,%s,%s,%s,Shared by: %s",
                                        season, year, continent, day, city, country, activityName, time, currentUser);

                                if (!updatedActivities.contains(sharedActivity)) {
                                    updatedActivities.add(sharedActivity);
                                    activitiesAdded++;
                                    System.out.println("Added activity: " + sharedActivity);
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing activity: " + activity);
                    }
                }
            }

            System.out.println("Adding " + activitiesAdded + " new activities to shared file");

            // Write all activities to the shared file
            if (activitiesAdded > 0) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(sharedActivityPath.toFile()))) {
                    for (String line : updatedActivities) {
                        writer.write(line);
                        writer.newLine();
                    }
                    System.out.println(
                            "Successfully wrote " + updatedActivities.size() + " activities to " + sharedActivityPath);
                }
            } else {
                System.out.println("No new activities to share for this plan");
            }
        } catch (IOException e) {
            System.out.println("Error sharing activities: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
