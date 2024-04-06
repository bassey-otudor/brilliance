package learn.brilliance.Model.Accounts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Accounts {
    public final StringProperty firstName;
    public final StringProperty lastName;
    public final StringProperty gender;
    public final StringProperty phoneNumber;
    public final StringProperty email;
    public final StringProperty facultyID;
    public final StringProperty departmentID;
    public final StringProperty dob;
    public final StringProperty password;
    protected Accounts(String firstName, String lastName, String gender, String dob, String phoneNumber, String email, String password, String facultyID, String departmentID) {
        this.firstName = new SimpleStringProperty(this,"First Name", firstName);
        this.lastName = new SimpleStringProperty(this,"Last Name", lastName);
        this.gender = new SimpleStringProperty(this,"Gender", gender);
        this.dob = new SimpleStringProperty(this,"Date of Birth", dob);
        this.phoneNumber = new SimpleStringProperty(this,"Phone Number", phoneNumber);
        this.email = new SimpleStringProperty(this,"Email", email);
        this.password = new SimpleStringProperty(this,"Password", password);
        this.facultyID = new SimpleStringProperty(this, "FacultyID", facultyID);
        this.departmentID = new SimpleStringProperty(this,"Department ID", departmentID);
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
    public StringProperty facultyIDProperty() { return facultyID; }
    public StringProperty departmentIDProperty() {
        return departmentID;
    }
    public StringProperty dobProperty() {
        return dob;
    }
}
