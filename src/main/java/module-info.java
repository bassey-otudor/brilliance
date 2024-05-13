module com.learn.briliance {
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.apache.commons.lang3;
    requires javafx.controls;
    requires javafx.fxml;
    requires jbcrypt;


    opens learn.brilliance to javafx.fxml;
    exports learn.brilliance;
    exports learn.brilliance.Controller;
    exports learn.brilliance.Controller.Admin;
    exports learn.brilliance.Controller.Teacher;
    exports learn.brilliance.View;
    exports learn.brilliance.View.Enums;
    exports learn.brilliance.Model;
    exports learn.brilliance.Controller.Admin.Dashboard;
    exports learn.brilliance.Model.Accounts;
}