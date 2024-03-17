package learn.brilliance.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    public final StringProperty courseID;
    public final StringProperty courseName;
    public final IntegerProperty courseLevel;
    public final StringProperty departmentName;
    public final StringProperty teacherID;

    public Course(String courseID, String courseName, int courseLevel, String departmentName, String teacherID) {
        this.courseID = new SimpleStringProperty(this, "CourseID", courseID);
        this.courseName = new SimpleStringProperty(this, "CourseName", courseName);
        this.courseLevel = new SimpleIntegerProperty(this, "CourseLevel", courseLevel);
        this.departmentName = new SimpleStringProperty(this, "DeptName", departmentName);
        this.teacherID = new SimpleStringProperty(this, "TeacherID", teacherID);
    }

    public StringProperty courseIDProperty() { return courseID; }
    public StringProperty courseNameProperty() { return courseName; }
    public IntegerProperty courseLevelProperty() {return courseLevel; }
    public StringProperty departmentNameProperty() { return departmentName; }
    public StringProperty teacherIDProperty() { return teacherID; }
}
