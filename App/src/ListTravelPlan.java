import java.io.*;
import java.util.*;

public class ListTravelPlan {
    private final String userDataPath;

    public ListTravelPlan(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    void listing(Scanner scanner) {
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

                    System.out.print("Enter Continent for Your Plan " + (i + 1) + ": ");
                    String continent = scanner.nextLine();

                    plans.add(new String[] { season, String.valueOf(year), continent });
                }

                System.out.println("\nYour New Travel Plans:");
                for (int i = 0; i < plans.size(); i++) {
                    String[] plan = plans.get(i);
                    System.out.println("Plan " + (i + 1) + ":");
                    System.out.println("Season: " + plan[0]);
                    System.out.println("Year: " + plan[1]);
                    System.out.println("Continent: " + plan[2]);
                    System.out.println();

                    writer.println(plan[0] + "," + plan[1] + "," + plan[2]);
                }
                writer.flush();
                System.out.println("All new plans have been saved to your personal travel plans");
            }
        } catch (IOException e) {
            System.out.println("Error saving travel plans: " + e.getMessage());
        }
    }
}