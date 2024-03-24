package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Course;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.Teacher;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {

    private final String idColumn = "courseID";
    private final String tableName = "courses";
    private final String nameColumn = "courseName";
    public TextField course_searchField;
    public ChoiceBox<String> course_filterFaculty;
    public ChoiceBox<String> course_filterDept;
    public ChoiceBox<String> course_filterLevel;
    public TableView<Course> course_tableView;
    public TableColumn<Course, String> course_tableView_col_facultyID;
    public TableView<Teacher> teacher_tableView;
    public TableColumn<Course, String> course_tableView_col_courseID;
    public TableColumn<Course, String> course_tableView_col_courseName;
    public TableColumn<Course, String> course_tableView_col_level;
    public TableColumn<Course, String> course_tableView_col_dept;
    public TableColumn<Course, String> course_tableView_col_teacher;
    public TextField course_courseName;
    public TextField course_courseID;
    public ChoiceBox<String> course_level;
    public ChoiceBox<String> course_dept;
    public ChoiceBox<String> course_teacher;
    public Button course_deleteBtn;
    public Button course_clearBtn;
    public Button course_updateBtn;
    public Button course_addBtn;
    public ChoiceBox<String> course_faculty;
    public Label operationStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        course_faculty.setItems(Model.getInstance().getConnectDB().getFaculties());
        course_faculty.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> course_dept.setItems(Model.getInstance().getConnectDB().getFacultyDepartments(newVal)));

        course_dept.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldVal, newVal)
                -> course_teacher.setItems(Model.getInstance().getConnectDB().getCourseTeacher(newVal))));

        course_level.setItems(Model.getInstance().getConnectDB().getLevel());

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
        String teacherID = course_teacher.getValue();
        String facultyID = course_faculty.getValue();
        boolean operation = true;
        boolean doesExists = Model.getInstance().getConnectDB().checkData(tableName, idColumn, courseID, nameColumn, courseName);

        try {
            if(courseID.isEmpty() || courseName.isEmpty() || courseFaculty.isEmpty() || courseLevel.isEmpty() || departmentID.isEmpty() || facultyID.isEmpty()) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (doesExists) {
                    course_courseID.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");
                    course_courseName.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Course already exist..");

                } else {

                    ResultSet resultSet = Model.getInstance().getConnectDB().checkTeacherCoursesColumns(teacherID);

                    while (resultSet.next()) {
                        if (resultSet.getString("course1").isEmpty()) {
                            String column = "course1";
                            Model.getInstance().getConnectDB().insertCoursesInTeacher(column, teacherID, courseID, operation);

                        } else if(resultSet.getString("course2").isEmpty()) {
                            String column = "course2";
                            Model.getInstance().getConnectDB().insertCoursesInTeacher(column, teacherID, courseID, operation);
                        }
                    }

                    Model.getInstance().getConnectDB().createCourse(courseID, courseName, courseLevel, departmentID, teacherID, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully added.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setCourses());
                    // Update the teacher table with new info
                    teacher_tableView.setItems(Model.getInstance().setTeachers());
                    // Clear entries
                    clearFields();
                }
            }
        } catch (SQLException e) {

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
        String teacherID = course_teacher.getValue();
        String facultyID = course_faculty.getValue();
        boolean operation = true;
        boolean doesExists = Model.getInstance().getConnectDB().checkData(tableName, idColumn, courseID, nameColumn, courseName);

        try {
            if (courseID.isEmpty() || courseName.isEmpty() || courseFaculty.isEmpty() || courseLevel.isEmpty() || departmentID.isEmpty() || teacherID.isEmpty() || facultyID.isEmpty()) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");
            } else {

                if (doesExists) {
                    ResultSet resultSet = Model.getInstance().getConnectDB().checkTeacherCoursesColumns(teacherID);

                    while (resultSet.next()) {
                        if (resultSet.getString("course1").isEmpty()) {
                            String column = "course1";
                            Model.getInstance().getConnectDB().insertCoursesInTeacher(column, teacherID, courseID, operation);
                        } else if(resultSet.getString("course2").isEmpty()) {
                            String column = "course2";
                            Model.getInstance().getConnectDB().insertCoursesInTeacher(column, teacherID, courseID, operation);
                        }
                    }

                    Model.getInstance().getConnectDB().updateCourse(courseID, courseName, courseLevel, departmentID, teacherID, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully updated.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setCourses());
                    // Update the teacher table with new info
                    teacher_tableView.setItems(Model.getInstance().setTeachers());
                    // Clear entries
                    clearFields();

                } else {
                    course_courseID.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Course does not exist..");
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to update course from controller.");
            e.printStackTrace();
        }
    }
    private void deleteCourse() {

        String courseID = course_courseID.getText().toUpperCase();
        String courseName = course_courseName.getText();
        String teacherID = course_teacher.getValue();
        boolean operation = false;
        boolean doesExists = Model.getInstance().getConnectDB().checkData(tableName, idColumn,courseID, nameColumn, courseName);

        try {
            if (courseID.isEmpty() || teacherID.isEmpty()) {
                course_courseName.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (doesExists) {
                    ResultSet resultSet = Model.getInstance().getConnectDB().checkTeacherCoursesColumns(teacherID);

                    while (resultSet.next()) {
                        if (resultSet.getString("course1").equals(courseID)) {

                            String column = "course1";
                            Model.getInstance().getConnectDB().insertCoursesInTeacher(column, teacherID, courseID, operation);

                        } else if(resultSet.getString("course2").equals(courseID)) {
                            String column = "course2";
                            Model.getInstance().getConnectDB().insertCoursesInTeacher(column, teacherID, courseID, operation);
                        }
                    }

                    Model.getInstance().getConnectDB().deleteCourse(courseID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Course successfully deleted.");

                    // Update the course table with new info
                    course_tableView.setItems(Model.getInstance().setCourses());
                    // Update the teacher table with new info
                    teacher_tableView.setItems(Model.getInstance().setTeachers());
                    // Clear entries
                    clearFields();
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to delete course from controller.");
            e.printStackTrace();
        }
    }
    private void clearFields() {
        course_courseID.setText("");
        course_courseName.setText("");
        course_faculty.setValue(null);
        course_level.setValue(null);
        course_dept.setValue(null);
        course_teacher.setValue(null);
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
        course_tableView_col_teacher.setCellValueFactory(cellData -> cellData.getValue().teacherIDProperty());
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
        course_teacher.setValue(String.valueOf(courses.teacherIDProperty().get()));
        course_faculty.setValue(String.valueOf(courses.facultyIDProperty().get()));
    }

}
