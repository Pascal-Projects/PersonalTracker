module com.pascalprojects.personaltracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pascalprojects.personaltracker to javafx.fxml;
    exports com.pascalprojects.personaltracker;
}