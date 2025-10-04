package com.napier.population;

/**
 * Represents a language spoken in a country.
 * Maps to the CountryLanguage table in the database.
 */
public class CountryLanguage {

    private String country_code;
    private String language;
    private String is_official;
    private double percentage;

    // ----------------- Getters and Setters -----------------

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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getIs_official() {
        return is_official;
    }

    public void setIs_official(String is_official) {
        this.is_official = is_official;
    }
}
