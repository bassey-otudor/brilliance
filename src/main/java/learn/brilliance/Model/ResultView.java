package learn.brilliance.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResultView {
    private final StringProperty deptID;
    private final StringProperty progressLabel;

    public ResultView(String deptID, String progressLabel) {
        this.deptID = new SimpleStringProperty(this, "departmentID", deptID);
        this.progressLabel = new SimpleStringProperty(this, "progressLabel", progressLabel);
    }

    public StringProperty deptIDProperty() { return deptID; }
    public StringProperty progressLabelProperty() { return progressLabel; }
}
