package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Degree {
    public final StringProperty degreeID;
    public final StringProperty degreeName;
    public final StringProperty departmentID;
    public final StringProperty duration;
    public final StringProperty numberOfCourses;
    public final StringProperty totalCredits;
    public final StringProperty requiredCredits;

    public Degree(String degreeID, String degreeName, String departmentID, String duration, String  numberOfCourses, String totalCredits, String requiredCreits) {
        this.degreeID = new SimpleStringProperty(this, "DegreeID", degreeID);
        this.degreeName = new SimpleStringProperty(this, "DegreeName", degreeName);
        this.departmentID = new SimpleStringProperty("DepartmentID", departmentID);
        this.duration = new SimpleStringProperty(this, "Duration", duration);
        this.numberOfCourses = new SimpleStringProperty(this, "NumberOfCourses", numberOfCourses);
        this.totalCredits = new SimpleStringProperty(this, "TotalCredits", totalCredits);
        this.requiredCredits = new SimpleStringProperty(this, "RequiredCredits", requiredCreits);
    }

    public StringProperty degreeIDProperty() { return degreeID; }
    public StringProperty degreeNameProperty() { return degreeName; }
    public StringProperty departmentIDProperty() { return departmentID; }
    public StringProperty durationProperty() { return duration; }
    public StringProperty numberOfCoursesProperty() { return numberOfCourses; }
    public StringProperty totalCreditsProperty() { return totalCredits; }
    public StringProperty requiredCreditsProperty() { return requiredCredits; }

}
