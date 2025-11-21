package com.napier.population;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

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
    Logger log = LoggerFactory.getLogger(ReportManager.class);

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
        try {
            CityReport report = new CityReport(con);

            // All Cities by Population Report
            List<City> allCities = report.getAllCitiesByPopulation();
            display.writeCityReportToFile(allCities, "No_7_All_Cities_by_Population_Report.md");

            // All Cities by Population Report
            List<City> citiesByContinent = report.getCitiesByContinentPopulationDesc();
            display.writeCityReportToFile(citiesByContinent, "No_8_Cities_by_Continent_Population_Report.md");

            // Top 50 Cities by Population Report
            List<City> top50Cities = report.getTop50CitiesByPopulation();
            display.writeCityReportToFile(top50Cities, "No_12_Top_50_Cities_by_Population_Report.md");

            // Top 10 Cities By Continent Population Report
            List<City> top10Cities = report.getTop10CitiesByContinentPopulation();
            display.writeCityReportToFile(top10Cities, "No_13_Top_10_Cities_By_Continent_Population_Report.md");

            // The top 5 populated cities in a region
            List<City> top5Cities = report.getTop5CitiesByRegionPopulation();
            display.writeCityReportToFile(top5Cities, "No_14_Top_5_Cities_by_Region_Population_Report.md");

            // Cities by Region Population Report
            List<City> cityByRegion = report.getAllCitiesByRegionPopulationDesc();
            display.writeCityReportToFile(cityByRegion, "No_9_Cities_by_Region_Population_Report.md");

            // Cities by Country Population Report
            List<City> cityByCountry = report.getAllCitiesByCountryPopulationDesc();
            display.writeCityReportToFile(cityByCountry, "No_10_Cities_by_Country_Population_Report.md");

            // Cities by District Population Report
            List<City> cityByDistrict = report.getCitiesByDistrictPopulationDesc();
            display.writeCityReportToFile(cityByDistrict, "No_11_Cities_by_District_Population_Report.md");

            // Top 5 Cities By Country Population Report
            List<City> top5CitiesByCountries = report.getTop5CitiesByCountryPopulation();
            display.writeCityReportToFile(top5CitiesByCountries, "No_15_Top_5_Cities_By_Country_Population_Report.md");

            // Topmost populated cities in each district
            List<City> topMostCity = report.getTopCityByDistrictPopulation();
            display.writeCityReportToFile(topMostCity, "No_16_Top_Most_City_by_District_Population_Report.md");
        } catch (Exception e) {
            log.debug("Failed to get cities reports: ", e);
        }
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
        try {
            CountryReport report = new CountryReport(con);

            // Countries by Continent Population Report
            List<Country> countriesByContinent = report.getCountriesByContinentPopulationDesc();
            display.writeCountryReportToFile(countriesByContinent, "No_2_Countries_by_Continent_Population_Report.md");

            // All Countries by Population Report
            List<Country> countriesByPopulation = report.getAllCountriesByPopulationDesc();
            display.writeCountryReportToFile(countriesByPopulation, "No_1_All_Countries_by_Population_Report.md");

            // Top 10 Countries by Continent Population Report
            List<Country> top10Countries = report.getTop10CountriesByContinentPopulation();
            display.writeCountryReportToFile(top10Countries, "No_5_Top_10_Countries_by_Continent_Population_Report.md");

            // Top 50 Countries by Population Report
            List<Country> top50Countries = report.getTop50CountriesByPopulation();
            display.writeCountryReportToFile(top50Countries, "No_4_Top_50_Countries_by_Population_Report.md");

            // Countries by Region Population Report
            List<Country> countriesByRegion = report.getCountriesByRegionPopulationDesc();
            display.writeCountryReportToFile(countriesByRegion, "No_3_Countries_by_Region_Population_Report.md");

            // Top 5 Countries by Population Report
            List<Country> top5Countries = report.getTop5CountriesPerRegion();
            display.writeCountryReportToFile(top5Countries, "No_6_Top_5_Countries_by_Population_Report.md");
        } catch (Exception e) {
            log.debug("Failed to get countries reports: ", e);
        }
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
        try {
            CapitalCityReport report = new CapitalCityReport(con);

            // No 17 All Capital Cities by Population Report
            List<City> allCapitalCities = report.getAllCapitalCitiesByPopulationDesc();
            display.writeCapitalCityReportToFile(allCapitalCities, "No_17_All_Capital_Cities_by_Population_Report.md");

            // No 18 Capital City by Continent Population Report
            List<City> capitals = report.getAllCapitalCitiesByContinentPopulationDesc();
            display.writeCapitalCityReportToFile(capitals, "No_18_Capital_City_by_Continent_Population_Report.md");

            // No 19 Capital Cities by Region Population Report
            List<City> capitalCitiesByRegion = report.getAllCapitalCitiesByRegionPopulationDesc();
            display.writeCapitalCityReportToFile(capitalCitiesByRegion, "No_19_Capital_Cities_by_Region_Population_Report.md");

            // No 20 Top 50 Capital Cities by Population Report
            List<City> top50capitals = report.getTop50CapitalCitiesByPopulation();
            display.writeCapitalCityReportToFile(top50capitals, "No_20_Top_50_Capital_Cities_by_Population_Report.md");

            // No 21 Top 10 Capital Cities by Continent Population Report
            List<City> top10Capitals = report.getTop10CapitalCitiesByContinentPopulation();
            display.writeCapitalCityReportToFile(top10Capitals, "No_21_Top_10_Capital_Cities_by_Continent_Population_Report.md");

            // No 22 Top 5 Capital Cities by Region Population Report
            List<City> top5CapitalsByRegion = report.getTop5CapitalCitiesByRegion();
            display.writeCapitalCityReportToFile(top5CapitalsByRegion, "No_22_Top_5_Capital_Cities_by_Region_Population_Report.md");
        } catch (Exception e) {
            log.debug("Failed to get capital cities reports: ", e);
        }
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
        try {
            PopulationReport report = new PopulationReport(con);

            // Retrieve the population report for all countries and write it to a file.
            List<PeoplePopulation> populations = report.getCountryPopulationReport();
            display.writePopulationReportToFile(populations, "Country", "No_25_Country_Population_Report.md");

            // Get the total world population and write it to a report file.
            List<PeoplePopulation> worldPopulations = report.getWorldPopulation();
            display.writeOverallPopulationReportToFile(worldPopulations, "World",  "No_26_World_Population_Report.md");

            // No 29 Total world population
            List<PeoplePopulation> totalCountryPopulations = report.getTotalPopulationPerCountry();
            display.writeOverallPopulationReportToFile(totalCountryPopulations, "Country", "No_29_Total_Population_Report.md");

            // Call the method in PopulationReport to get total population per continent
            List<PeoplePopulation> continentTotalPopulations = report.getContinentTotalPopulation();
            // Write the population report to file using Display
            display.writeOverallPopulationReportToFile(continentTotalPopulations, "Continent", "No_27_Continent_Total_Population_Report.md");

            // No 31 the total city population and write it to a report file.
            List<PeoplePopulation> cityTotalPopulations = report.getCityTotalPopulation();
            display.writeOverallPopulationReportToFile(cityTotalPopulations, "City", "No_31_Country_Total_Population_Report.md");

            // No 23 Continent Population Report
            List<PeoplePopulation> continentPopulations = report.getContinentPopulationReport();
            display.writePopulationReportToFile(continentPopulations, "Continent", "No_23_Continent_Population_Report.md");

            // No 30 get District Total Population
            List<PeoplePopulation> districtTotalPopulations = report.getDistrictTotalPopulation();
            display.writeOverallPopulationReportToFile(districtTotalPopulations, "District", "No_30_District_Population_Report.md");

            // No 24 Call the method in PopulationReport to get population data per region
            List<PeoplePopulation> regionPopulations = report.getRegionPopulationReport();
            // Write the population report to file using Display
            display.writePopulationReportToFile(regionPopulations, "Region", "No_24_Region_Population_Report.md");

            // No 28 Call the method in PopulationReport to get total population per region
            List<PeoplePopulation> regionTotalPopulations = report.getRegionTotalPopulation();
            // Write the population report to file using Display
            display.writeOverallPopulationReportToFile(regionTotalPopulations, "Region",  "No_28_Region_Total_Population_Report.md");
        } catch (Exception e) {
            log.debug("Failed to get population reports: ", e);
        }
    }

    /**
     * Generates and writes the language population distribution report.
     * This method will use the PopulationReport class to retrieve
     * world language statistics and delegate formatted writing to Display
     */
    public void generateLanguageReport() {
        PopulationReport report = new PopulationReport(con);

        // No 32 the languages from the greatest number to smallest, including the percentage of the world population
        List<CountryLanguage> countryLanguages = report.getWorldLanguageReport();
        display.writeLanguageReportToFile(countryLanguages, "No_32_World_Language_Report.md");
    }
}
