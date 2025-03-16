import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class DeleteDestination {
    private String userDataPath;

    public DeleteDestination(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    public void deleteDestination() {
        try (Scanner scanner = new Scanner(System.in)) {
            // Show continent options
            System.out.println("\nSelect continent to delete from:");
            System.out.println("1. Africa");
            System.out.println("2. Asia");
            System.out.println("3. Europe");
            System.out.println("4. America");
            System.out.println("5. Australia");

            System.out.print("Enter continent number (1-5): ");
            int continentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (continentChoice < 1 || continentChoice > 5) {
                System.out.println("Invalid continent choice.");
                return;
            }

            String fileName = Menu.DESTINATION_FILE[continentChoice - 1];
            String filePath = userDataPath + File.separator + fileName;

            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println("No destinations found in " + fileName);
                    return;
                }

                // Read all destinations
                List<String> destinations = Files.readAllLines(Paths.get(filePath));

                if (destinations.isEmpty()) {
                    System.out.println("No destinations found in " + fileName);
                    return;
                }

                // Display current destinations
                System.out.println("\nCurrent destinations:");
                System.out.println("Line  City            Country         Date");
                System.out.println("----------------------------------------");

                for (int i = 0; i < destinations.size(); i++) {
                    String[] parts = destinations.get(i).split(",");
                    if (parts.length >= 3) {
                        System.out.printf("%-5d %-15s %-15s %s%n",
                                i + 1,
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim());
                    }
                }

                // Get line number to delete
                System.out.print("\nEnter the line number to delete: ");
                int lineNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (lineNumber < 1 || lineNumber > destinations.size()) {
                    System.out.println("Invalid line number. Please try again.");
                    return;
                }

                // Remove the selected destination
                destinations.remove(lineNumber - 1);

                // Write back to file
                Files.write(Paths.get(filePath), destinations);
                System.out.println("Destination deleted successfully from " + fileName + "!");

            } catch (IOException e) {
                System.out.println("Error accessing file: " + e.getMessage());
            }
        }
    }
}