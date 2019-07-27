package administracija.controller;

import administracija.Utils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Dodaj_korisnikaController implements Initializable {

    @FXML
    private Pane dodaj_korisnika_pane;
    @FXML
    private JFXButton administrator_btn;
    @FXML
    private JFXButton korisnik_btn;
    @FXML
    private Pane administrator_pane;
    @FXML
    private Pane korisnik_pane;
    
    String style = "-fx-background-color: #DC143C;";
    String neaktivno = "-fx-background-color:  #272d32;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pane pregled;
        try {
            pregled = FXMLLoader.load(Utils.class.getResource("view/dodaj_administratora.fxml"));
            dodaj_korisnika_pane.getChildren().setAll(pregled);
            reset_pane(administrator_pane);

        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void dodaj_administratora(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/dodaj_administratora.fxml"));
        dodaj_korisnika_pane.getChildren().setAll(pregled);
        reset_pane(administrator_pane);
    }

    @FXML
    private void dodaj_kupca(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/dodaj_kupca.fxml"));
        dodaj_korisnika_pane.getChildren().setAll(pregled);
        reset_pane(korisnik_pane);
    }

    public void reset_pane(Pane pane) {
        administrator_pane.setStyle(neaktivno);
        korisnik_pane.setStyle(neaktivno);
        pane.setStyle(style);
    }
}
