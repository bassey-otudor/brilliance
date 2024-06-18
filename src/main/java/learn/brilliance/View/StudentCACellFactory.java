package learn.brilliance.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import learn.brilliance.Controller.Teacher.Overview.StudentCACellController;
import learn.brilliance.Model.CourseRecord;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentCACellFactory extends ListCell<CourseRecord> {
    @Override
    protected void updateItem(CourseRecord courseCATotal, boolean empty) {
        super.updateItem(courseCATotal, empty);
        if (empty) {
            setText(null);
            setGraphic(null);

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Overview/StudentCACell.fxml"));
            StudentCACellController controller = new StudentCACellController(courseCATotal);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());

            } catch (IOException ex) {
                Logger.getLogger(StudentCACellFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
