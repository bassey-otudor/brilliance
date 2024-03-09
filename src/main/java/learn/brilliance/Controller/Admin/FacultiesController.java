package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FacultiesController implements Initializable {
    public TextField faculty_searchField;
    public TableView faculty_tableView;
    public TableColumn faculty_tableView_col_facID;
    public TableColumn faculty_tableView_col_facName;
    public TableColumn faculty_tableView_col_facDirector;
    public TableColumn faculty_tableView_col_facDept1;
    public TableColumn faculty_tableView_col_facDept2;
    public TableColumn faculty_tableView_col_facDept3;
    public TableColumn faculty_tableView_col_facDept4;
    public TextField faculty_facName;
    public ChoiceBox faculty_director;
    public Button faculty_deleteBtn;
    public Button faculty_clearBtn;
    public Button faculty_updateBtn;
    public Button faculty_addBtn;
    public TextField faculty_facID;
    public TextField faculty_dept1;
    public TextField faculty_dept2;
    public TextField faculty_dept3;
    public TextField faculty_dept4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
