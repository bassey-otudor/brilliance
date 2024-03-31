package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Minor {
    public final StringProperty minorID;
    public final StringProperty minorName;
    public final StringProperty degreeName;
    public final StringProperty course1ID;
    public final StringProperty course2ID;
    public final StringProperty course3ID;
    public final StringProperty course4ID;
    public final StringProperty course5ID;
    public final StringProperty facultyID;
    public final StringProperty departmentID;

    public Minor(String minorID, String minorName, String degreeName, String facultyID, String departmentID, String course1ID, String course2ID, String course3ID, String course4ID, String course5ID) {
        this.minorID = new SimpleStringProperty(this, "MinorID", minorID);
        this.minorName = new SimpleStringProperty(this, "MinorName", minorName);
        this.degreeName = new SimpleStringProperty(this, "DegreeName", degreeName);
        this.facultyID = new SimpleStringProperty(this, "FacultyID", facultyID);
        this.departmentID = new SimpleStringProperty(this, "DepartmentID", departmentID);
        this.course1ID = new SimpleStringProperty(this, "Course1ID", course1ID);
        this.course2ID = new SimpleStringProperty(this, "Course2ID", course2ID);
        this.course3ID = new SimpleStringProperty(this, "Course3ID", course3ID);
        this.course4ID = new SimpleStringProperty(this, "Course4ID", course4ID);
        this.course5ID = new SimpleStringProperty(this, "Course5ID", course5ID);

    }

    public StringProperty minorIDProperty() { return minorID; }
    public StringProperty minorNameProperty() { return minorName; }
    public StringProperty degreeNameProperty() { return degreeName; }
    public StringProperty facultyIDProperty() { return facultyID; }
    public StringProperty departmentIDProperty() { return departmentID; }
    public StringProperty course1IDProperty() { return course1ID; }
    public StringProperty course2IDProperty() { return course2ID; }
    public StringProperty course3IDProperty() { return course3ID; }
    public StringProperty course4IDProperty() { return course4ID; }
    public StringProperty course5IDProperty() { return course5ID; }
}
