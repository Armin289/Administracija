/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija.controller;

import static administracija.model.Baza.DB;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Statistika_po_mjesecimaController implements Initializable {

    @FXML
    private LineChart lineChart;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    Date date = new Date();
    Date danasnji_datum;
    DecimalFormat df = new DecimalFormat("#.##");
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat stf = new SimpleDateFormat("HH.mm");
    String trenutno_vrijeme = stf.format(cal.getTime());
    String pocetni_datum;
    String krajnji_datum;

    Date datum_pocetka;
    Date datum_kraja;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datum();

        try {
            popuni_graf(pocetni_datum, krajnji_datum);
        } catch (SQLException ex) {
            Logger.getLogger(Statistika_po_mjesecimaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void popuni_graf(String datum1, String datum2) throws SQLException {
        try {

            XYChart.Series series = new XYChart.Series<>();
            PreparedStatement upit = DB.prepare("SELECT zr.datum, SUM(a.cijena*rs.kolicina) FROM racun_stavke rs, zaglavlje_racuna zr, artikal a WHERE zr.datum >= '" + datum1 + "' AND zr.datum <= '" + datum2 + "' AND rs.artikal_id = a.id AND zr.id = rs.zaglavlje_id GROUP BY zr.datum");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                
                series.getData().add(new XYChart.Data(rs.getString(1), rs.getDouble(2)));
                //System.out.println("2019-07-"+i+"" +" "+ rs.getDouble(2))

            }
            series.setName("Zarada po danima");
            lineChart.getData().add(series);

        } catch (Exception e) {
        }

    }

    private void datum() {
        try {
            LocalDate localDate = LocalDate.now();

            danasnji_datum = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.ofPattern("yyyy-MM-dd").format(localDate));
            System.out.println(danasnji_datum);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(danasnji_datum);
            int dan = calendar.get(Calendar.DAY_OF_MONTH);
            int mjesec = calendar.get(Calendar.MONTH) + 1;
            int godina = calendar.get(Calendar.YEAR);

            String mjesec_string = "";
            String zadnji_dan_mjeseca = "";
            if (mjesec < 10) {
                mjesec_string = "0" + String.valueOf(mjesec);
                mjesec = Integer.valueOf(mjesec_string);
                System.out.println("Mjesec string " + mjesec_string);
            } else {
                mjesec_string = String.valueOf(mjesec);
                mjesec = Integer.valueOf(mjesec_string);
                System.out.println("Mjesec string je " + mjesec_string);
            }

            if (mjesec == 01 || mjesec == 03 || mjesec == 05 || mjesec == 07 || mjesec == 8 || mjesec == 10) {
                zadnji_dan_mjeseca = "31";
            }
            if (mjesec == 02) {
                zadnji_dan_mjeseca = "28";
            }
            if (mjesec == 04 || mjesec == 06 || mjesec == 9 || mjesec == 11) {
                zadnji_dan_mjeseca = "30";
            }

            pocetni_datum = godina + "-" + mjesec_string + "-01";
            Date pocetni_datum = new SimpleDateFormat("yyyy-MM-dd").parse(this.pocetni_datum);
            System.out.println(pocetni_datum);
            krajnji_datum = godina + "-" + mjesec_string + "-" + zadnji_dan_mjeseca;
            System.out.println(krajnji_datum);
            Date krajnji_datum = new SimpleDateFormat("yyyy-MM-dd").parse(this.krajnji_datum);
        } catch (ParseException ex) {
            Logger.getLogger(Statistika_po_mjesecimaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
