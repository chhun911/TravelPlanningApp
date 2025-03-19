import java.io.*;
import java.util.*;

public class ActivityNotes {
    private static final String LIST_TRAVEL_PLAN = "ListTravelPlan.csv";
    private static final String ACTIVITY_NOTES_FILE = "activities.csv";
    private final Scanner scanner;
    private final String userDataPath;

    public ActivityNotes(String userDataPath) {
        this.userDataPath = userDataPath;
        this.scanner = new Scanner(System.in);
    }

    public void addActivityNotes() {
        List<String[]> plans = loadPlans();
        if (plans.isEmpty()) {
            System.out.println("\nNo travel plans found. Please add one first.");
            return;
        }

        displayPlans(plans);
        int selectedPlanNumber = getValidNumber("Select a plan (1-" + plans.size() + "): ", 1, plans.size());

        int numDestinations = getValidNumber("How many destinations are in your plan? ", 1, Integer.MAX_VALUE);

        for (int i = 1; i <= numDestinations; i++) {
            System.out.println("\nDestination " + i + ":");
            String city = getUserInput("Enter city: ");
            String country = getUserInput("Enter country: ");

            int days = getValidNumber("How many days will you spend in this destination? ", 1, Integer.MAX_VALUE);
            List<String[]> activities = getActivities(days);

            saveActivityNotes(selectedPlanNumber, city, country, activities);
        }

        System.out.println("\nActivity notes saved successfully!");
    }

    private List<String[]> loadPlans() {
        List<String[]> plans = new ArrayList<>();
        File file = new File(userDataPath + File.separator + LIST_TRAVEL_PLAN);

        if (!file.exists()) {
            return plans;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    plans.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading travel plans: " + e.getMessage());
        }
        return plans;
    }

    private void displayPlans(List<String[]> plans) {
        System.out.println("\nTravel Plans:");
        for (int i = 0; i < plans.size(); i++) {
            String[] plan = plans.get(i);
            System.out.println((i + 1) + ". " + plan[0] + " in " + plan[3] + "/" + plan[2] + "/" + plan[1] + " to " + plan[4] );
        }
    }

    private int getValidNumber(String prompt, int min, int max) {
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

    private String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private List<String[]> getActivities(int days) {
        List<String[]> activities = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            System.out.println("\nDay " + i + ":");
            int numActivities = getValidNumber("Enter number of activities for day " + i + ": ", 1, Integer.MAX_VALUE);
            for (int j = 1; j <= numActivities; j++) {
                System.out.println("Activity " + j + ":");
                String activity = getUserInput("Enter activity: ");
                String time = getUserInput("Enter time: ");
                activities.add(new String[] { String.valueOf(i), activity, time });
            }
        }
        return activities;
    }

    private void saveActivityNotes(int planNumber, String city, String country, List<String[]> activities) {
        File file = new File(userDataPath + File.separator + ACTIVITY_NOTES_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (String[] activity : activities) {
                writer.write(planNumber + "," + activity[0] + "," + city + "," + country + "," + activity[1] + "," + activity[2]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving activity notes: " + e.getMessage());
        }
    }
}