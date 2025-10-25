package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DbConnectionTest {

    DbConnection db;
    Connection con;

    @BeforeEach
    void setUp() {
        db = new DbConnection();// Establish connection to the database
        con = db.connect("localhost:33060", 10000);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void connect() {
        assertNotNull(con);
    }

    @Test
    void disconnect() {
        int check = db.disconnect();
        assertEquals(1, check);
    }
}