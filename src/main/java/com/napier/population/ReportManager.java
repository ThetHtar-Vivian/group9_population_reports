package com.napier.population;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * ReportManager is responsible for coordinating between
 * report classes (e.g., CityReport, CountryReport, CapitalCityReport, PopulationReport)
 * and the Display class.
 * It acts as a "controller" layer:
 * - Calls the correct report class to fetch data from the database.
 * - Sends the result to Display for formatted output.
 */
public class ReportManager {
    /**
     * Database connection (passed in from DbConnection)
     */
    private final Connection con;

    /**
     * Display utility for printing reports
     */
    private final Display display;

    /**
     * Constructor initializes the ReportManager with
     * a database connection and a Display instance.
     *
     * @param con Active database connection
     */
    public ReportManager(Connection con) {
        this.con = con;
        this.display = new Display();
    }

    /**
     * Generates and displays a city report.
     * Example: Top 10 most populated cities in each continent.
     * Steps:
     * 1. Create CityReport instance
     * 2. Fetch top 10 cities by continent
     * 3. Pass data to Display for formatted output
     */
    public void generateCityReport() {
        // Create CityReport instance to query database
        CityReport report = new CityReport(con);

        System.out.println("\n Generate all cities by population");

        // Fetch all cites by population
        ArrayList<City> allCities = report.getAllCitiesByPopulation();

        // Print the result using the display class
        display.printCityReport(allCities);

        // 8. All the cities in a continent organized by largest population to smallest.
        System.out.println("\nGenerate All Cities by Continent Population Report");
        // Call the report method to retrieve a list of all cities,
        // ordered by continent and sorted by population in descending order within each continent
        ArrayList<City> cities = report.getCitiesByContinentPopulationDesc();
        // Display the retrieved list in a formatted city report
        display.printCityReport(cities);

    }

    /**
     * Generates and displays a country report.
     * Currently, it is a placeholder (no display logic yet).
     * Steps (future implementation):
     * 1. Create CountryReport instance
     * 2. Fetch country data
     * 3. Pass to Display for formatted output
     */
    public void generateCountryReport() {
        CountryReport report = new CountryReport(con);

        System.out.println("\nGenerate All Countries by Continent Population Report");

        // Fetch the list of countries sorted by continent and population
        ArrayList<Country> countries = report.getCountriesByContinentPopulationDesc();

        // Print the result using Display class
        display.printCountryReport(countries);

        System.out.println("\nGenerating All Countries in the World by Population Report");
        ArrayList<Country> countriesByPopulation = report.getAllCountriesByPopulationDesc();
        display.printCountryReport(countriesByPopulation);

        // Generate Top 10 Populated Countries for each continent
        System.out.println("\nGenerating Top 10 Populated Countries per Continent Report");
        ArrayList<Country> top10CountriesPerContinent = report.getTop10CountriesByContinentPopulation();
        display.printCountryReport(top10CountriesPerContinent);

        // 4. The top 50 populated countries in the world.
        System.out.println("\nGenerate Top 50 Most Populated Countries Report");
        // Call the report method to retrieve a list of the top 50 most populated countries
        ArrayList<Country> top50Countries = report.getTop50CountriesByPopulation();
        // Display the retrieved list in a formatted country report
        display.printCountryReport(top50Countries);
    }

    /**
     * Generates and displays a capital city report.
     * Currently, it is a placeholder (no display logic yet).
     * Steps (future implementation):
     * 1. Create CapitalCityReport instance
     * 2. Fetch capital city data
     * 3. Pass to Display for formatted output
     */
    public void generateCapitalCityReport() {
        CapitalCityReport report = new CapitalCityReport(con);
    }

    /**
     * Generates and displays a population report.
     * Currently, it is a placeholder (no display logic yet).
     * Steps (future implementation):
     * 1. Create PopulationReport instance
     * 2. Fetch population data
     * 3. Pass to Display for formatted output
     */
    public void generatePopulationReport() {
        PopulationReport report = new PopulationReport(con);
    }
}
