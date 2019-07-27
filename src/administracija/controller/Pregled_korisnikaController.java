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
public class Pregled_korisnikaController implements Initializable {

    @FXML
    private JFXButton korisnik_btn;

    @FXML
    private Pane korisnik_pane;

    @FXML
    private JFXButton administrator_btn;

    @FXML
    private Pane administrator_pane;

    @FXML
    private Pane prikaz;

    String aktivno = "-fx-background-color: #DC143C;";
    String neaktivno = "-fx-background-color: #272D32;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pane pregled;
        try {
            pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_kupaca.fxml"));
            prikaz.getChildren().setAll(pregled);
            reset_pane(korisnik_pane);

        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void dodaj_administratora(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_administratora.fxml"));
        prikaz.getChildren().setAll(pregled);
        reset_pane(administrator_pane);
    }

    @FXML
    private void dodaj_kupca(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_kupaca.fxml"));
        prikaz.getChildren().setAll(pregled);
        reset_pane(korisnik_pane);
    }

    public void reset_pane(Pane pane) {
        administrator_pane.setStyle(neaktivno);
        korisnik_pane.setStyle(neaktivno);
        pane.setStyle(aktivno);
    }
}
