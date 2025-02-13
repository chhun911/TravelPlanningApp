package Plan;

import java.util.Scanner;

public class TravelMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();  
            int choice = getChoice(scanner);  

            switch (choice) {
                case 1:
                    System.out.println("Search for Destination...");
                    break;
                case 2:
                    System.out.println("List Travel Plans...");
                    break;
                case 3:
                    System.out.println("Add Destination...");
                    break;
                case 4:
                    System.out.println("Create Travel Plan...");
                    break;
                case 5:
                    System.out.println("Share Plan...");
                    break;
                case 6:
                    System.out.println("See Travel Plans...");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;  
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static int getChoice(Scanner scanner) { 
        System.out.print("Choose an option: ");
        while (!scanner.hasNextInt()) { 
            System.out.println("Invalid input. Enter a number.");
            scanner.next(); 
        return scanner.nextInt();
    }

    public static void displayMenu() {
        System.out.println("\n--- Travel Planning App Menu ---");
        System.out.println("1. Search for Destination (City)");
        System.out.println("2. List Travel Plans");
        System.out.println("3. Add Destination");
        System.out.println("4. Create Travel Plan");
        System.out.println("5. Share Plan");
        System.out.println("6. See Travel Plans");
        System.out.println("0. Exit");
    }
}
