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

}
