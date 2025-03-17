import java.io.*;
import java.util.*;

public class EditActivity {
    private static final String LIST_TRAVEL_PLAN = "ListTravelPlan.csv";
    private static final String ACTIVITY_NOTES_FILE = "activities.csv";
    private final Scanner scanner;
    private final String userDataPath;

    public EditActivity(String userDataPath) {
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
        int noteNumber = getValidNumber("Enter the number of the activity note to edit: ", 1, activityNotes.size());

        String[] parts = activityNotes.get(noteNumber - 1);
        String newCity = getUserInput("Enter new city (current: " + parts[2] + "): ");
        String newCountry = getUserInput("Enter new country (current: " + parts[3] + "): ");
        String newActivity = getUserInput("Enter new activity (current: " + parts[4] + "): ");
        String newTime = getUserInput("Enter new time (current: " + parts[5] + "): ");

        activityNotes.set(noteNumber - 1, new String[] { parts[0], parts[1], newCity, newCountry, newActivity, newTime });
        saveActivityNotes(activityNotes);
        System.out.println("Activity note updated successfully!");
    }

    private List<String[]> loadPlans() {
        return loadCsvFile(LIST_TRAVEL_PLAN, 3);
    }

    private List<String[]> loadActivityNotes() {
        return loadCsvFile(ACTIVITY_NOTES_FILE, 6);
    }

    private List<String[]> loadCsvFile(String fileName, int expectedParts) {
        List<String[]> data = new ArrayList<>();
        File file = new File(userDataPath + File.separator + fileName);

        if (!file.exists()) {
            return data;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= expectedParts) {
                    data.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + fileName + ": " + e.getMessage());
        }
        return data;
    }

    private void displayPlans(List<String[]> plans) {
        System.out.println("\nTravel Plans:");
        for (int i = 0; i < plans.size(); i++) {
            String[] plan = plans.get(i);
            System.out.println((i + 1) + ". " + plan[0] + " to " + plan[1] + " for " + plan[2] + " days");
        }
    }

    private void displayActivityNotes(List<String[]> activityNotes) {
        System.out.println("\nActivity Notes:");
        for (int i = 0; i < activityNotes.size(); i++) {
            String[] note = activityNotes.get(i);
            System.out.printf("%d. Plan Number: %s, City: %s, Country: %s, Activity: %s, Time: %s%n", 
                i + 1, note[0], note[2], note[3], note[4], note[5]);
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
        List<String[]> data = new ArrayList<>();
        for (String[] activity : activities) {
            data.add(new String[] { String.valueOf(planNumber), city, country, activity[0], activity[1] });
        }
        appendCsvFile(ACTIVITY_NOTES_FILE, data);
    }

    private void saveActivityNotes(List<String[]> activityNotes) {
        writeCsvFile(ACTIVITY_NOTES_FILE, activityNotes);
    }

    private void appendCsvFile(String fileName, List<String[]> data) {
        File file = new File(userDataPath + File.separator + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to " + fileName + ": " + e.getMessage());
        }
    }

    private void writeCsvFile(String fileName, List<String[]> data) {
        File file = new File(userDataPath + File.separator + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to " + fileName + ": " + e.getMessage());
        }
    }
}