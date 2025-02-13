package Plan;

import java.util.Scanner;

public class UserLogin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter your username: ");
            String name = scanner.nextLine().trim();  

            if (isValidName(name)) {
                System.out.println("User: " + name);
                System.out.println("Welcome to the Travel Planning App!");
                break;
            } else {
                System.out.println("Invalid name. Please use only letters.");
            }
        }
        scanner.close();
    }

    private static boolean isValidName(String name) {
        return name.matches("[A-Za-z]+");  
    }
}
