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

@ExtendWith(MockitoExtension.class)
class CityReportTest {

    @Mock Connection mockCon;
    @Mock Statement mockStmt;
    @Mock ResultSet mockRs;

    private CityReport report;

    @BeforeEach
    void setup() throws SQLException {
        when(mockCon.createStatement()).thenReturn(mockStmt);
    }

    private void prepareOneRowResultSet(String cityName, String countryName, String district,
                                        String region, String continent, int population) throws SQLException {
        if (!mockRs.next()) {
            when(mockRs.next()).thenReturn(true).thenReturn(false);
        } else {
            when(mockRs.next()).thenReturn(false);
        }

        lenient().when(mockRs.getString(anyString())).thenAnswer(invocation -> {
            String col = invocation.getArgument(0);
            switch (col) {
                case "CityName":
                case "City": return cityName;
                case "CountryName":
                case "Country": return countryName;
                case "District": return district;
                case "Region": return region;
                case "Continent": return continent;
                default: return null;
            }
        });

        lenient().when(mockRs.getInt(anyString())).thenAnswer(invocation -> {
            String col = invocation.getArgument(0);
            if ("Population".equals(col)) return population;
            return 0;
        });
    }

    private void wireExecuteQueryReturnsMockRs(String sqlContains) throws SQLException {
        when(mockStmt.executeQuery(ArgumentMatchers.contains(sqlContains))).thenReturn(mockRs);
    }

    private void wireExecuteQueryThrows(String sqlContains) throws SQLException {
        when(mockStmt.executeQuery(ArgumentMatchers.contains(sqlContains))).thenThrow(new SQLException("simulated"));
    }

