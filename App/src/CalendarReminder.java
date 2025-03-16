import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CalendarReminder {
    private static final String LIST_TRAVEL_PLAN = "ListTravelPlan.csv";
    private final String userDataPath;

    public CalendarReminder(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    public void displayCountdown() {
        try {
            Map<Integer, TravelPlan> plans = loadTravelPlans();
            if (plans.isEmpty()) {
                System.out.println("\nNo travel plans found.");
                return;
            }

            List<Map.Entry<Integer, TravelPlan>> sortedPlans = new ArrayList<>(plans.entrySet());
            sortedPlans.sort(Comparator.comparing(entry -> entry.getValue().date));

            LocalDate today = LocalDate.now();
            for (Map.Entry<Integer, TravelPlan> entry : sortedPlans) {
                TravelPlan plan = entry.getValue();
                long daysLeft = ChronoUnit.DAYS.between(today, plan.date);
                System.out.printf("Plan %d: %s to %s for %s%n", entry.getKey(), plan.season, plan.year, plan.continent);
                System.out.printf("Date: %s%n", plan.date);
                System.out.printf("Days left: %d%n", daysLeft);
                System.out.println("==========================================");
            }

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
            if (parts.length >= 5) {
                LocalDate date = LocalDate.of(
                        Integer.parseInt(parts[1].trim()), // year
                        Integer.parseInt(parts[2].trim()), // month
                        Integer.parseInt(parts[3].trim())  // day
                );
                plans.put(i + 1, new TravelPlan(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[4].trim(),
                        date));
            }
        }
        return plans;
    }

    private static class TravelPlan {
        final String season;
        final String year;
        final String continent;
        final LocalDate date;

        TravelPlan(String season, String year, String continent, LocalDate date) {
            this.season = season;
            this.year = year;
            this.continent = continent;
            this.date = date;
        }
    }

    public static void main(String[] args) {
        // Example usage
        String userDataPath = "e:\\TravelPlanningApp\\userdata\\sitha";
        CalendarReminder reminder = new CalendarReminder(userDataPath);
        reminder.displayCountdown();
    }
}
