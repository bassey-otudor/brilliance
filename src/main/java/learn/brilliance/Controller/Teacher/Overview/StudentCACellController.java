package learn.brilliance.Controller.Teacher.Overview;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import learn.brilliance.Model.CourseRecord;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentCACellController implements Initializable {
    public Label ca_studentName;
    public Label ca_department;
    public Label ca_CA1;
    public Label ca_CA2;
    public Label ca_CATotal;
    public final CourseRecord courseCATotal;
    public StudentCACellController(CourseRecord courseCATotal) {
        this.courseCATotal = courseCATotal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ca_studentName.textProperty().bind(courseCATotal.studentNameProperty());
        ca_department.textProperty().bind(Model.getInstance().getTeacher().departmentIDProperty());
        ca_CA1.textProperty().bind(courseCATotal.firstCAProperty());
        ca_CA2.textProperty().bind(courseCATotal.secondCAProperty());
        ca_CATotal.textProperty().bind(courseCATotal.totalProperty());
    }
}
