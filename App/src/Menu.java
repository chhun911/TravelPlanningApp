import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Menu {
    public static final String DESTINATION_FILE = "Destination.csv";
    private static final ListTravelPlan listTravelPlan = new ListTravelPlan();

    static void displayMenu(Scanner scanner) {
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. View Destinations");
            System.out.println("2. Add Destinations");
            System.out.println("3. Delete Destinations");
            System.out.println("4. List Travel Plans");
            System.out.println("5. Exit");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewDestinations();
                    break;
                case 2:
                    addDestination(scanner);
                    break;
                case 4:
                    listTravelPlan.listing(scanner);
                    break;
                case 3:
                    deleteDestination();
                    break;
                case 5:
                    System.out.println("Exiting the application. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void addDestination(Scanner scanner) {
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter country: ");
        String country = scanner.nextLine();
        System.out.print("Enter date: ");
        String date = scanner.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DESTINATION_FILE, true))) {
            bw.write(city + "," + country + "," + date);
            bw.newLine();
            System.out.println("Destination added successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void viewDestinations() {
        List<ViewDestination> destinations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DESTINATION_FILE))) {
            String data;
            while ((data = br.readLine()) != null) {
                String[] pieces = data.split(",");
                if (pieces.length == 3) {
                    destinations.add(new ViewDestination(pieces[0].trim(), pieces[1].trim(), pieces[2].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        // Print header
        System.out.printf("%-15s %-15s %s\n", "City", "Country", "Date");
        System.out.println("----------------------------------------");

        // Print formatted city-country-date pairs
        for (ViewDestination destination : destinations) {
            System.out.printf("%-15s %-15s %s\n", destination.getCity(), destination.getCountry(),
                    destination.getDate());
        }
    }

    public static void deleteDestination() {
        System.out.println("Delete functionality not yet implemented.");
    }
}