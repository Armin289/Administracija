package administracija.controller;

import static administracija.model.Baza.DB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Pripadnost_prodanih_artikalaController implements Initializable {

    @FXML
    private JFXDatePicker pocetni_datum;

    @FXML
    private JFXDatePicker krajnji_datum;

    @FXML
    private JFXButton prikazi_graf;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label caption1;
    @FXML
    private Label label_greska;

    double total;
    DecimalFormat df = new DecimalFormat("#.##");
    String datum_pocetka;
    String datum_kraja;
    Date date1;
    Date date2;
    boolean greška = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ucitaj_grafikon();
    }

    private void ucitaj_grafikon() {

        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(rs.kolicina) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id GROUP BY a.vrsta");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                total = total + rs.getDouble(2);
                detalji.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
            }

            pieChart.setAnimated(false);
            pieChart.setData(detalji);
            pieChart.setLegendSide(Side.LEFT);
            pieChart.setStartAngle(30);

            pieChart.getData().stream().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.ANY, e -> {
                    caption1.setText(data.getName() + ":  " + df.format((data.getPieValue() * 100) / total) + " %");
                });
            }
            );

        } catch (Exception e) {

        }

    }

    private void ucitaj_grafikon_datumi(String datum1, String datum2) {

        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(rs.kolicina) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.datum >= '" + datum1 + "' AND zr.datum<='" + datum2 + "' GROUP BY a.vrsta");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                total = total + rs.getDouble(2);
                detalji.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
            }

            pieChart.setAnimated(false);
            pieChart.setData(detalji);
            pieChart.setLegendSide(Side.LEFT);
            pieChart.setStartAngle(30);

            pieChart.getData().stream().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.ANY, e -> {
                    caption1.setText(data.getName() + ":  " + df.format((data.getPieValue() * 100) / total) + " %");
                });
            }
            );

        } catch (Exception e) {

        }
    }

    @FXML
    void dohvati_podatke(MouseEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate p_datum = pocetni_datum.getValue();
        LocalDate k_datum = krajnji_datum.getValue();

        

        if (p_datum == null || k_datum == null) {
            label_greska.setText("Molimo odaberite datume!");
            greška = false;
        } else {
            date1 = Date.from(p_datum.atStartOfDay(ZoneId.systemDefault()).toInstant());
            date2 = Date.from(k_datum.atStartOfDay(ZoneId.systemDefault()).toInstant());
            greška = date1.compareTo(date2) > 0;
        
        
        if (greška) {
            label_greska.setText("Molimo provjerite datum");
        } else {
            label_greska.setText("");
            datum_pocetka = p_datum.format(formatter);
            datum_kraja = k_datum.format(formatter);

            ucitaj_grafikon_datumi(datum_pocetka, datum_kraja);
        }}
    }

    @FXML
    private void proracun(MouseEvent event) throws IOException {
        final Label caption2 = new Label("");
        caption2.setTextFill(Color.WHITE);
        caption2.setStyle("-fx-font: 12 arial;");

        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption2.setTranslateX(e.getSceneX());
                    caption2.setTranslateY(e.getSceneY());

                    caption1.setText(String.valueOf(data.getPieValue()));
                }
            });
        }
    }

}


            