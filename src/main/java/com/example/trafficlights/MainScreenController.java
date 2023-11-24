package com.example.trafficlights;

import com.example.trafficlights.listeners.TrafficLightStateListener;
import com.example.trafficlights.listeners.TrafficLightTimeLeftListener;
import com.example.trafficlights.models.TextFieldContainInt;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainScreenController {
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
    } // преоброзование TextField в продолжительность фазы
    private TrafficLightsControl trafficLightsControl = new TrafficLightsControl(); // сфетофор
    @FXML
    private void initialize() {
        ComboBoxStates.setItems(FXCollections.observableArrayList(TrafficLightsControl.Event.RED, TrafficLightsControl.Event.RED_YELLOW, TrafficLightsControl.Event.YELLOW, TrafficLightsControl.Event.GREEN, TrafficLightsControl.Event.GREEN_BLINKING, TrafficLightsControl.Event.LAST_YELLOW));
        HBoxTrafficLights.getChildren().add(trafficLightsControl);
        stateListener = new TrafficLightStateListener(labelState);
        trafficLightsControl.events.subscribeAll(stateListener);
        timeLeftListener = new TrafficLightTimeLeftListener(labelTimeLeft);
        trafficLightsControl.events.subscribeAll(timeLeftListener);
//        editor.events.subscribe("yellow blinking light is on",this);
//        editor.events.subscribe("red light is on",this);
//        editor.events.subscribe("red and yellow light is on",this);
//        editor.events.subscribe("yellow light is on",this);
//        editor.events.subscribe("green light is on",this);
//        editor.events.subscribe("green blinking light is on",this);
//        editor.events.subscribe("yellow last light is on",this);
    }
    @FXML
    private void textFieldStateUpdate() {
        TrafficLightsControl.Event state = (TrafficLightsControl.Event)ComboBoxStates.getValue();
        boolean invalidValue = true;
        if (state != null) {
            TextFieldContainInt textFieldContainInt = parseStageDuration(TextFieldState);
            if (textFieldContainInt.containInt) {
                invalidValue = false;
                trafficLightsControl.setStateDuration(state, textFieldContainInt.value);
            }
        }
        if (invalidValue)
            LabelInvalidInput.setVisible(true);
        else
            LabelInvalidInput.setVisible(false);
    } // обработка нажатия на кнопку обновить продолжительность
    @FXML
    private void toggleButtonTrafficLightStateClicked() {
        if (ToggleButtonTrafficLightState.isSelected()) {
            ToggleButtonTrafficLightState.setText("Вкл.");
            trafficLightsControl.on();
        }
        else {
            ToggleButtonTrafficLightState.setText("Выкл.");
            trafficLightsControl.reset();
        }
    } // обработка вкл/выкл светофора
    private TrafficLightStateListener stateListener; // слушатель изменения фазы светофора
    private TrafficLightTimeLeftListener timeLeftListener; // слушатель оставшегося времени фазы светофора
    @FXML
    private VBox VBoxTrafficLights;
    @FXML
    private VBox VBoxRoot;
    @FXML
    private HBox HBoxTrafficLights;
    @FXML
    private ToggleButton ToggleButtonTrafficLightState;
    @FXML
    private Label labelTimeLeft;
    @FXML
    private Label labelState;
    @FXML
    private Label LabelInvalidInput;
    @FXML
    private TextField TextFieldState;
    @FXML
    private Button ButtonUpdateState;
    @FXML
    private ComboBox ComboBoxStates;

}