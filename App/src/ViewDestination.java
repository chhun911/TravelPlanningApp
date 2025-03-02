import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewDestination {
    private String city;
    private String country;
    private String date;

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

    public static void viewDestinations() {
        for (String fileName : Menu.DESTINATION_FILE) {
            List<ViewDestination> destinations = new ArrayList<>();
            String continent = fileName.replace("Destination", "").replace(".csv", "");

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String data;
                while ((data = br.readLine()) != null) {
                    String[] pieces = data.split(",");
                    if (pieces.length == 3) {
                        destinations.add(new ViewDestination(pieces[0].trim(), pieces[1].trim(), pieces[2].trim()));
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + fileName);
                e.printStackTrace();
            }

            // Print continent header
            System.out.println(continent + ":");
            System.out.printf("%-15s %-15s %s\n", "City", "Country", "Date");
            System.out.println("----------------------------------------");

            // Print formatted city-country-date pairs
            for (ViewDestination destination : destinations) {
                System.out.printf("%-15s %-15s %s\n", destination.getCity(), destination.getCountry(),
                        destination.getDate());
            }
            System.out.println(); // Add a blank line between continents
        }
    }
}