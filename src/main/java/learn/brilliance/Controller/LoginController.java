package learn.brilliance.Controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import learn.brilliance.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField loginID;
    public TextField password;
    public CheckBox rememberMe;
    public Hyperlink forgotPwd;
    public Button loginBtn;
    public Label errorLabel;
    public Button facebookBtn;
    public Button linkedInBtn;
    public Button twitterBtn;
    public Button stud_createAccBtn;
    public ChoiceBox acc_selector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction(e -> Model.getInstance().getViewFactory().showAdminWindow());
    }
}
