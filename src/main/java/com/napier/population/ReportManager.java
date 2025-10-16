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
    private final Connection con;
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
        display.clearReportFile();
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

        // All Cities by Population Report
        ArrayList<City> allCities = report.getAllCitiesByPopulation();
        display.writeCityReportToFile(allCities, "No 7 All Cities by Population Report");

        // All Cities by Population Report
        ArrayList<City> citiesByContinent = report.getCitiesByContinentPopulationDesc();
        display.writeCityReportToFile(citiesByContinent, "No 8 Cities by Continent Population Report");

        // Top 50 Cities by Population Report
        ArrayList<City> top50Cities = report.getTop50CitiesByPopulation();
        display.writeCityReportToFile(top50Cities, "No 12 Top 50 Cities by Population Report");

        // Top 10 Cities By Continent Population Report
        ArrayList<City> top10Cities = report.getTop10CitiesByContinentPopulation();
        display.writeCityReportToFile(top10Cities, "No 13 Top 10 Cities By Continent Population Report");

        // The top 5 populated cities in a region.
        ArrayList<City> top5Cities = report.getTop5CitiesByRegionPopulation();
        display.writeCityReportToFile(top5Cities, "No 14 Top 5 Cities by Region Population Report");

        // Cities by Region Population Report
        ArrayList<City> cityByRegion = report.getAllCitiesByRegionPopulationDesc();
        display.writeCityReportToFile(cityByRegion, "No 9 Cities by Region Population Report");

        // Cities by Country Population Report
        ArrayList<City> cityByCountry = report.getAllCitiesByCountryPopulationDesc();
        display.writeCityReportToFile(cityByCountry, "No 10  Cities by Country Population Report");

        // Cities by District Population Report
        ArrayList<City> cityByDistrict = report.getCitiesByDistrictPopulationDesc();
        display.writeCityReportToFile(cityByDistrict, "No 11 Cities by District Population Report");

        // Top 5 Cities By Country Population Report
        ArrayList<City> top5CitiesByCountries = report.getTop5CitiesByCountryPopulation();
        display.writeCityReportToFile(top5CitiesByCountries, "No 15 Top 5 Cities By Country Population Report");

        //16. The topmost populated cities in each district.
        ArrayList<City> topMostCity = report.getTopCityByDistrictPopulation();
        display.writeCityReportToFile(topMostCity, "No 16 Top Most City by District Population Report");

    }

    /**
     * Generates and displays a country report.
     * Currently, a placeholder (no display logic yet).
     * Steps (future implementation):
     * 1. Create CountryReport instance
     * 2. Fetch country data
     * 3. Pass to Display for formatted output
     */
    public void generateCountryReport() {
        CountryReport report = new CountryReport(con);

        // Top 10 Cities By Continent Population Report
        ArrayList<Country> countriesByContinent = report.getCountriesByContinentPopulationDesc();
        display.writeCountryReportToFile(countriesByContinent, "No 2 Countries by Continent Population Report");

        // All Countries by Population Report
        ArrayList<Country> countriesByPopulation = report.getAllCountriesByPopulationDesc();
        display.writeCountryReportToFile(countriesByPopulation, "No 1 All Countries by Population Report");

        // Top 10 Countries by Continent Population Report
        ArrayList<Country> top10Countries = report.getTop10CountriesByContinentPopulation();
        display.writeCountryReportToFile(top10Countries, "No 5 Top 10 Countries by Continent Population Report");

        // Top 50 Countries by Population Report
        ArrayList<Country> top50Countries = report.getTop50CountriesByPopulation();
        display.writeCountryReportToFile(top50Countries, "No 4 Top 50 Countries by Population Report");

        // Countries by Region Population Report
        ArrayList<Country> countriesByRegion = report.getCountriesByRegionPopulationDesc();
        display.writeCountryReportToFile(countriesByRegion, "No 3 Countries by Region Population Report");

        // The top 5 populated countries in a region.
        ArrayList<Country> top5Countries = report.getTop5CountriesPerRegion();
        display.writeCountryReportToFile(top5Countries, "No 6 Top 5 Countries by Population Report");

        // No 29 Total Population of Each Country Report
        ArrayList<Country> countryPopulations = report.getCountryPopulations();
        display.writeCountryReportToFile(countryPopulations, "No 29 Total Population of Each Country Report");
    }

    /**
     * Generates and displays a capital city report.
     * Currently, a placeholder (no display logic yet).
     * Steps (future implementation):
     * 1. Create CapitalCityReport instance
     * 2. Fetch capital city data
     * 3. Pass to Display for formatted output
     */
    public void generateCapitalCityReport() {
        CapitalCityReport report = new CapitalCityReport(con);

        // No 20 Retrieve the list of the top 50 most populated capital cities from the report
        ArrayList<City> top50capitals = report.getTop50CapitalCitiesByPopulation();
        // Write the retrieved data to a file with the given report title
        display.writeCapitalCityReportToFile(top50capitals, "No 20 Top 50 Capital Cities by Population Report");

        // Fetch all capital cities sorted by continent and descending population
        ArrayList<City> capitals = report.getAllCapitalCitiesByContinentPopulationDesc();
        // then write the results to an output file with the given report title.
        display.writeCityReportToFile(capitals, "No 18 Capital City by Continent Population Report");

        // No 19 Retrieve all capital cities in each region organized by population
        ArrayList<City> capitalCitiesByRegion = report.getAllCapitalCitiesByRegionPopulationDesc();
        display.writeCapitalCityReportToFile(capitalCitiesByRegion, "No 19 Capital Cities by Region Population Report");

        // No 22 Top 5 Capital Cities by Region Population
        ArrayList<City> top5CapitalsByRegion = report.getTop5CapitalCitiesByRegion();
        display.writeCapitalCityReportToFile(top5CapitalsByRegion, "No 22 Top 5 Capital Cities by Region Population Report");
    }

    /**
     * Generates and displays a population report.
     * Currently, a placeholder (no display logic yet).
     * Steps (future implementation):
     * 1. Create PopulationReport instance
     * 2. Fetch population data
     * 3. Pass to Display for formatted output
     */
    public void generatePopulationReport() {
        PopulationReport report = new PopulationReport(con);

        // Retrieve the population report for all countries and write it to a file.
        ArrayList<PeoplePopulation> populations = report.getCountryPopulationReport();
        display.writePopulationReportToFile(populations, "Country");

        // Get the total world population and write it to a report file.
        ArrayList<PeoplePopulation> worldPopulations = report.getWorldPopulation();
        display.writeOverallPopulationReportToFile(worldPopulations, "World");


    }

    /**
     * Generates and writes the language population distribution report.
     * This method will use the PopulationReport class to retrieve
     * world language statistics and delegate formatted writing to Display
     */
    public void generateLanguageReport() {
        PopulationReport report = new PopulationReport(con);
    }
}
