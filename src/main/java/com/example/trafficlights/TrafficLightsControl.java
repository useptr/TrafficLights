package com.example.trafficlights;

import com.example.trafficlights.interfaces.TrafficLightListener;
import com.example.trafficlights.models.TrafficLightEvent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class TrafficLightsControl extends AnchorPane implements TrafficLightListener {
    public enum Event {
        RED("Красная", 0), RED_YELLOW("Красный/Желтый", 1), YELLOW("Желтый", 2), GREEN("Зеленый",3), GREEN_BLINKING("Зеленый мигающий", 4), LAST_YELLOW("Последний желтый",5), YELLOW_BLINKING("Желтый мигающий",6);
        public final String name;
        public final int index;
        Event(String name, int index) {
            this.name = name;
            this.index = index;
        }
    } // события светофора
    public TrafficLightEventManager events = new TrafficLightEventManager(Event.RED, Event.RED_YELLOW, Event.YELLOW, Event.GREEN, Event.GREEN_BLINKING, Event.LAST_YELLOW, Event.YELLOW_BLINKING);
    private int[] stateDurations = {3, 5, 7, 10, 12, 14}; // продолжительности фаз
    private final int statesSize = 6; // количество фаз
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::stateHandler));
    private boolean isOn = true; // состояние светофора вкл/выкл
    private int countdown = 0; // время светофора
    public TrafficLightComponent trafficLight; // fxml view
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

            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } // конструктор
    @Override
    public void on() {
        isOn = true;
        state = Event.RED;
        countdown = 0;
    }

    @Override
    public void reset() {
        isOn = false;
        state = Event.YELLOW_BLINKING;
    }

    @Override
    public void setState(Event state) {
        if (state.index < statesSize) {
            if (state.index > 0)
                countdown = stateDurations[state.index-1];
            else
                countdown =0;
        }
        this.state = state;
    }
    @Override
    public Event state() {
        return state;
    }
    @Override
    public void setStateDuration(Event state, int duration) {
        if (state.index < statesSize) {
            if (state.index == 0)
                stateDurations[state.index] = duration;
            else {
//                System.out.println(stateDurations[0] + " " + stateDurations[1] + " " + stateDurations[2] + " " + stateDurations[3] + " " + stateDurations[4] + " " + stateDurations[5] + " ");
                int prevDuration = stateDurations[state.index]-stateDurations[state.index - 1];
                int dif = duration-prevDuration;

                for (int i = state.index; i < statesSize; ++i)
                    stateDurations[i] += dif;
//                System.out.println(stateDurations[0] + " " + stateDurations[1] + " " + stateDurations[2] + " " + stateDurations[3] + " " + stateDurations[4] + " " + stateDurations[5] + " ");
            }
        }
    } // установка продолжительности фазы
    private String getMessage(Event prevState, Event curState) {
        if (prevState != curState) {
            return  "Фаза светофора изменилась с "+prevState.name+" на " + curState.name;
        }
        return "Продолжается фаза " + curState.name;
    }
    private Event state = Event.RED; // текущая фаза
    private Event prevState = state; // предыдущая фаза
    public void stateHandler(ActionEvent event) {
        if (isOn) {
            if (countdown >= stateDurations[5])
                countdown = 0;

            prevState = state;
            if (countdown < stateDurations[0]) {
                trafficLight.onRed();
                state = Event.RED;
            } else if (countdown < stateDurations[1]) {
                trafficLight.onRedYellow();
                state = Event.RED_YELLOW;
            } else if (countdown < stateDurations[2]) {
                trafficLight.onYellow();
                state = Event.YELLOW;
            } else if (countdown < stateDurations[3]) {
                trafficLight.onGreen();
                state = Event.GREEN;
            } else if (countdown < stateDurations[4]) {
                trafficLight.onGreenBlinking();
                state = Event.GREEN_BLINKING;
            } else if (countdown < stateDurations[5]) {
                trafficLight.onLastYellow();
                state = Event.LAST_YELLOW;
            }
            int timeLeft = stateDurations[state.index]-countdown;
            TrafficLightEvent trafficLightEvent = new TrafficLightEvent( getMessage(prevState, state), timeLeft);
            events.notify(state, trafficLightEvent);
            ++countdown;
        } else {
            TrafficLightEvent trafficLightEvent = new TrafficLightEvent(getMessage(state, state), -1);
            trafficLight.onYellowBlinking();
            events.notify(state, trafficLightEvent);
        }
    } // генерирование события в зависимости от фазы
}
