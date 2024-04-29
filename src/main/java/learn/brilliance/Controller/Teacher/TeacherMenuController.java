package learn.brilliance.Controller.Teacher;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import learn.brilliance.Model.Model;
import learn.brilliance.View.Enums.TeacherMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherMenuController implements Initializable {
    public Label teacher_section;
    public Button teacher_overviewBtn;
    public Button teacher_coursesBtn;
    public Button teacher_profileBtn;
    public Button teacher_messagesBtn;
    public Button teacher_settingsBtn;
    public Button teacher_signoutBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListeners();
    }

    private void addListeners() {
        teacher_overviewBtn.setOnAction(e -> onOverview());
        teacher_coursesBtn.setOnAction(e -> onCourses());
        teacher_profileBtn.setOnAction(e -> onProfile());
        teacher_messagesBtn.setOnAction(e -> onMessages());
        teacher_settingsBtn.setOnAction(e -> onSettings());
        teacher_signoutBtn.setOnAction(e -> onLogout());

    }

    private void onOverview() {
        Model.getInstance().getViewFactory().getTeacherSelectedMenuOption().set(TeacherMenuOptions.OVERVIEW);
        sectionTitleSetter();
    }
    private void onCourses() {
        Model.getInstance().getViewFactory().getTeacherSelectedMenuOption().set(TeacherMenuOptions.COURSES);
        sectionTitleSetter();
    }
    private void onProfile() {
        Model.getInstance().getViewFactory().getTeacherSelectedMenuOption().set(TeacherMenuOptions.PROFILE);
        sectionTitleSetter();
    }
    private void onMessages() {
        Model.getInstance().getViewFactory().getTeacherSelectedMenuOption().set(TeacherMenuOptions.MESSAGES);
        sectionTitleSetter();
    }
    private void onSettings() {
        Model.getInstance().getViewFactory().getTeacherSelectedMenuOption().set(TeacherMenuOptions.SETTINGS);
        sectionTitleSetter();
    }
    private void onLogout() {
        Stage stage = (Stage)teacher_section.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().setTeacherLoginStatus(false);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    private void sectionTitleSetter() {
        switch (Model.getInstance().getViewFactory().getTeacherSelectedMenuOption().get()) {
            case OVERVIEW -> teacher_section.setText("OVERVIEW");
            case COURSES -> teacher_section.setText("COURSES");
            case PROFILE -> teacher_section.setText("PROFILE");
            case MESSAGES -> teacher_section.setText("MESSAGES");
            default -> teacher_section.setText("SETTINGS");
        }
        teacher_section.setStyle("-fx-font-size: 18px");
    }
}
