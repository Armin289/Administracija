package administracija.controller;

import administracija.model.Artikal;
import static administracija.model.Baza.DB;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import service.ArtikalService;
import service.RacunService;
import sun.misc.BASE64Decoder;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Statistika_tabletaController implements Initializable {

    @FXML
    private Label naziv_txt;
    @FXML
    private Label broj_prodanih;
    @FXML
    private Label ukupna_zarada;
    @FXML
    private Label zadnja_kupovina;
    @FXML
    private ImageView slika_img;
    @FXML
    private PieChart pieChart;
    
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            id = Funkcije.dohvatiIdTableta();
            Artikal artikal = ArtikalService.artikalService.izBazePremaId(id);
            byte[] img = artikal.getSlika();
            String s = new String(img);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] slika_artikla = decoder.decodeBuffer(s);
            ByteArrayInputStream in = new ByteArrayInputStream(slika_artikla);
            BufferedImage read = ImageIO.read(in);
            slika_img.setImage(SwingFXUtils.toFXImage(read, null));
            this.naziv_txt.setText(artikal.getNaziv());
            this.broj_prodanih.setText(String.valueOf(RacunService.racunService.prodano_artikla(id)));
            this.ukupna_zarada.setText(String.valueOf(RacunService.racunService.ukupna_zarada_na_artiklu(id)));
            this.zadnja_kupovina.setText(RacunService.racunService.datum_zadnje_kupovine_artikla(id));
            load_data(id);
        } catch (IOException ex) {
            Logger.getLogger(Statistika_artiklaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void load_data(int id) {
        try {
            ObservableList<PieChart.Data> detalji = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT a.vrsta, SUM(rs.kolicina) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE k.artikalid=a.id AND rs.zaglavlje_id=zr.id GROUP BY a.vrsta");
            Float zarada_na_artiklu = RacunService.racunService.ukupna_zarada_na_artiklu(id);
            Float ukupna_zarada = RacunService.racunService.izracunaj_ukupnu_zaradu() - zarada_na_artiklu;

            double total = 9000;
            System.out.println(total);
            detalji.add(new PieChart.Data(naziv_txt.getText(), zarada_na_artiklu));
            detalji.add(new PieChart.Data("Ostalo ", ukupna_zarada));

            pieChart.setAnimated(false);
            pieChart.setData(detalji);
            pieChart.setLegendSide(Side.LEFT);
            pieChart.setStartAngle(30);

        } catch (Exception e) {

        }

    }
}
