package administracija.controller;

import static administracija.model.Baza.DB;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import service.RacunService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Dnevna_zaradaController implements Initializable {

    @FXML
    private Label zarada_smartphone_txt;
    @FXML
    private Label zarada_tablet_txt;
    @FXML
    private Label zarada_oprema_txt;
    @FXML
    private Label dnevna_zarada;
    @FXML
    private PieChart pieChartZarada;
    @FXML
    private PieChart pieChartUdio;
    @FXML
    private Label udio_artikala_txt;
    @FXML
    private Label udio_zarade_txt;
    @FXML
    private double total_artikal;
    @FXML
    private double total_udio;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //String danasnji_datum = dateFormat.format(new Date());
    DecimalFormat df = new DecimalFormat("#.##");
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat stf = new SimpleDateFormat("HH.mm");
    String trenutno_vrijeme = stf.format(cal.getTime());
    Date date = new Date();
    Date danasnji_datum;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalDate localDate = LocalDate.now();
        try {
            danasnji_datum = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.ofPattern("yyyy-MM-dd").format(localDate));
            System.out.println(danasnji_datum);
        } catch (ParseException ex) {
            Logger.getLogger(PregledController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(danasnji_datum);
        int mjesec = calendar.get(Calendar.MONTH) + 1;
        int godina = calendar.get(Calendar.YEAR);
        int dan = calendar.get(Calendar.DAY_OF_MONTH);
        String mjesec_string = "";
        String dan_string = "";
        String zadnji_dan_mjeseca = "";
        if (dan < 10) {
            dan_string = "0" + String.valueOf(dan);
        } else {
            dan_string = String.valueOf(dan);
            System.out.println("Mjesec string je " + mjesec_string);
            System.out.println("Dan je "+dan);
        }
        if (mjesec < 10) {
            mjesec_string = "0" + String.valueOf(mjesec);
        } else {
            mjesec_string = String.valueOf(mjesec);
            System.out.println("Mjesec string je " + mjesec_string);
        }
        if (mjesec_string.equals("01") || mjesec_string.equals("03") || mjesec_string.equals("05") || mjesec_string.equals("07") || mjesec_string.equals("07") || mjesec_string.equals("10") || mjesec_string.equals("12")) {
            zadnji_dan_mjeseca = "31";
        }
        if (mjesec_string.equals("02")) {
            zadnji_dan_mjeseca = "28";
        } else {
            zadnji_dan_mjeseca = "30";
        }
        String datum1 = godina + "-" + mjesec_string + "-" + dan_string;
        System.out.println(dan);
        System.out.println(mjesec);
        System.out.println(godina);
        System.out.println(datum1);

        float danasnja_zarada = RacunService.racunService.izracunaj_dnevnu_zaradu(datum1);
        float danasnja_zarada_smartphone = RacunService.racunService.zarada_na_smartphone_danas(datum1);
        float danasnja_zarada_tablet = RacunService.racunService.zarada_na_tablet_danas(datum1);
        float danasnja_zarada_oprema = RacunService.racunService.zarada_na_oprema_danas(datum1);
        dnevna_zarada.setText(String.valueOf(danasnja_zarada));
        zarada_smartphone_txt.setText(String.valueOf(danasnja_zarada_smartphone));
        zarada_tablet_txt.setText(String.valueOf(danasnja_zarada_tablet));
        zarada_oprema_txt.setText(String.valueOf(danasnja_zarada_oprema));
        popuni_graf_artiklima(datum1);
        popuni_graf_zaradom(datum1);
    }

    private void popuni_graf_artiklima(String datum) {

        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(rs.kolicina) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr, korisnici o WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.kupac_id=o.id AND zr.datum='" + datum + "' GROUP BY a.vrsta");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                total_artikal = total_artikal + rs.getDouble(2);
                detalji.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
            }
            pieChartUdio.setAnimated(false);
            pieChartUdio.setData(detalji);

            pieChartUdio.setLegendSide(Side.LEFT);
            pieChartUdio.setStartAngle(30);
            pieChartUdio.getData().stream().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.ANY, e -> {
                    udio_artikala_txt.setText(data.getName() + ": " + df.format(((data.getPieValue() * 100) / total_artikal)) + " %");
                });
            }
            );

        } catch (Exception e) {

        }
    }

    private void popuni_graf_zaradom(String datum) {

        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(rs.kolicina*a.cijena) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr, korisnici o WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.kupac_id=o.id AND zr.datum='" + datum + "' GROUP BY a.vrsta");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                total_udio = total_udio + rs.getDouble(2);
                detalji.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
            }
            pieChartZarada.setAnimated(false);
            pieChartZarada.setData(detalji);

            pieChartZarada.setLegendSide(Side.LEFT);
            pieChartZarada.setStartAngle(30);
            pieChartZarada.getData().stream().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.ANY, e -> {
                    udio_zarade_txt.setText(data.getName() + ":  " + df.format(((data.getPieValue() * 100) / total_udio)) + " %");
                });
            }
            );

        } catch (Exception e) {

        }
    }

    public static Date getDateWithoutTimeUsingFormat()
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd");
        return formatter.parse(formatter.format(new Date()));
    }

}
