package learn.brilliance.Controller.Admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import learn.brilliance.Model.Accounts.Student;
import learn.brilliance.Model.Model;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {
    private final String tableName = "students";

    String studentID;
    String firstName;
    String lastName;
    String gender;
    String dob;
    String phoneNumber;
    String email;
    String password;
    String facultyID;
    String departmentID;
    String degreeID;
    String minorID;
    String level;
    String registrationDate = String.valueOf(LocalDate.now());

    public TextField stud_searchField;
    public ComboBox<String> stud_filterBy;
    public ComboBox<String> stud_filterOptions;
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
    public Button stud_resetFilterBtn;
    public Label operationStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Student tableView manipulation section
        stud_filterBy.setItems(Model.getInstance().getConnectDB().getFilterByForStudents());
        stud_filterBy.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> filterOptions(newVal));
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
        stud_resetFilterBtn.setOnAction(e -> resetFilter());

        // Student tableView section
        initialiseStudentTable();
        bindStudentTableData();
        stud_tableView.setItems(Model.getInstance().setAllStudents());
        updateStudentsTable();
        stud_tableView.setOnMouseClicked(e -> selectStudent());
        searchStudents();
        selectStudent();
        filterStudents();
    }

    private void searchStudents() {
        FilteredList<Student> searchFilter = new FilteredList<>(Model.getInstance().setAllStudents(), e -> true);
        stud_searchField.textProperty().addListener((observable, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateStudent -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String searchKey = newVal.toLowerCase();
                if(predicateStudent.studentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.firstNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.lastNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.genderProperty().toString().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.dobProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.phoneNumberProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.emailProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.facultyIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.degreeIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.minorIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateStudent.levelProperty().toString().toLowerCase().toLowerCase().contains(searchKey)) {
                    return true;
                } else return predicateStudent.registrationDateProperty().toString().toLowerCase().contains(searchKey);
            });

            SortedList<Student> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(stud_tableView.comparatorProperty());
            stud_tableView.setItems(sortedList);
        });
    }
    private void filterOptions(String val) {
        switch (val){
            case "Faculty" -> stud_filterOptions.setItems(Model.getInstance().getConnectDB().getFaculties());
            case "Department" -> stud_filterOptions.setItems(Model.getInstance().getConnectDB().getAllDepartments());
            case "Degree" -> stud_filterOptions.setItems(Model.getInstance().getConnectDB().getAllDegrees());
            case "Minor" -> stud_filterOptions.setItems(Model.getInstance().getConnectDB().getAllMinors());
        }
    }
    private void filterStudents() {
        FilteredList<Student> searchFilter = new FilteredList<>(Model.getInstance().setAllStudents(), e -> true);
        stud_filterOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateTeacher -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String filterKey = newVal.toLowerCase();
                if(predicateTeacher.facultyIDProperty().toString().toLowerCase().contains(filterKey)) {
                    return true;
                } else if(predicateTeacher.departmentIDProperty().toString().toLowerCase().contains(filterKey)) {
                    return true;
                } else if(predicateTeacher.degreeIDProperty().toString().toLowerCase().contains(filterKey)) {
                    return true;
                } else return predicateTeacher.minorIDProperty().toString().toLowerCase().contains(filterKey);
            });

            SortedList<Student> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(stud_tableView.comparatorProperty());
            stud_tableView.setItems(sortedList);
        });
    }
    private void createStudent()   {
        studentID = stud_studentID.getText();
        firstName = stud_fName.getText();
        lastName = stud_lName.getText();
        gender = stud_gender.getValue();
        dob = String.valueOf(stud_dob.getValue());
        phoneNumber = stud_phoneNumber.getText();
        email = stud_email.getText();
        password = stud_password.getText();
        facultyID = stud_faculty.getValue();
        departmentID = stud_department.getValue();
        degreeID = stud_degree.getValue();
        minorID = stud_minor.getValue();
        level = stud_level.getValue();
        registrationDate = String.valueOf(LocalDate.now());

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        boolean doesExist = Model.getInstance().getConnectDB().checkData(studentID, tableName);

        if((studentID == null || studentID.isEmpty()) ||
                (firstName == null || firstName.isEmpty()) ||
                (lastName == null || lastName.isEmpty()) ||
                (gender == null || gender.isEmpty()) ||
                ( dob == null || dob.isEmpty()) ||
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
                Model.getInstance().getConnectDB().createStudent(studentID, firstName, lastName, gender, dob, phoneNumber, email, hashedPassword, facultyID, departmentID, degreeID, minorID, level, registrationDate);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em; -fx-font-weight: bold");

                insertStudentInDegreeCourses();
                operationStatus.setText("Student created successfully.");
                stud_tableView.setItems(Model.getInstance().setAllStudents());
                clearFields();
            }
        }
    }
    private void updateStudent() {

        studentID = stud_studentID.getText();
        firstName = stud_fName.getText();
        lastName = stud_lName.getText();
        gender = stud_gender.getValue();
        dob = String.valueOf(stud_dob.getValue());
        phoneNumber = stud_phoneNumber.getText();
        email = stud_email.getText();
        facultyID = stud_faculty.getValue();
        departmentID = stud_department.getValue();
        degreeID = stud_degree.getValue();
        minorID = stud_minor.getValue();
        level = stud_level.getValue();

        boolean doesExist = Model.getInstance().getConnectDB().checkData(studentID, tableName);

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
                stud_studentID.setStyle("-fx-border-color: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Student not found.");

            }
        }
    }
    private void deleteStudent() {
        studentID = stud_studentID.getText();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(studentID, tableName);

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
    private void updateStudentsTable() {  // Update courses table
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> { // keyFrame with a 4 seconds trigger
            stud_tableView.setItems(Model.getInstance().setAllStudents());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // repeats indefinitely
        timeline.playFromStart();
    }
    private void insertStudentInDegreeCourses() { // Adds the student to the course records of courses belonging to the student's registered degree.
        String studentID = stud_studentID.getText();
        String studentFullName = stud_fName.getText() + " " + stud_lName.getText();
        String degreeID = stud_degree.getValue().toUpperCase(); // get the degreeID from the text field
        String departmentTableName = stud_department.getValue().toUpperCase() + "-" + stud_level.getValue().toUpperCase(); // department table name composes of the courseID and course level.
        ObservableList<String> degreeCourseList = Model.getInstance().getConnectDepartmentDB().getAllDegreeCourses(departmentTableName, degreeID); // gets the list of courses associated with the selected degree

        for(String degreeCourse : degreeCourseList) { // Looping through the array list of degree courses
            String tableName = degreeCourse.toUpperCase() + "-" + LocalDate.now().getYear();
            Model.getInstance().getConnectRecord().insertStudentOnRegister(tableName, studentID, studentFullName); // Insert a student into respective course table records
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
    private void resetFilter() {
        stud_filterBy.setValue("");
        stud_filterOptions.setValue("");
    }
}
