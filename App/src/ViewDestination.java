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
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDate() {
        return date;
    }
}