package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Accounts.Student;
import learn.brilliance.Model.Accounts.Teacher;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {
    private final String tableName = "students";
    private final String idColumn = "studentID";
    private final String columnName = "firstName";
    public TextField stud_searchField;
    public ComboBox<String> stud_filterDept;
    public ComboBox<String> stud_filterCourse;
    public TableView<Student> stud_tableView;
    public TableColumn<Student, String> stud_tableView_col_studentID;
    public TableColumn<Student, String> stud_tableView_col_fName;
    public TableColumn<Student, String> stud_tableView_col_lName;
    public TableColumn<Student, String> stud_tableView_col_phoneNumber;
    public TableColumn<Student, String> stud_tableView_col_email;
    public TableColumn<Student, String> stud_tableView_col_gender;
    public TableColumn<Student, String> stud_tableView_col_dob;
    public TableColumn<Student, String> stud_tableView_col_facultyID;
    public TableColumn<Student, String> stud_tableView_col_deptID;
    public TableColumn<Student, String> stud_tableView_col_degreeID;
    public TableColumn<Student, String> stud_tableView_col_minorID;
    public TableColumn<Student, String> stud_tableView_col_level;
    public TableColumn<Student, String> stud_tableView_col_regDate;
    public TextField stud_fName;
    public TextField stud_lName;
    public TextField stud_email;
    public TextField stud_phoneNumber;
    public DatePicker stud_dob;
    public ComboBox<String> stud_gender;
    public ComboBox<String> stud_faculty;
    public ComboBox<String> stud_department;
    public ComboBox<String> stud_degree;
    public ComboBox<String> stud_minor;
    public PasswordField stud_password;
    public ComboBox<String> stud_level;
    public TextField stud_studentID;
    public Button stud_genIDBtn;
    public Button stud_deleteBtn;
    public Button stud_clearBtn;
    public Button stud_updateBtn;
    public Button stud_addBtn;
    public Label operationStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Student tableView manipulation section
        stud_faculty.setItems(Model.getInstance().getConnectDB().getFaculties());

        stud_faculty.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> stud_department.setItems(Model.getInstance().getConnectDB().getFacultyDepartments(newVal)));

        stud_department.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> stud_degree.setItems(Model.getInstance().getConnectDB().getDepartmentDegrees(newVal)));

        stud_degree.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal)
                -> stud_minor.setItems(Model.getInstance().getConnectDB().getDegreeMinors(newVal)));
        stud_gender.setItems(Model.getInstance().getConnectDB().getGender());
        stud_level.setItems(Model.getInstance().getConnectDB().getLevel());

        stud_genIDBtn.setOnAction(e -> generateStudentID());
        stud_addBtn.setOnAction(e -> createStudent());
        stud_updateBtn.setOnAction(e -> updateStudent());
        stud_deleteBtn.setOnAction(e -> deleteStudent());
        stud_clearBtn.setOnAction(e -> clearFields());

        // Student tableView section
        initialiseStudentTable();
        bindStudentTableData();
        stud_tableView.setItems(Model.getInstance().setAllStudents());
        stud_tableView.setOnMouseClicked(e -> selectStudent());
        searchStudents();

    }

    private void searchStudents() {
        FilteredList<Student> searchFilter = new FilteredList<>(Model.getInstance().setAllStudents(), e -> true);
        stud_searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchFilter.setPredicate(predicateStudent -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if(predicateStudent.studentIDProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.firstNameProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.lastNameProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.genderProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.dobProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.phoneNumberProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.emailProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.facultyIDProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.departmentIDProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.degreeIDProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.minorIDProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.levelProperty().toString().contains(searchKey)) {
                    return true;
                } else return predicateStudent.registrationDateProperty().toString().contains(searchKey);
            });
        }));
    }

    private void createStudent()   {
        String studentID = stud_studentID.getText();
        String firstName = stud_fName.getText();
        String lastName = stud_lName.getText();
        String gender = stud_gender.getValue();
        String dob = String.valueOf(stud_dob.getValue());
        String phoneNumber = stud_phoneNumber.getText();
        String email = stud_email.getText();
        String password = stud_password.getText();
        String facultyID = stud_faculty.getValue();
        String departmentID = stud_department.getValue();
        String degreeID = stud_degree.getValue();
        String minorID = stud_minor.getValue();
        String level = stud_level.getValue();
        String registrationDate = String.valueOf(LocalDate.now());
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, studentID, columnName,null);

        if((studentID == null || studentID.isEmpty()) ||
                (firstName == null || firstName.isEmpty()) ||
                (lastName == null || lastName.isEmpty()) ||
                (gender == null || gender.isEmpty()) ||
                (dob.isEmpty()) ||
                (email == null || email.isEmpty()) ||
                (password == null || password.isEmpty()) ||
                (facultyID == null || facultyID.isEmpty()) ||
                (departmentID == null || departmentID.isEmpty()) ||
                (degreeID == null || degreeID.isEmpty()) ||
                (minorID == null || minorID.isEmpty()) ||
                (level == null || level.isEmpty())

        ) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
            operationStatus.setText("Please fill required fields.");

        } else {
            if (doesExist) {
                stud_studentID.setStyle("-fx-border-color: #EC6666;");
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
                operationStatus.setText("Student already exists.");

            } else {
                Model.getInstance().getConnectDB().createStudent(studentID, firstName, lastName, gender, dob, phoneNumber, email, password, facultyID, departmentID, degreeID, minorID, level, registrationDate);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em; -fx-font-weight: bold");
                operationStatus.setText("Student created successfully.");
                stud_tableView.setItems(Model.getInstance().setAllStudents());
                clearFields();
            }
        }
    }
    private void updateStudent() {
        String studentID = stud_studentID.getText();
        String firstName = stud_fName.getText();
        String lastName = stud_lName.getText();
        String gender = stud_gender.getValue();
        String dob = String.valueOf(stud_dob.getValue());
        String phoneNumber = stud_phoneNumber.getText();
        String email = stud_email.getText();
        String facultyID = stud_faculty.getValue();
        String departmentID = stud_department.getValue();
        String degreeID = stud_degree.getValue();
        String minorID = stud_minor.getValue();
        String level = stud_level.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, studentID, columnName,null);

        if((studentID == null || studentID.isEmpty()) ||
                (firstName == null || firstName.isEmpty()) ||
                (lastName == null || lastName.isEmpty()) ||
                (gender == null || gender.isEmpty()) ||
                (dob.isEmpty()) ||
                (email == null || email.isEmpty()) ||
                (facultyID == null || facultyID.isEmpty()) ||
                (departmentID == null || departmentID.isEmpty()) ||
                (degreeID == null || degreeID.isEmpty()) ||
                (minorID == null || minorID.isEmpty()) ||
                (level == null || level.isEmpty())

        ) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
            operationStatus.setText("Please fill required fields.");

        } else {
            if (doesExist) {
                Model.getInstance().getConnectDB().updateStudent(studentID, firstName, lastName, gender, dob, phoneNumber, email, facultyID, departmentID, degreeID, minorID, level);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em; -fx-font-weight: bold");
                operationStatus.setText("Student updated successfully.");
                stud_tableView.setItems(Model.getInstance().setAllStudents());
                clearFields();

            } else {
                stud_studentID.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Student not found.");

            }
        }
    }
    private void deleteStudent() {
        String studentID = stud_studentID.getText();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, studentID, columnName, null);

        if(studentID == null || studentID.isEmpty()) {
            stud_studentID.setStyle("-fx-border-color: #EC6666;");
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
            operationStatus.setText("Enter a student ID.");

        } else {
            if(doesExist) {
                Model.getInstance().getConnectDB().deleteStudent(studentID);
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
                operationStatus.setText("Student deleted successfully.");
                stud_tableView.setItems(Model.getInstance().setAllStudents());
                clearFields();

            } else {
                stud_studentID.setStyle("-fx-border-color: #EC6666;");
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
                operationStatus.setText("Student not found.");
            }
        }
    }
    private void clearFields() {
        stud_studentID.setText(null);
        stud_fName.setText(null);
        stud_lName.setText(null);
        stud_gender.setValue(null);
        stud_dob.setValue(null);
        stud_phoneNumber.setText(null);
        stud_email.setText(null);
        stud_password.setText(null);
        stud_faculty.setValue(null);
        stud_department.setValue(null);
        stud_degree.setValue(null);
        stud_minor.setValue(null);
    }
    private void initialiseStudentTable() {
        if(Model.getInstance().getAllStudents().isEmpty()) {
            Model.getInstance().setAllStudents();
        }
    }
    private void bindStudentTableData() {
        stud_tableView_col_studentID.setCellValueFactory(cellData -> cellData.getValue().studentIDProperty());
        stud_tableView_col_fName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        stud_tableView_col_lName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        stud_tableView_col_gender.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        stud_tableView_col_dob.setCellValueFactory(cellData -> cellData.getValue().dobProperty());
        stud_tableView_col_phoneNumber.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        stud_tableView_col_email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        stud_tableView_col_facultyID.setCellValueFactory(cellData -> cellData.getValue().facultyIDProperty());
        stud_tableView_col_deptID.setCellValueFactory(cellData -> cellData.getValue().departmentIDProperty());
        stud_tableView_col_degreeID.setCellValueFactory(cellData -> cellData.getValue().degreeIDProperty());
        stud_tableView_col_minorID.setCellValueFactory(cellData -> cellData.getValue().minorIDProperty());
        stud_tableView_col_level.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
        stud_tableView_col_regDate.setCellValueFactory(cellData -> cellData.getValue().registrationDateProperty());
    }
    private void generateStudentID() {
        String generatedID = null;
        String rowCount = String.valueOf(Model.getInstance().getConnectDB().getStudentRowCount() + 1);
        String prefix = "STU";
        String year = String.valueOf(LocalDate.now().getYear());

        if(rowCount.length() == 1) {
            generatedID = prefix + year + "000" + rowCount;
        } else if(rowCount.length() == 2) {
            generatedID = prefix + year + "00" + rowCount;
        } else if(rowCount.length() == 3) {
            generatedID = prefix + year + "0" + rowCount;
        } else if(rowCount.length() == 4) {
            generatedID = prefix + year + rowCount;
        }
        stud_studentID.setText(generatedID);
    }
    private void selectStudent() {
        Student student = stud_tableView.getSelectionModel().getSelectedItem();
        int num = stud_tableView.getSelectionModel().getSelectedIndex();
        if((num -1) < -1) return;
        stud_studentID.setText(String.valueOf(student.studentIDProperty().get()));
        stud_fName.setText(String.valueOf(student.firstNameProperty().get()));
        stud_lName.setText(String.valueOf(student.lastNameProperty().get()));
        stud_gender.setValue(String.valueOf(String.valueOf(student.genderProperty().get())));
        stud_dob.setValue(LocalDate.parse(String.valueOf(student.dobProperty().get())));
        stud_phoneNumber.setText(String.valueOf(student.phoneNumberProperty().get()));
        stud_email.setText(String.valueOf(String.valueOf(student.emailProperty().get())));
        stud_faculty.setValue(String.valueOf(student.facultyIDProperty().get()));
        stud_department.setValue(String.valueOf(student.departmentIDProperty().get()));
        stud_degree.setValue(String.valueOf(student.degreeIDProperty().get()));
        stud_minor.setValue(String.valueOf(student.minorIDProperty().get()));
        stud_level.setValue(String.valueOf(student.levelProperty().get()));
    }
}
