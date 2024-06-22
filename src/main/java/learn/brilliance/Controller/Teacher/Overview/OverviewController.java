package learn.brilliance.Controller.Teacher.Overview;

import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import learn.brilliance.Model.CourseRecord;
import learn.brilliance.Model.Model;
import learn.brilliance.View.CourseRecordCellFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OverviewController implements Initializable {
    public AnchorPane overview_date;
    public ListView overview_timetable;
    public ScatterChart<String, Number> overview_scatterChart;
    public ListView<CourseRecord> overview_topStudents;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeOverviewCourseRecordList();
        overview_topStudents.setItems(Model.getInstance().getOverviewCourseRecords());
        overview_topStudents.setCellFactory(e -> new CourseRecordCellFactory());
        courseScatteredChart();
    }

    private void initializeOverviewCourseRecordList() {
        if(Model.getInstance().getOverviewCourseRecords().isEmpty()) {
            Model.getInstance().setOverviewCourseRecords();
        }
    }

    private void courseScatteredChart() {
        overview_scatterChart.getData().clear();

        CategoryAxis xAxis = new CategoryAxis();
        CategoryAxis yAxis = new CategoryAxis();
        xAxis.setLabel("Marks");
        yAxis.setLabel("Occurrence");
        XYChart.Series <String, Number> firstCA = new XYChart.Series<>();
        firstCA.setName("First CA");
        XYChart.Series <String, Number> secondCA = new XYChart.Series<>();
        secondCA.setName("Second CA");
        XYChart.Series <String, Number> exam = new XYChart.Series<>();
        exam.setName("Exam");
        XYChart.Series <String, Number> total = new XYChart.Series<>();
        total.setName("Total");

        String tableName = Model.getInstance().getTeacher().courseProperty().get() + "-" + LocalDate.now().getYear();
        ResultSet firstCAOccurrences = Model.getInstance().getConnectRecord().getFirstCA(tableName);
        ResultSet secondCAOccurrences = Model.getInstance().getConnectRecord().getSecondCA(tableName);
        ResultSet examOccurrences = Model.getInstance().getConnectRecord().getExam(tableName);
        ResultSet totalOccurrences = Model.getInstance().getConnectRecord().getTotal(tableName);

        try {
            while(firstCAOccurrences.next()) {
                firstCA.getData().add(new XYChart.Data<>(firstCAOccurrences.getString(1), firstCAOccurrences.getInt(2)));
                // System.out.println(firstCAOccurrences.getString(1) +" "+ firstCAOccurrences.getInt(2));
            }
            while(secondCAOccurrences.next()) {
                secondCA.getData().add(new XYChart.Data<>(secondCAOccurrences.getString(1), secondCAOccurrences.getInt(2)));
            }
            while(examOccurrences.next()) {
                exam.getData().add(new XYChart.Data<>(examOccurrences.getString(1), examOccurrences.getInt(2)));
            }
            while(totalOccurrences.next()) {
                total.getData().add(new XYChart.Data<>(totalOccurrences.getString(1), totalOccurrences.getInt(2)));
            }

            overview_scatterChart.getData().addAll(firstCA, secondCA, exam, total);

        } catch(SQLException ex) {
            Logger.getLogger(OverviewController.class.getName()).log(Level.SEVERE, "Error setting chart points.", ex);
        }

    }

}
