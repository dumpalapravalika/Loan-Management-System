package com.hexaware.lms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    public static Connection getConnection(String fileName) {
        Connection conn = null;
        String connectionString = DBPropertyUtil.getConnectionString(fileName);

        try {
            if (connectionString != null) {
                conn = DriverManager.getConnection(connectionString);
                System.out.println("✅ Database connected successfully.");
            } else {
                System.out.println("❌ Connection string is null. Check your db.properties.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to database: " + e.getMessage());
        }

        return conn;
    }
}
