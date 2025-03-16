import java.io.*;
import java.util.ArrayList;

public class ViewListTravelPlan {
    private final String userDataPath;

    public ViewListTravelPlan(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    private void showAll() {
        String filePath = userDataPath + File.separator + "ListTravelPlan.csv";
        File travelPlanFile = new File(filePath);

        if (!travelPlanFile.exists()) {
            printNoPlansMessage("No travel plans file found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(travelPlanFile))) {
            String line;
            ArrayList<String[]> allPlans = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    allPlans.add(parts);
                }
            }

            System.out.println("\n========== YOUR TRAVEL PLANS ==========");

            if (allPlans.isEmpty()) {
                System.out.println("No travel plans found. Please add some plans first!");
            } else {
                System.out.println("Total travel plans: " + allPlans.size() + "\n");

                for (int i = 0; i < allPlans.size(); i++) {
                    String[] plan = allPlans.get(i);
                    System.out.println("Plan " + (i + 1) + ":");
                    System.out.println("Season: " + plan[0]);
                    System.out.println("Year: " + plan[1]);
                    System.out.println("Month: " + plan[2]);
                    System.out.println("Day: " + plan[3]);
                    System.out.println("Continent: " + plan[4]);
                    System.out.println();
                }
            }
            System.out.println("=======================================");

        } catch (IOException e) {
            printNoPlansMessage("Error reading travel plans file.");
        }
    }

    private void printNoPlansMessage(String errorMessage) {
        System.out.println("\n========== YOUR TRAVEL PLANS ==========");
        System.out.println(errorMessage);
        System.out.println("=======================================");
    }

    public void viewPlans() {
        System.out.println("\nViewing your personal travel plans...");
        showAll();
        System.out.println("Press Enter to return to the main menu...");
    }

    public void daysLeft() {

        throw new UnsupportedOperationException("Unimplemented method 'daysLeft'");
    }
}