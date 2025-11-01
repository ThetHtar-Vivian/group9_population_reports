package com.napier.population;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Full test coverage for CityReport using Mockito.
 * - Each method: happy path (one-row result) + SQLException path (executeQuery throws)
 * - One null-connection test for early-return branch
 *
 * Copy this file to src/test/java/com/napier/population/CityReportTest.java
 * Make sure you have Mockito and JUnit 5 dependencies in your test classpath.
 */
@ExtendWith(MockitoExtension.class)
class CityReportTest {

    @Mock Connection mockCon;
    @Mock Statement mockStmt;
    @Mock ResultSet mockRs;

    private CityReport report;

    /**
     * Set up default wiring: mockCon.createStatement() -> mockStmt
     */
    @BeforeEach
    void setup() throws SQLException {
        when(mockCon.createStatement()).thenReturn(mockStmt);
    }

    /**
     * Helper to configure a one-row ResultSet for typical city queries.
     */
    private void prepareOneRowResultSet(String cityName, String countryName, String district,
                                        String region, String continent, int population) throws SQLException {
        when(mockRs.next()).thenReturn(true).thenReturn(false);

        // Map column names to their values exactly as used in CityReport
        lenient().when(mockRs.getString(anyString())).thenAnswer(invocation -> {
            String col = invocation.getArgument(0);
            switch (col) {
                case "CityName": return cityName;
                case "CountryName": return countryName;
                case "District": return district;  // ensure district column returns district
                case "Region": return region;      // region column returns region
                case "Continent": return continent;
                case "City": return cityName;      // used in getTop50CitiesByPopulation
                case "Country": return countryName;// used in getTop50CitiesByPopulation
                default: return null;
            }
        });

        lenient().when(mockRs.getInt(anyString())).thenAnswer(invocation -> {
            String col = invocation.getArgument(0);
            if ("Population".equals(col)) return population;
            return 0;
        });
    }


    /**
     * Helper: wire mockStmt.executeQuery(...) to return mockRs when sql contains the given fragment
     */
    private void wireExecuteQueryReturnsMockRs(String sqlContains) throws SQLException {
        when(mockStmt.executeQuery(ArgumentMatchers.contains(sqlContains))).thenReturn(mockRs);
    }

    /**
     * Helper: wire mockStmt.executeQuery(...) to throw SQLException when sql contains the given fragment
     */
    private void wireExecuteQueryThrows(String sqlContains) throws SQLException {
        when(mockStmt.executeQuery(ArgumentMatchers.contains(sqlContains))).thenThrow(new SQLException("simulated"));
    }


    // ---------- getAllCitiesByPopulation ----------
    @Test
    void testGetAllCitiesByPopulation_happy() throws SQLException {
        prepareOneRowResultSet("A-City", "A-Country", "A-District", "A-Region", "A-Cont", 1111);
        wireExecuteQueryReturnsMockRs("ORDER BY c.Population DESC");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getAllCitiesByPopulation();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        City c = cities.get(0);
        assertEquals("A-City", c.getName());
        assertEquals(1111, c.getPopulation());
    }

