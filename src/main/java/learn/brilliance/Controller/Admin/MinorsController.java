package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MinorsController implements Initializable {

    public TextField minor_searchField;
    public ChoiceBox minor_filterDegree;
    public TableView minor_tableView;
    public TableColumn minor_tableView_col_minorID;
    public TableColumn minor_tableView_col_courseID;
    public TableColumn minor_tableView_col_minorName;
    public TableColumn minor_tableView_col_degree;
    public TextField minor_minorID;
    public ChoiceBox minor_courseID;
    public ChoiceBox minor_minorName;
    public ChoiceBox minor_degree;
    public Button minor_genIDBtn;
    public Button minor_deleteBtn;
    public Button minor_clearBtn;
    public Button minor_updateBtn;
    public Button minor_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
