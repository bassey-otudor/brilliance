package learn.brilliance.Controller.Teacher;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.CourseResults;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {
    public Button teacher_course1Btn;
    public Button teacher_course2Btn;
    public TableView<CourseResults> teacher_studentMarkTableView1;
    public TableColumn<CourseResults, String> teacher_col_studentID1;
    public TableColumn<CourseResults, String> teacher_col_studentName1;
    public TableColumn<CourseResults, String> teacher_col_firstCA1;
    public TableColumn<CourseResults, String> teacher_col_secondCA1;
    public TableColumn<CourseResults, String> teacher_col_exam1;
    public TableColumn<CourseResults, String> teacher_col_total1;
    public TableColumn<CourseResults, String> teacher_col_grade;
    public TextField teacher_searchField;
    public ComboBox<String> teacher_filterBy;
    public Button teacher_resetFilterBtn;
    public Button teacher_clearBtn;
    public TextField teacher_studentIDField;
    public TextField teacher_studentNameField;
    public TextField teacher_firstCAField;
    public TextField teacher_secondCAField;
    public TextField teacher_col_examField;
    public TextField teacher_totalField;
    public TextField teacher_gradeField;
    public Button teacher_updateBtn;
    public TableView<CourseResults> teacher_studentMarkTableView2;
    public TableColumn<CourseResults, String> teacher_col_studentID2;
    public TableColumn<CourseResults, String> teacher_col_studentName2;
    public TableColumn<CourseResults, String> teacher_col_firstCA2;
    public TableColumn<CourseResults, String> teacher_col_secondCA2;
    public TableColumn<CourseResults, String> teacher_col_exam2;
    public TableColumn<CourseResults, String> teacher_col_total2;
    public TableColumn<CourseResults, String> teacher_col_grade2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
