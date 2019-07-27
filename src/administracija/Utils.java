/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author armin
 */
public class Utils {
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static Stage window = null;
    public static void prikazi (Stage window, String view) {
        try {
            Parent root = FXMLLoader.load(Utils.class.getResource("view/"+view+".fxml"));
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.setTitle(view);
            window.centerOnScreen();
            
            //grab your root here
             root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setX(event.getScreenX() - xOffset);
                window.setY(event.getScreenY() - yOffset);
            }
        });

        } catch (IOException ex) {
            System.out.println("Greska prilikom otvaranja prozora.."+ex.getMessage());
        }
    } 
    
}
