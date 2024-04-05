package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class connectDB {
    private Connection conn;
    private static final String connURL = "jdbc:sqlite:school_database_structure.db";
    private static final String username = "root";
    private static final String password = "";
    public connectDB() {
        try {
            this.conn = DriverManager.getConnection(connURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the admins' information
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
            String getDirectors = "SELECT CONCAT(fname, ' ', lName) AS facultyDirector FROM teachers WHERE position = 'Director'";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getDirectors);

            while (resultSet.next()) {
                directorList.add(resultSet.getString("facultyDirector"));
            }

            directorList = FXCollections.observableArrayList(directorList);

        } catch (SQLException e) {
            System.out.println("Error retrieving ");
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(directorList);
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
    public void updateFaculty(String facultyID,String facultyName, String director) {
        try {
            Statement stmt = conn.createStatement();
            String update = "UPDATE faculties SET facultyID = '"+facultyID+"', facultyName ='"+facultyName+"', Director ='"+director+"' WHERE facultyID ='"+facultyID+"';";
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

    public void deleteFacultyDepartments(String facultyID) {

        try{
            Statement stmt;
            stmt = this.conn.createStatement();
            stmt.executeUpdate("DELETE FROM departments WHERE facultyID = '"+facultyID+"';");

        } catch (SQLException e) {
            System.out.println("Unable to delete departments in faculty.");
            e.printStackTrace();
        }
    }

    /**
     * Department section
     */
    public ObservableList<String> getHod(String facultyID) {

        List<String> hodList = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT CONCAT(fname, ' ', lName) AS departmentHOD FROM teachers WHERE position = 'HOD' AND facultyID ='"+facultyID+"'";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                hodList.add(resultSet.getString("departmentHOD"));
            }

            hodList = FXCollections.observableArrayList(hodList);

        } catch (SQLException e) {
            System.out.println("Error retrieving HODs");
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(hodList);
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

        return FXCollections.observableArrayList(faculityList);
    }
    public ObservableList<String> getMinor(String facultyID) {
        List<String> minorList = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT * FROM minors WHERE facultyID = '"+facultyID+"';";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                minorList.add(resultSet.getString("minorName"));
            }
            minorList = FXCollections.observableArrayList(minorList);

        } catch (SQLException e) {
            System.out.println("Unable to get list of minor1.");
            e.printStackTrace();
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
        } catch (SQLException e) {
            System.out.println("Unable to retrieve faculty data.");
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet checkEmptyDepartmentsColumns(String facultyID) {
        String checkDepartments = "SELECT * FROM faculties WHERE facultyID='"+facultyID+"';";
        Statement stmt;
        ResultSet resultSet = null;
        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(checkDepartments);
        } catch (SQLException e) {
            System.out.println("Unable to get departments in the faculty.");
            e.printStackTrace();
        }

        return resultSet;
    }
    public void createDepartment(String departmentID, String departmentName, String facultyID, String hod, String minor1, String minor2) {
        Statement stmt;
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO departments "
                    + "(deptID, deptName, facultyID, hod, minor1, minor2)"
                    + "VALUES ('"+ departmentID +"', '"+ departmentName +"', '"+facultyID+"', '"+hod+"', '"+minor1+"', '"+minor2+"');");

        } catch (SQLException e) {
            System.out.println("Unable to create department");
            e.printStackTrace();
        }
    }
    public void updateDepartment(String departmentID, String departmentName, String facultyID, String hod, String minor1, String minor2) {
        try {

            String update = "UPDATE departments SET deptID = '"+departmentID+"', deptName ='"+departmentName+"', facultyID ='"+facultyID+"', hod='"+hod+"',minor1='"+minor1+"', minor2='"+minor2+"' WHERE deptID ='"+departmentID+"';";
            Statement stmt = conn.createStatement();
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
    public void insertDepartmentInFaculty(String column, String departmentName, String facultyID) {
        String insertDepartment = "UPDATE faculties SET "+column+" = '"+departmentName+"' WHERE facultyID ='"+facultyID+"';";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertDepartment);
        } catch (SQLException e) {
            System.out.println("Unable to add department to the designated faculty.");
            e.printStackTrace();
        }
    }

    /**
     * Teacher section
     */
    public ResultSet getTeacherData() {
        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM teachers;");
        } catch (SQLException e) {
            System.out.println("Unable to retrieve teacher data.");
            e.printStackTrace();
        }

        return resultSet;
    }

    public ObservableList<String> getPosition() {
        List<String> positionList = new ArrayList<>();

        String selectPositions = "SELECT position FROM position;";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectPositions);
            while (resultSet.next()) {
                positionList.add(resultSet.getString("position"));

            }

            positionList = FXCollections.observableArrayList(positionList);
        } catch (SQLException e) {
            System.out.println("Unable to retrieve positions.");
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(positionList);
    }
    public void createTeacher(String teacherID, String firstName, String lastName, String gender, String phoneNumber, String email, String departmentID, LocalDate dob, String password, String course1, String course2, String position, String facultyID) {
        String createTeacher = "INSERT INTO teachers " +
                "(teacherID, fName, lName, gender, phoneNum, email, deptID, dob, password, course1, course2, position, facultyID)" +
                "VALUES ('"+teacherID+"', '"+firstName+"', '"+lastName+"', '"+gender+"', '"+phoneNumber+"', '"+email+"', '"+departmentID+"', '"+dob+"', '"+password+"', '"+course1+"', '"+course2+"', '"+position+"', '"+facultyID+"');";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createTeacher);
        } catch (SQLException e) {
            System.out.println("Unable to add teacher to the database.");
            e.printStackTrace();
        }
    }
    public void updateTeacher(String teacherID, String firstName, String lastName, String gender, String phoneNumber, String email, String departmentID, LocalDate dob, String course1, String course2, String position, String facultyID) {
        String updateTeacher= "UPDATE teachers SET fName = '"+firstName+"', lName = '"+lastName+"', gender = '"+gender+"', phoneNum = '"+phoneNumber+"', email = '"+email+"', deptID = '"+departmentID+"', dob = '"+dob+"', course1= '"+course1+"', course2 = '"+course2+"', position = '"+position+"', facultyID = '"+facultyID+"' WHERE teacherID ='"+teacherID+"';";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(updateTeacher);
        } catch (SQLException e) {
            System.out.println("Unable to update teacher info.");
            e.printStackTrace();
        }
    }
    public void deleteTeacher(String teacherID) {
        String deleteTeacher = "DELETE FROM teachers WHERE teacherID = '"+teacherID+"';";

        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(deleteTeacher);

        } catch (SQLException e) {
            System.out.println("Unable to delete teacher.");
            e.printStackTrace();
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
        } catch (SQLException e) {
            System.out.println("Unable to get row count.");
            e.printStackTrace();
        }

        return rowCount;
    }
    public void insertTeacherInCourse (String teacherID, String teacherName, String courseID, boolean operation) {
        Statement stmt;

        if(operation) {
            try{
                String insertTeacher = "UPDATE courses SET teacherID = '"+teacherID+"', teacherName = '"+teacherName+"' WHERE courseID = '"+courseID+"';";
                stmt = conn.createStatement();
                stmt.executeUpdate(insertTeacher);

            } catch (SQLException e) {
                System.out.println("Unable to assign course to teacher.");
                e.printStackTrace();
            }

        } else {

            try{

                String setNull = "NULL";
                String deleteTeacher = "UPDATE courses SET teacherID = '"+ setNull +"', teacherName = '"+setNull+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(deleteTeacher);

            } catch (SQLException e) {
                System.out.println("Unable to delete course from teacher.");
                e.printStackTrace();
            }
        }


    }

    // Courses
    public ResultSet getCourseData() {
        String getCourseData = "SELECT * FROM courses;";

        Statement stmt;
        ResultSet resultSet = null;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getCourseData);

        } catch (SQLException e) {
            System.out.println("Unable to retrieve course data.");
            e.printStackTrace();
        }

        return resultSet;
    }
    public ObservableList<String> getCourseTeacher(String departmentID) {
        List<String> teacherList = new ArrayList<>();

        String selectTeacherNames = "SELECT CONCAT(fname, ' ', lName) AS teacherFullNames FROM teachers WHERE deptID = '"+departmentID+"'";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectTeacherNames);

            while (resultSet.next()) {
               teacherList.add(resultSet.getString("teacherFullNames"));
            }
            teacherList = FXCollections.observableArrayList(teacherList);

        } catch (SQLException e) {
            System.out.println("Unable to retrieve list of filtered list of teachers.");
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(teacherList);
    }

    public ObservableList<String> getTeacherID(String firstName, String lastName) {
        List<String> teacherID = new ArrayList<>();

        String selectTeacherID = "SELECT teacherID FROM teachers WHERE fName = '"+firstName+"' AND lName = '"+lastName+"'";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectTeacherID);

            while (resultSet.next()) {
                teacherID.add(resultSet.getString("teacherID"));
            }
            teacherID = FXCollections.observableArrayList(teacherID);

        } catch (SQLException e) {
            System.out.println("Unable to retrieve list of filtered list of teachers.");
            e.printStackTrace();
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

        } catch (SQLException e) {
            System.out.println("Unable to retrieve list of credit values.");
            e.printStackTrace();
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

        } catch (SQLException e) {
            System.out.println("Unable to retrieve list of course positions.");
            e.printStackTrace();
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
                "VALUES ('"+courseID+"', '"+courseName+"', '"+courseLevel+"', '"+departmentID+"', '"+creditValue+"','"+teacherID+"', '"+teacherName+"', '"+facultyID+"');";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createCourse);

        } catch (SQLException e) {
            System.out.println("Unable to create teacher.");
            e.printStackTrace();
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
        String updateCourse = "UPDATE courses SET courseID = '"+courseID+"', courseName = '"+courseName+"', courseLevel = '"+courseLevel+"', deptID = '"+departmentID+"', creditValue ='"+creditValue+"',teacherID = '"+teacherID+"', teacherName = '"+teacherName+"', facultyID = '"+facultyID+"' WHERE courseID = '"+courseID+"'";

        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(updateCourse);

        } catch (SQLException e) {
            System.out.println("Unable to update course.");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to delete a course from the database.
     *
     * @param courseID The ID of the course to delete.
     */
    public void deleteCourse(String courseID) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM courses WHERE courseID = '"+courseID+"'");

        } catch (SQLException e) {
            System.out.println("Unable to delete course.");
            e.printStackTrace();
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

            String insertCourse = "UPDATE teachers SET " + columnName + " = '" + courseID + "' WHERE teacherID = '" + teacherID + "';";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertCourse);

        } catch (SQLException e) {
            System.out.println("Unable to assign courses to the designated teacher.");
            e.printStackTrace();
        }

    } else {

        try {

            String deleteCourse = "UPDATE teachers SET  " + columnName + "  = null WHERE teacherID = '" + teacherID + "';";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(deleteCourse);

        } catch (SQLException e) {
            System.out.println("Unable to delete courses from the designated teacher.");
            e.printStackTrace();
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
        String checkCourses = "SELECT teacherID, course1, course2 FROM teachers WHERE teacherID ='"+teacherID+"';";
        Statement stmt;
        ResultSet resultSet = null;
        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(checkCourses);

        } catch (SQLException e) {
            System.out.println("Unable to get verify assigned courses.");
            e.printStackTrace();
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

        } catch (SQLException e) {
            System.out.println("Unable to retrieve degree data.");
            e.printStackTrace();
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

        } catch (SQLException e) {
            System.out.println("Unable to get duration list.");
            e.printStackTrace();
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

        String selectCourses = "SELECT * FROM minors WHERE departmentID = '"+departmentID+"';";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectCourses);
            while (resultSet.next()) {
                minorList.add(resultSet.getString("minorID"));
            }
            minorList = FXCollections.observableArrayList(minorList);

        } catch (SQLException e) {
            System.out.println("Unable to retrieve courses.");
            e.printStackTrace();
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
                "VALUES ('"+degreeID+"', '"+degreeName+"', '"+departmentID+"', '"+minor+"', '"+duration+"', '"+numberOfCourses+"', '"+totalCredits+"', '"+requiredCredits+"', '"+facultyID+"')";

        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createDegree);

        } catch (SQLException e) {
            System.out.println("Unable to create degree");
            e.printStackTrace();
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
                "degreeName = '"+degreeName+"', " +
                "departmentID = '"+departmentID+"', minor = '"+minor+"' ,duration = '"+duration+"', " +
                "numberOfCourses = '"+numberOfCourses+"', totalCredits = '"+totalCredits+"', " +
                "requiredCredits = '"+requiredCredits+"', facultyID = '"+facultyID+"' WHERE degreeID ='"+degreeID+"';";

        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(updateCourse);

        } catch (SQLException e) {
            System.out.println("Unable to update course.");
            e.printStackTrace();
        }
    }

    /**
     * Deletes a degree from the database.
     *
     * @param degreeID The unique ID of the degree to delete.
     */
    public void deleteDegree(String degreeID) {
        String deleteDegree = "DELETE from department_degrees WHERE degreeID = '"+degreeID+"';";

        Statement stmt;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(deleteDegree);

        } catch (SQLException e) {
            System.out.println("Unable to delete course.");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to retrieve the number of rows in the department_degrees table.
     *
     * @param departmentID The ID of the department to retrieve the number of degrees for.
     * @return The number of rows in the department_degrees table for the specified department.
     */
    public int getDegreeRowCount(String departmentID) {
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

        } catch (SQLException e) {
            System.out.println("Unable to get degree row count.");
            e.printStackTrace();
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

        } catch (SQLException e) {
            System.out.println("Unable to get minors.");
            e.printStackTrace();
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

        } catch (SQLException e) {
            System.out.println("Unable to get minor list");
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(minorNumberList);
    }

    public ObservableList<String> getDepartmentDegrees(String departmentID) {
        List<String> degreeList = new ArrayList<>();
        String getDegree = "SELECT * FROM department_degrees WHERE departmentID = '"+departmentID+"';";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(getDegree);
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

        } catch (SQLException e) {
            System.out.println("Unable to retrieve course number list.");
            e.printStackTrace();
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
                "VALUES ('"+minorID+"', '"+minorName+"', '"+degreeID+"', '"+facultyID+"', '"+departmentID+"', '"+courseID+"')";

        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createMinor);

        } catch (SQLException e) {
            System.out.println("Unable to create minor");
            e.printStackTrace();
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

        } catch (SQLException e) {
            System.out.println("Unable to get all student data.");
            e.printStackTrace();
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
            stmt.executeQuery(createStudent);

        } catch (SQLException e) {
            System.out.println("Unable to create student.");
            e.printStackTrace();
        }
    }
    public void updateStudent(String studentID, String firstName, String lastName, String gender, String dob, String phoneNumber, String email, String password, String facultyID, String departmentID, String degreeID, String minorID, String level) {
        String updateStudent = "UPDATE students SET firstName = '"+firstName+"', lastName = '"+lastName+"', gender = '"+gender+"', dob = '"+dob+"', phoneNumber = '"+phoneNumber+"', email = '"+email+"', password = '"+password+"', facultyID = '"+facultyID+"', departmentID = '"+departmentID+"', degreeID = '"+degreeID+"', minorID = '"+minorID+"', level = '"+level+"' WHERE studentID = '"+studentID+"';";
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

        } catch (SQLException e) {
            System.out.println("Unable to retrieve gender data.");
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(genderList);
    }
    public ObservableList<String> getLevel() {
        List<String> levelList = new ArrayList<>();

        String selectLevels = "SELECT * FROM levels";
        Statement stmt;
        ResultSet resultSet;

        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectLevels);

            while (resultSet.next()) {
                levelList.add(resultSet.getString("level"));
            }

        } catch (SQLException e) {
            System.out.println("Unable to get levels");
            e.printStackTrace();
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

        String selectDepartments = "SELECT deptID FROM departments WHERE facultyID = '"+facultyID+"';";
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

        String selectCourses = "SELECT courseID FROM courses WHERE deptID = '"+departmentID+"';";
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


}
