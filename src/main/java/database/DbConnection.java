package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection getConnectionSqlite() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:database.db");

    }
}
