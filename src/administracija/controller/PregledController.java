package administracija.controller;

import administracija.Utils;
import static administracija.model.Baza.DB;
import com.itextpdf.text.DocumentException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import service.KorisnikService;
import service.PDF_service;
import service.RacunService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class PregledController implements Initializable {

    @FXML
    private Label ukupno_korisnika_txt;
    @FXML
    private Label ukupna_zarada_txt;
    @FXML
    private Label dnevna_zarada_txt;
    @FXML
    private Label broj_notifikacija_txt;
    @FXML
    private Label prodano_danas_txt;
    @FXML
    private Label prodano_ukupno_txt;
    @FXML
    private Label mjesečna_zarada_txt;
    @FXML
    private Pane ikona_pane;
    @FXML
    private Pane glavni_prostor;
    @FXML
    private Pane narudzbe_pane;
    @FXML
    private FontAwesomeIcon zvono;
    @FXML
    private Pane statistika_btn;
    @FXML
    private Pane detalji_artikala;
    @FXML
    private Pane udio_artikala;
    @FXML
    private Pane statistika_korisnika;
    @FXML
    private Pane mjesecna_zarada;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private Label broj_smartphone_txt;
    @FXML
    private Label broj_tableta_txt;
    @FXML
    private Label broj_opreme_txt;
    @FXML
    private PieChart barChart;
    @FXML
    private Label caption1;
    
    double total;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    Date danasnji_datum;
    DecimalFormat df = new DecimalFormat("#.##");
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat stf = new SimpleDateFormat("HH.mm");
    String trenutno_vrijeme = stf.format(cal.getTime());
    private byte[] img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalDate localDate = LocalDate.now();
        dtf.ofPattern("yyy/MM/dd").format(localDate);
        try { 
            danasnji_datum = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.ofPattern("yyyy-MM-dd").format(localDate));
            System.out.println(danasnji_datum);
        } catch (ParseException ex) {
            Logger.getLogger(PregledController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(danasnji_datum);
            int dan = calendar.get(Calendar.DAY_OF_MONTH);
            int mjesec = calendar.get(Calendar.MONTH)+1;
            int godina = calendar.get(Calendar.YEAR);
            String trenutni_datum = godina+"-"+mjesec+"-"+dan;
            String mjesec_string="";
            String zadnji_dan_mjeseca="";
            if(mjesec<10){
                mjesec_string = "0"+String.valueOf(mjesec);
            }
            else{
                mjesec_string = String.valueOf(mjesec);
                System.out.println("Mjesec string je "+mjesec_string);
            }
            if(mjesec_string.equals("01") || mjesec_string.equals("03") || mjesec_string.equals("05") || mjesec_string.equals("07") || mjesec_string.equals("07") || mjesec_string.equals("10") || mjesec_string.equals("12") ){
                zadnji_dan_mjeseca = "31";
            }
            if(mjesec_string.equals("02")){
                zadnji_dan_mjeseca = "28";
            }
            else{
                zadnji_dan_mjeseca="30";
            }
            
            
            String datum1 = godina+"-"+mjesec_string+"-01";
            Date pocetni_datum = new SimpleDateFormat("yyyy-MM-dd").parse(datum1);
            String datum2 = godina+"-"+mjesec_string+"-"+zadnji_dan_mjeseca;
            Date krajnji_datum = new SimpleDateFormat("yyyy-MM-dd").parse(datum2);
            System.out.println("datum1 "+datum1);
            System.out.println("datum2 "+datum2);
            
            popuni_graf();
            ikona_pane.setVisible(false);
            String cijena = Float.toString(RacunService.racunService.izracunaj_ukupnu_zaradu());
            ukupna_zarada_txt.setText(cijena);
            String broj_korisnika = Integer.toString(KorisnikService.korisnikService.ukupno_korisnika());
            ukupno_korisnika_txt.setText(broj_korisnika);
            String dnevna_zarada = Float.toString(RacunService.racunService.izracunaj_dnevnu_zaradu(trenutni_datum));
            dnevna_zarada_txt.setText(dnevna_zarada);
            String mjesecna_zarada = Float.toString(RacunService.racunService.izracunaj_mjesecnu_zaradu(datum1,datum2));
            mjesečna_zarada_txt.setText(mjesecna_zarada);
            int broj_notifikacija = RacunService.racunService.broj_notifikacija();
            String notifikacije = Integer.toString(RacunService.racunService.broj_notifikacija());
            broj_notifikacija_txt.setText(notifikacije);
            
            String broj_prodanih_artikala_danas = Integer.toString(RacunService.racunService.prodano_danas(trenutni_datum));
            prodano_danas_txt.setText(broj_prodanih_artikala_danas);
            
            String broj_prodanih_artikala_ukupno = Integer.toString(RacunService.racunService.prodano_ukupno());
            prodano_ukupno_txt.setText(broj_prodanih_artikala_ukupno);
            
            broj_smartphone_txt.setText(RacunService.racunService.ukupno_smartphone());
            broj_tableta_txt.setText(RacunService.racunService.ukupno_tablet());
            broj_opreme_txt.setText(RacunService.racunService.ukupno_oprema());
            
            if (broj_notifikacija > 0) {
                ikona_pane.setVisible(true);
                FadeTransition labelfadeTransition = new FadeTransition(Duration.seconds(1), broj_notifikacija_txt);
                labelfadeTransition.setFromValue(1.0);
                labelfadeTransition.setToValue(0.0);
                labelfadeTransition.setCycleCount(Animation.INDEFINITE);
                labelfadeTransition.play();
                
                FadeTransition zvonofadeTransition = new FadeTransition(Duration.seconds(1), zvono);
                zvonofadeTransition.setFromValue(2.0);
                zvonofadeTransition.setToValue(0.0);
                zvonofadeTransition.setCycleCount(Animation.INDEFINITE);
                zvonofadeTransition.play();
                
            }
        } catch (ParseException ex) {
            Logger.getLogger(PregledController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void popuni_graf() {

        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(rs.kolicina) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id = zr.id GROUP BY a.vrsta");
            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                total = total + rs.getDouble(2);
                detalji.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
            }

            barChart.setAnimated(false);
            barChart.setData(detalji);
            barChart.setLegendSide(Side.LEFT);
            barChart.setStartAngle(30);

            barChart.getData().stream().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.ANY, e -> {
                    caption1.setText(data.getName() + ":  " + df.format(((data.getPieValue() * 100) / total)) + " %");
                });
            }
            );

        } catch (Exception e) {

        }
        //

        //
    }

    @FXML
    private void otvori_narudzbe(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/lista_narudzbi.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
    }

    @FXML
    private void otvori_dnevnu_zaradu(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/dnevna_zarada.fxml"));
        glavni_prostor.getChildren().clear();
        glavni_prostor.getChildren().setAll(pregled);
    }

    @FXML
    private void otvori_statistiku(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_prodaje.fxml"));
        glavni_prostor.getChildren().clear();
        glavni_prostor.getChildren().setAll(pregled);
    }

    @FXML
    private void otvori_detalje(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/detalji_prodaje.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
    }

    @FXML
    private void otvori_udio_artikala(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pripadnost_prodanih_artikala.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
    }

    @FXML
    private void otvori_statistika_korisnika(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_korisnika.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
    }

    @FXML
    void otvori_pripadnost_artikala(MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pripadnost_prodanih_artikala.fxml"));
        glavni_prostor.getChildren().setAll(pregled);

    }
    @FXML
    void otvori_mjesecnu_zaradu()throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_po_mjesecima.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
    }

    @FXML
    void otvori_printanje_PDF(MouseEvent event) throws IOException {
        PDF_service.pdf_service.svi_artikli_PDF();
    }
    
            
    @FXML
    void kreiraj_PDF_prodano(MouseEvent event) throws IOException, DocumentException {
        PDF_service.pdf_service.prodani_artikli_PDF();
    }
}
