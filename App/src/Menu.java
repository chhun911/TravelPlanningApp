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
    private final ListTravelPlan listTravelPlan;
    private final ViewListTravelPlan viewListTravelPlan;
    private final ActivityNotes activityNotes;
    private final ViewActivity viewActivity;
    private final DeleteActivity deleteActivity;
    private final ShareTravelPlan shareTravelPlan;
    private final ViewSharedTravelPlan viewSharedTravelPlan;
    private final CalendarReminder calendarReminder;

    public Menu(String userDataPath, String currentUser) {
        this.userDataPath = userDataPath;
        this.listTravelPlan = new ListTravelPlan(userDataPath);
        this.viewListTravelPlan = new ViewListTravelPlan(userDataPath);
        this.activityNotes = new ActivityNotes(userDataPath);
        this.viewActivity = new ViewActivity(userDataPath);
        this.deleteActivity = new DeleteActivity(userDataPath);
        this.shareTravelPlan = new ShareTravelPlan(userDataPath, currentUser);
        this.viewSharedTravelPlan = new ViewSharedTravelPlan();
        this.calendarReminder = new CalendarReminder(userDataPath);
    }

    public void displayMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Destinations");
            System.out.println("2. Delete Destinations");
            System.out.println("3. Edit Destinations");
            System.out.println("4. View Destinations");
            System.out.println("5. Add List Travel Plans");
            System.out.println("6. Delete List Travel Plans");
            System.out.println("7. Edit List Travel Plans");
            System.out.println("8. View List Travel Plans");
            System.out.println("9. Add activity of your plans");
            System.out.println("10. Delete activity of your plans");
            System.out.println("11. Edit activity of your plans");
            System.out.println("12. View activity of your plans");
            System.out.println("13. Share your plans");
            System.out.println("14. View Shared Travel Plans with Activities");
            System.out.println("15. See How many days you have left for your trip");
            System.out.println("0. Exit");

            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    AddDestination.addDestination(scanner, userDataPath);
                    break;
                case 2:
                    DeleteDestination deleteDestination = new DeleteDestination(userDataPath);
                    deleteDestination.deleteDestination();
                    break;
                case 3:
                    EditDestinations editDestinations = new EditDestinations(userDataPath);
                    editDestinations.editDestinations(scanner);
                    break;
                case 4:
                    ViewDestination.viewDestinations(userDataPath);
                    break;
                case 5:
                    listTravelPlan.listing(scanner);
                    break;
                case 6:
                    DeleteListTravelPlan deleteListTravelPlan = new DeleteListTravelPlan(userDataPath);
                    deleteListTravelPlan.deleteTravelPlan(scanner);
                    break;
                case 7:
                    EditListTravelPlan editListTravelPlan = new EditListTravelPlan(userDataPath);
                    editListTravelPlan.editTravelPlans(scanner);
                    break;
                case 8:
                    viewListTravelPlan.viewPlans();
                    break;
                case 9:
                    activityNotes.addActivityNotes();
                    break;
                case 10:
                    deleteActivity.deleteActivity(scanner);
                    break;
                case 11:
                    activityNotes.editActivityNotes();
                    break;
                case 12:
                    viewActivity.viewActivities();
                    break;
                case 13:
                    shareTravelPlan.sharePlan(scanner);
                    break;
                case 14:
                    viewSharedTravelPlan.viewSharedPlans();
                    break;
                case 15:
                    calendarReminder.displayCountdown();
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