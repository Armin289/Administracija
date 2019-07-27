package administracija.controller;


import static administracija.model.Baza.DB;
import administracija.model.Korisnik;
import administracija.model.Racun;
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
import javafx.scene.input.MouseEvent;
import service.KorisnikService;
import service.RacunService;

public class Statistika_kupcaController implements Initializable {
    
    @FXML
    private PieChart pieChartZarada;

    @FXML
    private Label zarada_txt;

    @FXML
    private PieChart pieChartArtikli;

    @FXML
    private Label udio_txt;

    @FXML
    private Label ime_prezime_txt;

    @FXML
    private Label zarada_odabranog_korisnika_txt;

    @FXML
    private Label datum_zadnje_kupnje_txt;
    @FXML
    private Label broj_kupljenih_artikala_txt;

    @FXML
    private TableView tablica_racuna;

    @FXML
    private TableColumn naziv;

    @FXML
    private TableColumn kolicina;

    @FXML
    private TableColumn ukupno;

    @FXML
    private TableColumn datum;
    private double total_artikal;
    private double  total_udio;
    DecimalFormat df = new DecimalFormat("#.##");
    int id;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id = Pregled_kupacaController.dohvatiIdKupca();
        Korisnik korisnik = KorisnikService.korisnikService.izBazePremaId(id);
        ime_prezime_txt.setText(korisnik.getIme()+ " " + korisnik.getPrezime());
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
        popuni_tabelu_racunima(id);
    }    
    @FXML
    public void popuni_tabelu_racunima(int id) {
        ObservableList<Racun> racuni = RacunService.racunService.sveIzBazePremaKorisnikId(id);

        this.naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        this.kolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        this.ukupno.setCellValueFactory(new PropertyValueFactory<>("ukupna_cijena"));
        this.datum.setCellValueFactory(new PropertyValueFactory<>("datum"));

        // this.tabela_mobitela.setItems(artikli);
        this.tablica_racuna.setItems(racuni);

    }
    private void popuni_graf_artiklima(int id) {

        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(kolicina) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.kupac_id='"+ id +"' GROUP BY a.vrsta");
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
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(rs.kolicina*a.cijena) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr  WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.kupac_id='"+ id +"' GROUP BY a.vrsta");
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
        
        } catch (Exception e) {}
    }

}
