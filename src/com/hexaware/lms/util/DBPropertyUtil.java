package com.hexaware.lms.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getConnectionString(String fileName) {
        Properties props = new Properties();
        String connStr = null;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            props.load(fis);
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            connStr = url + "?user=" + username + "&password=" + password;
            System.out.println("✅ db.properties loaded successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error loading db.properties file: " + e.getMessage());
        }

        return connStr;
    }
}
