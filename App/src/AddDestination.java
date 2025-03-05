import java.io.*;
import java.util.Scanner;

public class AddDestination {
    public static void addDestination(Scanner scanner, String userDataPath) {
        System.out.println("\nSelect the continent:");
        for (int i = 0; i < Menu.DESTINATION_FILE.length; i++) {
            System.out.println((i + 1) + ". " +
                    Menu.DESTINATION_FILE[i].replace("Destination", "").replace(".csv", ""));
        }
        System.out.print("Enter the number corresponding to the continent: ");

        int continentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (continentChoice < 1 || continentChoice > Menu.DESTINATION_FILE.length) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String fileName = userDataPath + File.separator + Menu.DESTINATION_FILE[continentChoice - 1];

        System.out.print("Enter city: ");
        String city = scanner.nextLine().trim();
        System.out.print("Enter country: ");
        String country = scanner.nextLine().trim();
        System.out.print("Enter date: ");
        String date = scanner.nextLine().trim();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(city + "," + country + "," + date);
            bw.newLine();
            System.out.println("Destination added successfully to your personal travel plans.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
