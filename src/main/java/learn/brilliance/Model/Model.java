package learn.brilliance.Model;

import learn.brilliance.Controller.Admin.FacultiesController;
import learn.brilliance.View.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    connectDB connectDB;
    FacultiesController facultiesController;

    // Admin variables
    private boolean adminLoginStatus;
    private Model() {
        this.viewFactory = new ViewFactory();
        this.connectDB = new connectDB();
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
    public connectDB getConnectDB() {
        return connectDB;
    }


    // Admin login  control
    public boolean getAdminLoginStatus() {
        return this.adminLoginStatus;
    }
    public void setAdminLoginStatus(boolean adminLoginStatus) {
        this.adminLoginStatus = adminLoginStatus;
    }

    public void evaluateAdminLogin(String username, String password) {
        ResultSet resultSet = connectDB.getAdmin(username, password);
        try {
            if(resultSet.isBeforeFirst()) {
                this.adminLoginStatus = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
