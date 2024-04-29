package learn.brilliance.Controller.Teacher;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public TextField profile_firstName;
    public TextField profile_lastName;
    public TextField profile_teacherID;
    public TextField profile_email;
    public TextField profile_phoneNumber;
    public PasswordField profile_currentPassword;
    public DatePicker profile_dob;
    public TextField profile_gender;
    public Button profile_contactInfoSaveBtn;
    public PasswordField profile_newPassword;
    public Button profile_changePasswordBtn;
    public FontAwesomeIconView profile_personalInfoSaveIcon;
    public FontAwesomeIconView profile_contactInfoSaveIcon;
    public FontAwesomeIconView profile_changePasswordIcon;
    public TextField profile_teacherFaculty;
    public TextField profile_teacherDepartment;
    public TextField profile_teacherFirstCourse;
    public TextField profile_teacherSecondCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindTeacherData();
    }


    private void bindTeacherData() {
        profile_teacherID.setText(Model.getInstance().getTeacher().teacherIDProperty().get());
        profile_firstName.setText(Model.getInstance().getTeacher().firstNameProperty().get());
        profile_lastName.setText(Model.getInstance().getTeacher().lastNameProperty().get());
        profile_gender.setText(Model.getInstance().getTeacher().genderProperty().get());
        profile_email.setText(Model.getInstance().getTeacher().emailProperty().get());
        profile_phoneNumber.setText(Model.getInstance().getTeacher().phoneNumberProperty().get());
        profile_teacherFaculty.setText(Model.getInstance().getTeacher().facultyIDProperty().get());
        profile_teacherDepartment.setText(Model.getInstance().getTeacher().departmentIDProperty().get());
        profile_teacherFirstCourse.setText(Model.getInstance().getTeacher().firstCourseProperty().get());
        profile_teacherSecondCourse.setText(Model.getInstance().getTeacher().secondCourseProperty().get());

    }
}
