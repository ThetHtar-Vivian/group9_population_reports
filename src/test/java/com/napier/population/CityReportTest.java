package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CityReportTest {

    DbConnection db;
    Connection con;
    CityReport report;

    @BeforeEach
    void setUp() {
        db = new DbConnection();// Establish connection to the database
        con = db.connect("localhost:33060", 10000);
        report = new CityReport(con);
    }

    @AfterEach
    void tearDown() {
        db.disconnect();
    }
}