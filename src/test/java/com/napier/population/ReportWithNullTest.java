package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportWithNullTest {

    DbConnection db;
    Connection con;
    CapitalCityReport capitalCityReport;
    CityReport cityReport;
    CountryReport countryReport;
    PopulationReport populationReport;

    @BeforeEach
    void setUp() {
        db = new DbConnection();// Establish connection to the database
        con = null;

        capitalCityReport = new CapitalCityReport(con);
        cityReport = new CityReport(con);
        countryReport = new CountryReport(con);
        populationReport = new PopulationReport(con);
    }

    @AfterEach
    void tearDown() {
        db.disconnect();
    }

    // ---------------------------------------------------------------------
    //  CityReport Null-Connection Tests
    // ---------------------------------------------------------------------

    /**
     * Test 1: getAllCitiesByPopulation()
     * Checks that an empty list is returned when connection is null.
     */
    @Test
    void testGetAllCitiesByPopulation_NullConnection() {
        ArrayList<City> result = cityReport.getAllCitiesByPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 2: getCitiesByContinentPopulationDesc()
     * Ensures the method safely returns an empty list for null connection.
     */
    @Test
    void testGetCitiesByContinentPopulationDesc_NullConnection() {
        ArrayList<City> result = cityReport.getCitiesByContinentPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 3: getTop50CitiesByPopulation()
     * Confirms empty list is returned and no exception occurs when connection is null.
     */
    @Test
    void testGetTop50CitiesByPopulation_NullConnection() {
        ArrayList<City> result = cityReport.getTop50CitiesByPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 4: getTop10CitiesByContinentPopulation()
     * Verifies method handles null connection gracefully with empty result.
     */
    @Test
    void testGetTop10CitiesByContinentPopulation_NullConnection() {
        ArrayList<City> result = cityReport.getTop10CitiesByContinentPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 5: getTop5CitiesByRegionPopulation()
     * Checks that method returns empty list and avoids SQL execution when null.
     */
    @Test
    void testGetTop5CitiesByRegionPopulation_NullConnection() {
        ArrayList<City> result = cityReport.getTop5CitiesByRegionPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 6: getAllCitiesByRegionPopulationDesc()
     * Ensures method returns non-null but empty list for null DB connection.
     */
    @Test
    void testGetAllCitiesByRegionPopulationDesc_NullConnection() {
        ArrayList<City> result = cityReport.getAllCitiesByRegionPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 7: getAllCitiesByCountryPopulationDesc()
     * Confirms empty list is returned and no NullPointerException occurs.
     */
    @Test
    void testGetAllCitiesByCountryPopulationDesc_NullConnection() {
        ArrayList<City> result = cityReport.getAllCitiesByCountryPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 8: getCitiesByDistrictPopulationDesc()
     * Checks safe handling of null connection with empty result list.
     */
    @Test
    void testGetCitiesByDistrictPopulationDesc_NullConnection() {
        ArrayList<City> result = cityReport.getCitiesByDistrictPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 9: getTop5CitiesByCountryPopulation()
     * Ensures defensive handling of null connection with empty list output.
     */
    @Test
    void testGetTop5CitiesByCountryPopulation_NullConnection() {
        ArrayList<City> result = cityReport.getTop5CitiesByCountryPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 10: getTopCityByDistrictPopulation()
     * Confirms empty list returned when connection is null, no exception thrown.
     */
    @Test
    void testGetTopCityByDistrictPopulation_NullConnection() {
        ArrayList<City> result = cityReport.getTopCityByDistrictPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }


    // ---------------------------------------------------------------------
    //  CountryReport Null-Connection Tests
    // ---------------------------------------------------------------------

    /**
     * Test 1: getCountriesByContinentPopulationDesc()
     * Checks that an empty list is returned when connection is null.
     */
    @Test
    void testGetCountriesByContinentPopulationDesc_NullConnection() {
        ArrayList<Country> result = countryReport.getCountriesByContinentPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 2: getAllCountriesByPopulationDesc()
     * Ensures method returns empty list when database connection is null.
     */
    @Test
    void testGetAllCountriesByPopulationDesc_NullConnection() {
        ArrayList<Country> result = countryReport.getAllCountriesByPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 3: getTop10CountriesByContinentPopulation()
     * Verifies method handles null connection gracefully with empty result.
     */
    @Test
    void testGetTop10CountriesByContinentPopulation_NullConnection() {
        ArrayList<Country> result = countryReport.getTop10CountriesByContinentPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 4: getTop50CountriesByPopulation()
     * Confirms method returns empty list instead of throwing exceptions.
     */
    @Test
    void testGetTop50CountriesByPopulation_NullConnection() {
        ArrayList<Country> result = countryReport.getTop50CountriesByPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 5: getCountriesByRegionPopulationDesc()
     * Checks that no SQL execution occurs when connection is null.
     */
    @Test
    void testGetCountriesByRegionPopulationDesc_NullConnection() {
        ArrayList<Country> result = countryReport.getCountriesByRegionPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 6: getTop5CountriesPerRegion()
     * Ensures safe handling of null connection with empty result list.
     */
    @Test
    void testGetTop5CountriesPerRegion_NullConnection() {
        ArrayList<Country> result = countryReport.getTop5CountriesPerRegion();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    // ---------------------------------------------------------------------
    //  CapitalCityReport Null-Connection Tests
    // ---------------------------------------------------------------------

    /**
     * Test 1: getTop50CapitalCitiesByPopulation()
     * Checks that an empty list is returned when connection is null.
     */
    @Test
    void testGetTop50CapitalCitiesByPopulation_NullConnection() {
        ArrayList<City> result = capitalCityReport.getTop50CapitalCitiesByPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 2: getAllCapitalCitiesByContinentPopulationDesc()
     * Ensures method returns empty list and handles null connection safely.
     */
    @Test
    void testGetAllCapitalCitiesByContinentPopulationDesc_NullConnection() {
        ArrayList<City> result = capitalCityReport.getAllCapitalCitiesByContinentPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 3: getTop5CapitalCitiesByRegion()
     * Verifies that method avoids SQL execution and returns empty result list.
     */
    @Test
    void testGetTop5CapitalCitiesByRegion_NullConnection() {
        ArrayList<City> result = capitalCityReport.getTop5CapitalCitiesByRegion();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 4: getAllCapitalCitiesByRegionPopulationDesc()
     * Confirms method returns empty list when database connection is null.
     */
    @Test
    void testGetAllCapitalCitiesByRegionPopulationDesc_NullConnection() {
        ArrayList<City> result = capitalCityReport.getAllCapitalCitiesByRegionPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 5: getAllCapitalCitiesByPopulationDesc()
     * Checks that method safely returns an empty list for null connections.
     */
    @Test
    void testGetAllCapitalCitiesByPopulationDesc_NullConnection() {
        ArrayList<City> result = capitalCityReport.getAllCapitalCitiesByPopulationDesc();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 6: getTop10CapitalCitiesByContinentPopulation()
     * Ensures defensive handling of null connection with empty list result.
     */
    @Test
    void testGetTop10CapitalCitiesByContinentPopulation_NullConnection() {
        ArrayList<City> result = capitalCityReport.getTop10CapitalCitiesByContinentPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    // ---------------------------------------------------------------------
    //  PopulationReport Null-Connection Tests
    // ---------------------------------------------------------------------

    /**
     * Test 1: getCountryPopulationReport()
     * Checks that method returns empty list when connection is null.
     */
    @Test
    void testGetCountryPopulationReport_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getCountryPopulationReport();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 2: getWorldPopulation()
     * Ensures method handles null connection and returns empty result list.
     */
    @Test
    void testGetWorldPopulation_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getWorldPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 3: getTotalPopulationPerCountry()
     * Confirms empty list is returned and no exceptions occur when connection is null.
     */
    @Test
    void testGetTotalPopulationPerCountry_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getTotalPopulationPerCountry();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 4: getContinentTotalPopulation()
     * Checks that method safely returns an empty list for null connection.
     */
    @Test
    void testGetContinentTotalPopulation_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getContinentTotalPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 5: getDistrictTotalPopulation()
     * Ensures method returns empty list instead of throwing exceptions.
     */
    @Test
    void testGetDistrictTotalPopulation_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getDistrictTotalPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 6: getContinentPopulationReport()
     * Verifies defensive handling of null connection with empty result.
     */
    @Test
    void testGetContinentPopulationReport_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getContinentPopulationReport();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 7: getCityTotalPopulation()
     * Checks safe handling of null connection returning empty list.
     */
    @Test
    void testGetCityTotalPopulation_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getCityTotalPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 8: getWorldLanguageReport()
     * Ensures empty list is returned and no SQL operation occurs for null connection.
     */
    @Test
    void testGetWorldLanguageReport_NullConnection() {
        ArrayList<CountryLanguage> result = populationReport.getWorldLanguageReport();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 9: getRegionPopulationReport()
     * Confirms empty list result when connection is null.
     */
    @Test
    void testGetRegionPopulationReport_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getRegionPopulationReport();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }

    /**
     * Test 10: getRegionTotalPopulation()
     * Checks that method safely returns empty result list when DB connection is null.
     */
    @Test
    void testGetRegionTotalPopulation_NullConnection() {
        ArrayList<PeoplePopulation> result = populationReport.getRegionTotalPopulation();
        assertNotNull(result, "Result list should not be null when DB connection is null");
        assertTrue(result.isEmpty(), "Result list should be empty when DB connection is null");
    }
}