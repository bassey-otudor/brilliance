package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    public final StringProperty courseID;
    public final StringProperty courseName;
    public final StringProperty courseLevel;
    public final StringProperty departmentID;
    public final StringProperty teacherID;
    public final StringProperty teacherName;
    public final StringProperty facultyID;
    public final StringProperty course_creditValue;

    public Course(String courseID, String courseName, String courseLevel, String departmentID, String teacherID, String teacherName, String facultyID, String creditValue) {
        this.courseID = new SimpleStringProperty(this, "CourseID", courseID);
        this.courseName = new SimpleStringProperty(this, "CourseName", courseName);
        this.courseLevel = new SimpleStringProperty(this, "CourseLevel", courseLevel);
        this.departmentID = new SimpleStringProperty(this, "DeptName", departmentID);
        this.teacherID = new SimpleStringProperty(this, "TeacherID", teacherID);
        this.teacherName = new SimpleStringProperty(this, "TeacherName", teacherName);
        this.facultyID = new SimpleStringProperty(this, "FacultyID", facultyID);
        this.course_creditValue = new SimpleStringProperty(this, "CreditValue", creditValue);
    }

    public StringProperty courseIDProperty() { return courseID; }
    public StringProperty courseNameProperty() { return courseName; }
    public StringProperty courseLevelProperty() {return courseLevel; }
    public StringProperty departmentIDProperty() { return departmentID; }
    public StringProperty teacherIDProperty() { return teacherID; }
    public StringProperty teacherNameProperty() { return teacherName; }
    public StringProperty facultyIDProperty() { return facultyID; }
    public StringProperty creditValueProperty() { return course_creditValue; }
}
