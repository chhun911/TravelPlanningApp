import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ListTravelPlan {
    private static class TravelPlan {
        String season;
        int year;
        String continent;

        TravelPlan(String season, int year, String continent) {
            this.season = season;
            this.year = year;
            this.continent = continent;
        }
    }

    void listing(Scanner scanner) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("ListTravelPlan.csv", true))) {
            System.out.print("Enter the Number of Travel Plans (Enter 0 to Exit): ");
            int n = scanner.nextInt();
            scanner.nextLine(); 

            if (n > 0) {
                ArrayList<TravelPlan> plans = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    System.out.print("Enter Season for Your Plan " + (i + 1) + ": ");
                    String season = scanner.nextLine();

                    System.out.print("Enter Year for Your Plan " + (i + 1) + ": ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("Enter Continent for Your Plan " + (i + 1) + ": ");
                    String continent = scanner.nextLine();

                    plans.add(new TravelPlan(season, year, continent));
                }

                System.out.println("\nYour New Travel Plans:");
                for (int i = 0; i < plans.size(); i++) {
                    TravelPlan plan = plans.get(i);
                    System.out.println(" Plan " + (i + 1) + ":");
                    System.out.println("Season: " + plan.season);
                    System.out.println("Year: " + plan.year);
                    System.out.println("Continent: " + plan.continent);
                    System.out.println();


                    writer.println(plan.season + "," + plan.year + "," + plan.continent);
                }
                writer.flush();
                System.out.println("All new data has been saved to ListTravelPlan.csv");
                

                displayAllTravelPlans();
            } else {

                displayAllTravelPlans();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
    private void displayAllTravelPlans() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ListTravelPlan.csv"))) {
            String line;
            int planCount = 0;
            ArrayList<String[]> allPlans = new ArrayList<>();
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    allPlans.add(parts);
                }
            }
            System.out.println("\n===== All Your Travel Plans =====");
            if (allPlans.isEmpty()) {
                System.out.println("No travel plans found.");
            } else {
                for (int i = 0; i < allPlans.size(); i++) {
                    String[] plan = allPlans.get(i);
                    System.out.println(" Plan " + (i + 1) + ":");
                    System.out.println("Season: " + plan[0]);
                    System.out.println("Year: " + plan[1]);
                    System.out.println("Continent: " + plan[2]);
                    System.out.println();
                }
                System.out.println("Total number of plans: " + allPlans.size());
            }
            System.out.println("================================");
            
        } catch (IOException e) {
            System.out.println("No saved travel plans found or an error occurred while reading the file.");
        }
    }
}