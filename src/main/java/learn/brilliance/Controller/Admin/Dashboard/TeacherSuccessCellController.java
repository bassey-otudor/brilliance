package learn.brilliance.Controller.Admin.Dashboard;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.TeacherSuccess;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TeacherSuccessCellController implements Initializable {
    public Label resultView_teacherName;
    public ProgressBar resultView_progressBar;
    public Label resultView_progressLabel;
    public final TeacherSuccess teacherSuccess;
    public TeacherSuccessCellController(TeacherSuccess teacherSuccess) { this.teacherSuccess = teacherSuccess; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resultView_teacherName.textProperty().bind(teacherSuccess.teacherNameProperty());
        teacherSuccessResult();
    }

    private void teacherSuccessResult() {
        // Perform departmental result calculation and update progress bar and success label
        String tableName = teacherSuccess.courseIDProperty() + "-" + LocalDate.now().getYear();
        int passCount = Model.getInstance().getConnectRecord().getPassCount(tableName); // number of students who passed the course
        int totalCount = Model.getInstance().getConnectRecord().getTotalCount(tableName);  // total number of students registered for the course

        int progress = (passCount/totalCount) * 100;
        resultView_progressLabel.setText(String.valueOf(progress));
        resultView_progressBar.setProgress(progress);

    }
}
