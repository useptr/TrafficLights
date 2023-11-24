package com.example.trafficlights.interfaces;

import com.example.trafficlights.TrafficLightsControl;

public interface TrafficLightListener {
    void on();
    void reset();
    void setState(TrafficLightsControl.Event state);
    TrafficLightsControl.Event state();
    void setStateDuration(TrafficLightsControl.Event state, int duration);
}
