package fr.esgi.color_run.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnection {
    private static final String URL = "jdbc:h2:~/color_run_db;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    static { try { Class.forName("org.h2.Driver"); } catch (ClassNotFoundException e) { throw new RuntimeException(e); } }

    private DbConnection() {}
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
