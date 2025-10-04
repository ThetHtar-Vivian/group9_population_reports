package com.napier.population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Provides methods for generating city reports from the database.
 * Each method queries the database and returns a list of City objects
 * for use in reports.
 */
public class CityReport {

    /**
     * Active database connection used to execute queries
     */
    private Connection con;

    /**
     * Constructor initializes the CityReport with an active DB connection.
     *
     * @param con Active MySQL database connection
     */
    public CityReport(Connection con) {
        this.con = con;
    }

}
