package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Faculty;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.Teacher;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TeachersController implements Initializable {
    public TextField teach_searchField;
    public ChoiceBox<String> teach_filterDept;
    public ChoiceBox<String> teach_filterCourse;
    public TableView<Teacher> teach_tableView;
    public TableColumn<Teacher, String> teach_tableView_col_teacherID;
    public TableColumn<Teacher, String> teach_tableView_col_fName;
    public TableColumn<Teacher, String> teach_tableView_col_lName;
    public TableColumn<Teacher, String> teach_tableView_col_phoneNum;
    public TableColumn<Teacher, String> teach_tableView_col_email;
    public TableColumn<Teacher, String> teach_tableView_col_gender;
    public TableColumn<Teacher, String> teach_tableView_col_department;
    public TableColumn<Teacher, String> teach_tableView_col_course1;
    public TableColumn<Teacher, String> teach_tableView_col_course2;
    public TableColumn<Teacher, String> teach_tableView_col_position;
    public TableColumn<Teacher, String> teach_tableView_col_dob;
    public TextField teach_fName;
    public TextField teach_lName;
    public TextField teach_email;
    public TextField teach_phoneNum;
    public ChoiceBox<String> teach_gender;
    public DatePicker teach_dob;
    public ChoiceBox<String> teach_deptID;
    public PasswordField teach_pwd;
    public TextField teach_teacherID;
    public ChoiceBox<String> teach_course1;
    public ChoiceBox<String> teach_course2;
    public ChoiceBox<String> teach_position;
    public Label operationStatus;
    public Button teach_genIDBtn;
    public Button teach_deleteBtn;
    public Button teach_clearBtn;
    public Button teach_updateBtn;
    public Button teach_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teach_gender.setItems(Model.getInstance().getConnectDB().getGender());
        teach_deptID.setItems(Model.getInstance().getConnectDB().getDepartments());
        teach_course1.setItems(Model.getInstance().getConnectDB().getCourses());
        teach_course2.setItems(Model.getInstance().getConnectDB().getCourses());
        teach_position.setItems(Model.getInstance().getConnectDB().getPosition());

        teach_addBtn.setOnAction(e -> createTeacher());
        teach_updateBtn.setOnAction(e -> updateTeacher());
        teach_deleteBtn.setOnAction(e -> deleteTeacher());
        teach_clearBtn.setOnAction(e -> clearFields());
        teach_genIDBtn.setOnAction(e -> generateTeacherID());

        // Teacher tableview section
        initialiseTeachersTable();
        bindTeachersTableData();
        teach_tableView.setItems(Model.getInstance().setTeachers());
        teach_tableView.setOnMouseClicked(e -> selectTeacher());
        searchTeacher();
    }

    private void searchTeacher() {
        FilteredList<Teacher> searchFilter = new FilteredList<>(Model.getInstance().setTeachers(), e -> true);
        teach_searchField.textProperty().addListener(((observableValue, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateTeacher -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String searchKey = newVal.toLowerCase();

                if(predicateTeacher.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
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
        String gender = teach_gender.getValue();
        String phoneNumber = teach_phoneNum.getText();
        String email = teach_email.getText();
        String departmentID = teach_deptID.getValue();
        LocalDate dob = LocalDate.parse(teach_dob.getValue().toString());
        String password = teach_pwd.getText();
        String course1 = teach_course1.getValue();
        String course2 = teach_course2.getValue();
        String position = teach_position.getValue();

        if(teacherID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || position.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please fill required fields.");

        } else {
            Model.getInstance().getConnectDB().createTeacher(teacherID, firstName, lastName, gender, phoneNumber, email, departmentID, dob,password, course1, course2, position);
            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
            operationStatus.setText("Teacher successfully added.");

            teach_tableView.setItems(Model.getInstance().setTeachers());
            clearFields();
        }
    }
    private void updateTeacher() {
        String teacherID = teach_teacherID.getText();
        String firstName = teach_fName.getText();
        String lastName = teach_lName.getText();
        String gender = teach_gender.getValue();
        String phoneNumber = teach_phoneNum.getText();
        String email = teach_email.getText();
        String departmentID = teach_deptID.getValue();
        LocalDate dob = LocalDate.parse(teach_dob.getValue().toString());
        String course1 = teach_course1.getValue();
        String course2 = teach_course2.getValue();
        String position = teach_position.getValue();

        Model.getInstance().getConnectDB().updateTeacher(teacherID, firstName, lastName, gender, phoneNumber, email, departmentID, dob, course1, course2, position);
        operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
        operationStatus.setText("Teacher updated successfully.");

        teach_tableView.setItems(Model.getInstance().setTeachers());
        clearFields();
    }
    private void deleteTeacher() {
        String teacherID = teach_teacherID.getText();
        Model.getInstance().getConnectDB().deleteTeacher(teacherID);
        operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
        operationStatus.setText("Teacher deleted successfully.");
        teach_tableView.setItems(Model.getInstance().setTeachers());
        clearFields();
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
        if (Model.getInstance().getTeachers().isEmpty()) {
            Model.getInstance().setTeachers();
        }
    }
    private void bindTeachersTableData() {
        teach_tableView_col_teacherID.setCellValueFactory(cellData -> cellData.getValue().teacherIDProperty());
        teach_tableView_col_fName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        teach_tableView_col_lName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        teach_tableView_col_phoneNum.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        teach_tableView_col_email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        teach_tableView_col_gender.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        teach_tableView_col_department.setCellValueFactory(cellData -> cellData.getValue().departmentIDProperty());
        teach_tableView_col_course1.setCellValueFactory(cellData -> cellData.getValue().course1Property());
        teach_tableView_col_course2.setCellValueFactory(cellData -> cellData.getValue().course2Property());
        teach_tableView_col_position.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        teach_tableView_col_dob.setCellValueFactory(cellData -> cellData.getValue().dobProperty());
    }
    private void generateTeacherID() {
        String rowCount = String.valueOf(Model.getInstance().getConnectDB().getTeacherRowCount()) + 1;
        String prefix = "SE";
        String year = String.valueOf(LocalDate.now().getYear());
        teach_teacherID.setText(prefix + year + rowCount);
    }

    private void selectTeacher() {
        Teacher teacher = teach_tableView.getSelectionModel().getSelectedItem();
        int num = teach_tableView.getSelectionModel().getSelectedIndex();
        if((num -1)  < -1) return;
        teach_teacherID.setText(String.valueOf(teacher.teacherIDProperty().get()));
        teach_fName.setText(String.valueOf(teacher.firstNameProperty().get()));
        teach_lName.setText(String.valueOf(teacher.lastNameProperty().get()));
        teach_gender.setValue(String.valueOf(teacher.genderProperty().get()));
        teach_phoneNum.setText(String.valueOf(teacher.phoneNumberProperty().get()));
        teach_email.setText(String.valueOf(teacher.emailProperty().get()));
        teach_deptID.setValue(String.valueOf(teacher.departmentIDProperty().get()));
        teach_dob.setValue(LocalDate.parse(String.valueOf(teacher.dobProperty().get())));
        teach_course1.setValue(String.valueOf(teacher.course1Property().get()));
        teach_course2.setValue(String.valueOf(teacher.course2Property().get()));
        teach_position.setValue(String.valueOf(teacher.positionProperty().get()));
    }
}
