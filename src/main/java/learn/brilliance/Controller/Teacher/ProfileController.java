package learn.brilliance.Controller.Teacher;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public TextField profile_firstName;
    public TextField profile_lastName;
    public TextField profile_teacherID;
    public TextField profile_email;
    public TextField profile_phoneNumber;
    public TextField profile_currentPassword;
    public DatePicker profile_dob;
    public TextField profile_gender;
    public Button profile_personalInfoSaveBtn;
    public Button profile_contactInfoSaveBtn;
    public TextField profile_newPassword;
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

    }


}
