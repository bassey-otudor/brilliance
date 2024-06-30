package learn.brilliance.Controller.Teacher.Overview;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.Timetable;

import java.net.URL;
import java.util.ResourceBundle;

public class TimetableCellController implements Initializable {
    public Label overview_timetableCourseID;
    public Label overview_timetableCourseName;
    public Label overview_timetableLocation;
    public Label overview_timetableStartTime;
    public ProgressBar overview_timetableProgressBar;
    public final Timetable timetable;
    public TimetableCellController(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        overview_timetableCourseID.textProperty().bind(timetable.courseIDProperty());
        overview_timetableCourseName.setText(Model.getInstance().getConnectDB().getCourseName(overview_timetableCourseID.getText()));
        overview_timetableLocation.textProperty().bind(timetable.locationProperty());
        overview_timetableStartTime.textProperty().bind(timetable.startTimeProperty());

    }
}
