package com.napier.population;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;

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

    // ---------- Helper ----------
    private void prepareOneRowResultSet(String code, String name, String capitalName, String district,
                                        String region, String continent, int population) throws SQLException {
        when(mockRs.next()).thenReturn(true).thenReturn(false);

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

    private void wireExecuteQueryReturnsEmptyRs() throws SQLException {
        ResultSet emptyRs = mock(ResultSet.class);
        when(emptyRs.next()).thenReturn(false);
        lenient().when(mockStmt.executeQuery(anyString())).thenReturn(emptyRs);
    }

    // ---------- Tests ----------

    // 1. getAllCountriesByPopulationDesc
    @Test
    void testGetAllCountriesByPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("C1", "CountryA", "CapA", "DistrictA", "RegionA", "ContA", 1000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Population DESC");

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getAllCountriesByPopulationDesc();

        assertNotNull(list);
        assertEquals(1, list.size());
        Country c = list.get(0);
        assertEquals("C1", c.getCode());
        assertEquals("CountryA", c.getName());
        assertEquals("CapA", c.getCapitalName());
        assertEquals("DistrictA", c.getDistrict());
        assertEquals("RegionA", c.getRegion());
        assertEquals("ContA", c.getContinent());
        assertEquals(1000, c.getPopulation());
    }

    @Test
    void testGetAllCountriesByPopulationDesc_empty() throws SQLException {
        wireExecuteQueryReturnsEmptyRs();

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getAllCountriesByPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void testGetAllCountriesByPopulationDesc_sqlException() throws SQLException {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("simulated SQL error"));

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getAllCountriesByPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // 2. getCountriesByContinentPopulationDesc
    @Test
    void testGetCountriesByContinentPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("C2", "CountryB", "CapB", "DistrictB", "RegionB", "ContB", 2000);
        wireExecuteQueryReturnsMockRs("ORDER BY c.Continent, c.Population DESC");

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getCountriesByContinentPopulationDesc();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("CountryB", list.get(0).getName());
    }

    @Test
    void testGetCountriesByContinentPopulationDesc_empty() throws SQLException {
        wireExecuteQueryReturnsEmptyRs();

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getCountriesByContinentPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void testGetCountriesByContinentPopulationDesc_sqlException() throws SQLException {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("simulated SQL error"));

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getCountriesByContinentPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // 3. getCountriesByRegionPopulationDesc
    @Test
    void testGetCountriesByRegionPopulationDesc_happy() throws SQLException {
        prepareOneRowResultSet("C3", "CountryC", "CapC", "DistrictC", "RegionC", "ContC", 3000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region ASC, co.Population DESC");

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getCountriesByRegionPopulationDesc();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("CountryC", list.get(0).getName());
    }

    @Test
    void testGetCountriesByRegionPopulationDesc_empty() throws SQLException {
        wireExecuteQueryReturnsEmptyRs();

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getCountriesByRegionPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void testGetCountriesByRegionPopulationDesc_sqlException() throws SQLException {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("simulated SQL error"));

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getCountriesByRegionPopulationDesc();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // 4. getTop50CountriesByPopulation
    @Test
    void testGetTop50CountriesByPopulation_happy() throws SQLException {
        prepareOneRowResultSet("C4", "CountryD", "CapD", "DistrictD", "RegionD", "ContD", 4000);
        wireExecuteQueryReturnsMockRs("LIMIT 50");

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop50CountriesByPopulation();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("CountryD", list.get(0).getName());
    }

    @Test
    void testGetTop50CountriesByPopulation_empty() throws SQLException {
        wireExecuteQueryReturnsEmptyRs();

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop50CountriesByPopulation();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void testGetTop50CountriesByPopulation_sqlException() throws SQLException {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("simulated SQL error"));

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop50CountriesByPopulation();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // 5. getTop10CountriesByContinentPopulation
    @Test
    void testGetTop10CountriesByContinentPopulation_happy() throws SQLException {
        prepareOneRowResultSet("C5", "CountryE", "CapE", "DistrictE", "RegionE", "ContE", 5000);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop10CountriesByContinentPopulation();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("CountryE", list.get(0).getName());
    }

    @Test
    void testGetTop10CountriesByContinentPopulation_empty() throws SQLException {
        wireExecuteQueryReturnsEmptyRs();

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop10CountriesByContinentPopulation();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void testGetTop10CountriesByContinentPopulation_sqlException() throws SQLException {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("simulated SQL error"));

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop10CountriesByContinentPopulation();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // 6. getTop5CountriesPerRegion
    @Test
    void testGetTop5CountriesPerRegion_happy() throws SQLException {
        prepareOneRowResultSet("C6", "CountryF", "CapF", "DistrictF", "RegionF", "ContF", 6000);
        wireExecuteQueryReturnsMockRs("PARTITION BY c.Region");

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop5CountriesPerRegion();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("CountryF", list.get(0).getName());
    }

    @Test
    void testGetTop5CountriesPerRegion_empty() throws SQLException {
        wireExecuteQueryReturnsEmptyRs();

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop5CountriesPerRegion();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void testGetTop5CountriesPerRegion_sqlException() throws SQLException {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("simulated SQL error"));

        report = new CountryReport(mockCon);
        ArrayList<Country> list = report.getTop5CountriesPerRegion();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }
}

