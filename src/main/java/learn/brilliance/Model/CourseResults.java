package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseResults {
    public final StringProperty studentID;
    public final StringProperty studentName;
    public final StringProperty firstCA;
    public final StringProperty secondCA;
    public final StringProperty exam;
    public final StringProperty total;
    public final StringProperty grade;
    public final StringProperty status;

    public CourseResults(String studentID, String studentName, String firstCA, String secondCA, String exam, String total, String status) {
        this.studentID = new SimpleStringProperty(this, "studentID", studentID);
        this.studentName = new SimpleStringProperty(this, "studentName", studentName);
        this.firstCA = new SimpleStringProperty(this, "firstCA", firstCA);
        this.secondCA = new SimpleStringProperty(this, "secondCA", secondCA);
        this.exam = new SimpleStringProperty(this, "exam", exam);
        this.total = new SimpleStringProperty(this, "total", total);
        this.grade = new SimpleStringProperty(this, "grade", total);
        this.status = new SimpleStringProperty(this, "status", status);
    }

    public StringProperty studentIDProperty() { return studentID; }
    public StringProperty studentNameProperty() { return studentName; }
    public StringProperty firstCAProperty() { return firstCA; }
    public StringProperty secondCAProperty() { return secondCA; }
    public StringProperty examProperty() { return exam; }
    public StringProperty totalProperty() { return total; }
    public StringProperty gradeProperty() { return grade; }
    public StringProperty statusProperty() { return status; }

}
