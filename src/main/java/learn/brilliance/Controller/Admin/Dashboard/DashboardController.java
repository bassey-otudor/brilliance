package learn.brilliance.Controller.Admin.Dashboard;

import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class  DashboardController implements Initializable {

    public Label dashboard_greeting;
    public Label dashboard_adminUsername;
    public Label dashboard_message;
    public Label dashboard_time;
    public Label dashboard_date;
    public PieChart dashboard_pieChartDepartments;
    public LineChart<String, Number> dashboard_lineGraphStudents;
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
        dashboard_date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d yyyy")));
        studentLineChart();
    }

    private void studentLineChart() {
        dashboard_lineGraphStudents.getData().clear();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel("Number of Students");
        LineChart<String, Number> studentLineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> studentTotal = new XYChart.Series<>();

        ResultSet resultSet = Model.getInstance().getConnectDB().getTotalRegisteredStudents();
        try {
            for(int i = 2021; i <= LocalDate.now().getYear(); i++) {
                while (resultSet.next()) {
                    studentTotal.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getInt(2)));
                }
            }
            dashboard_lineGraphStudents.getData().addAll(studentTotal);

        } catch (SQLException e) {
            System.out.println("Unable to display line graph. " + e.getMessage());
        }
    }
}

