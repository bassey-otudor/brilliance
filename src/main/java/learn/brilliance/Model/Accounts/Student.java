package learn.brilliance.Model.Accounts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Student extends Accounts {
    public StringProperty studentID;
    public StringProperty degree;
    public StringProperty minor;
    public StringProperty level;
    protected Student(String firstName, String lastName, String gender, String phoneNum, String email, String deptID, LocalDate dob, String password, String studentID, String degree, String minor, String level) {
        super(firstName, lastName, gender, phoneNum, email, deptID, dob, password);
        this.studentID = new SimpleStringProperty(this, "StudentID", studentID);
        this.degree = new SimpleStringProperty(this, "Degree", degree);
        this.minor = new SimpleStringProperty(this, "Minor", minor);
        this.level = new SimpleStringProperty(this, "Level", level);
    }
    public StringProperty getStudentID() {return studentID;}
    public StringProperty getDegree() {
        return degree;
    }
    public StringProperty getMinor() {
        return minor;
    }
    public StringProperty getLevel() {
        return level;
    }
}
