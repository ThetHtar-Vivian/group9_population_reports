package com.napier.population;

/**
 * Represents a language spoken in a country.
 * Maps to the CountryLanguage table in the database.
 */
public class CountryLanguage {

    // Core attributes from the Language table
    private String language;
    private double percentage;

    // Extra fields for reporting
    private double world_percentage;

    /**
     * Parameterized constructor for creating a fully initialized CountryLanguage object.
     *
     * @param language         Name of the language
     * @param percentage       Percentage of people who speak this language
     * @param world_percentage Percentage of world population who speak this language
     */
    public CountryLanguage(String language,double percentage, double world_percentage) {
        this.language = language;
        this.percentage = percentage;
        this.world_percentage = world_percentage;
    }

    public String getLanguage() {
        return language;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getWorld_percentage() {
        return world_percentage;
    }
}
