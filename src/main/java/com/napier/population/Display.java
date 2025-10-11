package com.napier.population;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles displaying reports to the console in a tabular format.
 * Each method is responsible for printing a specific type of report.
 */
public class Display {

    private static final String OUTPUT_FILE = "reports/PopulationReports.txt";

    /**
     * Prints a country report in a formatted table.
     * Displays: code, name, capital, district, region, continent, and population.
     *
     * @param countries List of countries to display
     */
    public void writeCountryReportToFile(ArrayList<Country> countries, String title) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) { // append mode
            writer.write("===== " + title + " =====\n");
            writer.write(String.format("%-10s %-55s %-20s %-25s %-30s %-15s %-12s%n",
                    "Code", "Country Name", "Capital", "District", "Region", "Continent", "Population"));

            for (Country country : countries) {
                writer.write(String.format("%-10s %-55s %-20s %-25s %-30s %-15s %-12d%n",
                        country.getCode(),
                        country.getName(),
                        country.getCapitalName(),
                        country.getDistrict(),
                        country.getRegion(),
                        country.getContinent(),
                        country.getPopulation()));
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing country report: " + e.getMessage());
        }
    }

    /**
     * Prints a city report in a formatted table.
     * Displays: city name, country, district, region, continent, and population.
     *
     * @param cities List of cities to display
     */
    public void writeCityReportToFile(ArrayList<City> cities, String title) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("===== " + title + " =====\n");
            writer.write(String.format("%-5s %-25s %-40s %-20s %-30s %-15s %-10s%n",
                    "ID", "City", "Country", "District", "Region", "Continent", "Population"));

            int id = 1;
            for (City city : cities) {
                writer.write(String.format("%-5d %-25s %-40s %-20s %-30s %-15s %-10d%n",
                        id++,
                        city.getName(),
                        city.getCountry_name(),
                        city.getDistrict(),
                        city.getRegion(),
                        city.getContinent(),
                        city.getPopulation()));
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing city report: " + e.getMessage());
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
    public void writePopulationReportToFile(ArrayList<PeoplePopulation> peoplePopulations, String level) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write("===== " + level + " Population Report =====\n");
            writer.write(String.format("%-30s %-20s %-30s %-30s%n",
                    level, "Total Population", "City Population (% of total)", "Non-City Population (% of total)"));

            for (PeoplePopulation pp : peoplePopulations) {
                writer.write(String.format("%-30s %-20d %-30s %-30s%n",
                        pp.getLevel(),
                        pp.getTotalPopulation(),
                        pp.getCityPopulation() + " (" + String.format("%.2f", pp.getCityPopulationPercentage()) + "%)",
                        pp.getNonCityPopulation() + " (" + String.format("%.2f", pp.getNonCityPopulationPercentage()) + "%)"
                ));
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing population report: " + e.getMessage());
        }
    }

    /**
     * Optional: Clear previous reports before writing new ones
     */
    public void clearReportFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write(""); // overwrite with empty content
        } catch (IOException e) {
            System.out.println("Error clearing report file: " + e.getMessage());
        }
    }
}
