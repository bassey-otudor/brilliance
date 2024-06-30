package learn.brilliance.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import learn.brilliance.Controller.Teacher.Overview.TimetableCellController;
import learn.brilliance.Model.Timetable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimetableCellFactory extends ListCell<Timetable> {
    @Override
    protected void updateItem(Timetable timetable, boolean empty) {
        super.updateItem(timetable, empty);
        if (empty) {
            setText(null);
            setGraphic(null);

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Teacher/Overview/TimetableCell.fxml"));
            TimetableCellController controller = new TimetableCellController(timetable);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());

            } catch (IOException ex) {
                Logger.getLogger(TimetableCellController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
