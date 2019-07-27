package administracija.controller;

import administracija.model.Artikal;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import service.ArtikalService;

public class Pregled_opremeController implements Initializable {

    @FXML
    private BorderPane glavni_prostor;
    @FXML
    private TilePane artikli_tile;
    @FXML
    private JFXTextField pretraga_txt;

    @FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DohvatiArtikle();
        Funkcije.funkcije.predlozak(artikli_tile, glavni_prostor);
    }

    private void DohvatiArtikle() {

        ObservableList<Artikal> artikli = (ObservableList<Artikal>) ArtikalService.artikalService.sveIzBazeOprema();
        Funkcije.funkcije.ispisOpreme(artikli_tile, glavni_prostor, artikli);

    }

    @FXML
    private void PretraziArtikle() {
        ObservableList<Artikal> artikli = (ObservableList<Artikal>) ArtikalService.artikalService.pretragaIzBaze(pretraga_txt.getText(), "Oprema");
        Funkcije.funkcije.ispisOpreme(artikli_tile, glavni_prostor, artikli);

    }

}
