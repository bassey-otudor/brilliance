package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {

    public TextField course_searchField;
    public ChoiceBox course_filterFaculty;
    public ChoiceBox course_filterDept;
    public ChoiceBox course_filterLevel;
    public TableView course_tableView;
    public TableColumn course_tableView_col_courseID;
    public TableColumn course_tableView_col_courseName;
    public TableColumn course_tableView_col_level;
    public TableColumn course_tableView_col_dept;
    public TableColumn course_tableView_col_teacher;
    public TextField course_courseName;
    public TextField course_courseID;
    public ChoiceBox course_level;
    public ChoiceBox course_dept;
    public ChoiceBox course_teacher;
    public Button course_genIDBtn;
    public Button course_deleteBtn;
    public Button course_clearBtn;
    public Button course_updateBtn;
    public Button course_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
