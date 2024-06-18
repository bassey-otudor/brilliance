package learn.brilliance.Controller.Admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import learn.brilliance.Model.Accounts.Teacher;
import learn.brilliance.Model.Department;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentsController implements Initializable {
    public final String tableName = "departments";
    public final String idColumn = "deptID";
    public final String columnName = "deptName";

    public TextField dept_searchField;
    public ComboBox<String> dept_filterBy;
    public TableView<Department> dept_tableView;
    public TableColumn<Department, String> dept_tableView_col_deptID;
    public TableColumn<Department, String> dept_tableView_col_deptName;
    public TableColumn<Department, String> dept_tableView_col_faculty;
    public TableColumn<Department, String> dept_tableView_col_hod;
    public TableColumn<Department, String> dept_tableView_col_minor1;
    public TableColumn<Department, String> dept_tableView_col_minor2;
    public TextField dept_deptName;
    public ComboBox<String> dept_faculty;
    public ComboBox<String> dept_minor2;
    public ComboBox<String> dept_hod;
    public ComboBox<String> dept_minor1;
    public Button dept_deleteBtn;
    public Button dept_clearBtn;
    public Button dept_updateBtn;
    public Button dept_addBtn;
    public TextField dept_deptID;
    public Button dept_resetFilterBtn;
    public Label operationStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dept_filterBy.setItems(Model.getInstance().getConnectDB().getFaculties());

        dept_faculty.setItems(Model.getInstance().getConnectDB().getFaculties());
        dept_faculty.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newValue)
            -> dept_hod.setItems(Model.getInstance().getConnectDB().getHod(newValue)));

        dept_faculty.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal)
            -> dept_minor1.setItems(Model.getInstance().getConnectDB().getMinor(newVal)));

        dept_faculty.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal)
            -> dept_minor2.setItems(Model.getInstance().getConnectDB().getMinor(newVal)));

        dept_addBtn.setOnAction(e -> createDepartment());
        dept_updateBtn.setOnAction(e -> updateDepartment());
        dept_deleteBtn.setOnAction(e -> deleteDepartment());
        dept_clearBtn.setOnAction(e -> clearFields());
        dept_resetFilterBtn.setOnAction(e -> resetFilters());

        // Department tableview section
        initialiseDepartmentsTable();
        bindDepartmentsTableData();
        dept_tableView.setItems(Model.getInstance().setAllDepartments());
        dept_tableView.setOnMouseClicked(e -> selectDepartments());
        searchDepartments();
        filterTeacher();
    }


    private void searchDepartments() {
        FilteredList<Department> searchFilter = new FilteredList<>(Model.getInstance().setAllDepartments(), e -> true);
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
                } else return predicateFaculty.minor2Property().toString().toLowerCase().contains(searchKey);
            });

            SortedList<Department> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(dept_tableView.comparatorProperty());
            dept_tableView.setItems(sortedList);
        }));
    }
    private void filterTeacher() {
        FilteredList<Department> searchFilter = new FilteredList<>(Model.getInstance().setAllDepartments(), e -> true);
        dept_filterBy.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateTeacher -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String filterKey = newVal.toLowerCase();
                return predicateTeacher.facultyIDProperty().toString().toLowerCase().contains(filterKey);
            });

            SortedList<Department> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(dept_tableView.comparatorProperty());
            dept_tableView.setItems(sortedList);
        });
    }

    private void createDepartment() {
        String departmentID = dept_deptID.getText().toUpperCase();
        String departmentName = dept_deptName.getText();
        String facultyID = dept_faculty.getValue().toUpperCase();
        String hod = dept_hod.getValue();
        String minor1 = dept_minor1.getValue();
        String minor2 = dept_minor2.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(departmentID, tableName);

        try {

            if(departmentID.isEmpty() || departmentName.isEmpty() || facultyID.isEmpty() || hod.isEmpty() || minor1.isEmpty() || minor2.isEmpty()) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if(doesExist) {
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("Department already exists.");

                } else {

                    if(minor1.equals(minor2)) {
                        operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                        operationStatus.setText("Minors cannot be the same.");

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

                        // Update departments table
                        dept_tableView.setItems(Model.getInstance().setAllDepartments());
                        updateDepartmentsTable();
                        clearFields();
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("Unable to create department.");
            Logger.getLogger(DepartmentsController.class.getName()).log(Level.SEVERE, "Unable to create department", ex);
        }
    }
    private void updateDepartment() {
        String departmentID = dept_deptID.getText();
        String departmentName = dept_deptName.getText();
        String facultyID = dept_faculty.getSelectionModel().getSelectedItem();
        String hod = dept_hod.getSelectionModel().getSelectedItem();
        String minor1 = dept_minor1.getSelectionModel().getSelectedItem();
        String minor2 = dept_minor2.getSelectionModel().getSelectedItem();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(departmentID, tableName);

        if(departmentID.isEmpty() || departmentName.isEmpty() || facultyID.isEmpty() || hod.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please fill required fields.");

        } else {

            if (doesExist) {

                Model.getInstance().getConnectDB().updateDepartment(departmentID, departmentName, facultyID, hod, minor1, minor2);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                operationStatus.setText("Department successfully updated.");
                dept_tableView.setItems(Model.getInstance().setAllDepartments());
                // faculty_tableView.setItems(Model.getInstance().setFaculties());
                clearFields();
            } else {

                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Department does not exist.");
            }
        }

    }
    private void deleteDepartment() {
        String departmentID = dept_deptID.getText();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(departmentID, tableName);

        if(departmentID.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please enter a department ID.");

        } else {

            if(doesExist) {

                Model.getInstance().getConnectDB().deleteDepartment(departmentID);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                operationStatus.setText("Department successfully deleted.");
                dept_tableView.setItems(Model.getInstance().setAllDepartments());
                // faculty_tableView.setItems(Model.getInstance().setFaculties());
                clearFields();

            } else {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Department does not exist.");
            }
        }
    }
    private void updateDepartmentsTable() {  // Update courses table
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> { // keyFrame with a 4 seconds trigger
            dept_tableView.setItems(Model.getInstance().setAllDepartments());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // repeats indefinitely
        timeline.playFromStart();
    }
    private void clearFields() {
        dept_deptID.setText(null);
        dept_deptName.setText(null);
        dept_faculty.setValue(null);
        dept_hod.setValue(null);
        dept_minor1.setValue(null);
        dept_minor2.setValue(null);
    }
    private void resetFilters() {
        dept_filterBy.setValue(null);
    }

   /**
 * Initialises the departments table by populating it with data from the database.
 */
    public void initialiseDepartmentsTable() {
        if (Model.getInstance().getAllDepartments().isEmpty()) {
            Model.getInstance().setAllDepartments();
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
        dept_faculty.setValue(String.valueOf(departments.facultyIDProperty().get()));
        dept_hod.setValue(String.valueOf(departments.hodProperty().get()));
        dept_minor1.setValue(String.valueOf(departments.minor1Property().get()));
        dept_minor2.setValue(String.valueOf(departments.minor2Property().get()));


    }


}
