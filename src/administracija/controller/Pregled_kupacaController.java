package administracija.controller;

import administracija.Utils;
import administracija.model.Korisnik;
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
import service.KorisnikService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Pregled_kupacaController implements Initializable {

    @FXML
    private BorderPane glavni_prostor;

    @FXML
    private TilePane kupci_tile;

    @FXML
    private JFXTextField pretraga_txt;
    @FXML
    byte[] img;
    private static int myVariable; 

    String style = "-fx-max-width:300px;-fx-min-width:300px; -fx-background-color: #90EE90; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
    String style_brisi = "-fx-max-width:300px;-fx-min-width:300px; -fx-background-color: #DC143C; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
    String style_statistika = "-fx-max-width:300px;-fx-min-width:300px; -fx-background-color: #FF4500; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:30px;";
    String style_ponisti = "-fx-max-width:300px;-fx-min-width:300px; -fx-background-color: #483D8B; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:30px;";
    String style_vbox = "-fx-background-color: #FFFFFF; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #483D8B; -fx-border-style: solid; -fx-border-width: 1px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-padding: 0 0 20 0;";
    String style_scroll = "-fx-padding: 10 5 5 5;margin: auto;";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        ListaKupaca();
        ScrollPane scrollPane = new ScrollPane();    // Vertical scroll bar

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        scrollPane.setMaxHeight(590);
        scrollPane.setMinHeight(580);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(795);
        scrollPane.setMinWidth(790);
        scrollPane.setStyle(style_scroll);
        kupci_tile.setMinWidth(790);
        kupci_tile.setMaxWidth(790);
        kupci_tile.setPrefWidth(790);
        kupci_tile.setPrefColumns(3);
        kupci_tile.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(kupci_tile);
        glavni_prostor.setCenter(scrollPane);
        glavni_prostor.setMaxWidth(800);

    }

    private void ListaKupaca() {

        ObservableList<Korisnik> korisnici = (ObservableList<Korisnik>) KorisnikService.korisnikService.sveIzBaze();

        kupci_tile.setVgap(15);
        kupci_tile.setHgap(20);
        kupci_tile.setOrientation(Orientation.HORIZONTAL);

        for (Korisnik kupac : korisnici) {

            Text ime_prezime = new Text();
            ime_prezime.setText(kupac.getIme() + " " + kupac.getPrezime());
            ime_prezime.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            ime_prezime.setFill(Color.STEELBLUE);
            
            Text email = new Text();
            email.setText(kupac.getEmail());
            email.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            email.setFill(Color.ROYALBLUE);
            email.setCursor(Cursor.CLOSED_HAND);

            Text adresa = new Text();
            adresa.setText(kupac.getAdresa());
            adresa.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            adresa.setFill(Color.ROYALBLUE);
            adresa.setCursor(Cursor.CLOSED_HAND);

            Text grad = new Text();
            grad.setText(kupac.getGrad());
            grad.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            grad.setFill(Color.ROYALBLUE);
            grad.setCursor(Cursor.CLOSED_HAND);

            JFXButton btn = new JFXButton();
            btn.setText("Uredi");
            btn.alignmentProperty();
            btn.setStyle(style);
            btn.setOnMouseClicked((event) -> {
                try {
                    postaviIdKupca(kupac.getId());
                    Pane pregled;
                    pregled = FXMLLoader.load(Utils.class.getResource("view/uredi_korisnika.fxml"));

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
                alert.setContentText("Jeste li sigurni da želite obrisati " + kupac.getIme() + " ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    KorisnikService.korisnikService.brisi_saId(kupac.getId());
                    kupci_tile.getChildren().clear();
                    ListaKupaca();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            });
            
            JFXButton statistika_btn = new JFXButton();
            statistika_btn.setText("Statistika");
            statistika_btn.alignmentProperty();
            statistika_btn.setStyle(style_statistika);
            statistika_btn.setOnMouseClicked((event) -> {
                try {
                    postaviIdKupca(kupac.getId());
                    Pane pregled;
                    pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_kupca.fxml"));

                    glavni_prostor.getChildren().setAll(pregled);
                } catch (IOException ex) {
                    Logger.getLogger(Pregled_korisnikaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            JFXButton ponisti_lozinku_btn = new JFXButton();
            ponisti_lozinku_btn.setText("Poništi lozinku");
            ponisti_lozinku_btn.alignmentProperty();
            ponisti_lozinku_btn.setStyle(style_ponisti);
            ponisti_lozinku_btn.setOnMouseClicked((event) -> {
                try {
                    String lozinka = kupac.getIme() + kupac.getPrezime();
                    System.out.println(lozinka);
                    String kIme = kupac.getIme();
                    String kPrezime = kupac.getPrezime();
                    String kEmail = kupac.getEmail();
                    String kLozinka = lozinka;
                    String kAdresa = kupac.getAdresa();
                    String kGrad_kupca = kupac.getGrad();
                    int kPostanski_broj = kupac.getPostanskibroj();
                    String kTelefon = kupac.getTelefon();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda brisanja");
                    alert.setContentText("Jeste li sigurni da želite poništiti lozinku za "+ kupac.getIme()+" "+kupac.getPrezime()+"?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Korisnik k = new Korisnik(kupac.getId(), kIme, kPrezime, kEmail, kLozinka, kAdresa, kGrad_kupca, kPostanski_broj, kTelefon);
                        KorisnikService.korisnikService.uredi_lozinku(k);
                        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_kupaca.fxml"));
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
            vbox.setMinHeight(300);
            vbox.setPrefHeight(300);
            vbox.setMaxHeight(300);
            vbox.setAlignment(Pos.BOTTOM_CENTER);
            vbox.getChildren().addAll(ime_prezime, email, adresa, grad, btn, brisi_btn, statistika_btn, ponisti_lozinku_btn);
            kupci_tile.getChildren().addAll(vbox);

        }

    }

    @FXML
    private void pretraziKorisnike() {
        kupci_tile.getChildren().clear();
        ObservableList<Korisnik> korisnici = (ObservableList<Korisnik>) KorisnikService.korisnikService.pretragaIzBaze(pretraga_txt.getText());

        kupci_tile.setVgap(15);
        kupci_tile.setHgap(20);
        kupci_tile.setOrientation(Orientation.HORIZONTAL);

        for (Korisnik korisnik : korisnici) {

            Text ime_prezime = new Text();
            ime_prezime.setText(korisnik.getIme() + " " + korisnik.getPrezime());
            ime_prezime.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            ime_prezime.setFill(Color.STEELBLUE);
            
            Text email = new Text();
            email.setText(korisnik.getEmail());
            email.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            email.setFill(Color.ROYALBLUE);
            email.setCursor(Cursor.CLOSED_HAND);

            Text adresa = new Text();
            adresa.setText(korisnik.getAdresa());
            adresa.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            adresa.setFill(Color.ROYALBLUE);
            adresa.setCursor(Cursor.CLOSED_HAND);

            Text grad = new Text();
            grad.setText(korisnik.getGrad());
            grad.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            grad.setFill(Color.ROYALBLUE);
            grad.setCursor(Cursor.CLOSED_HAND);

            JFXButton btn = new JFXButton();
            btn.setText("Uredi");
            btn.alignmentProperty();
            btn.setStyle(style);
            btn.setOnMouseClicked((event) -> {
                try {
                    postaviIdKupca(korisnik.getId());
                    Pane pregled;
                    pregled = FXMLLoader.load(Utils.class.getResource("view/uredi_korisnika.fxml"));

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
                alert.setContentText("Jeste li sigurni da želite obrisati " + korisnik.getIme() + " ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    KorisnikService.korisnikService.brisi_saId(korisnik.getId());
                    kupci_tile.getChildren().clear();
                    ListaKupaca();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            });
            
            JFXButton statistika_btn = new JFXButton();
            statistika_btn.setText("Statistika");
            statistika_btn.alignmentProperty();
            statistika_btn.setStyle(style_statistika);
            statistika_btn.setOnMouseClicked((event) -> {
                try {
                    postaviIdKupca(korisnik.getId());
                    Pane pregled;
                    pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_kupca.fxml"));

                    glavni_prostor.getChildren().setAll(pregled);
                } catch (IOException ex) {
                    Logger.getLogger(Pregled_korisnikaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            JFXButton ponisti_lozinku_btn = new JFXButton();
            ponisti_lozinku_btn.setText("Poništi lozinku");
            ponisti_lozinku_btn.alignmentProperty();
            ponisti_lozinku_btn.setStyle(style_ponisti);
            ponisti_lozinku_btn.setOnMouseClicked((event) -> {
                try {
                    String lozinka = korisnik.getIme() + korisnik.getPrezime();
                    System.out.println(lozinka);
                    String kIme = korisnik.getIme();
                    String kPrezime = korisnik.getPrezime();
                    String kEmail = korisnik.getEmail();
                    String kLozinka = lozinka;
                    String kAdresa = korisnik.getAdresa();
                    String kGrad_kupca = korisnik.getGrad();
                    int kPostanski_broj = korisnik.getPostanskibroj();
                    String kTelefon = korisnik.getTelefon();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda brisanja");
                    alert.setContentText("Jeste li sigurni da želite poništiti lozinku za "+ korisnik.getIme()+" "+korisnik.getPrezime()+"?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Korisnik k = new Korisnik(korisnik.getId(), kIme, kPrezime, kEmail, kLozinka, kAdresa, kGrad_kupca, kPostanski_broj, kTelefon);
                        KorisnikService.korisnikService.uredi(k);
                        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_kupaca.fxml"));
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
            vbox.setMinHeight(300);
            vbox.setPrefHeight(300);
            vbox.setMaxHeight(300);
            vbox.setAlignment(Pos.BOTTOM_CENTER);
            vbox.getChildren().addAll(ime_prezime, email, adresa, grad, btn, brisi_btn, statistika_btn, ponisti_lozinku_btn);
            kupci_tile.getChildren().addAll(vbox);

        }
    }

    public static void postaviIdKupca(int myVariable) {
        Pregled_kupacaController.myVariable = myVariable;
    }

    public static int dohvatiIdKupca() {
        return myVariable;
    }
}