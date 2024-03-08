package learn.brilliance.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import learn.brilliance.Controller.Admin.AdminMenuController;
import learn.brilliance.Controller.Teacher.TeacherController;

public class ViewFactory {
    // Admin view
    private AnchorPane dashboardView;
    public ViewFactory(){}
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

    // how the login window
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
        stage.show();
    }
}
