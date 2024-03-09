package learn.brilliance.View;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import learn.brilliance.Controller.Admin.AdminMenuController;
import learn.brilliance.Controller.Student.StudentController;
import learn.brilliance.Controller.Teacher.TeacherController;
import learn.brilliance.View.Enums.AccountType;
import learn.brilliance.View.Enums.AdminMenuOptions;

public class ViewFactory {
    private AccountType accountType;
    // Admin view variables
    private final ObjectProperty<AdminMenuOptions> adminSelectedOption;
    private AnchorPane dashboardView;
    private AnchorPane teachersView;
    private AnchorPane studentsView;
    private AnchorPane departmentView;
    private AnchorPane facultiesView;
    private AnchorPane coursesView;
    private AnchorPane degreesView;
    private AnchorPane minorsView;
    private AnchorPane reportsView;
    public ViewFactory(){
        this.accountType = AccountType.ADMIN;
        this.adminSelectedOption = new SimpleObjectProperty<>();
    }

    // Admin Menu option getters
    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public ObjectProperty<AdminMenuOptions> getAdminSelectedOption() {
        return adminSelectedOption;
    }
    public AnchorPane getDashboardView() {
        if(dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }
    public AnchorPane getTeachersView() {
        if(teachersView == null) {
            try {
                teachersView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Teachers.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return teachersView;
    }
    public AnchorPane getStudentsView() {
        if (studentsView == null) {
          try {
              studentsView = new FXMLLoader(getClass().getResource("Fxml/Admin/Students.fxml")).load();
          } catch (Exception e) {
              e.printStackTrace();
          }
        }
        return studentsView;
    }
    public AnchorPane getDepartmentView() {
        if(departmentView == null) {
            try {
                departmentView = new FXMLLoader(getClass().getResource("Fxml/Admin/departments.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return departmentView;
    }
    public AnchorPane getFacultiesView() {
        if (facultiesView == null) {
            try {
                facultiesView = new FXMLLoader(getClass().getResource("Fxml/Admin/Faculties.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return facultiesView;
    }
    public AnchorPane getCoursesView() {
        if (coursesView == null) {
            try {
                coursesView = new FXMLLoader(getClass().getResource("Fxml/Admin/Courses.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return coursesView;
    }
    public AnchorPane getDegreesView() {
        if (degreesView == null) {
            try {
                degreesView = new FXMLLoader(getClass().getResource("Fxml/Admin/Degrees.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return degreesView;
    }
    public AnchorPane getMinorsView() {
        if (minorsView == null) {
            try {
                minorsView = new FXMLLoader(getClass().getResource("Fxml/Admin/Minors.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return minorsView;
    }
    public AnchorPane getReportsView() {
        if (reportsView == null) {
            try {
                reportsView = new FXMLLoader(getClass().getResource("Fxml/Admin/Reports.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } return reportsView;
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
        AdminMenuController adminMenuController = new AdminMenuController();
        loader.setController(adminMenuController);
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
    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Brilliance!");
        stage.setResizable(false);
        stage.show();
    }

    // Closes the login window
    public void closeStage(Stage stage) {
        stage.close();
    }
}
