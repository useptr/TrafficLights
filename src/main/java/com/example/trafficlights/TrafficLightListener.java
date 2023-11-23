package com.example.trafficlights;

public interface TrafficLightListener {
    void on();
    void reset();
    void setState(int state);
    int state();
    void setStateDuration(int state, int duration);
}
