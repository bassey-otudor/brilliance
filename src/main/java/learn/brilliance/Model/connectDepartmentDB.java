package learn.brilliance.Model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectDepartmentDB {
    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement stmt;
    private static final String connURL = "jdbc:sqlite:departmentDB.db";
    private static final String username = "root";
    private static final String password = "";

    public connectDepartmentDB() {
        try{
            this.conn = DriverManager.getConnection(connURL, username, password);

        } catch (SQLException ex) {
            System.out.println("Unable to connect to the department database");
            Logger.getLogger(connectDepartmentDB.class.getName()).log(Level.SEVERE, null, "Unable to connect to the department database");
        }

    }

    // Create department level tables
    public void createLevelTable(String tableName) {
        String createTable = "CREATE TABLE '"+tableName+"' (\"ID\"\tINTEGER NOT NULL, \"courseID\"\tvarchar(20) NOT NULL, \"degreeID\"\tvarchar(20) NOT NULL, PRIMARY KEY(\"ID\" AUTOINCREMENT))";
        try {
            stmt = this.conn.createStatement();
            stmt.executeUpdate(createTable);

        } catch (SQLException ex) {
            Logger.getLogger(connectDepartmentDB.class.getName()).log(Level.SEVERE, "Unable to create department course list", ex);
        }
    }

    // Add a course to a department level table upon creation
    public void addCourseToDepartmentLevelTable(String tableName, String courseID, String degreeID) {
        String addToTable = "INSERT INTO '"+tableName+"' (courseID, degreeID) VALUES (?,?);";
        try {
            preparedStatement = this.conn.prepareStatement(addToTable);
            preparedStatement.setString(1, courseID);
            preparedStatement.setString(2, degreeID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(connectDepartmentDB.class.getName()).log(Level.SEVERE, "Unable to add course to designated department table.", ex);
        }
    }

    // Mark the tables of deleted departments as deleted
    public void markTableAsDeleted(String oldName, String newName) {
        String markTableAsDeleted = "ALTER TABLE '"+oldName+"' RENAME to '"+newName+"'";
        try {
            stmt = this.conn.createStatement();
            stmt.executeUpdate(markTableAsDeleted);

        } catch (SQLException ex) {
            Logger.getLogger(connectDepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Remove course from


}
