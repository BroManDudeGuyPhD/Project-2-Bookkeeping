/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookkeeping;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author aaf8553
 */
public class StartScreenController implements Initializable {

   @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField teams,times,probs;
    @FXML
    private int numTeam,maxTime,numProbs;
    @FXML
    public String stringTeam,stringProb,stringTime;

    ArrayList info = new ArrayList();
    
    MainScreenController mainC = new MainScreenController();
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        stringTeam=teams.getText();
        stringTime=times.getText();
        stringProb=probs.getText();

        if(isInteger(stringTeam)&& isInteger(stringTime) && isInteger(stringProb)){

            mainC.mainScreen(Integer.parseInt(stringTeam),Integer.parseInt(stringTime),Integer.parseInt(stringProb));
            mainC.closeEdit();
        }
    }
    
    public String getTeams(){
        return stringTeam;
    }
    
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);

            // s is a valid integer
            isValidInteger = true;
        } catch (NumberFormatException ex) {
            // s is not an integer
        }

        return isValidInteger;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    
}
