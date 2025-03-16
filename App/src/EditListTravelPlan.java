import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EditListTravelPlan {
    private final String userDataPath;

    public EditListTravelPlan(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    public void listing(Scanner scanner) {
        String filePath = userDataPath + File.separator + "ListTravelPlan.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            System.out.print("Enter the Number of Travel Plans: ");
            int n = scanner.nextInt();
            scanner.nextLine();

            if (n > 0) {
                ArrayList<String[]> plans = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    System.out.print("Enter Season for Your Plan " + (i + 1) + ": ");
                    String season = scanner.nextLine();

                    System.out.print("Enter Year for Your Plan " + (i + 1) + ": ");
                    int year = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Month for Your Plan " + (i + 1) + " (1-12): ");
                    int month = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Day for Your Plan " + (i + 1) + " (1-31): ");
                    int day = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Continent for Your Plan " + (i + 1) + ": ");
                    String continent = scanner.nextLine();

                    plans.add(new String[] { season, String.valueOf(year), String.valueOf(month), String.valueOf(day), continent });
                }

                System.out.println("\nYour New Travel Plans:");
                for (int i = 0; i < plans.size(); i++) {
                    String[] plan = plans.get(i);
                    System.out.println("Plan " + (i + 1) + ":");
                    System.out.println("Season: " + plan[0]);
                    System.out.println("Year: " + plan[1]);
                    System.out.println("Month: " + plan[2]);
                    System.out.println("Day: " + plan[3]);
                    System.out.println("Continent: " + plan[4]);
                    System.out.println();

                    writer.println(plan[0] + "," + plan[1] + "," + plan[2] + "," + plan[3] + "," + plan[4]);
                }
                writer.flush();
                System.out.println("All new plans have been saved to your personal travel plans");
            }
        } catch (IOException e) {
            System.out.println("Error saving travel plans: " + e.getMessage());
        }
    }

    public void editTravelPlans(Scanner scanner) {
        List<String> travelPlans = loadTravelPlans();

        if (travelPlans.isEmpty()) {
            System.out.println("No travel plans found.");
            return;
        }

        displayTravelPlans(travelPlans);
        System.out.print("Enter the number of the travel plan to edit: ");
        int planNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (planNumber < 1 || planNumber > travelPlans.size()) {
            System.out.println("Invalid number. Please try again.");
            return;
        }

        String[] parts = travelPlans.get(planNumber - 1).split(",");
        System.out.print("Enter new season (current: " + parts[0] + "): ");
        String newSeason = scanner.nextLine().trim();
        System.out.print("Enter new year (current: " + parts[1] + "): ");
        int newYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new month (current: " + parts[2] + "): ");
        int newMonth = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new day (current: " + parts[3] + "): ");
        int newDay = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new continent (current: " + parts[4] + "): ");
        String newContinent = scanner.nextLine().trim();

        travelPlans.set(planNumber - 1, newSeason + "," + newYear + "," + newMonth + "," + newDay + "," + newContinent);
        saveTravelPlans(travelPlans);
        System.out.println("Travel plan updated successfully!");
    }

    private List<String> loadTravelPlans() {
        List<String> travelPlans = new ArrayList<>();
        String fileName = userDataPath + File.separator + "ListTravelPlan.csv";
        try {
            travelPlans = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.out.println("Error reading travel plans: " + e.getMessage());
        }
        return travelPlans;
    }

    private void displayTravelPlans(List<String> travelPlans) {
        System.out.println("\nCurrent travel plans:");
        for (int i = 0; i < travelPlans.size(); i++) {
            String[] parts = travelPlans.get(i).split(",");
            if (parts.length >= 5) {
                System.out.printf("%d. Season: %s, Year: %s, Month: %s, Day: %s, Continent: %s%n",
                        i + 1, parts[0], parts[1], parts[2], parts[3], parts[4]);
            }
        }
    }

    private void saveTravelPlans(List<String> travelPlans) {
        String fileName = userDataPath + File.separator + "ListTravelPlan.csv";
        try {
            Files.write(Paths.get(fileName), travelPlans);
        } catch (IOException e) {
            System.out.println("Error saving travel plans: " + e.getMessage());
        }
    }
}