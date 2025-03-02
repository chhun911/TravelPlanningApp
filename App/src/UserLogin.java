import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserLogin {
    public void StartLogin(Scanner scanner) {
        while (true) {
            System.out.print("Please enter your username: ");
            String name = scanner.nextLine().trim();

            if (isValidName(name)) {
                System.out.println("User: " + name);
                System.out.println("Welcome to the Travel Planning App!");
                saveUsernameToCSV(name);  // Save username to CSV
                break;
            } else {
                System.out.println("Invalid name. Please use only letters.");
            }
        }
    }

    private static boolean isValidName(String name) {
        return name.matches("[A-Za-z]+");
    }

    private static void saveUsernameToCSV(String name) {
        File file = new File("user.csv");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(name);  // Write the username to the file
            writer.newLine();     // Add a new line for the next username          
        } catch (IOException e) {
            System.out.println("An error occurred while saving the username.");
            e.printStackTrace();
        }
    }
}
