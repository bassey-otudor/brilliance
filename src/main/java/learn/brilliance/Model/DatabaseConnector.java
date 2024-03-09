package learn.brilliance.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private Connection conn;
    private static final String connURL = "jdbc:mysql:/localhost:3306/school_db_structure";
    private static final String username = "root";
    private static final String password = "";
    public DatabaseConnector() {
        try {
            this.conn = DriverManager.getConnection(connURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
