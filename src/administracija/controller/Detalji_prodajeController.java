package administracija.controller;

import administracija.model.Artikal;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.ArtikalService;
import service.RacunService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Detalji_prodajeController implements Initializable {

    @FXML
    private TableView<Artikal>  tabela_mobitela;
    @FXML
    private TableColumn naziv;
    @FXML
    private TableColumn cijena;
    @FXML
    private TableColumn garancija;
    @FXML
    private TableColumn OS;
    @FXML
    private TableColumn proizvodjac;
    @FXML
    private Label broj_prodanih_txt;
    @FXML
    private JFXTextField pretraga_txt;
     
    
    Artikal odabraniartikal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.popuni_tabelu_artiklima();
    }    
    
    @FXML
    public void popuni_tabelu_artiklima () {
        ObservableList <Artikal> artikli = ArtikalService.artikalService.prodaniArtikli();
        
        this.naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        this.cijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        this.garancija.setCellValueFactory(new PropertyValueFactory<>("garancija"));
        this.OS.setCellValueFactory(new PropertyValueFactory<>("operacijski_sustav"));
        this.proizvodjac.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        
       // this.tabela_mobitela.setItems(artikli);
        this.tabela_mobitela.setItems(artikli);
        
    }  
    @FXML
    public void postavi_artikal(MouseEvent event) throws IOException{
        
        this.odabraniartikal =(Artikal) this.tabela_mobitela.getSelectionModel().getSelectedItem();
        int id = this.odabraniartikal.getId();
            this.broj_prodanih_txt.setText(String.valueOf(RacunService.racunService.prodano_artikla(id)));

    }
    
    @FXML
    void pretrazi(KeyEvent event) {
        ObservableList <Artikal> artikli = null;
        artikli = ArtikalService.artikalService.prodaniArtikliPretraga(pretraga_txt.getText());
        
        this.naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        this.cijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        this.garancija.setCellValueFactory(new PropertyValueFactory<>("garancija"));
        this.OS.setCellValueFactory(new PropertyValueFactory<>("operacijski_sustav"));
        this.proizvodjac.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        
        this.tabela_mobitela.setItems(artikli);

    }
   
}
