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

        if (!Files.exists(activityPath)) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(activityPath.toFile()));
                BufferedWriter writer = new BufferedWriter(new FileWriter(sharedActivityPath.toFile(), true))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String planIdentifier = parts[0].trim() + "," + parts[1].trim() + "," + parts[2].trim();
                    if (planIdentifier.equals(plan)) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error sharing activities: " + e.getMessage());
        }
    }
}
