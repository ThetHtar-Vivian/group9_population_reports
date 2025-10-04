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

}
