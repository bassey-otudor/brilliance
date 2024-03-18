package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Faculty;
import learn.brilliance.Model.Model;

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
    public ChoiceBox<String> dept_faculty;
    public ChoiceBox<String> dept_minor2;
    public ChoiceBox<String> dept_hod;
    public ChoiceBox<String> dept_minor1;
    public Button dept_deleteBtn;
    public Button dept_clearBtn;
    public Button dept_updateBtn;
    public Button dept_addBtn;
    public TextField dept_deptID;
    public Label operationStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dept_faculty.setItems(Model.getInstance().getConnectDB().getFaculties());
        dept_hod.setItems(Model.getInstance().getConnectDB().getHod());
        dept_minor1.setItems(Model.getInstance().getConnectDB().getMinor1());
        dept_minor2.setItems(Model.getInstance().getConnectDB().getMinor2());
        dept_addBtn.setOnAction(e -> createDepartment());
        dept_updateBtn.setOnAction(e -> updateDepartment());
        dept_deleteBtn.setOnAction(e -> deleteDepartment());
        dept_clearBtn.setOnAction(e -> clearFields());
    }

    private void createDepartment() {
        String departmentID = dept_deptID.getId();
        String departmentName = dept_deptName.getText();
        String facultyID = dept_faculty.getSelectionModel().toString();
        String hod = dept_hod.getSelectionModel().toString();
        String minor1 = dept_minor1.getSelectionModel().toString();
        String minor2 = dept_minor2.getSelectionModel().toString();

        if(departmentID.isEmpty() || departmentName.isEmpty() || facultyID.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please fill required fields.");

        } else {
            Model.getInstance().getConnectDB().createDepartment(departmentID, departmentName, facultyID, hod, minor1, minor2);
            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
            operationStatus.setText("Department successfully added.");
            clearFields();
        }
    }
    private void updateDepartment() {
        String departmentID = dept_deptID.getText();
        String departmentName = dept_deptName.getText();
        String facultyID = dept_faculty.getSelectionModel().toString();
        String hod = dept_hod.getSelectionModel().toString();
        String minor1 = dept_minor1.getSelectionModel().toString();
        String minor2 = dept_minor2.getSelectionModel().toString();

        if(departmentID.isEmpty() || departmentName.isEmpty() || facultyID.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please fill required fields.");

        } else {
            Model.getInstance().getConnectDB().updateDepartment(departmentID, departmentName, facultyID, hod, minor1, minor2);
            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
            operationStatus.setText("Department successfully updated.");
            clearFields();
        }

    }
    private void deleteDepartment() {
        String departmentID = dept_deptID.getText();
        if(departmentID.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please enter a department ID.");
        } else {
            Model.getInstance().getConnectDB().deleteDepartment(departmentID);
            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
            operationStatus.setText("Department successfully deleted.");
            clearFields();
        }
    }
    private void clearFields() {
        dept_deptID.setText("");
        dept_deptName.setText("");
        dept_faculty.setValue("");
        dept_hod.setValue("");
        dept_minor1.setValue("");
        dept_minor2.setValue("");
    }
}
