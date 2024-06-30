package learn.brilliance.Model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectTimetable {
    private Connection conn;
    Statement stmt;
    PreparedStatement preparedStatement;
    private final String connURL = "jdbc:sqlite:timetable.db";
    private final String username = "root";
    private final String password = "";

    public connectTimetable() {
        try {
            this.conn = DriverManager.getConnection(connURL, username, password);

        } catch (SQLException ex) {
            Logger.getLogger(connectTimetable.class.getName()).log(Level.SEVERE, "Unable to connect to the timetable database", ex);
        }
    }

    // Get the day of today
    public ResultSet getTimetableData(int day, String teacherID) {
        String getTimetable = "";
        ResultSet resultSet = null;

        switch(day) {
            case 1 -> getTimetable = "SELECT * FROM sunday WHERE teacherID=?;";
            case 2 -> getTimetable = "SELECT * FROM monday WHERE teacherID=?;";
            case 3 -> getTimetable = "SELECT * FROM tuesday WHERE teacherID=?;";
            case 4 -> getTimetable = "SELECT * FROM wednesday WHERE teacherID=?;";
            case 5 -> getTimetable = "SELECT * FROM thursday WHERE teacherID=?;";
            case 6 -> getTimetable = "SELECT * FROM friday WHERE teacherID=?;";
            case 7 -> getTimetable = "SELECT * FROM saturday WHERE courseID=?;";
        }

        try {
            preparedStatement = conn.prepareStatement(getTimetable);
            preparedStatement.setString(1, teacherID);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(connectTimetable.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

}
