package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class  DashboardController implements Initializable {

    public Label dashboard_greeting;
    public Label dashboard_adminUsername;
    public Label dashboard_message;
    public Label dashboard_time;
    public Label dashboard_date;
    public PieChart dashboard_pieChart;
    public LineChart dashboard_lineGraph;
    public Label dashboard_term;
    public ListView dashboard_listView;
    public TextField dashboard_search;
    public AnchorPane dashboard_scrollPane;
    public Label teacherSuccess_teacherName;
    public ProgressBar teacherSuccess_progressBar;
    public Label teacherSuccess_successLabel;
    public Label resultView_department;
    public ProgressBar resultView_progressBar;
    public Label resultView_progressLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

