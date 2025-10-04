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

}
