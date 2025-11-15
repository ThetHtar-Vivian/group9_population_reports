package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DbConnectionTest {

    DbConnection db;
    Connection con;

    @Test
    void testConnectSuccess() {
        DbConnection db = new DbConnection();
        Connection con = db.connect("localhost:33060", 10000, "com.mysql.cj.jdbc.Driver");
        assertNotNull(con);
    }

    @Test
    void testConnectFailure() {
        DbConnection db = new DbConnection();
        Connection con = db.connect("invalid:3306", 0, "com.mysql.cj.jdbc.Driver");
        assertNull(con); // after 10 failed attempts
    }

    @Test
    void testDriverClassNotFound() {
        DbConnection db = new DbConnection();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            db.connect("localhost:33060", 0, "invalid.DriverClass");
        });

        assertTrue(ex.getCause() instanceof ClassNotFoundException);
    }


    @Test
    void testInterruptedDuringSleep() {
        DbConnection db = new DbConnection();
        Thread.currentThread().interrupt(); // force interruption

        Connection con = db.connect("invalid:3306", 1000, "com.mysql.cj.jdbc.Driver");
        // interruption does not kill test, but covers the catch block
        assertNull(con);
    }

    @Test
    void testDisconnectSuccess() {
        DbConnection db = new DbConnection();
        db.connect("localhost:33060", 10000, "com.mysql.cj.jdbc.Driver");
        int check = db.disconnect();

        assertEquals(1, check);
    }

    @Test
    void testDisconnectWhenNull() {
        DbConnection db = new DbConnection();
        assertEquals(0, db.disconnect());
    }

    @Test
    void testDisconnectException() throws Exception {
        DbConnection db = new DbConnection();

        // Create a mock connection
        Connection mockCon = Mockito.mock(Connection.class);

        // Force close() to throw an exception
        Mockito.doThrow(new RuntimeException("close failed"))
                .when(mockCon).close();

        // Inject the mock connection into db
        Field field = DbConnection.class.getDeclaredField("con");
        field.setAccessible(true);
        field.set(db, mockCon);

        // Call disconnect() → should hit catch block and return 0
        int result = db.disconnect();

        assertEquals(0, result);
    }

}