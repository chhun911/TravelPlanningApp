import java.io.*;
import java.util.*;

public class ViewDestination {
    private final String city;
    private final String country;
    private final String date;

    public ViewDestination(String city, String country, String date) {
        this.city = city;
        this.country = country;
        this.date = date;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getDate() {
        return this.date;
    }

    public static void viewDestinations(String userDataPath) {
        boolean hasDestinations = false;

        for (String fileName : Menu.DESTINATION_FILE) {
            List<ViewDestination> destinations = new ArrayList<>();
            String continent = fileName.replace("Destination", "").replace(".csv", "");
            File destinationFile = new File(userDataPath + File.separator + fileName);

            if (!destinationFile.exists() || destinationFile.length() == 0) {
                continue;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(destinationFile))) {
                String data;
                while ((data = br.readLine()) != null) {
                    String[] pieces = data.split(",");
                    if (pieces.length == 3) {
                        destinations.add(new ViewDestination(
                                pieces[0].trim(),
                                pieces[1].trim(),
                                pieces[2].trim()));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading destinations for " + continent);
                continue;
            }

            if (!destinations.isEmpty()) {
                hasDestinations = true;
                System.out.println("\n" + continent + ":");
                System.out.printf("%-15s %-15s %s\n", "City", "Country", "Date");
                System.out.println("----------------------------------------");

                for (ViewDestination destination : destinations) {
                    System.out.printf("%-15s %-15s %s\n",
                            destination.getCity(),
                            destination.getCountry(),
                            destination.getDate());
                }
            }
        }

        if (!hasDestinations) {
            System.out.println("\nNo destinations found in your travel plans.");
        }
    }
}