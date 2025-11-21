package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class ReportWithNullTest {

    DbConnection db;
    Connection con;
    CapitalCityReport capitalCityReport;
    CityReport cityReport;
    CountryReport countryReport;
    PopulationReport populationReport;

    @BeforeEach
    void setUp() {
        db = new DbConnection(); // Establish connection to the database
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

    // ------------------- Helper -------------------
    private <T> void assertEmptyOnNullConnection(List<T> result, String message) {
        if (result == null || !result.isEmpty()) {
            fail(message + " - Expected non-null empty list, but got: " + result);
        }
    }

    // ------------------- CityReport Tests -------------------
    @Test
    void testGetAllCitiesByPopulation_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getAllCitiesByPopulation(),
                "getAllCitiesByPopulation should return empty list for null connection");
    }

    @Test
    void testGetCitiesByContinentPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getCitiesByContinentPopulationDesc(),
                "getCitiesByContinentPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetTop50CitiesByPopulation_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getTop50CitiesByPopulation(),
                "getTop50CitiesByPopulation should return empty list for null connection");
    }

    @Test
    void testGetTop10CitiesByContinentPopulation_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getTop10CitiesByContinentPopulation(),
                "getTop10CitiesByContinentPopulation should return empty list for null connection");
    }

    @Test
    void testGetTop5CitiesByRegionPopulation_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getTop5CitiesByRegionPopulation(),
                "getTop5CitiesByRegionPopulation should return empty list for null connection");
    }

    @Test
    void testGetAllCitiesByRegionPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getAllCitiesByRegionPopulationDesc(),
                "getAllCitiesByRegionPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetAllCitiesByCountryPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getAllCitiesByCountryPopulationDesc(),
                "getAllCitiesByCountryPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetCitiesByDistrictPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getCitiesByDistrictPopulationDesc(),
                "getCitiesByDistrictPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetTop5CitiesByCountryPopulation_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getTop5CitiesByCountryPopulation(),
                "getTop5CitiesByCountryPopulation should return empty list for null connection");
    }

    @Test
    void testGetTopCityByDistrictPopulation_NullConnection() {
        assertEmptyOnNullConnection(cityReport.getTopCityByDistrictPopulation(),
                "getTopCityByDistrictPopulation should return empty list for null connection");
    }

    // ------------------- CountryReport Tests -------------------
    @Test
    void testGetCountriesByContinentPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(countryReport.getCountriesByContinentPopulationDesc(),
                "getCountriesByContinentPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetAllCountriesByPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(countryReport.getAllCountriesByPopulationDesc(),
                "getAllCountriesByPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetTop10CountriesByContinentPopulation_NullConnection() {
        assertEmptyOnNullConnection(countryReport.getTop10CountriesByContinentPopulation(),
                "getTop10CountriesByContinentPopulation should return empty list for null connection");
    }

    @Test
    void testGetTop50CountriesByPopulation_NullConnection() {
        assertEmptyOnNullConnection(countryReport.getTop50CountriesByPopulation(),
                "getTop50CountriesByPopulation should return empty list for null connection");
    }

    @Test
    void testGetCountriesByRegionPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(countryReport.getCountriesByRegionPopulationDesc(),
                "getCountriesByRegionPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetTop5CountriesPerRegion_NullConnection() {
        assertEmptyOnNullConnection(countryReport.getTop5CountriesPerRegion(),
                "getTop5CountriesPerRegion should return empty list for null connection");
    }

    // ------------------- CapitalCityReport Tests -------------------
    @Test
    void testGetTop50CapitalCitiesByPopulation_NullConnection() {
        assertEmptyOnNullConnection(capitalCityReport.getTop50CapitalCitiesByPopulation(),
                "getTop50CapitalCitiesByPopulation should return empty list for null connection");
    }

    @Test
    void testGetAllCapitalCitiesByContinentPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(capitalCityReport.getAllCapitalCitiesByContinentPopulationDesc(),
                "getAllCapitalCitiesByContinentPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetTop5CapitalCitiesByRegion_NullConnection() {
        assertEmptyOnNullConnection(capitalCityReport.getTop5CapitalCitiesByRegion(),
                "getTop5CapitalCitiesByRegion should return empty list for null connection");
    }

    @Test
    void testGetAllCapitalCitiesByRegionPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(capitalCityReport.getAllCapitalCitiesByRegionPopulationDesc(),
                "getAllCapitalCitiesByRegionPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetAllCapitalCitiesByPopulationDesc_NullConnection() {
        assertEmptyOnNullConnection(capitalCityReport.getAllCapitalCitiesByPopulationDesc(),
                "getAllCapitalCitiesByPopulationDesc should return empty list for null connection");
    }

    @Test
    void testGetTop10CapitalCitiesByContinentPopulation_NullConnection() {
        assertEmptyOnNullConnection(capitalCityReport.getTop10CapitalCitiesByContinentPopulation(),
                "getTop10CapitalCitiesByContinentPopulation should return empty list for null connection");
    }

    // ------------------- PopulationReport Tests -------------------
    @Test
    void testGetCountryPopulationReport_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getCountryPopulationReport(),
                "getCountryPopulationReport should return empty list for null connection");
    }

    @Test
    void testGetWorldPopulation_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getWorldPopulation(),
                "getWorldPopulation should return empty list for null connection");
    }

    @Test
    void testGetTotalPopulationPerCountry_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getTotalPopulationPerCountry(),
                "getTotalPopulationPerCountry should return empty list for null connection");
    }

    @Test
    void testGetContinentTotalPopulation_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getContinentTotalPopulation(),
                "getContinentTotalPopulation should return empty list for null connection");
    }

    @Test
    void testGetDistrictTotalPopulation_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getDistrictTotalPopulation(),
                "getDistrictTotalPopulation should return empty list for null connection");
    }

    @Test
    void testGetContinentPopulationReport_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getContinentPopulationReport(),
                "getContinentPopulationReport should return empty list for null connection");
    }

    @Test
    void testGetCityTotalPopulation_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getCityTotalPopulation(),
                "getCityTotalPopulation should return empty list for null connection");
    }

    @Test
    void testGetWorldLanguageReport_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getWorldLanguageReport(),
                "getWorldLanguageReport should return empty list for null connection");
    }

    @Test
    void testGetRegionPopulationReport_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getRegionPopulationReport(),
                "getRegionPopulationReport should return empty list for null connection");
    }

    @Test
    void testGetRegionTotalPopulation_NullConnection() {
        assertEmptyOnNullConnection(populationReport.getRegionTotalPopulation(),
                "getRegionTotalPopulation should return empty list for null connection");
    }
}
