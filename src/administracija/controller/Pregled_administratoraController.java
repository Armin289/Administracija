package administracija.controller;

import administracija.Utils;
import administracija.model.Administrator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import service.AdministratorService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Pregled_administratoraController implements Initializable {

    @FXML
    private BorderPane glavni_prostor;
    @FXML
    private TilePane administratori_tile;
    @FXML
    private JFXTextField pretraga_txt;
    @FXML
    byte[] img;
    private static int myVariable;

    String style = "-fx-max-width:300px;-fx-min-width:300px; -fx-background-color: #90EE90; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
    String style_brisi = "-fx-max-width:300px;-fx-min-width:300px; -fx-background-color: #DC143C; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
    String style_statistika = "-fx-max-width:300px;-fx-min-width:300px; -fx-background-color: #FF4500; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:30px;";
    String style_ponisti = "-fx-max-width:300px;-fx-min-width:300px; -fx-background-color: #483D8B; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:30px;";
    String style_scroll = "-fx-padding: 10 5 5 5;margin: auto;";
    String style_vbox = "-fx-background-color: #FFFFFF;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: #272D32; -fx-border-style: solid;-fx-border-width: 1px;-fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-padding: 0 0 20 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ListaAdministratora();
        ScrollPane scrollPane = new ScrollPane();    // Vertical scroll bar

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        scrollPane.setMaxHeight(580);
        scrollPane.setMinHeight(580);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(795);
        scrollPane.setMinWidth(790);
        scrollPane.setStyle(style_scroll);
        administratori_tile.setMinWidth(790);
        administratori_tile.setMaxWidth(790);
        administratori_tile.setPrefWidth(790);
        administratori_tile.setPrefColumns(3);
        administratori_tile.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(administratori_tile);
        glavni_prostor.setCenter(scrollPane);
        glavni_prostor.setMaxWidth(800);

    }

    private void ListaAdministratora() {

        ObservableList<Administrator> administratori = (ObservableList<Administrator>) AdministratorService.administratorService.sveIzBaze();

        administratori_tile.setVgap(15);
        administratori_tile.setHgap(20);
        administratori_tile.setOrientation(Orientation.HORIZONTAL);

        for (Administrator administrator : administratori) {

            Text ime_prezime = new Text();
            ime_prezime.setText(administrator.getIme() + " " + administrator.getPrezime());
            ime_prezime.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            ime_prezime.setFill(Color.STEELBLUE);
            //
            Text email = new Text();
            email.setText(administrator.getEmail());
            email.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            email.setFill(Color.ROYALBLUE);
            email.setCursor(Cursor.CLOSED_HAND);
            //
            Text korisnicko_ime = new Text();
            korisnicko_ime.setText(administrator.getKorisnicko_ime());
            korisnicko_ime.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            korisnicko_ime.setFill(Color.STEELBLUE);
            //
            JFXButton btn = new JFXButton();
            btn.setText("Uredi");
            btn.alignmentProperty();
            btn.setStyle(style);
            btn.setOnMouseClicked((event) -> {
                try {
                    postaviIdAdministratora(administrator.getId());
                    Pane pregled;
                    pregled = FXMLLoader.load(Utils.class.getResource("view/uredi_administratora.fxml"));

                    glavni_prostor.getChildren().setAll(pregled);
                } catch (IOException ex) {
                    Logger.getLogger(Pregled_korisnikaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            JFXButton brisi_btn = new JFXButton();
            brisi_btn.setText("Obriši");
            brisi_btn.alignmentProperty();
            brisi_btn.setStyle(style_brisi);
            brisi_btn.setOnMouseClicked((event) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potvrda brisanja");
                alert.setContentText("Jeste li sigurni da želite obrisati " + administrator.getIme() + " ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    AdministratorService.administratorService.brisi_saId(administrator.getId());
                    administratori_tile.getChildren().clear();
                    ListaAdministratora();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            });

            JFXButton ponisti_lozinku_btn = new JFXButton();
            ponisti_lozinku_btn.setText("Poništi lozinku");
            ponisti_lozinku_btn.alignmentProperty();
            ponisti_lozinku_btn.setStyle(style_ponisti);
            ponisti_lozinku_btn.setOnMouseClicked((event) -> {
                try {
                    //Random random = new Random(System.nanoTime());
                    //int randomInt = random.nextInt(1000000);
                    String lozinka = administrator.getIme() + administrator.getPrezime();
                    String kIme = administrator.getIme();
                    String kPrezime = administrator.getPrezime();
                    String kEmail = administrator.getEmail();
                    String kKorisnicko_ime = administrator.getKorisnicko_ime();
                    String kLozinka = lozinka;

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda brisanja");
                    alert.setContentText("Jeste li sigurni da želite poništiti lozinku za " + administrator.getIme() + " " + administrator.getPrezime() + "?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Administrator a = new Administrator(administrator.getId(), kIme, kPrezime, kEmail, kKorisnicko_ime, kLozinka);
                        AdministratorService.administratorService.urediLozinku(a);
                        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_administratora.fxml"));
                        glavni_prostor.getChildren().setAll(pregled);
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Pregled_korisnikaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            VBox vbox = new VBox();
            vbox.setStyle(style_vbox);
            vbox.setSpacing(5);
            //vbox.setPadding(new Insets(5, 0, 0, 0));

            vbox.setMaxWidth(300);
            vbox.setMinWidth(300);
            vbox.setPrefWidth(300);
            vbox.setMinHeight(200);
            vbox.setPrefHeight(200);
            vbox.setMaxHeight(200);
            vbox.setAlignment(Pos.BOTTOM_CENTER);
            vbox.getChildren().addAll(ime_prezime, email, korisnicko_ime, btn, brisi_btn, ponisti_lozinku_btn);
            administratori_tile.getChildren().addAll(vbox);

        }

    }

    @FXML
    private void pretraziKorisnike() {
        administratori_tile.getChildren().clear();
        ObservableList<Administrator> administratori = (ObservableList<Administrator>) AdministratorService.administratorService.pretragaIzBaze(pretraga_txt.getText());

        administratori_tile.setVgap(15);
        administratori_tile.setHgap(20);
        administratori_tile.setOrientation(Orientation.HORIZONTAL);

        for (Administrator administrator : administratori) {

            Text ime_prezime = new Text();
            ime_prezime.setText(administrator.getIme() + " " + administrator.getPrezime());
            ime_prezime.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            ime_prezime.setFill(Color.STEELBLUE);
            //
            Text email = new Text();
            email.setText(administrator.getEmail());
            email.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            email.setFill(Color.ROYALBLUE);
            email.setCursor(Cursor.CLOSED_HAND);
            //
            Text korisnicko_ime = new Text();
            korisnicko_ime.setText(administrator.getKorisnicko_ime());
            korisnicko_ime.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            korisnicko_ime.setFill(Color.STEELBLUE);
            //
            JFXButton btn = new JFXButton();
            btn.setText("Uredi");
            btn.alignmentProperty();
            btn.setStyle(style);
            btn.setOnMouseClicked((event) -> {
                try {
                    postaviIdAdministratora(administrator.getId());
                    Pane pregled;
                    pregled = FXMLLoader.load(Utils.class.getResource("view/uredi_administratora.fxml"));

                    glavni_prostor.getChildren().setAll(pregled);
                } catch (IOException ex) {
                    Logger.getLogger(Pregled_korisnikaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            JFXButton brisi_btn = new JFXButton();
            brisi_btn.setText("Obriši");
            brisi_btn.alignmentProperty();
            brisi_btn.setStyle(style_brisi);
            brisi_btn.setOnMouseClicked((event) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potvrda brisanja");
                alert.setContentText("Jeste li sigurni da želite obrisati " + administrator.getIme() + " ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    AdministratorService.administratorService.brisi_saId(administrator.getId());
                    administratori_tile.getChildren().clear();
                    ListaAdministratora();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            });

            JFXButton ponisti_lozinku_btn = new JFXButton();
            ponisti_lozinku_btn.setText("Poništi lozinku");
            ponisti_lozinku_btn.alignmentProperty();
            ponisti_lozinku_btn.setStyle(style_ponisti);
            ponisti_lozinku_btn.setOnMouseClicked((event) -> {
                try {
                    String lozinka = administrator.getIme() + administrator.getPrezime();
                    String kIme = administrator.getIme();
                    String kPrezime = administrator.getPrezime();
                    String kEmail = administrator.getEmail();
                    String kKorisnicko_ime = administrator.getKorisnicko_ime();
                    String kLozinka = lozinka;

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda brisanja");
                    alert.setContentText("Jeste li sigurni da želite poništiti lozinku za " + administrator.getIme() + " " + administrator.getPrezime() + "?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Administrator a = new Administrator(administrator.getId(), kIme, kPrezime, kEmail, kKorisnicko_ime, kLozinka);
                        AdministratorService.administratorService.uredi(a);
                        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_administratora.fxml"));
                        glavni_prostor.getChildren().setAll(pregled);
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Pregled_korisnikaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            VBox vbox = new VBox();
            vbox.setStyle(style_vbox);
            vbox.setSpacing(5);
            //vbox.setPadding(new Insets(5, 0, 0, 0));

            vbox.setMaxWidth(300);
            vbox.setMinWidth(300);
            vbox.setPrefWidth(300);
            vbox.setMinHeight(200);
            vbox.setPrefHeight(200);
            vbox.setMaxHeight(200);
            vbox.setAlignment(Pos.BOTTOM_CENTER);
            vbox.getChildren().addAll(ime_prezime, email, korisnicko_ime, btn, brisi_btn, ponisti_lozinku_btn);
            administratori_tile.getChildren().addAll(vbox);

        }
    }

    public static void postaviIdAdministratora(int myVariable) {
        Pregled_administratoraController.myVariable = myVariable;
    }

    public static int dohvatiIdAdministratora() {
        return myVariable;
    }
}
