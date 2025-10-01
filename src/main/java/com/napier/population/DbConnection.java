package com.napier.population;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles connecting and disconnecting
 * from a MySQL database.
 * - Provides retry mechanism
 * - Uses JDBC driver
 * - Closes connections safely
 */
public class DbConnection {
    // Database connection object
    private Connection con = null;

    /**
     * Connects to the MySQL database with retries.
     */
    public void connect() {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;

        /*
         * Try connecting multiple times (10 max).
         * Waits 30 seconds between retries.
         * Useful when database takes time to start.
         */
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                Thread.sleep(30000); // Single-line Comment: Wait before retry
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        "root",
                        "abc123!@#");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect attempt " + i);
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted.");
            }
        }
    }

    /**
     * Disconnects from the database safely.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close(); // Close connection
            } catch (Exception e) {
                System.out.println("Error closing connection");
            }
        }
    }
}
