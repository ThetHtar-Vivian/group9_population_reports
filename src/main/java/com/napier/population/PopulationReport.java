package com.napier.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * PopulationReport is responsible for generating
 * population-related reports from the database.
 */
public class PopulationReport {

    // Active database connection used to query capital city information
    private Connection con;

    /**
     * Constructor initializes the PopulationReport with a database connection.
     *
     * @param con Active connection to the database
     */
    public PopulationReport(Connection con) {
        this.con = con;
    }

    /**
     * Gets the population of people, people living in cities, and people not living in cities in each country.
     *
     * @return List of PeoplePopulation objects representing each country's population data.
     */
    public ArrayList<PeoplePopulation> getCountryPopulationReport() {
        ArrayList<PeoplePopulation> populations = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            // SQL: Calculates total, city, and non-city population per country
            String sql =
                    "SELECT " +
                            "    co.Name AS CountryName, " +
                            "    co.Population AS TotalPopulation, " +
                            "    COALESCE(SUM(ci.Population), 0) AS CityPopulation, " +
                            "    (co.Population - COALESCE(SUM(ci.Population), 0)) AS NonCityPopulation " +
                            "FROM country co " +
                            "LEFT JOIN city ci ON co.Code = ci.CountryCode " +
                            "GROUP BY co.Code, co.Name, co.Population " +
                            "ORDER BY co.Name;";

            ResultSet rset = stmt.executeQuery(sql);

            // Process each record
            while (rset.next()) {
                String level = rset.getString("CountryName");
                long total = rset.getLong("TotalPopulation");
                long cityPop = rset.getLong("CityPopulation");
                long nonCityPop = rset.getLong("NonCityPopulation");

                PeoplePopulation pop = new PeoplePopulation(level, total, cityPop, nonCityPop);
                populations.add(pop);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get population by country: " + e.getMessage());
        }

        return populations;
    }

    /**
     * Gets the total world population by summing all country populations.
     *
     * @return a list with one PeoplePopulation object labeled "World"
     */
    public ArrayList<PeoplePopulation> getWorldPopulation() {
        ArrayList<PeoplePopulation> worldPopulations = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            // ✅ SQL: Calculate total world population
            String sql = "SELECT SUM(Population) AS WorldPopulation FROM country;";

            ResultSet rset = stmt.executeQuery(sql);

            // ✅ Process result
            if (rset.next()) {
                long total = rset.getLong("WorldPopulation");
                PeoplePopulation pop = new PeoplePopulation("World", total);
                worldPopulations.add(pop);
            }

            rset.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Failed to get world population: " + e.getMessage());
        }

