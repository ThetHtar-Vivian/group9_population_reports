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
}
