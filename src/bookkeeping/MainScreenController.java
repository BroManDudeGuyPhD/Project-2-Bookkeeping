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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author AndrewFossier
 */
public class MainScreenController implements Initializable {


    
    static boolean timerOn = false;
    static boolean timerPaused = true;
    static int seconds = 0;
    static int minutes = 0;
    
    EventHandler<ActionEvent> eventHandler = null;
    static javafx.animation.Timeline timer = null;
    
    @FXML
    private static Button button;
    @FXML
    private static Label timerLabel;
    @FXML 
    private static Button startButton;
    @FXML
    private static CheckBox Problem1;
    @FXML
    private static Label teamNumber;
   
    
    
    @FXML
    public static void handleButtonAction(ActionEvent event) {

        System.out.println("YAY");
        
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
    public static void handleStartButton(ActionEvent event){
        
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
    
    
    
        public static void generateButtons(int teamsAmount, Pane pane){
        int buttonYValue = 42;
        int labelYValue = 46;
        Label[] teamNames = new Label[teamsAmount];
        Button[] buttons = new Button[teamsAmount];
        
        for(int i = 0; i < teamsAmount; i++){
            //Initialize labesl with team names
//            teamNames[i].setText("Team "+i+1);
//            teamNames[i].setLayoutX(14);
//            teamNames[i].setLayoutY(labelYValue);

            //Initialize buttons to control team timers
            buttons[i] = new Button();
            buttons[i].setText("     ");
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
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
            });
            buttons[i].setLayoutX(170);
            buttons[i].setLayoutY(buttonYValue);

            pane.getChildren().add(buttons[i]);

            buttonYValue += 15;
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