        return worldPopulations;
    }

    /**
     * Gets the total population of each country.
     * This report lists every country with its total population.
     *
     * @return List of PeoplePopulation objects representing total population per country.
     */
    public ArrayList<PeoplePopulation> getTotalPopulationPerCountry() {
        ArrayList<PeoplePopulation> populations = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            // SQL: Get total population per country, excluding null or empty names
            String sql = "SELECT Name AS CountryName, Population AS TotalPopulation " +
                    "FROM country " +
                    "WHERE Name IS NOT NULL AND Name <> '' " +
                    "ORDER BY Population DESC;";

            ResultSet rset = stmt.executeQuery(sql);

            // Process each record
            while (rset.next()) {
                String level = rset.getString("CountryName");
                long total = rset.getLong("TotalPopulation");

                // Only include if population > 0
                if (total > 0) {
                    PeoplePopulation pop = new PeoplePopulation(level, total);
                    populations.add(pop);
                }
            }

            rset.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Failed to get total population per country: " + e.getMessage());
        }

        return populations;
    }
    /**
     * No 27 Retrieves the total population of each continent.
     *
     * @return ArrayList<PeoplePopulation> list of continents with their total population
     */
    public ArrayList<PeoplePopulation> getContinentTotalPopulation() {
        ArrayList<PeoplePopulation> continentPopulations = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            String sql = "SELECT Continent, SUM(Population) AS TotalPopulation " +
                    "FROM country " +
                    "GROUP BY Continent " +
                    "ORDER BY TotalPopulation DESC;";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                String continentName = rset.getString("Continent");
                long totalPopulation = rset.getLong("TotalPopulation");

                PeoplePopulation pp = new PeoplePopulation(continentName, totalPopulation);
                continentPopulations.add(pp);
            }

            rset.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Failed to get continent total population: " + e.getMessage());
        }

        return continentPopulations;
    }
    /**
     * Retrieves the total population for each district within each country.
     * Groups the city data by both CountryCode and District to avoid merging
     * districts with the same name in different countries.
     *
     * @return ArrayList of PeoplePopulation objects containing district population data.
     */
    public ArrayList<PeoplePopulation> getDistrictTotalPopulation() {
        // Initialize list to store population data by district
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();

        try {
            // Create SQL statement object
            Statement stmt = con.createStatement();

            // SQL query to calculate total population per district within each country
            String sql = "SELECT ci.CountryCode, ci.District AS name, " +
                    "SUM(ci.Population) AS totalPopulation " +
                    "FROM city ci " +
                    "GROUP BY ci.CountryCode, ci.District " +
                    "ORDER BY ci.CountryCode, ci.District";

            // Execute the query
            ResultSet rset = stmt.executeQuery(sql);

            // Loop through results and populate PeoplePopulation objects
            while (rset.next()) {
                peoplePopulations.add(new PeoplePopulation(
                        rset.getString("name"),           // District name
                        rset.getLong("totalPopulation")   // Total population
                ));
            }
        } catch (Exception e) {
            // Print exception message if any error occurs
            System.out.println(e.getMessage());
        }

        // Return final list of district populations
        return peoplePopulations;
    }

    /**
     * Retrieves a population report grouped by continent.
     * For each continent, it calculates:
     * - Total population (from all countries in that continent)
     * - Total city population (sum of populations of all cities in that continent)
     * - Non-city population (difference between total and city populations)
     *
     * @return ArrayList of PeoplePopulation objects containing population data per continent.
     */
    public ArrayList<PeoplePopulation> getContinentPopulationReport() {
        // Create a list to store population results
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();

        try {
            // Create SQL statement object for query execution
            Statement stmt = con.createStatement();

            // SQL query to calculate continent-level populations
            String sql = "SELECT c.Continent AS name, " +
                    "SUM(c.Population) AS totalPopulation, " +
                    "SUM(ci.Population) AS cityPopulation, " +
                    "(SUM(c.Population) - SUM(ci.Population)) AS nonCityPopulation " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON ci.CountryCode = c.Code " +
                    "GROUP BY c.Continent";

            // Execute query and store result set
            ResultSet rset = stmt.executeQuery(sql);

            // Iterate through results and store in the list
            while (rset.next()) {
                peoplePopulations.add(new PeoplePopulation(
                        rset.getString("name"),                 // Continent name
                        rset.getLong("totalPopulation"),       // Total population
                        rset.getLong("cityPopulation"),        // City population
                        rset.getLong("nonCityPopulation")      // Non-city population
                ));
            }
        } catch (Exception e) {
            // Print exception message if something goes wrong
            System.out.println(e.getMessage());
        }

        // Return final list of continent population data
        return peoplePopulations;
    }

    /**
     * Retrieves the total population for each city.
     * Lists all cities in ascending order of population.
     *
     * @return ArrayList of PeoplePopulation objects containing city population data.
     */
    public ArrayList<PeoplePopulation> getCityTotalPopulation() {
        // Create a list to store city-level population data
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();

        try {
            // Create SQL statement object
            Statement stmt = con.createStatement();

            // SQL query to retrieve population of each city ordered by population ascending
            String sql = "SELECT ci.Name AS name, " +
                    "ci.Population AS totalPopulation " +
                    "FROM city ci " +
                    "ORDER BY ci.Population;";

            // Execute query and retrieve results
            ResultSet rset = stmt.executeQuery(sql);

            // Populate each record into PeoplePopulation objects
            while (rset.next()) {
                peoplePopulations.add(new PeoplePopulation(
                        rset.getString("name"),           // City name
                        rset.getLong("totalPopulation")   // City population
                ));
            }
        } catch (SQLException e) {
            // Print detailed message if SQL query fails
            System.out.println("Failed to get city population: " + e.getMessage());
        }

        // Return all city population data
        return peoplePopulations;
    }

    /**
     * Retrieves the world language report for the five most spoken languages:
     * Chinese, English, Hindi, Spanish, and Arabic.
     * The report includes:
     * - Language name
     * - Total number of speakers worldwide
     * - Percentage of world population speaking that language
     *
     * @return An ArrayList of CountryLanguage objects containing language statistics
     */
    public ArrayList<CountryLanguage> getWorldLanguageReport() {
        // Initialize list to store language report data
        ArrayList<CountryLanguage> languages = new ArrayList<>();

        try {
            // Create a Statement object to execute SQL queries
            Statement stmt = con.createStatement();

            // SQL query to calculate total speakers and world percentage for selected languages
            String sql = "SELECT " +
                    "cl.Language AS language, " +
                    "SUM(c.Population * (cl.Percentage / 100)) AS totalSpeakers, " +
                    "ROUND(SUM(c.Population * (cl.Percentage / 100)) / " +
                    "(SELECT SUM(Population) FROM country) * 100, 2) AS worldPercentage " +
                    "FROM countrylanguage cl " +
                    "JOIN country c ON cl.CountryCode = c.Code " +
                    "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') " +
                    "GROUP BY cl.Language " +
                    "ORDER BY totalSpeakers DESC;";

            // Execute the SQL query and obtain results
            ResultSet rset = stmt.executeQuery(sql);

            // Loop through the result set to populate CountryLanguage objects
            while (rset.next()) {
                CountryLanguage cl = new CountryLanguage();

                // Set the language name
                cl.setLanguage(rset.getString("language"));

                // Store total speakers temporarily in the 'percentage' field
                // (optional: could create a separate field for total speakers)
                cl.setPercentage(rset.getDouble("totalSpeakers"));

                // Set world population percentage for this language
                cl.setWorld_percentage(rset.getDouble("worldPercentage"));

                // Add the CountryLanguage object to the list
                languages.add(cl);
            }

            // Close ResultSet and Statement to free resources
            rset.close();
            stmt.close();
        } catch (Exception e) {
            // Print any errors encountered during the database operation
            System.out.println("Error retrieving world language report: " + e.getMessage());
        }

        // Return the list of languages with calculated statistics
        return languages;
    }

    /**
     * No 24 Retrieves population data for each region, including:
     * - Total population
     * - Population living in cities
     * - Population not living in cities
     *
     * @return ArrayList<PeoplePopulation> population data per region
     */
    public ArrayList<PeoplePopulation> getRegionPopulationReport() {
        // List to store population data for each region
        ArrayList<PeoplePopulation> regionPopulations = new ArrayList<>();

        try {
            // Create a SQL statement
            Statement stmt = con.createStatement();

            // SQL query:
            // - Selects region name, total country population, and total city population per region
            // - Uses LEFT JOIN to include countries even if they have no cities
            // - Groups results by region
            String sql = "SELECT co.Region AS RegionName, " +
                    "SUM(co.Population) AS TotalPopulation, " +
                    "SUM(ci.Population) AS CityPopulation " +
                    "FROM country co " +
                    "LEFT JOIN city ci ON ci.CountryCode = co.Code " +
                    "GROUP BY co.Region;";

            // Execute the query
            ResultSet rset = stmt.executeQuery(sql);

            // Loop through each row of the result set
            while (rset.next()) {
                String region = rset.getString("RegionName");             // Get region name
                long totalPop = rset.getLong("TotalPopulation");         // Get total population of the region
                long cityPop = rset.getLong("CityPopulation");           // Get total population living in cities
                long nonCityPop = totalPop - cityPop;                    // Calculate population not living in cities

                // Create a PeoplePopulation object and add it to the list
                PeoplePopulation pop = new PeoplePopulation(region, totalPop, cityPop, nonCityPop);
                regionPopulations.add(pop);
            }

        } catch (SQLException e) {
            // Print error message if query fails
            System.out.println("Failed to get region population report: " + e.getMessage());
        }

        // Return the list of population data per region
        return regionPopulations;
    }

    /**
     * 28 Retrieves the total population of each region.
     *
     * @return ArrayList<PeoplePopulation> list of regions with their total population
     */
    public ArrayList<PeoplePopulation> getRegionTotalPopulation() {
        // List to store total population data per region
        ArrayList<PeoplePopulation> regionPopulations = new ArrayList<>();

        try {
            // Create a SQL statement
            Statement stmt = con.createStatement();

            // SQL query:
            // - Selects region name and sum of population for that region
            // - Groups by region
            // - Orders the results by total population in descending order
            String sql = "SELECT Region, SUM(Population) AS TotalPopulation " +
                    "FROM country " +
                    "GROUP BY Region " +
                    "ORDER BY TotalPopulation DESC;";

            // Execute the query
            ResultSet rset = stmt.executeQuery(sql);

            // Loop through each row of the result set
            while (rset.next()) {
                String regionName = rset.getString("Region");            // Get region name
                long totalPopulation = rset.getLong("TotalPopulation"); // Get total population

                // Create a PeoplePopulation object with only the total population
                PeoplePopulation pp = new PeoplePopulation(regionName, totalPopulation);

                // Add the object to the list
                regionPopulations.add(pp);
            }

        } catch (SQLException e) {
            // Print error message if query fails
            System.out.println("Failed to get region total population: " + e.getMessage());
        }

        // Return the list of total populations per region
        return regionPopulations;
    }

}
