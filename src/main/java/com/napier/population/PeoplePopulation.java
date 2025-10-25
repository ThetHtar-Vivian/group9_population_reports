package com.napier.population;

/**
 * Represents population data at a certain level (e.g., world, continent, region, or country).
 * This class stores total population, city population, and non-city population
 * along with their percentages.
 */
public class PeoplePopulation {

    // Core attributes from the table
    private long totalPopulation;
    private long cityPopulation;
    private long nonCityPopulation;

    // Extra fields for reporting
    private String level;
    private double cityPopulationPercentage;
    private double nonCityPopulationPercentage;

    /**
     * Constructor to initialize population data.
     *
     * @param level             Name of the level (e.g., "World", "Continent", "Country")
     * @param totalPopulation   Total population
     * @param cityPopulation    Population living in cities
     * @param nonCityPopulation Population not living in cities
     */
    public PeoplePopulation(String level, long totalPopulation, long cityPopulation, long nonCityPopulation) {
        this.level = level;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
        this.nonCityPopulation = nonCityPopulation;

        // Avoid division by zero when calculating percentages
        if (totalPopulation > 0) {
            this.cityPopulationPercentage = (cityPopulation * 100.0) / totalPopulation;
            this.nonCityPopulationPercentage = (nonCityPopulation * 100.0) / totalPopulation;
        } else {
            this.cityPopulationPercentage = 0.0;
            this.nonCityPopulationPercentage = 0.0;
        }
    }

    /**
     * Constructs a PeoplePopulation object with only the total population data.
     *
     * @param level             the aggregation level
     * @param totalPopulation   the total population for this level
     */
    public PeoplePopulation(String level, long totalPopulation) {
        this.level = level;
        this.totalPopulation = totalPopulation;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public long getCityPopulation() {
        return cityPopulation;
    }

    public long getNonCityPopulation() {
        return nonCityPopulation;
    }

    public String getLevel() {
        return level;
    }

    public double getCityPopulationPercentage() {
        return cityPopulationPercentage;
    }

    public double getNonCityPopulationPercentage() {
        return nonCityPopulationPercentage;
    }
}
