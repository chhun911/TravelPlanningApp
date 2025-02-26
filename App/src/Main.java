import java.util.Scanner;

public class Main {
    private static final UserLogin userLogin = new UserLogin();
    private static final ListTravelPlan listTravelPlan = new ListTravelPlan();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Start user login
        userLogin.StartLogin(scanner);

        // Show the menu after login
        Menu.displayMenu(scanner);

        // List travel plans
        listTravelPlan.listing(scanner);

        scanner.close();
    }
}