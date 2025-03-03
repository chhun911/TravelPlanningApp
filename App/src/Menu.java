import java.util.Scanner;

public class Menu {
    public static final String[] DESTINATION_FILE = {
            "DestinationAfrica.csv",
            "DestinationAsia.csv",
            "DestinationEurope.csv",
            "DestinationAmerica.csv",
            "DestinationAustralia.csv"
    };

    private final ListTravelPlan listTravelPlan;
    private final String userDataPath;
    private final ViewListTravelPlan viewListTravelPlan;
    private final ActivityNotes activityNotes;

    public Menu(String userDataPath) {
        this.userDataPath = userDataPath;
        this.listTravelPlan = new ListTravelPlan(userDataPath);
        this.viewListTravelPlan = new ViewListTravelPlan(userDataPath);
        this.activityNotes = new ActivityNotes(userDataPath);
    }

    public void displayMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View Destinations");
            System.out.println("2. Add Destinations");
            System.out.println("3. Delete Destinations");
            System.out.println("4. List Travel Plans");
            System.out.println("5. View Travel Plans");
            System.out.println("6. Add activity of your plans");
            System.out.println("7. Exit");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    ViewDestination.viewDestinations(userDataPath);
                    break;
                case 2:
                    AddDestination.addDestination(scanner, userDataPath);
                    break;
                case 3:
                    System.out.println("not yet implemented");
                    break;
                case 4:
                    listTravelPlan.listing(scanner);
                    break;
                case 5:
                    viewListTravelPlan.viewPlans();
                    break;
                case 6:
                    activityNotes.addActivityNotes();
                    break;
                case 7:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}