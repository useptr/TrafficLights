package com.example.trafficlights;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class TrafficLightsControl extends AnchorPane implements TrafficLightListener {
//    private enum TrafficLightEvent {
//        RED, RED_YELLOW, YELLOW, GREEN, GREEN_BLINKING, LAST_YELLOW, YELLOW_BLINKING
//    }
    public static final int RED = 0, RED_YELLOW = 1, YELLOW = 2, GREEN = 3, GREEN_BLINKING = 4, LAST_YELLOW = 5, YELLOW_BLINKING = 6;
    private int[] stateDurations = {3, 5, 7, 10, 12, 14};
    private final int statesSize = 6;
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::stateHandler));
    private boolean isOn = false;
    private int countdown = 0;
    public TrafficLightComponent trafficLight;
    public TrafficLightsControl() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("traffic-light.fxml"));
            trafficLight = new TrafficLightComponent();
            loader.setController(trafficLight);
            Node node = loader.load();
            this.getChildren().add(node);

            this.setStyle("-fx-background-color: gray;");
            trafficLight.initTimelines();
            trafficLight.initBinds(this);
            trafficLight.onYellowBlinking();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void on() {
        isOn = true;
    }

    @Override
    public void reset() {
        isOn = false;
    }

    @Override
    public void setState(int state) {
        if (state < statesSize) {
            if (state > 0)
                countdown = stateDurations[state-1];
            else
                countdown =0;
        }
    }
    @Override
    public int state() {
        if (countdown < stateDurations[0])
            return RED;
        else if (countdown < stateDurations[1])
            return RED_YELLOW;
        else if (countdown < stateDurations[2])
            return YELLOW;
        else if (countdown < stateDurations[3])
            return GREEN;
        else if (countdown < stateDurations[4])
            return GREEN_BLINKING;
        else if (countdown < stateDurations[5])
            return LAST_YELLOW;
        return YELLOW_BLINKING;
    }

    @Override
    public void setStateDuration(int state, int duration) {
        if (state < statesSize) {
            stateDurations[state] = duration;
        }
    }
    public void stateHandler(ActionEvent event) {
        if (isOn) {
            ++countdown;
            if (countdown > stateDurations[5])
                countdown = 0;

            if (state() == RED) {

            } else if (state() == RED_YELLOW) {

            } else if (state() == YELLOW) {

            } else if (state() == GREEN) {

            } else if (state() == GREEN_BLINKING) {

            }
        } else {

        }
    }
}
