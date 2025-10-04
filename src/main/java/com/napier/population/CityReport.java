package com.napier.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Provides methods for generating city reports from the database.
 * Each method queries the database and returns a list of City objects
 * for use in reports.
 */
public class CityReport {

    /**
     * Active database connection used to execute queries
     */
    private Connection con;

    /**
     * Constructor initializes the CityReport with an active DB connection.
     *
     * @param con Active MySQL database connection
     */
    public CityReport(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all cities in the world ordered by population from largest to smallest.
     *
     * @return A list of City objects sorted by population in descending order.
     */

    public ArrayList<City> getAllCitiesByPopulation() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT \n" +
                    "    c.Name AS CityName,\n" +
                    "    co.Name AS CountryName,\n" +
                    "    c.District,\n" +
                    "    co.Region,\n" +
                    "    co.Continent,\n" +
                    "    c.Population\n" +
                    "FROM city c\n" +
                    "JOIN country co ON c.CountryCode = co.Code\n" +
                    "ORDER BY c.Population DESC;\n";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                City city = new City();
                city.setName(rset.getString("CityName"));
                city.setCountry_name(rset.getString("CountryName"));
                city.setDistrict(rset.getString("District"));
                city.setRegion(rset.getString("Region"));
                city.setContinent(rset.getString("Continent"));
                city.setPopulation(rset.getInt("Population"));
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get cities by population: " + e.getMessage());
        }
        return cities;
    }

    /**
     * 8. Retrieves a list of all cities in the world, organized by continent and
     * sorted by city population in descending order within each continent.
     * The method joins the 'city' and 'country' tables to include relevant
     * information such as country name, district, region, and continent.
     *
     * @return An ArrayList of City objects containing city details ordered by continent and population.
     */
    public ArrayList<City> getCitiesByContinentPopulationDesc() {
        // Create a list to store the retrieved City objects
        ArrayList<City> cities = new ArrayList<>();

        try {
            // Create a SQL statement object to execute the query
            Statement stmt = con.createStatement();

            // SQL query:
            // - Joins 'city' and 'country' tables using the country code
            // - Selects city name, country name, district, region, continent, and population
            // - Orders the result first by continent (alphabetically),
            //   then by city population in descending order within each continent
            String sql = "SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, " +
                    "co.Region, co.Continent, ci.Population " +
                    "FROM city ci " +
                    "JOIN country co ON ci.CountryCode = co.Code " +
                    "ORDER BY co.Continent, ci.Population DESC;";

            // Execute the SQL query and store the result set
            ResultSet rset = stmt.executeQuery(sql);

            // Loop through the result set and map each row to a City object
            while (rset.next()) {
                City city = new City();
                city.setName(rset.getString("CityName"));             // Set city name
                city.setCountry_name(rset.getString("CountryName"));  // Set the name of the country the city belongs to
                city.setDistrict(rset.getString("District"));         // Set the city's district
                city.setRegion(rset.getString("Region"));             // Set the region name
                city.setContinent(rset.getString("Continent"));       // Set the continent name
                city.setPopulation(rset.getInt("Population"));        // Set the city's population

                // Add the populated City object to the list
                cities.add(city);
            }

        } catch (SQLException e) {
            // Handle any SQL errors that occur during the query execution
            System.out.println("Failed to get cities by continent population: " + e.getMessage());
        }

        // Return the list of cities organized by continent and sorted by population
        return cities;
    }

    /**
     * Retrieves the top 50 most populated cities in the world.
     *
     * This method queries the database, joining the city and country tables
     * to get additional information such as country name, region, and continent.
     * Results are ordered by city population in descending order and limited to 50 entries.
     *
     * @return ArrayList of City objects containing city name, country, district, region, continent, and population
     */
    public ArrayList<City> getTop50CitiesByPopulation() {
        ArrayList<City> cities = new ArrayList<>();

        try {
            // Create a statement object to execute the SQL query
            Statement stmt = con.createStatement();

            // SQL query explanation:
            // - Select city name, country name, district, region, continent, and population
            // - Join city table with country table using CountryCode
            // - Order the results by population descending
            // - Limit to top 50 cities
            String sql =
                    "SELECT city.Name AS City, " +
                            "       country.Name AS Country, " +
                            "       city.District, " +
                            "       country.Region, " +
                            "       country.Continent, " +
                            "       city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "ORDER BY city.Population DESC " +
                            "LIMIT 50;";

            // Execute the query
            ResultSet rset = stmt.executeQuery(sql);

            // Process each row in the result set
            while (rset.next()) {
                City city = new City();

                city.setName(rset.getString("City"));              // Set city name
                city.setCountry_name(rset.getString("Country"));   // Set country name
                city.setDistrict(rset.getString("District"));      // Set district
                city.setRegion(rset.getString("Region"));          // Set region
                city.setContinent(rset.getString("Continent"));    // Set continent
                city.setPopulation(rset.getInt("Population"));     // Set population

                // Add city to the list
                cities.add(city);
            }
        } catch (SQLException e) {
            // Print error message if query fails
            System.out.println("Failed to get city report: " + e.getMessage());
        }

        // Return the list of top 50 cities
        return cities;
    }

    /**
     * Retrieves the top 10 most populated cities for each continent.
     * SQL uses ROW_NUMBER() window function to rank cities within each continent.
     * Only the top 10 cities per continent are returned, ordered by continent and population descending.
     *
     * @return ArrayList of City objects containing name, country, district, region, continent, and population
     */
    public ArrayList<City> getTop10CitiesByContinentPopulation() {
        ArrayList<City> cities = new ArrayList<>();

        try {
            // Create a statement to execute SQL query
            Statement stmt = con.createStatement();

            // SQL query:
            // - Join city and country tables
            // - Partition by continent and order by city population descending
            // - Use ROW_NUMBER() to select top 10 per continent
            String sql = "SELECT CityName, CountryName, District, Region, Continent, Population " + "FROM ( " + "    SELECT c.Name AS CityName, " + "           co.Name AS CountryName, " + "           c.District, " + "           co.Region, " + "           co.Continent, " + "           c.Population, " + "           ROW_NUMBER() OVER (PARTITION BY co.Continent ORDER BY c.Population DESC) AS rn " + "    FROM city c " + "    JOIN country co ON c.CountryCode = co.Code " + ") sub " + "WHERE rn <= 10 " + "ORDER BY Continent, Population DESC;";

            // Execute query and get results
            ResultSet rset = stmt.executeQuery(sql);

            // Process each row in the result set
            while (rset.next()) {
                City city = new City();
                city.setName(rset.getString("CityName"));            // Set city name
                city.setCountry_name(rset.getString("CountryName")); // Set country name
                city.setDistrict(rset.getString("District"));        // Set district
                city.setRegion(rset.getString("Region"));            // Set region
                city.setContinent(rset.getString("Continent"));      // Set continent
                city.setPopulation(rset.getInt("Population"));       // Set population

                cities.add(city); // Add city to list
            }
        } catch (SQLException e) {
            // Print error message if query fails
            System.out.println("Failed to get top 10 cities by continent: " + e.getMessage());
        }

        return cities; // Return list of top cities
    }
}
