package com.example.trafficlights;

import com.example.trafficlights.models.TextFieldContainInt;
import com.example.trafficlights.models.TrafficLight;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class Controller implements EventListener {
    private TrafficLight editor = new TrafficLight();
    private ArrayList<TextField> durations = new ArrayList<>();
    private String lastTrafficLightStage;

    @FXML
    private void initialize() {
        double circleRadiusRatio = 3*2+0.1;
        RedCircle.radiusProperty().bind(VBoxRoot.heightProperty().divide(circleRadiusRatio));
        YellowCircle.radiusProperty().bind(VBoxRoot.heightProperty().divide(circleRadiusRatio));
        GreenCircle.radiusProperty().bind(VBoxRoot.heightProperty().divide(circleRadiusRatio));

        durations.add(TextFieldRedStage);
        durations.add(TextFieldRedYellowStage);
        durations.add(TextFieldYellowStage);

        durations.add(TextFieldGreenStage);
        durations.add(TextFieldGreenBlinkingStage);
        durations.add(TextFieldYellowLastStage);

        greenBlinkingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.8), new KeyValue(GreenCircle.fillProperty(), Color.GREEN)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(GreenCircle.fillProperty(), Color.WHITE))
        );
        greenBlinkingTimeline.setCycleCount(Animation.INDEFINITE);

        yellowBlinkingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.8), new KeyValue(YellowCircle.fillProperty(), Color.YELLOW)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(YellowCircle.fillProperty(), Color.WHITE))
        );
        yellowBlinkingTimeline.setCycleCount(Animation.INDEFINITE);
        yellowBlinkingTimeline.play();
        lastTrafficLightStage = "yellow blinking light is on";

        editor.events.subscribe("yellow blinking light is on",this);
        editor.events.subscribe("red light is on",this);
        editor.events.subscribe("red and yellow light is on",this);
        editor.events.subscribe("yellow light is on",this);
        editor.events.subscribe("green light is on",this);
        editor.events.subscribe("green blinking light is on",this);
        editor.events.subscribe("yellow last light is on",this);

//        yellowBlinkingTimeline.play();
//        timeline.play();
//        timeline.stop();
    }
    @FXML
    public VBox VBoxTrafficLights;
    @FXML
    public VBox VBoxRoot;
    @FXML
    public HBox HBoxTrafficLights;
    @FXML
    public Circle RedCircle;
    @FXML
    public Circle YellowCircle;
    @FXML
    public Circle GreenCircle;
    private Timeline greenBlinkingTimeline;
    private Timeline yellowBlinkingTimeline;
    @FXML
    public ToggleButton ToggleButtonTrafficLightState;
    @FXML
    public Label LabelStage;
    @FXML
    public Label LabelTimeLeft;
    @FXML
    public Label LabelInvalidInput;
    @FXML
    public TextField TextFieldRedStage;
    @FXML
    public TextField TextFieldRedYellowStage;
    @FXML
    public TextField TextFieldYellowStage;
    @FXML
    public TextField TextFieldGreenStage;
    @FXML
    public TextField TextFieldGreenBlinkingStage;
    @FXML
    public TextField TextFieldYellowLastStage;
    @FXML
    public Button ButtonUpdateStages;
    @FXML
    public void ToggleButtonTrafficLightStateAction() {
        if (ToggleButtonTrafficLightState.isSelected()) {
            ToggleButtonTrafficLightState.setText("Вкл.");
            editor.enableTrafficLight(true);
        }
        else {
            ToggleButtonTrafficLightState.setText("Выкл.");
            editor.enableTrafficLight(false);
        }

    }
    private TextFieldContainInt parseStageDuration(TextField textField) {
        TextFieldContainInt textFieldContainInt = new TextFieldContainInt();
        try {
            textFieldContainInt.value = Integer.parseInt(textField.getText().trim());
            if (textFieldContainInt.value < 1)
                textFieldContainInt.containInt = false;
            else
                textFieldContainInt.containInt = true;
        } catch (NumberFormatException e) {
            textFieldContainInt.containInt = false;
        }
        return textFieldContainInt;
    }
    @FXML
    public void ButtonUpdateStagesAction() {
        boolean existInvalidValue = false;
        ArrayList<Integer> durationValues = new ArrayList<>();
        for(TextField textField : durations) {
            TextFieldContainInt textFieldContainInt = parseStageDuration(textField);
            if (!textFieldContainInt.containInt) {
                existInvalidValue = true;
                break;
            }
            durationValues.add(textFieldContainInt.value);
        }
        if (existInvalidValue)
            LabelInvalidInput.setVisible(true);
        else {
            LabelInvalidInput.setVisible(false);
            editor.updateStageDurations(durationValues);
        }
    }
    @Override
    public void update(String eventType, int time) {
        if (!eventType.equals(lastTrafficLightStage)) {
            lastTrafficLightStage = eventType;
            if (eventType.equals("yellow blinking light is on")) {
                turnOnYellowBlinkingLight();
            } else if (eventType.equals("red light is on")) {
                turnOnRedLight();
            } else if (eventType.equals("red and yellow light is on")) {
                turnOnRedYellowLight();
            } else if (eventType.equals("yellow light is on")) {
                turnOnYellowLight();
            } else if (eventType.equals("green light is on")) {
                turnOnGreenLight();
            } else if (eventType.equals("green blinking light is on")) {
                turnOnGreenBlinkingLight();
            } else if (eventType.equals("yellow last light is on")) {
                turnOnLastYellowLight();
            }
        }
        if (lastTrafficLightStage.equals("yellow blinking light is on"))
            setTimeLeft("бесконечность");
        else
            setTimeLeft(time+ " с");
    }
    private void setTimeLeft(String left) {
        LabelTimeLeft.setText("Осталось времени текущей фазы: " + left);
    }
    private void setStage(String stage) {
        LabelStage.setText("Фаза: " + stage);
    }
    public void resetTrafficLightStages() {
        RedCircle.setFill(Color.WHITE);
        YellowCircle.setFill(Color.WHITE);
        GreenCircle.setFill(Color.WHITE);
        greenBlinkingTimeline.stop();
        yellowBlinkingTimeline.stop();
    }
    public void turnOnRedLight() {
        resetTrafficLightStages();
        RedCircle.setFill(Color.RED);
        setStage("Красный");
    }
    public void turnOnRedYellowLight() {
        resetTrafficLightStages();
        RedCircle.setFill(Color.RED);
        YellowCircle.setFill(Color.YELLOW);
        setStage("Красный/желтый");
    }
    public void turnOnYellowLight() {
        resetTrafficLightStages();
        YellowCircle.setFill(Color.YELLOW);
        setStage("Желтый");
    }
    public void turnOnGreenLight() {
        resetTrafficLightStages();
        GreenCircle.setFill(Color.GREEN);
        setStage("Зеленый");
    }
    public void turnOnGreenBlinkingLight() {
        resetTrafficLightStages();
        greenBlinkingTimeline.play();
        setStage("Зеленый мигающий");
    }
    public void turnOnLastYellowLight() {
        resetTrafficLightStages();
        YellowCircle.setFill(Color.YELLOW);
        setStage("Желтый");
    }
    public void turnOnYellowBlinkingLight() {
        resetTrafficLightStages();
        yellowBlinkingTimeline.play();
        setStage("Желтый мигающий");
    }
}