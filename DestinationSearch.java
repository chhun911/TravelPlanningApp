package Plan;

import java.util.Scanner;

public class DestinationSearch {
    private String city;
    private String country;

    public DestinationSearch(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DestinationSearch[] cities = {
            new DestinationSearch("Paris", "France"),
            new DestinationSearch("Bangkok", "Thailand"),
            new DestinationSearch("Barcelona", "Spain"),
            new DestinationSearch("Siem Reap", "Cambodia"),
            new DestinationSearch("Rome", "Italy"),
            new DestinationSearch("Toronto", "Canada"),
            new DestinationSearch("Dubai", "UAE"),
            new DestinationSearch("Rio de Janeiro", "Brazil"),
            new DestinationSearch("Tokyo", "Japan"),
            new DestinationSearch("Sydney", "Australia")
        };

        System.out.print("Enter City to Search: ");
        String userInput = scanner.nextLine().trim();

        DestinationSearch foundCity = searchCity(cities, userInput);
        if (foundCity != null) {
            System.out.println("Found: " + foundCity);
        } else {
            System.out.println("City not found.");
        }
 
        scanner.close();
    }

    public static DestinationSearch searchCity(DestinationSearch[] cities, String cityName) {
        for (DestinationSearch city : cities) {
            if (city.getCity().equalsIgnoreCase(cityName)) {  
                return city;
            }
        }
        return null;  
}
