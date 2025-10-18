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
}
