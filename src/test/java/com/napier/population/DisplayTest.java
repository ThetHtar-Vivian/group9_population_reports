package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DisplayTest {

    private Display display;
    private String filename;

    @BeforeEach
    void setUp() {
        filename = "reports/DisplayTest.txt";
        display = new Display(filename);
    }

    @AfterEach
    void tearDown() {
        display.clearReportFile();
    }

    @Test
    void writeCountryReportToFileCatchIOException() throws IOException {
        Display display = new Display("TestReports/ReadOnlyTest.txt");

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country(
                "AYA",
                "Ayarwaddy",
                "Yangon",
                "Yangon",
                "South East Asia",
                "Asia",
                2300
        ));

        int result = display.writeCountryReportToFile(countries, "IOException Test");
        assertEquals(0, result, "Expected 0 since IOException should be caught.");
    }

    @Test
    void testWriteCountryReport_withValidData() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country(
                "AYA",
                "Ayarwaddy",
                "Yangon",
                "Yangon",
                "South East Asia",
                "Asia",
                2300
        ));

        int count = display.writeCountryReportToFile(countries, "Test Valid Country Report");

        assertEquals(1, count, "Expected 1 when writing one valid country");
    }

    @Test
    void testWriteCountryReport_withEmptyList() {
        ArrayList<Country> countries = new ArrayList<>();

        int count = display.writeCountryReportToFile(countries, "Test Empty List");

        assertEquals(0, count, "Expected 0 when no countries are provided");
    }

    @Test
    void testWriteCountryReport_withNullCountry() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);

        int count = display.writeCountryReportToFile(countries, "Test Null Country");

        assertEquals(0, count, "Expected 0 when a null country is present");
    }

    @Test
    void testWriteCountryReport_withNullList() {
        int count = display.writeCountryReportToFile(null, "Test Null List");

        assertEquals(0, count, "Expected 0 when the list itself is null");
    }

    @Test
    void writeCityReportToFileCatchIOException() throws IOException {
        Display display = new Display("TestReports/ReadOnlyTest.txt");

        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(
                "AYA",
                "Myanmar",
                "Yangon",
                "Yangon",
                "South East Asia",
                2300
        ));

        int result = display.writeCityReportToFile(cities, "IOException Test");
        assertEquals(0, result, "Expected 0 since IOException should be caught.");
    }

    @Test
    void testWriteCityReport_withValidData() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(
                "AYA",
                "Myanmar",
                "Yangon",
                "Yangon",
                "South East Asia",
                2300
        ));

        int count = display.writeCityReportToFile(cities, "Test Valid City Report");

        assertEquals(1, count, "Expected 1 when writing one valid city");
    }

    @Test
    void testWriteCityReport_withEmptyList() {
        ArrayList<City> cities = new ArrayList<>();

        int count = display.writeCityReportToFile(cities, "Test Empty List");

        assertEquals(0, count, "Expected 0 when no cities are provided");
    }

    @Test
    void testWriteCityReport_withNullCountry() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);

        int count = display.writeCityReportToFile(cities, "Test Null City");

        assertEquals(0, count, "Expected 0 when a null city is present");
    }

    @Test
    void testWriteCityReport_withNullList() {
        int count = display.writeCityReportToFile(null, "Test Null List");

        assertEquals(0, count, "Expected 0 when the list itself is null");
    }

    @Test
    void writeCapitalCityReportToFileCatchIOException() throws IOException {
        Display display = new Display("TestReports/ReadOnlyTest.txt");

        ArrayList<City> capitalCities = new ArrayList<>();
        capitalCities.add(new City(
                "AYA",
                "Myanmar",
                "Yangon",
                "Yangon",
                "South East Asia",
                2300
        ));

        int result = display.writeCapitalCityReportToFile(capitalCities, "IOException Test");
        assertEquals(0, result, "Expected 0 since IOException should be caught.");
    }

    @Test
    void testWriteCapitalCityReport_withValidData() {
        ArrayList<City> capitalCities = new ArrayList<>();
        capitalCities.add(new City(
                "AYA",
                "Myanmar",
                "Yangon",
                "Yangon",
                "South East Asia",
                2300
        ));

        int count = display.writeCapitalCityReportToFile(capitalCities, "Test Valid Capital City Report");

        assertEquals(1, count, "Expected 1 when writing one valid capital city");
    }

    @Test
    void testWriteCapitalCityReport_withEmptyList() {
        ArrayList<City> capitalCities = new ArrayList<>();

        int count = display.writeCapitalCityReportToFile(capitalCities, "Test Empty List");

        assertEquals(0, count, "Expected 0 when no capital cities are provided");
    }

    @Test
    void testWriteCapitalCityReport_withNullCountry() {
        ArrayList<City> capitalCities = new ArrayList<>();
        capitalCities.add(null);

        int count = display.writeCapitalCityReportToFile(capitalCities, "Test Null Capital City");

        assertEquals(0, count, "Expected 0 when a null capital city is present");
    }

    @Test
    void testWriteCapitalCityReport_withNullList() {
        int count = display.writeCapitalCityReportToFile(null, "Test Null List");

        assertEquals(0, count, "Expected 0 when the list itself is null");
    }

    @Test
    void writePopulationReportToFileCatchIOException() throws IOException {
        Display display = new Display("TestReports/ReadOnlyTest.txt");

        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();
        peoplePopulations.add(new PeoplePopulation(
                "Continent",
                300000,
                100000,
                100000
        ));

        int result = display.writePopulationReportToFile(peoplePopulations, "IOException Test");
        assertEquals(0, result, "Expected 0 since IOException should be caught.");
    }

    @Test
    void testWritePopulationReport_withValidData() {
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();
        peoplePopulations.add(new PeoplePopulation(
                "Continent",
                300000,
                100000,
                100000
        ));

        int count = display.writePopulationReportToFile(peoplePopulations, "Test Valid Capital City Report");

        assertEquals(1, count, "Expected 1 when writing one valid capital city");
    }

    @Test
    void testWritePopulationReport_withEmptyList() {
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();

        int count = display.writePopulationReportToFile(peoplePopulations, "Test Empty List");

        assertEquals(0, count, "Expected 0 when no capital cities are provided");
    }

    @Test
    void testWritePopulationReport_withNullCountry() {
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();
        peoplePopulations.add(null);

        int count = display.writePopulationReportToFile(peoplePopulations, "Test Null Capital City");

        assertEquals(0, count, "Expected 0 when a null capital city is present");
    }

    @Test
    void testWritePopulationReport_withNullList() {
        int count = display.writePopulationReportToFile(null, "Test Null List");

        assertEquals(0, count, "Expected 0 when the list itself is null");
    }

    @Test
    void writeOverallPopulationReportToFileCatchIOException() throws IOException {
        Display display = new Display("TestReports/ReadOnlyTest.txt");

        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();
        peoplePopulations.add(new PeoplePopulation(
                "Continent",
                300000
        ));

        int result = display.writeOverallPopulationReportToFile(peoplePopulations, "IOException Test");
        assertEquals(0, result, "Expected 0 since IOException should be caught.");
    }

    @Test
    void testWriteOverallPopulationReport_withValidData() {
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();
        peoplePopulations.add(new PeoplePopulation(
                "Continent",
                300000
        ));

        int count = display.writeOverallPopulationReportToFile(peoplePopulations, "Test Valid Capital City Report");

        assertEquals(1, count, "Expected 1 when writing one valid capital city");
    }

    @Test
    void testWriteOverallPopulationReport_withEmptyList() {
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();

        int count = display.writeOverallPopulationReportToFile(peoplePopulations, "Test Empty List");

        assertEquals(0, count, "Expected 0 when no capital cities are provided");
    }

    @Test
    void testWriteOverallPopulationReport_withNullCountry() {
        ArrayList<PeoplePopulation> peoplePopulations = new ArrayList<>();
        peoplePopulations.add(null);

        int count = display.writeOverallPopulationReportToFile(peoplePopulations, "Test Null Capital City");

        assertEquals(0, count, "Expected 0 when a null capital city is present");
    }

    @Test
    void testWriteOverallPopulationReport_withNullList() {
        int count = display.writeOverallPopulationReportToFile(null, "Test Null List");

        assertEquals(0, count, "Expected 0 when the list itself is null");
    }

    @Test
    void writeLanguageReportToFileCatchIOException() throws IOException {
        Display display = new Display("TestReports/ReadOnlyTest.txt");

        ArrayList<CountryLanguage> languages = new ArrayList<>();
        languages.add(new CountryLanguage(
                "Myanmar",
                30000,
                2
        ));

        int result = display.writeLanguageReportToFile(languages, "IOException Test");
        assertEquals(0, result, "Expected 0 since IOException should be caught.");
    }

    @Test
    void testWriteLanguageReport_withValidData() {
        ArrayList<CountryLanguage> languages = new ArrayList<>();
        languages.add(new CountryLanguage(
                "Myanmar",
                30000,
                2
        ));

        int count = display.writeLanguageReportToFile(languages, "Test Valid Capital City Report");

        assertEquals(1, count, "Expected 1 when writing one valid capital city");
    }

    @Test
    void testWriteLanguageReport_withEmptyList() {
        ArrayList<CountryLanguage> languages = new ArrayList<>();

        int count = display.writeLanguageReportToFile(languages, "Test Empty List");

        assertEquals(0, count, "Expected 0 when no capital cities are provided");
    }

    @Test
    void testWriteLanguageReport_withNullCountry() {
        ArrayList<CountryLanguage> languages = new ArrayList<>();
        languages.add(null);

        int count = display.writeLanguageReportToFile(languages, "Test Null Capital City");

        assertEquals(0, count, "Expected 0 when a null capital city is present");
    }

    @Test
    void testWriteLanguageReport_withNullList() {
        int count = display.writeLanguageReportToFile(null, "Test Null List");

        assertEquals(0, count, "Expected 0 when the list itself is null");
    }

    @Test
    void clearReportFileCatchIOException() throws IOException {
        Display display = new Display("TestReports/ReadOnlyTest.txt");
        int check = display.clearReportFile();

        assertEquals(0, check, "Expected 0 since IOException should be caught.");
    }
}