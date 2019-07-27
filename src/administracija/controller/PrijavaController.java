package administracija.controller;

import administracija.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.PrijavaService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class PrijavaController implements Initializable {

    @FXML
    private FontAwesomeIcon zatvori_aplikaciju;
    @FXML
    private JFXTextField korisnicko_ime_txt;
    @FXML
    private JFXPasswordField lozinka_txt;
    @FXML
    private JFXButton prijava_btn;
    @FXML
    private Label greska_lbl;

    @FXML
    public void zatvori(MouseEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        korisnicko_ime_txt.setText("armin");
        lozinka_txt.setText("1");
    }

    @FXML
    private void ponisti(MouseEvent event) {
        korisnicko_ime_txt.setStyle("-jfx-focus-color: #483d8b; -jfx-unfocus-color: #483d8b;");
        lozinka_txt.setStyle("-jfx-focus-color: #483d8b; -jfx-unfocus-color: #483d8b;");
    }

    @FXML
    private void prijavi_se(MouseEvent event) {
        if (korisnicko_ime_txt.getText().equals("") || lozinka_txt.getText().equals("")) {
            greska_lbl.setText("Niste unijeli sve tražene podatke.");
        } else {

            greska_lbl.setText("");
            String korisnicko_ime = korisnicko_ime_txt.getText();
            String lozinka = lozinka_txt.getText();

            if (PrijavaService.prijava(korisnicko_ime, lozinka)) {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                Utils.prikazi(stage, "pocetna");
                //pref.put("user", korisnicko_ime);
                //pref.put("pass", lozinka);
            } else {
                greska_lbl.setText("Korisničko ime ili lozinka nisu točni ili pak niste administrator !");
                korisnicko_ime_txt.setStyle("-jfx-focus-color: red; -jfx-unfocus-color: red;");
                lozinka_txt.setStyle("-jfx-focus-color: red; -jfx-unfocus-color: red;");
            }
        }

    }
    @FXML
    private void minimiziraj(MouseEvent event){
        Stage stage = (Stage)((ImageView)event.getSource()).getScene().getWindow();
        // is stage minimizable into task bar. (true | false)
        stage.setIconified(true);
    }
    

}
