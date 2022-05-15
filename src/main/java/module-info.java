module com.example.molikoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.molikoe to javafx.fxml;
    exports com.example.molikoe;
}