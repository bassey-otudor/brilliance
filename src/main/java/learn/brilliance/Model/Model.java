package learn.brilliance.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import learn.brilliance.Controller.Admin.TeachersController;
import learn.brilliance.Model.Accounts.Teacher;
import learn.brilliance.Model.Accounts.Student;
import learn.brilliance.View.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    connectDB connectDB;
    connectRecord connectRecord;
    connectTimetable connectTimetable;
    connectDepartmentDB connectDepartmentDB;

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
    private final ObservableList<CourseRecord> courseRecords;
    private final ObservableList<CourseRecord> overviewCourseRecords;
    private final ObservableList<CourseRecord> courseOverall;
    private final ObservableList<Timetable> todayTimetables;
    private final ObservableList<Timetable> tomorrowTimetables;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.connectDB = new connectDB();
        this.connectRecord = new connectRecord();
        this.connectTimetable = new connectTimetable();
        this.connectDepartmentDB = new connectDepartmentDB();

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
        this.teacher = new Teacher("", "", "", null, null, null, null, null , null, null, null, null);
        this.courseRecords = FXCollections.observableArrayList();
        this.overviewCourseRecords = FXCollections.observableArrayList();
        this.courseOverall = FXCollections.observableArrayList();
        this.todayTimetables = FXCollections.observableArrayList();
        this.tomorrowTimetables = FXCollections.observableArrayList();
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
    public connectRecord getConnectRecord() { return connectRecord; }
    public connectTimetable getConnectTimetable() { return connectTimetable; }
    public connectDepartmentDB getConnectDepartmentDB() { return connectDepartmentDB; }


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
                this.teacher.courseProperty().set(resultSet.getString("course"));
                this.teacher.positionProperty().set(resultSet.getString("position"));

                this.teacherLoginStatus = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Course page
    public ObservableList<CourseRecord> getAllCourseRecords() { return courseRecords; }


    public ObservableList<CourseRecord> setAllCourseRecords(String year) {
        String tableName = Model.getInstance().getTeacher().courseProperty().get() + "-" + year;

        ResultSet resultSet = connectRecord.getCourseRecordData(tableName);
        ObservableList<CourseRecord> courseRecordList = FXCollections.observableArrayList();
        CourseRecord courseRecordData;

        try {
            while(resultSet.next()) {
                courseRecordData = new CourseRecord(
                        resultSet.getString("studentID"),
                        resultSet.getString("studentName"),
                        resultSet.getString("firstCA"),
                        resultSet.getString("secondCA"),
                        resultSet.getString("exam"),
                        resultSet.getString("total"),
                        resultSet.getString("grade"),
                        resultSet.getString("status")

                );
                courseRecordList.add(courseRecordData);
            }

        } catch (SQLException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courseRecordList;
    }

    // Overview page

    /**
     * This method prepares the overview page's course record data.
     * It retrieves the best course record data for the current year based on the teacher's course.
     * The data is then added to the provided ObservableList of CourseRecord objects.
     *
     * @param courseRecord The ObservableList of CourseRecord objects where the course record data will be added.
     * @throws SQLException if there is an error while executing the SQL query.
     */
    public void prepareCourseRecord(ObservableList<CourseRecord> courseRecord) {
        String tableName = Model.getInstance().getTeacher().courseProperty().get() + "-" + LocalDate.now().getYear();
        ResultSet resultSet = getConnectRecord().getBestCourseRecordData(tableName);

        try {
            while (resultSet.next()) {
                String studentID = resultSet.getString("studentID");
                String studentName = resultSet.getString("studentName");
                String firstCA = resultSet.getString("firstCA");
                String secondCA = resultSet.getString("secondCA");
                String exam = resultSet.getString("exam");
                String total = resultSet.getString("total");
                String grade = resultSet.getString("grade");
                String status = resultSet.getString("status");

                courseRecord.add(new CourseRecord(studentID, studentName, firstCA, secondCA, exam, total, grade, status));
            }

        } catch (SQLException ex) {
            Logger.getLogger(learn.brilliance.Model.connectRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setOverviewCourseRecords() {
        prepareCourseRecord(overviewCourseRecords);
    }
    public ObservableList<CourseRecord> getOverviewCourseRecords() {
        return overviewCourseRecords;
    }

    // Timetable methods

    /**
     * This method prepares the today's timetable for the given teacher.
     * It retrieves the timetable data for the current day based on the teacher's ID and adds it to the provided ObservableList of Timetable objects.
     *
     * @param timetable The ObservableList of Timetable objects where the today's timetable will be added.
     * @throws SQLException if there is an error while executing the SQL query.
     */
    public void prepareTodayTimetable(ObservableList<Timetable> timetable) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(day);
        String teacherID = Model.getInstance().getTeacher().teacherIDProperty().get();
        ResultSet resultSet = connectTimetable.getTimetableData(day, teacherID);

        try {
            while(resultSet.next()) {
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                String courseID = resultSet.getString("courseID");
                String level = resultSet.getString("level");
                String location = resultSet.getString("location");

                timetable.add(new Timetable(startTime, endTime, courseID, teacherID, level, location));
            }

        } catch (SQLException ex) {
            Logger.getLogger(learn.brilliance.Model.connectRecord.class.getName()).log(Level.SEVERE, "Unable to get today's timetable.", ex);
        }
    }

    /**
     * This method prepares the tomorrow's timetable for the given teacher.
     * It retrieves the timetable data for the next day based on the teacher's ID and adds it to the provided ObservableList of Timetable objects.
     *
     * @param timetable The ObservableList of Timetable objects where the tomorrow's timetable will be added.
     * @throws SQLException if there is an error while executing the SQL query.
     */
    public void prepareTomorrowTimetable(ObservableList<Timetable> timetable) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) + 1;
        String teacherID = Model.getInstance().getTeacher().teacherIDProperty().get();
        ResultSet resultSet = connectTimetable.getTimetableData(day, teacherID);

        try {
            while(resultSet.next()) {
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                String courseID = resultSet.getString("courseID");
                String level = resultSet.getString("level");
                String location = resultSet.getString("location");

                timetable.add(new Timetable(startTime, endTime, courseID, teacherID, level, location));
            }

        } catch (SQLException ex) {
            Logger.getLogger(learn.brilliance.Model.connectRecord.class.getName()).log(Level.SEVERE, "Unable to get tomorrow's timetable.", ex);
        }
    }

    public void setTodayTimetable() {
        prepareTodayTimetable(todayTimetables);
    }
    public void setTomorrowTimetable() { prepareTomorrowTimetable(tomorrowTimetables); }

    public ObservableList<Timetable> getAllTodayTimetables() {
        return todayTimetables;
    }
    public ObservableList<Timetable> getAllTomorrowTimetables() {
        return tomorrowTimetables;
    }


    // Binding data section
    // Faculties
    public ObservableList<Faculty> getAllFaculties() {
        return faculties;
    }

    /**
     * This method sets all faculty data into an ObservableList of faculties.
     * It uses the ResultSet returned by the getFacultyData() method.
     * The attributes of every faculty are gotten and stored in an ObservableList of faculties.
     * This method is called and the list used to initialise the facultyView table.
     *
     * @return ObservableList of faculties
     * @throws SQLException if there is an error while executing the SQL query
     */
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
     * This method sets all department data into an ObservableList of departments.
     * It uses the ResultSet returned by the getDepartmentData() method.
     * The attributes of every department are gotten and stored in an ObservableList of departments.
     * This method is called and the list used to initialise the departmentView table.
     *
     * @return ObservableList of departments
     * @throws SQLException if there is an error while executing the SQL query
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
    /**
     * This method sets all teacher data into an ObservableList of teachers.
     * It uses the ResultSet returned by the getTeacherData() method.
     * The attributes of every teacher are gotten and stored in an ObservableList of teachers.
     * This method is called and the list used to initialise the teacherView table.
     *
     * @return ObservableList of teachers
     * @throws SQLException if there is an error while executing the SQL query
     */
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
                        resultSet.getString("course"),
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

    /**
     * This method sets all course data into an ObservableList of courses.
     * It uses the ResultSet returned by the getCourseData() method.
     * The attributes of every course are gotten and stored in an ObservableList of courses.
     * This method is called and the list used to initialise the courseView table.
     *
     * @return ObservableList of courses
     * @throws SQLException if there is an error while executing the SQL query
     */
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
                        resultSet.getString("creditValue"),
                        resultSet.getString("degreeID")
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

    /**
     * This method sets all degree data into an ObservableList of degrees.
     * It uses the ResultSet returned by the getDegreeData() method.
     * The attributes of every degree are gotten and stored in an ObservableList of degrees.
     * This method is called and the list used to initialise the degreeView table.
     *
     * @return ObservableList of degrees
     * @throws SQLException if there is an error while executing the SQL query
     */
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

    /**
     * This method sets all minor data into an ObservableList of minors.
     * It uses the ResultSet returned by the getMinorData() method.
     * The attributes of every minor are gotten and stored in an ObservableList of minors.
     * This method is called and the list used to initialise the minorView table.
     *
     * @return ObservableList of minors
     * @throws SQLException if there is an error while executing the SQL query
     */
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

    // Students


    public ObservableList<Student> getAllStudents() { return students; }
    /**
     * This method sets all students data into an ObservableList of students.
     * It uses the ResultSet returned by the getStudentData() method.
     * The attributes of every student are gotten and stored in an ObservableList of students.
     * This method is called and the list used to initialise the studentView table.
     *
     * @return ObservableList of students
     * @throws SQLException if there is an error while executing the SQL query
     */
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
