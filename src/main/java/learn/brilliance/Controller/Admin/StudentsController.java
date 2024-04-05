package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Accounts.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {
    public TextField stud_searchField;
    public ComboBox<String> stud_filterDept;
    public ComboBox<String> stud_filterCourse;
    public TableView<Student> stud_tableView;
    public TableColumn<Student, String> stud_tableView_col_studentID;
    public TableColumn<Student, String> stud_tableView_col_fName;
    public TableColumn<Student, String> stud_tableView_col_lName;
    public TableColumn<Student, String> stud_tableView_col_phoneNum;
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
    public TextField stud_phoneNum;
    public DatePicker stud_dob;
    public ComboBox<String> stud_gender;
    public ComboBox<String> stud_faculty;
    public ComboBox<String> stud_department;
    public ComboBox<String> stud_degree;
    public ComboBox<String> stud_minor;
    public PasswordField stud_pwd;
    public ComboBox<String> stud_level;
    public TextField stud_studentID;
    public Button stud_genIDBtn;
    public Button stud_deleteBtn;
    public Button stud_clearBtn;
    public Button stud_updateBtn;
    public Button stud_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void createStudent() {

    }
}
