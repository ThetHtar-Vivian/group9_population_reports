package com.napier.population;

import java.util.ArrayList;

/**
 * Handles displaying reports to the console in a tabular format.
 * Each method is responsible for printing a specific type of report.
 */
public class Display {

    /**
     * Prints a country report in a formatted table.
     * Displays: code, name, capital, district, region, continent, and population.
     *
     * @param countries List of countries to display
     */
    public void printCountryReport(ArrayList<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries to display.");
            return;
        }

        // Print table header
        System.out.printf("%-10s %-55s %-20s %-25s %-30s %-15s %-12s%n",
                "Code", "Country Name", "Capital", "District", "Region", "Continent", "Population");

        // Print each country
        for (Country country : countries) {
            if (country == null) continue;  // Defensive programming: skip null objects

            System.out.printf("%-10s %-55s %-20s %-25s %-30s %-15s %-12d%n",
                    country.getCode(),
                    country.getName(),
                    country.getCapitalName(), // Display capital city name (from City table)
                    country.getDistrict(),
                    country.getRegion(),
                    country.getContinent(),
                    country.getPopulation());
        }
    }

    /**
     * Prints a city report in a formatted table.
     * Displays: city name, country, district, region, continent, and population.
     *
     * @param cities List of cities to display
     */
    public void printCityReport(ArrayList<City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities to display.");
            return;
        }

        // Print table header
        System.out.printf("%-5s %-25s %-40s %-20s %-30s %-15s %-10s%n",
                "ID", "City", "Country", "District", "Region", "Continent", "Population");

        int id = 1; // Auto-increment row ID (for readability)

        // Print each city
        for (City city : cities) {
            if (city == null) continue; // Defensive: skip null objects

            System.out.printf("%-5d %-25s %-40s %-20s %-30s %-15s %-10d%n",
                    id++,
                    city.getName(),
                    city.getCountry_name(),
                    city.getDistrict(),
                    city.getRegion(),
                    city.getContinent(),
                    city.getPopulation());
        }
    }

    /**
     * Prints a capital city report in a formatted table.
     * Displays: capital city name, country, region, continent, and population.
     *
     * @param capitals List of capital cities to display
     */
    public void printCapitalCityReport(ArrayList<City> capitals) {
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital city data available.");
            return;
        }

        // Print table header
        System.out.printf("%-5s %-30s %-45s %-30s %-15s %-12s%n",
                "ID", "City", "Country", "Region", "Continent", "Population");

        int id = 1; // Auto-increment row ID

        // Print each capital city
        for (City capital : capitals) {
            if (capital == null) continue; // Defensive: skip null

            System.out.printf("%-5s %-30s %-45s %-30s %-15s %-12d%n",
                    id++,
                    capital.getName(),
                    capital.getCountry_name(),
                    capital.getRegion(),
                    capital.getContinent(),
                    capital.getPopulation());
        }
    }

    /**
     * Prints the people population report in a tabular format.
     * Displays: total population, city population (with %), and non-city population (with %).
     *
     * @param peoplePopulations List of population objects to display
     * @param level             Label describing the report level (e.g., "World", "Continent", "Country")
     */
    public void printPopulationReport(ArrayList<PeoplePopulation> peoplePopulations, String level) {
        if (peoplePopulations == null || peoplePopulations.isEmpty()) {
            System.out.println("No people populations available.");
            return;
        }

        // Print report title
        System.out.println("===== " + level + " Population Report =====");

        // Print table header
        System.out.printf("%-30s %-20s %-30s %-30s%n",
                level, "Total Population", "City Population (% of total)", "Non-City Population (% of total)");

        // Print each row of population data
        for (PeoplePopulation peoplePopulation : peoplePopulations) {
            if (peoplePopulation == null) continue; // Skip null objects

            System.out.printf("%-30s %-20d %-30s %-30s%n",
                    peoplePopulation.getLevel(),
                    peoplePopulation.getTotalPopulation(),
                    peoplePopulation.getCityPopulation() + " (" +
                            String.format("%.2f", peoplePopulation.getCityPopulationPercentage()) + "%%)",
                    peoplePopulation.getNonCityPopulation() + " (" +
                            String.format("%.2f", peoplePopulation.getNonCityPopulationPercentage()) + "%%)"
            );
        }
    }
}
