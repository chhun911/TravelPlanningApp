import java.util.Scanner;

public class Menu {
    public static final String[] DESTINATION_FILE = {
            "DestinationAfrica.csv",
            "DestinationAsia.csv",
            "DestinationEurope.csv",
            "DestinationAmerica.csv",
            "DestinationAustralia.csv"
    };

    private final String userDataPath;
    private final String currentUser;
    private final ListTravelPlan listTravelPlan;
    private final ViewListTravelPlan viewListTravelPlan;
    private final ActivityNotes activityNotes;
    private final ViewActivity viewActivity;
    private final DeleteActivity deleteActivity;
    private final ShareTravelPlan shareTravelPlan;
    private final ViewSharedTravelPlan viewSharedTravelPlan;

    public Menu(String userDataPath, String currentUser) {
        this.userDataPath = userDataPath;
        this.currentUser = currentUser;
        this.listTravelPlan = new ListTravelPlan(userDataPath);
        this.viewListTravelPlan = new ViewListTravelPlan(userDataPath);
        this.activityNotes = new ActivityNotes(userDataPath);
        this.viewActivity = new ViewActivity(userDataPath);
        this.deleteActivity = new DeleteActivity(userDataPath);
        this.shareTravelPlan = new ShareTravelPlan(userDataPath, currentUser);
        this.viewSharedTravelPlan = new ViewSharedTravelPlan();
    }

    public void displayMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View Destinations");
            System.out.println("2. Add Destinations");
            System.out.println("3. Delete Destinations");
            System.out.println("4. Add List Travel Plans");
            System.out.println("5. View List Travel Plans");
            System.out.println("6. Delete List Travel Plans");
            System.out.println("7. Add activity of your plans");
            System.out.println("8. View activity of your plans");
            System.out.println("9. Delete activity of your plans");
            System.out.println("10. Share your plans");
            System.out.println("11. View Shared Travel Plans with Activities");
            System.out.println("0. Exit");

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
                    DeleteDestination deleteDestination = new DeleteDestination(userDataPath);
                    deleteDestination.deleteDestination();
                    break;
                case 4:
                    listTravelPlan.listing(scanner);
                    break;
                case 5:
                    viewListTravelPlan.viewPlans();
                    break;
                case 6:
                    DeleteListTravelPlan deleteListTravelPlan = new DeleteListTravelPlan(userDataPath);
                    deleteListTravelPlan.deleteTravelPlan(scanner);
                    break;
                case 7:
                    activityNotes.addActivityNotes();
                    break;
                case 8:
                    viewActivity.viewActivities();
                    break;
                case 9:
                    deleteActivity.deleteActivity(scanner);
                    break;
                case 10:
                    shareTravelPlan.sharePlan(scanner);
                    break;
                case 11:
                    viewSharedTravelPlan.viewSharedPlans();
                    break;
                case 0:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
