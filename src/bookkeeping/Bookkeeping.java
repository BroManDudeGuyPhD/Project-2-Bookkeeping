/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookkeeping;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author AndrewFossier
 */
public class Bookkeeping extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
        public static void generateButtons(int teamsAmount){
        int buttonYValue = 42;
        int labelYValue = 46;
        Label[] teamNames = new Label[teamsAmount];
        Button[] buttons = new Button[teamsAmount];
        
        for(int i = 0; i < teamsAmount; i++){
            //Initialize labesl with team names
            teamNames[i].setText("Team "+i+1);
            teamNames[i].setLayoutX(14);
            teamNames[i].setLayoutY(labelYValue);
            
            //Initialize buttons to control team timers
            buttons[i].setText("     ");
            buttons[i].setOnAction(MainScreenController.handleButtonAction);
            buttons[i].setLayoutX(170);
            buttons[i].setLayoutY(buttonYValue);
            
            
            
            
            buttonYValue+=10;
        }
        
    }
    
}