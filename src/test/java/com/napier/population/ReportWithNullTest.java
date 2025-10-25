package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportWithNullTest {

    DbConnection db;
    Connection con;
    CapitalCityReport capitalCityReport;
    CityReport cityReport;
    CountryReport countryReport;
    PopulationReport populationReport;

    @BeforeEach
    void setUp() {
        db = new DbConnection();// Establish connection to the database
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
}