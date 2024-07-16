package learn.brilliance.Controller.Teacher.Overview;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import learn.brilliance.Model.CourseRecord;
import learn.brilliance.Model.Model;
import learn.brilliance.Model.Timetable;
import learn.brilliance.View.CourseRecordCellFactory;
import learn.brilliance.View.TimetableCellFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class OverviewController implements Initializable {
    public AnchorPane overview_date;
    public ListView overview_timetable;
    public ListView<Timetable> overview_timetableTodayListView;
    public ListView<Timetable> overview_timetableTomorrowListView;
    public ScatterChart<Number, Number> overview_scatterChart;
    public ListView<CourseRecord> overview_topStudents;
    public NumberAxis yAxis;
    public NumberAxis xAxis;
    public Label overview_timetableToday;
    public Label overview_timetableTomorrow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeOverviewCourseRecordList();
        overview_timetableToday.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d yyyy")));
        tomorrowDate();
        overview_topStudents.setItems(Model.getInstance().getOverviewCourseRecords());
        overview_topStudents.setCellFactory(e -> new CourseRecordCellFactory());
        initializeTimetableList();
        overview_timetableTodayListView.setItems(Model.getInstance().getAllTodayTimetables());
        overview_timetableTodayListView.setCellFactory(e -> new TimetableCellFactory());
        overview_timetableTomorrowListView.setItems(Model.getInstance().getAllTomorrowTimetables());
        overview_timetableTomorrowListView.setCellFactory(e -> new TimetableCellFactory());

        courseScatteredChart();
        updateOverviewTab();
    }

    private void initializeOverviewCourseRecordList() {
        if(Model.getInstance().getOverviewCourseRecords().isEmpty()) {
            Model.getInstance().setOverviewCourseRecords();
        }
    }

    private void initializeTimetableList() {
        if(Model.getInstance().getAllTodayTimetables().isEmpty() || Model.getInstance().getAllTomorrowTimetables().isEmpty()) {
            Model.getInstance().setTodayTimetable();
            Model.getInstance().setTomorrowTimetable();
        }
    }

    private void courseScatteredChart() {
        overview_scatterChart.getData().clear();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(100);
        xAxis.setTickUnit(1);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(30);
        yAxis.setTickUnit(1);

        xAxis.setLabel("Marks");
        yAxis.setLabel("Occurrence");
        XYChart.Series <Number, Number> firstCA = new XYChart.Series<>();
        firstCA.setName("First CA");
        XYChart.Series <Number, Number> secondCA = new XYChart.Series<>();
        secondCA.setName("Second CA");
        XYChart.Series <Number, Number> exam = new XYChart.Series<>();
        exam.setName("Exam");
        XYChart.Series <Number, Number> total = new XYChart.Series<>();
        total.setName("Total");

        String tableName = Model.getInstance().getTeacher().courseProperty().get() + "-" + LocalDate.now().getYear();
        ResultSet firstCAOccurrences = Model.getInstance().getConnectRecord().getFirstCA(tableName);
        ResultSet secondCAOccurrences = Model.getInstance().getConnectRecord().getSecondCA(tableName);
        ResultSet examOccurrences = Model.getInstance().getConnectRecord().getExam(tableName);
        ResultSet totalOccurrences = Model.getInstance().getConnectRecord().getTotal(tableName);

        try {
            while(firstCAOccurrences.next()) {
                firstCA.getData().add(new XYChart.Data<>(firstCAOccurrences.getInt(1), firstCAOccurrences.getInt(2)));
                // System.out.println(firstCAOccurrences.getString(1) +" "+ firstCAOccurrences.getInt(2));
            }
            while(secondCAOccurrences.next()) {
                secondCA.getData().add(new XYChart.Data<>(secondCAOccurrences.getInt(1), secondCAOccurrences.getInt(2)));
            }
            while(examOccurrences.next()) {
                exam.getData().add(new XYChart.Data<>(examOccurrences.getInt(1), examOccurrences.getInt(2)));
            }
            while(totalOccurrences.next()) {
                total.getData().add(new XYChart.Data<>(totalOccurrences.getInt(1), totalOccurrences.getInt(2)));
            }

            overview_scatterChart.getData().addAll(firstCA, secondCA, exam, total);

        } catch(SQLException ex) {
            Logger.getLogger(OverviewController.class.getName()).log(Level.SEVERE, "Error setting chart points.", ex);
        }


    }
    private void updateOverviewTab() {  // Update courses table
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> { // keyFrame with a 4 seconds trigger
            overview_topStudents.setItems(Model.getInstance().getOverviewCourseRecords());
            overview_topStudents.setCellFactory(e -> new CourseRecordCellFactory());
            courseScatteredChart();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // repeats indefinitely
        timeline.playFromStart();
    }

    private void tomorrowDate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        overview_timetableTomorrow.setText(tomorrow.format(DateTimeFormatter.ofPattern("EEEE, MMMM d yyyy")));
    }
}
