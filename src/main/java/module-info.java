module com.example.trafficlights {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trafficlights to javafx.fxml;
    exports com.example.trafficlights;
}