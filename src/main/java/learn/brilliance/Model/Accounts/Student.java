package learn.brilliance.Model.Accounts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Student extends Accounts {
    public StringProperty studentID;
    public StringProperty degree;
    public StringProperty minor;
    public StringProperty level;
    public StringProperty registrationDate;
    public Student(String firstName, String lastName, String gender, String dob, String password, String phoneNumber, String email, String facultyID, String departmentID, String studentID, String degree, String minor, String level, String registrationDate) {
        super(firstName, lastName, gender, phoneNumber, email, facultyID, departmentID, dob, password);
        this.studentID = new SimpleStringProperty(this, "StudentID", studentID);
        this.degree = new SimpleStringProperty(this, "Degree", degree);
        this.minor = new SimpleStringProperty(this, "Minor", minor);
        this.level = new SimpleStringProperty(this, "Level", level);
        this.registrationDate = new SimpleStringProperty(this, "Registration Date", registrationDate);
    }
    public StringProperty studentIDProperty() {return studentID;}
    public StringProperty degreeProperty() {
        return degree;
    }
    public StringProperty minorProperty() {
        return minor;
    }
    public StringProperty levelProperty() {
        return level;
    }
    public StringProperty registrationDateProperty() {
        return registrationDate;
    }
}
