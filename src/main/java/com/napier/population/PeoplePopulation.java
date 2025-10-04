package com.napier.population;

/**
 * Represents population data at a certain level (e.g., world, continent, region, or country).
 * This class stores total population, city population, and non-city population
 * along with their percentages.
 */
public class PeoplePopulation {

    private String level;
    private long totalPopulation;
    private long cityPopulation;
    private long nonCityPopulation;
    private double cityPopulationPercentage;
    private double nonCityPopulationPercentage;

    /**
     * Constructor to initialize population data.
     *
     * @param level             Name of the level (e.g., "World", "Continent: Asia", "Country: France")
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

    // Getters and setters

    /**
     * @return the level of aggregation (World, Continent, Region, Country)
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level sets the aggregation level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the total population
     */
    public long getTotalPopulation() {
        return totalPopulation;
    }

    /**
     * @param totalPopulation sets the total population
     */
    public void setTotalPopulation(long totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    /**
     * @return the city population
     */
    public long getCityPopulation() {
        return cityPopulation;
    }

    /**
     * @param cityPopulation sets the city population
     */
    public void setCityPopulation(long cityPopulation) {
        this.cityPopulation = cityPopulation;
    }

    /**
     * @return the non-city (rural) population
     */
    public long getNonCityPopulation() {
        return nonCityPopulation;
    }

    /**
     * @param nonCityPopulation sets the non-city (rural) population
     */
    public void setNonCityPopulation(long nonCityPopulation) {
        this.nonCityPopulation = nonCityPopulation;
    }

    /**
     * @return percentage of population living in cities
     */
    public double getCityPopulationPercentage() {
        return cityPopulationPercentage;
    }

    /**
     * @param cityPopulationPercentage sets percentage of city population
     */
    public void setCityPopulationPercentage(double cityPopulationPercentage) {
        this.cityPopulationPercentage = cityPopulationPercentage;
    }

    /**
     * @return percentage of population not living in cities
     */
    public double getNonCityPopulationPercentage() {
        return nonCityPopulationPercentage;
    }

    /**
     * @param nonCityPopulationPercentage sets percentage of non-city population
     */
    public void setNonCityPopulationPercentage(double nonCityPopulationPercentage) {
        this.nonCityPopulationPercentage = nonCityPopulationPercentage;
    }
}
