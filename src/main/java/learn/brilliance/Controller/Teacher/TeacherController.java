package learn.brilliance.Controller.Teacher;

import javafx.fxml.Initializable;
import learn.brilliance.Model.Model;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {
    public BorderPane teacher_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getTeacherSelectedMenuOption().addListener((observable, oldVal, newVal) -> {
            switch (newVal) {
                case OVERVIEW -> teacher_parent.setCenter(Model.getInstance().getViewFactory().getTeachersOverviewView());
                case COURSES -> teacher_parent.setCenter(Model.getInstance().getViewFactory().getTeacherCoursesView());
                case PROFILE -> teacher_parent.setCenter(Model.getInstance().getViewFactory().getTeacherProfileView());
                case MESSAGES -> teacher_parent.setCenter(Model.getInstance().getViewFactory().getTeacherMessagesView());
                case SETTINGS -> teacher_parent.setCenter(Model.getInstance().getViewFactory().getTeacherSettingsView());
            }
        });
    }
}
