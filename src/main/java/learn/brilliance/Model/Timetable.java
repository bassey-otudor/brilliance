package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Timetable {
    private final StringProperty startTime;
    private final StringProperty endTime;
    private final StringProperty courseID;
    private final StringProperty level;
    private final StringProperty teacherID;
    private final StringProperty location;
    public Timetable(String startTime, String endTime, String courseID, String level, String teacherID, String location) {
        this.startTime = new SimpleStringProperty(this, "startTime", startTime);
        this.endTime = new SimpleStringProperty(this, "endTime", endTime);
        this.courseID = new SimpleStringProperty(this, "courseID", courseID);
        this.level = new SimpleStringProperty(this, "level", level);
        this.teacherID = new SimpleStringProperty(this, "teacherID", teacherID);
        this.location = new SimpleStringProperty(this, "location", location);

    }

    public StringProperty startTimeProperty() { return startTime; }
    public StringProperty endTimeProperty() { return endTime; }
    public StringProperty courseIDProperty() { return courseID; }
    public StringProperty levelProperty() { return level; }
    public StringProperty teacherIDProperty() { return teacherID; }
    public StringProperty locationProperty() { return location; }
}
