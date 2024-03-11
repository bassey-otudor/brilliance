package learn.brilliance.Model;

import learn.brilliance.View.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    DatabaseConnector databaseConnector;

    // Admin variables
    private boolean adminLoginStatus;
    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseConnector = new DatabaseConnector();
        this.adminLoginStatus = false;
    }
    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }


    // Admin login  control
    public boolean getAdminLoginStatus() {
        return this.adminLoginStatus;
    }
    public void setAdminLoginStatus(boolean adminLoginStatus) {
        this.adminLoginStatus = adminLoginStatus;
    }

    public ResultSet evaluateAdminLogin(String username, String password) {
        ResultSet resultSet = databaseConnector.getAdmin(username, password);
        try {
            if(resultSet.isBeforeFirst()) {
                this.adminLoginStatus = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
