package com.napier.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Handles generating country-related reports from the database.
 * This class interacts with the database using the provided Connection
 * and retrieves country data for use in reports.
 */
public class CountryReport {

    /**
     * Database connection object used to query the database
     */
    private Connection con;

    /**
     * Constructor initializes the CountryReport with an active database connection.
     *
     * @param con Active database connection (from DbConnection)
     */
    public CountryReport(Connection con) {
        this.con = con;
    }

    /**
     * 2. All the countries in a continent organized by largest population to smallest.
     * Retrieves all countries grouped by continent, ordered by population descending.
     * Uses capital city's district for the report.
     * @return List of Country objects containing code, name, capital, district, region, continent, and population.
     */
    public ArrayList<Country> getCountriesByContinentPopulationDesc() {
        ArrayList<Country> countries = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            // Join country with city to get capital name and capital district
            String sql = "SELECT c.Code, c.Name AS CountryName, ci.Name AS CapitalName, ci.District AS CapitalDistrict, " +
                    "c.Region, c.Continent, c.Population " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "ORDER BY c.Continent, c.Population DESC;";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                Country country = new Country();

                country.setCode(rset.getString("Code"));
                country.setName(rset.getString("CountryName"));
                country.setCapitalName(rset.getString("CapitalName"));      // Capital city name
                country.setDistrict(rset.getString("CapitalDistrict"));     // Capital's district
                country.setRegion(rset.getString("Region"));
                country.setContinent(rset.getString("Continent"));
                country.setPopulation(rset.getInt("Population"));

                countries.add(country);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get countries by continent population: " + e.getMessage());
        }

        return countries;
    }
    /**
     * Retrieves a list of all countries in the world ordered by population (from largest to smallest).
     * Each record includes the country's code, name, capital city name and district,
     * region, continent, and total population. The data is fetched by joining the
     * country and city tables to include capital city details.
     * @return ArrayList of Country objects sorted in descending order by population.
     */
    public ArrayList<Country> getAllCountriesByPopulationDesc() {
        // Create a list to hold all countries retrieved from the database
        ArrayList<Country> countries = new ArrayList<>();

        try {
            // Create a statement to execute SQL queries
            Statement stmt = con.createStatement();

            // SQL query:
            // - Select country details including name, code, region, continent, and population
            // - Left join with city table to include capital city details
            // - Sort the results by population in descending order
            String sql =
                    "SELECT co.Code, co.Name AS CountryName, " +
                            "       c.Name AS CapitalName, " +
                            "       c.District, " +
                            "       co.Region, co.Continent, co.Population " +
                            "FROM country co " +
                            "LEFT JOIN city c ON co.Capital = c.ID " +   // Join to include capital details
                            "ORDER BY co.Population DESC;";              // Order by population (largest first)

            // Execute query and obtain result set
            ResultSet rset = stmt.executeQuery(sql);

            // Process each record in the result set
            while (rset.next()) {
                Country country = new Country();

                // Map each field from the query result to the Country object
                country.setCode(rset.getString("Code"));               // Country code
                country.setName(rset.getString("CountryName"));        // Country name
                country.setCapitalName(rset.getString("CapitalName")); // Capital city name
                country.setDistrict(rset.getString("District"));       // Capital's district
                country.setRegion(rset.getString("Region"));           // Region of the country
                country.setContinent(rset.getString("Continent"));     // Continent name
                country.setPopulation(rset.getInt("Population"));      // Total population

                // Add populated Country object to the list
                countries.add(country);
            }
        } catch (SQLException e) {
            // Handle SQL exceptions and log an error message
            System.out.println("Failed to get countries by population: " + e.getMessage());
        }

        // Return the complete list of countries sorted by population
        return countries;
    }

}