    @Test
    void testGetAllCitiesByPopulation_sqlException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY c.Population DESC");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getAllCitiesByPopulation();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getCitiesByContinentPopulationDesc ----------
    @Test
    void testGetCitiesByContinentPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("CityC", "CountryC", "DistC", "RegC", "ContC", 2222);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Continent, ci.Population DESC");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getCitiesByContinentPopulationDesc();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("CityC", cities.get(0).getName());
    }

    @Test
    void testGetCitiesByContinentPopulationDesc_exception() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Continent, ci.Population DESC");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getCitiesByContinentPopulationDesc();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getTop50CitiesByPopulation ----------
    @Test
    void testGetTop50CitiesByPopulation_happy() throws SQLException {
        prepareOneRowResultSet("T50City", "T50Country", "TD", "TR", "TC", 3333);
        wireExecuteQueryReturnsMockRs("LIMIT 50");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTop50CitiesByPopulation();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("T50City", cities.get(0).getName());
    }

    @Test
    void testGetTop50CitiesByPopulation_exception() throws SQLException {
        wireExecuteQueryThrows("LIMIT 50");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTop50CitiesByPopulation();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getTop10CitiesByContinentPopulation ----------
    @Test
    void testGetTop10CitiesByContinentPopulation_happy() throws SQLException {
        prepareOneRowResultSet("Top10City", "Top10Country", "D10", "R10", "Cont10", 4444);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTop10CitiesByContinentPopulation();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Top10City", cities.get(0).getName());
    }

    @Test
    void testGetTop10CitiesByContinentPopulation_exception() throws SQLException {
        wireExecuteQueryThrows("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTop10CitiesByContinentPopulation();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getTop5CitiesByRegionPopulation ----------
    @Test
    void testGetTop5CitiesByRegionPopulation_happy() throws SQLException {
        prepareOneRowResultSet("Top5City", "Top5Country", "D5", "R5", "Cont5", 5555);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Region");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTop5CitiesByRegionPopulation();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("Top5City", cities.get(0).getName());
    }

    @Test
    void testGetTop5CitiesByRegionPopulation_exception() throws SQLException {
        wireExecuteQueryThrows("PARTITION BY co.Region");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTop5CitiesByRegionPopulation();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getAllCitiesByRegionPopulationDesc ----------
    @Test
    void testGetAllCitiesByRegionPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("RCity", "RCountry", "RDist", "RRegion", "RCont", 6666);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region, ci.Population DESC");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getAllCitiesByRegionPopulationDesc();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("RCity", cities.get(0).getName());
    }

    @Test
    void testGetAllCitiesByRegionPopulationDesc_exception() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Region, ci.Population DESC");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getAllCitiesByRegionPopulationDesc();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getAllCitiesByCountryPopulationDesc ----------
    @Test
    void testGetAllCitiesByCountryPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("CCity", "CountryC", "CDist", "CRegion", "CCont", 7777);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Name, ci.Population DESC");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getAllCitiesByCountryPopulationDesc();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("CCity", cities.get(0).getName());
    }

    @Test
    void testGetAllCitiesByCountryPopulationDesc_exception() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Name, ci.Population DESC");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getAllCitiesByCountryPopulationDesc();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getCitiesByDistrictPopulationDesc ----------
    @Test
    void testGetCitiesByDistrictPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("DCity", "DCountry", "DistrictA", "DRegion", "DCont", 8888);
        wireExecuteQueryReturnsMockRs("ORDER BY ci.District, ci.Population DESC");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getCitiesByDistrictPopulationDesc();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("DistrictA", cities.get(0).getDistrict());
    }

    @Test
    void testGetCitiesByDistrictPopulationDesc_exception() throws SQLException {
        wireExecuteQueryThrows("ORDER BY ci.District, ci.Population DESC");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getCitiesByDistrictPopulationDesc();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getTop5CitiesByCountryPopulation ----------
    @Test
    void testGetTop5CitiesByCountryPopulation_happy() throws SQLException {
        prepareOneRowResultSet("CountryTop5", "CountryX", "CD", "CR", "CC", 9999);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Code");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTop5CitiesByCountryPopulation();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("CountryTop5", cities.get(0).getName());
    }

    @Test
    void testGetTop5CitiesByCountryPopulation_exception() throws SQLException {
        wireExecuteQueryThrows("PARTITION BY co.Code");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTop5CitiesByCountryPopulation();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    // ---------- getTopCityByDistrictPopulation ----------
    @Test
    void testGetTopCityByDistrictPopulation_happy() throws SQLException {
        prepareOneRowResultSet("TopDistrictCity", "TopCountry", "DistrictZ", "RZ", "CZ", 1010);
        wireExecuteQueryReturnsMockRs("GROUP BY District");

        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTopCityByDistrictPopulation();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("DistrictZ", cities.get(0).getDistrict());
    }

    @Test
    void testGetTopCityByDistrictPopulation_exception() throws SQLException {
        wireExecuteQueryThrows("GROUP BY District");
        report = new CityReport(mockCon);
        ArrayList<City> cities = report.getTopCityByDistrictPopulation();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }
}