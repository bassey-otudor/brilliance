package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectDepartmentDB {
    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement stmt;
    private static final String connURL = "jdbc:sqlite:departmentDB.db";
    private static final String username = "root";
    private static final String password = "";

    /**
     * Initializes a new instance of the {@code connectDepartmentDB} class.
     * This class is responsible for establishing a connection to the department database and providing methods to interact with it.
     *
     * @throws SQLException if an error occurs while establishing the database connection
     */
    public connectDepartmentDB() {
        try{
            this.conn = DriverManager.getConnection(connURL, username, password);

        } catch (SQLException ex) {
            System.out.println("Unable to connect to the department database");
            Logger.getLogger(connectDepartmentDB.class.getName()).log(Level.SEVERE, null, "Unable to connect to the department database");
        }

    }

    /**
     * Creates a new department level table in the database.
     *
     * @param tableName the name of the new department level table
     *
     * @throws SQLException if an error occurs while executing the SQL statement
     */
    public void createLevelTable(String tableName) {
        String createTable = "CREATE TABLE '"+tableName+"' (\"ID\"\tINTEGER NOT NULL, \"courseID\"\tvarchar(20) NOT NULL, \"degreeID\"\tvarchar(20) NOT NULL, PRIMARY KEY(\"ID\" AUTOINCREMENT))";
        try {
            stmt = this.conn.createStatement();
            stmt.executeUpdate(createTable);

        } catch (SQLException ex) {
            Logger.getLogger(connectDepartmentDB.class.getName()).log(Level.SEVERE, "Unable to create department course list", ex);
        }
    }

    /**
 * Adds a course to a department level table.
 *
 * @param tableName the name of the department level table to which the course will be added
 * @param courseID the unique identifier for the course
 * @param degreeID the unique identifier for the degree associated with the course
 *
 * @throws SQLException if an error occurs while executing the SQL statement
 */
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


    /**
     * Retrieves a list of all courses associated with a particular degree.
     *
     * @param tableName the name of the department level table to which the course will be added
     * @param degreeID the unique identifier for the degree associated with the course
     *
     * @return an observable list of strings containing the course IDs associated with the specified degree
     *
     * @throws SQLException if an error occurs while executing the SQL statement
     */
    public ObservableList<String> getAllDegreeCourses(String tableName, String degreeID) {
        List<String> courseList = new ArrayList<>();
        String getDegreeCourses = "SELECT courseID FROM '"+tableName+"' WHERE degreeID =? OR degreeID = 'GEN' ORDER BY courseID;";
        ResultSet resultSet;
        try {
            preparedStatement = this.conn.prepareStatement(getDegreeCourses);
            preparedStatement.setString(1, degreeID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseList.add(resultSet.getString("courseID"));
            }

            courseList = FXCollections.observableArrayList(courseList);
        } catch (SQLException ex) {
            Logger.getLogger(connectDepartmentDB.class.getName()).log(Level.SEVERE, "Unable to get course list for degree '"+degreeID+"' ", ex);
        }

        return FXCollections.observableArrayList(courseList);
    }

    /**
     * Marks the tables of deleted departments as deleted.
     *
     * @param oldName the original name of the department level table
     * @param newName the new name for the department level table
     *
     * @throws SQLException if an error occurs while executing the SQL statement
     */
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
