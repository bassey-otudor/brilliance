package learn.brilliance.View;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import learn.brilliance.Controller.Admin.AdminController;
import learn.brilliance.Controller.Student.StudentController;
import learn.brilliance.Controller.Teacher.TeacherController;
import learn.brilliance.Model.connectRecord;
import learn.brilliance.View.Enums.AccountType;
import learn.brilliance.View.Enums.AdminMenuOptions;
import learn.brilliance.View.Enums.TeacherMenuOptions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewFactory {
    private AccountType accountType;

    // Admin view variables
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuOption;
    private AnchorPane dashboardView;
    private AnchorPane teachersView;
    private AnchorPane studentsView;
    private AnchorPane departmentView;
    private AnchorPane facultiesView;
    private AnchorPane coursesView;
    private AnchorPane degreesView;
    private AnchorPane minorsView;
    private AnchorPane settingsView;

    // Teacher view variables
    private final ObjectProperty<TeacherMenuOptions> teacherSelectedMenuOption;
    private AnchorPane teachersOverviewView;
    private AnchorPane teacherCoursesView;
    private AnchorPane teacherProfileView;
    private AnchorPane teacherMessagesView;
    private AnchorPane teacherSettingsView;

    public ViewFactory(){
        this.accountType = AccountType.ADMIN;
        this.adminSelectedMenuOption = new SimpleObjectProperty<>();
        this.teacherSelectedMenuOption = new SimpleObjectProperty<>();
    }

    // Admin Menu option getters
    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuOption() {
        return adminSelectedMenuOption;
    }
    public AnchorPane getDashboardView() {
        if(dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard/Dashboard.fxml")).load();
            } catch (IOException ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load admin dashboard.", ex);
            }
        }
        return dashboardView;
    }
    public AnchorPane getTeachersView() {
        if(teachersView == null) {
            try {
                teachersView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Teachers.fxml")).load();

            } catch (Exception ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load teachers view", ex);
            }
        }
        return teachersView;
    }
    public AnchorPane getStudentsView() {
        if (studentsView == null) {
          try {
              studentsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Students.fxml")).load();
          } catch (IOException ex) {
              Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load students view", ex);
          }
        }
        return studentsView;
    }
    public AnchorPane getDepartmentsView() {
        if(departmentView == null) {
            try {
                departmentView = new FXMLLoader(getClass().getResource("/Fxml/Admin/departments.fxml")).load();
            } catch (Exception ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load departments view", ex);
            }
        }
        return departmentView;
    }
    public AnchorPane getFacultiesView() {
        if (facultiesView == null) {
            try {
                facultiesView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Faculties.fxml")).load();
            } catch (Exception ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load faculties view", ex);
            }
        }
        return facultiesView;
    }
    public AnchorPane getCoursesView() {
        if (coursesView == null) {
            try {
                coursesView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Courses.fxml")).load();
            } catch (Exception ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load courses view", ex);
            }
        }
        return coursesView;
    }
    public AnchorPane getDegreesView() {
        if (degreesView == null) {
            try {
                degreesView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Degrees.fxml")).load();
            } catch (Exception ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load degrees view", ex);
            }
        }
        return degreesView;
    }
    public AnchorPane getMinorsView() {
        if (minorsView == null) {
            try {
                minorsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Minors.fxml")).load();
            } catch (Exception ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load minors view", ex);
            }
        }
        return minorsView;
    }
    public AnchorPane getSettingsView() {
        if (settingsView == null) {
            try {
                settingsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Settings.fxml")).load();
            } catch (Exception ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load settings page", ex);
            }
        } return settingsView;
    }

    // Teacher Menu option getters
    public ObjectProperty<TeacherMenuOptions> getTeacherSelectedMenuOption() {
        return teacherSelectedMenuOption;
    }
    public AnchorPane getTeachersOverviewView() {
        if (teachersOverviewView == null) {
            try {
                teachersOverviewView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Overview/Overview.fxml")).load();

            } catch (IOException ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load teacher Overview page.", ex);
            }
        }

        return teachersOverviewView;
    }
    public AnchorPane getTeacherCoursesView() {
        if (teacherCoursesView == null) {
            try {
                teacherCoursesView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Courses.fxml")).load();

            } catch (IOException ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load teacher Courses page.", ex);
            }
        }
        return teacherCoursesView;
    }
    public AnchorPane getTeacherProfileView() {
        if (teacherProfileView == null) {
            try {
                teacherProfileView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Profile.fxml")).load();

            } catch (IOException ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load teacher Profile page.", ex);
            }
        }
        return teacherProfileView;
    }
    public AnchorPane getTeacherMessagesView() {
        if (teacherMessagesView == null) {
            try {
                teacherMessagesView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Messages.fxml")).load();

            } catch (IOException ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load teacher Messages page.", ex);
            }
        }
        return teacherMessagesView;
    }
    public AnchorPane getTeacherSettingsView() {
        if (teacherSettingsView == null) {
            try {
                teacherSettingsView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Settings.fxml")).load();

            } catch (IOException ex) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, "Unable to load teacher Settings page.", ex);
            }
        }
        return teacherSettingsView;
    }
    /*
    * Utility methods
    **/

    // show the login window
    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    // Show admin section
    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    // Show teacher section
    public void showTeacherWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Teacher.fxml"));
        TeacherController teacherController = new TeacherController();
        loader.setController(teacherController);
        createStage(loader);
    }

    // Show student section
    public void showStudentWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Student/Student.fxml"));
        StudentController studentController = new StudentController();
        loader.setController(studentController);
        createStage(loader);
    }
    // Re-factored code for reuse when rendering windows
    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(connectRecord.class.getName()).log(Level.SEVERE, "Unable to load scene", ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Brilliance!");
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/hessay.png"))));
        stage.setResizable(false);
        stage.show();
    }

    // Closes the login window
    public void closeStage(Stage stage) {
        stage.close();
    }
}
