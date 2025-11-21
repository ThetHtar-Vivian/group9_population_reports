package com.napier.population;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryReportTest {

    @Mock Connection mockCon;
    @Mock Statement mockStmt;
    @Mock ResultSet mockRs;

    private CountryReport report;

    @BeforeEach
    void setup() throws SQLException {
        when(mockCon.createStatement()).thenReturn(mockStmt);
    }

    private void prepareOneRowResultSet(String code, String name, String capitalName, String district,
                                        String region, String continent, int population) throws SQLException {
        if (!mockRs.next()) {
            when(mockRs.next()).thenReturn(true).thenReturn(false);
        } else {
            when(mockRs.next()).thenReturn(false);
        }

        lenient().when(mockRs.getString(anyString())).thenAnswer(invocation -> {
            String col = invocation.getArgument(0);
            switch (col) {
                case "Code": return code;
                case "CountryName": return name;
                case "CapitalName": return capitalName;
                case "District":
                case "CapitalDistrict": return district;
                case "Region": return region;
                case "Continent": return continent;
                default: return null;
            }
        });

        lenient().when(mockRs.getInt(anyString())).thenAnswer(invocation -> {
            if ("Population".equals(invocation.getArgument(0))) return population;
            return 0;
        });
    }

    private void wireExecuteQueryReturnsMockRs(String sqlContains) throws SQLException {
        lenient().when(mockStmt.executeQuery(contains(sqlContains))).thenReturn(mockRs);
    }

    private void wireExecuteQueryThrows(String sqlContains) throws SQLException {
        when(mockStmt.executeQuery(contains(sqlContains))).thenThrow(new SQLException("simulated SQL error"));
    }

    // -------- Tests for getAllCountriesByPopulationDesc --------
    @Test
    void testGetAllCountriesByPopulationDesc_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getAllCountriesByPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getAllCountriesByPopulationDesc");
    }

    @Test
    void testGetAllCountriesByPopulationDesc_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryA", "CapA", "DistrictA", "RegionA", "ContA", 1000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getAllCountriesByPopulationDesc();
        assertNull(list.get(0).getCode(), "Expected null country code in getAllCountriesByPopulationDesc result list");
    }

    @Test
    void testGetAllCountriesByPopulationDesc_returnsValidList() throws SQLException {
        prepareOneRowResultSet("C1", "CountryA", "CapA", "DistrictA", "RegionA", "ContA", 1000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getAllCountriesByPopulationDesc();
        assertEquals("C1", list.get(0).getCode(), "Expected valid country code 'C1' in getAllCountriesByPopulationDesc result list");
    }

    // -------- Tests for getCountriesByContinentPopulationDesc --------
    @Test
    void testGetCountriesByContinentPopulationDesc_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY c.Continent, c.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getCountriesByContinentPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getCountriesByContinentPopulationDesc");
    }

    @Test
    void testGetCountriesByContinentPopulationDesc_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryB", "CapB", "DistrictB", "RegionB", "ContB", 2000);
        wireExecuteQueryReturnsMockRs("ORDER BY c.Continent, c.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getCountriesByContinentPopulationDesc();
        assertNull(list.get(0).getCode(), "Expected null country code in getCountriesByContinentPopulationDesc result list");
    }

    @Test
    void testGetCountriesByContinentPopulationDesc_returnsValidList() throws SQLException {
        prepareOneRowResultSet("C2", "CountryB", "CapB", "DistrictB", "RegionB", "ContB", 2000);
        wireExecuteQueryReturnsMockRs("ORDER BY c.Continent, c.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getCountriesByContinentPopulationDesc();
        assertEquals("C2", list.get(0).getCode(), "Expected valid country code 'C2' in getCountriesByContinentPopulationDesc result list");
    }

    // -------- Tests for getCountriesByRegionPopulationDesc --------
    @Test
    void testGetCountriesByRegionPopulationDesc_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Region ASC, co.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getCountriesByRegionPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getCountriesByRegionPopulationDesc");
    }

    @Test
    void testGetCountriesByRegionPopulationDesc_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryC", "CapC", "DistrictC", "RegionC", "ContC", 3000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region ASC, co.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getCountriesByRegionPopulationDesc();
        assertNull(list.get(0).getCode(), "Expected null country code in getCountriesByRegionPopulationDesc result list");
    }

    @Test
    void testGetCountriesByRegionPopulationDesc_returnsValidList() throws SQLException {
        prepareOneRowResultSet("C3", "CountryC", "CapC", "DistrictC", "RegionC", "ContC", 3000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region ASC, co.Population DESC");
        report = new CountryReport(mockCon);
        List<Country> list = report.getCountriesByRegionPopulationDesc();
        assertEquals("C3", list.get(0).getCode(), "Expected valid country code 'C3' in getCountriesByRegionPopulationDesc result list");
    }

    // -------- Tests for getTop50CountriesByPopulation --------
    @Test
    void testGetTop50CountriesByPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("LIMIT 50");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop50CountriesByPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getTop50CountriesByPopulation");
    }

    @Test
    void testGetTop50CountriesByPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryD", "CapD", "DistrictD", "RegionD", "ContD", 4000);
        wireExecuteQueryReturnsMockRs("LIMIT 50");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop50CountriesByPopulation();
        assertNull(list.get(0).getCode(), "Expected null country code in getTop50CountriesByPopulation result list");
    }

    @Test
    void testGetTop50CountriesByPopulation_returnsValidList() throws SQLException {
        prepareOneRowResultSet("C4", "CountryD", "CapD", "DistrictD", "RegionD", "ContD", 4000);
        wireExecuteQueryReturnsMockRs("LIMIT 50");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop50CountriesByPopulation();
        assertEquals("C4", list.get(0).getCode(), "Expected valid country code 'C4' in getTop50CountriesByPopulation result list");
    }

    // -------- Tests for getTop10CountriesByContinentPopulation --------
    @Test
    void testGetTop10CountriesByContinentPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop10CountriesByContinentPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getTop10CountriesByContinentPopulation");
    }

    @Test
    void testGetTop10CountriesByContinentPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryE", "CapE", "DistrictE", "RegionE", "ContE", 5000);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop10CountriesByContinentPopulation();
        assertNull(list.get(0).getCode(), "Expected null country code in getTop10CountriesByContinentPopulation result list");
    }

    @Test
    void testGetTop10CountriesByContinentPopulation_returnsValidList() throws SQLException {
        prepareOneRowResultSet("C5", "CountryE", "CapE", "DistrictE", "RegionE", "ContE", 5000);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop10CountriesByContinentPopulation();
        assertEquals("C5", list.get(0).getCode(), "Expected valid country code 'C5' in getTop10CountriesByContinentPopulation result list");
    }

    // -------- Tests for getTop5CountriesPerRegion --------
    @Test
    void testGetTop5CountriesPerRegion_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("PARTITION BY c.Region");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop5CountriesPerRegion();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getTop5CountriesPerRegion");
    }

    @Test
    void testGetTop5CountriesPerRegion_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryF", "CapF", "DistrictF", "RegionF", "ContF", 6000);
        wireExecuteQueryReturnsMockRs("PARTITION BY c.Region");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop5CountriesPerRegion();
        assertNull(list.get(0).getCode(), "Expected null country code in getTop5CountriesPerRegion result list");
    }

    @Test
    void testGetTop5CountriesPerRegion_returnsValidList() throws SQLException {
        prepareOneRowResultSet("C6", "CountryF", "CapF", "DistrictF", "RegionF", "ContF", 6000);
        wireExecuteQueryReturnsMockRs("PARTITION BY c.Region");
        report = new CountryReport(mockCon);
        List<Country> list = report.getTop5CountriesPerRegion();
        assertEquals("C6", list.get(0).getCode(), "Expected valid country code 'C6' in getTop5CountriesPerRegion result list");
    }
}
