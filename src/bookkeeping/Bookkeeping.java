/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookkeeping;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author AndrewFossier
 */
public class Bookkeeping extends Application {

    MainScreenController mainC = new MainScreenController();

    @Override
    public void start(Stage stage) throws Exception {

        Pane root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));

        mainC.generateButtons(root);

        stage.setScene(new Scene(root, 1024, 768));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
