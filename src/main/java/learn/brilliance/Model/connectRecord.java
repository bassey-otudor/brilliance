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

    /**
     * Retrieves the course record data from the specified table in descending order.
     *
     * @param tableName The name of the table to retrieve data from.
     * @return A ResultSet containing the best course records data.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
    public ResultSet getBestCourseRecordData(String tableName) {
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

    /**
     * Retrieves all data from the specified course record table.
     *
     * @param tableName The name of the table to retrieve data from.
     * @return A ResultSet containing all data from the specified table.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
    public ResultSet getCourseRecordData(String tableName) {
        ResultSet resultSet = null;

        try {
            stmt = this.conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM '"+tableName+"';");

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    /**
     * Creates a new course record table with the specified name.
     *
     * @param tableName The name of the table to create.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
    public void createCourseRecordTable(String tableName) {

        String newCourseRecordTable = "CREATE TABLE '"+tableName+"' (\"ID\"\tINTEGER NOT NULL, \"studentID\"\tvarchar(20) NOT NULL, \"studentName\"\tvarchar(50) NOT NULL, \"firstCA\"\tvarchar(10), \"secondCA\"\tvarchar(10), \"exam\"\tvarchar(10), \"total\" varchar(10), \"grade\"\tvarchar(10), \"status\"\tvarchar(10), PRIMARY KEY(\"ID\" AUTOINCREMENT))";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(newCourseRecordTable);

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates a course record in the specified table.
     *
     * @param tableName The name of the table to update the course record in.
     * @param studentID The unique identifier for the student.
     * @param studentName The name of the student.
     * @param firstCA The first CA score of the student.
     * @param secondCA The second CA score of the student.
     * @param exam The exam score of the student.
     * @param total The total score of the student.
     * @param grade The grade of the student.
     * @param status The status of the student.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
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
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to create course record.", ex);
        }
    }

    /**
     * Inserts a new student record into the specified table.
     *
     * @param tableName The name of the table to insert the new student record into.
     * @param studentID The unique identifier for the student.
     * @param studentName The name of the student.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
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

    /**
     * Retrieves the occurrences of each first CA score in the specified table.
     *
     * @param tableName The name of the table to retrieve data from.
     * @return A ResultSet containing the occurrences of each first CA score in the specified table.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
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

    /**
     * Retrieves the occurrences of each second CA score in the specified table.
     *
     * @param tableName The name of the table to retrieve data from.
     * @return A ResultSet containing the occurrences of each second CA score in the specified table.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
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

    /**
     * Retrieves the occurrences of each exam score in the specified table.
     *
     * @param tableName The name of the table to retrieve data from.
     * @return A ResultSet containing the occurrences of each exam score in the specified table.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
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

    /**
     * Retrieves the occurrences of each total score in the specified table.
     *
     * @param tableName The name of the table to retrieve data from.
     * @return A ResultSet containing the occurrences of each total score in the specified table.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
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


