package learn.brilliance.Controller.Admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuOption().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case DASHBOARD -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                case TEACHERS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getTeachersView());
                case STUDENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getStudentsView());
                case DEPARTMENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDepartmentsView());
                case FACULTIES -> admin_parent.setCenter(Model.getInstance().getViewFactory().getFacultiesView());
                case COURSES -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCoursesView());
                case DEGREES -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDegreesView());
                case MINORS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getMinorsView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getSettingsView());
            }

        });
    }
}
