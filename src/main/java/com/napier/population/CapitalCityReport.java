package com.napier.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Handles generating reports specifically for capital cities.
 * This class interacts with the database using the provided Connection
 * and retrieves capital city data for reporting purposes.
 */
public class CapitalCityReport {


    // Active database connection used to query capital city information
    private Connection con;
    /**
     * Constructor initializes the CapitalCityReport with an active database connection.
     *
     * @param con Active MySQL database connection
     */
    public CapitalCityReport(Connection con) {
        this.con = con;
    }

    /**
     * No 20 Retrieves the top 50 most populated capital cities in the world.
     * It joins the city and country tables where the city's ID matches the country's Capital ID,
     * sorts them by population in descending order, and limits the results to 50.
     *
     * @return A list of City objects containing the top 50 capital cities by population.
     */
    public ArrayList<City> getTop50CapitalCitiesByPopulation() {
        // List to store the top 50 capital cities
        ArrayList<City> capitals = new ArrayList<>();

        try {
            // Create a SQL statement
            Statement stmt = con.createStatement();

            // SQL query:
            // - Joins city and country tables where the city's ID = country's Capital ID
            // - Selects relevant details: city name, country name, district, region, continent, and population
            // - Orders by population (descending)
            // - Limits to 50 results
            String sql = "SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, " +
                    "co.Region, co.Continent, ci.Population " +
                    "FROM city ci " +
                    "JOIN country co ON ci.ID = co.Capital " +
                    "ORDER BY ci.Population DESC " +
                    "LIMIT 50;";

            // Execute the SQL query
            ResultSet rset = stmt.executeQuery(sql);

            // Loop through the result set and create City objects
            while (rset.next()) {
                City city = new City();
                city.setName(rset.getString("CityName"));             // Set city name
                city.setCountry_name(rset.getString("CountryName"));  // Set country name
                city.setDistrict(rset.getString("District"));         // Set district name
                city.setRegion(rset.getString("Region"));             // Set region name
                city.setContinent(rset.getString("Continent"));       // Set continent name
                city.setPopulation(rset.getInt("Population"));        // Set population

                // Add the city to the list
                capitals.add(city);
            }

        } catch (SQLException e) {
            // Handle any SQL errors
            System.out.println("Failed to get top 50 capital cities by population: " + e.getMessage());
        }

        // Return the list of top 50 capital cities
        return capitals;
    }

    /**
     * Retrieves all capital cities ordered by continent (A–Z) and population (highest first).
     * Each result includes the city's name, country, district, region, continent, and population.
     *
     * @return a list of capital cities sorted by continent and descending population
     */
    public ArrayList<City> getAllCapitalCitiesByContinentPopulationDesc() {
        ArrayList<City> capitals = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            // SQL query: Include District to match report columns
            String sql = "SELECT " +
                    "ci.Name AS CityName, " +
                    "co.Name AS CountryName, " +
                    "ci.District, " +
                    "co.Region, " +
                    "co.Continent, " +
                    "ci.Population " +
                    "FROM city ci " +
                    "JOIN country co ON ci.ID = co.Capital " +
                    "ORDER BY co.Continent ASC, ci.Population DESC;";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                City capital = new City();
                capital.setName(rset.getString("CityName"));
                capital.setCountry_name(rset.getString("CountryName"));
                capital.setDistrict(rset.getString("District"));
                capital.setRegion(rset.getString("Region"));
                capital.setContinent(rset.getString("Continent"));
                capital.setPopulation(rset.getInt("Population"));
                capitals.add(capital);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get capital cities by continent population: " + e.getMessage());
        }

        return capitals;
    }

}
