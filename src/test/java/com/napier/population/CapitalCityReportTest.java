package com.napier.population;

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
 * Full Mockito-based unit tests for CapitalCityReport.
 * Covers all major query methods with both happy and exception paths.
 * No real database connection required.
 */
@ExtendWith(MockitoExtension.class)
class CapitalCityReportTest {

    @Mock Connection mockCon;
    @Mock Statement mockStmt;
    @Mock ResultSet mockRs;

    private CapitalCityReport report;

    @BeforeEach
    void setup() throws SQLException {
        when(mockCon.createStatement()).thenReturn(mockStmt);
    }

    /**
     * Helper: mock a one-row ResultSet for capital city queries.
     * Includes District column for accurate City mapping.
     */
    private void prepareOneRowResultSet(String cityName, String countryName,
                                        String district, String region, String continent, int population) throws SQLException {
        when(mockRs.next()).thenReturn(true).thenReturn(false);

        lenient().when(mockRs.getString(anyString())).thenAnswer(invocation -> {
            String col = invocation.getArgument(0);
            switch (col) {
                case "CityName":
                    return cityName;
                case "CountryName":
                    return countryName;
                case "District":
                    return district;
                case "Region":
                    return region;
                case "Continent":
                    return continent;
                case "City":
                    return cityName;
                case "Country":
                    return countryName;
                default:
                    return null;
            }
        });

        lenient().when(mockRs.getInt(anyString())).thenAnswer(invocation -> {
            String col = invocation.getArgument(0);
            if ("Population".equals(col)) return population;
            return 0;
        });
    }

    private void wireExecuteQueryReturnsMockRs(String sqlContains) throws SQLException {
        lenient().when(mockStmt.executeQuery(contains("FROM city"))).thenReturn(mockRs);
    }

    private void wireExecuteQueryThrows(String sqlFragment) throws SQLException {
        lenient().when(mockStmt.executeQuery(ArgumentMatchers.contains(sqlFragment)))
                .thenThrow(new SQLException("simulated"));
    }

    // ---------- getAllCapitalCitiesByPopulationDesc ----------
    @Test
    void testGetAllCapitalCitiesByPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("CapA", "CountryA", "DistrictA", "RegionA", "ContA", 1000);
        wireExecuteQueryReturnsMockRs("ORDER BY ci.Population DESC");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getAllCapitalCitiesByPopulationDesc();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("CapA", list.get(0).getName());
    }

    @Test
    void testGetAllCapitalCitiesByPopulationDesc_exception() throws SQLException {
        wireExecuteQueryThrows("ORDER BY ci.Population DESC");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getAllCapitalCitiesByPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // ---------- getAllCapitalCitiesByContinentPopulationDesc ----------
    @Test
    void testGetAllCapitalCitiesByContinentPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("ContCap", "CountryB", "DistrictB", "RegionB", "ContB", 2000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Continent, ci.Population DESC");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getAllCapitalCitiesByContinentPopulationDesc();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("ContCap", list.get(0).getName());
    }

    @Test
    void testGetAllCapitalCitiesByContinentPopulationDesc_exception() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Continent");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getAllCapitalCitiesByContinentPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // ---------- getAllCapitalCitiesByRegionPopulationDesc ----------
    @Test
    void testGetAllCapitalCitiesByRegionPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("RegionCap", "CountryC", "DistrictC", "RegionC", "ContC", 3000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region, ci.Population DESC");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getAllCapitalCitiesByRegionPopulationDesc();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("RegionCap", list.get(0).getName());
    }

    @Test
    void testGetAllCapitalCitiesByRegionPopulationDesc_exception() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Region, ci.Population DESC");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getAllCapitalCitiesByRegionPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // ---------- getTop50CapitalCitiesByPopulation ----------
    @Test
    void testGetTop50CapitalCitiesByPopulation_happy() throws SQLException {
        prepareOneRowResultSet("Top50", "CountryD", "DistrictD", "RegionD", "ContD", 4000);
        wireExecuteQueryReturnsMockRs("LIMIT 50");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getTop50CapitalCitiesByPopulation();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Top50", list.get(0).getName());
    }

    @Test
    void testGetTop50CapitalCitiesByPopulation_exception() throws SQLException {
        wireExecuteQueryThrows("LIMIT 50");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getTop50CapitalCitiesByPopulation();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // ---------- getTop10CapitalCitiesByContinentPopulation ----------
    @Test
    void testGetTop10CapitalCitiesByContinentPopulation_happy() throws SQLException {
        prepareOneRowResultSet("Top10ContCap", "CountryE", "DistrictE", "RegionE", "ContE", 5000);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getTop10CapitalCitiesByContinentPopulation();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Top10ContCap", list.get(0).getName());
    }

    @Test
    void testGetTop10CapitalCitiesByContinentPopulation_exception() throws SQLException {
        wireExecuteQueryThrows("ROW_NUMBER() OVER (PARTITION BY co.Continent");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getTop10CapitalCitiesByContinentPopulation();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // ---------- getTop5CapitalCitiesByRegion ----------
    @Test
    void testGetTop5CapitalCitiesByRegion_happy() throws SQLException {
        prepareOneRowResultSet("Top5RegCap", "CountryF", "DistrictF", "RegionF", "ContF", 6000);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Region");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getTop5CapitalCitiesByRegion();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Top5RegCap", list.get(0).getName());
    }

    @Test
    void testGetTop5CapitalCitiesByRegion_exception() throws SQLException {
        wireExecuteQueryThrows("PARTITION BY co.Region");

        report = new CapitalCityReport(mockCon);
        ArrayList<City> list = report.getTop5CapitalCitiesByRegion();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

}