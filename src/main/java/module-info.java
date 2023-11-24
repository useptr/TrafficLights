module com.example.trafficlights {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trafficlights to javafx.fxml;
    exports com.example.trafficlights;
    exports com.example.trafficlights.interfaces;
    opens com.example.trafficlights.interfaces to javafx.fxml;
    exports com.example.trafficlights.models;
    opens com.example.trafficlights.models to javafx.fxml;
}