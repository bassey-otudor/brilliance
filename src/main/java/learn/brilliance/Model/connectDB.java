package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class connectDB {
    private Connection conn;
    //private static final String connURL = "jdbc:sqlite:school_db_structure.db";
    private static final String connURL = "jdbc:sqlite:final.db";
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
    public ObservableList<String> getMinor1(String facultyID) {
        List<String> minor1List = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT minorID FROM minors WHERE facultyID = '"+facultyID+"';";
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
    public ObservableList<String> getMinor2(String facultyID) {
        List<String> minor2List = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;

        try {
            String sql = "SELECT minorID FROM minors WHERE facultyID = '"+facultyID+"'";
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
        assert positionList instanceof ObservableList<String>;
        return (ObservableList<String>) positionList;
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
            resultSet = stmt.executeQuery("SELECT COUNT(teacherID) AS id_count FROM teachers;");
            if(resultSet.next()) {
                rowCount = resultSet.getInt("id_count");
            }
        } catch (SQLException e) {
            System.out.println("Unable to get row count.");
            e.printStackTrace();
        }

        return rowCount;
    }
    public void insertTeacherInCourse (String teacherID, String courseID, boolean operation) {
        Statement stmt;

        if(operation) {
            try{
                String insertTeacher = "UPDATE courses SET teacherID = '"+teacherID+"';";
                stmt = conn.createStatement();
                stmt.executeUpdate(insertTeacher);

            } catch (SQLException e) {
                System.out.println("Unable to assign course to teacher.");
                e.printStackTrace();
            }

        } else {

            try{

                String setNull = "NULL";
                String deleteTeacher = "UPDATE courses SET teacherID = '"+ setNull +"' WHERE courseID = '"+courseID+"'";
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

        // String selectTeachersID = "SELECT * FROM teachers WHERE deptID = '"+departmentID+"';";

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

        assert teacherList instanceof ObservableList<String>;
        return (ObservableList<String>) teacherList;
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
    public void createCourse(String courseID, String courseName, String courseLevel, String departmentID, String teacherID, String facultyID) {

        String createCourse = "INSERT INTO Courses " +
                "(courseID, courseName, courseLevel, deptID, teacherID, facultyID)" +
                "VALUES ('"+courseID+"', '"+courseName+"', '"+courseLevel+"', '"+departmentID+"', '"+teacherID+"', '"+facultyID+"');";
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
    public void updateCourse(String courseID, String courseName, String courseLevel, String departmentID, String teacherID, String facultyID) {
        String updateCourse = "UPDATE courses SET courseID = '"+courseID+"', courseName = '"+courseName+"', courseLevel = '"+courseLevel+"', deptID = '"+departmentID+"', teacherID = '"+teacherID+"', facultyID = '"+facultyID+"' WHERE courseID = '"+courseID+"'";

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

        String setNull = "NULL";

        try {
            String deleteCourse = "UPDATE teachers SET " + columnName + " = '" + setNull + "' WHERE teacherID = '" + teacherID + "';";
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
        String checkCourses = "SELECT course1, course2 FROM teachers WHERE teacherID ='"+teacherID+"';";
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

        assert genderList instanceof ObservableList<String>;
        return (ObservableList<String>) genderList;
    }
    public ObservableList<String> getLevel() {
        final String [] levels = {"1", "2", "3", "4"};
        List<String > levelList = new ArrayList<>();
        Collections.addAll(levelList, levels);

        levelList = FXCollections.observableArrayList(levelList);
        return (ObservableList<String>) levelList;
    }
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

        assert departmentList instanceof ObservableList<String>;
        return (ObservableList<String>) departmentList;
    }
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

        assert courseList instanceof ObservableList<String>;
        return (ObservableList<String>) courseList;
    }


}
