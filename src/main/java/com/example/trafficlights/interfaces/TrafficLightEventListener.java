package com.example.trafficlights.interfaces;

import com.example.trafficlights.models.TrafficLightEvent;
import com.example.trafficlights.TrafficLightsControl;

public interface TrafficLightEventListener {
    void update(TrafficLightsControl.Event eventType, TrafficLightEvent event);
    // метод вызывающийся у всех слушателей после уведомления о событии
}
