package administracija.controller;

import administracija.Utils;
import static administracija.model.Baza.DB;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Statistika_prodajeController implements Initializable {

    @FXML
    private Pane glavni_prostor;
    @FXML
    private JFXDatePicker pocetni_datum;
    @FXML
    private JFXDatePicker krajnji_datum;
    @FXML
    private LineChart lineChart;
    @FXML
    private Label label_greska;

    String datum_pocetka;
    String datum_zavrsetka;
    private boolean greska = false;
    Date date1;
    Date date2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void otvori_godisnju_statistiku (MouseEvent event) throws IOException {
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_godisnja.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
    }
    
    @FXML
    private void dohvati_podatke(MouseEvent event) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate p_datum = pocetni_datum.getValue();
        LocalDate k_datum = krajnji_datum.getValue();
        if (p_datum == null || k_datum == null) {
            label_greska.setText("Molimo odaberite datume!");
            greska = false;
        } else {
            date1 = Date.from(p_datum.atStartOfDay(ZoneId.systemDefault()).toInstant());
            date2 = Date.from(k_datum.atStartOfDay(ZoneId.systemDefault()).toInstant());
            greska = date1.compareTo(date2) > 0;
            
            if (greska) {
            label_greska.setText("Molimo provjerite datum");
        } else {
            label_greska.setText("");
            datum_pocetka = p_datum.format(formatter);
            datum_zavrsetka = k_datum.format(formatter);

            load_data(datum_pocetka, datum_zavrsetka);
        }}
    }

    @FXML
    private void load_data(String datum1, String datum2) {

        try {
            lineChart.getData().clear();
            XYChart.Series series = new XYChart.Series<>();
            PreparedStatement upit = DB.prepare("SELECT zr.datum, SUM(a.cijena*rs.kolicina) FROM racun_stavke rs, zaglavlje_racuna zr, artikal a WHERE zr.datum >= '" + datum1 + "' AND zr.datum <= '" + datum2 + "' AND rs.artikal_id = a.id AND zr.id = rs.zaglavlje_id GROUP BY zr.datum");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {

                series.getData().add(new XYChart.Data(rs.getString(1), rs.getDouble(2)));
            }
            lineChart.setAnimated(false);
            series.setName("Zarada po datumima");
            lineChart.getData().add(series);

        } catch (Exception e) {

        }
    }
}
