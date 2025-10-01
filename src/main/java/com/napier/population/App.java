package com.napier.population;

/**
 * The App class is the entry point of the application.
 * It demonstrates how to:
 * Create a database connection
 * Connect to the database
 * Disconnect safely
 */
public class App {

    /**
     * The main method where the program starts execution.
     *
     * @param args Command line arguments (not used in this program).
     */
    public static void main(String[] args) {
        // Create a new database connection object
        DbConnection con = new DbConnection();

        /*
         * Establish a connection to the MySQL database.
         * If connection fails, retry logic inside DbConnection will handle it.
         */
        con.connect();

        // Disconnect from the database safely
        con.disconnect();
    }
}
