import java.io.*;
import java.util.*;

public class ViewSharedTravelPlan {
    private static final String SHARED_TRAVEL_PLAN_FILE = "shared_travel_plans.csv";
    private static final String SHARED_ACTIVITY_FILE = "shared_activities.csv";
    private static final String SEPARATOR = "==========================================";

    public void viewSharedPlans() {
        try {
            List<String[]> sharedPlans = loadSharedTravelPlans();
            if (sharedPlans.isEmpty()) {
                System.out.println("\nNo shared travel plans found.");
                return;
            }

            Map<String, List<String>> sharedActivities = loadSharedActivities();
            displaySharedPlans(sharedPlans, sharedActivities);
        } catch (IOException e) {
            System.err.println("Error reading shared travel plans: " + e.getMessage());
        }
    }

    private List<String[]> loadSharedTravelPlans() throws IOException {
        List<String[]> plans = new ArrayList<>();
        File file = new File(SHARED_TRAVEL_PLAN_FILE);

        if (!file.exists()) {
            System.out.println("Shared travel plans file does not exist.");
            return plans;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    plans.add(new String[] { parts[0] + "," + parts[1] + "," + parts[2], parts[3] });
                }
            }
        }
        return plans;
    }

    private Map<String, List<String>> loadSharedActivities() {
        Map<String, List<String>> activities = new HashMap<>();
        File file = new File(SHARED_ACTIVITY_FILE);

        if (!file.exists()) {
            System.out.println("Shared activities file does not exist.");
            return activities;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) { // At least 8 parts for a valid shared activity
                    // The first three parts form the plan identifier
                    String planIdentifier = parts[0] + "," + parts[1] + "," + parts[2];

                    // Create a human-readable activity description
                    // parts[3] = day, parts[4] = city, parts[5] = country, parts[6] = activity,
                    // parts[7] = time
                    String activityDetail = String.format("Day %s - %s in %s, %s at %s",
                            parts[3], parts[6], parts[4], parts[5], parts[7]);

                    // Add to the activities map
                    activities.computeIfAbsent(planIdentifier, k -> new ArrayList<>()).add(activityDetail);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading shared activities: " + e.getMessage());
        }

        return activities;
    }

    private void displaySharedPlans(List<String[]> plans, Map<String, List<String>> sharedActivities) {
        System.out.println("\nSHARED TRAVEL PLANS AND ACTIVITIES");
        System.out.println(SEPARATOR);

        for (int i = 0; i < plans.size(); i++) {
            String[] plan = plans.get(i);
            System.out.printf("\nPlan %d:%n", i + 1);
            System.out.println(plan[0]); // Travel plan details
            System.out.printf("%s%n", plan[1]);

            // Show activities
            List<String> activities = sharedActivities.getOrDefault(plan[0], new ArrayList<>());
            if (!activities.isEmpty()) {
                System.out.println("\nActivities:");
                for (String activity : activities) {
                    System.out.println("  - " + activity);
                }
            } else {
                // System.out.println("\nNo activities available for this plan.");
            }
            System.out.println(SEPARATOR);
        }
    }
}

