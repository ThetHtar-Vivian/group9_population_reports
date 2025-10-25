package com.napier.population;

/**
 * Represents a City entity from the database.
 * Includes both raw city table fields and
 * extended fields used for reporting (e.g., country, continent, region).
 */
public class City {

    // Core attributes
    private String name;
    private String district;
    private int population;
    private String country_name;

    // Extended reporting attributes
    private String continent;
    private String region;

    /**
     * Parameterized constructor for creating a fully initialized City object.
     *
     * @param name          City name
     * @param district      District of the city
     * @param population    Population of the city
     * @param country_name  Country name the city belongs to
     * @param continent     Continent where the city is located
     * @param region        Region within the continent
     */
    public City(String name,
                String country_name, String district, String region,  String continent, int population) {
        this.name = name;
        this.country_name = country_name;
        this.district = district;
        this.region = region;
        this.continent = continent;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getDistrict() {
        return district;
    }

    public int getPopulation() {
        return population;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getContinent() {
        return continent;
    }

    public String getRegion() {
        return region;
    }
}
