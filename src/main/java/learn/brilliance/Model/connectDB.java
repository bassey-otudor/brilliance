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
            Statement stmt = conn.createStatement();
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

    // Department section
    public ObservableList<String> getHod() {

        List<String> hodList = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT CONCAT(fname, ' ', lName) AS deptHod FROM teachers WHERE position = 'hod'";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                hodList.add(resultSet.getString("departmentHod"));
            }

            hodList = FXCollections.observableArrayList(hodList);

        } catch (SQLException e) {
            System.out.println("Error retrieving HODs");
            e.printStackTrace();
        }

        assert hodList instanceof ObservableList<String>;
        return (ObservableList<String>) hodList;
    }
    public ObservableList<String> getFaculties() {
        List<String> faculityList = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT facultyID from faculties;";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                faculityList.add(resultSet.getString("facultyID"));
            }
            faculityList = FXCollections.observableArrayList(faculityList);

        } catch (SQLException e) {
            System.out.println("Unable to get list of faculties.");
            e.printStackTrace();
        }

        assert faculityList instanceof ObservableList<String>;
        return (ObservableList<String>) faculityList;
    }
    public ObservableList<String> getMinor1() {
        List<String> minor1List = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT minorID from minors;";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                minor1List.add(resultSet.getString("minor1"));
            }
            minor1List = FXCollections.observableArrayList(minor1List);

        } catch (SQLException e) {
            System.out.println("Unable to get list of minor1.");
            e.printStackTrace();
        }

        assert minor1List instanceof ObservableList<String>;
        return (ObservableList<String>) minor1List;
    }
    public ObservableList<String> getMinor2() {
        List<String> minor2List = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT minorID from minors;";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                minor2List.add(resultSet.getString("minor2"));
            }
            minor2List = FXCollections.observableArrayList(minor2List);

        } catch (SQLException e) {
            System.out.println("Unable to get list of minors2.");
            e.printStackTrace();
        }

        assert minor2List instanceof ObservableList<String>;
        return (ObservableList<String>) minor2List;
    }
    public void createDepartment(String departmentID, String departmentName, String facultyID, String hod, String minor1, String minor2) {
        Statement stmt;
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO departments "
                    + "(deptID, deptName, facultyID, hod, minor1, minor2)"
                    + "VALUES ('"+ departmentID +"', '"+ departmentName +"', '"+facultyID+"', '"+hod+"', '"+minor1+"', '"+minor2+"')");

        } catch (SQLException e) {
            System.out.println("Unable to create department");
            e.printStackTrace();
        }
    }
    public void updateDepartment(String departmentID, String departmentName, String facultyID, String hod, String minor1, String minor2) {
        try {
            Statement stmt = conn.createStatement();
            String update = "UPDATE departments SET deptID = '"+departmentID+"', deptName ='"+departmentName+"', facultyID ='"+facultyID+"', hod='"+hod+"',minor1='"+minor1+"', minor2='"+minor2+"' WHERE deptID ='"+departmentID+"';";
            stmt.executeUpdate(update);
        } catch (SQLException e) {
            System.out.println("Unable to update department info.");
            e.printStackTrace();
        }
    }
    public void deleteDepartment(String departmentID) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM departments WHERE deptID='"+departmentID+"';");

        } catch (SQLException e) {
            System.out.println("Unable to delete department.");
            e.printStackTrace();
        }
    }
}
