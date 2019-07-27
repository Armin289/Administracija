package administracija.controller;

import administracija.Utils;
import administracija.controller.Statistika_po_mjesecimaController;
import static administracija.model.Baza.DB;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Statistika_godisnjaController implements Initializable {

    @FXML
    private LineChart lineChart;
    @FXML
    private JFXComboBox godine_combo;
    @FXML
    private Label godina_label;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private Pane glavni_prostor;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    Date date = new Date();
    Date danasnji_datum;
    DecimalFormat df = new DecimalFormat("#.##");
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat stf = new SimpleDateFormat("HH.mm");
    String trenutno_vrijeme = stf.format(cal.getTime());

    ObservableList<String> godine = FXCollections.observableArrayList("2015", "2016", "2017", "2018", "2019", "2020");

    int pocetni_datum;
    int krajnji_datum;
    int dan;
    int mjesec;
    int godina;
    int odabrana_godina;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        postavi_datume();
        godine_combo.setEditable(false);
        godine_combo.setItems(godine);
        godine_combo.setValue(String.valueOf(godina));
        godina_label.setText(String.valueOf(godina));
        System.out.println(pocetni_datum);

        System.out.println(krajnji_datum);
        System.out.println(godina);
        popuni_graf(pocetni_datum, krajnji_datum, godina);
        // TODO

    }

    public void comboAction() {
        System.out.println("Selected value : " + godine_combo.getValue());
        String odabrano = (String) godine_combo.getValue();
        odabrana_godina = Integer.valueOf(odabrano);
        godine_combo.setValue(String.valueOf(odabrana_godina));
        godina_label.setText(String.valueOf(odabrana_godina));
        popuni_graf(pocetni_datum, krajnji_datum, odabrana_godina);

    }

    public void popuni_graf(int mjesec1, int mjesec2, int godina) {
        try {
            
            
            System.out.println("Mjesec 1 "+mjesec1);
            System.out.println("Mjesec 2 "+mjesec2);
            System.out.println("Godina "+godina);
            lineChart.getData().clear();
            XYChart.Series series = new XYChart.Series<>();
            PreparedStatement upit = DB.prepare("SELECT  MONTH (zr.datum), SUM(rs.kolicina*a.cijena) FROM zaglavlje_racuna zr, racun_stavke rs, artikal a WHERE zr.id = rs.zaglavlje_id AND rs.artikal_id=a.id AND MONTH(zr.datum) >= CONVERT(" + mjesec1 + ",UNSIGNED INTEGER)  AND MONTH(zr.datum) <= CONVERT(" + mjesec2 + ",UNSIGNED INTEGER) AND YEAR(zr.datum) = CONVERT(" + godina + ",UNSIGNED INTEGER) GROUP BY MONTH(zr.datum)");
            //PreparedStatement upit = DB.prepare("SELECT  MONTH (zr.datum), SUM(rs.kolicina*a.cijena) FROM zaglavlje_racuna zr, racun_stavke rs, artikal a WHERE zr.id = rs.zaglavlje_id AND rs.artikal_id=a.id AND MONTH(zr.datum) >= CONVERT(INT, "+mjesec1+") AND MONTH(zr.datum) <=CONVERT(INT, "+mjesec2+") AND YEAR(zr.datum) =CONVERT(INT, "+godina+") GROUP BY MONTH(zr.datum)");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {

                series.getData().add(new XYChart.Data(rs.getString(1), rs.getDouble(2)));

            }
            series.setName("Zarada po mjesecima");
            lineChart.getData().add(series);
            lineChart.setAnimated(false);

        } catch (Exception e) {
        }

    }

    public void postavi_datume() {
        try {
            LocalDate localDate = LocalDate.now();

            danasnji_datum = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.ofPattern("yyyy-MM-dd").format(localDate));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(danasnji_datum);
            dan = calendar.get(Calendar.DAY_OF_MONTH);
            mjesec = calendar.get(Calendar.MONTH) + 1;
            godina = calendar.get(Calendar.YEAR);
            System.out.println(godina);

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

            pocetni_datum = Integer.valueOf("01");
            if (godina > odabrana_godina) {
                krajnji_datum = Integer.valueOf(mjesec_string);
            }
            else{
                krajnji_datum = 12;
            }

        } catch (ParseException ex) {
            Logger.getLogger(Statistika_po_mjesecimaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    private void nazad() throws IOException{
        Pane pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_prodaje.fxml"));
        glavni_prostor.getChildren().setAll(pregled);
    
    }

}
