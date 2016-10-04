/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookkeeping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author aaf8553
 */
public class MainScreenController extends Application {
    
    boolean timerOn = false;
    boolean timerPaused = true;
    int seconds = 0;
    int minutes = 0;
    int teamsAmount = 0;
    int buttonDeterminer = 0;
    int buttonYValue = 42;
    int labelYValue = 46;
    int timerYValue = 46;
    
    Label[] teamTimers = new Label[teamsAmount];
    Label[] teamNames = new Label[teamsAmount];
    Button[] buttons = new Button[teamsAmount];
    int[] teamTimerSeconds = new int[teamsAmount];
    int[] teamTimerMinutes = new int[teamsAmount];
    
    boolean[] teamTimerPaused = new boolean[teamsAmount];

    public Stage startStage = new Stage();
    public Stage primaryStage = new Stage();
    EventHandler<ActionEvent> eventHandler = null;
    javafx.animation.Timeline timer = null;
    
    public void closeEdit(){
        primaryStage.close();
    }
    
    
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //info();


        //Create Edit button
        StartScreenController start = new StartScreenController();
        try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    startStage.initModality(Modality.WINDOW_MODAL);
                    startStage.initStyle(StageStyle.DECORATED);
                    startStage.setTitle("EDIT");
                    startStage.setScene(new Scene(root1));
                    startStage.show();
                    
                    System.out.println(start.getInfo());
                    
                } catch (IOException ex) {
                    Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
          
            }
        

        
    
    
    public void mainScreen(){
        StartScreenController start = new StartScreenController();
        ArrayList<String> results = new ArrayList<>();
        results = start.getInfo();
        
        
        teamsAmount = Integer.parseInt(results.get(0));
        int maxTime = Integer.parseInt(stringTime);
        int numProbs = Integer.parseInt(stringProb);

        
        startStage.close();
        Pane root = new Pane();
        Scene scene = new Scene(root, 1024, 768);
                //Initialize main timer
        Label mainTimerLabel = new Label();
        mainTimerLabel.setText("00:00");
        mainTimerLabel.setLayoutX(512);
        mainTimerLabel.setLayoutY(19);
        root.getChildren().add(mainTimerLabel);
        
        //Initialize start button
        Button startButton = new Button();
        startButton.setText("Start All");
        startButton.setLayoutX(439);
        startButton.setLayoutY(19);
        root.getChildren().add(startButton);
        
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!timerOn) {
                    System.out.println("YAY");
                    timer.play(); // Start timer
                    timerOn = true;
                    timerPaused = false;

                    startButton.setText("Pause All");
                    mainTimerLabel.setTextFill(Color.GREEN);

                    for (int q = 0; q < teamsAmount; q++) {
                        teamTimerPaused[q] = false;
                        System.out.println("Team " + q + " set to true");
                        //buttons[q].setText("Pause");
                    }

                } 
                
                else if (timerPaused) {

            timer.play();
            timerPaused = false;
            mainTimerLabel.setTextFill(Color.GREEN);
            startButton.setText("Pause");
            startButton.setText("Pause All");
        } else if (!timerPaused) {

                    timer.pause();
                    timerPaused = true;

                    startButton.setText("Resume All");
                    mainTimerLabel.setTextFill(Color.RED);
                }
            }
        });

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
            root.getChildren().add(buttons[i]);
            root.getChildren().add(teamNames[i]);
            root.getChildren().add(teamTimers[i]);
            buttonYValue += 27;
            labelYValue += 27;
            timerYValue += 27;
        
        System.out.println("Initialized! "+i);

        eventHandler = e -> {
            seconds++;
            if (seconds == 60) {
                minutes++;
                seconds = 0;
            }
           

            
            
            mainTimerLabel.setText(String.format("%02d:%02d", minutes, seconds));

            for (int j = 0; j < teamsAmount; j++) {
                
                if (!teamTimerPaused[j]) {
                teamTimerSeconds[j]++;
                if (teamTimerSeconds[j] == 60) {
                    teamTimerMinutes[j]++;
                    teamTimerSeconds[j] = 0;
                }
            }
                
                teamTimers[j].setText(String.format("%02d:%02d", teamTimerMinutes[j], teamTimerSeconds[j]));
            }

        };

        timer = new Timeline(
                new KeyFrame(Duration.millis(1000), eventHandler));
        timer.setCycleCount(180);

    }
        
        
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
