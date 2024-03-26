package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DegreesController implements Initializable {
    public TextField degree_searchField;
    public ComboBox degree_filterDept;
    public ComboBox degree_filterDuration;
    public TableView degree_tableView;
    public TableColumn degree_tableView_col_degreeID;
    public TableColumn degree_tableView_col_degreeName;
    public TableColumn degree_tableView_col_deptID;
    public TableColumn degree_tableView_col_duration;
    public TableColumn degree_tableView_col_numCourses;
    public TableColumn degree_tableView_col_totalCredits;
    public TableColumn degree_tableView_col_requiredCredits;
    public TextField degree_degreeName;
    public TextField degree_degreeID;
    public ComboBox degree_deptID;
    public ComboBox degree_duration;
    public ComboBox degree_numCourses;
    public ComboBox degree_totalCredits;
    public ComboBox degree_requiredCredits;
    public Button degree_genIDBtn;
    public Button degree_deleteBtn;
    public Button degree_clearBtn;
    public Button degree_updateBtn;
    public Button degree_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
