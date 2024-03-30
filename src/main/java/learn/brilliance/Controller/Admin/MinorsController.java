package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Minor;

import java.net.URL;
import java.util.ResourceBundle;

public class MinorsController implements Initializable {

    public TextField minor_searchField;
    public TableView<Minor> minor_tableView;
    public TableColumn <Minor, String> minor_tableView_col_minorID;
    public TableColumn <Minor, String> minor_tableView_col_minorName;
    public TableColumn <Minor, String> minor_tableView_col_degree;
    public TableColumn <Minor, String> minor_tableView_col_course1ID;
    public TableColumn <Minor, String> minor_tableView_col_course2ID;
    public TableColumn <Minor, String> minor_tableView_col_course3ID;
    public TableColumn <Minor, String> minor_tableView_col_course4ID;
    public TableColumn<Minor, String>  minor_tableView_col_course5ID;
    public TextField minor_minorID;
    public ComboBox<String> minor_facultyID;
    public TextField minor_minorName;
    public ComboBox<String> minor_departmentID;
    public ComboBox<String> minor_courseID;
    public ComboBox<String> minor_degreeID;
    public ComboBox<String> minor_courseNumber;
    public Button minor_genIDBtn;
    public Button minor_deleteBtn;
    public Button minor_clearBtn;
    public Button minor_updateBtn;
    public Button minor_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
