package learn.brilliance.Model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Faculty {
    public final StringProperty facultyID;
    public final StringProperty facultyName;
    public final StringProperty director;
    public final StringProperty department1;
    public final StringProperty department2;
    public final StringProperty department3;


    public Faculty(String facultyID, String facultyName, String director, String department1, String department2, String department3) {
        this.facultyID = new SimpleStringProperty(this, "FacultyID", facultyID);
        this.facultyName = new SimpleStringProperty(this, "FacultyName", facultyName);
        this.director = new SimpleStringProperty(this, "Director", director);
        this.department1 = new SimpleStringProperty(this, "Department1", department1);
        this.department2 = new SimpleStringProperty(this, "Department2", department2);
        this.department3 = new SimpleStringProperty(this, "Department3", department3);
    }

    public StringProperty facultyIDProperty() {
        return facultyID;
    }
    public StringProperty facultyNameProperty() {
        return facultyName;
    }
    public StringProperty directorProperty() {
        return director;
    }
    public StringProperty department1Property() {
        return department1;
    }
    public StringProperty department2Property() {
        return department2;
    }
    public StringProperty department3Property() {
        return department3;
    }
}
