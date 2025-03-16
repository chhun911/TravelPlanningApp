import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EditDestinations {
    private final String userDataPath;

    public EditDestinations(String userDataPath) {
        this.userDataPath = userDataPath;
    }

    public void editDestinations(Scanner scanner) {
        System.out.println("\nSelect the continent to edit destinations:");
        for (int i = 0; i < Menu.DESTINATION_FILE.length; i++) {
            System.out.println((i + 1) + ". " + Menu.DESTINATION_FILE[i].replace("Destination", "").replace(".csv", ""));
        }
        System.out.print("Enter the number corresponding to the continent: ");

        int continentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (continentChoice < 1 || continentChoice > Menu.DESTINATION_FILE.length) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String fileName = userDataPath + File.separator + Menu.DESTINATION_FILE[continentChoice - 1];
        List<String> destinations = loadDestinations(fileName);

        if (destinations.isEmpty()) {
            System.out.println("No destinations found in " + fileName);
            return;
        }

        displayDestinations(destinations);
        System.out.print("Enter the number of the destination to edit: ");
        int destinationNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (destinationNumber < 1 || destinationNumber > destinations.size()) {
            System.out.println("Invalid number. Please try again.");
            return;
        }

        String[] parts = destinations.get(destinationNumber - 1).split(",");
        System.out.print("Enter new city (current: " + parts[0] + "): ");
        String newCity = scanner.nextLine().trim();
        System.out.print("Enter new country (current: " + parts[1] + "): ");
        String newCountry = scanner.nextLine().trim();
        System.out.print("Enter new date (current: " + parts[2] + "): ");
        String newDate = scanner.nextLine().trim();

        destinations.set(destinationNumber - 1, newCity + "," + newCountry + "," + newDate);
        saveDestinations(fileName, destinations);
        System.out.println("Destination updated successfully!");
    }

    private List<String> loadDestinations(String fileName) {
        List<String> destinations = new ArrayList<>();
        try {
            destinations = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.out.println("Error reading destinations: " + e.getMessage());
        }
        return destinations;
    }

    private void displayDestinations(List<String> destinations) {
        System.out.println("\nCurrent destinations:");
        for (int i = 0; i < destinations.size(); i++) {
            String[] parts = destinations.get(i).split(",");
            if (parts.length >= 3) {
                System.out.printf("%d. City: %s, Country: %s, Date: %s%n", i + 1, parts[0], parts[1], parts[2]);
            }
        }
    }

    private void saveDestinations(String fileName, List<String> destinations) {
        try {
            Files.write(Paths.get(fileName), destinations);
        } catch (IOException e) {
            System.out.println("Error saving destinations: " + e.getMessage());
        }
    }
}
