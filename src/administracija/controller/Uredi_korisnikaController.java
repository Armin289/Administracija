package administracija.controller;

import administracija.Utils;
import administracija.model.Korisnik;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import service.KorisnikService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Uredi_korisnikaController implements Initializable {

    @FXML
    private Pane glavni_prostor;
    @FXML
    private JFXTextField ime_kupca;

    @FXML
    private JFXTextField prezime_kupca;

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
    private JFXButton obrisi_btn;

    @FXML
    private JFXButton spremi_btn;

    @FXML
    private Label label_greska;
    byte[] slika_artikla;
    byte[] img;
    int id;
    String lozinka;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id = Pregled_kupacaController.dohvatiIdKupca();
        try {
            postavi_kupca(id);
        } catch (IOException ex) {
            Logger.getLogger(Uredi_korisnikaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void postavi_kupca(int id) throws IOException {
        Korisnik korisnik = KorisnikService.korisnikService.izBazePremaId(id);
        lozinka=korisnik.getLozinka();
        ime_kupca.setText(korisnik.getIme());
        prezime_kupca.setText(korisnik.getPrezime());
        email_kupca.setText(korisnik.getEmail());
        adresa_kupca.setText(korisnik.getAdresa());
        grad_kupca.setText(korisnik.getGrad());
        telefon_kupca.setText(korisnik.getTelefon());
        postanski_broj_kupca.setText(String.valueOf(korisnik.getPostanskibroj()));
    }
    

    @FXML
    private void spremi(MouseEvent event) {
        int kPostanski_broj;
        String kIme = this.ime_kupca.getText();
        String kPrezime = this.prezime_kupca.getText();
        String kEmail = this.email_kupca.getText();
        String kLozinka = lozinka;
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

            Korisnik k = new Korisnik(id, kIme, kPrezime, kEmail, kLozinka, kAdresa, kGrad_kupca, kPostanski_broj, kTelefon);
            KorisnikService.korisnikService.uredi(k);
            ponisti();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potvrda");
            alert.setHeaderText("Podaci kupca "+ kIme+" "+kPrezime+" su uspješno ažurirani.");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                try {
                    Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_kupaca.fxml"));
                    glavni_prostor.getChildren().setAll(pregled);
                } catch (IOException ex) {
                    Logger.getLogger(Uredi_mobitelController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});

        }
    }
    private void ponisti(){
        ime_kupca.setText("");
        prezime_kupca.setText("");
        email_kupca.setText("");
        adresa_kupca.setText("");
        grad_kupca.setText("");
        postanski_broj_kupca.setText("");
        telefon_kupca.setText("");
    }


}
