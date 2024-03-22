package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    public final StringProperty courseID;
    public final StringProperty courseName;
    public final StringProperty courseLevel;
    public final StringProperty departmentID;
    public final StringProperty teacherID;
    public final StringProperty facultyID;

    public Course(String courseID, String courseName, String courseLevel, String departmentID, String teacherID, String facultyID) {
        this.courseID = new SimpleStringProperty(this, "CourseID", courseID);
        this.courseName = new SimpleStringProperty(this, "CourseName", courseName);
        this.courseLevel = new SimpleStringProperty(this, "CourseLevel", courseLevel);
        this.departmentID = new SimpleStringProperty(this, "DeptName", departmentID);
        this.teacherID = new SimpleStringProperty(this, "TeacherID", teacherID);
        this.facultyID = new SimpleStringProperty(this, "FacultyID", facultyID);
    }

    public StringProperty courseIDProperty() { return courseID; }
    public StringProperty courseNameProperty() { return courseName; }
    public StringProperty courseLevelProperty() {return courseLevel; }
    public StringProperty departmentIDProperty() { return departmentID; }
    public StringProperty teacherIDProperty() { return teacherID; }
    public StringProperty facultyIDProperty() { return facultyID; }
}
