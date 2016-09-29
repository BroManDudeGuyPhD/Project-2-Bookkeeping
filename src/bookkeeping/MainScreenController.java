/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookkeeping;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author AndrewFossier
 */
public class MainScreenController implements Initializable {

    static EventHandler<ActionEvent> handleButtonAction;
    
    boolean timerOn = false;
    boolean timerPaused = true;
    int seconds = 0;
    int minutes = 0;
    
    EventHandler<ActionEvent> eventHandler = null;
    javafx.animation.Timeline timer = null;
    
    @FXML
    private Button button;
    @FXML
    private Label timerLabel;
    @FXML 
    private Button startButton;
    @FXML
    private CheckBox Problem1;
    @FXML
    private Label teamNumber;
    
    
    @FXML
    public void handleButtonAction(ActionEvent event) {

        if (!timerOn) {
            
            timer.play(); // Start timer
            timerOn = true;
            timerPaused = false;
            button.setText("Pause");
            startButton.setText("Pause All");
            timerLabel.setTextFill(Color.GREEN);

        } else if (timerPaused) {

            timer.play();
            timerPaused = false;
            timerLabel.setTextFill(Color.GREEN);
            button.setText("Pause");
            startButton.setText("Pause All");
        } else if (!timerPaused) {
            
            timer.pause();
            timerPaused = true;
            button.setText("Resume");
            timerLabel.setTextFill(Color.RED);
        }
        
    }
    
    @FXML
    public void handleStartButton(ActionEvent event){
        
        if (!timerOn) {
            
            timer.play(); // Start timer
            timerOn = true;
            timerPaused = false;
            button.setText("Pause");
            startButton.setText("Pause All");
            timerLabel.setTextFill(Color.GREEN);

        } 
        
        else if (!timerPaused) {
            
            timer.pause();
            timerPaused = true;
            button.setText("Resume");
            startButton.setText("Resume All");
            timerLabel.setTextFill(Color.RED);
        }
        
    }
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
System.out.println("Initialized!");
        
            eventHandler = e -> {
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
            };
            
            timer = new Timeline(
                new KeyFrame(Duration.millis(1000), eventHandler));
            timer.setCycleCount(180);
            
    }      
    
}
