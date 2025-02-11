import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Menu(); // Display the menu first
            int choice = getChoice(); // Then get the user's choice
            switch (choice) {
                case 1:
                    System.out.println("Search for Destination");
                    break;
                case 2:
                    System.out.println("List Travel Plans");
                    break;
                case 3:
                    System.out.println("Add Destination");
                    break;
                case 4:
                    System.out.println("Create Travel Plan");
                    break;
                case 5:
                    System.out.println("Share Plan");
                    break;
                case 6:
                    System.out.println("See Travel Plans");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;

            }
            scanner.close();
        }
    }

    public static int getChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void Menu() {
        System.out.println("\n--- Travel Planning App Menu ---");
        System.out.println("1. Search for Destination");
        System.out.println("2. List Travel Plans");
        System.out.println("3. Add Destination");
        System.out.println("4. Create Travel Plan");
        System.out.println("5. Share Plan");
        System.out.println("6. See Travel Plans");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }
}
