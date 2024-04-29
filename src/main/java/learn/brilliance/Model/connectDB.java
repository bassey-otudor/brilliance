package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectDB {
    private Connection conn;
    private PreparedStatement preparedStatement;
    private static final String connURL = "jdbc:sqlite:school_database_structure.db";
    private static final String username = "root";
    private static final String password = "";

    // Connects to the general database (school_database_structure)
    public connectDB() {
        try {
            this.conn = DriverManager.getConnection(connURL, username, password);

        } catch (SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Admin section
     */

    // Get the admins' information
    public ResultSet getAdmin(String username, String password) {
        String getAdmin = "SELECT * FROM admin WHERE username=? AND password=?";
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(getAdmin);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    // Get the teacher's information
    public ResultSet getTeacher(String teacherID, String password) {
        String getTeacher = "SELECT * FROM teacher WHERE teacherID=? AND password=?";
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(getTeacher);
            preparedStatement.setString(1, teacherID);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    // Dashboard
    public ResultSet getPieChartData() {
        String chartData = "SELECT departmentID, COUNT(departmentID) AS Occurrences FROM students GROUP BY departmentID;";

        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(chartData);

        } catch (SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }
    public ResultSet getTotalRegisteredStudents() {
        String getRegisteredStudents = "SELECT registrationDate, COUNT(registrationDate) AS Occurences FROM students GROUP BY registrationDate;";

        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getRegisteredStudents);

        } catch (SQLException ex) {
            System.out.println("Unable to get registered students.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    public ResultSet getAllRegisteredMaleStudents() {
        String getRegisteredMaleStudents = "SELECT registrationDate , COUNT(gender) AS Male FROM students WHERE gender = 'Male' GROUP BY registrationDate;";

        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getRegisteredMaleStudents);

        } catch (SQLException ex) {
            System.out.println("Unable to get registered male students.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    public ResultSet getAllRegisteredFemaleStudents() {
        String getRegisteredFemaleStudents = "SELECT registrationDate , COUNT(gender) AS Female FROM students WHERE gender = 'Female' GROUP BY registrationDate;;";

        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getRegisteredFemaleStudents);

        } catch (SQLException ex) {
            System.out.println("Unable to get registered female students.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    // Faculty section
    /**
     * This method is used to retrieve the faculty data from the database.
     *
     * @return A ResultSet containing the faculty data.
     */
    public ResultSet getFacultyData() {
        Statement stmt;
        ResultSet resultSet = null;
        try{
            stmt = this.conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM faculties;");
        } catch (SQLException ex) {
            System.out.println("Unable to get faculty data");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    public ObservableList<String> getDirectors() {

        List<String> directorList = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String getDirectors = "SELECT CONCAT(fname, ' ', lName) AS facultyDirector FROM teachers WHERE position = 'Director' ORDER BY facultyDirector ASC";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getDirectors);

            while (resultSet.next()) {
                directorList.add(resultSet.getString("facultyDirector"));
            }

            directorList = FXCollections.observableArrayList(directorList);

        } catch (SQLException ex) {
            System.out.println("Error retrieving ");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(directorList);
    }

    public void createFaculty(String facultyID,String facultyName, String director, String department1, String department2, String department3) {
        String createFaculty = "INSERT into faculties"
                + "(facultyID, facultyName, director, department1, department2, department3)"
                + "VALUES (?,?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(createFaculty);
            preparedStatement.setString(1, facultyID);
            preparedStatement.setString(2, facultyName);
            preparedStatement.setString(3, director);
            preparedStatement.setString(4, department1);
            preparedStatement.setString(5, department2);
            preparedStatement.setString(6, department3);
            preparedStatement.executeUpdate();

        } catch (SQLException x) {
            System.out.println("Error inserting faculty data");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, x);
        }

    }
    public void updateFaculty(String facultyID,String facultyName, String director) {
        String updateFaculty = "UPDATE faculties SET facultyName = ?, Director = ?, WHERE facultyID = ?";
        try {
            preparedStatement = conn.prepareStatement(updateFaculty);
            preparedStatement.setString(1, facultyName);
            preparedStatement.setString(2, director);
            preparedStatement.setString(3, facultyID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error executing update.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteFaculty(String facultyID) {
        String deleteFaculty = "DELETE FROM faculties WHERE facultyID = ?";
        try{
            preparedStatement = conn.prepareStatement(deleteFaculty);
            preparedStatement.setString(1, facultyID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error deleting faculty.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteFacultyDepartments(String facultyID) {
        String deleteFacultyDepartments = "DELETE FROM departments WHERE facultyID = ?";
        try{
            preparedStatement = conn.prepareStatement(deleteFacultyDepartments);
            preparedStatement.setString(1, facultyID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to delete departments in faculty.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Department section
     */
    public ObservableList<String> getHod(String facultyID) {
        String getHod = "SELECT CONCAT(fname, ' ', lName) AS departmentHOD FROM teachers WHERE position = 'HOD' AND facultyID = ? ORDER BY departmentHOD ASC ";
        List<String> hodList = new ArrayList<>();
        ResultSet resultSet;

        try {
            preparedStatement = conn.prepareStatement(getHod);
            preparedStatement.setString(1, facultyID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                hodList.add(resultSet.getString("departmentHOD"));
            }

            hodList = FXCollections.observableArrayList(hodList);

        } catch (SQLException ex) {
            System.out.println("Error retrieving HODs");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(hodList);
    }
    public ObservableList<String> getFaculties() {
        List<String> faculityList = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String selectFaculty = "SELECT facultyID from faculties ORDER BY facultyID ASC;";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectFaculty);

            while (resultSet.next()) {
                faculityList.add(resultSet.getString("facultyID"));
            }
            faculityList = FXCollections.observableArrayList(faculityList);

        } catch (SQLException ex) {
            System.out.println("Unable to get list of faculties.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(faculityList);
    }
    public ObservableList<String> getMinor(String facultyID) {
        String selectMinors = "SELECT * FROM minor WHERE facultyID = ? ORDER BY minorName ASC ";
        List<String> minorList = new ArrayList<>();
        ResultSet resultSet;

        try {
            preparedStatement = conn.prepareStatement(selectMinors);
            preparedStatement.setString(1, facultyID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                minorList.add(resultSet.getString("minorName"));
            }
            minorList = FXCollections.observableArrayList(minorList);

        } catch (SQLException ex) {
            System.out.println("Unable to get list of minor1.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(minorList);
    }

    /**
     * This method retrieves the department data needed to populate the department tableView.
     * It is called by a private setDepartments() method in the Model class.
     */
    public ResultSet getDepartmentData() {
        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM departments;");
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve faculty data.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    public ResultSet checkEmptyDepartmentsColumns(String facultyID) {
        String checkDepartments = "SELECT * FROM faculties WHERE facultyID=?;";
        ResultSet resultSet = null;
        try{
            preparedStatement = conn.prepareStatement(checkDepartments);
            preparedStatement.setString(1, facultyID);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.out.println("Unable to get departments in the faculty.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }
    public void createDepartment(String departmentID, String departmentName, String facultyID, String hod, String minor1, String minor2) {
        String createDepartment = "INSERT INTO departments "
                + "(departmentID, departmentName, facultyID, hod, minor1, minor2)"
                + "VALUES (?,?,?,?,?,?)";

        try{
            preparedStatement = conn.prepareStatement(createDepartment);
            preparedStatement.setString(1, departmentID);
            preparedStatement.setString(2, departmentName);
            preparedStatement.setString(3, facultyID);
            preparedStatement.setString(4, hod);
            preparedStatement.setString(5, minor1);
            preparedStatement.setString(6, minor2);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to create department");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateDepartment(String departmentID, String departmentName, String facultyID, String hod, String minor1, String minor2) {
        String updateDepartment = "UPDATE departments SET deptID =?, deptName =?, facultyID =?, hod =?, minor1 =?, minor2 =? WHERE deptID = ?";
        try {
            preparedStatement = conn.prepareStatement(updateDepartment);
            preparedStatement.setString(1, departmentID);
            preparedStatement.setString(2, departmentName);
            preparedStatement.setString(3, facultyID);
            preparedStatement.setString(4, hod);
            preparedStatement.setString(5, minor1);
            preparedStatement.setString(6, minor2);
            preparedStatement.setString(7, departmentID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to update department info.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteDepartment(String departmentID) {
        String deleteDepartment = "DELETE FROM departments WHERE deptID = ?";
        try {
            preparedStatement = conn.prepareStatement(deleteDepartment);
            preparedStatement.setString(1, departmentID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to delete department.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void insertDepartmentInFaculty(String column, String departmentName, String facultyID) {
        String insertDepartment = "UPDATE faculties SET ? =? WHERE facultyID =?;";
        try {
            preparedStatement = conn.prepareStatement(insertDepartment);
            preparedStatement.setString(1, column);
            preparedStatement.setString(2, departmentName);
            preparedStatement.setString(3, facultyID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to add department to the designated faculty.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Teacher Page
     */
    public ResultSet getTeacherData() {
        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM teachers;");

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve teacher data.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }
    public ObservableList<String> getPosition() {
        List<String> positionList = new ArrayList<>();

        String selectPositions = "SELECT position FROM position ORDER BY position ASC;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectPositions);
            while (resultSet.next()) {
                positionList.add(resultSet.getString("position"));

            }

            positionList = FXCollections.observableArrayList(positionList);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve positions.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(positionList);
    }
    public void createTeacher(String teacherID, String firstName, String lastName, String gender, String phoneNumber, String email, String departmentID, LocalDate dob, String password, String course1, String course2, String position, String facultyID) {
        String createTeacher = "INSERT INTO teachers "
                + "(teacherID, fName, lName, gender, phoneNum, email, deptID, dob, password, course1, course2, position, facultyID)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = conn.prepareStatement(createTeacher);
            preparedStatement.setString(1, teacherID);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, departmentID);
            preparedStatement.setString(8, dob.toString());
            preparedStatement.setString(9, password);
            preparedStatement.setString(10, course1);
            preparedStatement.setString(11, course2);
            preparedStatement.setString(12, position);
            preparedStatement.setString(13, facultyID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to add teacher to the database.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateTeacher(String teacherID, String firstName, String lastName, String gender, String phoneNumber, String email, String departmentID, LocalDate dob, String course1, String course2, String position, String facultyID) {
        String updateTeacher= "UPDATE teachers SET fName =?, lName =?, gender =?, phoneNum =?, email =?, deptID =?, dob =?, course1=?, course2 =?, position =?, facultyID =? WHERE teacherID =?;";
        try {
            preparedStatement = conn.prepareStatement(updateTeacher);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, departmentID);
            preparedStatement.setString(7, dob.toString());
            preparedStatement.setString(8, course1);
            preparedStatement.setString(9, course2);
            preparedStatement.setString(10, position);
            preparedStatement.setString(11, facultyID);
            preparedStatement.setString(12, teacherID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to update teacher info.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, "Error in update syntax.", ex);
        }
    }
    public void deleteTeacher(String teacherID) {
        String deleteTeacher = "DELETE FROM teachers WHERE teacherID =?;";
        try {
            preparedStatement = conn.prepareStatement(deleteTeacher);
            preparedStatement.setString(1, teacherID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to delete teacher.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, "Error in delete syntax.", ex);
        }

    }
    public int getTeacherRowCount() {

        Statement stmt;
        ResultSet resultSet;
        int rowCount = 0;
        try {
            stmt = this.conn.createStatement();
            resultSet = stmt.executeQuery("SELECT seq FROM sqlite_sequence WHERE name = 'teachers';");
            if(resultSet.next()) {
                rowCount = resultSet.getInt("seq");
            }
        } catch (SQLException ex) {
            System.out.println("Unable to get row count.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowCount;
    }
    public void insertTeacherInCourse (String teacherID, String teacherName, String courseID, boolean operation) {
        if(operation) {
            try{
                String insertTeacher = "UPDATE courses SET teacherID =?, teacherName =? WHERE courseID =?;";
                preparedStatement = conn.prepareStatement(insertTeacher);
                preparedStatement.setString(1, teacherID);
                preparedStatement.setString(2, teacherName);
                preparedStatement.setString(3, courseID);
                preparedStatement.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Unable to assign course to teacher.");
                Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try{
                String deleteTeacher = "UPDATE courses SET teacherID =?, teacherName =?";
                preparedStatement = conn.prepareStatement(deleteTeacher);
                preparedStatement.setString(1, teacherID);
                preparedStatement.setString(2, null);
                preparedStatement.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Unable to delete course from teacher.");
                Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    // Courses page
    public ResultSet getCourseData() {
        String getCourseData = "SELECT * FROM courses;";

        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getCourseData);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve course data.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }
    public ObservableList<String> getCourseTeacher(String departmentID) {
        List<String> teacherList = new ArrayList<>();
        String selectTeacherNames = "SELECT CONCAT(fname, ' ', lName) AS teacherFullNames FROM teachers WHERE deptID =? ORDER BY teacherFullNames ASC";
        ResultSet resultSet;

        try {
            preparedStatement = conn.prepareStatement(selectTeacherNames);
            preparedStatement.setString(1, departmentID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               teacherList.add(resultSet.getString("teacherFullNames"));
            }
            teacherList = FXCollections.observableArrayList(teacherList);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve list of filtered list of teachers.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(teacherList);
    }

    public ObservableList<String> getTeacherID(String firstName, String lastName) {
        List<String> teacherID = new ArrayList<>();
        String selectTeacherID = "SELECT teacherID FROM teachers WHERE fName =? AND lName =?";
        ResultSet resultSet;

        try {
            preparedStatement = conn.prepareStatement(selectTeacherID);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                teacherID.add(resultSet.getString("teacherID"));
            }
            teacherID = FXCollections.observableArrayList(teacherID);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve list of filtered list of teachers.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(teacherID);
    }

    public ObservableList<String> getCreditValue() {
        List<String> creditValueList = new ArrayList<>();

        String selectCreditValue = "SELECT * FROM creditValue";

        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectCreditValue);

            while (resultSet.next()) {
                creditValueList.add(resultSet.getString("creditValue"));
            }
            creditValueList = FXCollections.observableArrayList(creditValueList);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve list of credit values.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(creditValueList);
    }
    public ObservableList<String> getCoursePosition() {
        List<String> coursePositionList = new ArrayList<>();

        String selectCoursePosition = "SELECT * FROM course_position";

        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectCoursePosition);

            while (resultSet.next()) {
                coursePositionList.add(resultSet.getString("course_position"));
            }
            coursePositionList = FXCollections.observableArrayList(coursePositionList);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve list of course positions.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(coursePositionList);
    }

    /**
     * Creates a new course in the database.
     *
     * @param courseID The unique ID of the course.
     * @param courseName The name of the course.
     * @param courseLevel The level of the course.
     * @param departmentID The ID of the department that the course belongs to.
     * @param teacherID The ID of the teacher that is teaching the course.
     * @param facultyID The ID of the faculty that is associated with the course.
     */
    public void createCourse(String courseID, String courseName, String courseLevel, String departmentID, String creditValue,String teacherID, String teacherName,String facultyID) {

        String createCourse = "INSERT INTO Courses " +
                "(courseID, courseName, courseLevel, deptID, creditValue ,teacherID, teacherName, facultyID)" +
                "VALUES (?,?,?,?,?,?,?,?);";
        try {

            preparedStatement = conn.prepareStatement(createCourse);
            preparedStatement.setString(1, courseID);
            preparedStatement.setString(2, courseName);
            preparedStatement.setString(3, courseLevel);
            preparedStatement.setString(4, departmentID);
            preparedStatement.setString(5, creditValue);
            preparedStatement.setString(6, teacherID);
            preparedStatement.setString(7, teacherName);
            preparedStatement.setString(8, facultyID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to create teacher.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is used to update a course in the database.
     *
     * @param courseID The unique ID of the course to update.
     * @param courseName The name of the course.
     * @param courseLevel The level of the course.
     * @param departmentID The ID of the department that the course belongs to.
     * @param teacherID The ID of the teacher that is teaching the course.
     * @param facultyID The ID of the faculty that is associated with the course.
     */
    public void updateCourse(String courseID, String courseName, String courseLevel, String departmentID, String creditValue,String teacherID, String teacherName, String facultyID) {
        String updateCourse = "UPDATE courses SET courseID =?, courseName =?, courseLevel =?, deptID =?, creditValue =?,teacherID =?, teacherName =?, facultyID =? WHERE courseID =?";
        try {
            preparedStatement = conn.prepareStatement(updateCourse);
            preparedStatement.setString(1, courseID);
            preparedStatement.setString(2, courseName);
            preparedStatement.setString(3, courseLevel);
            preparedStatement.setString(4, departmentID);
            preparedStatement.setString(5, creditValue);
            preparedStatement.setString(6, teacherID);
            preparedStatement.setString(7, teacherName);
            preparedStatement.setString(8, facultyID);
            preparedStatement.setString(9, courseID);
            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("Unable to update course.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is used to delete a course from the database.
     *
     * @param courseID The ID of the course to delete.
     */
    public void deleteCourse(String courseID) {
        String deleteCourse = "DELETE FROM courses WHERE courseID =?";
        try {
            preparedStatement = conn.prepareStatement(deleteCourse);
            preparedStatement.setString(1, courseID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to delete course.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
 * This method is used to insert or delete a course from a teacher.
 *
 * @param columnName The column name of the courses table that will be updated.
 * @param teacherID The ID of the teacher that the course will be assigned or removed from.
 * @param courseID The ID of the course that will be assigned or removed.
 * @param operation A boolean value indicating whether to insert or delete the course.
 */
    public void insertCoursesInTeacher(String columnName, String teacherID, String courseID, boolean operation) {

        if (operation) {
            try {

                String insertCourse = "UPDATE teachers SET ?=? WHERE teacherID =?;";
                preparedStatement = conn.prepareStatement(insertCourse);
                preparedStatement.setString(1, columnName);
                preparedStatement.setString(2, courseID);
                preparedStatement.setString(3, teacherID);
                preparedStatement.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Unable to assign courses to the designated teacher.");
                Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try {

                String deleteCourse = "UPDATE teachers SET  ?= null WHERE teacherID =?;";
                preparedStatement = conn.prepareStatement(deleteCourse);
                preparedStatement.setString(1, courseID);
                preparedStatement.setString(2, teacherID);
                preparedStatement.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Unable to delete courses from the designated teacher.");
                Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is used to check if a teacher has any courses assigned to them.
     *
     * @param teacherID The ID of the teacher to check.
     * @return A ResultSet containing the teacher's information, including any courses they are assigned to.
     */
    public ResultSet checkTeacherCoursesColumns(String teacherID) {
        String checkCourses = "SELECT teacherID, course1, course2 FROM teachers WHERE teacherID =?;";
        ResultSet resultSet = null;
        try{
            preparedStatement = conn.prepareStatement(checkCourses);
            preparedStatement.setString(1, teacherID);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.out.println("Unable to get verify assigned courses.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }


    // Degree methods

    public ResultSet getDegreeData() {
        Statement stmt;
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM department_degrees;");

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve degree data.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }

    /**
     * Returns a list of all available degree durations.
     *
     * @return a list of degree durations
     */
    public ObservableList<String> getDuration() {
        List<String> durationList = new ArrayList<>();

        String selectDuration = "SELECT duration FROM degree_duration;";
        Statement stmt;
        ResultSet resultSet;
        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectDuration);
            while (resultSet.next()) {
                durationList.add(resultSet.getString("duration"));
            }

        } catch (SQLException ex) {
            System.out.println("Unable to get duration list.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(durationList);
    }

    /**
     * This method is used to retrieve the list of minors associated with a specific department.
     *
     * @param departmentID The ID of the department to retrieve the minors for.
     * @return An ObservableList containing the list of minors associated with the department.
     */
    public ObservableList<String> getDepartmentMinors(String departmentID) {
        List<String> minorList = new ArrayList<>();

        String selectCourses = "SELECT * FROM minors WHERE departmentID =? ORDER BY minorID ASC;";
        ResultSet resultSet;

        try {
            preparedStatement = conn.prepareStatement(selectCourses);
            preparedStatement.setString(1, departmentID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                minorList.add(resultSet.getString("minorID"));
            }
            minorList = FXCollections.observableArrayList(minorList);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve courses.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(minorList);
    }

    /**
     * Creates a new degree in the database.
     *
     * @param degreeID The unique ID of the degree.
     * @param degreeName The name of the degree.
     * @param departmentID The ID of the department that the degree belongs to.
     * @param minor The minor that is associated with the degree.
     * @param duration The duration of the degree.
     * @param numberOfCourses The number of courses required for the degree.
     * @param totalCredits The total credits required for the degree.
     * @param requiredCredits The required credits for the degree.
     */
    public void createDegree(String degreeID, String degreeName, String departmentID, String minor, String duration, String numberOfCourses, String totalCredits, String requiredCredits, String facultyID) {
        String createDegree = "INSERT INTO  department_degrees " +
                "(degreeID, degreeName, departmentID, minor, duration, numberOfCourses, totalCredits, requiredCredits, facultyID)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(createDegree);
            preparedStatement.setString(1, degreeID);
            preparedStatement.setString(2, degreeName);
            preparedStatement.setString(3, departmentID);
            preparedStatement.setString(4, minor);
            preparedStatement.setString(5, duration);
            preparedStatement.setString(6, numberOfCourses);
            preparedStatement.setString(7, totalCredits);
            preparedStatement.setString(8, requiredCredits);
            preparedStatement.setString(9, facultyID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to create degree");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates the information of a degree in the database.
     *
     * @param degreeID The unique ID of the degree to update.
     * @param degreeName The name of the degree to update.
     * @param departmentID The ID of the department that the degree belongs to.
     * @param minor The minor that is associated with the degree.
     * @param duration The duration of the degree.
     * @param numberOfCourses The number of courses required for the degree.
     * @param totalCredits The total credits required for the degree.
     * @param requiredCredits The required credits for the degree.
     */
    public void updateDegree(String degreeID, String degreeName, String departmentID, String minor, String duration, String numberOfCourses, String totalCredits, String requiredCredits, String facultyID) {
        String updateCourse = "UPDATE department_degrees SET " +
                "degreeName =?, departmentID =?, minor =?,duration =?, numberOfCourses =?, totalCredits =?, requiredCredits =?, facultyID =? WHERE degreeID =?;";
        try {
            preparedStatement = conn.prepareStatement(updateCourse);
            preparedStatement.setString(1, degreeName);
            preparedStatement.setString(2, departmentID);
            preparedStatement.setString(3, minor);
            preparedStatement.setString(4, duration);
            preparedStatement.setString(5, numberOfCourses);
            preparedStatement.setString(6, totalCredits);
            preparedStatement.setString(7, requiredCredits);
            preparedStatement.setString(8, facultyID);
            preparedStatement.setString(9, degreeID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to update course.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes a degree from the database.
     *
     * @param degreeID The unique ID of the degree to delete.
     */
    public void deleteDegree(String degreeID) {
        String deleteDegree = "DELETE from department_degrees WHERE degreeID =?;";
        try {
            preparedStatement = conn.prepareStatement(deleteDegree);
            preparedStatement.setString(1, degreeID);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Unable to delete course.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is used to retrieve the number of rows in the department_degrees table.
     *
     * @return The number of rows in the department_degrees table for the specified department.
     */
    public int getDegreeRowCount() {
        String degreeRowCount = "SELECT seq FROM sqlite_sequence WHERE name = 'department_degrees';";
        Statement stmt;
        ResultSet resultSet;
        int rowCount = 0;

        try {
            stmt = conn.createStatement();
            resultSet= stmt.executeQuery(degreeRowCount);
            if(resultSet.next()) {
                rowCount = resultSet.getInt("seq");
            }

        } catch (SQLException ex) {
            System.out.println("Unable to get degree row count.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowCount;
    }

    /**
     * Minor section
     */

    public ResultSet getMinorData() {
        Statement stmt;
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM minors;");

        } catch (SQLException ex) {
            System.out.println("Unable to get minors.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    public ObservableList<String> getMinorNumber() {
        List<String> minorNumberList = new ArrayList<>();
        String getMinorList = "SELECT * FROM minor_number;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getMinorList);
            while (resultSet.next()) {
                minorNumberList.add(resultSet.getString("minorNumber"));
            }
            minorNumberList = FXCollections.observableArrayList(minorNumberList);

        } catch (SQLException ex) {
            System.out.println("Unable to get minor list");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(minorNumberList);
    }
    public ObservableList<String> getDepartmentDegrees(String departmentID) {
        String getDegree = "SELECT * FROM department_degrees WHERE departmentID =? ORDER BY degreeID ASC;";
        List<String> degreeList = new ArrayList<>();
        ResultSet resultSet;

        try {
            preparedStatement = conn.prepareStatement(getDegree);
            preparedStatement.setString(1, departmentID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                degreeList.add(resultSet.getString("degreeID"));
            }
            degreeList = FXCollections.observableArrayList(degreeList);

        } catch (SQLException e) {
            System.out.println("Unable to retrieve department degree programs");
        }

        return FXCollections.observableArrayList(degreeList);
    }
    public ObservableList<String> getCourseNumber() {
        List<String> courseNumberList = new ArrayList<>();
        String getCourseList = "SELECT * FROM minor_courseNumber;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getCourseList);
            while (resultSet.next()) {
                courseNumberList.add(resultSet.getString("courseNumber"));
            }
            courseNumberList = FXCollections.observableArrayList(courseNumberList);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve course number list.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(courseNumberList);
    }

    /**
     * Creates a new minor in the database.
     *
     * @param minorID The unique ID of the minor.
     * @param minorName The name of the minor.
     * @param degreeID The ID of the degree that the minor belongs to.
     * @param facultyID The ID of the faculty that the minor belongs to.
     * @param departmentID The ID of the department that the minor belongs to.
     * @param courseID The first course that is required for the minor.
     */
    public void createMinor(String minorID, String minorName, String degreeID, String facultyID, String departmentID, String courseID, String courseNumber) {
        String createMinor = "INSERT INTO minors" +
                "(minorID, minorName, degreeID, facultyID, departmentID, '"+courseNumber+"')" +
                "VALUES (?,?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(createMinor);
            preparedStatement.setString(1, minorID);
            preparedStatement.setString(2, minorName);
            preparedStatement.setString(3, degreeID);
            preparedStatement.setString(4, facultyID);
            preparedStatement.setString(5, departmentID);
            preparedStatement.setString(6, courseID);

        } catch (SQLException ex) {
            System.out.println("Unable to create minor");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateMinor(String minorID, String minorName, String degreeID, String facultyID, String departmentID, String courseID, String courseNumber) {
        String updateMinor = "UPDATE minors SET " +
                "minorName = '"+minorName+"', degreeID = '"+degreeID+"', facultyID = '"+facultyID+"'," +
                " departmentID = '"+departmentID+"', '"+courseNumber+"' = '"+courseID+"' WHERE minorID = '"+minorID+"';";

        Statement stmt;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(updateMinor);

        } catch (SQLException e) {
            System.out.println("Unable to update minor.");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to delete a minor from the database.
     *
     * @param minorID The ID of the minor to delete.
     */
    public void deleteMinor(String minorID) {
        String deleteMinor = "DELETE FROM minors WHERE minorID = '"+minorID+"'";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(deleteMinor);

        } catch (SQLException e) {
            System.out.println("Unable to delete minor.");
            e.printStackTrace();
        }
    }

    public void insertMinorIntoDepartment(String minorNumber, String minorName, String departmentID, boolean operation) {

        Statement stmt;

        if(operation) {
            try {
                String insertMinor = "UPDATE departments SET '"+minorNumber+"' = '"+minorName+"' WHERE deptID = '"+departmentID+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(insertMinor);

            } catch (SQLException e) {
                System.out.println("Unable to add minor to department.");
                e.printStackTrace();
            }

        } else {

            try {
                String insertMinor = "UPDATE departments SET '"+minorNumber+"' = null WHERE deptID = '"+departmentID+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(insertMinor);

            } catch (SQLException e) {
                System.out.println("Unable to add minor to department.");
                e.printStackTrace();
            }
        }

    }

    // Student section

    public ResultSet getStudentData() {
        Statement stmt;
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM students;");

        } catch (SQLException ex) {
            System.out.println("Unable to get all student data.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    public void createStudent(String studentID, String firstName, String lastName, String gender, String dob, String phoneNumber, String email, String password, String facultyID, String departmentID, String degreeID, String minorID, String level, String registrationDate) {
        String createStudent = "INSERT INTO students" +
                "(studentID, firstName, lastName, gender, dob, phoneNumber, email, password, facultyID, departmentID, degreeID, minorID, level, registrationDate)" +
                "VALUES ('"+studentID+"', '"+firstName+"', '"+lastName+"', '"+gender+"', '"+dob+"', '"+phoneNumber+"', '"+email+"', '"+password+"', '"+facultyID+"', '"+departmentID+"', '"+degreeID+"', '"+minorID+"', '"+level+"', '"+registrationDate+"')";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createStudent);

        } catch (SQLException e) {
            System.out.println("Unable to create student.");
            e.printStackTrace();
        }
    }
    public void updateStudent(String studentID, String firstName, String lastName, String gender, String dob, String phoneNumber, String email, String facultyID, String departmentID, String degreeID, String minorID, String level) {
        String updateStudent = "UPDATE students SET firstName = '"+firstName+"', lastName = '"+lastName+"', gender = '"+gender+"', dob = '"+dob+"', phoneNumber = '"+phoneNumber+"', email = '"+email+"', facultyID = '"+facultyID+"', departmentID = '"+departmentID+"', degreeID = '"+degreeID+"', minorID = '"+minorID+"', level = '"+level+"' WHERE studentID = '"+studentID+"';";
        Statement stmt;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(updateStudent);

        } catch (SQLException e) {
            System.out.println("Unable to update student.");
            e.printStackTrace();
        }
    }
    public void deleteStudent(String studentID) {
        String deleteStudent = "DELETE FROM students WHERE studentID = '"+studentID+"';";
        Statement stmt;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(deleteStudent);

        } catch (SQLException e) {
            System.out.println("Unable to delete student.");
            e.printStackTrace();
        }
    }
    public int getStudentRowCount() {
        int rowCount = 0;
        String studentCount = "SELECT seq FROM sqlite_sequence WHERE name ='students';";

        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(studentCount);
            while (resultSet.next()) {
             rowCount = resultSet.getInt("seq");
            }

        } catch (SQLException ex) {
            System.out.println("Unable to get student count.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowCount;
    }

    public ObservableList<String> getDegreeMinors(String degreeID) {
        List<String> minorList = new ArrayList<>();
        String getMinor = "SELECT * FROM minors WHERE degreeID = '"+degreeID+"' ORDER BY minorID ASC;";

        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getMinor);
            while (resultSet.next()) {
                minorList.add(resultSet.getString("minorID"));
            }
            minorList = FXCollections.observableArrayList(minorList);

        } catch (SQLException e) {
            System.out.println("Unable to get degree minors.");
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(minorList);
    }

    // Utility methods

    /**
     * This method is used to check if a specific data exists in a specific table.
     *
     * @param tableName The name of the table to search in.
     * @param idColumn The name of the column that contains the unique ID of the entity.
     * @param entityID The unique ID of the entity to check.
     * @param columnName The name of the column that contains the name of the entity.
     * @param entityName The name of the entity to check.
     * @return A boolean value indicating whether the entity exists or not.
     */
    public boolean checkData(String tableName, String idColumn, String entityID, String columnName, String entityName) {

        boolean doesExists = false;
        String checkCourse = "SELECT * FROM "+tableName+" WHERE "+idColumn+" = '"+entityID+"' OR '"+columnName+"' = '"+entityName+"';";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(checkCourse);
            while (resultSet.next()) {
                doesExists = true;
            }

        } catch (SQLException e) {
            System.out.println("Unable to verify if course exists.");
            e.printStackTrace();
        }

        return doesExists;
    }

    /**
     * This method is used to retrieve the list of available genders.
     *
     * @return An ObservableList containing the list of available genders.
     */
    public ObservableList<String> getGender() {
        List<String> genderList = new ArrayList<>();

        String selectGenders = "SELECT gender FROM genders;";
        Statement stmt;
        ResultSet resultSet;
        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectGenders);

            while (resultSet.next()) {
                genderList.add(resultSet.getString("gender"));
            }
            genderList = FXCollections.observableArrayList(genderList);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve gender data.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(genderList);
    }
    public ObservableList<String> getLevel() {
        List<String> levelList = new ArrayList<>();

        String selectLevels = "SELECT * FROM levels;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectLevels);

            while (resultSet.next()) {
                levelList.add(resultSet.getString("level"));
            }

        } catch (SQLException ex) {
            System.out.println("Unable to get levels");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(levelList);
    }

    /**
     * This method is used to retrieve the list of departments associated with a specific faculty.
     *
     * @param facultyID The ID of the faculty to retrieve the departments for.
     * @return An ObservableList containing the list of departments associated with the faculty.
     */
    public ObservableList<String> getFacultyDepartments(String facultyID) {
        List<String> departmentList = new ArrayList<>();

        String selectDepartments = "SELECT deptID FROM departments WHERE facultyID = '"+facultyID+"' ORDER BY deptID ASC;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectDepartments);
            while (resultSet.next()) {
                departmentList.add(resultSet.getString("deptID"));
            }
            departmentList = FXCollections.observableArrayList(departmentList);

        } catch (SQLException e) {
            System.out.println("Unable to retrieve departments.");
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(departmentList);
    }

    /**
     * This method is used to retrieve the list of courses associated with a specific department.
     *
     * @param departmentID The ID of the department to retrieve the courses for.
     * @return An ObservableList containing the list of courses associated with the department.
     */
    public ObservableList<String> getDepartmentCourses(String departmentID) {
        List<String> courseList = new ArrayList<>();

        String selectCourses = "SELECT courseID FROM courses WHERE deptID = '"+departmentID+"' ORDER BY courseID ASC ;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectCourses);
            while (resultSet.next()) {
                courseList.add(resultSet.getString("courseID"));
            }
            courseList = FXCollections.observableArrayList(courseList);

        } catch (SQLException e) {
            System.out.println("Unable to retrieve courses.");
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(courseList);
    }
    public ObservableList<String> getAllDepartments() {
        List<String> departmentList = new ArrayList<>();

        String selectDepartments = "SELECT deptID FROM departments ORDER BY deptID ASC ;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectDepartments);
            while (resultSet.next()) {
                departmentList.add(resultSet.getString("deptID"));
            }
            departmentList = FXCollections.observableArrayList(departmentList);

        } catch (SQLException ex) {
            System.out.println("Unable to retrieve courses.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(departmentList);
    }
    public ObservableList<String> getAllDegrees() {
        List<String> degreeList = new ArrayList<>();
        String getDegrees = "SELECT degreeID FROM department_degrees ORDER BY degreeID ASC";

        Statement stmt;
        ResultSet resultSet;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getDegrees);
            while (resultSet.next()) {
                degreeList.add(resultSet.getString("degreeID"));
            }
            degreeList = FXCollections.observableArrayList(degreeList);

        } catch (SQLException ex) {
            System.out.println("Unable to get all degrees");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(degreeList);
    }
    public ObservableList<String> getAllMinors() {
        List<String> minorList = new ArrayList<>();
        String getMinors = "SELECT minorID FROM minors ORDER BY minorID ASC";

        Statement stmt;
        ResultSet resultSet;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getMinors);
            while (resultSet.next()) {
                minorList.add(resultSet.getString("minorID"));
            }
            minorList = FXCollections.observableArrayList(minorList);

        } catch (SQLException ex) {
            System.out.println("Unable to get all minors");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(minorList);
    }
    public ObservableList<String> getFilterByForTeachers() {
        List<String> filterByList = new ArrayList<>();

        String getFilter = "SELECT criteria FROM filter_criteria ORDER BY criteria ASC;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getFilter);
            while (resultSet.next()) {
                filterByList.add(resultSet.getString("criteria"));
            }
            filterByList = FXCollections.observableArrayList(filterByList);

        } catch (SQLException ex) {
            System.out.println("Unable to get filter.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FXCollections.observableArrayList(filterByList);
    }
    public ObservableList<String> generalFilterBy() {
        List<String> filterByList = new ArrayList<>();

        String getFilter = "SELECT criteria FROM filter_criteria LIMIT 2;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getFilter);
            while (resultSet.next()) {
                filterByList.add(resultSet.getString("criteria"));
            }
            filterByList = FXCollections.observableArrayList(filterByList);

        } catch (SQLException ex) {
            System.out.println("Unable to get filter.");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, "Error in FilterBy syntax.", ex);
        }

        return FXCollections.observableArrayList(filterByList);
    }
    public ObservableList<String> getFilterByForStudents() {
        List<String> filterByList = new ArrayList<>();
        String getFilter = "SELECT criteria FROM filter_criteria WHERE marker = 'student' ORDER BY criteria ASC";

        Statement stmt;
        ResultSet resultSet;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getFilter);
            while (resultSet.next()) {
                filterByList.add(resultSet.getString("criteria"));
            }
            filterByList = FXCollections.observableArrayList(filterByList);

        } catch (SQLException ex) {
            System.out.println("Unable to get student filter");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, "Error in filterByStudents syntax.", ex);
        }
        return FXCollections.observableArrayList(filterByList);
    }

    /**
     * Teacher section
     */

    // Update teacher contact information
    public void updateContactInfo(String teacherID, String phoneNumber, String email, String option) {
        String updateContactInfo = "UPDATE teachers SET phoneNum = ?, email = ?, option = ? WHERE teacherID = ?";

        try {
            preparedStatement = conn.prepareStatement(updateContactInfo);

            if(Objects.equals(option, "1")) { // both fields are filled
                preparedStatement.setString(1, phoneNumber);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, teacherID);
                preparedStatement.executeUpdate(updateContactInfo);

            } else if (Objects.equals(option, "2")) { // only phone field is filled
                preparedStatement.setString(1, phoneNumber);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, teacherID);
                preparedStatement.executeUpdate(updateContactInfo);

            } else if (Objects.equals(option, "3")) { // only the email field is filled
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, teacherID);
                preparedStatement.executeUpdate(updateContactInfo);

            }

        } catch (SQLException ex) {
            System.out.println("Unable to update teacher contact info");
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
