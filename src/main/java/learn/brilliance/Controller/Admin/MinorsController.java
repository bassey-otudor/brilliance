package learn.brilliance.Controller.Admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import learn.brilliance.Model.Minor;
import learn.brilliance.Model.Model;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class MinorsController implements Initializable {

    public final String tableName = "minors";
    public final String idColumn = "minorID";
    public final String columnName = "minorName";
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
    public TableColumn<Minor, String>  minor_tableView_col_facultyID;
    public TableColumn<Minor, String>  minor_tableView_col_departmentID;
    public TextField minor_minorID;
    public ComboBox<String> minor_facultyID;
    public TextField minor_minorName;
    public ComboBox<String> minor_departmentID;
    public ComboBox<String> minor_courseID;
    public ComboBox<String> minor_degreeID;
    public ComboBox<String> minor_courseNumber;
    public ComboBox<String> minor_number;
    public Button minor_genIDBtn;
    public Button minor_deleteBtn;
    public Button minor_clearBtn;
    public Button minor_updateBtn;
    public Button minor_addBtn;
    public Label operationStatus;
    public ComboBox<String> minor_filterBy;
    public ComboBox<String> minor_filterOptions;
    public Button minor_resetFilterBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Minor table manipulation section
        minor_filterBy.setItems(Model.getInstance().getConnectDB().generalFilterBy());
        minor_filterBy.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> filterOptions(newVal));

        minor_facultyID.setItems(Model.getInstance().getConnectDB().getFaculties());
        minor_facultyID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> minor_departmentID.setItems(Model.getInstance().getConnectDB().getFacultyDepartments(newValue)));

        minor_departmentID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> minor_courseID.setItems(Model.getInstance().getConnectDB().getDepartmentCourses(newValue)));

        minor_departmentID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> minor_degreeID.setItems(Model.getInstance().getConnectDB().getDepartmentDegrees(newValue)));

        minor_courseNumber.setItems(Model.getInstance().getConnectDB().getCourseNumber());
        minor_number.setItems(Model.getInstance().getConnectDB().getMinorNumber());
        minor_addBtn.setOnAction(e -> createMinor());
        minor_updateBtn.setOnAction(e -> updateMinor());
        minor_deleteBtn.setOnAction(e -> deleteMinor());
        minor_clearBtn.setOnAction(e -> clearFields());
        minor_genIDBtn.setOnAction(e -> generateMinorID());
        minor_resetFilterBtn.setOnAction(e -> resetFilter());
        minor_tableView.setOnMouseClicked(e -> selectMinor());

        // Minor tableView section
        initialiseMinorTable();
        bindMinorsTableData();
        minor_tableView.setItems(Model.getInstance().setAllMinors());
        updateMinorsTable();
        searchMinor();
        selectMinor();
        filterMinor();
    }

    private void createMinor() {
        String minorID = minor_minorID.getText();
        String minorName = minor_minorName.getText();
        String facultyID = minor_facultyID.getValue();
        String departmentID = minor_departmentID.getValue();
        String degreeID = minor_degreeID.getValue();
        String courseID = minor_courseID.getValue();
        String courseNumber = minor_courseNumber.getValue();
        String minorNumber = minor_number.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(minorID, tableName);

        if(minorID == null || minorName == null || facultyID == null || departmentID == null || degreeID == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
            operationStatus.setText(" Please fill all required fields.");

        } else {
            if(doesExist) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em; -fx-font-weight: bold");
                operationStatus.setText("Minor already exist.");

            } else {
                Model.getInstance().getConnectDB().createMinor(minorID, minorName, degreeID, facultyID, departmentID, courseID, courseNumber);
                Model.getInstance().getConnectDB().insertMinorIntoDepartment(minorNumber, minorName, departmentID, true);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em;  -fx-font-weight: bold");
                operationStatus.setText("Minor successfully created.");
                minor_tableView.setItems(Model.getInstance().setAllMinors());
            }
        }
    }
    private void updateMinor() {
        String minorID = minor_minorID.getText();
        String minorName = minor_minorName.getText();
        String facultyID = minor_facultyID.getValue();
        String departmentID = minor_departmentID.getValue();
        String degreeID = minor_degreeID.getValue();
        String courseID = minor_courseID.getValue();
        String courseNumber = minor_courseNumber.getValue();
        String minorNumber = minor_number.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(minorID, tableName);

        if(minorID == null || minorName == null || facultyID == null || departmentID == null || degreeID == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;  -fx-font-weight: bold");
            operationStatus.setText("Please fill all required fields.");

        } else {
            if(doesExist) {
                Model.getInstance().getConnectDB().updateMinor(minorID, minorName, degreeID, facultyID, departmentID, courseID, courseNumber);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em;  -fx-font-weight: bold");
                operationStatus.setText("Minor successfully updated.");
                Model.getInstance().getConnectDB().insertMinorIntoDepartment(minorNumber, minorName, departmentID, true);
                minor_tableView.setItems(Model.getInstance().setAllMinors());

            } else {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;  -fx-font-weight: bold");
                operationStatus.setText("Minor not found.");
            }
        }
    }
    private void deleteMinor() {
        String minorID = minor_minorID.getText();
        String minorName = minor_minorName.getText();
        String departmentID = minor_departmentID.getValue();
        String minorNumber = minor_number.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(minorID, tableName);

        if(minorID == null || minorName == null || departmentID == null || minorNumber == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;  -fx-font-weight: bold");
            operationStatus.setText(" Please fill all required fields.");

        } else {
            if(doesExist) {
                Model.getInstance().getConnectDB().deleteMinor(minorID);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em;  -fx-font-weight: bold");
                operationStatus.setText("Minor successfully deleted.");
                minor_tableView.setItems(Model.getInstance().setAllMinors());
                Model.getInstance().getConnectDB().insertMinorIntoDepartment(minorNumber, minorName,departmentID, false);
                clearFields();

            } else {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;  -fx-font-weight: bold");
                operationStatus.setText("Minor not found.");
            }
        }
    }

    private void updateMinorsTable() {  // Update courses table
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> { // keyFrame with a 4 seconds trigger
            minor_tableView.setItems(Model.getInstance().setAllMinors());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // repeats indefinitely
        timeline.playFromStart();
    }
    private void clearFields() {
        minor_minorID.setText(null);
        minor_minorName.setText(null);
        minor_facultyID.setValue(null);
        minor_departmentID.setValue(null);
        minor_degreeID.setValue(null);
        minor_courseID.setValue(null);
        minor_courseNumber.setValue(null);
    }
    private void initialiseMinorTable() {
        if(Model.getInstance().getAllMinors().isEmpty()) {
            Model.getInstance().setAllMinors();
        }
    }
    private void bindMinorsTableData() {
        minor_tableView_col_minorID.setCellValueFactory(cellData -> cellData.getValue().minorIDProperty());
        minor_tableView_col_minorName.setCellValueFactory(cellData -> cellData.getValue().minorNameProperty());
        minor_tableView_col_degree.setCellValueFactory(cellData -> cellData.getValue().degreeIDProperty());
        minor_tableView_col_course1ID.setCellValueFactory(cellData -> cellData.getValue().course1IDProperty());
        minor_tableView_col_course2ID.setCellValueFactory(cellData -> cellData.getValue().course2IDProperty());
        minor_tableView_col_course3ID.setCellValueFactory(cellData -> cellData.getValue().course3IDProperty());
        minor_tableView_col_course4ID.setCellValueFactory(cellData -> cellData.getValue().course4IDProperty());
        minor_tableView_col_course5ID.setCellValueFactory(cellData -> cellData.getValue().course5IDProperty());
        minor_tableView_col_facultyID.setCellValueFactory(cellData -> cellData.getValue().facultyIDProperty());
        minor_tableView_col_departmentID.setCellValueFactory(cellData -> cellData.getValue().departmentIDProperty());

    }
    private void generateMinorID() {
        String minorName = minor_minorName.getText();
        String degreeID = minor_degreeID.getValue();
        String minorID = null;
        if(minorName == null || degreeID == null) {
            minor_minorName.setStyle("-fx-border-color: #EC6666; -fx-font-size: 1.0em;");
            minor_degreeID.setStyle("-fx-border-color: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;  -fx-font-weight: bold");
            operationStatus.setText("Select a degree and enter a minor name.");

        } else {
            String minorCode = StringUtils.left(minorName, 4);
            minorID = (degreeID + "-" + minorCode).toUpperCase();
        }

        minor_minorID.setText(minorID);
    }
    private void selectMinor() {
        Minor minor = minor_tableView.getSelectionModel().getSelectedItem();
        int num = minor_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1) return;
        minor_minorID.setText(String.valueOf(minor.minorIDProperty().get()));
        minor_minorName.setText(String.valueOf(minor.minorNameProperty().get()));
        minor_degreeID.setValue(String.valueOf(minor.degreeIDProperty().get()));
        minor_courseID.setValue(String.valueOf(minor.course1IDProperty().get()));
        minor_facultyID.setValue(String.valueOf(minor.facultyIDProperty().get()));
        minor_departmentID.setValue(String.valueOf(minor.departmentIDProperty().get()));
    }

    private void searchMinor() {
        FilteredList<Minor> searchFilter = new FilteredList<>(Model.getInstance().setAllMinors(), e -> true);
        minor_searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFilter.setPredicate(predicateMinor -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();
                if(predicateMinor.minorIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateMinor.minorNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateMinor.facultyIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateMinor.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateMinor.degreeIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateMinor.course1IDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateMinor.course2IDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateMinor.course3IDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateMinor.course4IDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else return predicateMinor.course5IDProperty().toString().toLowerCase().contains(searchKey);
            });

            SortedList<Minor> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(minor_tableView.comparatorProperty());
            minor_tableView.setItems(sortedList);
        });
    }
    private void filterMinor() {
        FilteredList<Minor> searchFilter = new FilteredList<>(Model.getInstance().setAllMinors(), e -> true);
        minor_filterOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            searchFilter.setPredicate(predicateMinor -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterKey = newValue.toLowerCase();
                if(predicateMinor.facultyIDProperty().toString().toLowerCase().contains(filterKey)) {
                    return true;
                } else return predicateMinor.departmentIDProperty().toString().toLowerCase().contains(filterKey);
            });

            SortedList<Minor> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(minor_tableView.comparatorProperty());
            minor_tableView.setItems(sortedList);
        });
    }
    private void filterOptions(String val) {
        switch (val){
            case "Faculty" -> minor_filterOptions.setItems(Model.getInstance().getConnectDB().getFaculties());
            case "Department" -> minor_filterOptions.setItems(Model.getInstance().getConnectDB().getAllDepartments());
        }
    }
    private void resetFilter() {
        minor_filterBy.setValue("");
        minor_filterOptions.setValue("");
    }
}
