package com.napier.population;

/**
 * Represents a City entity from the database.
 * Includes both raw city table fields and
 * extended fields used for reporting (e.g., country, continent, region).
 */
public class City {

    // Core attributes
    private String name;
    private String country_code;
    private String district;
    private int population;
    private String country_name;

    // Extended reporting attributes
    private String continent;
    private String region;

    /**
     * Default constructor for creating an empty City object.
     * Useful for frameworks or when setting values manually later.
     */
    public City() {
        // Default constructor
    }

    /**
     * Parameterized constructor for creating a fully initialized City object.
     *
     * @param name          City name
     * @param country_code  Country code of the city
     * @param district      District of the city
     * @param population    Population of the city
     * @param country_name  Country name the city belongs to
     * @param continent     Continent where the city is located
     * @param region        Region within the continent
     */
    public City(String name, String country_code, String district, int population,
                String country_name, String continent, String region) {
        this.name = name;
        this.country_code = country_code;
        this.district = district;
        this.population = population;
        this.country_name = country_name;
        this.continent = continent;
        this.region = region;
    }

    // Getters and Setters

    /**
     * @return City name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name City name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Country code
     */
    public String getCountry_code() {
        return country_code;
    }

    /**
     * @param country_code Country code
     */
    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    /**
     * @return District name
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district District name
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return Population of the city
     */
    public int getPopulation() {
        return population;
    }

    /**
     * @param population Population of the city
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * @return Country name
     */
    public String getCountry_name() {
        return country_name;
    }

    /**
     * @param country_name Country name
     */
    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    /**
     * @return Continent name
     */
    public String getContinent() {
        return continent;
    }

    /**
     * @param continent Continent name
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * @return Region name
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region Region name
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Returns a string representation of the City object.
     *
     * @return A formatted string with city details
     */
    @Override
    public String toString() {
        return String.format(
                "City{name='%s', country_code='%s', district='%s', population=%d, country_name='%s', continent='%s', region='%s'}",
                name, country_code, district, population, country_name, continent, region
        );
    }
}
