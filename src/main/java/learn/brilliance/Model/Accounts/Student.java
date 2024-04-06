package learn.brilliance.Model.Accounts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student extends Accounts {
    public StringProperty studentID;
    public StringProperty degree;
    public StringProperty minor;
    public StringProperty level;
    public StringProperty registrationDate;
    public Student(String studentID, String firstName, String lastName, String gender, String dob, String phoneNumber, String email, String password, String facultyID, String departmentID, String degree, String minor, String level, String registrationDate) {
        super(firstName, lastName, gender, dob, phoneNumber, email, password, facultyID, departmentID);
        this.studentID = new SimpleStringProperty(this, "StudentID", studentID);
        this.degree = new SimpleStringProperty(this, "Degree", degree);
        this.minor = new SimpleStringProperty(this, "Minor", minor);
        this.level = new SimpleStringProperty(this, "Level", level);
        this.registrationDate = new SimpleStringProperty(this, "Registration Date", registrationDate);
    }
    public StringProperty studentIDProperty() {return studentID;}
    public StringProperty degreeIDProperty() {
        return degree;
    }
    public StringProperty minorIDProperty() {
        return minor;
    }
    public StringProperty levelProperty() {
        return level;
    }
    public StringProperty registrationDateProperty() {
        return registrationDate;
    }
}
