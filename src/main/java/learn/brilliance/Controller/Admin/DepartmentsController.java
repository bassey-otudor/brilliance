package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentsController implements Initializable {
    public TextField dept_searchField;
    public ChoiceBox dept_filterDept;
    public TableView dept_tableView;
    public TableColumn dept_tableView_col_deptID;
    public TableColumn dept_tableView_col_deptName;
    public TableColumn dept_tableView_col_faculty;
    public TableColumn dept_tableView_col_hod;
    public TableColumn dept_tableView_col_minor1;
    public TableColumn dept_tableView_col_minor2;
    public TextField dept_deptName;
    public ChoiceBox dept_faculty;
    public ChoiceBox dept_minor2;
    public ChoiceBox dept_hod;
    public ChoiceBox dept_minor1;
    public Button dept_deleteBtn;
    public Button dept_clearBtn;
    public Button dept_updateBtn;
    public Button dept_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
