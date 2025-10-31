package com.napier.population;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PopulationReportTest {

    @Mock
    private Connection mockCon;
    @Mock
    private Statement mockStmt;
    @Mock
    private ResultSet mockRset;

    private PopulationReport populationReport;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockCon.createStatement()).thenReturn(mockStmt);
        populationReport = new PopulationReport(mockCon);
    }

    // ===============================
    // 1. Test: getCountryPopulationReport
    // ===============================
    @Test
    void testGetCountryPopulationReport() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("CountryName")).thenReturn("France");
        when(mockRset.getLong("TotalPopulation")).thenReturn(67000000L);
        when(mockRset.getLong("CityPopulation")).thenReturn(45000000L);
        when(mockRset.getLong("NonCityPopulation")).thenReturn(22000000L);

        ArrayList<PeoplePopulation> result = populationReport.getCountryPopulationReport();

        assertEquals(1, result.size());
        PeoplePopulation france = result.get(0);
        assertEquals("France", france.getLevel());
        assertEquals(67000000L, france.getTotalPopulation());
        assertEquals(45000000L, france.getCityPopulation());
        assertEquals(22000000L, france.getNonCityPopulation());
    }

    // ===============================
    // 2. Test: getWorldPopulation
    // ===============================
    @Test
    void testGetWorldPopulation() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true);
        when(mockRset.getLong("WorldPopulation")).thenReturn(8000000000L);

        ArrayList<PeoplePopulation> result = populationReport.getWorldPopulation();

        assertEquals(1, result.size());
        assertEquals("World", result.get(0).getLevel());
        assertEquals(8000000000L, result.get(0).getTotalPopulation());
    }

    // ===============================
    // 3. Test: getTotalPopulationPerCountry
    // ===============================
    @Test
    void testGetTotalPopulationPerCountry() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("CountryName")).thenReturn("Japan");
        when(mockRset.getLong("TotalPopulation")).thenReturn(125000000L);

        ArrayList<PeoplePopulation> result = populationReport.getTotalPopulationPerCountry();

        assertEquals(1, result.size());
        assertEquals("Japan", result.get(0).getLevel());
        assertEquals(125000000L, result.get(0).getTotalPopulation());
    }

    // ===============================
    // 4. Test: getContinentTotalPopulation
    // ===============================
    @Test
    void testGetContinentTotalPopulation() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("Continent")).thenReturn("Asia");
        when(mockRset.getLong("TotalPopulation")).thenReturn(4700000000L);

        ArrayList<PeoplePopulation> result = populationReport.getContinentTotalPopulation();

        assertEquals(1, result.size());
        assertEquals("Asia", result.get(0).getLevel());
        assertEquals(4700000000L, result.get(0).getTotalPopulation());
    }

    // ===============================
    // 5. Test: getDistrictTotalPopulation
    // ===============================
    @Test
    void testGetDistrictTotalPopulation() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("name")).thenReturn("California");
        when(mockRset.getLong("totalPopulation")).thenReturn(39500000L);

        ArrayList<PeoplePopulation> result = populationReport.getDistrictTotalPopulation();

        assertEquals(1, result.size());
        PeoplePopulation district = result.get(0);
        assertEquals("California", district.getLevel());
        assertEquals(39500000L, district.getTotalPopulation());
    }

    // ===============================
    // 6. Test: getContinentPopulationReport
    // ===============================
    @Test
    void testGetContinentPopulationReport() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("name")).thenReturn("Europe");
        when(mockRset.getLong("totalPopulation")).thenReturn(750000000L);
        when(mockRset.getLong("cityPopulation")).thenReturn(500000000L);
        when(mockRset.getLong("nonCityPopulation")).thenReturn(250000000L);

        ArrayList<PeoplePopulation> result = populationReport.getContinentPopulationReport();

        assertEquals(1, result.size());
        PeoplePopulation continent = result.get(0);
        assertEquals("Europe", continent.getLevel());
        assertEquals(750000000L, continent.getTotalPopulation());
        assertEquals(500000000L, continent.getCityPopulation());
        assertEquals(250000000L, continent.getNonCityPopulation());
    }


    // ===============================
    // 7. Test: getRegionTotalPopulation
    // ===============================
    @Test
    void testGetRegionTotalPopulation() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("Region")).thenReturn("Western Europe");
        when(mockRset.getLong("TotalPopulation")).thenReturn(200000000L);

        ArrayList<PeoplePopulation> result = populationReport.getRegionTotalPopulation();

        assertEquals(1, result.size());
        assertEquals("Western Europe", result.get(0).getLevel());
        assertEquals(200000000L, result.get(0).getTotalPopulation());
    }

    // ===============================
    // 8. Test: getCityTotalPopulation
    // ===============================
    @Test
    void testGetCityTotalPopulation() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("name")).thenReturn("Tokyo");
        when(mockRset.getLong("totalPopulation")).thenReturn(13960000L);

        ArrayList<PeoplePopulation> result = populationReport.getCityTotalPopulation();

        assertEquals(1, result.size());
        assertEquals("Tokyo", result.get(0).getLevel());
        assertEquals(13960000L, result.get(0).getTotalPopulation());
    }

    // ===============================
    // 9. Test: getRegionPopulationReport
    // ===============================
    @Test
    void testGetRegionPopulationReport() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("RegionName")).thenReturn("Eastern Asia");
        when(mockRset.getLong("TotalPopulation")).thenReturn(1500000000L);
        when(mockRset.getLong("CityPopulation")).thenReturn(900000000L);

        ArrayList<PeoplePopulation> result = populationReport.getRegionPopulationReport();

        assertEquals(1, result.size());
        PeoplePopulation region = result.get(0);
        assertEquals("Eastern Asia", region.getLevel());
        assertEquals(1500000000L, region.getTotalPopulation());
        assertEquals(900000000L, region.getCityPopulation());
        assertEquals(600000000L, region.getNonCityPopulation());
    }

    // ===============================
    // 10. Test: getWorldLanguageReport
    // ===============================
    @Test
    void testGetWorldLanguageReport() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenReturn(mockRset);
        when(mockRset.next()).thenReturn(true, false);
        when(mockRset.getString("language")).thenReturn("English");
        when(mockRset.getDouble("totalSpeakers")).thenReturn(1500000000.0);
        when(mockRset.getDouble("worldPercentage")).thenReturn(18.0);

        ArrayList<CountryLanguage> result = populationReport.getWorldLanguageReport();

        assertEquals(1, result.size());
        CountryLanguage lang = result.get(0);
        assertEquals("English", lang.getLanguage());
        assertEquals(1500000000.0, lang.getPercentage());
        assertEquals(18.0, lang.getWorld_percentage());
    }

    // -------------------------------
    // Exception tests (SQLException)
    // -------------------------------
    @Test
    void testGetCountryPopulationReport_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getCountryPopulationReport();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetWorldPopulation_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getWorldPopulation();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTotalPopulationPerCountry_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getTotalPopulationPerCountry();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetContinentTotalPopulation_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getContinentTotalPopulation();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetDistrictTotalPopulation_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getDistrictTotalPopulation();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetContinentPopulationReport_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getContinentPopulationReport();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetRegionTotalPopulation_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getRegionTotalPopulation();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetCityTotalPopulation_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getCityTotalPopulation();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetRegionPopulationReport_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<PeoplePopulation> result = populationReport.getRegionPopulationReport();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetWorldLanguageReport_ThrowsSQLException() throws Exception {
        when(mockStmt.executeQuery(anyString())).thenThrow(new SQLException("DB error"));
        ArrayList<CountryLanguage> result = populationReport.getWorldLanguageReport();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}

