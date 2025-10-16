package com.napier.population;

/**
 * Represents a language spoken in a country.
 * Maps to the CountryLanguage table in the database.
 */
public class CountryLanguage {

    // Core attributes from the Language table
    private String country_code;
    private String language;
    private String is_official;
    private double percentage;

    // Extra fields for reporting
    private double world_percentage;

    /**
     * Default constructor for creating an empty CountryLanguage object.
     * Useful for frameworks or for setting values later via setters.
     */
    public CountryLanguage() {
        // Default constructor
    }

    /**
     * Parameterized constructor for creating a fully initialized CountryLanguage object.
     *
     * @param country_code     ISO country code
     * @param language         Name of the language
     * @param is_official      Indicates if the language is official ("T" or "F")
     * @param percentage       Percentage of people who speak this language
     * @param world_percentage Percentage of world population who speak this language
     */
    public CountryLanguage(String country_code, String language, String is_official, double percentage, double world_percentage) {
        this.country_code = country_code;
        this.language = language;
        this.is_official = is_official;
        this.percentage = percentage;
        this.world_percentage = world_percentage;
    }


    // Getters and Setters

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIs_official() {
        return is_official;
    }

    public void setIs_official(String is_official) {
        this.is_official = is_official;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getWorld_percentage() {
        return world_percentage;
    }

    public void setWorld_percentage(double world_percentage) {
        this.world_percentage = world_percentage;
    }

    /**
     * Returns a formatted string representation of the CountryLanguage object.
     *
     * @return A formatted string with language details
     */
    @Override
    public String toString() {
        return String.format(
                "CountryLanguage{country_code='%s', language='%s', is_official='%s', percentage=%.2f, world_percentage=%.2f}",
                country_code, language, is_official, percentage, world_percentage
        );
    }
}
