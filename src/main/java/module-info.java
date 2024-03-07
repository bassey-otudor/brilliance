module com.learn.briliance {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.learn.briliance to javafx.fxml;
    exports com.learn.briliance;
}