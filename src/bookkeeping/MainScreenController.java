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
import javafx.scene.control.CheckBox;
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
    Button[] problemButton = new Button[teamsAmount];
    Button[] buttons = new Button[teamsAmount];
    CheckBox[] problemStatus = new CheckBox[0];
    int[] teamTimerSeconds = new int[teamsAmount];
    int[] teamTimerMinutes = new int[teamsAmount];
    
    boolean[] teamTimerPaused = new boolean[teamsAmount];

    public Stage startStage = new Stage();
    public Stage primaryStage = new Stage();
    Stage teamPopup = new Stage();
    StartScreenController start;
    EventHandler<ActionEvent> eventHandler = null;
    javafx.animation.Timeline timer = null;
    
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //info();

        //Create Edit button
        StartScreenController start = new StartScreenController();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            startStage.initModality(Modality.APPLICATION_MODAL);
            startStage.initStyle(StageStyle.DECORATED);
            startStage.setTitle("EDIT");
            startStage.setScene(new Scene(root1));
            startStage.show();


        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void closeEdit(){
        
        startStage.hide();
        System.out.println("Closed!");
    }
    

    public void mainScreen(int teamNumbers, int totalTime, int problemAmount) {

        teamsAmount = teamNumbers;
        teamTimers = new Label[teamsAmount];
        teamNames = new Label[teamsAmount];
        buttons = new Button[teamsAmount];
        teamTimerSeconds = new int[teamsAmount];
        teamTimerMinutes = new int[teamsAmount];
        
        problemButton = new Button[problemAmount];
        problemStatus = new CheckBox[problemAmount];
        
        teamTimerPaused = new boolean[teamsAmount];
        int totalTimer = totalTime;

        //startStage.close();
        Pane root = new Pane();
        Scene scene = new Scene(root, 1024, 768);

        //Initialize main timer
        Label mainTimerLabel = new Label();
        mainTimerLabel.setText("00:00");
        mainTimerLabel.setLayoutX(515);
        mainTimerLabel.setLayoutY(20);
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

                } else if (timerPaused) {

                    timer.play();
                    timerPaused = false;
                    mainTimerLabel.setTextFill(Color.GREEN);

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


            //Initialize team timers
            teamTimers[i] = new Label();
            teamTimers[i].setText("00:00");
            teamTimers[i].setLayoutX(100);
            teamTimers[i].setLayoutY(timerYValue);
            //206 46

            
            //Initialize buttons to control team timers
            buttons[i] = new Button();
            buttons[i].setText("Team "+i);
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    //Initialize team popup
                    buttonDeterminer = q;
                    teamPopup.close();
                    Pane root = new Pane();
                    Scene scene = new Scene(root, 500, 300);
                    teamPopup.setTitle("Team "+buttonDeterminer);
                    Button pause = new Button();
                    pause.setText("Pause Team Timer");
                    pause.setLayoutX(194);
                    pause.setLayoutY(45);
                    root.getChildren().add(pause);

                    //Vanity Labels
                    Label key = new Label();
                    key.setLayoutX(112);
                    key.setLayoutY(14);
                    key.setText("Green: Running");
                    key.setTextFill(Color.GREEN);
                    root.getChildren().add(key);
                    
                    Label key2 = new Label();
                    key2.setLayoutX(217);
                    key2.setLayoutY(14);
                    key2.setText("Blue: Paused");
                    key2.setTextFill(Color.BLUE);
                    root.getChildren().add(key2);
                    
                    Label key3 = new Label();
                    key3.setLayoutX(293);
                    key3.setLayoutY(14);
                    key3.setText("Red: completed");
                    key3.setTextFill(Color.RED);
                    root.getChildren().add(key3);
                    
                    
                    int problemButtonXLayout = 5;
                    int problemCBXLayout = 1;
                    for(int b = 0; b < problemAmount; b++){
                        problemButton[b] = new Button();
                        problemButton[b].setTextFill(Color.GREEN);
                        
                        problemStatus[b] = new CheckBox();
                        
                        
                        if(b < 4){
                            problemButton[b].setLayoutY(89);
                            problemButton[b].setLayoutX(problemButtonXLayout);
                            problemButtonXLayout += 100;

                            problemStatus[b].setLayoutY(125);
                            problemStatus[b].setLayoutX(problemCBXLayout);
                            problemCBXLayout+=100;
                        }
                        
                        else if(b == 4){
                            problemButton[b].setLayoutY(89);
                            problemButton[b].setLayoutX(problemButtonXLayout);
                            problemButtonXLayout = 5;

                            problemStatus[b].setLayoutY(125);
                            problemStatus[b].setLayoutX(problemCBXLayout);
                            problemCBXLayout = 1;
                        }

                        
                        else if(b > 4){
                            
                            problemButton[b].setLayoutY(185);
                            problemButton[b].setLayoutX(problemButtonXLayout);
                            problemButtonXLayout += 100;
    
                            problemStatus[b].setLayoutY(216);
                            problemStatus[b].setLayoutX(problemCBXLayout);
                            problemCBXLayout+=100;
                        }
                        
                        int temp = b;
                        temp = temp + 1;
                        problemButton[b].setText("Problem " + temp);
                        root.getChildren().add(problemButton[b]);
                        
                        
                        problemStatus[b].setText("Completed");
                        root.getChildren().add( problemStatus[b]);

                        final int tempTemp = temp;
                        final int c = b;
                        problemButton[b].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                teamTimers[q].setTextFill(Color.RED);

                                if (teamTimerPaused[q]) {
                                    problemButton[c].setText("Resume "+tempTemp);
                                    pause.setText("Resume timing");
                                } else {
                                    problemButton[c].setTextFill(Color.BLUE);
                                    problemButton[c].setText("Resume");
                                }

                                teamTimerPaused[q] = !teamTimerPaused[q];
                            }

                        });
                        
                        
                        
                        problemStatus[b].setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent actionEvent) {
                                
                                problemButton[c].setText("Completed");
                                problemButton[c].setTextFill(Color.RED);
                            }

                        });

                    }
                    
                    
                    //Grab team data
                    
                    
                    
                    teamPopup.setScene(scene);
                    teamPopup.show();
                    
                    
                    
                }
                
            });

            buttons[i].setLayoutX(14);
            buttons[i].setLayoutY(buttonYValue);

            //Add Children elements for each team to the pane
            root.getChildren().add(buttons[i]);
            root.getChildren().add(teamTimers[i]);
            buttonYValue += 27;
            labelYValue += 27;
            timerYValue += 27;

            System.out.println("Initialized! " + i);

            eventHandler = e -> {
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }

                mainTimerLabel.setText(String.format("%02d:%02d", minutes, seconds));

                for (int j = 0; j < teamsAmount; j++) {
                    
                    for (int k = 0; k < problemAmount; k++) {
                        if (!teamTimerPaused[j]) {
                            teamTimerSeconds[j]++;
                            if (teamTimerSeconds[j] == 60) {
                                teamTimerMinutes[j]++;
                                teamTimerSeconds[j] = 0;
                            }
                        }
                    }

                    teamTimers[j].setText(String.format("%02d:%02d", teamTimerMinutes[j], teamTimerSeconds[j]));
                }

            };

            timer = new Timeline(
                    new KeyFrame(Duration.millis(1000), eventHandler));
            timer.setCycleCount(180);

        }

        primaryStage.setTitle("BOOKKEEPER 3000");
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
