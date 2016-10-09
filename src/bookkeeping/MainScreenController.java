/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookkeeping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class MainScreenController extends Application {
    
    boolean timerOn = false;
    boolean timerPaused = true;
    boolean timeExpired = false;
    int seconds = 0;
    int minutes = 0;
    int secondsLeft = 0;
    int minutesLeft = 0;
    int teamsAmount = 0;
    int problemsAmount = 0;
    int buttonDeterminer = 0;
    int buttonYValue = 50;
    int timerYValue = 50;
    
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
    ArrayList<String> tesm = new ArrayList<>();
    
    public Stage startStage = new Stage();
    public Stage primaryStage = new Stage();
    Stage teamPopup = new Stage();
    StartScreenController start;
    EventHandler<ActionEvent> eventHandler = null;
    javafx.animation.Timeline timer = null;
    
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

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
    
    public void mainScreen(Integer teamNumbers, Integer totalTime, Integer problemAmount) {
    //Initialize local variables to function call
        teamsAmount = teamNumbers;
        problemsAmount = problemAmount;
        
        teamTimers = new Label[teamsAmount];
        
        buttons = new Button[teamsAmount];
        teamTimerSeconds = new int[teamsAmount];
        
        int tempTime = totalTime;
        minutesLeft = tempTime-=1;
        secondsLeft = 60;


    
        
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
        
        
        Pane root = new Pane();
        Scene scene = new Scene(root, 1024, 768);

        //Initialize main timer
        Label mainTimerLabel = new Label();
        mainTimerLabel.setText("00:00");
        mainTimerLabel.setLayoutX(515);
        mainTimerLabel.setLayoutY(20);
        root.getChildren().add(mainTimerLabel);
        
        //Initialize timeLeft timer
        Label timeLeftTimerLabel = new Label();
        timeLeftTimerLabel.setText("00:00");
        timeLeftTimerLabel.setLayoutX(615);
        timeLeftTimerLabel.setLayoutY(20);
        root.getChildren().add(timeLeftTimerLabel);
        
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
                    timeLeftTimerLabel.setTextFill(Color.RED);
                    
                    for (Integer teams = 0; teams < teamsAmount; teams++) {
                        teamTimers[teams].setText("Working");
                        teamTimers[teams].setTextFill(Color.GREEN);
                        
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

        //Initialize Calculate outcome button
        Button outcomeButton = new Button();
        outcomeButton.setText("Calculate results");
        outcomeButton.setLayoutX(800);
        outcomeButton.setLayoutY(19);
        root.getChildren().add(outcomeButton);
        outcomeButton.setTextFill(Color.CRIMSON);
        outcomeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HashMap<String, ArrayList<Integer>> teamPlacements = new HashMap<>();
                

                if(timeExpired == true){
                    
                    int mostProblems = 0;
                    
                    for(Integer teams = 0; teams < teamNumbers; teams ++){
                        teamPlacements.put(teams.toString(), new ArrayList<>());
                        
                        int problemsCounter = 0;
                        
                        for(Integer problems = 0; problems < problemAmount; problems ++){
                            
                            if (teamProblemsStatus.get(teams.toString()).get(problems).equals("TRUE")) {
                                
                                teamPlacements.get(teams.toString()).add(teamProblemSeconds.get(teams.toString()).get(problems));
                                
                                
                                problemsCounter++;
                                
                            }
                        }
                        if(problemsCounter > mostProblems){
                            mostProblems = problemsCounter;

                        }
                    }  
                }
            }
        });
        
        //for loop that runs for as many teams as there are to generate buttons for the team overview page
        for (Integer teamTimerFOR = 0; teamTimerFOR < teamsAmount; teamTimerFOR++) {
            int tempTeamNum = teamTimerFOR + 1;
            //variable to refference i, since refference variable here must be final
            final Integer teamTimerFORRefference = teamTimerFOR;
            teamTimerSeconds[teamTimerFOR] = 0;

            //Initialize boolean arrays for teamTimers logic (starts at false)


            //Initialize team timers
            teamTimers[teamTimerFOR] = new Label();
            teamTimers[teamTimerFOR].setText("Waiting to start");
            
            
            if (teamTimerFOR < 24) {
                teamTimers[teamTimerFOR].setLayoutX(100);
                teamTimers[teamTimerFOR].setLayoutY(timerYValue);
            }
            
            else if(teamTimerFOR == 24){
                teamTimers[teamTimerFOR].setLayoutX(100);
                teamTimers[teamTimerFOR].setLayoutY(timerYValue);
                timerYValue = 32;
            }
            
            else if(teamTimerFOR > 24){
                teamTimers[teamTimerFOR].setLayoutX(500);
                teamTimers[teamTimerFOR].setLayoutY(timerYValue);
            }
           
            
            //Initialize buttons to control team timers
            buttons[teamTimerFOR].setText("Team "+tempTeamNum);
            buttons[teamTimerFOR].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    //Initialize team popup
                    buttonDeterminer = teamTimerFORRefference;
                    int temp = teamTimerFORRefference;
                    temp +=1;
                    teamPopup.close();
                    Pane root = new Pane();
                    Scene scene = new Scene(root, 500, 300);
                    teamPopup.setTitle("Team "+temp);


                    //Key Labels
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
                    
                    for(int problemButtonFOR = 0; problemButtonFOR < problemAmount; problemButtonFOR++){
                        
                        
                        if(problemButtonFOR < 4){
                            problemButton[problemButtonFOR].setLayoutY(89);
                            problemButton[problemButtonFOR].setLayoutX(problemButtonXLayout);
                            problemButtonXLayout += 100;

                            problemStatus[problemButtonFOR].setLayoutY(125);
                            problemStatus[problemButtonFOR].setLayoutX(problemCBXLayout);
                            problemCBXLayout+=100;
                        }
                        
                        else if(problemButtonFOR == 4){
                            problemButton[problemButtonFOR].setLayoutY(89);
                            problemButton[problemButtonFOR].setLayoutX(problemButtonXLayout);
                            problemButtonXLayout = 5;

                            problemStatus[problemButtonFOR].setLayoutY(125);
                            problemStatus[problemButtonFOR].setLayoutX(problemCBXLayout);
                            problemCBXLayout = 1;
                        }

                        
                        else if(problemButtonFOR > 4){
                            
                            problemButton[problemButtonFOR].setLayoutY(185);
                            problemButton[problemButtonFOR].setLayoutX(problemButtonXLayout);
                            problemButtonXLayout += 100;
    
                            problemStatus[problemButtonFOR].setLayoutY(216);
                            problemStatus[problemButtonFOR].setLayoutX(problemCBXLayout);
                            problemCBXLayout+=100;
                        }
                        
                        int tempp = problemButtonFOR;
                        tempp = tempp + 1;
                        
                        
                        
                        
                        //Check problem status to determine if solved. Shows Red button text and checks box if so
                        if (teamProblemsStatus.get(teamTimerFORRefference.toString()).get(problemButtonFOR).equals("TRUE")) {
                            problemButton[problemButtonFOR].setText("Completed");
                            problemButton[problemButtonFOR].setTextFill(Color.RED);

                            problemStatus[problemButtonFOR].setText("Completed");
                            problemStatus[problemButtonFOR].setSelected(true);
                        }
                        
                        //Check problem timer status to determine if paused. If paused then button text is blue.
                        else if(teamProblemTimers.get(teamTimerFORRefference.toString()).get(problemButtonFOR).equals("FALSE")){
                            problemButton[problemButtonFOR].setText("Resume");
                            problemButton[problemButtonFOR].setTextFill(Color.BLUE);
                        }
                        
                        else {
                            problemButton[problemButtonFOR].setText("Problem " + tempp);
                            problemButton[problemButtonFOR].setTextFill(Color.GREEN);

                            problemStatus[problemButtonFOR].setText("Completed");
                            problemStatus[problemButtonFOR].setSelected(false);
                        }
                        
                        
                        
                        root.getChildren().add(problemButton[problemButtonFOR]);
                        root.getChildren().add( problemStatus[problemButtonFOR]);
                        
                        
                        final int tempTemp = tempp;
                        final int c = problemButtonFOR;
                        problemButton[problemButtonFOR].setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                teamTimers[teamTimerFORRefference].setTextFill(Color.RED);
                                

                                if (teamProblemTimers.get(teamTimerFORRefference.toString()).get(c).equals("FALSE")) {
                                    problemButton[c].setTextFill(Color.GREEN);
                                    problemButton[c].setText("Problem "+tempTemp);
                                    teamProblemTimers.get(teamTimerFORRefference.toString()).set(c,"TRUE");  
                                    teamTimers[teamTimerFORRefference].setText("Working");
                                    teamTimers[teamTimerFORRefference].setTextFill(Color.GREEN);
                                } 
                                
                                else if (teamProblemTimers.get(teamTimerFORRefference.toString()).get(c).equals("TRUE") && timeExpired == false) {
                                    problemButton[c].setTextFill(Color.BLUE);
                                    problemButton[c].setText("Resume");
                                    teamProblemTimers.get(teamTimerFORRefference.toString()).set(c,"FALSE");
                                    teamTimers[teamTimerFORRefference].setText("Judging");
                                    teamTimers[teamTimerFORRefference].setTextFill(Color.BLUE);
                                    
                                    System.out.println("Team: "+teamTimerFORRefference+ " Problem " +tempTemp+" PAUSED at: "+teamProblemSeconds.get(teamTimerFORRefference.toString()).get(c));
                                }

                            }

                        });
                        
                        
                            
                        problemStatus[c].setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent actionEvent) {
                                
                                if(teamProblemTimers.get(teamTimerFORRefference.toString()).get(c).equals("FALSE")){
                                    problemButton[c].setText("Completed");
                                    problemButton[c].setTextFill(Color.RED);
                                    teamProblemsStatus.get(teamTimerFORRefference.toString()).set(c, "TRUE");
                                    teamTimers[teamTimerFORRefference].setText("Working");
                                    teamTimers[teamTimerFORRefference].setTextFill(Color.GREEN);
                                    
                                    System.out.println("Team: "+teamTimerFORRefference+ " Problem " +tempTemp+" SOLVED at: "+teamProblemSeconds.get(teamTimerFORRefference.toString()).get(c));
                                }
                                
                            }

                        });
                        

                    }
                    
                    
                    //Grab team data
                    
                    
                    
                    teamPopup.setScene(scene);
                    teamPopup.show();
                    
                    
                    
                }
                
            });

            
            if (teamTimerFOR < 24) {
                buttons[teamTimerFOR].setLayoutX(14);
                buttons[teamTimerFOR].setLayoutY(buttonYValue);
            }
            
            else if(teamTimerFOR == 24){
                buttons[teamTimerFOR].setLayoutX(14);
                buttons[teamTimerFOR].setLayoutY(buttonYValue);
                buttonYValue = 32;
            }
            
            else if(teamTimerFOR > 24){
                buttons[teamTimerFOR].setLayoutX(415);
                buttons[teamTimerFOR].setLayoutY(buttonYValue);
            }
            

            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            //Add Children elements for each team to the pane
            root.getChildren().add(buttons[teamTimerFOR]);
            root.getChildren().add(teamTimers[teamTimerFOR]);
            
            //Y value increments each FOR Loop run
            buttonYValue += 27;
            timerYValue += 27;

            
            //Timer eventHndler, handles main timer and individual team problem timers
            eventHandler = e -> {
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
                
                secondsLeft--;
                if(secondsLeft == 0){
                    minutesLeft--;
                    secondsLeft = 59;
                }
                
                    if (minutes == totalTime) {
                        mainTimerLabel.setText("Time Expired!");
                        mainTimerLabel.setTextFill(Color.RED);
                        timer.pause();
                        timeExpired = true;
                    } 
                    
                    else if (minutes != totalTime) {
                        mainTimerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                        timeLeftTimerLabel.setText(String.format("%02d:%02d", minutesLeft, secondsLeft));
                        for (Integer teams = 0; teams < teamsAmount; teams++) {

                            for (Integer problems = 0; problems < problemsAmount; problems++) {

                                if (teamProblemTimers.get(teams.toString()).get(problems).equals("TRUE")) {
                                    Integer tempSeconds = teamProblemSeconds.get(teams.toString()).get(problems);
                                    teamProblemSeconds.get(teams.toString()).set(problems, tempSeconds += 1);

                                }

                                int tempMins = 0;
                                int tempSeconds = teamProblemSeconds.get(teams.toString()).get(problems);

                                while (tempSeconds >= 60) {
                                    tempMins += 1;
                                    tempSeconds -= 60;
                                }

                            }
                        }
                    }
            };

            timer = new Timeline(
                    new KeyFrame(Duration.millis(1000), eventHandler));
            timer.setCycleCount(180);

        }

        primaryStage.setTitle("BOOKKEEPER 3000");
        primaryStage.setScene(scene);
        //primaryStage.getIcons().add(new Image("/src/plat.png")); 
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    public static <K, V extends Comparable<? super V>> Map<K, V> 
        sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

}
