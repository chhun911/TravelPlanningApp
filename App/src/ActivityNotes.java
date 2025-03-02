import java.io.*;
import java.util.*;

public class ActivityNotes {
    private static final String LIST_TRAVEL_PLAN = "ListTravelPlan.csv";
    // private static final String ACTIVITY_NOTES_FILE = "ActivityNote.csv";
    private Scanner scanner = new Scanner(System.in);
    private static String username;
    private static int planNumber;

    public static void setUsernameAndPlan(String user, int plan) {
        username = user;
        planNumber = plan;
    }

    public void addActivityNotes() {
        List<String[]> plans = loadPlans();
        if (plans.isEmpty()) {
            System.out.println("No travel plans found. Please add one first.");
            return;
        }

        displayPlans(plans);
        int selectedPlanNumber = getValidNumber("Select a plan (1-" + plans.size() + "): ", 1, plans.size());

        // Ask for the number of destinations
        int numDestinations = getValidNumber("How many destinations are in your plan? ", 1, Integer.MAX_VALUE);

        // Loop through the destinations and gather city, country, and activities for
        // each one
        for (int i = 1; i <= numDestinations; i++) {
            System.out.println("\nDestination " + i + ":");
            String city = getUserInput("Enter city: ");
            String country = getUserInput("Enter country: ");

            int days = getValidNumber("How many days will you spend in this destination? ", 1, Integer.MAX_VALUE);
            List<String[]> activities = getActivities(days);

            saveActivityNotes(selectedPlanNumber, city, country, activities);
        }

        System.out.println("\nActivity notes saved successfully for all destinations!");
    }

    private void displayPlans(List<String[]> plans) {
        System.out.println("\n----- YOUR TRAVEL PLANS -----");
        for (int i = 0; i < plans.size(); i++) {
            System.out.println("Plan " + (i + 1) + ": " + String.join(", ", plans.get(i)));
        }
    }

    private int getValidNumber(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int num = Integer.parseInt(scanner.nextLine().trim());
                if (num >= min && num <= max)
                    return num;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid input. Please enter a number between " + min + " and " + max);
        }
    }

    private String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private List<String[]> getActivities(int days) {
        List<String[]> activities = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            activities.add(new String[] { String.valueOf(i), getUserInput("Day " + i + " activity: ") });
        }
        return activities;
    }

    private List<String[]> loadPlans() {
        List<String[]> plans = new ArrayList<>();
        File file = new File(LIST_TRAVEL_PLAN);
        if (!file.exists())
            return plans;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3)
                    plans.add(parts);
            }
        } catch (IOException e) {
            System.out.println("Error reading travel plans file: " + e.getMessage());
        }
        return plans;
    }

    private void saveActivityNotes(int planNumber, String city, String country, List<String[]> activities) {
        try (FileWriter writer = new FileWriter(username + "_activity_" + ActivityNotes.planNumber + ".csv", true)) {
            for (String[] activity : activities) {
                writer.write(planNumber + "," + city + "," + country + "," + activity[0] + "," + activity[1] + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving activities: " + e.getMessage());
        }
    }
}
