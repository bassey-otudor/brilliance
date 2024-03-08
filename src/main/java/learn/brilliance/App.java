package learn.brilliance;
import javafx.application.Application;
import javafx.stage.Stage;
import learn.brilliance.Model.Model;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}