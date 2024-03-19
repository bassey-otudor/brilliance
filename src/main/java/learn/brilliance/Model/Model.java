package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import learn.brilliance.Controller.Admin.FacultiesController;
import learn.brilliance.View.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    connectDB connectDB;
    FacultiesController facultiesController;

    // Admin variables
    private boolean adminLoginStatus;
    private final ObservableList<Faculty> faculties;
    private final ObservableList<Department> departments;
    private Model() {
        this.viewFactory = new ViewFactory();
        this.connectDB = new connectDB();

        // Admin data
        this.adminLoginStatus = false;
        this.faculties = FXCollections.observableArrayList();
        this.departments = FXCollections.observableArrayList();
    }
    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
    public connectDB getConnectDB() {
        return connectDB;
    }


    // Admin login  control
    public boolean getAdminLoginStatus() {
        return this.adminLoginStatus;
    }
    public void setAdminLoginStatus(boolean adminLoginStatus) {
        this.adminLoginStatus = adminLoginStatus;
    }

    public void evaluateAdminLogin(String username, String password) {
        ResultSet resultSet = connectDB.getAdmin(username, password);
        try {
            if(resultSet.isBeforeFirst()) {
                this.adminLoginStatus = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Binding data section
    // Faculties
    public ObservableList<Faculty> getFaculties() {
        return faculties;
    }
    public ObservableList<Faculty> setFaculties() {
        ResultSet resultSet = connectDB.getFacultyData();

        ObservableList<Faculty> facultiesList = FXCollections.observableArrayList();
        Faculty facultyData;
        try{

            while (resultSet.next()) {
                facultyData = new Faculty(
                        resultSet.getString("FacultyID"),
                        resultSet.getString("FacultyName"),
                        resultSet.getString("Director"),
                        resultSet.getString("Department1"),
                        resultSet.getString("Department2"),
                        resultSet.getString("Department3")
                );

                facultiesList.add(facultyData);
            }

        } catch (SQLException e) {
            System.out.println("Unable to get faculty data");
            e.printStackTrace();
        }
        return facultiesList;
    }

    // Departments
    public ObservableList<Department> getDepartments() { return departments; }

    /**
     * This method used the ResultSet returned by the getDepartmentData() method.
     * The attributes of every department is gotten and stored in an ObservableList of departments.
     * It is returned and the method is called and the list used to initialise the departmentView table.
     */
    public ObservableList<Department> setDepartments() {
        ResultSet resultSet = connectDB.getDepartmentData();
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        Department departmentData;
        try {
            while (resultSet.next()) {
                departmentData = new Department(
                     resultSet.getString("deptID"),
                     resultSet.getString("deptName"),
                     resultSet.getString("facultyID"),
                     resultSet.getString("hod"),
                     resultSet.getString("minor1"),
                     resultSet.getString("minor2")
                );
                departmentList.add(departmentData);
            }
        } catch (SQLException e) {
            System.out.println("Unable to retrieve faculty data");
            e.printStackTrace();
        }
        return departmentList;
    }


}
