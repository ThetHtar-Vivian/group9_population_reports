package com.napier.population;

import java.sql.Connection;

/**
 * Handles generating reports specifically for capital cities.
 * This class interacts with the database using the provided Connection
 * and retrieves capital city data for reporting purposes.
 */
public class CapitalCityReport {

    /**
     * Active database connection used to query capital city information
     */
    private Connection con;

    /**
     * Constructor initializes the CapitalCityReport with an active database connection.
     *
     * @param con Active MySQL database connection
     */
    public CapitalCityReport(Connection con) {
        this.con = con;
    }

}
