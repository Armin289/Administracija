package administracija.controller;

import administracija.model.Korisnik;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import service.KorisnikService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Dodaj_kupcaController implements Initializable {

    @FXML
    private JFXTextField ime_kupca;
    @FXML
    private JFXTextField prezime_kupca;
    @FXML
    private JFXPasswordField lozinka_kupca;
    @FXML
    private JFXTextField email_kupca;
    @FXML
    private JFXTextField adresa_kupca;
    @FXML
    private JFXTextField grad_kupca;
    @FXML
    private JFXTextField postanski_broj_kupca;
    @FXML
    private JFXTextField telefon_kupca;
    @FXML
    private JFXButton spremi_btn;
    @FXML
    private JFXButton ocisti_btn;
    @FXML
    private Label label_greska;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void ponisti(MouseEvent event) {
        this.ime_kupca.setText("");
        this.prezime_kupca.setText("");
        this.email_kupca.setText("");
        this.lozinka_kupca.setText("");
        this.adresa_kupca.setText("");
        this.grad_kupca.setText("");
        this.postanski_broj_kupca.setText("");
        this.telefon_kupca.setText("");
    }

    @FXML
    private void spremi(MouseEvent event) {
        int kPostanski_broj;
        String kIme = this.ime_kupca.getText();
        String kPrezime = this.prezime_kupca.getText();
        String kEmail = this.email_kupca.getText();
        String kLozinka = this.lozinka_kupca.getText();
        String kAdresa = this.adresa_kupca.getText();
        String kGrad_kupca = this.grad_kupca.getText();
        String postanski_broj_tmp = postanski_broj_kupca.getText();
        if (postanski_broj_tmp.equals("")) {
            kPostanski_broj = 0;
        } else {
            kPostanski_broj = Integer.valueOf(postanski_broj_tmp);
        }
        String kTelefon = this.telefon_kupca.getText();

        if (kIme.equals("") || kPrezime.equals("") || kEmail.equals("") || kAdresa.equals("") || kLozinka.equals("") || kGrad_kupca.equals("") || kPostanski_broj == 0 || postanski_broj_tmp.equals("") || kTelefon.equals("")) {
            label_greska.setText("Niste unijeli sve tražene podatke !");
        } else {
            label_greska.setText("");

            Korisnik korisnik = new Korisnik(0, kIme, kPrezime, kEmail, kLozinka, kAdresa, kGrad_kupca, kPostanski_broj, kTelefon);
            KorisnikService.korisnikService.spasi(korisnik);
            ponisti(event);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potvrda");
            alert.setHeaderText("Kupac " + kIme +" "+kPrezime+ " je uspješno spremljen.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });

        }
    }

}
