package learn.brilliance.Controller.Teacher.Overview;

import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import learn.brilliance.Model.CourseRecord;
import learn.brilliance.Model.Model;
import learn.brilliance.View.StudentCACellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {
    public AnchorPane overview_date;
    public ListView overview_timetable;
    public ScatterChart overview_scatterChart;
    public ListView<CourseRecord> overview_topStudentsCA;
    public ListView<CourseRecord> overview_topStudentsTotal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCourseCATotalList();
        overview_topStudentsCA.setItems(Model.getInstance().getAllCourseCATotals());
        overview_topStudentsCA.setCellFactory(e -> new StudentCACellFactory());
    }

    private void initializeCourseCATotalList() {
        if(Model.getInstance().getAllCourseCATotals().isEmpty()) {
            Model.getInstance().setAllCourseCATotals();
        }
    }
}
