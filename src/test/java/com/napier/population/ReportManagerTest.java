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
        connection = db.connect("localhost:33060", 10000);
        manager = new ReportManager(connection);
    }

    @AfterEach
    void tearDown() {
    }
}