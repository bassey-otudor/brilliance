package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import learn.brilliance.View.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    connectDB connectDB;

    // Admin variables
    private boolean adminLoginStatus;
    private final ObservableList<Faculty> faculties;
    private final ObservableList<Department> departments;
    private final ObservableList<Teacher> teachers;
    private final ObservableList<Course> courses;
    private final ObservableList<Degree> degree;
    private Model() {
        this.viewFactory = new ViewFactory();
        this.connectDB = new connectDB();

        // Admin data
        this.adminLoginStatus = false;
        this.faculties = FXCollections.observableArrayList();
        this.departments = FXCollections.observableArrayList();
        this.teachers = FXCollections.observableArrayList();
        this.courses = FXCollections.observableArrayList();
        this.degree = FXCollections.observableArrayList();
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
    public ObservableList<Faculty> getAllFaculties() {
        return faculties;
    }
    public ObservableList<Faculty> setAllFaculties() {
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
    public ObservableList<Department> getAllDepartments() { return departments; }

    /**
     * This method uses the ResultSet returned by the getDepartmentData() method.
     * The attributes of every department is gotten and stored in an ObservableList of departments.
     * This method is called and the list used to initialise the departmentView table.
     */
    public ObservableList<Department> setAllDepartments() {
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

    // Teachers
    public ObservableList<Teacher> getAllTeachers() { return teachers; }
    public ObservableList<Teacher> setAllTeachers() {
        ResultSet resultSet = connectDB.getTeacherData();
        ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
        Teacher teacherData;
        try {
            while (resultSet.next()) {
                teacherData = new Teacher(
                        resultSet.getString("teacherID"),
                        resultSet.getString("fName"),
                        resultSet.getString("lName"),
                        resultSet.getString("gender"),
                        resultSet.getString("phoneNum"),
                        resultSet.getString("email"),
                        resultSet.getString("deptID"),
                        resultSet.getString("dob"),
                        resultSet.getString("password"),
                        resultSet.getString("course1"),
                        resultSet.getString("course2"),
                        resultSet.getString("position"),
                        resultSet.getString("facultyID")
                );

                teacherList.add(teacherData);
            }
        } catch (SQLException e) {
            System.out.println("Unable to add teacher data into a list.");
            e.printStackTrace();
        }

        return  teacherList;
    }

    // Courses
    public ObservableList<Course> getAllCourses() { return courses; }
    public ObservableList<Course> setAllCourses() {
        ResultSet resultSet = connectDB.getCourseData();
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        Course courseData;

        try {
            while(resultSet.next()) {
                courseData = new Course (
                      resultSet.getString("courseID"),
                      resultSet.getString("courseName"),
                      resultSet.getString("courseLevel"),
                      resultSet.getString("deptID"),
                      resultSet.getString("teacherID"),
                      resultSet.getString("teacherName"),
                      resultSet.getString("facultyID"),
                      resultSet.getString("creditValue")

                );
                courseList.add(courseData);
            }
        } catch (SQLException e) {
            System.out.println("Unable to retrieve course data.");
            e.printStackTrace();
        }

        return courseList;
    }

    //  Degrees
    public ObservableList<Degree> getAllDegrees() { return degree; }

    public ObservableList<Degree> setAllDegrees() {
        ResultSet resultSet = Model.getInstance().connectDB.getDegreeData();
        ObservableList<Degree> degreeList = FXCollections.observableArrayList();
        Degree degreeData;

        try {
            while(resultSet.next()) {
                degreeData = new Degree(
                        resultSet.getString("degreeID"),
                        resultSet.getString("degreeName"),
                        resultSet.getString("departmentID"),
                        resultSet.getString("duration"),
                        resultSet.getString("numberOfCourses"),
                        resultSet.getString("totalCredits"),
                        resultSet.getString("requiredCredits")
                );
                degreeList.add(degreeData);
            }

        } catch (SQLException e) {
            System.out.println("Unable to retrieve degree data");
        }

        return degreeList;
    }

}
