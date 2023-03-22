module main.laser_adventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens main to javafx.fxml;
    exports main;
}