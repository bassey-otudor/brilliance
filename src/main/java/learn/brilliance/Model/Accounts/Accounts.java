package learn.brilliance.Model.Accounts;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public abstract class Accounts {
    public final StringProperty firstName;
    public final StringProperty lastName;
    public final StringProperty gender;
    public final StringProperty phoneNum;
    public final StringProperty email;
    public final StringProperty deptID;
    public final ObjectProperty<LocalDate> dob;
    public final StringProperty password;
    protected Accounts(String firstName, String lastName, String gender, String phoneNum, String email, String deptID, LocalDate dob, String password) {
        this.firstName = new SimpleStringProperty(this,"First Name", firstName);
        this.lastName = new SimpleStringProperty(this,"Last Name", lastName);
        this.gender = new SimpleStringProperty(this,"Gender", gender);
        this.phoneNum = new SimpleStringProperty(this,"Phone Number", phoneNum);
        this.email = new SimpleStringProperty(this,"Email", email);
        this.deptID = new SimpleStringProperty(this,"Department ID", deptID);
        this.dob = new SimpleObjectProperty<LocalDate>(this,"Date of Birth", dob);
        this.password = new SimpleStringProperty(this,"Password", password);
    }
    public StringProperty getFirstName() {
        return firstName;
    }
    public StringProperty getLastName() {
        return lastName;
    }
    public StringProperty getGender() {
        return gender;
    }
    public StringProperty getPhoneNum() {
        return phoneNum;
    }
    public StringProperty getEmail() {
        return email;
    }
    public StringProperty getDeptID() {
        return deptID;
    }
    public ObjectProperty<LocalDate> getDob() {
        return dob;
    }
    public StringProperty getPassword() {
        return password;
    }
}
