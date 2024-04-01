package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Minor;
import learn.brilliance.Model.Model;

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
    public Label operationStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void createMinor() {
        String minorID = minor_minorID.getText();
        String minorName = minor_minorName.getText();
        String facultyID = minor_facultyID.getValue();
        String departmentID = minor_departmentID.getValue();
        String degreeID = minor_degreeID.getValue();
        String courseID = minor_courseID.getValue();
        String courseNumber = minor_courseNumber.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, minorID, columnName, minorName);

        if(minorID == null || minorName == null || facultyID == null || departmentID == null || degreeID == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText(" Please fill all required fields.");

        } else {
            if(doesExist) {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Minor already exist.");

            } else {
                Model.getInstance().getConnectDB().createMinor(minorID, minorName, degreeID, facultyID, departmentID, courseID, courseNumber);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em;");
                operationStatus.setText(".");
            }
        }
    }
    public void updateMinor() {
        String minorID = minor_minorID.getText();
        String minorName = minor_minorName.getText();
        String facultyID = minor_facultyID.getValue();
        String departmentID = minor_departmentID.getValue();
        String degreeID = minor_degreeID.getValue();
        String courseID = minor_courseID.getValue();
        String courseNumber = minor_courseNumber.getValue();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, minorID, columnName, minorName);

        if(minorID == null || minorName == null || facultyID == null || departmentID == null || degreeID == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText("Please fill all required fields.");

        } else {
            if(doesExist) {
                Model.getInstance().getConnectDB().updateMinor(minorID, minorName, degreeID, facultyID, departmentID, courseID, courseNumber);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em;");
                operationStatus.setText("Minor successfully updated.");


            } else {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Minor not found.");
            }
        }
    }
    public void deleteMinor() {
        String minorID = minor_minorID.getText();
        String minorName = minor_minorName.getText();
        boolean doesExist = Model.getInstance().getConnectDB().checkData(tableName, idColumn, minorID, columnName, minorName);

        if(minorID == null || minorName == null) {
            operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
            operationStatus.setText(" Please fill all required fields.");

        } else {
            if(doesExist) {
                Model.getInstance().getConnectDB().deleteMinor(minorID);
                operationStatus.setStyle("-fx-text-fill: green; -fx-font-size: 1.0em;");
                operationStatus.setText("Minor successfully deleted.");

            } else {
                operationStatus.setStyle("-fx-text-fill: #EC6666; -fx-font-size: 1.0em;");
                operationStatus.setText("Minor not found.");
            }
        }
    }
    public void clearFields() {
        minor_minorID.setText(null);
        minor_minorName.setText(null);
        minor_facultyID.setValue(null);
        minor_departmentID.setValue(null);
        minor_degreeID.setValue(null);
        minor_courseID.setValue(null);
        minor_courseNumber.setValue(null);
    }


}
