import java.util.Scanner;

public class Menu {
    public static final String[] DESTINATION_FILE = {
            "DestinationAfrica.csv",
            "DestinationAsia.csv",
            "DestinationEurope.csv",
            "DestinationAmerica.csv",
            "DestinationAustralia.csv"
    };
    private static final ListTravelPlan listTravelPlan = new ListTravelPlan();

    static void displayMenu(Scanner scanner) {
        while (true) {
            System.out.println("Menu:");
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
                    ViewDestination.viewDestinations();
                    break;
                case 2:
                    AddDestination.addDestination(scanner);
                    break;
                case 4:
                    listTravelPlan.listing(scanner);
                    break;
                case 3:
                    System.out.println("not yet implemented");
                    break;
                case 5:
                    ViewListTravelPlan viewListTravelPlan = new ViewListTravelPlan();
                    viewListTravelPlan.viewPlans();
                    break;
                case 6:
                    ActivityNotes note = new ActivityNotes();
                    note.addActivityNotes();
                    break;
                case 7:
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }
}