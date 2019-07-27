package administracija.controller;

import administracija.model.Administrator;
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
import service.AdministratorService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Dodaj_administratoraController implements Initializable {

    @FXML
    private JFXTextField ime_administratora;
    @FXML
    private JFXTextField prezime_administratora;
    @FXML
    private JFXTextField korisnicko_ime_administratora;
    @FXML
    private JFXPasswordField lozinka_administratora;
    @FXML
    private JFXPasswordField potvrda_lozinke_administratora;
    @FXML
    private JFXTextField email_administratora;
    @FXML
    private JFXButton spremi_btn;
    @FXML
    private JFXButton ocisti_btn;
    @FXML
    private JFXButton pokazi;
    @FXML
    private Label label_greska;

    private String kEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void ponisti(MouseEvent event) {
        this.ime_administratora.setText("");
        this.prezime_administratora.setText("");
        this.email_administratora.setText("");
        this.korisnicko_ime_administratora.setText("");
        this.lozinka_administratora.setText("");
        this.potvrda_lozinke_administratora.setText("");
    }

    public void spremi(MouseEvent event) {
        String kIme = this.ime_administratora.getText();
        String kPrezime = this.prezime_administratora.getText();
        kEmail = this.email_administratora.getText();
        String kKorisnisnicko_ime = this.korisnicko_ime_administratora.getText();
        String kLozinka = this.lozinka_administratora.getText();
        String kPotvrda_lozinke = this.potvrda_lozinke_administratora.getText();

        if (kLozinka.equals(kPotvrda_lozinke)) {

            if (kIme.equals("") || kPrezime.equals("") || kEmail.equals("") || kKorisnisnicko_ime.equals("") || kLozinka.equals("") || kPotvrda_lozinke.equals("")) {
                label_greska.setText("Niste unijeli sve tražene podatke !");
            } else {
                label_greska.setText("");

                Administrator administrator = new Administrator(0, kIme, kPrezime, kEmail, kKorisnisnicko_ime, kLozinka);
                AdministratorService.administratorService.spasi(administrator);
                ponisti(event);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Potvrda");
                alert.setHeaderText("Administrator " + kIme + " " + kPrezime + " je uspješno spremljen.");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                    }
                });

            }
        } else {
            label_greska.setText("Lozinke se ne podudaraju");
        }
    }

}
