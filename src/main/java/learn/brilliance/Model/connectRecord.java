package learn.brilliance.Model;


import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectRecord {
    private Connection conn;
    private PreparedStatement preparedStatement;
    private static final String connURL = "jdbc:sqlite:courseRecords.db";
    private static final String username = "root";
    private static final String password = "";

    public connectRecord() {
        try {
            this.conn = DriverManager.getConnection(connURL, username, password);

        } catch (SQLException ex) {
            System.out.println("Unable to connect to database");
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to connect to the database", ex);
        }
    }

    public ResultSet getCATotalOverall(String tableName, String operation) {
        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = this.conn.createStatement();
            if(Objects.equals(operation, "CA")) {
                resultSet = stmt.executeQuery("SELECT ID, studentID, studentName, firstCA, secondCA, exam, total, grade, status, (coalesce(firstCA, 0) + coalesce(secondCA, 0)) as Total FROM '"+tableName+"' ORDER BY Total DESC;");

            } else {
                resultSet = stmt.executeQuery("SELECT * FROM '"+tableName+"';");
            }

        } catch (SQLException ex) {
            System.out.println("Unable to get course records CA total or overall.");
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    public ResultSet getCourseRecordData(String tableName) {
        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = this.conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM '"+tableName+"';");

        } catch (SQLException ex) {
            System.out.println("Unable to get all course record data");
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }
    public void updateCourseRecord(String tableName, String studentID, String studentName, String firstCA, String secondCA, String exam, String total, String grade, String status) {
        String newCourseRecord = "UPDATE '"+tableName+"' SET studentName=?, firstCA=?, secondCA=?, exam=?, total=?, grade=?, status=? WHERE studentID=?;";
        try {
            preparedStatement = conn.prepareStatement(newCourseRecord);
            preparedStatement.setString(1, studentName);
            preparedStatement.setString(2, firstCA);
            preparedStatement.setString(3, secondCA);
            preparedStatement.setString(4, exam);
            preparedStatement.setString(5, total);
            preparedStatement.setString(6, grade);
            preparedStatement.setString(7, status);
            preparedStatement.setString(8, studentID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to create new course record of student " + studentID);
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to create course record.", ex);
        }
    }
    public void createCourseRecordTable(String tableName) {
        String newCourseRecordTable = "CREATE TABLE IF NOT EXISTS '"+tableName+"' '(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, studentID varchar(20) NOT NULL, studentName(50) NOT NULL, firstCA varchar(10), secondCA varchar(10), exam varchar(10), total varchar(10), grade varchar(10), status varchar(10);'";
        Statement stmt;

        try {
            stmt = conn.createStatement();
            stmt.executeQuery(newCourseRecordTable);

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unable to create course record table.");
        }
    }

    public void insertStudentOnRegister(String tableName, String studentID, String studentName) {
        String newStudentRecord = "INSERT INTO '"+tableName+"' " +
            "(studentID, studentName, firstCA, secondCA, exam, total, grade, status)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            preparedStatement = conn.prepareStatement(newStudentRecord);
            preparedStatement.setString(1, studentID);
            preparedStatement.setString(2, studentName);
            preparedStatement.setString(3, null);
            preparedStatement.setString(4, null);
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, null);
            preparedStatement.setString(7, null);
            preparedStatement.setString(8, null);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to insert student on registration", ex);
        }

    }

}
