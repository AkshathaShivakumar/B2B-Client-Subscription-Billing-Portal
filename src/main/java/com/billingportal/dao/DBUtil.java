package com.billingportal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Single place that knows how to open a JDBC connection to the local
 * MySQL Workbench database every team member set up from schema.sql.
 *
 * NOTE: Once the team merges everyone's code into main, this class may
 * already exist (someone else might add a shared DBUtil). If so, delete
 * this copy and just import theirs - don't have two.
 */
public class DBUtil {

    // Adjust these three if your local MySQL setup differs.
    private static final String URL =
            "jdbc:mysql://localhost:3306/billing_portal?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "nisha";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver not found on classpath", e);
        }
    }

    private DBUtil() {
        // utility class, no instances
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
