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
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author AndrewFossier
 */
public class MainScreenController implements Initializable {
    
    boolean timerOn = false;
    boolean timerPaused = true;
    int seconds = 0;
    int minutes = 0;
    
    EventHandler<ActionEvent> eventHandler = null;
    javafx.animation.Timeline timer = null;
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private Label timerLabel;
    @FXML 
    private Button startButton;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (!timerOn) {
            
            timer.play(); // Start timer
            timerOn = true;
            timerPaused = false;
            button.setText("Pause");
            timerLabel.setTextFill(Color.GREEN);

        } else if (timerPaused) {

            timer.play();
            timerPaused = false;
             timerLabel.setTextFill(Color.GREEN);
            button.setText("Pause");
            
        } else if (!timerPaused) {
            
            timer.pause();
            timerPaused = true;
            button.setText("Resume");
            timerLabel.setTextFill(Color.RED);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
