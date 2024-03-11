package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import learn.brilliance.Model.Model;
import learn.brilliance.View.Enums.AdminMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Label admin_currentSection;
    public Button admin_dashboardBtn;
    public Button admin_teachersBtn;
    public Button admin_studentsBtn;
    public Button admin_departmentsBtn;
    public Button admin_facultiesBtn;
    public Button admin_coursesBtn;
    public Button admin_degreesBtn;
    public Button admin_minorsBtn;
    public Button admin_settingsBtn;
    public Button admin_logoutBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        admin_dashboardBtn.setOnAction(e -> onDashboard());
        admin_teachersBtn.setOnAction(e -> onTeachers());
        admin_studentsBtn.setOnAction(e -> onStudents());
        admin_departmentsBtn.setOnAction(e -> onDepartments());
        admin_facultiesBtn.setOnAction(e -> onFaculties());
        admin_coursesBtn.setOnAction(e -> onCourses());
        admin_degreesBtn.setOnAction(e -> onDegrees());
        admin_minorsBtn.setOnAction(e -> onMinors());
        admin_settingsBtn.setOnAction(e -> onSettings());
        admin_logoutBtn.setOnAction(e -> onLogout());
    }
    private void onDashboard() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.DASHBOARD);
    }
    private void onTeachers() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.TEACHERS);
    }
    private void onStudents() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.STUDENTS);
    }
    private void onDepartments() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.DEPARTMENTS);
    }
    private void onFaculties() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.FACULTIES);
    }
    private void onCourses() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.COURSES);
    }
    private void onDegrees() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.DEGREES);
    }
    private void onMinors() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.MINORS);
    }
    private void onSettings() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().set(AdminMenuOptions.SETTINGS);
    }
    // Returns the user to the login window
    private void onLogout() {
        Stage stage = (Stage)admin_currentSection.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().setAdminLoginStatus(false);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
