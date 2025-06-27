package com.hexaware.lms.util;

import java.sql.Connection;

public class DBUtil {

    public static Connection getDBConn() {
        return DBConnUtil.getConnection("db.properties");
    }
}
