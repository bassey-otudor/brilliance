package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Minor {
    public final StringProperty minorID;
    public final StringProperty courseID;
    public final StringProperty minorName;
    public final StringProperty degreeName;

    public Minor(String minorID, String courseID, String minorName, String degreeName) {
        this.minorID = new SimpleStringProperty(this, "MinorID", minorID);
        this.courseID = new SimpleStringProperty(this, "CourseID", courseID);
        this.minorName = new SimpleStringProperty(this, "MinorName", minorName);
        this.degreeName = new SimpleStringProperty(this, "DegreeName", degreeName);
    }

    public StringProperty minorIDProperty() { return minorID; }
    public StringProperty courseIDProperty() { return courseID; }
    public StringProperty minorNameProperty() { return minorName; }
    public StringProperty degreeNameProperty() { return degreeName; }
}
