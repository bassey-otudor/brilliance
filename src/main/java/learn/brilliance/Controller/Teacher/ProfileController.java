package learn.brilliance.Controller.Teacher;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ProfileController implements Initializable {
    public TextField profile_firstName;
    public TextField profile_lastName;
    public TextField profile_teacherID;
    public TextField profile_email;
    public TextField profile_phoneNumber;
    public PasswordField profile_confirmCurrentPassword;
    public DatePicker profile_dob;
    public TextField profile_gender;
    public Button profile_contactInfoSaveBtn;
    public PasswordField profile_newPassword;
    public Button profile_changePasswordBtn;
    public FontAwesomeIconView profile_contactInfoSaveIcon;
    public FontAwesomeIconView profile_changePasswordIcon;
    public TextField profile_teacherFaculty;
    public TextField profile_teacherDepartment;
    public TextField profile_teacherFirstCourse;
    public TextField profile_teacherSecondCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profile_contactInfoSaveBtn.setOnAction(e -> updateContactInfo());
        profile_changePasswordBtn.setOnAction(e -> changePassword());
        bindTeacherData();
    }

    private void updateContactInfo() {
        String email;
        String phoneNumber;
        String teacherID = Model.getInstance().getTeacher().teacherIDProperty().get();
        email = profile_email.getText();
        phoneNumber = profile_phoneNumber.getText();

        if(email.isEmpty() && phoneNumber.isEmpty()) {
            profile_email.setStyle("-fx-border-color: red;  -fx-border-width: 1px;");
            profile_phoneNumber.setStyle("-fx-border-color: red; -fx-border-width: 1px");

        } else if (email.isEmpty()) {
            Model.getInstance().getConnectDB().updateContactInfo(teacherID,phoneNumber, null);
            profile_contactInfoSaveIcon.setVisible(true);
            profile_phoneNumber.setStyle(" -fx-border-width: 1px; -fx-border-color: green;");

        } else if (phoneNumber.isEmpty()) {
            Model.getInstance().getConnectDB().updateContactInfo(teacherID,null, email);
            profile_contactInfoSaveIcon.setVisible(true);
            profile_email.setStyle("-fx-border-width: 1px; -fx-border-color: green;");

        } else {
            Model.getInstance().getConnectDB().updateContactInfo(teacherID, phoneNumber, email);
            profile_email.setStyle("-fx-border-width: 1px; -fx-border-color: green;");
            profile_phoneNumber.setStyle(" -fx-border-width: 1px; -fx-border-color: green;");
            profile_contactInfoSaveIcon.setVisible(true);
            resetStyleTimer(); // reset the styles after success notifier
        }

    }

    // change password validator
    private void changePassword() {
        String confirmCurrentPassword;
        String newPassword;
        String teacherID = Model.getInstance().getTeacher().teacherIDProperty().get();
        String currentPassword = Model.getInstance().getTeacher().passwordProperty().get();

        confirmCurrentPassword = profile_confirmCurrentPassword.getText();
        newPassword = profile_newPassword.getText();

        if(confirmCurrentPassword.isEmpty() && newPassword.isEmpty()) {
            profile_confirmCurrentPassword.setStyle("-fx-border-width: 1px; -fx-border-color: red;");
            profile_newPassword.setStyle("-fx-border-width: 1px; -fx-border-color: red;");

        } else if (confirmCurrentPassword.isEmpty()){
            profile_confirmCurrentPassword.setStyle("-fx-border-width: 1px; -fx-border-color: red;");
            profile_newPassword.setStyle("-fx-border-color: linear-gradient(to right, #40C5CF, #2ca772); -fx-border-width: 0.2px;");


        } else if (newPassword.isEmpty()){
            profile_newPassword.setStyle("-fx-border-width: 1px; -fx-border-color: red;");
            profile_confirmCurrentPassword.setStyle("-fx-border-color: linear-gradient(to right, #40C5CF, #2ca772); -fx-border-width: 0.2px;");

        } else {
            if (!Objects.equals(currentPassword, confirmCurrentPassword)) { // makes sure the user remembers the old password for verification
                profile_confirmCurrentPassword.setStyle("-fx-border-width: 1px; -fx-border-color: red;");
                profile_newPassword.setStyle("-fx-border-color: linear-gradient(to right, #40C5CF, #2ca772); -fx-border-width: 0.2px;");


            } else {
                Model.getInstance().getConnectDB().teacherChangePassword(teacherID, newPassword);
                profile_newPassword.setStyle("-fx-border-width: 1px; -fx-border-color: green;");
                profile_confirmCurrentPassword.clear();
                profile_newPassword.clear();
                profile_changePasswordIcon.setVisible(true);
                resetStyleTimer(); // reset the style after success notifier

            }
        }
    }


    // Binds the teacher data to the profile
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

    // timer to execute the resetConfirmStyles() method
    private void resetStyleTimer() {
        final Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        resetConfirmStyles();
                        timer.cancel();
                    }
                },
                5000
        );
    }


    // reset all styles to default
    private void resetConfirmStyles() {
        profile_contactInfoSaveIcon.setVisible(false);
        profile_changePasswordIcon.setVisible(false);
        profile_confirmCurrentPassword.setStyle("-fx-border-color: linear-gradient(to right, #40C5CF, #2ca772); -fx-border-width: 0.2px;");
        profile_newPassword.setStyle("-fx-border-color: linear-gradient(to right, #40C5CF, #2ca772); -fx-border-width: 0.2px;");
    }
}
