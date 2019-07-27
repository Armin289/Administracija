package administracija.controller;

import administracija.Utils;
import administracija.model.Administrator;
import com.jfoenix.controls.JFXPasswordField;
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
import service.AdministratorService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Uredi_administratoraController implements Initializable {
    @FXML
    private Pane glavni_prostor;
    @FXML
    private JFXTextField ime_administratora;

    @FXML
    private JFXTextField prezime_administratora;

    @FXML
    private JFXTextField email_administratora;

    @FXML
    private JFXTextField korisnicko_ime_administratora;

    @FXML
    private JFXPasswordField potvrda_lozinke_administratora;

    @FXML
    private Label label_greska;
    byte[] slika_artikla;
    byte[] img;
    int id;
    private String lozinka;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id = Pregled_administratoraController.dohvatiIdAdministratora();
        try {
            postavi_administratora(id);
        } catch (IOException ex) {
            Logger.getLogger(Uredi_korisnikaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void postavi_administratora(int id) throws IOException {
        Administrator administrator = AdministratorService.administratorService.izBazePremaId(id);
        lozinka = administrator.getLozinka();
        ime_administratora.setText(administrator.getIme());
        prezime_administratora.setText(administrator.getPrezime());
        email_administratora.setText(administrator.getEmail());
        korisnicko_ime_administratora.setText(administrator.getKorisnicko_ime());
        lozinka = administrator.getLozinka();
    }

    @FXML
    private void spremi(MouseEvent event) {
        int kPostanski_broj;
        String kIme = this.ime_administratora.getText();
        String kPrezime = this.prezime_administratora.getText();
        String kEmail = this.email_administratora.getText();
        String kLozinka = lozinka;
        String kKorisnicko_ime = this.korisnicko_ime_administratora.getText();

        if (kIme.equals("") || kPrezime.equals("") || kEmail.equals("") || kLozinka.equals("") || kKorisnicko_ime.equals("")) {
            label_greska.setText("Niste unijeli sve tražene podatke !");
        } else {
            label_greska.setText("");

            Administrator a = new Administrator(id, kIme, kPrezime, kEmail, kKorisnicko_ime, kLozinka);
            AdministratorService.administratorService.uredi(a);
            ponisti();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potvrda");
            alert.setHeaderText("Podaci administratora "+ kIme+" "+kPrezime+" su uspješno ažurirani.");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                try {
                    Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_administratora.fxml"));
                    glavni_prostor.getChildren().setAll(pregled);
                } catch (IOException ex) {
                    Logger.getLogger(Uredi_mobitelController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});

        }
    }
    private void ponisti(){
        ime_administratora.setText("");
        prezime_administratora.setText("");
        email_administratora.setText("");
        korisnicko_ime_administratora.setText("");
    }

}
