package com.example.trafficlights.models;

import com.example.trafficlights.EventManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class TrafficLight {
    public EventManager events = new EventManager("yellow blinking light is on",
            "red light is on",
            "red and yellow light is on",
            "yellow light is on",
            "green light is on",
            "green blinking light is on",
            "yellow last light is on");
    int[] stageDurations = {3, 5, 7, 10, 12, 14};
    private TrafficLightsStage lastStage = TrafficLightsStage.YELLOW_BLINKING;
    private boolean isOn = false;
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::stageHandler));
    private int countdown = 0;

    public TrafficLight() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void enableTrafficLight(boolean status) {
        if (status != isOn) {
            if (isOn) {
                events.notify("yellow blinking light is on", countdown);
                lastStage = TrafficLightsStage.YELLOW_BLINKING;
                countdown =0;
            } else {
                events.notify("red light is on", countdown);
                lastStage = TrafficLightsStage.RED;
            }
            isOn = status;
        }
    }
    public void updateStageDurations(ArrayList<Integer> durations) {
        stageDurations[0] = durations.get(0);
        for (int i = 1; i<durations.size(); ++i) {
            stageDurations[i] = stageDurations[i-1] +durations.get(i);
        }
//        stageDurations[1] = stageDurations[0]+ durations.get(1);
//        stageDurations[2] = stageDurations[1]+durations.get(2);
//        stageDurations[3] = stageDurations[2]+durations.get(3);
//        stageDurations[4] = stageDurations[3]+durations.get(4);
//        stageDurations[5] = stageDurations[4]+durations.get(5);
        countdown =0;
        //  countdown = getStageByTime();
    }
    public void stageHandler(ActionEvent event) {
        if (isOn) {
            ++countdown;
            if (countdown > stageDurations[5])
                countdown = 0;

            if (isRedStage()) {
//                if (lastStage != TrafficLightsStage.RED) {
                    events.notify("red light is on", stageDurations[0]-countdown);
                    lastStage = TrafficLightsStage.RED;
//                }
            } else if (isRedYellowStage()) {
//                if (lastStage != TrafficLightsStage.RED_YELLOW) {
                    events.notify("red and yellow light is on", stageDurations[1]-countdown);
                    lastStage = TrafficLightsStage.RED_YELLOW;
//                }
            } else if (isYellowStage()) {
//                if (lastStage != TrafficLightsStage.YELLOW) {
                    events.notify("yellow light is on", stageDurations[2]-countdown);
                    lastStage = TrafficLightsStage.YELLOW;
//                }
            } else if (isGreenStage()) {
//                if (lastStage != TrafficLightsStage.GREEN) {
                    events.notify("green light is on", stageDurations[3]-countdown);
                    lastStage = TrafficLightsStage.GREEN;
//                }
            } else if (isGreenBlinkingStage()) {
//                if (lastStage != TrafficLightsStage.GREEN_BLINKING) {
                    events.notify("green blinking light is on", stageDurations[4]-countdown);
                    lastStage = TrafficLightsStage.GREEN_BLINKING;
//                }
            } else if (isLastYellowStage()) {
//                if (lastStage != TrafficLightsStage.LAST_YELLOW) {
                    events.notify("yellow last light is on", stageDurations[5]-countdown);
                    lastStage = TrafficLightsStage.LAST_YELLOW;
//                }
            }
        } else {
//            if (lastStage != TrafficLightsStage.YELLOW_BLINKING) {
                events.notify("yellow blinking light is on", -1);
                lastStage = TrafficLightsStage.YELLOW_BLINKING;
//            }
        }
    }
    public boolean isRedStage() {
        if (countdown < stageDurations[0])
            return true;
        return false;
    }
    public boolean isRedYellowStage() {
        if (countdown < stageDurations[1])
            return true;
        return false;
    }
    public boolean isYellowStage() {
        if (countdown < stageDurations[2])
            return true;
        return false;
    }
    public boolean isGreenStage() {
        if (countdown < stageDurations[3])
            return true;
        return false;
    }
    public boolean isGreenBlinkingStage() {
        if (countdown < stageDurations[4])
            return true;
        return false;
    }
    public boolean isLastYellowStage() {
        if (countdown < stageDurations[5])
            return true;
        return false;
    }

}
