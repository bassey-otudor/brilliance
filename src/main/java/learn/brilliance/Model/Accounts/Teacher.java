package learn.brilliance.Model.Accounts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Teacher extends Accounts{
    public StringProperty teacherID;
    public StringProperty course;
    public StringProperty position;
    public Teacher(String teacherID, String firstName, String lastName, String gender, String dob, String phoneNumber, String email, String password, String facultyID, String departmentID, String course, String position) {
        super(firstName, lastName, gender, dob, phoneNumber, email, password, facultyID, departmentID);
        this.teacherID = new SimpleStringProperty(this, "TeacherID", teacherID);
        this.course = new SimpleStringProperty(this, "FirstCourse", course);
        this.position = new SimpleStringProperty(this, "Position", position);
    }
    public StringProperty teacherIDProperty() {return teacherID;}
    public StringProperty courseProperty() {
        return course;
    }
    public StringProperty positionProperty() {
        return position;
    }
}
