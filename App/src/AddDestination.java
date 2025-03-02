import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AddDestination {
    public static void addDestination(Scanner scanner) {
        System.out.println("Select the continent:");
        for (int i = 0; i < Menu.DESTINATION_FILE.length; i++) {
            System.out
                    .println((i + 1) + ". " + Menu.DESTINATION_FILE[i].replace("Destination", "").replace(".csv", ""));
        }
        System.out.print("Enter the number corresponding to the continent: ");
        int continentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (continentChoice < 1 || continentChoice > Menu.DESTINATION_FILE.length) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String fileName = Menu.DESTINATION_FILE[continentChoice - 1];

        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter country: ");
        String country = scanner.nextLine();
        System.out.print("Enter date: ");
        String date = scanner.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(city + "," + country + "," + date);
            bw.newLine();
            System.out.println("Destination added successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}