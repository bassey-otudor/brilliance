package learn.brilliance.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import learn.brilliance.Controller.Admin.Dashboard.TeacherSuccessCellController;
import learn.brilliance.Model.TeacherSuccess;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherSuccessCellFactory extends ListCell<TeacherSuccess> {
    @Override
    public void updateItem(TeacherSuccess teacherSuccess, boolean empty) {
        super.updateItem(teacherSuccess, empty);
        if(empty) {
            setText(null);
            setGraphic(null);

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard/TeacherSuccessCell.fxml"));
            TeacherSuccessCellController controller = new TeacherSuccessCellController(teacherSuccess);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());

            } catch (IOException ex) {
                Logger.getLogger(TeacherSuccessCellFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
