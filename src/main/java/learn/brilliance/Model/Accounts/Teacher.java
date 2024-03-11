package learn.brilliance.Model.Accounts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Teacher extends Accounts{
    public StringProperty teacherID;
    public StringProperty firstCourse;
    public StringProperty secondCourse;
    public StringProperty position;
    protected Teacher(String firstName, String lastName, String gender, String phoneNum, String email, String deptID, LocalDate dob, String password, String teacherID, String firstCourse, String secondCourse, String position) {
        super(firstName, lastName, gender, phoneNum, email, deptID, dob, password);
        this.teacherID = new SimpleStringProperty(this, "TeacherID", teacherID);
        this.firstCourse = new SimpleStringProperty(this, "Course1", firstCourse);
        this.secondCourse = new SimpleStringProperty(this, "Course2", secondCourse);
        this.position = new SimpleStringProperty(this, "Position", position);
    }
    public StringProperty getTeacherID() {return teacherID;}
    public StringProperty getFirstCourse() {
        return firstCourse;
    }
    public StringProperty getSecondCourse() {
        return secondCourse;
    }
    public StringProperty getPosition() {
        return position;
    }
}
