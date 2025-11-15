package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ReportManagerTest {
    DbConnection db;
    Connection connection;
    ReportManager manager;

    @BeforeEach
    void setUp() {
        db = new DbConnection();// Establish connection to the database
        connection = db.connect("localhost:33060", 10000, "com.mysql.cj.jdbc.Driver");
        manager = new ReportManager(connection);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGenerateCityReport() {
        assertDoesNotThrow(() -> manager.generateCityReport(),
                "generateCityReport() should not throw any exceptions");
    }

    @Test
    void testGenerateCountryReport() {
        assertDoesNotThrow(() -> manager.generateCountryReport(),
                "generateCountryReport() should not throw any exceptions");
    }

    @Test
    void testGenerateCapitalCityReport() {
        assertDoesNotThrow(() -> manager.generateCapitalCityReport(),
                "generateCapitalCityReport() should not throw any exceptions");
    }

    @Test
    void testGeneratePopulationReport() {
        assertDoesNotThrow(() -> manager.generatePopulationReport(),
                "generatePopulationReport() should not throw any exceptions");
    }

    @Test
    void testGenerateLanguageReport() {
        assertDoesNotThrow(() -> manager.generateLanguageReport(),
                "generateLanguageReport() should not throw any exceptions");
    }
}