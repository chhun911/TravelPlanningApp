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

    public void editActivityNotes() {
        List<String[]> activityNotes = loadActivityNotes();

        if (activityNotes.isEmpty()) {
            System.out.println("No activity notes found.");
            return;
        }

        displayActivityNotes(activityNotes);
        System.out.print("Enter the number of the activity note to edit: ");
        int noteNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (noteNumber < 1 || noteNumber > activityNotes.size()) {
            System.out.println("Invalid number. Please try again.");
            return;
        }

        String[] parts = activityNotes.get(noteNumber - 1);
        System.out.print("Enter new city (current: " + parts[1] + "): ");
        String newCity = scanner.nextLine().trim();
        System.out.print("Enter new country (current: " + parts[2] + "): ");
        String newCountry = scanner.nextLine().trim();
        System.out.print("Enter new activity (current: " + parts[3] + "): ");
        String newActivity = scanner.nextLine().trim();
        System.out.print("Enter new time (current: " + parts[4] + "): ");
        String newTime = scanner.nextLine().trim();

        activityNotes.set(noteNumber - 1, new String[] { parts[0], newCity, newCountry, newActivity, newTime });
        saveActivityNotes(activityNotes);
        System.out.println("Activity note updated successfully!");
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
            System.out.println((i + 1) + ". " + plan[0] + " to " + plan[1] + " for " + plan[2] + " days");
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
            String activity = getUserInput("Enter activity: ");
            String time = getUserInput("Enter time: ");
            activities.add(new String[] { activity, time });
        }
        return activities;
    }

    private void saveActivityNotes(int planNumber, String city, String country, List<String[]> activities) {
        File file = new File(userDataPath + File.separator + ACTIVITY_NOTES_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (String[] activity : activities) {
                writer.write(planNumber + "," + city + "," + country + "," + activity[0] + "," + activity[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving activity notes: " + e.getMessage());
        }
    }

    private List<String[]> loadActivityNotes() {
        List<String[]> activityNotes = new ArrayList<>();
        File file = new File(userDataPath + File.separator + ACTIVITY_NOTES_FILE);

        if (!file.exists()) {
            return activityNotes;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    activityNotes.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading activity notes: " + e.getMessage());
        }
        return activityNotes;
    }

    private void displayActivityNotes(List<String[]> activityNotes) {
        System.out.println("\nActivity Notes:");
        for (int i = 0; i < activityNotes.size(); i++) {
            String[] note = activityNotes.get(i);
            System.out.printf("%d. Plan Number: %s, City: %s, Country: %s, Activity: %s, Time: %s%n", 
                i + 1, note[0], note[1], note[2], note[3], note[4]);
        }
    }

    private void saveActivityNotes(List<String[]> activityNotes) {
        File file = new File(userDataPath + File.separator + ACTIVITY_NOTES_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String[] note : activityNotes) {
                writer.write(String.join(",", note));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving activity notes: " + e.getMessage());
        }
    }
}