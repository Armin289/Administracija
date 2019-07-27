package administracija.controller;

import administracija.model.Administrator;
import administracija.model.Artikal;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import service.ArtikalService;
import service.PrijavaService;
import sun.misc.BASE64Encoder;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Dodaj_tabletController implements Initializable {

    @FXML
    private Label label_greska;
    @FXML
    private ImageView slika_img;
    @FXML
    private JFXTextField naziv_txt;
    @FXML
    private JFXTextField cijena_txt;
    @FXML
    private JFXTextField ekran_txt;
    @FXML
    private JFXComboBox garancija;
    @FXML
    private JFXComboBox interna_memorija;
    @FXML
    private JFXTextField kapacitet_baterije_txt;
    @FXML
    private JFXComboBox operacijski_sustav;
    @FXML
    private JFXComboBox glavna_kamera;
    @FXML
    private JFXComboBox prednja_kamera;
    @FXML
    private JFXTextField cpu_txt;
    @FXML
    private JFXComboBox ram_memorija;
    @FXML
    private JFXTextField link_txt;
    @FXML
    private JFXComboBox proizvodjac;
    @FXML
    private JFXTextArea opis_txt;
    @FXML
    private JFXButton spremi_btn;
    @FXML
    private JFXButton ocisti_btn;
    @FXML
    private JFXButton odaberi_sliku_btn;

    byte[] slika_artikla;
    private String slika_string;
    ObservableList<String> garancija_list = FXCollections.observableArrayList("6 mjeseci", "12 mjeseci", "24 mjeseca", "36 mjeseci");
    ObservableList<String> RAM_memorija_list = FXCollections.observableArrayList("1GB", "1.5GB", "2GB", "3GB", "4GB", "6GB", "8GB", "12GB");
    ObservableList<String> interna_memorija_list = FXCollections.observableArrayList("4GB", "8GB", "16GB", "32GB", "64GB", "128GB", "256GB", "512GB", "1TB");
    ObservableList<String> OS_list = FXCollections.observableArrayList("Android", "iOS", "Windows");
    ObservableList<String> glavna_kamera_list = FXCollections.observableArrayList("2MP", "3.15MP", "5MP", "6MP", "8MP", "12MP", "13MP", "12MP+12MP+16MP", "40MP+20MP", "16MP");
    ObservableList<String> prednja_kamera_list = FXCollections.observableArrayList("2MP", "5MP", "6MP", "8MP", "12MP", "16MP");
    ObservableList<String> proizvodjac_list = FXCollections.observableArrayList("Samsung", "Apple", "LG", "Huawei");
    Administrator prijavljeni = PrijavaService.logiraniKorisnik();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        garancija.setItems(garancija_list);
        interna_memorija.setItems(interna_memorija_list);
        operacijski_sustav.setItems(OS_list);
        glavna_kamera.setItems(glavna_kamera_list);
        prednja_kamera.setItems(prednja_kamera_list);
        proizvodjac.setItems(proizvodjac_list);
        ram_memorija.setItems(RAM_memorija_list);
        this.ram_memorija.setValue(null);
        this.garancija.setValue(null);
        this.interna_memorija.setValue(null);
        this.prednja_kamera.setValue(null);
        this.glavna_kamera.setValue(null);
        this.proizvodjac.setValue(null);
        this.operacijski_sustav.setValue(null);
    }

    @FXML
    public void dohvati_sliku(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            slika_img.setImage(image);

            try (ByteArrayOutputStream out = new ByteArrayOutputStream(262144)) {
                ImageIO.write(bufferedImage, "jpg", out);
                ImageIO.write(bufferedImage, "png", out);
                out.flush();
                slika_artikla = out.toByteArray();
                BASE64Encoder encoder = new BASE64Encoder();
                slika_string = encoder.encode(slika_artikla);
            }
        } catch (IOException ex) {

        }
    }

    public void spremi(MouseEvent event) {
        Double aCijena;
        String aNaziv = this.naziv_txt.getText();
        String test = cijena_txt.getText();
        if (test.equals("")) {
            aCijena = 0.0;
        } else {
            aCijena = Double.parseDouble(this.cijena_txt.getText());
        }
        String aKapacitet_baterije = this.kapacitet_baterije_txt.getText();
        String aCpu = this.cpu_txt.getText();
        String aRam = (String) this.ram_memorija.getValue();
        String aLink = this.link_txt.getText();
        String aOpis = this.opis_txt.getText();
        String aEkran = this.ekran_txt.getText();
        String aGarancija = (String) this.garancija.getValue();
        String aInterna_memorija = (String) this.interna_memorija.getValue();
        String aPrednja_kamera = (String) this.prednja_kamera.getValue();
        String aGlavna_kamera = (String) this.glavna_kamera.getValue();
        String aProizvodjac = (String) this.proizvodjac.getValue();
        String aOperacijski_sustav = (String) this.operacijski_sustav.getValue();
        String aTip = "uređaj";
        String aVrsta = "Tablet";
        int aLogin_id = prijavljeni.getId();

        if (aNaziv.equals("") || aCijena == null || aGarancija.equals("") || aOpis.equals("") || aInterna_memorija.equals("") || aKapacitet_baterije.equals("") || aOperacijski_sustav.equals("") || aGlavna_kamera.equals("") || aPrednja_kamera.equals("")
                || aEkran.equals("") || aCpu.equals("") || aRam.equals("") || aLink.equals("") || aProizvodjac.equals("") || slika_string == null || aVrsta.equals("") || aTip.equals("")) {
            label_greska.setText("Niste unijeli sve tražene podatke !");
        } else {
            label_greska.setText("");

            Artikal artikal = new Artikal(0, aNaziv, aCijena, aGarancija, aOpis, aInterna_memorija, aKapacitet_baterije,
                    aOperacijski_sustav, aGlavna_kamera, aEkran, aPrednja_kamera, aCpu, aRam, slika_string, aLink, aProizvodjac, aVrsta, aTip, aLogin_id);
            ArtikalService.artikalService.spasi(artikal);
            ponisti(event);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potvrda");
            alert.setHeaderText("Artikal " + aNaziv + " je uspješno spremljen.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });

        }
    }

    public void ponisti(MouseEvent event) {
        this.naziv_txt.setText("");
        this.cijena_txt.setText("");
        this.kapacitet_baterije_txt.setText("");
        this.cpu_txt.setText("");
        this.ram_memorija.setValue(null);
        this.link_txt.setText("");
        this.opis_txt.setText("");
        this.ekran_txt.setText("");
        this.garancija.setValue(null);
        this.interna_memorija.setValue(null);
        this.prednja_kamera.setValue(null);
        this.glavna_kamera.setValue(null);
        this.proizvodjac.setValue(null);
        this.operacijski_sustav.setValue(null);
        slika_img.setImage(null);
    }

}
