package com.example.trafficlights.interfaces;

import com.example.trafficlights.TrafficLightsControl;

public interface TrafficLightBehaviour {
    void on();
    void reset();
    void setState(TrafficLightsControl.Event state);
    TrafficLightsControl.Event state(); // получить текущее состояние
    void setStateDuration(TrafficLightsControl.Event state, int duration);
}
