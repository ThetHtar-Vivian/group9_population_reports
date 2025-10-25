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

    private String fileName;

    public Display(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Prints a country report in a formatted table.
     * Displays: code, name, capital, district, region, continent, and population.
     *
     * @param countries List of countries to display
     * @param title     name of the report
     */
    public int writeCountryReportToFile(ArrayList<Country> countries, String title) {
        if (countries == null || countries.isEmpty()) {
            return 0;
        }

        int index = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // append mode
            writer.write("===== " + title + " =====\n");
            writer.write(String.format("%-10s %-55s %-20s %-25s %-30s %-15s %-12s%n",
                    "Code", "Country Name", "Capital", "District", "Region", "Continent", "Population"));

            for (Country country : countries) {
                if (country == null) {
                    return 0;
                }

                writer.write(String.format("%-10s %-55s %-20s %-25s %-30s %-15s %-12d%n",
                        country.getCode(),
                        country.getName(),
                        country.getCapitalName(),
                        country.getDistrict(),
                        country.getRegion(),
                        country.getContinent(),
                        country.getPopulation()
                ));
                index++;
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing country report: " + e.getMessage());
        }
        return index;
    }

    /**
     * Prints a city report in a formatted table.
     * Displays: city name, country, district, region, continent, and population.
     *
     * @param cities List of cities to display
     * @param title  name of the report
     */
    public int writeCityReportToFile(ArrayList<City> cities, String title) {
        if (cities == null || cities.isEmpty()) {
            return 0;
        }

        int index = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("===== " + title + " =====\n");
            writer.write(String.format("%-5s %-25s %-40s %-20s %-30s %-15s %-10s%n",
                    "ID", "City", "Country", "District", "Region", "Continent", "Population"));

            int id = 1;
            for (City city : cities) {
                if (city == null) {
                    return 0;
                }

                writer.write(String.format("%-5d %-25s %-40s %-20s %-30s %-15s %-10d%n",
                        id++,
                        city.getName(),
                        city.getCountry_name(),
                        city.getDistrict(),
                        city.getRegion(),
                        city.getContinent(),
                        city.getPopulation()
                ));
                index++;
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing city report: " + e.getMessage());
        }
        return index;
    }

    /**
     * Prints a capital city report in a formatted table.
     * Displays: capital city name, country, region, continent, and population.
     *
     * @param capitals List of capital cities to display
     * @param title    name of the report
     */
    public int writeCapitalCityReportToFile(ArrayList<City> capitals, String title) {
        if (capitals == null || capitals.isEmpty()) {
            return 0;
        }

        int index = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("===== " + title + " =====\n");
            writer.write(String.format("%-5s %-30s %-45s %-30s %-15s %-12s%n",
                    "ID", "City", "Country", "Region", "Continent", "Population"));

            int id = 1;
            for (City capital : capitals) {
                if (capital == null) {
                    return 0;
                }

                writer.write(String.format("%-5s %-30s %-45s %-30s %-15s %-12d%n",
                        id++,
                        capital.getName(),
                        capital.getCountry_name(),
                        capital.getRegion(),
                        capital.getContinent(),
                        capital.getPopulation()
                ));
                index++;
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing capital city report: " + e.getMessage());
        }
        return index;
    }

    /**
     * Prints the people population report in a tabular format.
     * Displays: total population, city population (with %), and non-city population (with %).
     *
     * @param peoplePopulations List of population objects to display
     * @param level             Label describing the report level (e.g., "World", "Continent", "Country")
     */
    public int writePopulationReportToFile(ArrayList<PeoplePopulation> peoplePopulations, String level) {
        if (peoplePopulations == null || peoplePopulations.isEmpty()) {
            return 0;
        }

        int index = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("===== " + level + " Population Report =====\n");
            writer.write(String.format("%-30s %-20s %-30s %-30s%n",
                    level, "Total Population", "City Population (% of total)", "Non-City Population (% of total)"));

            for (PeoplePopulation pp : peoplePopulations) {
                if (pp == null) {
                    return 0;
                }

                writer.write(String.format("%-30s %-20d %-30s %-30s%n",
                        pp.getLevel(),
                        pp.getTotalPopulation(),
                        pp.getCityPopulation() + " (" + String.format("%.2f", pp.getCityPopulationPercentage()) + "%)",
                        pp.getNonCityPopulation() + " (" + String.format("%.2f", pp.getNonCityPopulationPercentage()) + "%)"
                ));
                index++;
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing population report: " + e.getMessage());
        }
        return index;
    }

    /**
     * Writes the overall population report to a text file.
     * This method appends population data for a specified level (e.g., World, Continent, Region)
     *
     * @param peoplePopulations A list of PeoplePopulation objects containing population data.
     * @param level             The population level being reported (e.g., "World", "Continent", or "Region").
     */
    public int writeOverallPopulationReportToFile(ArrayList<PeoplePopulation> peoplePopulations, String level) {
        if (peoplePopulations == null || peoplePopulations.isEmpty()) {
            return 0;
        }

        int index = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("===== " + level + " Total Population Report =====\n");
            writer.write(String.format("%-40s %-20s%n",
                    level, "Total Population"));

            for (PeoplePopulation pp : peoplePopulations) {
                if (pp == null) {
                    return 0;
                }

                writer.write(String.format("%-40s %-20d%n",
                        pp.getLevel(),
                        pp.getTotalPopulation()
                ));
                index++;
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error population report: " + e.getMessage());
        }
        return index;
    }

    /**
     * Writes the global language statistics report to the output file.
     * Each entry shows the language name, total number of speakers, and
     * its percentage of the total world population.
     *
     * @param languages A list of CountryLanguage objects containing language statistics.
     * @param title     The report title (e.g., "Top 5 Languages by Total Speakers").
     */
    public int writeLanguageReportToFile(ArrayList<CountryLanguage> languages, String title) {
        if (languages == null || languages.isEmpty()) {
            return 0;
        }

        int index = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Write report title and table header
            writer.write("===== " + title + " =====\n");
            writer.write(String.format("%-10s %-20s %-5s%n",
                    "Language", "Total Speakers", "World Percentage"));

            // Loop through each language record and print formatted data
            for (CountryLanguage lang : languages) {
                if (lang == null) {
                    return 0;
                }

                writer.write(String.format(
                        "%-10s %, -20.0f %-5.2f%%%n",
                        lang.getLanguage(),           // Language name
                        lang.getPercentage(),          // Total speakers (stored in 'percentage' field)
                        lang.getWorld_percentage()     // World percentage of speakers
                ));
                index++;
            }

            // Add a blank line after each report for readability
            writer.write("\n");

        } catch (IOException e) {
            // Handle file writing errors gracefully
            System.out.println("Error writing language report: " + e.getMessage());
        }
        return index;
    }

    /**
     * Optional: Clear previous reports before writing new ones
     */
    public int clearReportFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(""); // overwrite with empty content
            return 1;
        } catch (IOException e) {
            System.out.println("Error clearing report file: " + e.getMessage());
        }
        return 0;
    }
}