    // ---------- getAllCitiesByPopulation ----------
    @Test
    void testGetAllCitiesByPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY c.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getAllCitiesByPopulation");
    }

    @Test
    void testGetAllCitiesByPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryA", "DistrictA", "RegionA", "ContA", 1000);
        wireExecuteQueryReturnsMockRs("ORDER BY c.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByPopulation();
        assertNull(list.get(0).getName(), "Expected null city name in getAllCitiesByPopulation result list");
    }

    @Test
    void testGetAllCitiesByPopulation_returnsValidList() throws SQLException {
        prepareOneRowResultSet("CityA", "CountryA", "DistrictA", "RegionA", "ContA", 1000);
        wireExecuteQueryReturnsMockRs("ORDER BY c.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByPopulation();
        assertEquals("CityA", list.get(0).getName(), "Expected valid city name 'CityA' for happy path");
    }

    // ---------- getCitiesByContinentPopulationDesc ----------
    @Test
    void testGetCitiesByContinentPopulationDesc_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Continent, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getCitiesByContinentPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getCitiesByContinentPopulationDesc");
    }

    @Test
    void testGetCitiesByContinentPopulationDesc_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryC", "D", "R", "ContC", 2000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Continent, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getCitiesByContinentPopulationDesc();
        assertNull(list.get(0).getName(), "Expected null city name in getCitiesByContinentPopulationDesc result list");
    }

    @Test
    void testGetCitiesByContinentPopulationDesc_returnsValidList() throws SQLException {
        prepareOneRowResultSet("CityC", "CountryC", "D", "R", "ContC", 2000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Continent, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getCitiesByContinentPopulationDesc();
        assertEquals("CityC", list.get(0).getName(), "Expected valid city name 'CityC' in getCitiesByContinentPopulationDesc");
    }

    // ---------- getTop50CitiesByPopulation ----------
    @Test
    void testGetTop50CitiesByPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("LIMIT 50");
        report = new CityReport(mockCon);
        List<City> list = report.getTop50CitiesByPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getTop50CitiesByPopulation");
    }

    @Test
    void testGetTop50CitiesByPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryX", "D", "R", "C", 3000);
        wireExecuteQueryReturnsMockRs("LIMIT 50");
        report = new CityReport(mockCon);
        List<City> list = report.getTop50CitiesByPopulation();
        assertNull(list.get(0).getName(), "Expected null city name in getTop50CitiesByPopulation result list");
    }

    @Test
    void testGetTop50CitiesByPopulation_returnsValidList() throws SQLException {
        prepareOneRowResultSet("Top50City", "CountryX", "D", "R", "C", 3000);
        wireExecuteQueryReturnsMockRs("LIMIT 50");
        report = new CityReport(mockCon);
        List<City> list = report.getTop50CitiesByPopulation();
        assertEquals("Top50City", list.get(0).getName(), "Expected valid city name 'Top50City' in getTop50CitiesByPopulation");
    }

    // ---------- getTop10CitiesByContinentPopulation ----------
    @Test
    void testGetTop10CitiesByContinentPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CityReport(mockCon);
        List<City> list = report.getTop10CitiesByContinentPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getTop10CitiesByContinentPopulation");
    }

    @Test
    void testGetTop10CitiesByContinentPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "Country10", "D10", "R10", "Cont10", 4000);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CityReport(mockCon);
        List<City> list = report.getTop10CitiesByContinentPopulation();
        assertNull(list.get(0).getName(), "Expected null city name in getTop10CitiesByContinentPopulation result list");
    }

    @Test
    void testGetTop10CitiesByContinentPopulation_returnsValidList() throws SQLException {
        prepareOneRowResultSet("Top10City", "Country10", "D10", "R10", "Cont10", 4000);
        wireExecuteQueryReturnsMockRs("ROW_NUMBER() OVER (PARTITION BY co.Continent");
        report = new CityReport(mockCon);
        List<City> list = report.getTop10CitiesByContinentPopulation();
        assertEquals("Top10City", list.get(0).getName(), "Expected valid city name 'Top10City' in getTop10CitiesByContinentPopulation");
    }

    // ---------- getTop5CitiesByRegionPopulation ----------
    @Test
    void testGetTop5CitiesByRegionPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("PARTITION BY co.Region");
        report = new CityReport(mockCon);
        List<City> list = report.getTop5CitiesByRegionPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getTop5CitiesByRegionPopulation");
    }

    @Test
    void testGetTop5CitiesByRegionPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "Country5", "D5", "R5", "C5", 5000);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Region");
        report = new CityReport(mockCon);
        List<City> list = report.getTop5CitiesByRegionPopulation();
        assertNull(list.get(0).getName(), "Expected null city name in getTop5CitiesByRegionPopulation result list");
    }

    @Test
    void testGetTop5CitiesByRegionPopulation_returnsValidList() throws SQLException {
        prepareOneRowResultSet("Top5City", "Country5", "D5", "R5", "C5", 5000);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Region");
        report = new CityReport(mockCon);
        List<City> list = report.getTop5CitiesByRegionPopulation();
        assertEquals("Top5City", list.get(0).getName(), "Expected valid city name 'Top5City' in getTop5CitiesByRegionPopulation");
    }

    // ---------- getAllCitiesByRegionPopulationDesc ----------
    @Test
    void testGetAllCitiesByRegionPopulationDesc_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Region, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByRegionPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getAllCitiesByRegionPopulationDesc");
    }

    @Test
    void testGetAllCitiesByRegionPopulationDesc_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryR", "DR", "RR", "CR", 6000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByRegionPopulationDesc();
        assertNull(list.get(0).getName(), "Expected null city name in getAllCitiesByRegionPopulationDesc result list");
    }

    @Test
    void testGetAllCitiesByRegionPopulationDesc_returnsValidList() throws SQLException {
        prepareOneRowResultSet("RCity", "CountryR", "DR", "RR", "CR", 6000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Region, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByRegionPopulationDesc();
        assertEquals("RCity", list.get(0).getName(), "Expected valid city name 'RCity' in getAllCitiesByRegionPopulationDesc");
    }

    // ---------- getAllCitiesByCountryPopulationDesc ----------
    @Test
    void testGetAllCitiesByCountryPopulationDesc_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY co.Name, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByCountryPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getAllCitiesByCountryPopulationDesc");
    }

    @Test
    void testGetAllCitiesByCountryPopulationDesc_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryC", "D", "R", "C", 7000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Name, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByCountryPopulationDesc();
        assertNull(list.get(0).getName(), "Expected null city name in getAllCitiesByCountryPopulationDesc result list");
    }

    @Test
    void testGetAllCitiesByCountryPopulationDesc_returnsValidList() throws SQLException {
        prepareOneRowResultSet("CCity", "CountryC", "D", "R", "C", 7000);
        wireExecuteQueryReturnsMockRs("ORDER BY co.Name, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getAllCitiesByCountryPopulationDesc();
        assertEquals("CCity", list.get(0).getName(), "Expected valid city name 'CCity' in getAllCitiesByCountryPopulationDesc");
    }

    // ---------- getCitiesByDistrictPopulationDesc ----------
    @Test
    void testGetCitiesByDistrictPopulationDesc_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("ORDER BY ci.District, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getCitiesByDistrictPopulationDesc();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getCitiesByDistrictPopulationDesc");
    }

    @Test
    void testGetCitiesByDistrictPopulationDesc_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryD", "DistrictD", "R", "C", 8000);
        wireExecuteQueryReturnsMockRs("ORDER BY ci.District, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getCitiesByDistrictPopulationDesc();
        assertNull(list.get(0).getName(), "Expected null city name in getCitiesByDistrictPopulationDesc result list");
    }

    @Test
    void testGetCitiesByDistrictPopulationDesc_returnsValidList() throws SQLException {
        prepareOneRowResultSet("DCity", "CountryD", "DistrictD", "R", "C", 8000);
        wireExecuteQueryReturnsMockRs("ORDER BY ci.District, ci.Population DESC");
        report = new CityReport(mockCon);
        List<City> list = report.getCitiesByDistrictPopulationDesc();
        assertEquals("DCity", list.get(0).getName(), "Expected valid city name 'DCity' in getCitiesByDistrictPopulationDesc");
    }

    // ---------- getTop5CitiesByCountryPopulation ----------
    @Test
    void testGetTop5CitiesByCountryPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("PARTITION BY co.Code");
        report = new CityReport(mockCon);
        List<City> list = report.getTop5CitiesByCountryPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getTop5CitiesByCountryPopulation");
    }

    @Test
    void testGetTop5CitiesByCountryPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryX", "D", "R", "C", 9000);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Code");
        report = new CityReport(mockCon);
        List<City> list = report.getTop5CitiesByCountryPopulation();
        assertNull(list.get(0).getName(), "Expected null city name in getTop5CitiesByCountryPopulation result list");
    }

    @Test
    void testGetTop5CitiesByCountryPopulation_returnsValidList() throws SQLException {
        prepareOneRowResultSet("CountryTop5", "CountryX", "D", "R", "C", 9000);
        wireExecuteQueryReturnsMockRs("PARTITION BY co.Code");
        report = new CityReport(mockCon);
        List<City> list = report.getTop5CitiesByCountryPopulation();
        assertEquals("CountryTop5", list.get(0).getName(), "Expected valid city name 'CountryTop5' in getTop5CitiesByCountryPopulation");
    }

    // ---------- getTopCityByDistrictPopulation ----------
    @Test
    void testGetTopCityByDistrictPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows("GROUP BY District");
        report = new CityReport(mockCon);
        List<City> list = report.getTopCityByDistrictPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQL exception occurs in getTopCityByDistrictPopulation");
    }

    @Test
    void testGetTopCityByDistrictPopulation_containsNullMember() throws SQLException {
        prepareOneRowResultSet(null, "CountryZ", "DistrictZ", "RZ", "CZ", 1010);
        wireExecuteQueryReturnsMockRs("GROUP BY District");
        report = new CityReport(mockCon);
        List<City> list = report.getTopCityByDistrictPopulation();
        assertNull(list.get(0).getName(), "Expected null city name in getTopCityByDistrictPopulation result list");
    }

    @Test
    void testGetTopCityByDistrictPopulation_returnsValidList() throws SQLException {
        prepareOneRowResultSet("TopDistrictCity", "CountryZ", "DistrictZ", "RZ", "CZ", 1010);
        wireExecuteQueryReturnsMockRs("GROUP BY District");
        report = new CityReport(mockCon);
        List<City> list = report.getTopCityByDistrictPopulation();
        assertEquals("TopDistrictCity", list.get(0).getName(), "Expected valid city name 'TopDistrictCity' in getTopCityByDistrictPopulation");
    }
}