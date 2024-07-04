package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeacherSuccess {
    private final StringProperty teacherName;
    private final StringProperty courseID;
    private final StringProperty count;

    public TeacherSuccess(String teacherName, String courseID, String count) {
        this.teacherName = new SimpleStringProperty(this, "teacherName", teacherName);
        this.courseID = new SimpleStringProperty(this, "courseID", courseID);
        this.count = new SimpleStringProperty(this, "count", count);
    }

    public StringProperty teacherNameProperty() { return teacherName; }
    public StringProperty courseIDProperty() { return courseID; }
    public StringProperty countProperty() { return count; }
}
