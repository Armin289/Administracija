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

public class Dodaj_opremuController implements Initializable {

    @FXML
    private Label label_greska;
    @FXML
    private ImageView slika_img;
    @FXML
    private JFXTextField naziv_txt;
    @FXML
    private JFXTextField cijena_txt;
    @FXML
    private JFXComboBox garancija;
    @FXML
    private JFXComboBox tip;
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
    ObservableList<String> tip_list = FXCollections.observableArrayList("Maska", "Punjač", "Baterija", "Slušalice", "Zaštitno staklo", "Folija", "Memorijska kartica");
    Administrator prijavljeni = PrijavaService.logiraniKorisnik();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        garancija.setItems(garancija_list);
        tip.setItems(tip_list);
        this.garancija.setValue(null);
        this.tip.setValue(null);

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
        String aOpis = this.opis_txt.getText();
        String aGarancija = (String) this.garancija.getValue();
        String aTip = (String) this.tip.getValue();
        String aVrsta = "Oprema";
        String aNull = "";
        int aLogin_id = prijavljeni.getId();
        byte[] aSlika = slika_artikla;

        if (aNaziv.equals("") || aCijena <= 0 || aGarancija.equals("") || aOpis.equals("")
                || slika_string == null || aVrsta.equals("") || aTip.equals("")) {
            label_greska.setText("Niste unijeli sve tražene podatke !");
        } else {
            label_greska.setText("");

            Artikal artikal = new Artikal(0, aNaziv, aCijena, aGarancija, aOpis, aNull, aNull,
                    aNull, aNull, aNull, aNull, aNull, aNull, slika_string, aNull, aNull, aVrsta, aTip, aLogin_id);
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
        this.opis_txt.setText("");
        this.garancija.setValue(null);
        this.tip.setValue(null);
        slika_img.setImage(null);
    }
}
