package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Model;

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
    public TextField faculty_facName;
    public TextField faculty_facID;
    public ChoiceBox<String> faculty_director;
    public TextField faculty_dept1;
    public TextField faculty_dept2;
    public TextField faculty_dept3;
    public Button faculty_deleteBtn;
    public Button faculty_clearBtn;
    public Button faculty_updateBtn;
    public Button faculty_addBtn;
    public Label operationStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        faculty_director.setItems(Model.getInstance().getConnectDB().getDirectors());
        faculty_addBtn.setOnAction(e -> createFaculty());
        faculty_updateBtn.setOnAction(e -> updateFaculty());
        faculty_deleteBtn.setOnAction(e -> deleteFaculty());
        faculty_clearBtn.setOnAction(e -> clearFields());
    }
    public void createFaculty() {
        String facultyName = faculty_facName.getText();
        String facultyID = faculty_facID.getText();
        String facultyDirector = faculty_director.getValue();
        String department1 = faculty_dept1.getText();
        String department2 = faculty_dept2.getText();
        String department3 = faculty_dept3.getText();

        if (facultyID.isEmpty() || facultyName.isEmpty() || facultyDirector.isEmpty() || department1.isEmpty() || department2.isEmpty()) {
            operationStatus.setText("Please fill all fields.");
        } else {
            Model.getInstance().getConnectDB().createFaculty(facultyID, facultyName, facultyDirector, department1, department2, department3);
            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.3em; -fx-font-weight: bold");
            operationStatus.setText("Added faculty successfully.");
            clearFields();
        }
    }
    private void updateFaculty() {
        String facultyName = faculty_facName.getText();
        String facultyID = faculty_facID.getText();
        String facultyDirector = faculty_director.getValue();
        String department1 = faculty_dept1.getText();
        String department2 = faculty_dept2.getText();
        String department3 = faculty_dept3.getText();

        if (facultyID.isEmpty() || facultyName.isEmpty() || facultyDirector.isEmpty() || department1.isEmpty() || department2.isEmpty()) {
            operationStatus.setText("Please fill all fields.");
        } else {
            Model.getInstance().getConnectDB().updateFaculty(facultyID, facultyName, facultyDirector, department1, department2, department3);
            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.3em; -fx-font-weight: bold");
            operationStatus.setText("Updated faculty successfully.");
            clearFields();
        }
    }
    private void deleteFaculty() {
        String facultyID = faculty_facID.getText();

        if(facultyID.isEmpty()) {
            operationStatus.setText("Please enter a faculty ID.");
        } else {
            Model.getInstance().getConnectDB().deleteFaculty(facultyID);
            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.3em; -fx-font-weight: bold");
            operationStatus.setText("Deleted faculty successfully.");
            clearFields();
        }
    }
    private void clearFields() {
        faculty_facName.setText("");
        faculty_facID.setText("");
        faculty_director.setValue("");
        faculty_dept1.setText("");
        faculty_dept2.setText("");
        faculty_dept3.setText("");
    }
}
