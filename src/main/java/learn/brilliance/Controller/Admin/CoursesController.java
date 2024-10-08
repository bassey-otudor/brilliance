package learn.brilliance.Controller.Admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import learn.brilliance.Model.Course;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoursesController implements Initializable {
    private final String tableName = "courses";
    public TextField course_searchField;
    public ComboBox<String> course_filterBy;
    public ComboBox<String> course_filterOptions;
    public TableView<Course> course_tableView;
    public TableColumn<Course, String> course_tableView_col_facultyID;
    public TableColumn<Course, String> course_tableView_col_courseID;
    public TableColumn<Course, String> course_tableView_col_courseName;
    public TableColumn<Course, String> course_tableView_col_level;
    public TableColumn<Course, String> course_tableView_col_dept;
    public TableColumn<Course, String> course_tableView_col_teacher;
    public TableColumn <Course, String> course_tableView_col_teacherID;
    public TableColumn<Course, String> course_tableView_col_creditValue;
    public TextField course_courseName;
    public TextField course_courseID;
    public ComboBox<String> course_creditValue;
    public ComboBox<String> course_level;
    public ComboBox<String> course_dept;
    public ComboBox<String> course_teacher;
    public Button course_deleteBtn;
    public Button course_clearBtn;
    public Button course_updateBtn;
    public Button course_addBtn;
    public ComboBox<String> course_faculty;
    public Label operationStatus;
    public TextField course_teacherID;
    public Button course_resetFilterBtn;
    public ComboBox<String> course_degreeID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        course_filterBy.setItems(Model.getInstance().getConnectDB().generalFilterBy());
        course_filterBy.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> filterOptions(newVal));
        course_faculty.setItems(Model.getInstance().getConnectDB().getFaculties());
        course_faculty.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> course_dept.setItems(Model.getInstance().getConnectDB().getFacultyDepartments(newVal)));

        course_dept.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            course_degreeID.setItems(Model.getInstance().getConnectDB().getDepartmentDegrees(newValue));
            course_teacher.setItems(Model.getInstance().getConnectDB().getCourseTeacher(newValue));
        }));
        course_teacher.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldVal, newVal)
                -> course_teacherID.setText(getTeacherID(newVal))));


        course_level.setItems(Model.getInstance().getConnectDB().getLevel());
        course_creditValue.setItems(Model.getInstance().getConnectDB().getCreditValue());
        course_addBtn.setOnAction(e -> createCourse());
        course_updateBtn.setOnAction(e -> updateCourse());
        course_deleteBtn.setOnAction(e -> deleteCourse());
        course_clearBtn.setOnAction(e -> clearFields());
        course_resetFilterBtn.setOnAction(e -> resetFilter());

        // course tableView section
        initialiseCoursesTable();
        bindCoursesTableData();
        course_tableView.setItems(Model.getInstance().setAllCourses());
        updateCoursesTable();
        course_tableView.setOnMouseClicked(e -> selectCourses());
        searchCourses();
        selectCourses();
        filterCourses();
    }


    private void searchCourses() {
        FilteredList<Course> searchFilter = new FilteredList<>(Model.getInstance().setAllCourses(), e -> true);
        course_searchField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateCourse -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String searchKey = newVal.toLowerCase();

                if(predicateCourse.courseIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCourse.courseNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCourse.courseLevelProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCourse.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCourse.teacherIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCourse.teacherNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateCourse.facultyIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else return predicateCourse.creditValueProperty().toString().toLowerCase().contains(searchKey);
            });

            SortedList<Course> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(course_tableView.comparatorProperty());
            course_tableView.setItems(sortedList);
        });
    }
    private void createCourse() {

        String courseID = course_courseID.getText().toUpperCase();
        String courseName = course_courseName.getText();
        String courseFaculty = course_faculty.getValue();
        String courseLevel = course_level.getValue();
        String departmentID = course_dept.getValue();
        String teacherName = course_teacher.getValue();
        String teacherID = getTeacherID(teacherName);
        String facultyID = course_faculty.getValue();
        String creditValue = course_creditValue.getValue();
        String degreeID = course_degreeID.getValue();
        boolean doesExists = Model.getInstance().getConnectDB().checkData(courseID, tableName);
        String tableName = courseID + "-" + LocalDate.now().getYear();
        String departmentTableName = departmentID + "-" + courseLevel;

        try {
            if(courseID.isEmpty() || courseName.isEmpty() || courseFaculty.isEmpty() || courseLevel.isEmpty() || departmentID.isEmpty() || creditValue.isEmpty() || facultyID.isEmpty()) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (doesExists) {
                    course_courseID.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");
                    course_courseName.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Course already exist..");

                } else {

                    Model.getInstance().getConnectDB().createCourse(courseID, courseName, courseLevel, departmentID, creditValue, teacherID, teacherName, facultyID, degreeID);
                    Model.getInstance().getConnectRecord().createCourseRecordTable(tableName);
                    Model.getInstance().getConnectDepartmentDB().addCourseToDepartmentLevelTable(departmentTableName, courseID, degreeID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully added.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setAllCourses());
                    // Update the teacher table with new info
                    Model.getInstance().teachersController.initialiseTeachersTable();

                    // Clear entries
                    clearFields();
                }
            }
        } catch (Exception ex) {

            System.out.println("Unable to execute create course from controller.");
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void updateCourse() {
        String courseID = course_courseID.getText().toUpperCase();
        String courseName = course_courseName.getText();
        String courseFaculty = course_faculty.getValue();
        String courseLevel = course_level.getValue();
        String departmentID = course_dept.getValue();
        String teacherName = course_teacher.getValue();
        String teacherID = getTeacherID(teacherName);
        String facultyID = course_faculty.getValue();
        String creditValue = course_creditValue.getValue();
        String degreeID = course_degreeID.getValue();
        boolean doesExists = Model.getInstance().getConnectDB().checkData(courseID, tableName);

        try {
            if (courseID.isEmpty() || courseName.isEmpty() || courseFaculty.isEmpty() || courseLevel.isEmpty() || departmentID.isEmpty() || teacherID.isEmpty() || creditValue.isEmpty() ||facultyID.isEmpty()) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (doesExists) {

                    Model.getInstance().getConnectDB().updateCourse(courseID, courseName, courseLevel, departmentID, creditValue,teacherID, teacherName, facultyID, degreeID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully updated.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setAllCourses());
                    // Clear entries
                    clearFields();

                } else {
                    course_courseID.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Course does not exist..");
                }
            }
        } catch (Exception ex) {
            System.out.println("Unable to update course from controller.");
            Logger.getLogger(CoursesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void deleteCourse() {

        String courseID = course_courseID.getText();
        String teacherID = course_teacherID.getText();
        boolean operation = false;
        boolean doesExists = Model.getInstance().getConnectDB().checkData(courseID, tableName);

        try {
            if (courseID.isEmpty() || teacherID.isEmpty()) {
                course_courseID.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                course_teacher.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (doesExists) {
                    Model.getInstance().getConnectDB().insertCoursesInTeacher(teacherID, courseID, operation);
                    Model.getInstance().getConnectDB().deleteCourse(courseID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully deleted.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setAllCourses());

                    // Update the teacher table with new info

                    // Clear entries
                    clearFields();

                } else {
                    System.out.println(courseID);
                    course_courseID.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Course not found.");

                }
            }
        } catch (Exception ex) {
            System.out.println("Unable to delete course from controller.");
            Logger.getLogger(CoursesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String getTeacherID(String teacherName) {

        String teacherID = null;

        if (teacherName != null) {
            String[] names = teacherName.split(" ");

            if (names.length == 2) {

                teacherID = String.valueOf(Model.getInstance().getConnectDB().getTeacherID(names[0], names[1]));
                teacherID = teacherID.substring(1, teacherID.length() - 1);
            } else {
                teacherID = "";
            }
        }
        return teacherID;
    }
    private void updateCoursesTable() {  // Update courses table
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> { // keyFrame with a 4 seconds trigger
            course_tableView.setItems(Model.getInstance().setAllCourses());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // repeats indefinitely
        timeline.playFromStart();
    }
    private void clearFields() {
        course_courseID.setText(null);
        course_courseName.setText(null);
        course_faculty.setValue(null);
        course_level.setValue(null);
        course_dept.setValue(null);
        course_teacher.setValue(null);
        course_teacher.setValue(null);
        course_creditValue.setValue(null);
    }
    public void initialiseCoursesTable() {
        if(Model.getInstance().getAllCourses().isEmpty()) {
            Model.getInstance().setAllCourses();
        }
    }
    private void bindCoursesTableData() {
        course_tableView_col_courseID.setCellValueFactory(cellData -> cellData.getValue().courseIDProperty());
        course_tableView_col_courseName.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
        course_tableView_col_level.setCellValueFactory(cellData -> cellData.getValue().courseLevelProperty());
        course_tableView_col_dept.setCellValueFactory(cellData -> cellData.getValue().departmentIDProperty());
        course_tableView_col_creditValue.setCellValueFactory(cellData -> cellData.getValue().creditValueProperty());
        course_tableView_col_teacher.setCellValueFactory(cellData -> cellData.getValue().teacherNameProperty());
        course_tableView_col_teacherID.setCellValueFactory(cellData -> cellData.getValue().teacherIDProperty());
        course_tableView_col_facultyID.setCellValueFactory(cellData -> cellData.getValue().facultyIDProperty());
    }
    private void selectCourses() {
        Course courses = course_tableView.getSelectionModel().getSelectedItem();
        int num = course_tableView.getSelectionModel().getSelectedIndex();
        if((num -1) < -1) return;
        course_courseID.setText(String.valueOf(courses.courseIDProperty().get()));
        course_courseName.setText(String.valueOf(courses.courseNameProperty().get()));
        course_level.setValue(String.valueOf(courses.courseLevelProperty().get()));
        course_dept.setValue(String.valueOf(courses.departmentIDProperty().get()));
        course_creditValue.setValue(String.valueOf(courses.creditValueProperty().get()));
        course_teacher.setValue(String.valueOf(courses.teacherNameProperty().get()));
        course_teacherID.setText(String.valueOf(courses.teacherIDProperty().get()));
        course_faculty.setValue(String.valueOf(courses.facultyIDProperty().get()));
    }
    private void filterCourses() {
        FilteredList<Course> searchFilter = new FilteredList<>(Model.getInstance().setAllCourses(), e -> true);
        course_filterOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateCourse -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String filterKey = newVal.toLowerCase();
                if(predicateCourse.facultyIDProperty().toString().toLowerCase().contains(filterKey)) {
                    return true;
                } else return predicateCourse.departmentIDProperty().toString().toLowerCase().contains(filterKey);
            });

            SortedList<Course> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(course_tableView.comparatorProperty());
            course_tableView.setItems(sortedList);
        });
    }
    private void filterOptions(String val) {
        switch (val){
            case "Faculty" -> course_filterOptions.setItems(Model.getInstance().getConnectDB().getFaculties());
            case "Department" -> course_filterOptions.setItems(Model.getInstance().getConnectDB().getAllDepartments());
        }
    }
    private void resetFilter() {
        course_filterBy.setValue("");
        course_filterOptions.setValue("");
    }
}
