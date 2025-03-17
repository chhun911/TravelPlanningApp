import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ViewActivity {
    private static final String LIST_TRAVEL_PLAN = "ListTravelPlan.csv";
    private static final String ACTIVITY_NOTES_FILE = "activities.csv";
    private static final String SEPARATOR = "==========================================";

    private final String userDataPath;

    public ViewActivity(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    public void viewActivities() {
        try {
            Map<Integer, TravelPlan> plans = loadTravelPlans();
            if (plans.isEmpty()) {
                System.out.println("\nNo travel plans found.");
                return;
            }

            Map<Integer, List<ActivityNote>> activities = loadActivities();
            displayPlansWithActivities(plans, activities);

        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    private Map<Integer, TravelPlan> loadTravelPlans() throws IOException {
        Map<Integer, TravelPlan> plans = new HashMap<>();
        Path planPath = Paths.get(userDataPath, LIST_TRAVEL_PLAN);

        if (!Files.exists(planPath)) {
            return plans;
        }

        List<String> lines = Files.readAllLines(planPath);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 3) {
                plans.put(i + 1, new TravelPlan(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim()));
            }
        }
        return plans;
    }

    private Map<Integer, List<ActivityNote>> loadActivities() throws IOException {
        Map<Integer, List<ActivityNote>> activities = new HashMap<>();
        Path activityPath = Paths.get(userDataPath, ACTIVITY_NOTES_FILE);

        if (!Files.exists(activityPath)) {
            return activities;
        }

        List<String> lines = Files.readAllLines(activityPath);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                try {
                    int planNumber = Integer.parseInt(parts[0].trim());
                    int day = Integer.parseInt(parts[1].trim());
                    String city = parts[2].trim();
                    String country = parts[3].trim();
                    String activity = parts[4].trim();
                    String time = parts[5].trim();

                    ActivityNote note = new ActivityNote(day, city, country, activity, time);
                    activities.computeIfAbsent(planNumber, k -> new ArrayList<>()).add(note);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing activity note: " + Arrays.toString(parts));
                }
            } else {
                System.err.println("Invalid activity note format: " + Arrays.toString(parts));
            }
        }
        return activities;
    }

    private void displayPlansWithActivities(
            Map<Integer, TravelPlan> plans,
            Map<Integer, List<ActivityNote>> activities) {

        System.out.println("\nYOUR TRAVEL PLANS AND ACTIVITIES");
        System.out.println(SEPARATOR);

        for (Map.Entry<Integer, TravelPlan> entry : plans.entrySet()) {
            int planNumber = entry.getKey();
            TravelPlan plan = entry.getValue();

            System.out.printf("\nPlan %d:%n", planNumber);
            System.out.printf("Season: %s%n", plan.season);
            System.out.printf("Year: %s%n", plan.year);
            System.out.printf("Continent: %s%n", plan.continent);

            List<ActivityNote> planActivities = activities.get(planNumber);
            if (planActivities != null && !planActivities.isEmpty()) {
                System.out.println("\nActivities:");
                displayActivities(planActivities);
            } else {
                System.out.println("\nNo activities planned yet for Plan " + planNumber + ".");
            }
            System.out.println(SEPARATOR);
        }
    }

    private void displayActivities(List<ActivityNote> activities) {
        Map<Integer, Map<String, List<ActivityNote>>> groupedActivities = new TreeMap<>();

        for (ActivityNote note : activities) {
            groupedActivities
                .computeIfAbsent(note.day, k -> new TreeMap<>())
                .computeIfAbsent(note.city + ", " + note.country, k -> new ArrayList<>())
                .add(note);
        }

        for (Map.Entry<Integer, Map<String, List<ActivityNote>>> dayEntry : groupedActivities.entrySet()) {
            int day = dayEntry.getKey();
            System.out.printf("\nDay %d:%n", day);

            for (Map.Entry<String, List<ActivityNote>> locationEntry : dayEntry.getValue().entrySet()) {
                String location = locationEntry.getKey();
                System.out.printf("Location: %s%n", location);

                for (ActivityNote note : locationEntry.getValue()) {
                    System.out.printf("  - Activity: %s at %s%n", note.activity, note.time);
                }
            }
        }
    }

    private static class TravelPlan {
        final String season;
        final String year;
        final String continent;

        TravelPlan(String season, String year, String continent) {
            this.season = season;
            this.year = year;
            this.continent = continent;
        }
    }

    private static class ActivityNote {
        final int day;
        final String city;
        final String country;
        final String activity;
        final String time;

        ActivityNote(int day, String city, String country, String activity, String time) {
            this.day = day;
            this.city = city;
            this.country = country;
            this.activity = activity;
            this.time = time;
        }
    }
}