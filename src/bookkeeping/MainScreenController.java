/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookkeeping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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


public class MainScreenController extends Application {
    
    boolean timerOn = false;
    boolean timerPaused = true;
    int seconds = 0;
    int minutes = 0;
    int teamsAmount = 0;
    int problemsAmount = 0;
    int buttonDeterminer = 0;
    int buttonYValue = 42;
    int labelYValue = 46;
    int timerYValue = 46;
    
    //ArrayList<Label> tt= new ArrayList<Label>();
    
    Label[] teamTimers;
    Button[] problemButton;
    Button[] buttons;
    CheckBox[] problemStatus;
    int[] teamTimerSeconds;

    HashMap<String, ArrayList<String>> teamProblemTimers = new HashMap<>();
    HashMap<String, ArrayList<Integer>> teamProblemSeconds = new HashMap<>();
    HashMap<String, ArrayList<String>> teamProblemsStatus = new HashMap<>();
    HashMap<String, ArrayList<String>> teamProblemsCompletionTimes = new HashMap<>();
    
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
    //Initialize local variables to function call
        teamsAmount = teamNumbers;
        problemsAmount = problemAmount;
        
        teamTimers = new Label[teamsAmount];
        
        buttons = new Button[teamsAmount];
        teamTimerSeconds = new int[teamsAmount];
        
        int totalTimer = totalTime;


    
        
    //Set all elements to defaults (Like check boxws to empty) So when they are used they wont be overwritten by re-creating elements again
        
        for (int teams = 0; teams < teamNumbers; teams++) {
            buttons[teams] = new Button();
            problemButton = new Button[problemAmount];
            problemStatus = new CheckBox[problemAmount];
            for (int problems = 0; problems < problemAmount; problems++) {
                problemButton[problems] = new Button();
                problemButton[problems].setTextFill(Color.GREEN);

                problemStatus[problems] = new CheckBox();
            }
        }
        
        //Initialize Map storing individual problem timers and set all to true
        
        

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

                    for (Integer teams = 0; teams < teamsAmount; teams++) {
                        System.out.println("Team " + teams + " set to true");
                        //buttons[q].setText("Pause");
                        
                        teamProblemTimers.put(teams.toString(), new ArrayList<>());
                        teamProblemSeconds.put(teams.toString(), new ArrayList<>());
                        teamProblemsStatus.put(teams.toString(), new ArrayList<>());
                        //Initialise MAP controlling problem timers to FALSE (Not paused)
                        for(int problems = 0; problems < problemsAmount; problems++){
                            
                            //Problem Timers are all running on StareButton
                            teamProblemTimers.get(teams.toString()).add("TRUE");
                            
                            //ProblemTimers initialized to 0
                            int initializeZero = 0;
                            teamProblemSeconds.get(teams.toString()).add(initializeZero);
                            
                            //No problems are finished yet
                            teamProblemsStatus.get(teams.toString()).add("FALSE");
                        }
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

        for (Integer i = 0; i < teamsAmount; i++) {
            int tempTeamNum = i + 1;
            //variable to refference i, since refference variable here must be final
            final Integer q = i;
            teamTimerSeconds[i] = 0;

            //Initialize boolean arrays for teamTimers logic (starts at false)


            //Initialize team timers
            teamTimers[i] = new Label();
            teamTimers[i].setText("00:00");
            teamTimers[i].setLayoutX(100);
            teamTimers[i].setLayoutY(timerYValue);
            
           
            
            //Initialize buttons to control team timers
            buttons[i].setText("Team "+tempTeamNum);
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    //Initialize team popup
                    buttonDeterminer = q;
                    teamPopup.close();
                    Pane root = new Pane();
                    Scene scene = new Scene(root, 500, 300);
                    teamPopup.setTitle("Team "+buttonDeterminer);


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
                        problemButton[b].setTextFill(Color.GREEN);
                                    
                        problemStatus[b].setText("Completed");
                        problemStatus[b].setSelected(false);
                        
                        
                        if (teamProblemsStatus.get(q.toString()).get(b).equals("TRUE")) {
                            problemButton[b].setText("Completed");
                            problemButton[b].setTextFill(Color.RED);

                            problemStatus[b].setText("Completed");
                            problemStatus[b].setSelected(true);
                        }
                        
                        root.getChildren().add(problemButton[b]);
                        root.getChildren().add( problemStatus[b]);
                        
                        
                        final int tempTemp = temp;
                        final int c = b;
                        problemButton[b].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                teamTimers[q].setTextFill(Color.RED);
                                

                                if (teamProblemTimers.get(q.toString()).get(q).equals("FALSE")) {
                                    problemButton[c].setTextFill(Color.GREEN);
                                    problemButton[c].setText("Problem "+tempTemp);
                                    teamProblemTimers.get(q.toString()).set(q,"TRUE");  
                                } 
                                
                                else if (teamProblemTimers.get(q.toString()).get(q).equals("TRUE")) {
                                    problemButton[c].setTextFill(Color.BLUE);
                                    problemButton[c].setText("Resume");
                                    System.out.println("Team: "+q+ " Problem " +tempTemp+" paused at: "+teamProblemSeconds.get(q.toString()).get(c));
                                    teamProblemTimers.get(q.toString()).set(q,"FALSE");
                                }

                            }

                        });
                        
                        
                            
                        problemStatus[b].setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent actionEvent) {
                                
                                if(teamProblemTimers.get(q.toString()).get(q).equals("FALSE")){
                                    problemButton[c].setText("Completed");
                                    problemButton[c].setTextFill(Color.RED);
                                    teamProblemsStatus.get(q.toString()).set(c, "TRUE");
                                    
                                }
                                
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

                for (Integer teams = 0; teams < teamsAmount; teams++) {
                    
                    for (Integer problems = 0; problems < problemsAmount; problems++) {

                        if (teamProblemTimers.get(teams.toString()).get(problems).equals("TRUE")) {
                            Integer tempSeconds = teamProblemSeconds.get(teams.toString()).get(problems);
                            teamProblemSeconds.get(teams.toString()).set(problems, tempSeconds+=1);

                        }
                    
                        int tempMins = 0;
                        int tempSeconds = teamProblemSeconds.get(teams.toString()).get(problems);
                        
                        while(tempSeconds >= 60){
                            tempMins +=1;
                            tempSeconds -= 60;
                        }
                        

                    //teamTimers[j].setText(String.format("%02d:%02d", tempMins, tempSeconds));
                }
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
