package com.napier.population;

/**
 * Represents a Country entity from the world database.
 * Includes both raw country table fields and extra fields
 * used for reporting (e.g., district, capital name).
 */
public class Country {

    // Core attributes
    private String code;
    private String name;
    private String continent;
    private String region;
    private int population;

    // Extra fields for reporting (not directly in the Country table)
    private String district;
    private String capitalName;

    /**
     * Parameterized constructor for creating a fully initialized Country object.
     *
     * @param code             ISO country code
     * @param name             Country name
     * @param continent        Continent name
     * @param region           Region name
     * @param population       Population of the country
     * @param district         District (for reporting)
     * @param capitalName      Capital city name (for reporting)
     */
    public Country(String code, String name, String capitalName, String district, String region, String continent, int population) {
        this.code = code;
        this.name = name;
        this.capitalName = capitalName;
        this.district = district;
        this.region = region;
        this.continent = continent;
        this.population = population;
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getRegion() {
        return region;
    }

    public int getPopulation() {
        return population;
    }

    public String getDistrict() {
        return district;
    }

    public String getCapitalName() {
        return capitalName;
    }
}
