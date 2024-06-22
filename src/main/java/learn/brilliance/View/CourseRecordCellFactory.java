package learn.brilliance.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import learn.brilliance.Controller.Teacher.Overview.CourseRecordCellController;
import learn.brilliance.Model.CourseRecord;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseRecordCellFactory extends ListCell<CourseRecord> {
    @Override
    protected void updateItem(CourseRecord courseRecord, boolean empty) {
        super.updateItem(courseRecord, empty);
        if (empty) {
            setText(null);
            setGraphic(null);

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Overview/CourseRecordCell.fxml"));
            CourseRecordCellController controller = new CourseRecordCellController(courseRecord);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());

            } catch (IOException ex) {
                Logger.getLogger(CourseRecordCellFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
