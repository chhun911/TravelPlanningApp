import java.io.*;
import java.util.*;

public class UserLogin {
    private static final String USER_DATA_DIR = "userdata";
    private String currentUser;
    private Set<String> existingUsers;

    public UserLogin() {
        existingUsers = loadExistingUsers();
        // Create main user data directory if it doesn't exist
        new File(USER_DATA_DIR).mkdir();
    }

    private Set<String> loadExistingUsers() {
        Set<String> users = new HashSet<>();
        File file = new File("users.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    users.add(line.trim());
                }
            } catch (IOException e) {
                System.out.println("Error reading users file.");
                e.printStackTrace();
            }
        }
        return users;
    }

    public String StartLogin(Scanner scanner) {
        while (true) {
            System.out.println("\n1. Login with existing username");
            System.out.println("2. Create new username");
            System.out.print("Choose an option (1 or 2): ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                return loginExistingUser(scanner);
            } else if (choice.equals("2")) {
                return createNewUser(scanner);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private String loginExistingUser(Scanner scanner) {
        while (true) {
            System.out.print("Enter your username: ");
            String name = scanner.nextLine().trim();

            if (existingUsers.contains(name)) {
                this.currentUser = name;
                createUserDirectory(name); // Ensure directory exists
                System.out.println("Welcome back, " + name + "!");
                return name;
            } else {
                System.out.println("Username not found. Please try again or create a new account.");
                return StartLogin(scanner);
            }
        }
    }

    private String createNewUser(Scanner scanner) {
        while (true) {
            System.out.print("Create your username (letters only): ");
            String name = scanner.nextLine().trim();

            if (existingUsers.contains(name)) {
                System.out.println("Username already exists. Please choose another one.");
                continue;
            }

            if (isValidName(name)) {
                this.currentUser = name;
                createUserDirectory(name);
                saveUsernameToCSV(name);
                existingUsers.add(name);
                System.out.println("Welcome to the Travel Planning App, " + name + "!");
                return name;
            } else {
                System.out.println("Invalid name. Please use only letters.");
            }
        }
    }

    private static boolean isValidName(String name) {
        return name.matches("[A-Za-z]+");
    }

    private void createUserDirectory(String username) {
        String userPath = USER_DATA_DIR + File.separator + username;
        File userDir = new File(userPath);
        if (!userDir.exists()) {
            userDir.mkdir();
            createUserDataFiles(username);
        }
    }

    private void createUserDataFiles(String username) {
        String userPath = USER_DATA_DIR + File.separator + username;
        String[] files = {
                "ListTravelPlan.csv",
                "activities.csv"
        };

        // Create destination files
        for (String destFile : Menu.DESTINATION_FILE) {
            try {
                new File(userPath + File.separator + destFile).createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating destination file: " + destFile);
            }
        }

        // Create other user files
        for (String file : files) {
            try {
                new File(userPath + File.separator + file).createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + file);
            }
        }
    }

    private static void saveUsernameToCSV(String name) {
        try (FileWriter writer = new FileWriter("users.csv", true)) {
            writer.write(name + "\n");
        } catch (IOException e) {
            System.out.println("Error saving username.");
            e.printStackTrace();
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getUserDataPath() {
        return USER_DATA_DIR + File.separator + currentUser;
    }
}