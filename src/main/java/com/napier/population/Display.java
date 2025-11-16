package com.napier.population;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
     * @param fileName  name of the file
     */
    public int writeCountryReportToFile(ArrayList<Country> countries, String fileName) {
        if (countries == null || countries.isEmpty()) {
            return 0;
        }

        int index = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("| Code | Country Name | Capital | District | Region | Continent | Population |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- | --- |\r\n");

        for (Country country : countries) {
            if (country == null) {
                return 0;
            }

            sb.append(
                    "| " + country.getCode() + " | " +
                            country.getName() + " | " +
                            country.getCapitalName() + " | " +
                            country.getDistrict() + " | " +
                            country.getRegion() + " | " +
                            country.getContinent() + " | " +
                            country.getPopulation() + " |\r\n"
            );
            index++;
        }

        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new                                 File("./reports/" + fileName)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing country report: " + e.getMessage());
        }
        return index;
    }

    /**
     * Prints a city report in a formatted table.
     * Displays: city name, country, district, region, continent, and population.
     *
     * @param cities    List of cities to display
     * @param fileName  name of the file
     */
    public int writeCityReportToFile(ArrayList<City> cities, String fileName) {
        if (cities == null || cities.isEmpty()) {
            return 0;
        }

        int index = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("| ID | City | Country | District | Region | Continent | Population |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- | --- |\r\n");

        int id = 1;
        for (City city : cities) {
            if (city == null) {
                return 0;
            }

            sb.append(
                    "| " + id++ + " | " +
                            city.getName() + " | " +
                            city.getCountry_name() + " | " +
                            city.getDistrict() + " | " +
                            city.getRegion() + " | " +
                            city.getContinent() + " | " +
                            city.getPopulation() + " |\r\n"
            );
            index++;
        }

        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new                                 File("./reports/" + fileName)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing city report: " + e.getMessage());
        }
        return index;
    }

    /**
     * Prints a capital city report in a formatted table.
     * Displays: capital city name, country, region, continent, and population.
     *
     * @param capitals    List of capital cities to display
     * @param fileName    name of the file
     */
    public int writeCapitalCityReportToFile(ArrayList<City> capitals, String fileName) {
        if (capitals == null || capitals.isEmpty()) {
            return 0;
        }

        int index = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("| ID | City | Country | Region | Continent | Population |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- |\r\n");

        int id = 1;
        for (City capital : capitals) {
            if (capital == null) {
                return 0;
            }

            sb.append(
                    "| " + id++ + " | " +
                            capital.getName() + " | " +
                            capital.getCountry_name() + " | " +
                            capital.getRegion() + " | " +
                            capital.getContinent() + " | " +
                            capital.getPopulation() + " |\r\n"
            );
            index++;
        }

        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new                                 File("./reports/" + fileName)));
            writer.write(sb.toString());
            writer.close();
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
    public int writePopulationReportToFile(ArrayList<PeoplePopulation> peoplePopulations, String level, String fileName) {
        if (peoplePopulations == null || peoplePopulations.isEmpty()) {
            return 0;
        }

        int index = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("| " + level + " | Total Population | City Population (% of total) | Non-City Population (% of total) |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        for (PeoplePopulation pp : peoplePopulations) {
            if (pp == null) {
                return 0;
            }

            sb.append(
                    "| " + pp.getLevel() + " | " +
                            pp.getTotalPopulation() + " | " +
                            pp.getCityPopulation() + " (" + String.format("%.2f", pp.getCityPopulationPercentage()) + "%)" + " | " +
                            pp.getNonCityPopulation() + " (" + String.format("%.2f", pp.getNonCityPopulationPercentage()) + "%)" + " |\r\n"
            );
            index++;
        }

        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new                                 File("./reports/" + fileName)));
            writer.write(sb.toString());
            writer.close();
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
    public int writeOverallPopulationReportToFile(ArrayList<PeoplePopulation> peoplePopulations, String level, String fileName) {
        if (peoplePopulations == null || peoplePopulations.isEmpty()) {
            return 0;
        }

        int index = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("| " + level + " | Total Population |\r\n");
        sb.append("| --- | --- |\r\n");

        for (PeoplePopulation pp : peoplePopulations) {
            if (pp == null) {
                return 0;
            }

            sb.append("| " + pp.getLevel() + " | " +
                    pp.getTotalPopulation() + " |\r\n"
            );
            index++;
        }

        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new                                 File("./reports/" + fileName)));
            writer.write(sb.toString());
            writer.close();
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
     * @param languages     A list of CountryLanguage objects containing language statistics.
     * @param fileName      The report file
     */
    public int writeLanguageReportToFile(ArrayList<CountryLanguage> languages, String fileName) {
        if (languages == null || languages.isEmpty()) {
            return 0;
        }

        int index = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("| Language | Total Speakers | World Percentage |\r\n");
        sb.append("| --- | --- | --- |\r\n");

        for (CountryLanguage lang : languages) {
            if (lang == null) {
                return 0;
            }

            sb.append(
                    "| " + lang.getLanguage() + " | " +
                            lang.getPercentage() + " | " +
                            lang.getPercentage() + " |\r\n"
            );
            index++;
        }

        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new                                 File("./reports/" + fileName)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            // Handle file writing errors gracefully
            System.out.println("Error writing language report: " + e.getMessage());
        }
        return index;
    }
}
