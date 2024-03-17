package learn.brilliance.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Student {
    public final StringProperty studentID;
    public final StringProperty firstName;
    public final StringProperty lastName;
    public final StringProperty gender;
    public final StringProperty phoneNumber;
    public final StringProperty email;
    public final StringProperty departmentID;
    public final StringProperty degree;
    public final ObjectProperty<LocalDate> dob;
    public final StringProperty password;
    public final ObjectProperty<LocalDate> registrationDate;
    public final StringProperty minor;
    public final StringProperty level;

    public Student(String studentID, String firstName, String lastName, String gender, String phoneNumber, String email, String departmentID, String degree, LocalDate dob, String password, LocalDate registrationDate, String minor, String level) {
        this.studentID = new SimpleStringProperty(this, "StudentID", studentID);
        this.firstName = new SimpleStringProperty(this, "FirstName", firstName);
        this.lastName = new SimpleStringProperty(this, "LastName", lastName);
        this.gender = new SimpleStringProperty(this, "Gender", gender);
        this.phoneNumber = new SimpleStringProperty(this, "PhoneNumber", phoneNumber);
        this.email = new SimpleStringProperty(this, "Email", email);
        this.departmentID = new SimpleStringProperty(this, "DepartmentID", departmentID);
        this.degree = new SimpleStringProperty(this,"Degree", degree);
        this.dob = new SimpleObjectProperty<>(this, "DoB", dob);
        this.password = new SimpleStringProperty(this, "Password", password);
        this.registrationDate = new SimpleObjectProperty<>(this, "RegDate", registrationDate);
        this.minor = new SimpleStringProperty(this,"Minor", minor);
        this.level = new SimpleStringProperty(this, "Level", level);
    }
    public StringProperty studentIDProperty() {
        return studentID;
    }
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }
    public StringProperty genderProperty() { return gender; }
    public StringProperty phoneNumberProperty() { return phoneNumber; }
    public StringProperty emailProperty() { return email; }
    public StringProperty departmentIDProperty() { return departmentID; }
    public StringProperty degreeProperty() { return degree; }
    public ObjectProperty<LocalDate> dobProperty() { return dob; }
    public StringProperty passwordProperty() { return password; }
    public ObjectProperty<LocalDate> registrationDateProperty() { return registrationDate; }
    public StringProperty minorProperty() { return minor; }
    public StringProperty levelProperty() { return level; }
}
