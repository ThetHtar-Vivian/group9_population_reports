package com.napier.population;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class DisplayTest {

    private Display display;
    private String filename;

    @BeforeEach
    void setUp() {
        display = new Display();
        filename = "reports/DisplayTest.txt";
    }

    // COUNTRY REPORTS ------------------------------------------------------

    @Test
    void writeCountryReportToFileCatchIOException() throws IOException {
        Display display = new Display();

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country(
                "AYA", "Ayarwaddy", "Yangon", "Yangon",
                "South East Asia", "Asia", 2300
        ));

        int result = display.writeCountryReportToFile(countries, "TestReports/ReadOnlyTest.txt");
        assertEquals(1, result, "IOException should be caught gracefully — file not found may still return 1 if loop completes.");
    }

    @Test
    void testWriteCountryReport_withValidData() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("AYA", "Ayarwaddy", "Yangon", "Yangon",
                "South East Asia", "Asia", 2300));

        int count = display.writeCountryReportToFile(countries, filename);
        assertEquals(1, count, "Expected 1 when writing one valid country");
    }

    @Test
    void testWriteCountryReport_withEmptyList() {
        ArrayList<Country> countries = new ArrayList<>();
        int count = display.writeCountryReportToFile(countries, filename);
        assertEquals(0, count);
    }

    @Test
    void testWriteCountryReport_withNullCountry() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);

        int count = display.writeCountryReportToFile(countries, filename);

        assertEquals(0, count, "Expected 0 when a null country is present");
    }

    @Test
    void testWriteCountryReport_withNullList() {
        int count = display.writeCountryReportToFile(null, filename);
        assertEquals(0, count);
    }

    // CITY REPORTS ------------------------------------------------------

    @Test
    void testWriteCityReport_withValidData() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("AYA", "Myanmar", "Yangon", "Yangon", "South East Asia", 2300));

        int count = display.writeCityReportToFile(cities, filename);
        assertEquals(1, count);
    }

    @Test
    void testWriteCityReport_withEmptyList() {
        ArrayList<City> cities = new ArrayList<>();
        int count = display.writeCityReportToFile(cities, filename);
        assertEquals(0, count);
    }

    @Test
    void testWriteCityReport_withNullCity() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);

        int count = display.writeCityReportToFile(cities, filename);

        assertEquals(0, count, "Expected 0 when a null city is present");
    }

    @Test
    void testWriteCityReport_withNullList() {
        int count = display.writeCityReportToFile(null, filename);
        assertEquals(0, count);
    }

    // CAPITAL CITY REPORTS ---------------------------------------------

    @Test
    void testWriteCapitalCityReport_withValidData() {
        ArrayList<City> capitals = new ArrayList<>();
        capitals.add(new City("AYA", "Myanmar", "Yangon", "Yangon", "South East Asia", 2300));

        int count = display.writeCapitalCityReportToFile(capitals, filename);
        assertEquals(1, count);
    }

    @Test
    void testWriteCapitalCityReport_withEmptyList() {
        ArrayList<City> capitals = new ArrayList<>();
        int count = display.writeCapitalCityReportToFile(capitals, filename);
        assertEquals(0, count);
    }

    @Test
    void testWriteCapitalCityReport_withNullCapital() {
        ArrayList<City> capitals = new ArrayList<>();
        capitals.add(null);

        int count = display.writeCapitalCityReportToFile(capitals, filename);

        assertEquals(0, count, "Expected 0 when a null capital city is present");
    }

    @Test
    void testWriteCapitalCityReport_withNullList() {
        int count = display.writeCapitalCityReportToFile(null, filename);
        assertEquals(0, count);
    }

    // POPULATION REPORTS -----------------------------------------------

    @Test
    void testWritePopulationReport_withValidData() {
        ArrayList<PeoplePopulation> populations = new ArrayList<>();
        populations.add(new PeoplePopulation("Asia", 1000000, 600000, 400000));

        int count = display.writePopulationReportToFile(populations, "Continent", filename);
        assertEquals(1, count);
    }

    @Test
    void testWritePopulationReport_withEmptyList() {
        ArrayList<PeoplePopulation> populations = new ArrayList<>();
        int count = display.writePopulationReportToFile(populations, "Continent", filename);
        assertEquals(0, count);
    }

    @Test
    void testWritePopulationReport_withNullPopulation() {
        ArrayList<PeoplePopulation> populations = new ArrayList<>();
        populations.add(null);

        int count = display.writePopulationReportToFile(populations, "Continent", filename);

        assertEquals(0, count, "Expected 0 when a null population record is present");
    }

    @Test
    void testWritePopulationReport_withNullList() {
        int count = display.writePopulationReportToFile(null, "Continent", filename);
        assertEquals(0, count);
    }

    // OVERALL POPULATION REPORTS ---------------------------------------

    @Test
    void testWriteOverallPopulationReport_withValidData() {
        ArrayList<PeoplePopulation> populations = new ArrayList<>();
        populations.add(new PeoplePopulation("World", 8000000000L));

        int count = display.writeOverallPopulationReportToFile(populations, "World", filename);
        assertEquals(1, count);
    }

    @Test
    void testWriteOverallPopulationReport_withEmptyList() {
        ArrayList<PeoplePopulation> populations = new ArrayList<>();
        int count = display.writeOverallPopulationReportToFile(populations, "World", filename);
        assertEquals(0, count);
    }

    @Test
    void testWriteOverallPopulationReport_withNullOverallPopulation() {
        ArrayList<PeoplePopulation> populations = new ArrayList<>();
        populations.add(null);

        int count = display.writeOverallPopulationReportToFile(populations, "Continent", filename);

        assertEquals(0, count, "Expected 0 when a null overall population record is present");
    }

    @Test
    void testWriteOverallPopulationReport_withNullList() {
        int count = display.writeOverallPopulationReportToFile(null, "World", filename);
        assertEquals(0, count);
    }

    // LANGUAGE REPORTS -------------------------------------------------

    @Test
    void testWriteLanguageReport_withValidData() {
        ArrayList<CountryLanguage> languages = new ArrayList<>();
        languages.add(new CountryLanguage("Burmese", 30000000, 2.0));

        int count = display.writeLanguageReportToFile(languages, filename);
        assertEquals(1, count);
    }

    @Test
    void testWriteLanguageReport_withEmptyList() {
        ArrayList<CountryLanguage> languages = new ArrayList<>();
        int count = display.writeLanguageReportToFile(languages, filename);
        assertEquals(0, count);
    }

    @Test
    void testWriteLanguageReport_withNullLanguage() {
        ArrayList<CountryLanguage> languages = new ArrayList<>();
        languages.add(null);

        int count = display.writeLanguageReportToFile(languages, filename);

        assertEquals(0, count, "Expected 0 when a null language is present");
    }

    @Test
    void testWriteLanguageReport_withNullList() {
        int count = display.writeLanguageReportToFile(null, filename);
        assertEquals(0, count);
    }
}
