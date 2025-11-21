package com.napier.population;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

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
        if (!mockRs.next()) {
            when(mockRs.next()).thenReturn(true).thenReturn(false);
        } else {
            when(mockRs.next()).thenReturn(false);
        }

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
        lenient().when(mockStmt.executeQuery(contains(sqlContains))).thenReturn(mockRs);
    }

    private void wireExecuteQueryThrows(String sqlFragment) throws SQLException {
        lenient().when(mockStmt.executeQuery(ArgumentMatchers.contains(sqlFragment)))
                .thenThrow(new SQLException("simulated"));
    }

    // -------- Tests for getAllCapitalCitiesByPopulationDesc --------
    @Test
    void testGetAllCapitalCitiesByPopulationDesc_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY ci.Population DESC");

        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByPopulationDesc();

        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs");
    }

    @Test
    void testGetAllCapitalCitiesByPopulationDesc_containsNullMember() throws SQLException {
        // Mock result set returning one row but with a null city name
        prepareOneRowResultSet(null, "CountryA", "DistrictA", "RegionA", "ContA", 1000);
        wireExecuteQueryReturnsMockRs("ORDER BY ci.Population DESC");

        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByPopulationDesc();

        assertNull(list.get(0).getName(), "Expected null city name in result list");
    }

    @Test
    void testGetAllCapitalCitiesByPopulationDesc_returnsValidList() throws SQLException {
        prepareOneRowResultSet("CapA", "CountryA", "DistrictA", "RegionA", "ContA", 1000);
        wireExecuteQueryReturnsMockRs("ORDER BY ci.Population DESC");

        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByPopulationDesc();

        assertEquals("CapA", list.get(0).getName(), "Expected valid city name for happy path");
    }

    // -------- Tests for getAllCapitalCitiesByContinentPopulationDesc --------
    @Test
    void testGetAllCapitalCitiesByContinentPopulationDesc_sqlException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Continent");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByContinentPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list on SQL exception");
    }

    @Test
    void testGetAllCapitalCitiesByContinentPopulationDesc_nullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryB", "DistrictB", "RegionB", "ContB", 2000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Continent");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByContinentPopulationDesc();
        assertNull(list.get(0).getName(), "Expected null city name in result list");
    }

    @Test
    void testGetAllCapitalCitiesByContinentPopulationDesc_normalList() throws SQLException {
        prepareOneRowResultSet("ContCap", "CountryB", "DistrictB", "RegionB", "ContB", 2000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Continent");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByContinentPopulationDesc();
        assertEquals("ContCap", list.get(0).getName(), "Expected valid city name for happy path");
    }

    // ---------- Tests for getAllCapitalCitiesByRegionPopulationDesc ----------
    @Test
    void testGetAllCapitalCitiesByRegionPopulationDesc_sqlException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Region, ci.Population DESC");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByRegionPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list on SQL exception");
    }

    @Test
    void testGetAllCapitalCitiesByRegionPopulationDesc_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryC", "DistrictC", "RegionC", "ContC", 3000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region, ci.Population DESC");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByRegionPopulationDesc();
        assertNull(list.get(0).getName(), "Expected null city name in result list");
    }

    @Test
    void testGetAllCapitalCitiesByRegionPopulationDesc_normalList() throws SQLException {
        prepareOneRowResultSet("RegionCap", "CountryC", "DistrictC", "RegionC", "ContC", 3000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region, ci.Population DESC");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getAllCapitalCitiesByRegionPopulationDesc();
        assertEquals("RegionCap", list.get(0).getName(), "Expected valid city name for happy path");
    }

    // ---------- Tests for getTop50CapitalCitiesByPopulation ----------
    @Test
    void testGetTop50CapitalCitiesByPopulation_sqlException() throws SQLException {
        wireExecuteQueryThrows("LIMIT 50");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop50CapitalCitiesByPopulation();
        assertTrue(list.isEmpty(), "Expected empty list on SQL exception");
    }

    @Test
    void testGetTop50CapitalCitiesByPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryD", "DistrictD", "RegionD", "ContD", 4000);
        wireExecuteQueryReturnsMockRs("LIMIT 50");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop50CapitalCitiesByPopulation();
        assertNull(list.get(0).getName(), "Expected null city name in result list");
    }

    @Test
    void testGetTop50CapitalCitiesByPopulation_normalList() throws SQLException {
        prepareOneRowResultSet("Top50", "CountryD", "DistrictD", "RegionD", "ContD", 4000);
        wireExecuteQueryReturnsMockRs("LIMIT 50");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop50CapitalCitiesByPopulation();
        assertEquals("Top50", list.get(0).getName(), "Expected valid city name for happy path");
    }

    // ---------- Tests for getTop10CapitalCitiesByContinentPopulation ----------
    @Test
    void testGetTop10CapitalCitiesByContinentPopulation_sqlException() throws SQLException {
        wireExecuteQueryThrows("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop10CapitalCitiesByContinentPopulation();
        assertTrue(list.isEmpty(), "Expected empty list on SQL exception");
    }

    @Test
    void testGetTop10CapitalCitiesByContinentPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryE", "DistrictE", "RegionE", "ContE", 5000);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop10CapitalCitiesByContinentPopulation();
        assertNull(list.get(0).getName(), "Expected null city name in result list");
    }

    @Test
    void testGetTop10CapitalCitiesByContinentPopulation_normalList() throws SQLException {
        prepareOneRowResultSet("Top10ContCap", "CountryE", "DistrictE", "RegionE", "ContE", 5000);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop10CapitalCitiesByContinentPopulation();
        assertEquals("Top10ContCap", list.get(0).getName(), "Expected valid city name for happy path");
    }

    // ---------- Tests for getTop5CapitalCitiesByRegion ----------
    @Test
    void testGetTop5CapitalCitiesByRegion_sqlException() throws SQLException {
        wireExecuteQueryThrows("PARTITION BY co.Region");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop5CapitalCitiesByRegion();
        assertTrue(list.isEmpty(), "Expected empty list on SQL exception");
    }

    @Test
    void testGetTop5CapitalCitiesByRegion_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryF", "DistrictF", "RegionF", "ContF", 6000);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Region");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop5CapitalCitiesByRegion();
        assertNull(list.get(0).getName(), "Expected null city name in result list");
    }

    @Test
    void testGetTop5CapitalCitiesByRegion_normalList() throws SQLException {
        prepareOneRowResultSet("Top5RegCap", "CountryF", "DistrictF", "RegionF", "ContF", 6000);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Region");
        report = new CapitalCityReport(mockCon);
        List<City> list = report.getTop5CapitalCitiesByRegion();
        assertEquals("Top5RegCap", list.get(0).getName(), "Expected valid city name for happy path");
    }
}