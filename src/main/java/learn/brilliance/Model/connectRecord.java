package learn.brilliance.Model;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectRecord {
    private Connection conn;
    Statement stmt;
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

    public ResultSet getBestCourseRecord(String tableName) {
        ResultSet resultSet = null;

        try {
            stmt = this.conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM '"+tableName+"' ORDER BY total DESC;");

        } catch (SQLException ex) {
            System.out.println("Unable to get course records CA total or overall.");
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    public ResultSet getCourseRecordData(String tableName) {
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

    // Get data for course performance
    public ResultSet getFirstCA(String tableName) {
        String getFirstCA = "SELECT firstCA, COUNT(firstCA) AS Occurences FROM '"+tableName+"' WHERE firstCA >= 0 GROUP BY firstCA;";
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getFirstCA);

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to get firstCA occurrences.", ex);
        }
        return resultSet;
    }
    public ResultSet getSecondCA(String tableName) {
        String getSecondCA = "SELECT secondCA, COUNT(secondCA) AS Occurences FROM '"+tableName+"' WHERE secondCA >= 0 GROUP BY secondCA;";
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getSecondCA);

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to get secondCA occurrences.", ex);
        }
        return resultSet;
    }
    public ResultSet getExam(String tableName) {
        String getExam = "SELECT exam, COUNT(exam) AS Occurences FROM '"+tableName+"' WHERE exam >= 0 GROUP BY exam;";
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getExam);

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to get exam occurrences.", ex);
        }
        return resultSet;
    }
    public ResultSet getTotal(String tableName) {
        String getTotal = "SELECT total, COUNT(total) AS Occurences FROM '"+tableName+"' WHERE total >= 0 GROUP BY total;";
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getTotal);

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to get total occurrences.", ex);
        }
        return resultSet;
    }
}


