package com.napier.population;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DbConnection class manages the connection to the MySQL database.
 * - Establishes a connection with retries
 * - Provides access to the JDBC Connection object
 * - Safely disconnects from the database
 */
public class DbConnection {

    // Active database connection used to execute queries
    private Connection con = null;

    /**
     * Connects to the MySQL database.
     * Includes retry logic (up to 10 attempts) with a wait time
     * between retries, useful when the database container is starting up.
     */
    public void connect() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1); // Exit if driver not found
        }

        int retries = 10; // Maximum connection attempts

        /*
         * Attempt to connect multiple times (10 retries).
         * Waits 30 seconds between retries in case DB service is not ready.
         */
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                Thread.sleep(30000); // Pause before retrying connection

                // Establish connection to the database
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        "root",
                        "abc123!@#");

                System.out.println("Successfully connected");
                break; // Exit loop once connection succeeds
            } catch (SQLException sqle) {
                // Log failure and retry
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                // This should rarely happen, but log it anyway
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnects from the MySQL database if a connection exists.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close active connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Returns the active JDBC connection.
     *
     * @return Connection object if connected, otherwise null
     */
    public Connection getConnection() {
        return con;
    }
}
