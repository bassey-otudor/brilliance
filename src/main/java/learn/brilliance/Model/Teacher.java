package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Teacher{
    public final StringProperty teacherID;
    public final StringProperty firstName;
    public final StringProperty lastName;
    public final StringProperty gender;
    public final StringProperty phoneNumber;
    public final StringProperty email;
    public final StringProperty departmentID;
    public final StringProperty dob;
    public final StringProperty password;
    public final StringProperty course1;
    public final StringProperty course2;
    public final StringProperty position;

    public Teacher(String teacherID, String firstName, String lastName, String gender, String phoneNumber, String email, String departmentID, String dob, String password, String course1, String course2, String position) {
        this.teacherID = new SimpleStringProperty(this, "TeacherID", teacherID);
        this.firstName = new SimpleStringProperty(this, "FirstName", firstName);
        this.lastName = new SimpleStringProperty(this, "LastName", lastName);
        this.gender = new SimpleStringProperty(this, "Gender", gender);
        this.phoneNumber = new SimpleStringProperty(this, "PhoneNumber", phoneNumber);
        this.email = new SimpleStringProperty(this, "Email", email);
        this.departmentID = new SimpleStringProperty(this, "DepartmentID", departmentID);
        this.dob = new SimpleStringProperty(this, "DoB", dob);
        this.password = new SimpleStringProperty(this, "Password", password);
        this.course1 = new SimpleStringProperty(this, "Course1", course1);
        this.course2 = new SimpleStringProperty(this, "Course2", course2);
        this.position = new SimpleStringProperty(this, "Position", position);
    }

    public StringProperty teacherIDProperty() {
        return teacherID;
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public StringProperty genderProperty() {
        return gender;
    }
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty departmentIDProperty() { return departmentID; }
    public StringProperty dobProperty() { return dob;}
    public StringProperty passwordProperty() { return password; }
    public StringProperty course1Property() { return course1; }
    public StringProperty course2Property() { return course2; }
    public StringProperty positionProperty() { return position; }

}
