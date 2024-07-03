package learn.brilliance.Controller.Teacher;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.CourseRecord;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {
    public TextField teacher_searchField;
    public ComboBox<String> teacher_filterByGrade;
    public Button teacher_resetFilterBtn;
    public Button teacher_clearBtn;
    public Label teacher_tableName;
    public ComboBox<String> teacher_courseRecordYear;
    public TextField teacher_studentIDField;
    public TextField teacher_studentNameField;
    public TextField teacher_firstCAField;
    public TextField teacher_secondCAField;
    public TextField teacher_examField;
    public TextField teacher_totalField;
    public TextField teacher_gradeField;
    public Button teacher_updateBtn;

    public TableView<CourseRecord> teacher_studentMarkTableView;
    public TableColumn<CourseRecord, String> teacher_col_studentID;
    public TableColumn<CourseRecord, String> teacher_col_studentName;
    public TableColumn<CourseRecord, String> teacher_col_firstCA;
    public TableColumn<CourseRecord, String> teacher_col_secondCA;
    public TableColumn<CourseRecord, String> teacher_col_exam;
    public TableColumn<CourseRecord, String> teacher_col_total;
    public TableColumn<CourseRecord, String> teacher_col_grade;
    public TableColumn<CourseRecord, String> teacher_col_status;
    public ComboBox<String> teacher_filterByStatus;
    public Label teacher_operationMessage;
    String tableName = Model.getInstance().getTeacher().courseProperty().get() + "-" + LocalDate.now().getYear();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teacher_tableName.setText(Model.getInstance().getTeacher().courseProperty().get());
        teacher_filterByGrade.setItems(Model.getInstance().getConnectDB().getGrades());

        teacher_filterByStatus.setItems(Model.getInstance().getConnectDB().getStatus());


        teacher_updateBtn.setOnAction(e -> updateCourseRecord());
        teacher_clearBtn.setOnAction(e -> clearAllFields());
        teacher_resetFilterBtn.setOnAction(e -> resetFilters());

        // Setup Course TableView.
        initializeCourseRecordTable();
        bindTableData();
        teacher_studentMarkTableView.setItems(Model.getInstance().setAllCourseRecords(String.valueOf(LocalDate.now().getYear())));
        fillInputFields();
        teacher_studentMarkTableView.setOnMouseClicked(e -> selectCourseRecord());
        searchCourseRecords();
        filterByGrade();
        filterByStatus();

        // changeAcademicYear();

    }

    private void searchCourseRecords() {
        FilteredList<CourseRecord> searchFilter = new FilteredList<>(Model.getInstance().setAllCourseRecords(String.valueOf(LocalDate.now().getYear())), e -> true);
        teacher_searchField.textProperty().addListener(((observable, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateCourseRecord -> {
                if(newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String searchKey = newVal.toLowerCase();
                if(predicateCourseRecord.studentIDProperty().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else return predicateCourseRecord.studentNameProperty().toString().toLowerCase().contains(searchKey);

            });
            SortedList<CourseRecord> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(teacher_studentMarkTableView.comparatorProperty());
            teacher_studentMarkTableView.setItems(sortedList);
        }));
    }
    // Create course record validator
    private void updateCourseRecord() {
        String studentID = teacher_studentIDField.getText();
        String studentName = teacher_studentNameField.getText();
        String firstCA = teacher_firstCAField.getText();
        String secondCA = teacher_secondCAField.getText();
        String exam = teacher_examField.getText();
        String total = teacher_totalField.getText();
        String grade = teacher_gradeField.getText();
        String status;

        if(firstCA == null || firstCA.isEmpty()) {
            Model.getInstance().getConnectRecord().updateCourseRecord(tableName, studentID, studentName, null, null, null, null,"ABSENT", "FAILED");

        } else {
            status = determineStatus(total);
            Model.getInstance().getConnectRecord().updateCourseRecord(tableName, studentID, studentName, firstCA, secondCA, exam, total, grade, status);
        }
        teacher_studentMarkTableView.setItems(Model.getInstance().setAllCourseRecords(String.valueOf(LocalDate.now().getYear())));
        clearAllFields();

    }
    private void clearAllFields() {
        teacher_studentIDField.setText(null);
        teacher_studentNameField.setText(null);
        teacher_firstCAField.setText(null);
        teacher_secondCAField.setText(null);
        teacher_examField.setText(null);
        teacher_totalField.setText(null);
        teacher_gradeField.setText(null);
    }

    private String calculateTotal(String firstCA, String secondCA, String exam) {
        if(firstCA == null || firstCA.isEmpty()) {
            firstCA = "0";
        }
        if(secondCA == null || secondCA.isEmpty()) {
            secondCA = "0";
        }
        if(exam == null || exam.isEmpty()) {
            exam = "0";
        }
        return String.valueOf(Integer.parseInt(firstCA) + Integer.parseInt(secondCA) + Integer.parseInt(exam));
    }
    private String determineGrade(int total) {

        String grade = null;
        if(total > 97) {
            grade = "A+";

        } else if(total > 92) {
            grade = "A";

        } else if(total > 90) {
            grade = "A-";

        } else if(total > 88) {
            grade = "B+";

        } else if(total > 83) {
            grade = "B";

        } else if(total > 80) {
            grade = "B-";

        } else if(total > 78) {
            grade = "C+";

        } else if(total > 73) {
            grade = "C";

        } else if(total > 70) {
            grade = "C-";

        } else if(total > 68) {
            grade = "D+";

        } else if(total > 63) {
            grade = "D";

        } else if(total > 60) {
            grade = "D-";

        } else if(total < 50) {
            grade = "F";
        }

        return grade;
    }

    private void fillInputFields() {
        teacher_firstCAField.textProperty().addListener((observable, oldVal, newVal) -> {
            teacher_totalField.setText(calculateTotal(teacher_firstCAField.getText(), teacher_secondCAField.getText(), teacher_examField.getText()));
            teacher_gradeField.setText(determineGrade(Integer.parseInt(teacher_totalField.getText())));

        });

        teacher_secondCAField.textProperty().addListener((observable, oldVal, newVal) -> {
            teacher_totalField.setText(calculateTotal(teacher_firstCAField.getText(), teacher_secondCAField.getText(), teacher_examField.getText()));
            teacher_gradeField.setText(determineGrade(Integer.parseInt(teacher_totalField.getText())));

        });

        teacher_examField.textProperty().addListener((observable, oldVal, newVal) -> {
            teacher_totalField.setText(calculateTotal(teacher_firstCAField.getText(), teacher_secondCAField.getText(), teacher_examField.getText()));
            teacher_gradeField.setText(determineGrade(Integer.parseInt(teacher_totalField.getText())));

        });

    }
    private String determineStatus(String total) {
        if(Integer.parseInt(total) < 50) {
            return "FAILED";
        } else {
            return "PASSED";
        }
    }
    private void filterByStatus() {
        FilteredList<CourseRecord> searchFilter = new FilteredList<>(Model.getInstance().setAllCourseRecords(String.valueOf(LocalDate.now().getYear())), e -> true);
        teacher_filterByStatus.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateCourseRecord -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String filterKey = newVal.toLowerCase();
                return predicateCourseRecord.statusProperty().toString().toLowerCase().contains(filterKey);
            });

            SortedList<CourseRecord> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(teacher_studentMarkTableView.comparatorProperty());
            teacher_studentMarkTableView.setItems(sortedList);
        });
    }
    private void filterByGrade() {
        FilteredList<CourseRecord> searchFilter = new FilteredList<>(Model.getInstance().setAllCourseRecords(String.valueOf(LocalDate.now().getYear())), e -> true);
        teacher_filterByGrade.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            searchFilter.setPredicate(predicateCourseRecord -> {
                if(newVal == null || newVal.isEmpty()) { return true; }

                String searchKey = newVal.toLowerCase();
                return predicateCourseRecord.gradeProperty().toString().toLowerCase().contains(searchKey);
            });
            SortedList<CourseRecord> sortedList = new SortedList<>(searchFilter);
            sortedList.comparatorProperty().bind(teacher_studentMarkTableView.comparatorProperty());
            teacher_studentMarkTableView.setItems(sortedList);
        });
    }
    private void resetFilters() {
        teacher_filterByGrade.setValue(null);
        teacher_filterByStatus.setValue(null);
    }


    // Change course year control function
    /*private void changeAcademicYear() {
        teacher_courseRecordYear.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            Model.getInstance().setAllFirstCourseRecords("2024");
        });

        Model.getInstance().setAllSecondCourseRecords("2024");

    }*/

    private void initializeCourseRecordTable() {
        if(Model.getInstance().getAllCourseRecords().isEmpty()) {
            Model.getInstance().setAllCourseRecords(String.valueOf(LocalDate.now().getYear()));
        }
    }
    private void bindTableData() {
        // First course table
        teacher_col_studentID.setCellValueFactory(cellData -> cellData.getValue().studentIDProperty());
        teacher_col_studentName.setCellValueFactory(cellData -> cellData.getValue().studentNameProperty());
        teacher_col_firstCA.setCellValueFactory(cellData -> cellData.getValue().firstCAProperty());
        teacher_col_secondCA.setCellValueFactory(cellData -> cellData.getValue().secondCAProperty());
        teacher_col_exam.setCellValueFactory(cellData -> cellData.getValue().examProperty());
        teacher_col_total.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        teacher_col_grade.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
        teacher_col_status.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
    }
    private void selectCourseRecord() {
        CourseRecord courseRecord = teacher_studentMarkTableView.getSelectionModel().getSelectedItem();
        String firstCA = courseRecord.firstCAProperty().get();
        String secondCA = courseRecord.secondCAProperty().get();
        String exam = courseRecord.examProperty().get();
        String total = courseRecord.totalProperty().get();
        String grade = courseRecord.gradeProperty().get();

        if(firstCA == null) { firstCA = ""; }
        if(secondCA == null)  { secondCA = ""; }
        if(exam == null) { exam = ""; }
        if(total == null) { total = ""; }
        if(grade == null) { grade = ""; }

        int num = teacher_studentMarkTableView.getSelectionModel().getSelectedIndex();
        if((num-1) < -1) return;
        teacher_studentIDField.setText(String.valueOf(courseRecord.studentIDProperty().get()));
        teacher_studentNameField.setText(String.valueOf(courseRecord.studentNameProperty().get()));
        teacher_firstCAField.setText(firstCA);
        teacher_secondCAField.setText(secondCA);
        teacher_examField.setText(exam);
        teacher_totalField.setText(total);
        teacher_gradeField.setText(grade);
    }
}
