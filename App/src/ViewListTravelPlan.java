    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;


    public class ViewListTravelPlan {
    public void showall()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("ListTravelPlan.csv"))) {
            String line;
            ArrayList<String[]> AllPlans = new ArrayList<>();
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length >= 3){
                    AllPlans.add(parts);

                }
            }
            System.out.println("\n========== YOUR TRAVEL PLANS ==========");
                    if (AllPlans.isEmpty()) {
                        System.out.println("No travel plans found. Please add some plans first!");
                    } else {
                        System.out.println("Total travel plans: " + AllPlans.size() + "\n");
                        
                        for (int i = 0; i < AllPlans.size(); i++) {
                            String[] plan = AllPlans.get(i);
                            System.out.println("Plan " + (i + 1) + ":");
                            System.out.println("Season: " + plan[0]);
                            System.out.println("Year: " + plan[1]);
                            System.out.println("Continent: " + plan[2]);
                            System.out.println();
                        }
                    }
                    System.out.println("=======================================");
                    
                } catch (IOException e) {
                    System.out.println("\n========== YOUR TRAVEL PLANS ==========");
                    System.out.println("No saved travel plans found ");
                    System.out.println("Please make sure ListTravelPlan.csv exists and is accessible.");
                    System.out.println("=======================================");
                }
            }
        public void viewPlans() {
            System.out.println("\nViewing all your travel plans...");
            showall();
            System.out.println("Press Enter to return to the main menu...");
        }
    }



