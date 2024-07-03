package learn.brilliance.Controller.Admin.Dashboard;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import learn.brilliance.Model.ResultView;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsViewCellController implements Initializable {
    public Label resultView_department;
    public ProgressBar resultView_progressBar;
    public Label resultView_progressLabel;
    public final ResultView resultView;
    public ResultsViewCellController(ResultView resultView) { this.resultView = resultView; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resultView_department.textProperty().bind(resultView.deptIDProperty());
        resultView_progressLabel.textProperty().bind(resultView.progressLabelProperty());

    }
}
