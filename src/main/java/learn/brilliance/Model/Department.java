package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Department {
    public final StringProperty departmentID;
    public final StringProperty departmentName;
    public final StringProperty facultyID;
    public final StringProperty hod;
    public final StringProperty minor1;
    public final StringProperty minor2;

    public Department(String departmentID, String departmentName, String facultyID, String hod, String minor1, String minor2) {
        this.departmentID = new SimpleStringProperty(this, "DepartmentID", departmentID);
        this.departmentName = new SimpleStringProperty(this, "DepartmentName", departmentName);
        this.facultyID = new SimpleStringProperty(this, "FacultyID", facultyID);
        this.hod = new SimpleStringProperty(this, "HOD", hod);
        this.minor1 = new SimpleStringProperty(this, "Minor1", minor1);
        this.minor2 = new SimpleStringProperty(this, "Minor2", minor2);
    }
    public StringProperty departmentIDProperty() {
        return departmentID;
    }
    public StringProperty departmentNameProperty() {
        return departmentName;
    }
    public StringProperty facultyIDProperty() {
        return facultyID;
    }
    public StringProperty hodProperty() {
        return hod;
    }
    public StringProperty minor1Property() {
        return minor1;
    }
    public StringProperty minor2Property() {
        return minor2;
    }
}
