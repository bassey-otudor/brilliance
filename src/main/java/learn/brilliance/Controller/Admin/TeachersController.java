package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.Teacher;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TeachersController implements Initializable {

    private final String tableName = "teachers";
    private final String idColumn = "teacherID";
    private final String columnName = "fName";
    public TextField teach_searchField;
    public ComboBox<String> teach_filterDept;
    public ComboBox<String> teach_filterCourse;
    public TableView<Teacher> teach_tableView;
    public TableColumn<Teacher, String> teach_tableView_col_teacherID;
    public TableColumn<Teacher, String> teach_tableView_col_fName;
    public TableColumn<Teacher, String> teach_tableView_col_lName;
    public TableColumn<Teacher, String> teach_tableView_col_phoneNum;
    public TableColumn<Teacher, String> teach_tableView_col_email;
    public TableColumn<Teacher, String> teach_tableView_col_gender;
    public TableColumn<Teacher, String> teach_tableView_col_facultyID;
    public TableColumn<Teacher, String> teach_tableView_col_department;
    public TableColumn<Teacher, String> teach_tableView_col_course1;
    public TableColumn<Teacher, String> teach_tableView_col_course2;
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
    public ComboBox<String> teach_course1;
    public ComboBox<String> teach_course2;
    public ComboBox<String> teach_position;
    public Label operationStatus;
    public Button teach_genIDBtn;
    public Button teach_deleteBtn;
    public Button teach_clearBtn;
    public Button teach_updateBtn;
    public Button teach_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        teach_facultyID.setItems(Model.getInstance().getConnectDB().getFaculties());
        teach_facultyID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> teach_deptID.setItems(Model.getInstance().getConnectDB().getFacultyDepartments(newVal)));

        teach_deptID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> teach_course1.setItems(Model.getInstance().getConnectDB().getDepartmentCourses(newVal)));

        teach_deptID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> teach_course2.setItems(Model.getInstance().getConnectDB().getDepartmentCourses(newVal)));

        teach_gender.setItems(Model.getInstance().getConnectDB().getGender());
        teach_position.setItems(Model.getInstance().getConnectDB().getPosition());

        teach_addBtn.setOnAction(e -> createTeacher());
        teach_updateBtn.setOnAction(e -> updateTeacher());
        teach_deleteBtn.setOnAction(e -> deleteTeacher());
        teach_clearBtn.setOnAction(e -> clearFields());
        teach_genIDBtn.setOnAction(e -> generateTeacherID());

        // Teacher tableview section
        initialiseTeachersTable();
        bindTeachersTableData();
        teach_tableView.setItems(Model.getInstance().setAllTeachers());
        teach_tableView.setOnMouseClicked(e -> selectTeacher());

        searchTeacher();
    }

    private void searchTeacher() {
        FilteredList<Teacher> searchFilter = new FilteredList<>(Model.getInstance().setAllTeachers(), e -> true);
        teach_searchField.textProperty().addListener(((observableValue, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateTeacher -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String searchKey = newVal.toLowerCase();

                if (predicateTeacher.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.firstNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.lastNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.course1Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.course2Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.positionProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

            SortedList<Teacher> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(teach_tableView.comparatorProperty());
            teach_tableView.setItems(sortedList);
        }));
    }

    private void createTeacher() {
        String teacherID = teach_teacherID.getText();
        String firstName = teach_fName.getText();
        String lastName = teach_lName.getText();
        String teacherFullName = firstName + " " + lastName;
        String gender = teach_gender.getValue();
        String phoneNumber = teach_phoneNum.getText();
        String email = teach_email.getText();
        String departmentID = teach_deptID.getValue();
        LocalDate dob = LocalDate.parse(teach_dob.getValue().toString());
        String password = teach_pwd.getText();
        String course1 = teach_course1.getSelectionModel().getSelectedItem();
        String course2 = teach_course2.getSelectionModel().getSelectedItem();
        String position = teach_position.getValue();
        String facultyID = teach_facultyID.getValue();
        boolean operation = true;
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, teacherID, columnName, firstName);

        if (teacherID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || position.isEmpty() || facultyID.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please fill required fields.");

        } else {

            if (doesExist) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Teacher already exists.");

            } else {


                if (course1 == null || course2 == null) {
                    Model.getInstance().getConnectDB().createTeacher(teacherID, firstName, lastName, gender, phoneNumber, email, departmentID, dob, password, course1, course2, position, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Teacher created successfully.");

                } else {
                    Model.getInstance().getConnectDB().createTeacher(teacherID, firstName, lastName, gender, phoneNumber, email, departmentID, dob, password, course1, course2, position, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Teacher created successfully.");

                    alterTeacherInCourses(teacherID, course1, course2, teacherFullName, operation);

                }
            }

            // Update teacher table with new data
            teach_tableView.setItems(Model.getInstance().setAllTeachers());
            clearFields();
        }
    }

    private void updateTeacher() {
        String teacherID = teach_teacherID.getText();
        String firstName = teach_fName.getText();
        String lastName = teach_lName.getText();
        String teacherName = firstName + " " + lastName;
        String gender = teach_gender.getValue();
        String phoneNumber = teach_phoneNum.getText();
        String email = teach_email.getText();
        String departmentID = teach_deptID.getValue();
        LocalDate dob = LocalDate.parse(teach_dob.getValue().toString());
        String course1 = teach_course1.getValue();
        String course2 = teach_course2.getValue();
        String position = teach_position.getValue();
        String facultyID = teach_facultyID.getValue();
        boolean operation = true;
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, teacherID, columnName, firstName);

        if (doesExist) {
            if (teacherID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || position.isEmpty() || facultyID.isEmpty()) {

                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if (course1.isEmpty() && course2.isEmpty()) {
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Choose at least one course.");

                } else {
                    Model.getInstance().getConnectDB().updateTeacher(teacherID, firstName, lastName, gender, phoneNumber, email, departmentID, dob, course1, course2, position, facultyID);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Teacher updated successfully.");

                    teach_tableView.setItems(Model.getInstance().setAllTeachers());
                    alterTeacherInCourses(teacherID, course1, teacherName, course2, operation);

                    clearFields();
                }

            }
        } else {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Teacher not found.");

        }
    }

    private void alterTeacherInCourses(String teacherID, String course1, String course2, String teacherName, boolean operation) {
        if (!course1.isEmpty() && !course2.isEmpty()) {

            if (course1.equals(course2)) {
                teach_course1.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                teach_course2.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");

                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Cannot teach the same course twice.");

            } else {
                Model.getInstance().getConnectDB().insertTeacherInCourse(teacherID, teacherName, course1, operation);
                Model.getInstance().getConnectDB().insertTeacherInCourse(teacherID, teacherName, course2, operation);
            }

        } else if (!course1.isEmpty()) {
            Model.getInstance().getConnectDB().insertTeacherInCourse(teacherID, teacherName, course1, operation);
        }
    }

    private void deleteTeacher() {
        String teacherID = teach_teacherID.getText();
        String firstName = teach_fName.getText();
        String courseID = teach_course1.getValue();
        boolean operation = false;
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, teacherID, columnName, firstName);

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

    private void clearFields() {
        teach_teacherID.setText("");
        teach_fName.setText("");
        teach_lName.setText("");
        teach_gender.setValue(null);
        teach_phoneNum.setText("");
        teach_email.setText("");
        teach_deptID.setValue(null);
        teach_dob.setValue(null);
        teach_pwd.setText("");
        teach_course1.setValue(null);
        teach_course2.setValue(null);
        teach_position.setValue(null);
    }

    private void initialiseTeachersTable() {
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
        teach_tableView_col_course1.setCellValueFactory(cellData -> cellData.getValue().course1Property());
        teach_tableView_col_course2.setCellValueFactory(cellData -> cellData.getValue().course2Property());
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
        teach_course1.setValue(String.valueOf(teacher.course1Property().get()));
        teach_course2.setValue(String.valueOf(teacher.course2Property().get()));
        teach_position.setValue(String.valueOf(teacher.positionProperty().get()));
    }
}