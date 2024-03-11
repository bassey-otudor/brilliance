package learn.brilliance.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import learn.brilliance.Model.Model;
import learn.brilliance.View.Enums.AccountType;

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
    public ChoiceBox<AccountType> acc_selector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.STUDENT, AccountType.TEACHER, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getAccountType());
        acc_selector.valueProperty().addListener(observable -> setAcc_selector());
        loginBtn.setOnAction(e -> onLogin());
    }

    // Login control function
    private void onLogin() {
        Stage stage = (Stage)errorLabel.getScene().getWindow();

        if(Model.getInstance().getViewFactory().getAccountType() == AccountType.ADMIN) {
            Model.getInstance().evaluateAdminLogin(loginID.getText(), password.getText());

            if (Model.getInstance().getAdminLoginStatus()) {
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showAdminWindow();
            } else {
                loginID.setText("");
                password.setText("");
                errorLabel.setText("Invalid login credentials");
            }
        } else if (Model.getInstance().getViewFactory().getAccountType() == AccountType.STUDENT) {
            Model.getInstance().getViewFactory().showStudentWindow();
        } else {
            Model.getInstance().getViewFactory().showTeacherWindow();
        }
    }

    // This method sets which account to log in to.
    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setAccountType(acc_selector.getValue());
        // Set the appropriate user prompt
        if(acc_selector.getValue() == AccountType.ADMIN) {
            loginID.setPromptText("Username");
        } else if (acc_selector.getValue() == AccountType.STUDENT) {
            loginID.setPromptText("Student ID");
        } else {
            loginID.setPromptText("Teacher ID");
        }
    }

}
