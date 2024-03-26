package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {
    public TextField stud_searchField;
    public ComboBox stud_filterDept;
    public ComboBox stud_filterCourse;
    public TableView stud_tableView;
    public TableColumn stud_tableView_col_studentID;
    public TableColumn stud_tableView_col_fName;
    public TableColumn stud_tableView_col_lName;
    public TableColumn stud_tableView_col_phoneNum;
    public TableColumn stud_tableView_col_email;
    public TableColumn stud_tableView_col_gender;
    public TableColumn stud_tableView_col_dept;
    public TableColumn stud_tableView_col_degree;
    public TableColumn stud_tableView_col_minor;
    public TableColumn stud_tableView_col_level;
    public TextField stud_fName;
    public TextField stud_lName;
    public TextField stud_email;
    public TextField stud_phoneNum;
    public DatePicker stud_dob;
    public ComboBox stud_gender;
    public ComboBox stud_department;
    public ComboBox stud_degree;
    public ComboBox stud_minor;
    public PasswordField stud_pwd;
    public ComboBox stud_level;
    public TextField stud_studentID;
    public Button stud_genIDBtn;
    public Button stud_deleteBtn;
    public Button stud_clearBtn;
    public Button stud_updateBtn;
    public Button stud_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
