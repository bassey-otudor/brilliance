package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Faculty;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;


public class FacultiesController implements Initializable {

    private final String idColumn = "facultyID";
    private final String tableName = "faculties";
    private final String columnName = "facultyName";
    public TextField faculty_searchField;
    public TableView<Faculty> faculty_tableView;
    public TableColumn<Faculty, String> faculty_tableView_col_facID;
    public TableColumn<Faculty, String> faculty_tableView_col_facName;
    public TableColumn<Faculty, String> faculty_tableView_col_facDirector;
    public TableColumn<Faculty, String> faculty_tableView_col_facDept1;
    public TableColumn<Faculty, String> faculty_tableView_col_facDept2;
    public TableColumn<Faculty, String> faculty_tableView_col_facDept3;
    public TextField faculty_facName;
    public TextField faculty_facID;
    public ComboBox<String> faculty_director;
    public TextField faculty_dept1;
    public TextField faculty_dept2;
    public TextField faculty_dept3;
    public Button faculty_deleteBtn;
    public Button faculty_clearBtn;
    public Button faculty_updateBtn;
    public Button faculty_addBtn;
    public Label operationStatus;
    public TextField faculty_dept1ID;
    public TextField faculty_dept2ID;
    public TextField faculty_dept3ID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Faculty manipulation section
        faculty_director.setItems(Model.getInstance().getConnectDB().getDirectors());
        faculty_addBtn.setOnAction(e -> createFaculty());
        faculty_updateBtn.setOnAction(e -> updateFaculty());
        faculty_deleteBtn.setOnAction(e -> deleteFaculty());
        faculty_clearBtn.setOnAction(e -> clearFields());

