package learn.brilliance.Controller.Admin;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Department;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.Teacher;

import java.net.URL;
import java.util.ResourceBundle;

public class TeachersController implements Initializable {
    public TextField teach_searchField;
    public ChoiceBox teach_filterDept;
    public ChoiceBox teach_filterCourse;
    public TableView<Teacher> teach_tableView;
    public TableColumn teach_tableView_col_teacherID;
    public TableColumn teach_tableView_col_fName;
    public TableColumn teach_tableView_col_lName;
    public TableColumn teach_tableView_col_phoneNum;
    public TableColumn teach_tableView_col_email;
    public TableColumn teach_tableView_col_gender;
    public TableColumn teach_tableView_col_department;
    public TableColumn teach_tableView_col_course1;
    public TableColumn teach_tableView_col_course2;
    public TableColumn teach_tableView_col_position;
    public TextField teach_fName;
    public TextField teach_lName;
    public TextField teach_email;
    public TextField teach_phoneNum;
    public ChoiceBox teach_gender;
    public DatePicker teach_dob;
    public ChoiceBox teach_department;
    public PasswordField teach_pwd;
    public TextField teach_teacherID;
    public ChoiceBox teach_course1;
    public ChoiceBox teach_course2;
    public ChoiceBox teach_position;
    public Button teach_genIDBtn;
    public Button teach_deleteBtn;
    public Button teach_clearBtn;
    public Button teach_updateBtn;
    public Button teach_addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void searchTeacher() {
        FilteredList<Teacher> searchFilter = new FilteredList<>(Model.getInstance().setTeachers(), e -> true);
        teach_searchField.textProperty().addListener(((observableValue, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateTeacher -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String searchKey = newVal.toLowerCase();

                if(predicateTeacher.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.firstNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.lastNameProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.departmentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.course1Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.course2Property().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacher.positionProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

            SortedList<Teacher> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(teach_tableView.comparatorProperty());
            teach_tableView.setItems(sortedList);
        }));
    }
    private void createTeacher() {
        
    }
}
