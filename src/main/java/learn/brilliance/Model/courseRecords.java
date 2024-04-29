package learn.brilliance.Model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class courseRecords {
    private Connection conn;
    private static final String connURL = "jdbc:sqlite:school_database_structure.db";
    private static final String username = "root";
    private static final String password = "";

    public courseRecords() {
        try {
            this.conn = DriverManager.getConnection(connURL, username, password);

        } catch (SQLException ex) {
            Logger.getLogger(courseRecords.class.getName()).log(Level.SEVERE, "Unable to connect to the database", ex);
        }
    }

}