        // Faculty tableView section
        initialiseFacultiesTable();
        bindFacultyTableData();
        faculty_tableView.setItems(Model.getInstance().setAllFaculties());
        faculty_tableView.setOnMouseClicked(e -> selectFaculties());
        searchFaculties();
    }

    // search the faculty table
    private void searchFaculties() {
        FilteredList<Faculty> searchFilter = new FilteredList<>(Model.getInstance().setAllFaculties(), e -> true);
        faculty_searchField.textProperty().addListener(((observableValue, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateFaculty -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String searchKey = newVal.toLowerCase();

                if(predicateFaculty.facultyIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.facultyNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.directorProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.department1Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.department2Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFaculty.department3Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

            SortedList<Faculty> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(faculty_tableView.comparatorProperty());
            faculty_tableView.setItems(sortedList);
        }));
    }
    private void createFaculty() {
        String facultyName = faculty_facName.getText();
        String facultyID = faculty_facID.getText().toUpperCase();
        String facultyDirector = faculty_director.getValue();
        String department1 = faculty_dept1.getText();
        String department2 = faculty_dept2.getText();
        String department3 = faculty_dept3.getText();
        String department1ID = faculty_dept1ID.getText().toUpperCase();
        String department2ID = faculty_dept2ID.getText().toUpperCase();
        String department3ID = faculty_dept3ID.getText().toUpperCase();

        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, facultyID, columnName, facultyName);

        try {

            if(facultyID.isEmpty() || facultyName.isEmpty()) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Please fill required fields.");

            } else {

                if(doesExist) {
                    operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                    operationStatus.setText("This faculty already exists.");

                } else {

                    if(!department1ID.isEmpty() && department2ID.isEmpty() && department3ID.isEmpty()) {
                        Model.getInstance().getConnectDB().createDepartment(department1ID, department1, facultyID, "NULL", "NULL", "NULL");

                    } else if(!department1ID.isEmpty() && !department2ID.isEmpty() && department3.isEmpty()) {
                        Model.getInstance().getConnectDB().createDepartment(department1ID, department1, facultyID, "NULL", "NULL", "NULL");
                        Model.getInstance().getConnectDB().createDepartment(department2ID, department2, facultyID, "NULL", "NULL", "NULL");

                    } else if(!department1ID.isEmpty() && !department2ID.isEmpty()) {
                        Model.getInstance().getConnectDB().createDepartment(department1ID, department1, facultyID, "NULL", "NULL", "NULL");
                        Model.getInstance().getConnectDB().createDepartment(department2ID, department2, facultyID, "NULL", "NULL", "NULL");
                        Model.getInstance().getConnectDB().createDepartment(department3ID, department3, facultyID, "NULL", "NULL", "NULL");
                    }

                    Model.getInstance().getConnectDB().createFaculty(facultyID, facultyName, facultyDirector, department1, department2, department3);
                    operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em;");
                    operationStatus.setText("Added faculty successfully.");

                    // Update faculty table
                    faculty_tableView.setItems(Model.getInstance().setAllFaculties());
                    // Update departments table
                    // dept_tableView.setItems(Model.getInstance().setDepartments());

                    clearFields();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    private void updateFaculty() {
        String facultyName = faculty_facName.getText();
        String facultyID = faculty_facID.getText().toUpperCase();
        String facultyDirector = faculty_director.getValue();
        String department1 = faculty_dept1.getText().toUpperCase();
        String department2 = faculty_dept2.getText().toUpperCase();
        String department3 = faculty_dept3.getText().toUpperCase();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, facultyID, columnName, facultyName);

        if (facultyID.isEmpty() || facultyName.isEmpty() || facultyDirector.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please fill all fields.");

        } else {

            if(doesExist) {

                Model.getInstance().getConnectDB().updateFaculty(facultyID, facultyName, facultyDirector);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em;");
                operationStatus.setText("Updated faculty successfully.");
                faculty_tableView.setItems(Model.getInstance().setAllFaculties());
                clearFields();

            } else {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Faculty not found.");
            }
        }
    }
    private void deleteFaculty() {
        String facultyID = faculty_facID.getText().toUpperCase();
        String facultyName = faculty_facName.getText();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, facultyID, columnName, facultyName);

        if(facultyID.isEmpty()) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please enter a faculty ID.");
        } else {
            Model.getInstance().getConnectDB().deleteFaculty(facultyID);
            Model.getInstance().getConnectDB().deleteFacultyDepartments(facultyID);

            operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em;");
            operationStatus.setText("Deleted faculty successfully.");
            clearFields();
            faculty_tableView.setItems(Model.getInstance().setAllFaculties());
        }
    }

    /**
 * Clears all text fields in the faculty form.
 */
    private void clearFields() {
    faculty_facName.setText(null);
    faculty_facID.setText(null);
    faculty_director.setValue(null);
    faculty_dept1.setText(null);
    faculty_dept2.setText(null);
    faculty_dept3.setText(null);
    faculty_dept1ID.setText(null);
    faculty_dept2ID.setText(null);
    faculty_dept3ID.setText(null);
}

    /**
 * Initializes the faculty table view by populating it with data from the database.
 */
    private void initialiseFacultiesTable() {
        if (Model.getInstance().getAllFaculties().isEmpty()) {
            Model.getInstance().setAllFaculties();
        }
    }

    private void bindFacultyTableData() {
        faculty_tableView_col_facID.setCellValueFactory(cellData -> cellData.getValue().facultyIDProperty());
        faculty_tableView_col_facName.setCellValueFactory(cellDate -> cellDate.getValue().facultyNameProperty());
        faculty_tableView_col_facDirector.setCellValueFactory(cellData -> cellData.getValue().directorProperty());
        faculty_tableView_col_facDept1.setCellValueFactory(cellData -> cellData.getValue().department1Property());
        faculty_tableView_col_facDept2.setCellValueFactory(cellData -> cellData.getValue().department2Property());
        faculty_tableView_col_facDept3.setCellValueFactory(cellData -> cellData.getValue().department3Property());
    }

    /**
     * Selects the currently selected faculty in the faculty table.
     */
    private void selectFaculties() {
        Faculty faculties = faculty_tableView.getSelectionModel().getSelectedItem();
        int num = faculty_tableView.getSelectionModel().getSelectedIndex();
        if((num -1)  < -1) return;
        faculty_facID.setText(String.valueOf(faculties.facultyIDProperty().get()));
        faculty_facName.setText(String.valueOf(faculties.facultyNameProperty().get()));
        faculty_director.setValue(String.valueOf(faculties.directorProperty().get()));
        faculty_dept1.setText(String.valueOf(faculties.department1Property().get()));
        faculty_dept2.setText(String.valueOf(faculties.department2Property().get()));
        faculty_dept3.setText(String.valueOf(faculties.department3Property().get()));
    }
}
