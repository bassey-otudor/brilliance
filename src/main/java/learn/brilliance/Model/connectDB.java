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
    // Get all faculties and store the in a list
    public ResultSet getFacultyData() {
        Statement stmt;
        ResultSet resultSet = null;
        try{
            stmt = this.conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM faculties;");
        } catch (SQLException e) {
            System.out.println("Unable to get faculty data");
            e.printStackTrace();
        }
        return resultSet;
    }
    public ObservableList<String> getDirectors() {

        List<String> directorList = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT CONCAT(fname, ' ', lName) AS facDirector FROM teachers WHERE position = 'director'";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                directorList.add(resultSet.getString("facDirector"));
            }

            directorList = FXCollections.observableArrayList(directorList);

        } catch (SQLException e) {
            System.out.println("Error retrieving ");
            e.printStackTrace();
        }

        assert directorList instanceof ObservableList<String>;
        return (ObservableList<String>) directorList;
    }
    public void createFaculty(String facultyID,String facultyName, String director, String department1, String department2, String department3) {
        Statement stmt;
        try {
            stmt = this.conn.createStatement();
            stmt.executeUpdate("INSERT INTO faculties "
            + "(facultyID, facultyName, Director, Department1, Department2, Department3)"
            + "VALUES ('"+facultyID+"', '"+facultyName+"','"+director+"', '"+department1+"', '"+department2+"', '"+department3+"')");
        } catch (SQLException e) {
            System.out.println("Error inserting faculty data");
            e.printStackTrace();
        }

    }
    public void updateFaculty(String facultyID,String facultyName, String director, String department1, String department2, String department3) {
        try {
            Statement stmt = this.conn.createStatement();
            String update = "UPDATE faculties SET facultyID = '"+facultyID+"', facultyName ='"+facultyName+"', Director ='"+director+"', Department1 ='"+department1+"', Department2 ='"+department2+"', Department3 ='"+department3+"' WHERE facultyID ='"+facultyID+"';";
            stmt.executeUpdate(update);
        } catch (SQLException e) {
            System.out.println("Error executing update.");
            e.printStackTrace();
        }
    }
    public void deleteFaculty(String facultyID) {
        try{
            Statement stmt = this.conn.createStatement();
            String delete = "DELETE FROM faculties WHERE facultyID = '"+facultyID+"';";
            stmt.executeUpdate(delete);
        } catch (SQLException e) {
            System.out.println("Error deleting faculty.");
            e.printStackTrace();
        }
    }
}
