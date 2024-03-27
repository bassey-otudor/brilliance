package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Course;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {

    private final String idColumn = "courseID";
    private final String tableName = "courses";
    private final String columnName = "courseName";
    public TextField course_searchField;
    public ComboBox<String> course_filterFaculty;
    public ComboBox<String> course_filterDept;
    public ComboBox<String> course_filterLevel;
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
    public ComboBox<String> course_firstSecond;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        course_faculty.setItems(Model.getInstance().getConnectDB().getFaculties());
        course_faculty.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> course_dept.setItems(Model.getInstance().getConnectDB().getFacultyDepartments(newVal)));

        course_dept.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldVal, newVal)
                -> course_teacher.setItems(Model.getInstance().getConnectDB().getCourseTeacher(newVal))));
        course_teacher.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldVal, newVal)
                -> course_teacherID.setText(getTeacherID(newVal))));

        course_firstSecond.setItems(Model.getInstance().getConnectDB().getCoursePosition());
        course_teacher.valueProperty().addListener(((observableValue, oldVal, newVal)
                -> course_firstSecond.setDisable(Boolean.parseBoolean(newVal))
        ));

        course_level.setItems(Model.getInstance().getConnectDB().getLevel());
        course_creditValue.setItems(Model.getInstance().getConnectDB().getCreditValue());
        course_addBtn.setOnAction(e -> createCourse());
        course_updateBtn.setOnAction(e -> updateCourse());
        course_deleteBtn.setOnAction(e -> deleteCourse());
        course_clearBtn.setOnAction(e -> clearFields());

        // course tableView section
        initialiseCoursesTable();
        bindCoursesTableData();
        course_tableView.setItems(Model.getInstance().setCourses());
        course_tableView.setOnMouseClicked(e -> selectCourses());
        searchCourses();
    }


    private void searchCourses() {
        FilteredList<Course> searchFilter = new FilteredList<>(Model.getInstance().setCourses(), e -> true);
        course_searchField.textProperty().addListener(((observableValue, oldVal, newVal) -> {
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
                } else if(predicateCourse.facultyIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

            SortedList<Course> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(course_tableView.comparatorProperty());
            course_tableView.setItems(sortedList);
        }));
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
        boolean operation = true;
        boolean doesExists = Model.getInstance().getConnectDB().checkData(tableName, idColumn, courseID, columnName, courseName);
        String coursePosition = course_firstSecond.getValue();

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

                    if (coursePosition.length() > 0){
                        Model.getInstance().getConnectDB().insertCoursesInTeacher(coursePosition, teacherID, courseID, operation);
                    }

                    Model.getInstance().getConnectDB().createCourse(courseID, courseName, courseLevel, departmentID, creditValue, teacherID, teacherName, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully added.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setCourses());
                    // Update the teacher table with new info
                    // teacher_tableView.setItems(Model.getInstance().setTeachers());
                    // Clear entries
                    clearFields();
                }
            }
        } catch (Exception e) {

            System.out.println("Unable to execute create course from controller.");
            e.printStackTrace();
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
        String coursePosition = course_firstSecond.getValue();
        boolean operation = true;
        boolean doesExists = Model.getInstance().getConnectDB().checkData(tableName, idColumn, courseID, columnName, courseName);

        try {
            if (courseID.isEmpty() || courseName.isEmpty() || courseFaculty.isEmpty() || courseLevel.isEmpty() || departmentID.isEmpty() || teacherID.isEmpty() || creditValue.isEmpty() ||facultyID.isEmpty()) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (doesExists) {

                    if (coursePosition.length() > 0) {
                        Model.getInstance().getConnectDB().insertCoursesInTeacher(coursePosition, teacherID, courseID, operation);

                    }

                    Model.getInstance().getConnectDB().updateCourse(courseID, courseName, courseLevel, departmentID, creditValue,teacherID, teacherName, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully updated.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setCourses());
                    // Clear entries
                    clearFields();

                } else {
                    course_courseID.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Course does not exist..");
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to update course from controller.");
            e.printStackTrace();
        }
    }
    private void deleteCourse() {

        String courseID = course_courseID.getText().toUpperCase();
        String courseName = course_courseName.getText();
        String teacherID = course_teacherID.getText();
        String coursePosition = course_firstSecond.getValue();
        boolean operation = false;
        boolean doesExists = Model.getInstance().getConnectDB().checkData(tableName, idColumn,courseID, columnName, courseName);

        try {
            if (courseID.isEmpty() || teacherID.isEmpty() || coursePosition.length() == 0 ) {
                course_courseID.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                course_teacher.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                course_firstSecond.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (!doesExists) {
                    course_courseID.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Course not found.");

                } else {
                    Model.getInstance().getConnectDB().insertCoursesInTeacher(coursePosition, teacherID, courseID, operation);
                    Model.getInstance().getConnectDB().deleteCourse(courseID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully deleted.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setCourses());

                    // Clear entries
                    clearFields();
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to delete course from controller.");
            e.printStackTrace();
        }
    }

    private String getTeacherID(String teacherName) {

        String teacherID = null;

        if (teacherName == null) {
            // Do nothing
        } else {
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

    private void clearFields() {
        course_courseID.setText("");
        course_courseName.setText("");
        course_faculty.setValue(null);
        course_level.setValue(null);
        course_dept.setValue(null);
        course_teacher.setValue(null);
        course_creditValue.setValue(null);
        course_firstSecond.setDisable(true);
    }
    private void initialiseCoursesTable() {
        if(Model.getInstance().getCourses().isEmpty()) {
            Model.getInstance().setCourses();
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

}
