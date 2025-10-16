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

    // Active database connection used to query capital city information
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
    /**
     * Retrieves the top 10 most populated countries within each continent.
     *
     * Only the top 10 countries per continent are included in the result.
     * Each record includes the country's code, name, capital city name and district, region,
     * continent, and total population.
     *
     * @return ArrayList of Country objects containing the top 10 populated countries per continent,
     * ordered by continent and population in descending order.
     */
    public ArrayList<Country> getTop10CountriesByContinentPopulation() {
        // Create a list to store the resulting countries
        ArrayList<Country> countries = new ArrayList<>();

        try {
            // Create a statement to execute the SQL query
            Statement stmt = con.createStatement();

            // SQL query:
            // - Join the country table with city to include capital information
            // - Use ROW_NUMBER() to rank countries within each continent by population
            // - Select only the top 10 countries for each continent
            // - Order by continent and descending population
            String sql =
                    "SELECT Code, CountryName, CapitalName, District, Region, Continent, Population " +
                            "FROM ( " +
                            "   SELECT co.Code, co.Name AS CountryName, c.Name AS CapitalName, c.District, " +
                            "          co.Region, co.Continent, co.Population, " +
                            "          ROW_NUMBER() OVER (PARTITION BY co.Continent ORDER BY co.Population DESC) AS rn " +
                            "   FROM country co " +
                            "   LEFT JOIN city c ON co.Capital = c.ID " +
                            ") sub " +
                            "WHERE rn <= 10 " +  // Top 10 countries per continent
                            "ORDER BY Continent, Population DESC;";

            // Execute the SQL query and store the result
            ResultSet rset = stmt.executeQuery(sql);

            // Process each row in the result set
            while (rset.next()) {
                Country country = new Country();

                // Map each database column to the corresponding Country object field
                country.setCode(rset.getString("Code"));               // Country code
                country.setName(rset.getString("CountryName"));        // Country name
                country.setCapitalName(rset.getString("CapitalName")); // Capital city name
                country.setDistrict(rset.getString("District"));       // Capital's district
                country.setRegion(rset.getString("Region"));           // Region
                country.setContinent(rset.getString("Continent"));     // Continent
                country.setPopulation(rset.getInt("Population"));      // Population

                // Add the populated Country object to the list
                countries.add(country);
            }

            // Close the result set and statement to free resources
            rset.close();
            stmt.close();

        } catch (SQLException e) {
            // Handle SQL exceptions and display error message
            System.out.println("Failed to get top 10 countries by continent: " + e.getMessage());
        }

        // Return the list of top 10 populated countries per continent
        return countries;
    }

    /**
     * 4. Retrieves the top 50 most populated countries from the database.
     * The method queries the 'country' and 'city' tables, joining them to get each country's capital name,
     * then orders the results by population in descending order and limits the output to 50 countries.
     *
     * @return An ArrayList of Country objects representing the top 50 most populated countries.
     */
    public ArrayList<Country> getTop50CountriesByPopulation() {
        // Create a list to store the retrieved Country objects
        ArrayList<Country> countries = new ArrayList<>();

        try {
            // Create a SQL statement object to execute the query
            Statement stmt = con.createStatement();

            // SQL query to select the top 50 most populated countries
            // Includes country code, name, capital city name (via LEFT JOIN), district, region, continent, and population
            String sql = "SELECT co.Code, co.Name AS CountryName, " +
                    "ci.Name AS CapitalName, ci.District, co.Region, co.Continent, co.Population " +
                    "FROM country co " +
                    "LEFT JOIN city ci ON co.Capital = ci.ID " +
                    "ORDER BY co.Population DESC " +
                    "LIMIT 50;";

            // Execute the SQL query and store the result set
            ResultSet rset = stmt.executeQuery(sql);

            // Loop through the result set and map each row to a Country object
            while (rset.next()) {
                Country country = new Country();
                country.setCode(rset.getString("Code"));                // Set country code
                country.setName(rset.getString("CountryName"));         // Set country name
                country.setCapitalName(rset.getString("CapitalName"));  // Set capital city name
                country.setDistrict(rset.getString("District"));        // Set district of the capital
                country.setRegion(rset.getString("Region"));            // Set region name
                country.setContinent(rset.getString("Continent"));      // Set continent name
                country.setPopulation(rset.getInt("Population"));       // Set population value

                // Add the populated Country object to the list
                countries.add(country);
            }

        } catch (SQLException e) {
            // Handle any SQL errors that occur during the query execution
            System.out.println("Failed to get top 50 countries by population: " + e.getMessage());
        }

        // Return the list of top 50 countries
        return countries;
    }

    /**
     * Retrieves all countries in each region organized by population (largest to smallest)
     * Uses capital city's name for the report.
     * @return A list of countries ordered by region and population
     */
    public ArrayList<Country> getCountriesByRegionPopulationDesc() {
        ArrayList<Country> countries = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            // Join country with city to get capital name
            String sql = "SELECT co.Code, co.Name AS CountryName, ci.Name AS CapitalName, ci.District AS CapitalDistrict, " +
                    "co.Region, co.Continent, co.Population " +
                    "FROM country co " +
                    "LEFT JOIN city ci ON co.Capital = ci.ID " +
                    "ORDER BY co.Region ASC, co.Population DESC;";


            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Country country = new Country();
                country.setCode(rs.getString("Code"));
                country.setName(rs.getString("CountryName"));
                country.setCapitalName(rs.getString("CapitalName"));  // string name of capital
                country.setDistrict(rs.getString("CapitalDistrict"));     // Capital's district
                country.setRegion(rs.getString("Region"));
                country.setContinent(rs.getString("Continent"));
                country.setPopulation(rs.getInt("Population"));
                countries.add(country);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Failed to get countries by region population: " + e.getMessage());
        }

        return countries;
    }

    /**
     * Retrieves the top 5 most populated countries per region.
     * Skips countries with null or zero population.
     *
     * @return List of Country objects containing code, name, capital, district, region, continent, and population
     */
    public ArrayList<Country> getTop5CountriesPerRegion() {
        ArrayList<Country> countries = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            // SQL: Top 5 populated countries per region, skip null/0 population
            String sql = "SELECT Code, CountryName, CapitalName, CapitalDistrict, Region, Continent, Population\n" +
                    "            FROM (\n" +
                    "                SELECT \n" +
                    "                    c.Code,\n" +
                    "                    c.Name AS CountryName,\n" +
                    "                    ci.Name AS CapitalName,\n" +
                    "                    ci.District AS CapitalDistrict,\n" +
                    "                    c.Region,\n" +
                    "                    c.Continent,\n" +
                    "                    c.Population,\n" +
                    "                    ROW_NUMBER() OVER (PARTITION BY c.Region ORDER BY c.Population DESC) AS rn\n" +
                    "                FROM country c\n" +
                    "                LEFT JOIN city ci ON c.Capital = ci.ID\n" +
                    "                WHERE c.Population IS NOT NULL AND c.Population > 0\n" +
                    "            ) AS sub\n" +
                    "            WHERE rn <= 5\n" +
                    "            ORDER BY Region, Population DESC;";

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
            System.out.println("Failed to get top 5 countries per region: " + e.getMessage());
        }

        return countries;
    }

    /**
     * No 29: Retrieves the total population of each country.
     * Produces a list of all countries with their code, capital, district, continent, region, and population.
     * Results are ordered from largest to smallest population.
     *
     * @return List of Country objects containing code, name, capital, district, region, continent, and population.
     */
    public ArrayList<Country> getCountryPopulations() {
        ArrayList<Country> countries = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            // SQL query: include code, join with city for capital info
            String sql =
                    "SELECT co.Code, co.Name AS CountryName, " +
                            "       ci.Name AS CapitalName, ci.District AS CapitalDistrict, " +
                            "       co.Region, co.Continent, co.Population " +
                            "FROM country co " +
                            "LEFT JOIN city ci ON co.Capital = ci.ID " + // get capital details
                            "WHERE co.Name IS NOT NULL AND co.Name <> '' " +
                            "  AND co.Population IS NOT NULL AND co.Population > 0 " +
                            "ORDER BY co.Population DESC;"; // largest population first

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                Country country = new Country();
                country.setCode(rset.getString("Code"));                  // Country code
                country.setName(rset.getString("CountryName"));           // Country name
                country.setCapitalName(rset.getString("CapitalName"));    // Capital city name
                country.setDistrict(rset.getString("CapitalDistrict"));   // Capital district
                country.setRegion(rset.getString("Region"));              // Region
                country.setContinent(rset.getString("Continent"));        // Continent
                country.setPopulation(rset.getInt("Population"));         // Population
                countries.add(country);
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to get country populations: " + e.getMessage());
        }

        return countries;
    }
}
