import java.util.Scanner;

public class Main {
    private static final UserLogin userLogin = new UserLogin();
    private static final ListTravelPlan listTravelPlan = new ListTravelPlan();
    private static final ViewListTravelPlan viewListTravelPlan = new ViewListTravelPlan();
    private static final ActivityNotes note = new ActivityNotes();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Start user login
        userLogin.StartLogin(scanner);

        // Show the menu after login
        Menu.displayMenu(scanner);

        // List travel plans
        listTravelPlan.listing(scanner);

        // View all travel plans
        viewListTravelPlan.viewPlans();
        // Add activity Of each Days
        note.addActivityNotes();

        scanner.close();
    }
}