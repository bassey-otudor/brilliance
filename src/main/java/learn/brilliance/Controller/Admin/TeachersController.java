package learn.brilliance.Controller.Admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.Accounts.Teacher;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TeachersController implements Initializable {
    String teacherID;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    String gender;
    String password;
    String facultyID;
    String departmentID;
    String course;
    LocalDate dob;
    
    private final String tableName = "teachers";

    public TextField teach_searchField;
    public ComboBox<String> teach_filterBy;
    public ComboBox<String> teach_filterOptions;
    public Button teach_resetFilterBtn;
    public TableView<Teacher> teach_tableView;
    public TableColumn<Teacher, String> teach_tableView_col_teacherID;
    public TableColumn<Teacher, String> teach_tableView_col_fName;
    public TableColumn<Teacher, String> teach_tableView_col_lName;
    public TableColumn<Teacher, String> teach_tableView_col_phoneNum;
    public TableColumn<Teacher, String> teach_tableView_col_email;
    public TableColumn<Teacher, String> teach_tableView_col_gender;
    public TableColumn<Teacher, String> teach_tableView_col_facultyID;
    public TableColumn<Teacher, String> teach_tableView_col_department;
    public TableColumn<Teacher, String> teach_tableView_col_course;
    public TableColumn<Teacher, String> teach_tableView_col_position;
    public TableColumn<Teacher, String> teach_tableView_col_dob;
    public TextField teach_fName;
    public TextField teach_lName;
    public TextField teach_email;
    public TextField teach_phoneNum;
    public DatePicker teach_dob;
    public PasswordField teach_pwd;
    public ComboBox<String> teach_gender;
    public TextField teach_teacherID;
    public ComboBox<String> teach_deptID;
    public ComboBox<String> teach_facultyID;
    public ComboBox<String> teach_course;
    public Label operationStatus;
    public Button teach_genIDBtn;
    public Button teach_deleteBtn;
    public Button teach_clearBtn;
    public Button teach_updateBtn;
    public Button teach_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Teacher tableView manipulation section
        teach_filterBy.setItems(Model.getInstance().getConnectDB().getFilterByForTeachers());
        teach_filterBy.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal)
                -> filterOptions(newVal));

        teach_facultyID.setItems(Model.getInstance().getConnectDB().getFaculties());
        teach_facultyID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> teach_deptID.setItems(Model.getInstance().getConnectDB().getFacultyDepartments(newVal)));

        teach_deptID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> teach_course.setItems(Model.getInstance().getConnectDB().getDepartmentCourses(newVal)));

        teach_gender.setItems(Model.getInstance().getConnectDB().getGender());
        teach_resetFilterBtn.setOnAction(e -> resetFilters());


        teach_addBtn.setOnAction(e -> createTeacher());
        teach_updateBtn.setOnAction(e -> updateTeacher());
        teach_deleteBtn.setOnAction(e -> deleteTeacher());
        teach_clearBtn.setOnAction(e -> clearFields());
        teach_genIDBtn.setOnAction(e -> generateTeacherID());

        // Teacher tableview section
        initialiseTeachersTable();
        bindTeachersTableData();
        teach_tableView.setItems(Model.getInstance().setAllTeachers());
        updateTeachersTable();
        teach_tableView.setOnMouseClicked(e -> selectTeacher());

        selectTeacher();
        searchTeacher();
        filterTeacher();
    }

    /**
     * Initializes the search functionality for the teachers table.
     * This method sets up a FilteredList to filter the teachers table based on the input from the search field.
     * The searchFilter is updated whenever the search field's text property changes.
     * The searchFilter is then used to populate the teachers table with filtered data.
     *
     * @see FilteredList
     * @see SortedList
     */
    private void searchTeacher() {
        FilteredList<Teacher> searchFilter = new FilteredList<>(Model.getInstance().setAllTeachers(), e -> true);
        teach_searchField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateTeacher -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String searchKey = newVal.toLowerCase();

                if (predicateTeacher.teacherIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.firstNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.lastNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.phoneNumberProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.emailProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.genderProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.facultyIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateTeacher.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.courseProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.positionProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else return predicateTeacher.dobProperty().toString().toLowerCase().contains(searchKey);
            });

            SortedList<Teacher> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(teach_tableView.comparatorProperty());
            teach_tableView.setItems(sortedList);
        });
    }
    private void filterOptions(String val) {
        switch (val){
            case "Faculty" -> teach_filterOptions.setItems(Model.getInstance().getConnectDB().getFaculties());
            case "Department" -> teach_filterOptions.setItems(Model.getInstance().getConnectDB().getAllDepartments());
            case "Gender" -> teach_filterOptions.setItems(Model.getInstance().getConnectDB().getGender());
        }
    }
    private void filterTeacher() {
        FilteredList<Teacher> searchFilter = new FilteredList<>(Model.getInstance().setAllTeachers(), e -> true);
        teach_filterOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateTeacher -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String filterKey = newVal.toLowerCase();
                if(predicateTeacher.facultyIDProperty().toString().toLowerCase().contains(filterKey)) {
                    return true;
                } else if(predicateTeacher.departmentIDProperty().toString().toLowerCase().contains(filterKey)) {
                    return true;
                } else if(predicateTeacher.positionProperty().toString().toLowerCase().contains(filterKey)) {
                    return true;
                } else return predicateTeacher.genderProperty().toString().toLowerCase().contains(filterKey);
            });

            SortedList<Teacher> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(teach_tableView.comparatorProperty());
            teach_tableView.setItems(sortedList);
        });
    }

    private void createTeacher() {
        teacherID = teach_teacherID.getText();
        firstName = teach_fName.getText();
        lastName = teach_lName.getText();
        String teacherFullName = firstName + " " + lastName;
        gender = teach_gender.getValue();
        phoneNumber = teach_phoneNum.getText();
        email = teach_email.getText();
        departmentID = teach_deptID.getValue();
        dob = LocalDate.parse(teach_dob.getValue().toString());
        password = teach_pwd.getText();
        course = teach_course.getSelectionModel().getSelectedItem();
        facultyID = teach_facultyID.getValue();
        boolean operation = true;
        boolean doesExist = Model.getInstance().getConnectDB().checkData(teacherID, tableName);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        if (teacherID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || facultyID.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please fill required fields.");

        } else {

            if (doesExist) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
                operationStatus.setText("Teacher already exists.");

            } else {


                if (course == null) {
                    Model.getInstance().getConnectDB().createTeacher(teacherID, firstName, lastName, gender, phoneNumber, email, departmentID, dob, hashedPassword, course, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Teacher created successfully.");

                } else {
                    Model.getInstance().getConnectDB().createTeacher(teacherID, firstName, lastName, gender, phoneNumber, email, departmentID, dob, hashedPassword, course, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Teacher created successfully.");
                    alterTeacherInCourses(teacherID, course, teacherFullName, operation);
                    clearFields();

                }
            }

            // Update teacher table with new data
            teach_tableView.setItems(Model.getInstance().setAllTeachers());
        }
    }
    private void updateTeacher() {
        teacherID = teach_teacherID.getText();
        firstName = teach_fName.getText();
        lastName = teach_lName.getText();
        String teacherName = firstName + " " + lastName;
        gender = teach_gender.getValue();
        phoneNumber = teach_phoneNum.getText();
        email = teach_email.getText();
        departmentID = teach_deptID.getValue();
        dob = LocalDate.parse(teach_dob.getValue().toString());
        course = teach_course.getValue();
        facultyID = teach_facultyID.getValue();
        boolean operation = true;
        boolean doesExist = Model.getInstance().getConnectDB().checkData(teacherID, tableName); // check if teacher already exists

        if (doesExist) {
            if (teacherID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dob == null || phoneNumber.isEmpty() || email.isEmpty() || facultyID.isEmpty()) {

                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (course.isEmpty()) {
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Please select a course.");

                } else {
                    Model.getInstance().getConnectDB().updateTeacher(teacherID, firstName, lastName, gender, phoneNumber, email, departmentID, dob, course, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Teacher updated successfully.");

                    teach_tableView.setItems(Model.getInstance().setAllTeachers());

                    // If @param operation is true, assign a teacher to a course and if @param operation is false, remove a teacher from assigned course
                    alterTeacherInCourses(teacherID, course, teacherName, operation);

                    clearFields();
                }

            }
        } else {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Teacher not found.");

        }
    }
    private void alterTeacherInCourses(String teacherID, String course, String teacherName, boolean operation) {
        Model.getInstance().getConnectDB().insertTeacherInCourse(teacherID, teacherName, course, operation);

    }
    private void deleteTeacher() {
        String teacherID = teach_teacherID.getText();
        String courseID = teach_course.getValue();
        boolean operation = false;
        boolean doesExist = Model.getInstance().getConnectDB().checkData(teacherID, tableName);

        if (doesExist) {

            assert courseID != null;
            Model.getInstance().getConnectDB().deleteTeacher(teacherID);
            Model.getInstance().getConnectDB().insertTeacherInCourse(teacherID, null, courseID,operation);

            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
            operationStatus.setText("Teacher deleted successfully.");
            teach_tableView.setItems(Model.getInstance().setAllTeachers());

            clearFields();

        } else {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Teacher not found.");

        }


    }
    private void updateTeachersTable() {  // Update courses table
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> { // keyFrame with a 4 seconds trigger
            teach_tableView.setItems(Model.getInstance().setAllTeachers());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // repeats indefinitely
        timeline.playFromStart();
    }
    private void clearFields() {
        teach_teacherID.setText(null);
        teach_fName.setText(null);
        teach_lName.setText(null);
        teach_gender.setValue(null);
        teach_phoneNum.setText(null);
        teach_email.setText(null);
        teach_deptID.setValue(null);
        teach_dob.setValue(null);
        teach_pwd.setText(null);
        teach_course.setValue(null);
    }
    private void resetFilters() {
        teach_filterBy.setValue("");
        teach_filterOptions.setValue("");
    }
    public void initialiseTeachersTable() {
        if (Model.getInstance().getAllTeachers().isEmpty()) {
            Model.getInstance().setAllTeachers();
        }
    }
    private void bindTeachersTableData() {
        teach_tableView_col_teacherID.setCellValueFactory(cellData -> cellData.getValue().teacherIDProperty());
        teach_tableView_col_fName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        teach_tableView_col_lName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        teach_tableView_col_phoneNum.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        teach_tableView_col_email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        teach_tableView_col_gender.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        teach_tableView_col_facultyID.setCellValueFactory(cellData -> cellData.getValue().facultyIDProperty());
        teach_tableView_col_department.setCellValueFactory(cellData -> cellData.getValue().departmentIDProperty());
        teach_tableView_col_course.setCellValueFactory(cellData -> cellData.getValue().courseProperty());
        teach_tableView_col_position.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        teach_tableView_col_dob.setCellValueFactory(cellData -> cellData.getValue().dobProperty());

    }
    private void generateTeacherID() {
        String rowCount = String.valueOf(Model.getInstance().getConnectDB().getTeacherRowCount() + 1);
        String prefix = "SE";
        String year = String.valueOf(LocalDate.now().getYear());

        if (rowCount.length() == 1) {
            teach_teacherID.setText(prefix + year + "00" + rowCount);

        } else if (rowCount.length() == 2) {
            teach_teacherID.setText(prefix + year + "0" + rowCount);

        } else if (rowCount.length() == 3) {
            teach_teacherID.setText(prefix + year + rowCount);
        }

    }

    /**
     * Selects the teacher from the table based on the selected row index.
     * This method retrieves the selected teacher object from the table and populates the input fields with the selected teacher's data.
     *
     * @see javafx.scene.control.TableView
     * @see javafx.scene.control.TableColumn
     */
    private void selectTeacher() {
        Teacher teacher = teach_tableView.getSelectionModel().getSelectedItem();
        int num = teach_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) return;
        teach_teacherID.setText(String.valueOf(teacher.teacherIDProperty().get()));
        teach_fName.setText(String.valueOf(teacher.firstNameProperty().get()));
        teach_lName.setText(String.valueOf(teacher.lastNameProperty().get()));
        teach_gender.setValue(String.valueOf(teacher.genderProperty().get()));
        teach_phoneNum.setText(String.valueOf(teacher.phoneNumberProperty().get()));
        teach_email.setText(String.valueOf(teacher.emailProperty().get()));
        teach_facultyID.setValue(String.valueOf(teacher.facultyIDProperty().get()));
        teach_deptID.setValue(String.valueOf(teacher.departmentIDProperty().get()));
        teach_dob.setValue(LocalDate.parse(teacher.dobProperty().get()));
        teach_course.setValue(String.valueOf(teacher.courseProperty().get()));
    }
}