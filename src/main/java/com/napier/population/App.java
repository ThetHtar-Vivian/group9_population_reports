package com.napier.population;

import java.sql.Connection;

/**
 * Main entry point for the Population Reporting Application.
 * This class is responsible for:
 * - Establishing a database connection
 * - Passing the connection to ReportManager
 * - Generating different types of reports
 * - Closing the database connection before exiting
 */
public class App {
    public static void main(String[] args) {
        // Create a new database connection instance
        DbConnection db = new DbConnection();

        // Establish connection to the database
        Connection con;
        if(args.length < 1){
            con = db.connect("localhost:33060", 30000);
        } else {
            con = db.connect(args[0], Integer.parseInt(args[1]));
        }

        // Initialize ReportManager with the database connection
        ReportManager manager = new ReportManager(con);

        // Generate different reports
        manager.generateCityReport();           // Report on cities
        manager.generateCountryReport();        // Report on countries
        manager.generateCapitalCityReport();    // Report on capital cities
        manager.generatePopulationReport();     // Report on people population
        manager.generateLanguageReport();       // Report on languages

        // Close the database connection before exiting
        db.disconnect();
    }
}
