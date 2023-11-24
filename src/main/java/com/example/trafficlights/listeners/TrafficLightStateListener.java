package com.example.trafficlights.listeners;

import com.example.trafficlights.models.TrafficLightEvent;
import com.example.trafficlights.interfaces.TrafficLightEventListener;
import com.example.trafficlights.TrafficLightsControl;
import javafx.scene.control.Label;

public class TrafficLightStateListener implements TrafficLightEventListener {
    private Label label;
    public TrafficLightStateListener(Label label) {
        this.label = label;
    }
    @Override
    public void update(TrafficLightsControl.Event eventType, TrafficLightEvent event) {
        label.setText("Текущая фаза: " + eventType.name);
    }
}
