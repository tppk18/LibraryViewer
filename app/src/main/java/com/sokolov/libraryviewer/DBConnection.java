package com.sokolov.libraryviewer;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:mysql://89.111.153.23:3306/irbis?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String user = "reader";
    private static final String password = "BmOAPnCK(wkgrmgK";
    public static Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            Log.d("DBConnection", "Подключение успешно");
        }
        catch (SQLException e) {
            Log.e("DBConnection", "Ошибка подключения к базе", e);
            throw new RuntimeException(e);
        }
        return conn;
    }
}