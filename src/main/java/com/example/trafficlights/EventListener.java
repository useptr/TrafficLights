package com.example.trafficlights;

public interface EventListener {
    void update(String eventType, int time); // метод вызывающийся у всех слушателей после уведомления о событии
}
