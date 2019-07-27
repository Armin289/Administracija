package administracija.controller;

import administracija.Utils;
import administracija.model.Administrator;
import administracija.model.Artikal;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import service.ArtikalService;
import service.PrijavaService;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Uredi_opremuController implements Initializable {

    @FXML
    private Pane glavni_prostor;
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
    private ImageView slika_img;

    @FXML
    private JFXButton odaberi_sliku_btn;

    @FXML
    private JFXButton obrisi_btn;

    @FXML
    private JFXButton spremi_btn;

    @FXML
    private Label label_greska;

    int id;
    byte[] slika_artikla;
    byte[] img;
    byte[] slika_artikla_byte;
    private String slika_string;
    private String aSlika;
    File file;
    ObservableList<String> garancija_list = FXCollections.observableArrayList("6 mjeseci", "12 mjeseci", "24 mjeseca", "36 mjeseci");
    ObservableList<String> tip_list = FXCollections.observableArrayList("Maska","Punjač", "Baterija", "Slušalice", "Zaštitno staklo", "Folija", "Memorijska kartica");
    Administrator prijavljeni = PrijavaService.logiraniKorisnik();
    private String naziv_artikla;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        garancija.setItems(garancija_list);
        tip.setItems(tip_list);

        id = Funkcije.funkcije.dohvatiIdOpreme();
        try {
            postavi_artikal(id);
        } catch (IOException ex) {
            Logger.getLogger(Uredi_opremuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void obrisi_artikal(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setContentText("Jeste li sigurni da želite obrisati artikal " + naziv_artikla + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ArtikalService.artikalService.brisi_saId(id);
            Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_opreme.fxml"));
            glavni_prostor.getChildren().setAll(pregled);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    @FXML
    void spremi(MouseEvent event) {
        String aNaziv = this.naziv_txt.getText();
        Double aCijena;
        if (this.cijena_txt.getText().equals("")) {
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
        if (file != null) {
            aSlika = slika_string;
        } else {
            BASE64Encoder encoder = new BASE64Encoder();
            slika_string = encoder.encode(slika_artikla_byte);
            aSlika = slika_string;
        }

        if (aNaziv.equals("") || aCijena <= 0 || aGarancija.equals("") || aOpis.equals("")
                || aSlika == null || aVrsta.equals("") || aTip.equals("")) {
            label_greska.setText("Niste unijeli sve tražene podatke !");
        } else {
            label_greska.setText("");

            Artikal a = new Artikal(id, aNaziv, aCijena, aGarancija, aOpis, aNull, aNull,
                    aNull, aNull, aNull, aNull, aNull, aNull, aSlika, aNull, aNull, aVrsta, aTip, aLogin_id);
            ArtikalService.artikalService.uredi(a);
            ponisti(event);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Potvrda");
            alert.setHeaderText("Artikal "+ aNaziv+" je uspješno ažuriran.");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                try {
                    Pane pregled = FXMLLoader.load(Utils.class.getResource("view/pregled_opreme.fxml"));
                    glavni_prostor.getChildren().setAll(pregled);
                } catch (IOException ex) {
                    Logger.getLogger(Uredi_mobitelController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});

        }
    }

    @FXML
    void dohvati_sliku(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        //Show open file dialog
        file = fileChooser.showOpenDialog(null);
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

    void postavi_artikal(int id) throws IOException {
        Artikal artikal = ArtikalService.artikalService.izBazePremaId(id);
        garancija.setValue(artikal.getGarancija());
        tip.setValue(artikal.getTip());
        naziv_txt.setText(artikal.getNaziv());
        naziv_artikla = artikal.getNaziv();
        cijena_txt.setText(String.valueOf(artikal.getCijena()));
        opis_txt.setText(artikal.getOpis());

        img = artikal.getSlika();
        String s = new String(img);
        BASE64Decoder decoder = new BASE64Decoder();
        slika_artikla_byte = decoder.decodeBuffer(s);
        
        ByteArrayInputStream in = new ByteArrayInputStream(slika_artikla_byte);
        BufferedImage read = ImageIO.read(in);
        slika_img.setImage(SwingFXUtils.toFXImage(read, null));
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
