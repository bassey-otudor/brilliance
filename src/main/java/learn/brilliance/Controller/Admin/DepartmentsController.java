package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Department;
import learn.brilliance.Model.Faculty;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DepartmentsController implements Initializable {
    public TextField dept_searchField;
    public ChoiceBox dept_filterDept;
    public TableView<Department> dept_tableView;
    public TableView<Faculty> faculty_tableView;
    public TableColumn<Department, String> dept_tableView_col_deptID;
    public TableColumn<Department, String> dept_tableView_col_deptName;
    public TableColumn<Department, String> dept_tableView_col_faculty;
    public TableColumn<Department, String> dept_tableView_col_hod;
    public TableColumn<Department, String> dept_tableView_col_minor1;
    public TableColumn<Department, String> dept_tableView_col_minor2;
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

        // Department tableview section
        initialiseDepartmentsTable();
        bindDepartmentsTableData();
        dept_tableView.setItems(Model.getInstance().setDepartments());
        dept_tableView.setOnMouseClicked(e -> selectDepartments());
        searchDepartments();
    }

    private void searchDepartments() {
        FilteredList<Department> searchFilter = new FilteredList<>(Model.getInstance().setDepartments(), e -> true);
        dept_searchField.textProperty().addListener(((observableValue, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateFaculty -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String searchKey = newVal.toLowerCase();

                if(predicateFaculty.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.departmentNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.facultyIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.hodProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.minor1Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.minor2Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

            SortedList<Department> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(dept_tableView.comparatorProperty());
            dept_tableView.setItems(sortedList);
        }));
    }
    private void createDepartment() {
        String departmentID = dept_deptID.getText().toUpperCase();
        String departmentName = dept_deptName.getText();
        String facultyID = dept_faculty.getValue().toUpperCase();
        String hod = dept_hod.getValue();
        String minor1 = dept_minor1.getValue();
        String minor2 = dept_minor2.getValue();

        try {

            if(departmentID.isEmpty() || departmentName.isEmpty() || facultyID.isEmpty()) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                ResultSet resultSet = Model.getInstance().getConnectDB().checkEmptyDepartmentsColumns(facultyID);
                while (resultSet.next()) {
                    if(resultSet.getString("Department1").isEmpty()) {
                        String column = "Department1";
                        Model.getInstance().getConnectDB().insertDepartmentInFaculty(column, departmentName, facultyID);

                    } else if (resultSet.getString("Department2").isEmpty()){
                        String column = "Department2";
                        Model.getInstance().getConnectDB().insertDepartmentInFaculty(column, departmentName, facultyID);

                    } else if (resultSet.getString("Department3").isEmpty()) {
                        String column = "Department3";
                        Model.getInstance().getConnectDB().insertDepartmentInFaculty(column, departmentName, facultyID);
                    }
                }

                Model.getInstance().getConnectDB().createDepartment(departmentID, departmentName, facultyID, hod, minor1, minor2);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                operationStatus.setText("Department successfully added.");
                dept_tableView.setItems(Model.getInstance().setDepartments());
                faculty_tableView.setItems(Model.getInstance().setFaculties());
                clearFields();
            }
        } catch (SQLException e) {
            System.out.println("Unable to create department.");
            e.printStackTrace();
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
            dept_tableView.setItems(Model.getInstance().setDepartments());
            faculty_tableView.setItems(Model.getInstance().setFaculties());
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
            dept_tableView.setItems(Model.getInstance().setDepartments());
            faculty_tableView.setItems(Model.getInstance().setFaculties());
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

    private void initialiseDepartmentsTable() {
        if (Model.getInstance().getDepartments().isEmpty()) {
            Model.getInstance().setDepartments();
        }
    }
    private void bindDepartmentsTableData() {
        dept_tableView_col_deptID.setCellValueFactory(cellData -> cellData.getValue().departmentIDProperty());
        dept_tableView_col_deptName.setCellValueFactory(cellData -> cellData.getValue().departmentNameProperty());
        dept_tableView_col_faculty.setCellValueFactory(cellData -> cellData.getValue().facultyIDProperty());
        dept_tableView_col_hod.setCellValueFactory(cellData -> cellData.getValue().hodProperty());
        dept_tableView_col_minor1.setCellValueFactory(cellData -> cellData.getValue().minor1Property());
        dept_tableView_col_minor2.setCellValueFactory(cellData -> cellData.getValue().minor2Property());
    }
    private void selectDepartments() {
        Department departments = dept_tableView.getSelectionModel().getSelectedItem();
        int num = dept_tableView.getSelectionModel().getSelectedIndex();
        if((num -1) < -1) return;
        dept_deptID.setText(String.valueOf(departments.departmentIDProperty().get()));
        dept_deptName.setText(String.valueOf(departments.departmentNameProperty().get()));

    }
}
