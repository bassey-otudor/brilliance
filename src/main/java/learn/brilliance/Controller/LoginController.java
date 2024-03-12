package learn.brilliance.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import learn.brilliance.Model.Model;
import learn.brilliance.View.Enums.AccountType;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField loginID;
    public PasswordField password;
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
        getRemember();
        rememberMe.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
             if(newVal) {
                 setRememberMe();
             } else {
                 removeRememberMe();
             }
         });

    }

    // Login control function
    private void onLogin() {
        Stage stage = (Stage)errorLabel.getScene().getWindow();
        inputValidator(stage);
    }

    // Makes sure all fields are filled
    private void inputValidator(Stage stage) {
        if(loginID.getText().isEmpty() && password.getText().isEmpty()) {
            loginID.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");
            password.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");

        } else if (loginID.getText().isEmpty()) {
            loginID.setStyle("-fx-border-color:  #EC6666 ; -fx-border-width: 1px ;");
            password.setStyle("-fx-border-color: #40C5CF; -fx-border-width: 1px ;");

        } else if (password.getText().isEmpty()) {
            loginID.setStyle("-fx-border-color:  #40C5CF ; -fx-border-width: 1px ;");
            password.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");

        } else {
            if(Model.getInstance().getViewFactory().getAccountType() == AccountType.ADMIN) {
                Model.getInstance().evaluateAdminLogin(loginID.getText(), password.getText());
                if(Model.getInstance().getAdminLoginStatus()) {
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showAdminWindow();
                } else {
                    errorLabel.setText("Invalid Credentials. Please try again.");
                }

            } else if (Model.getInstance().getViewFactory().getAccountType() == AccountType.STUDENT) {
                Model.getInstance().getViewFactory().showStudentWindow();
            } else {
                Model.getInstance().getViewFactory().showTeacherWindow();
            }
        }
    }

    // This method sets which account to log in to.
    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setAccountType(acc_selector.getValue());
        // Set the appropriate user prompt
        if(acc_selector.getValue() == AccountType.ADMIN) {
            loginID.setPromptText("Username");
            clearFields();

        } else if (acc_selector.getValue() == AccountType.STUDENT) {
            loginID.setPromptText("Student ID");
            clearFields();

        } else {
            loginID.setPromptText("Teacher ID");
            clearFields();
        }
    }
    private void getRemember() {
        String filePath = "C:\\Users\\Bassey\\Documents\\Java Projects\\brilliance\\src\\main\\resources\\Settings\\settings.cfg";
        File file = new File(filePath);

        // check if the file exists and sets
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

                // stores the contents of the read file in an array of strings.
                String[] contents = reader.readLine().split("-");

                if (contents[0].equals("true")) {
                    this.rememberMe.setSelected(true);
                    this.loginID.setText(contents[1]);
                    switch (contents[2]) {
                        case "ADMIN" -> acc_selector.setValue(AccountType.ADMIN);
                        case "STUDENT" -> acc_selector.setValue(AccountType.STUDENT);
                        case "TEACHER" -> acc_selector.setValue(AccountType.TEACHER);
                    }
                } else {
                    acc_selector.setValue(AccountType.STUDENT);
                }

            } catch (IOException e) {
                System.out.println("File not found.");
                e.printStackTrace();
            }

        } else {
            File settings = new File(filePath);
            try {
                if (settings.createNewFile()) {
                    String[] contents = new String[3];
                    contents[0] = String.valueOf(rememberMe.isSelected());
                    contents[1] = loginID.getText();

                    if(acc_selector.getValue().equals(AccountType.ADMIN)) {
                        contents[2] = "ADMIN";
                    } else if(acc_selector.getValue().equals(AccountType.STUDENT)) {
                        contents[2] = "STUDENT";
                    } else if(acc_selector.getValue().equals(AccountType.TEACHER)) {
                        contents[2] = "TEACHER";
                    }

                    FileWriter writer = new FileWriter(filePath);
                    writer.write(contents[0] + "-" + contents[1] + "-" + contents[2]);
                    writer.close();
                }

            } catch (IOException e) {
                System.out.println("Error creating file");
                e.printStackTrace();
            }

        }

    }

    // Save the user's settings
    private void setRememberMe() {
        String filePath = "C:\\Users\\Bassey\\Documents\\Java Projects\\brilliance\\src\\main\\resources\\Settings\\settings.cfg";
        String[] contents = new String[3];
        if(loginID.getText().isEmpty()) {
            loginID.setStyle("-fx-border-color: #EC6666 ; -fx-border-width: 1px ;");

        } else{
            contents[0] = String.valueOf(rememberMe.isSelected());
            contents[1] = loginID.getText();
            contents[2] = String.valueOf(acc_selector.getValue());

            try {
                FileWriter writer = new FileWriter(filePath, false);
                writer.write(contents[0] + "-" + contents[1] + "-" + contents[2]);
                writer.close();

            } catch (Exception e) {
                System.out.println("Error saving settings");
                e.printStackTrace();
            }
        }

    }

    private void removeRememberMe() {
        String filePath = "C:\\Users\\Bassey\\Documents\\Java Projects\\brilliance\\src\\main\\resources\\Settings\\settings.cfg";
        try {
            FileWriter writer = new FileWriter(filePath, false);
            writer.write(String.valueOf(rememberMe.isSelected()));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void clearFields() {
        loginID.setText("");
        password.setText("");
    }
}
