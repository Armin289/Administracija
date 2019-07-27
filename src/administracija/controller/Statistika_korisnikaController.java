package administracija.controller;

import static administracija.model.Baza.DB;
import administracija.model.Korisnik;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.KorisnikService;
import service.RacunService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Statistika_korisnikaController implements Initializable {

    @FXML
    private TableView tabela_mobitela;

    @FXML
    private TableColumn<?, ?> ime;

    @FXML
    private TableColumn<?, ?> prezime;

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private TableColumn<?, ?> broj_kupljenih_artikala;

    @FXML
    private TableColumn<?, ?> ukupna_potrosnja;

    @FXML
    private Label broj_kupljenih_artikala_txt;

    @FXML
    private Label zarada_odabranog_korisnika_txt;

    @FXML
    private Label datum_zadnje_kupnje_txt;
    @FXML
    private JFXTextField pretraga_txt;
    @FXML
    private PieChart pieChartZarada;

    @FXML
    private PieChart pieChartArtikli;
    @FXML
    private Label zarada_txt;

    @FXML
    private Label udio_txt;
    @FXML
    private double total_artikal;
    @FXML
    private double  total_udio;

    Korisnik odabranikorisnik;
    DecimalFormat df = new DecimalFormat("#.##");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popuni_tabelu_mobitela();
    }

    @FXML
    public void popuni_tabelu_mobitela() {
        ObservableList<Korisnik> korisnici = KorisnikService.korisnikService.sveIzBaze();

        this.ime.setCellValueFactory(new PropertyValueFactory<>("ime"));
        this.prezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        this.email.setCellValueFactory(new PropertyValueFactory<>("email"));

        // this.tabela_mobitela.setItems(artikli);
        this.tabela_mobitela.setItems(korisnici);

    }

    @FXML
    public void postaviPodatke(MouseEvent event) throws IOException {

        this.odabranikorisnik = (Korisnik) this.tabela_mobitela.getSelectionModel().getSelectedItem();
        int id = this.odabranikorisnik.getId();
        int broj_kupovina_korisnika = RacunService.racunService.broj_kupovina_korisnika(id);
        float zarada_od_korisnika = RacunService.racunService.zarada_od_korisnika(id);
        String datum_posljednje_kupovine = RacunService.racunService.datum_zadnje_kupovine(id);
        broj_kupljenih_artikala_txt.setText(String.valueOf(broj_kupovina_korisnika));
        zarada_odabranog_korisnika_txt.setText(String.valueOf(zarada_od_korisnika));
        datum_zadnje_kupnje_txt.setText(datum_posljednje_kupovine);
        total_udio=0;
        total_artikal=0;
        popuni_graf_artiklima(id);
        popuni_graf_udjelima(id);

    }

    @FXML
    public void pretrazi(KeyEvent evt) {
        ObservableList<Korisnik> korisnici2 = KorisnikService.korisnikService.pretragaIzBaze(pretraga_txt.getText());
        this.ime.setCellValueFactory(new PropertyValueFactory<>("ime"));
        this.prezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        this.email.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.tabela_mobitela.setItems(korisnici2);
    }

    // SELECT a.vrsta, count(*) FROM artikal a, kupovina k WHERE k.artikalid=a.id AND k.korisnikid='29' GROUP BY a.vrsta
    private void popuni_graf_artiklima(int id) {

        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(rs.kolicina) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND zr.kupac_id='"+id+"' AND rs.zaglavlje_id = zr.id GROUP BY a.vrsta");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                total_artikal = total_artikal + rs.getDouble(2);
                detalji.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
            }
            
            pieChartArtikli.setAnimated(false);
            pieChartArtikli.setData(detalji);

            pieChartArtikli.setLegendSide(Side.LEFT);
            pieChartArtikli.setStartAngle(30);
            pieChartArtikli.getData().stream().forEach(data->{data.getNode().addEventHandler(MouseEvent.ANY, e->{udio_txt.setText(data.getName()+": "+df.format(((data.getPieValue()*100)/total_artikal))+" %");});}
            
            );

        } catch (Exception e) {

        }
    }
    
    private void popuni_graf_udjelima(int id) {

        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, sum(rs.kolicina*a.cijena) FROM artikal a, zaglavlje_racuna zr, racun_stavke rs WHERE rs.artikal_id=a.id AND zr.kupac_id='"+id+"' AND rs.zaglavlje_id = zr.id GROUP BY a.vrsta");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                total_udio = total_udio + rs.getDouble(2);
                detalji.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
            }
            pieChartZarada.setAnimated(false);
            pieChartZarada.setData(detalji);

            pieChartZarada.setLegendSide(Side.LEFT);
            pieChartZarada.setStartAngle(30);
            pieChartZarada.getData().stream().forEach(data->{data.getNode().addEventHandler(MouseEvent.ANY, e->{zarada_txt.setText(data.getName()+":  "+df.format(((data.getPieValue()*100)/total_udio))+" %");});}
            
            );
        
        } catch (Exception e) {

        }
    }

}
