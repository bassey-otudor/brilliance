package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import learn.brilliance.Controller.Admin.TeachersController;
import learn.brilliance.Model.Accounts.Teacher;
import learn.brilliance.Model.Accounts.Student;
import learn.brilliance.View.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    connectDB connectDB;
    courseRecords courseRecords;

    // Admin variables
    private boolean adminLoginStatus;
    private final ObservableList<Faculty> faculties;
    private final ObservableList<Department> departments;
    private final ObservableList<Teacher> teachers;
    private final ObservableList<Course> courses;
    private final ObservableList<Degree> degree;
    private final ObservableList<Minor> minor;
    private final ObservableList<Student> students;
    public final TeachersController teachersController = new TeachersController();

    // Teacher variables
    private final Teacher teacher;
    private boolean teacherLoginStatus;
    private final ObservableList<CourseResults> courseResults;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.connectDB = new connectDB();
        this.courseRecords = new courseRecords();

        // Admin data
        this.adminLoginStatus = false;
        this.faculties = FXCollections.observableArrayList();
        this.departments = FXCollections.observableArrayList();
        this.teachers = FXCollections.observableArrayList();
        this.courses = FXCollections.observableArrayList();
        this.degree = FXCollections.observableArrayList();
        this.minor = FXCollections.observableArrayList();
        this.students = FXCollections.observableArrayList();

        // Teacher data
        this.teacherLoginStatus = false;
        this.teacher = new Teacher("", "", "", null, null, null, null, null , null, null, null, null, null);
        this.courseResults = FXCollections.observableArrayList();
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
    public courseRecords getCourseRecords() { return courseRecords; }


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
        } catch (SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Teacher getTeacher() {
        return teacher;
    }

    // Teacher login control
    public boolean getTeacherLoginStatus() { return this.teacherLoginStatus; }
    public void setTeacherLoginStatus(boolean teacherLoginStatus) { this.teacherLoginStatus = teacherLoginStatus; }
    public void evaluateTeacherLogin(String username, String password) {
        ResultSet resultSet = connectDB.getTeacher(username, password);
        try {
            if(resultSet.isBeforeFirst()) {
                this.teacher.teacherIDProperty().set(resultSet.getString("teacherID"));
                this.teacher.firstNameProperty().set(resultSet.getString("fName"));
                this.teacher.lastNameProperty().set(resultSet.getString("lName"));
                this.teacher.genderProperty().set(resultSet.getString("gender"));
                this.teacher.dobProperty().set(resultSet.getString("dob"));
                this.teacher.phoneNumberProperty().set(resultSet.getString("phoneNum"));
                this.teacher.emailProperty().set(resultSet.getString("email"));
                this.teacher.passwordProperty().set(resultSet.getString("password"));
                this.teacher.facultyIDProperty().set(resultSet.getString("facultyID"));
                this.teacher.departmentIDProperty().set(resultSet.getString("deptID"));
                this.teacher.firstCourseProperty().set(resultSet.getString("course1"));
                this.teacher.secondCourseProperty().set(resultSet.getString("course2"));
                this.teacher.positionProperty().set(resultSet.getString("position"));

                this.teacherLoginStatus = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
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

        } catch (SQLException ex) {
            System.out.println("Unable to get faculty data");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve faculty data");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
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
                        resultSet.getString("dob"),
                        resultSet.getString("phoneNum"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("facultyID"),
                        resultSet.getString("deptID"),
                        resultSet.getString("course1"),
                        resultSet.getString("course2"),
                        resultSet.getString("position")
                );

                teacherList.add(teacherData);
            }
        } catch (SQLException ex) {
            System.out.println("Unable to add teacher data into a list.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve course data.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
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
                        resultSet.getString("minor"),
                        resultSet.getString("duration"),
                        resultSet.getString("numberOfCourses"),
                        resultSet.getString("totalCredits"),
                        resultSet.getString("requiredCredits"),
                        resultSet.getString("facultyID")
                );
                degreeList.add(degreeData);
            }

        } catch (SQLException e) {
            System.out.println("Unable to retrieve degree data");
        }

        return degreeList;
    }
    public ObservableList<Minor> getAllMinors() { return minor; }
    public ObservableList<Minor> setAllMinors() {
        ResultSet resultSet = Model.getInstance().connectDB.getMinorData();
        ObservableList<Minor> minorList = FXCollections.observableArrayList();
        Minor minorData;

        try {
            while (resultSet.next()) {
                minorData = new Minor(
                        resultSet.getString("minorID"),
                        resultSet.getString("minorName"),
                        resultSet.getString("degreeID"),
                        resultSet.getString("facultyID"),
                        resultSet.getString("departmentID"),
                        resultSet.getString("course1"),
                        resultSet.getString("course2"),
                        resultSet.getString("course3"),
                        resultSet.getString("course4"),
                        resultSet.getString("course5")
                );
                minorList.add(minorData);
            }

        } catch (SQLException ex) {
            System.out.println("Unable to get minor data");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return minorList;

    }

    public ObservableList<Student> getAllStudents() { return students; }
    public ObservableList<Student> setAllStudents() {
        ResultSet resultSet = Model.getInstance().connectDB.getStudentData();
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        Student studentData;

        try {
            while (resultSet.next()) {
                studentData = new Student(
                        resultSet.getString("studentID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("dob"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("facultyID"),
                        resultSet.getString("departmentID"),
                        resultSet.getString("degreeID"),
                        resultSet.getString("minorID"),
                        resultSet.getString("level"),
                        resultSet.getString("registrationDate")

                );
                studentList.add(studentData);
            }

        } catch (SQLException ex) {
            System.out.println("Unable to get student data.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentList;
    }

}
