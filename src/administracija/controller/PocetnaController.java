package administracija.controller;

import administracija.Utils;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.RacunService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class PocetnaController implements Initializable {

    @FXML
    private Pane glavni_prostor;
    @FXML
    private Pane pocetna_pane;
    @FXML
    private Pane mobiteli_pane;
    @FXML
    private Pane dodaj_mobitel_pane;
    @FXML
    private Pane tableti_pane;
    @FXML
    private Pane dodaj_tablet_pane;
    @FXML
    private Pane oprema_pane;
    @FXML
    private Pane dodaj_opremu_pane;
    @FXML
    private Pane korisnici_pane;
    @FXML
    private Pane dodaj_korisnika_pane;
    @FXML
    private FontAwesomeIcon zatvori_aplikaciju;
    @FXML
    private JFXButton pocetna_btn;
    @FXML
    private JFXButton pregled_mobitela_btn;
    @FXML
    private JFXButton dodaj_mobitel_btn;
    @FXML
    private JFXButton pregled_tableta_btn;
    @FXML
    private JFXButton dodaj_tablet_btn;
    @FXML
    private JFXButton pregled_opreme_btn;
    @FXML
    private JFXButton dodaj_opremu_btn;
    @FXML
    private JFXButton pregled_korisnika_btn;
    @FXML
    private JFXButton dodaj_korisnika_btn;
    @FXML
    private JFXButton odjava_btn;
    @FXML
    private Pane ikona_pane;
    @FXML
    private FontAwesomeIcon zvono;

    @FXML
    public void zatvori(MouseEvent event) {
        System.exit(0);

    }

    String aktivno = "-fx-background-color: #DC143C;-fx-background-radius:30px 1px 1px 30px;";
    String neaktivno = "-fx-background-color: #272D32;-fx-background-radius:30px 1px 1px 30px;";
    String aktivan_button = "-fx-background-color:  #D3D3D3;\n"
            + "    -fx-background-radius:1px 10px 10px 1px;\n"
            + "    -fx-text-fill: #272D32;\n"
            + "    -fx-border-color: #272D32;\n"
            + "    -fx-border-style: solid;\n"
            + "    -fx-border-width: 1px;\n"
            + "    -fx-border-radius: 1px 10px 10px 1px;\n"
            + "    -fx-font-size: 14px;"
            + "    -fx-alignment:left;";
    String neaktivan_button = "-fx-background-color: #272d32;\n"
            + "    -fx-background-radius: 1px 10px 10px 1px;\n"
            + "    -fx-text-fill: #FFFFFF;\n"
            + "    -fx-font-size: 14px;\n"
            + "    -fx-fill:#272D32;\n"
            + "    -fx-background-radius:1px 10px 10px 1px;"
            + "-fx-alignment:left;";

    private double xOffset = 0;
    private double yOffset = 0;
    
    int broj_notifikacija;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ikona_pane.setVisible(false);
        int broj_notifikacija = RacunService.racunService.broj_notifikacija();
        if (broj_notifikacija > 0) {
            ikona_pane.setVisible(true);
            FadeTransition zvonofadeTransition = new FadeTransition(Duration.seconds(1), zvono);
            zvonofadeTransition.setFromValue(2.0);
            zvonofadeTransition.setToValue(0.0);
            zvonofadeTransition.setCycleCount(Animation.INDEFINITE);
            zvonofadeTransition.play();
        }

        Pane pregled;
        try {
            pregled = FXMLLoader.load(Utils.class.getResource("view/pregled.fxml"));
            glavni_prostor.getChildren().setAll(pregled);
            reset_pane(pocetna_pane, pocetna_btn);

        } catch (IOException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
    

    @FXML
    private void otvori_narudzbe(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/lista_narudzbi.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
    }

    @FXML
    private void odjava(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Utils.prikazi(stage, "prijava_registracija");
    }

    @FXML
    private void pocetna(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(pocetna_pane, pocetna_btn);
    }

    @FXML
    private void pregled_mobitela(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_mobitela.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(mobiteli_pane, pregled_mobitela_btn);
    }

    @FXML
    private void dodaj_mobitel(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/dodaj_mobitel.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(dodaj_mobitel_pane, dodaj_mobitel_btn);
    }

    @FXML
    private void pregled_tableta(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_tableta.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(tableti_pane, pregled_tableta_btn);
    }

    @FXML
    private void dodaj_tablet(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/dodaj_tablet.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(dodaj_tablet_pane, dodaj_tablet_btn);
    }

    @FXML
    private void pregled_opreme(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_opreme.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(oprema_pane, pregled_opreme_btn);
    }

    @FXML
    private void dodaj_opremu(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/dodaj_opremu.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(dodaj_opremu_pane, dodaj_opremu_btn);
    }

    @FXML
    private void pregled_korisnika(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_korisnika.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(korisnici_pane, pregled_korisnika_btn);
    }

    @FXML
    private void dodaj_korisnika(MouseEvent event) throws IOException {
        // Pane pane = FXMLLoader.load(getClass().getResource("view/pregled.fxml"));
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/dodaj_korisnika.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
        reset_pane(dodaj_korisnika_pane, dodaj_korisnika_btn);
    }

    public void reset_pane(Pane pane, JFXButton button) {
        pocetna_pane.setStyle(neaktivno);
        mobiteli_pane.setStyle(neaktivno);
        dodaj_mobitel_pane.setStyle(neaktivno);
        tableti_pane.setStyle(neaktivno);
        dodaj_tablet_pane.setStyle(neaktivno);
        oprema_pane.setStyle(neaktivno);
        dodaj_opremu_pane.setStyle(neaktivno);
        korisnici_pane.setStyle(neaktivno);
        dodaj_korisnika_pane.setStyle(neaktivno);

        pocetna_btn.setStyle(neaktivan_button);
        pregled_mobitela_btn.setStyle(neaktivan_button);
        dodaj_mobitel_btn.setStyle(neaktivan_button);
        pregled_tableta_btn.setStyle(neaktivan_button);
        dodaj_tablet_btn.setStyle(neaktivan_button);
        pregled_opreme_btn.setStyle(neaktivan_button);
        dodaj_opremu_btn.setStyle(neaktivan_button);
        pregled_korisnika_btn.setStyle(neaktivan_button);
        dodaj_korisnika_btn.setStyle(neaktivan_button);
        pane.setStyle(aktivno);
        button.setStyle(aktivan_button);
    }
    
    @FXML
    private void minimiziraj(MouseEvent event){
        Stage stage = (Stage)((ImageView)event.getSource()).getScene().getWindow();
        // is stage minimizable into task bar. (true | false)
        stage.setIconified(true);
    }
}
