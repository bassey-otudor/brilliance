package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class connectDB {
    private Connection conn;
    private static final String connURL = "jdbc:mysql://localhost:3306/school_db_structure";
    private static final String username = "root";
    private static final String password = "";
    public connectDB() {
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

    // Faculty section
    public ObservableList<String> getDirectors() {

        List<String> directorList = new ArrayList<>();
        Statement stmt;
        ResultSet rs;

        try {
            String sql = "SELECT CONCAT(fname, ' ', lName) AS facDirector FROM teachers WHERE position = 'director'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                directorList.add(rs.getString("facDirector"));
            }

            directorList = FXCollections.observableArrayList(directorList);

        } catch (SQLException e) {
            System.out.println("Error retrieving ");
            e.printStackTrace();
        }

        assert directorList instanceof ObservableList<String>;
        return (ObservableList<String>) directorList;
    }
    public void createFaculty(String facID,String facName, String facDirector, String dept1, String dept2, String dept3) {
        Statement stmt;
        try {
            stmt = this.conn.createStatement();
            stmt.executeUpdate("INSERT INTO faculties "
            + "(facultyID, facultyName, Director, Department1, Department2, Department3)"
            + "VALUES ('"+facID+"', '"+facName+"','"+facDirector+"', '"+dept1+"', '"+dept2+"', '"+dept3+"')");
        } catch (SQLException e) {
            System.out.println("Error inserting faculty data");
            e.printStackTrace();
        }

    }
    public void updateFaculty(String facID,String facName, String facDirector, String dept1, String dept2, String dept3) {
        try {
            Statement stmt = this.conn.createStatement();
            String update = "UPDATE faculties SET facultyID = '"+facID+"', facultyName ='"+facName+"', Director ='"+facDirector+"', Department1 ='"+dept1+"', Department2 ='"+dept1+"', Department3 ='"+dept1+"' WHERE facultyID ='"+facID+"';";
            stmt.executeUpdate(update);
        } catch (SQLException e) {
            System.out.println("Error executing update.");
            e.printStackTrace();
        }
    }
    public void deleteFaculty(String facID) {
        try{
            Statement stmt = this.conn.createStatement();
            String delete = "DELETE FROM faculties WHERE facultyID = '"+facID+"';";
            stmt.executeUpdate(delete);
        } catch (SQLException e) {
            System.out.println("Error deleting faculty.");
            e.printStackTrace();
        }
    }
}
