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

    boolean timerOn = false;
    boolean timerPaused = true;
    int seconds = 0;
    int minutes = 0;
    int teamsAmount = 10;
    
    Label[] teamTimers = new Label[teamsAmount];
    Label[] teamNames = new Label[teamsAmount];
    Button[] buttons = new Button[teamsAmount];
    int[] teamTimerSeconds = new int[teamsAmount];
    int[] teamTimerMinutes = new int[teamsAmount];
    
    boolean[] teamTimerPaused = new boolean[teamsAmount];

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
    public void handleStartButton(ActionEvent event) {

        if (!timerOn) {
            System.out.println("YAY");
            timer.play(); // Start timer
            timerOn = true;
            timerPaused = false;
            
            startButton.setText("Pause All");
            timerLabel.setTextFill(Color.GREEN);
            
            for(int q = 0; q < teamsAmount; q++){
                teamTimerPaused[q] = false;
                System.out.println("Team "+q+" set to true");
                //buttons[q].setText("Pause");
            }

        } else if (!timerPaused) {

            timer.pause();
            timerPaused = true;
            
            startButton.setText("Resume All");
            timerLabel.setTextFill(Color.RED);
        }

    }

    int buttonDeterminer = 0;
    public void generateButtons(Pane pane) {

        int buttonYValue = 42;
        int labelYValue = 46;
        int timerYValue = 46;

        for (int i = 0; i < teamsAmount; i++) {
            //variable to refference i, since refference variable here must be final
            final int q = i;
            teamTimerSeconds[i] = 0;
            teamTimerMinutes[i] = 0;

            //Initialize boolean arrays for teamTimers logic (starts at false)
            teamTimerPaused[i] = true;
            
            
            //Initialize labesl with team names
            teamNames[i] = new Label();
            teamNames[i].setText("Team " + i);
            teamNames[i].setLayoutX(14);
            teamNames[i].setLayoutY(labelYValue);

            //Initialize team timers
            teamTimers[i] = new Label();
            teamTimers[i].setText("00:00");
            teamTimers[i].setLayoutX(206);
            teamTimers[i].setLayoutY(timerYValue);
            //206 46

            //Initialize buttons to control team timers
            buttons[i] = new Button();
            buttons[i].setText("Start All");
            //buttons[i].setOnAction(handleButtonAction);
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("YAY " + q);
                    teamTimers[q].setTextFill(Color.RED);
                     
                    if (teamTimerPaused[q]) {
                        buttons[q].setText("Pause");
                    } else {
                        buttons[q].setText("Resume");
                    }

                    teamTimerPaused[q] = !teamTimerPaused[q];
                    buttonDeterminer = q;
                }
            });


            buttons[i].setLayoutX(98);
            buttons[i].setLayoutY(buttonYValue);

            //Add Children elements for each team to the pane
            pane.getChildren().add(buttons[i]);
            pane.getChildren().add(teamNames[i]);
            pane.getChildren().add(teamTimers[i]);
            buttonYValue += 27;
            labelYValue += 27;
            timerYValue += 27;
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

            for (int i = 0; i < teamsAmount; i++) {
                
                if (!teamTimerPaused[i]) {
                teamTimerSeconds[i]++;
                if (teamTimerSeconds[i] == 60) {
                    teamTimerMinutes[i]++;
                    teamTimerSeconds[i] = 0;
                }
            }
                
                teamTimers[i].setText(String.format("%02d:%02d", teamTimerMinutes[i], teamTimerSeconds[i]));
            }

        };

        timer = new Timeline(
                new KeyFrame(Duration.millis(1000), eventHandler));
        timer.setCycleCount(180);



        

    }

}
