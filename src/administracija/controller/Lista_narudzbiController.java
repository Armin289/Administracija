package administracija.controller;

import administracija.model.Racun;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.RacunService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Lista_narudzbiController implements Initializable {

    @FXML
    private TableView tabela_racuna;

    @FXML
    private TableColumn<?, ?> naziv;

    @FXML
    private TableColumn<?, ?> kolicina;
    @FXML
    private TableColumn<?, ?> dostava;
    @FXML
    private TableColumn<?, ?> grad;
    @FXML
    private TableColumn<?, ?> ulica;
    @FXML
    private TableColumn<?, ?> datum;
    @FXML
    private TableColumn<?, ?> vrijeme;
    @FXML
    private JFXTextField naziv_artikla_txt;
    @FXML
    private JFXTextField kolicina_txt;
    @FXML
    private JFXTextField ukupna_cijena_txt;
    @FXML
    private JFXTextField nacin_preuzimanja_txt;
    @FXML
    private JFXTextField datum_kupovine_txt;
    @FXML
    private JFXTextField vrijeme_kupovine_txt;
    @FXML
    private JFXTextField ime_kupca_txt;
    @FXML
    private JFXTextField prezime_kupca_txt;
    @FXML
    private JFXTextField ulica_kupca_txt;
    @FXML
    private JFXTextField grad_kupca_txt;
    @FXML
    private JFXTextField postanski_broj_txt;

    @FXML
    private JFXButton spremi_btn;
    @FXML
    private JFXButton obrisi_btn;

    private int id;
    private int korisnik_id;
    int artikal_id;
    int kolicina_kupljenih;
    Racun odabranastavka;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popuni_tabelu_racuna();
    }

    @FXML
    public void popuni_tabelu_racuna() {
        ObservableList<Racun> racuni = RacunService.racunService.dohvatiNepotvrdeneNarudzbe();

        this.naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        this.kolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        this.dostava.setCellValueFactory(new PropertyValueFactory<>("placanje"));
        this.grad.setCellValueFactory(new PropertyValueFactory<>("grad"));
        this.ulica.setCellValueFactory(new PropertyValueFactory<>("ulica"));
        this.datum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        this.vrijeme.setCellValueFactory(new PropertyValueFactory<>("vrijeme"));

        this.tabela_racuna.setItems(racuni);

    }

    @FXML
    public void postaviRacun(MouseEvent event) throws IOException {

        this.odabranastavka = (Racun) this.tabela_racuna.getSelectionModel().getSelectedItem();
        this.naziv_artikla_txt.setText(this.odabranastavka.getNaziv());
        this.kolicina_txt.setText(String.valueOf(this.odabranastavka.getKolicina()));
        this.ukupna_cijena_txt.setText(String.valueOf(this.odabranastavka.getUkupna_cijena()));
        this.nacin_preuzimanja_txt.setText(this.odabranastavka.getPlacanje());
        this.datum_kupovine_txt.setText(this.odabranastavka.getDatum());
        this.vrijeme_kupovine_txt.setText(this.odabranastavka.getVrijeme());
        this.ime_kupca_txt.setText(this.odabranastavka.getIme());
        this.prezime_kupca_txt.setText(this.odabranastavka.getPrezime());
        this.ulica_kupca_txt.setText(this.odabranastavka.getUlica());
        this.grad_kupca_txt.setText(this.odabranastavka.getGrad());
        this.postanski_broj_txt.setText(this.odabranastavka.getPostanski_broj());

        id = this.odabranastavka.getId();
        korisnik_id = this.odabranastavka.getKorisnik_id();
        artikal_id = this.odabranastavka.getArtikal_id();
        kolicina_kupljenih = this.odabranastavka.getKolicina();

    }

    @FXML
    void prihvati(MouseEvent event) {

        if (kolicina_txt.getText().equals("") || nacin_preuzimanja_txt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Niste popunili sva polja");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });
        } else {

            int kolicina = Integer.valueOf(kolicina_txt.getText());
            Float ukupna_cijena = Float.parseFloat(ukupna_cijena_txt.getText());
            String nacin_preuzimanja = nacin_preuzimanja_txt.getText();
            String datum = datum_kupovine_txt.getText();
            String vrijeme = vrijeme_kupovine_txt.getText();
            String Status = "Prihvaćeno";

            if (kolicina > 0) {
                this.odabranastavka.setId(id);
                System.out.println(id);
                this.odabranastavka.setKorisnik_id(korisnik_id);
                this.odabranastavka.setArtikal_id(artikal_id);
                this.odabranastavka.setKolicina(kolicina);
                this.odabranastavka.setPlacanje(nacin_preuzimanja);
                this.odabranastavka.setDatum(datum);
                this.odabranastavka.setVrijeme(vrijeme);
                this.odabranastavka.setStatus(Status);
                RacunService.racunService.prihvati(this.odabranastavka);
                ponisti();
                popuni_tabelu_racuna();
                this.odabranastavka = null;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Količina artikla ne može biti 0");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                    }
                });
            }
        }
    }

    @FXML
    private void obrisi(MouseEvent event) {
        if (this.odabranastavka != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda brisanja");
            alert.setContentText("Jeste li sigurni da želite obrisati stavku za: " + odabranastavka.getNaziv() + " ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                RacunService.racunService.brisi(this.odabranastavka);
                ponisti();
                popuni_tabelu_racuna();
                this.odabranastavka = null;
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Niste odabrali stavku koju želite obrisati!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });
        }
    }

    private void ponisti() {
        naziv_artikla_txt.setText("");
        kolicina_txt.setText("");
        ukupna_cijena_txt.setText("");
        nacin_preuzimanja_txt.setText("");
        datum_kupovine_txt.setText("");
        vrijeme_kupovine_txt.setText("");
        ime_kupca_txt.setText("");
        prezime_kupca_txt.setText("");
        ulica_kupca_txt.setText("");
        grad_kupca_txt.setText("");
        postanski_broj_txt.setText("");
    }
}
