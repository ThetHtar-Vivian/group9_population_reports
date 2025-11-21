package com.napier.population;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PopulationReportTest {

    @Mock private Connection mockCon;
    @Mock private Statement mockStmt;
    @Mock private ResultSet mockRset;

    private PopulationReport populationReport;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockCon.createStatement()).thenReturn(mockStmt);
        populationReport = new PopulationReport(mockCon);
    }

    // ---------- Helpers ----------
    private void prepareOneRowPopulationResult(String name, Long total, Long city, Long nonCity) throws SQLException {
        if (!mockRset.next()) {
            when(mockRset.next()).thenReturn(true).thenReturn(false);
        } else {
            when(mockRset.next()).thenReturn(false);
        }
        when(mockRset.getString(anyString())).thenReturn(name);
        when(mockRset.getLong("TotalPopulation")).thenReturn(total != null ? total : 0L);
        when(mockRset.getLong("CityPopulation")).thenReturn(city != null ? city : 0L);
        when(mockRset.getLong("NonCityPopulation")).thenReturn(nonCity != null ? nonCity : 0L);
    }

    private void prepareOneRowLanguageResult(String language, Double totalSpeakers, Double worldPercentage) throws SQLException {
        if (!mockRset.next()) {
            when(mockRset.next()).thenReturn(true).thenReturn(false);
        } else {
            when(mockRset.next()).thenReturn(false);
        }
        when(mockRset.getString("language")).thenReturn(language);
        when(mockRset.getDouble("totalSpeakers")).thenReturn(totalSpeakers != null ? totalSpeakers : 0.0);
        when(mockRset.getDouble("worldPercentage")).thenReturn(worldPercentage != null ? worldPercentage : 0.0);
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
    }

    private void wireExecuteQueryReturnsMockRset() throws SQLException {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
    }

    private void wireExecuteQueryThrows() throws SQLException {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("simulated"));
    }

    // ------------------ Tests for getCountryPopulationReport ------------------
    @Test
    void testGetCountryPopulationReport_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getCountryPopulationReport();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getCountryPopulationReport");
    }

    @Test
    void testGetCountryPopulationReport_containsNullMember() throws SQLException {
        prepareOneRowPopulationResult(null, 1000L, 500L, 500L);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getCountryPopulationReport();
        assertNull(list.get(0).getLevel(), "Expected null level for first PeoplePopulation entry in getCountryPopulationReport");
    }

    @Test
    void testGetCountryPopulationReport_returnsValidList() throws SQLException {
        prepareOneRowPopulationResult("France", 67000000L, 45000000L, 22000000L);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getCountryPopulationReport();
        PeoplePopulation pp = list.get(0);
        assertEquals("France", pp.getLevel(), "Expected level to be 'France' for first PeoplePopulation entry");
    }

    // ------------------ Tests for getWorldPopulation ------------------
    @Test
    void testGetWorldPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getWorldPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getWorldPopulation");
    }

    @Test
    void testGetWorldPopulation_containsNullMember() throws SQLException {
        if (!mockRset.next()) {
            when(mockRset.next()).thenReturn(true).thenReturn(false);
        } else {
            when(mockRset.next()).thenReturn(false);
        }

        when(mockRset.getLong("WorldPopulation")).thenReturn(0L);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getWorldPopulation();
        assertEquals(0L, list.get(0).getTotalPopulation(), "Expected totalPopulation 0 for first PeoplePopulation entry in getWorldPopulation");
    }

    @Test
    void testGetWorldPopulation_returnsValidList() throws SQLException {
        if (!mockRset.next()) {
            when(mockRset.next()).thenReturn(true).thenReturn(false);
        } else {
            when(mockRset.next()).thenReturn(false);
        }

        when(mockRset.getLong("WorldPopulation")).thenReturn(8000000000L);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getWorldPopulation();
        assertEquals("World", list.get(0).getLevel(), "Expected level 'World' for first PeoplePopulation entry in getWorldPopulation");
    }

    // ------------------ Tests for getTotalPopulationPerCountry ------------------
    @Test
    void testGetTotalPopulationPerCountry_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getTotalPopulationPerCountry();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getTotalPopulationPerCountry");
    }

    @Test
    void testGetTotalPopulationPerCountry_containsNullMember() throws SQLException {
        prepareOneRowPopulationResult(null, 125000000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getTotalPopulationPerCountry();
        assertNull(list.get(0).getLevel(), "Expected null level for first PeoplePopulation entry in getTotalPopulationPerCountry");
    }

    @Test
    void testGetTotalPopulationPerCountry_returnsValidList() throws SQLException {
        prepareOneRowPopulationResult("Japan", 125000000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getTotalPopulationPerCountry();
        assertEquals("Japan", list.get(0).getLevel(), "Expected level 'Japan' for first PeoplePopulation entry in getTotalPopulationPerCountry");
    }

    // ------------------ Tests for getContinentTotalPopulation ------------------
    @Test
    void testGetContinentTotalPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getContinentTotalPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getContinentTotalPopulation");
    }

    @Test
    void testGetContinentTotalPopulation_containsNullMember() throws SQLException {
        prepareOneRowPopulationResult(null, 4700000000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getContinentTotalPopulation();
        assertNull(list.get(0).getLevel(), "Expected null level for first PeoplePopulation entry in getContinentTotalPopulation");
    }

    @Test
    void testGetContinentTotalPopulation_returnsValidList() throws SQLException {
        prepareOneRowPopulationResult("Asia", 4700000000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getContinentTotalPopulation();
        assertEquals("Asia", list.get(0).getLevel(), "Expected level 'Asia' for first PeoplePopulation entry in getContinentTotalPopulation");
    }

    // ------------------ Tests for getDistrictTotalPopulation ------------------
    @Test
    void testGetDistrictTotalPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getDistrictTotalPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getDistrictTotalPopulation");
    }

    @Test
    void testGetDistrictTotalPopulation_containsNullMember() throws SQLException {
        prepareOneRowPopulationResult(null, 39500000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getDistrictTotalPopulation();
        assertNull(list.get(0).getLevel(), "Expected null level for first PeoplePopulation entry in getDistrictTotalPopulation");
    }

    @Test
    void testGetDistrictTotalPopulation_returnsValidList() throws SQLException {
        prepareOneRowPopulationResult("California", 39500000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getDistrictTotalPopulation();
        assertEquals("California", list.get(0).getLevel(), "Expected level 'California' for first PeoplePopulation entry in getDistrictTotalPopulation");
    }

    // ------------------ Tests for getContinentPopulationReport ------------------
    @Test
    void testGetContinentPopulationReport_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getContinentPopulationReport();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getContinentPopulationReport");
    }

    @Test
    void testGetContinentPopulationReport_containsNullMember() throws SQLException {
        prepareOneRowPopulationResult(null, 750000000L, 500000000L, 250000000L);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getContinentPopulationReport();
        assertNull(list.get(0).getLevel(), "Expected null level for first PeoplePopulation entry in getContinentPopulationReport");
    }

    @Test
    void testGetContinentPopulationReport_returnsValidList() throws SQLException {
        prepareOneRowPopulationResult("Europe", 750000000L, 500000000L, 250000000L);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getContinentPopulationReport();
        assertEquals("Europe", list.get(0).getLevel(), "Expected level 'Europe' for first PeoplePopulation entry in getContinentPopulationReport");
    }

    // ------------------ Tests for getRegionTotalPopulation ------------------
    @Test
    void testGetRegionTotalPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getRegionTotalPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getRegionTotalPopulation");
    }

    @Test
    void testGetRegionTotalPopulation_containsNullMember() throws SQLException {
        prepareOneRowPopulationResult(null, 200000000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getRegionTotalPopulation();
        assertNull(list.get(0).getLevel(), "Expected null level for first PeoplePopulation entry in getRegionTotalPopulation");
    }

    @Test
    void testGetRegionTotalPopulation_returnsValidList() throws SQLException {
        prepareOneRowPopulationResult("Western Europe", 200000000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getRegionTotalPopulation();
        assertEquals("Western Europe", list.get(0).getLevel(), "Expected level 'Western Europe' for first PeoplePopulation entry in getRegionTotalPopulation");
    }

    // ------------------ Tests for getCityTotalPopulation ------------------
    @Test
    void testGetCityTotalPopulation_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getCityTotalPopulation();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getCityTotalPopulation");
    }

    @Test
    void testGetCityTotalPopulation_containsNullMember() throws SQLException {
        prepareOneRowPopulationResult(null, 13960000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getCityTotalPopulation();
        assertNull(list.get(0).getLevel(), "Expected null level for first PeoplePopulation entry in getCityTotalPopulation");
    }

    @Test
    void testGetCityTotalPopulation_returnsValidList() throws SQLException {
        prepareOneRowPopulationResult("Tokyo", 13960000L, null, null);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getCityTotalPopulation();
        assertEquals("Tokyo", list.get(0).getLevel(), "Expected level 'Tokyo' for first PeoplePopulation entry in getCityTotalPopulation");
    }

    // ------------------ Tests for getRegionPopulationReport ------------------
    @Test
    void testGetRegionPopulationReport_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<PeoplePopulation> list = populationReport.getRegionPopulationReport();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getRegionPopulationReport");
    }

    @Test
    void testGetRegionPopulationReport_containsNullMember() throws SQLException {
        prepareOneRowPopulationResult(null, 1500000000L, 900000000L, 600000000L);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getRegionPopulationReport();
        assertNull(list.get(0).getLevel(), "Expected null level for first PeoplePopulation entry in getRegionPopulationReport");
    }

    @Test
    void testGetRegionPopulationReport_returnsValidList() throws SQLException {
        prepareOneRowPopulationResult("Eastern Asia", 1500000000L, 900000000L, 600000000L);
        wireExecuteQueryReturnsMockRset();
        List<PeoplePopulation> list = populationReport.getRegionPopulationReport();
        assertEquals("Eastern Asia", list.get(0).getLevel(), "Expected level 'Eastern Asia' for first PeoplePopulation entry in getRegionPopulationReport");
    }

    // ------------------ Tests for getWorldLanguageReport ------------------
    @Test
    void testGetWorldLanguageReport_returnsEmptyListOnSQLException() throws SQLException {
        wireExecuteQueryThrows();
        List<CountryLanguage> list = populationReport.getWorldLanguageReport();
        assertTrue(list.isEmpty(), "Expected empty list when SQLException occurs in getWorldLanguageReport");
    }

    @Test
    void testGetWorldLanguageReport_containsNullMember() throws SQLException {
        prepareOneRowLanguageResult(null, 1500000000.0, 18.0);
        List<CountryLanguage> list = populationReport.getWorldLanguageReport();
        assertNull(list.get(0).getLanguage(), "Expected null language for first CountryLanguage entry in getWorldLanguageReport");
    }

    @Test
    void testGetWorldLanguageReport_returnsValidList() throws SQLException {
        prepareOneRowLanguageResult("English", 1500000000.0, 18.0);
        List<CountryLanguage> list = populationReport.getWorldLanguageReport();
        assertEquals("English", list.get(0).getLanguage(), "Expected language 'English' for first CountryLanguage entry in getWorldLanguageReport");
    }
}