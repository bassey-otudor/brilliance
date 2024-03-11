package learn.brilliance.Model;

import java.sql.*;

public class DatabaseConnector {
    private Connection conn;
    private static final String connURL = "jdbc:mysql://localhost:3306/school_db_structure";
    private static final String username = "root";
    private static final String password = "";
    public DatabaseConnector() {
        try {
            this.conn = DriverManager.getConnection(connURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the admin's information
    public ResultSet getAdmin(String username, String password) {
        Statement stmt;
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM admin WHERE username = '" + username + "' AND password = '" + password + "'";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
