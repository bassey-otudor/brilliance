module com.learn.briliance {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
            
                            
    opens learn.brilliance to javafx.fxml;
    exports learn.brilliance;
    exports learn.brilliance.Controller;
    exports learn.brilliance.Controller.Admin;
    exports learn.brilliance.View;
    exports learn.brilliance.View.Enums;
    exports learn.brilliance.Model;
    exports learn.brilliance.Controller.Admin.Dashboard;

}