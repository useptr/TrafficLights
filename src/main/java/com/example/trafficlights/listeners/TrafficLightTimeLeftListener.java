package com.example.trafficlights.listeners;

import com.example.trafficlights.models.TrafficLightEvent;
import com.example.trafficlights.interfaces.TrafficLightEventListener;
import com.example.trafficlights.TrafficLightsControl;
import javafx.scene.control.Label;

public class TrafficLightTimeLeftListener implements TrafficLightEventListener {
    private Label label;
    public TrafficLightTimeLeftListener(Label label) {
        this.label = label;
    }
    @Override
    public void update(TrafficLightsControl.Event eventType, TrafficLightEvent event) {
        if (event.timeLeft == -1)
            label.setText("Текущая фаза будет длиться бесконечно");
        else
            label.setText("Осталось времени текущей фазы: " + event.timeLeft);
    }
}
