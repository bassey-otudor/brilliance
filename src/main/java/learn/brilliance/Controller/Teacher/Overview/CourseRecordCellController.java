package learn.brilliance.Controller.Teacher.Overview;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import learn.brilliance.Model.CourseRecord;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseRecordCellController implements Initializable {
    public Label courseRecord_studentName;
    public Label courseRecord_studentID;
    public Label courseRecord_department;
    public Label courseRecord_firstCa;
    public Label courseRecord_secondCa;
    public Label courseRecord_exam;
    public Label courseRecord_total;
    public Label courseRecord_grade;
    public final CourseRecord courseRecord;
    public CourseRecordCellController(CourseRecord courseRecord) {
        this.courseRecord = courseRecord;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseRecord_studentName.textProperty().bind(courseRecord.studentNameProperty());
        courseRecord_studentID.textProperty().bind(courseRecord.studentIDProperty());
        courseRecord_department.textProperty().bind(Model.getInstance().getTeacher().departmentIDProperty());
        courseRecord_firstCa.textProperty().bind(courseRecord.firstCAProperty());
        courseRecord_secondCa.textProperty().bind(courseRecord.secondCAProperty());
        courseRecord_exam.textProperty().bind(courseRecord.examProperty());
        courseRecord_total.textProperty().bind(courseRecord.totalProperty());
        courseRecord_grade.textProperty().bind(courseRecord.gradeProperty());
    }
}
