package learn.brilliance.Model.Accounts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Teacher extends Accounts{
    public StringProperty teacherID;
    public StringProperty firstCourse;
    public StringProperty secondCourse;
    public StringProperty position;
    public Teacher(String firstName, String lastName, String gender, String dob, String phoneNumber, String email, String password, String facultyID, String departmentID, String teacherID, String firstCourse, String secondCourse, String position) {
        super(firstName, lastName, gender, dob, password, phoneNumber, email, facultyID, departmentID);
        this.teacherID = new SimpleStringProperty(this, "TeacherID", teacherID);
        this.firstCourse = new SimpleStringProperty(this, "FirstCourse", firstCourse);
        this.secondCourse = new SimpleStringProperty(this, "SecondCourse", secondCourse);
        this.position = new SimpleStringProperty(this, "Position", position);
    }
    public StringProperty teacherIDProperty() {return teacherID;}
    public StringProperty firstCourseProperty() {
        return firstCourse;
    }
    public StringProperty secondCourseProperty() {
        return secondCourse;
    }
    public StringProperty positionProperty() {
        return position;
    }
}
