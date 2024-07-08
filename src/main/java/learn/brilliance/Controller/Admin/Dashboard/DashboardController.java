package learn.brilliance.Controller.Admin.Dashboard;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.TeacherSuccess;
import learn.brilliance.Model.connectDB;
import learn.brilliance.View.TeacherSuccessCellFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class  DashboardController extends PieChart implements Initializable {
    private final Map<PieChart.Data, Text> labels = new HashMap<>();
    public Label dashboard_greeting;
    public Label dashboard_message;
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
    public ListView<TeacherSuccess> dashboard_teacherSuccess;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboard_date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d yyyy")));
        studentLineChart();
        departmentPieChart();
        initializeTeacherSuccessList();

        dashboard_teacherSuccess.setItems(Model.getInstance().getAllResultViews());
        dashboard_teacherSuccess.setCellFactory(e -> new TeacherSuccessCellFactory());
    }

    private void departmentPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ResultSet resultSet = Model.getInstance().getConnectDB().getPieChartData();
        try {
            while (resultSet.next()) {
                pieChartData.add(new PieChart.Data(resultSet.getString(1), resultSet.getInt(2)));
            }

        } catch(SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        dashboard_pieChartDepartments.setData(pieChartData);
        dashboard_pieChartDepartments.setLegendVisible(false);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty()
                        )
                )
        );

    }
    private void studentLineChart() {
        dashboard_lineGraphStudents.getData().clear();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel("Number of Students");
        XYChart.Series<String, Number> totalAdmissions = new XYChart.Series<>(); // Total students chart
        totalAdmissions.setName("Total Admissions");

        XYChart.Series<String, Number> maleStudents = new XYChart.Series<>(); // Male students chart
        maleStudents.setName("Male");

        XYChart.Series<String, Number> femaleStudents = new XYChart.Series<>(); // Female students chart
        femaleStudents.setName("Female");

        ResultSet totalRegisteredStudents = Model.getInstance().getConnectDB().getTotalRegisteredStudents();
        ResultSet registeredMaleStudents = Model.getInstance().getConnectDB().getAllRegisteredMaleStudents();
        ResultSet registeredFemaleStudents = Model.getInstance().getConnectDB().getAllRegisteredFemaleStudents();
        try {
            while (totalRegisteredStudents.next()) {
                totalAdmissions.getData().add(new XYChart.Data<>(totalRegisteredStudents.getString(1), totalRegisteredStudents.getInt(2)));
            }
            while (registeredMaleStudents.next()) {
               maleStudents.getData().add(new XYChart.Data<>(registeredMaleStudents.getString(1), registeredMaleStudents.getInt(2)));

            }
            while (registeredFemaleStudents.next()) {
                femaleStudents.getData().add(new XYChart.Data<>(registeredFemaleStudents.getString(1), registeredFemaleStudents.getInt(2)));
            }

            dashboard_lineGraphStudents.getData().addAll(maleStudents, femaleStudents, totalAdmissions);

        } catch (SQLException e) {
            System.out.println("Unable to display line graph. " + e.getMessage());
        }

    }

    private void initializeTeacherSuccessList() {
        if(Model.getInstance().getAllResultViews().isEmpty()) {
            Model.getInstance().setAllResultViews();
        }
    }
}

