import java.util.Scanner;

public class Main {
    private static final UserLogin userLogin = new UserLogin();

    public static void main(String[] args) {
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.displayWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        try {
            String currentUser = userLogin.StartLogin(scanner);
            System.out.println("Logged in as: " + currentUser);
            String userDataPath = userLogin.getUserDataPath();
            Menu menu = new Menu(userDataPath, currentUser);
            menu.displayMenu(scanner);
        } finally {
            scanner.close();
        }
    }
}