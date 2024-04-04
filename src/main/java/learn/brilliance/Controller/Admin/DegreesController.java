package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Degree;
import learn.brilliance.Model.Model;

import org.apache.commons.lang3.StringUtils;
import java.net.URL;
import java.util.ResourceBundle;

public class DegreesController implements Initializable {

    private final static String tableName = "department_degrees";
    private final static String idColumn = "degreeID";
    private final static String columnName = "degreeName";
    public TextField degree_searchField;
    public ComboBox<String> degree_filterFaculty;
    public ComboBox<String> degree_filterDegree;
    public TableView<Degree> degree_tableView;
    public TableColumn<Degree, String> degree_tableView_col_degreeID;
    public TableColumn<Degree, String> degree_tableView_col_degreeName;
    public TableColumn<Degree, String> degree_tableView_col_deptID;
    public TableColumn<Degree, String> degree_tableView_col_minor;
    public TableColumn<Degree, String> degree_tableView_col_duration;
    public TableColumn<Degree, String> degree_tableView_col_numCourses;
    public TableColumn<Degree, String> degree_tableView_col_totalCredits;
    public TableColumn<Degree, String> degree_tableView_col_requiredCredits;
    public TableColumn<Degree, String> degree_tableView_col_facultyID;
    public TextField degree_degreeName;
    public TextField degree_degreeID;
    public ComboBox<String> degree_facultyID;
    public ComboBox<String> degree_duration;
    public TextField degree_numCourses;
    public TextField degree_totalCredits;
    public TextField degree_requiredCredits;
    public Button degree_genIDBtn;
    public Button degree_deleteBtn;
    public Button degree_clearBtn;
    public Button degree_updateBtn;
    public Button degree_addBtn;
    public ComboBox<String> degree_deptID;
    public ComboBox<String> degree_minor;
    public Label operationStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        degree_facultyID.setItems(Model.getInstance().getConnectDB().getFaculties());
        degree_facultyID.getSelectionModel().selectedItemProperty().addListener((observable,  oldVal, newVal)
                -> degree_deptID.setItems(Model.getInstance().getConnectDB().getFacultyDepartments(newVal)));
        degree_deptID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> degree_minor.setItems(Model.getInstance().getConnectDB().getDepartmentMinors(newValue)));
        degree_duration.setItems(Model.getInstance().getConnectDB().getDuration());

        degree_genIDBtn.setOnAction(e -> generateDegreeID());
        degree_addBtn.setOnAction(e -> createDegree());
        degree_updateBtn.setOnAction(e -> updateDegree());
        degree_deleteBtn.setOnAction(e -> deleteDegree());
        degree_clearBtn.setOnAction(e ->clearFields());

        // TableView section
        initialiseDegreeTable();
        bindDegreeTableData();
        degree_tableView.setItems(Model.getInstance().setAllDegrees());
        degree_tableView.setOnMouseClicked(e -> selectDegree());
        searchDegrees();

    }

    private void createDegree() {
        String degreeID = degree_degreeID.getText();
        String degreeName = degree_degreeName.getText();
        String minor = degree_minor.getValue();
        String duration = degree_duration.getValue();
        String numberOfDegree = degree_numCourses.getText();
        String totalCredits = degree_totalCredits.getText();
        String requiredCredit = degree_requiredCredits.getText();
        String facultyID = degree_facultyID.getValue();
        String departmentID = degree_deptID.getValue();
        String degreeMinor = degree_minor.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, degreeID, columnName, degreeName);

        if(degreeID == null || degreeName == null || degreeMinor == null || duration == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1em");
            operationStatus.setText("Please fill all required fields.");

        } else {

            if(doesExist) {
                operationStatus.setText("Degree already exist.");
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1em" );

            } else {

                Model.getInstance().getConnectDB().createDegree(degreeID, degreeName, departmentID, minor, duration, numberOfDegree, totalCredits, requiredCredit, facultyID);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em");
                operationStatus.setText("Degree successfully added.");
                degree_tableView.setItems(Model.getInstance().setAllDegrees());
                clearFields();
            }
        }
    }

    private void updateDegree() {
        String degreeID = degree_degreeID.getText();
        String degreeName = degree_degreeName.getText();
        String minor = degree_minor.getValue();
        String duration = degree_duration.getValue();
        String numberOfCourses = degree_numCourses.getText();
        String totalCredits = degree_totalCredits.getText();
        String requiredCredit = degree_requiredCredits.getText();
        String facultyID = degree_facultyID.getValue();
        String departmentID = degree_deptID.getValue();
        String degreeMinor = degree_minor.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, degreeID, columnName, degreeName);

        if(degreeID == null || degreeName == null || degreeMinor == null || duration == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1em");
            operationStatus.setText("Please fill all required fields.");
        } else {

            if(doesExist) {
                Model.getInstance().getConnectDB().updateDegree(degreeID, degreeName, departmentID, minor, duration, numberOfCourses, totalCredits, requiredCredit, facultyID);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em");
                operationStatus.setText("Degree updated successfully.");
                degree_tableView.setItems(Model.getInstance().setAllDegrees());

            } else {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1em");
                operationStatus.setText("Degree not found.");
            }
        }
    }
    private void deleteDegree() {
        String degreeID = degree_degreeID.getText();
        String degreeName = degree_degreeName.getText();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, degreeID, columnName, degreeName);

        if(degreeID == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1em");
            operationStatus.setText("Enter a Degree ID.");
            degree_degreeID.setStyle("-fx-border-color: #EC6666");

        } else {
            if(doesExist) {
                Model.getInstance().getConnectDB().deleteDegree(degreeID);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1em");
                operationStatus.setText("Degree deleted successfully.");
                degree_tableView.setItems(Model.getInstance().setAllDegrees());
                clearFields();

            } else {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1em");
                operationStatus.setText("Degree not found.");
                degree_degreeID.setStyle("-fx-border-color: #EC6666");
            }
        }
    }
    private void clearFields() {
        degree_degreeID.setText(null);
        degree_degreeName.setText(null);
        degree_facultyID.setValue(null);
        degree_deptID.setValue(null);
        degree_duration.setValue(null);
        degree_numCourses.setText(null);
        degree_totalCredits.setText(null);
        degree_minor.setValue(null);


    }

    private void initialiseDegreeTable() {
        if(Model.getInstance().getAllDegrees().isEmpty()) {
            Model.getInstance().setAllDegrees();
        }
    }
    private void bindDegreeTableData() {
        degree_tableView_col_degreeID.setCellValueFactory(cellData -> cellData.getValue().degreeIDProperty());
        degree_tableView_col_degreeName.setCellValueFactory(cellData -> cellData.getValue().degreeNameProperty());
        degree_tableView_col_deptID.setCellValueFactory(cellData -> cellData.getValue().departmentIDProperty());
        degree_tableView_col_minor.setCellValueFactory(cellData -> cellData.getValue().minorProperty());
        degree_tableView_col_duration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
        degree_tableView_col_numCourses.setCellValueFactory(cellData -> cellData.getValue().numberOfCoursesProperty());
        degree_tableView_col_totalCredits.setCellValueFactory(cellData -> cellData.getValue().totalCreditsProperty());
        degree_tableView_col_requiredCredits.setCellValueFactory(cellData -> cellData.getValue().requiredCreditsProperty());
        degree_tableView_col_facultyID.setCellValueFactory(cellData -> cellData.getValue().facultyIDProperty());
    }
    private void generateDegreeID() {
        String departmentID = degree_deptID.getValue();
        String rowCount = String.valueOf(Model.getInstance().getConnectDB().getDegreeRowCount(departmentID) + 1);
        String degreeCode =  StringUtils.left(departmentID, 2);
        String generatedID;

        if(rowCount.length() == 1) {
            generatedID = departmentID + "-" + degreeCode + "X00" + rowCount;

        } else if (rowCount.length() == 2) {
            generatedID = departmentID + "-" + degreeCode + "X0" + rowCount;

        } else {
            generatedID = departmentID + "-" + degreeCode + "X" + rowCount;
        }

        degree_degreeID.setText(generatedID);
    }
    private void selectDegree() {
        Degree degrees = degree_tableView.getSelectionModel().getSelectedItem();
        int num = degree_tableView.getSelectionModel().getSelectedIndex();
        if((num-1) < -1) return;
        degree_degreeID.setText(String.valueOf(degrees.degreeIDProperty().get()));
        degree_degreeName.setText(String.valueOf(degrees.degreeNameProperty().get()));
        degree_deptID.setValue(String.valueOf(degrees.departmentIDProperty().get()));
        degree_minor.setValue(String.valueOf(degrees.minorProperty().get()));
        degree_duration.setValue(String.valueOf(degrees.durationProperty().get()));
        degree_numCourses.setText(String.valueOf(degrees.numberOfCoursesProperty().get()));
        degree_totalCredits.setText(String.valueOf(degrees.totalCreditsProperty().get()));
        degree_requiredCredits.setText(String.valueOf(degrees.requiredCreditsProperty().get()));
    }
    private void searchDegrees() {
        FilteredList<Degree> searchFilter = new FilteredList<>(Model.getInstance().setAllDegrees(), e -> true);
        degree_searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFilter.setPredicate(predicateDegree -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if(predicateDegree.degreeIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDegree.degreeNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDegree.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDegree.minorProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDegree.durationProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDegree.numberOfCoursesProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDegree.totalCreditsProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDegree.requiredCreditsProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else return predicateDegree.facultyIDProperty().toString().toLowerCase().contains(searchKey);

            });

            SortedList<Degree> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(degree_tableView.comparatorProperty());
            degree_tableView.setItems(sortedList);
        });
    }
}
