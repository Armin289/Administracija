/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija.controller;

import administracija.Utils;
import administracija.model.Artikal;
import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import service.ArtikalService;
import sun.misc.BASE64Decoder;

/**
 *
 * @author armin
 */

public class Funkcije {
    
    public static final Funkcije funkcije = new Funkcije();
    
    private static int id_opreme;
    private static int id_mobitela;
    private static int id_tableta;

    
    public static void predlozak(TilePane artikli_tile, BorderPane glavni_prostor){
        String style_scroll = "-fx-padding: 10 5 5 5;margin: auto;";
        ScrollPane scrollPane = new ScrollPane();    // Vertical scroll bar

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        scrollPane.setMaxHeight(620);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setMaxWidth(795);
        scrollPane.setMinWidth(790);
        scrollPane.setStyle(style_scroll);
        artikli_tile.setMinWidth(790);
        artikli_tile.setMaxWidth(790);
        artikli_tile.setPrefWidth(790);
        artikli_tile.setPrefColumns(3);
        artikli_tile.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(artikli_tile);
        glavni_prostor.setCenter(scrollPane);
        glavni_prostor.setMaxWidth(800);
    }
    
    
    public static void ispisOpreme(TilePane artikli_tile, BorderPane glavni_prostor, ObservableList<Artikal> artikli){
        
        String style = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #483D8B; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
        String style_brisi = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #DC143C; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
        String style_statistika = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #FF4500; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:30px;";
        String style_slika = "-fx-border-color: #483D8B; -fx-background-color: #FFFFFF; -fx-border-style: solid;-fx-border-width: 1px;-fx-margin-top:20px;";
        String style_vbox = "-fx-background-color: #FFFFFF;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: #272D32; -fx-border-style: solid;-fx-border-width: 1px;-fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-padding: 0 0 20 0;";

        artikli_tile.getChildren().clear();
        //ObservableList<Artikal> artikli = (ObservableList<Artikal>) ArtikalService.artikalService.sveIzBazeOprema();
        artikli_tile.setVgap(15);
        artikli_tile.setHgap(20);
        artikli_tile.setOrientation(Orientation.HORIZONTAL);

        for (Artikal artikal : artikli) {

            try {
                Text naziv = new Text();
                naziv.setText(artikal.getNaziv());
                naziv.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                naziv.setWrappingWidth(200);
                naziv.setStyle("-fx-text-alignment: center;");
                naziv.setFill(Color.STEELBLUE);

                Text cijena = new Text();
                cijena.setText(artikal.getCijena() + " KM");
                cijena.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                cijena.setFill(Color.ROYALBLUE);

                byte[] img = artikal.getSlika();
                String s = new String(img);
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] slika_artikla = decoder.decodeBuffer(s);
                ImageView slika_img = new ImageView();

                ByteArrayInputStream in = new ByteArrayInputStream(slika_artikla);
                BufferedImage read = ImageIO.read(in);
                slika_img.setImage(SwingFXUtils.toFXImage(read, null));

                slika_img.setStyle(style_slika);

                JFXButton btn = new JFXButton();
                btn.setText("Uredi");
                btn.alignmentProperty();
                btn.setStyle(style);
                btn.setCursor(Cursor.CLOSED_HAND);

                btn.setOnMouseClicked((event) -> {
                    try {
                        postaviIdOpreme(artikal.getId());
                        Pane pregled;
                        pregled = FXMLLoader.load(Utils.class.getResource("view/uredi_opremu.fxml"));
                        glavni_prostor.getChildren().setAll(pregled);
                    } catch (IOException ex) {
                        Logger.getLogger(Funkcije.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                JFXButton brisi_btn = new JFXButton();
                brisi_btn.setText("Obriši");
                brisi_btn.alignmentProperty();
                brisi_btn.setStyle(style_brisi);

                brisi_btn.setOnMouseClicked((event) -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda brisanja");
                    alert.setContentText("Jeste li sigurni da želite obrisati " + artikal.getNaziv() + " ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ArtikalService.artikalService.brisi_saId(artikal.getId());
                        artikli_tile.getChildren().clear();
                        ispisOpreme(artikli_tile,glavni_prostor, artikli);
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                });

                JFXButton statistika_btn = new JFXButton();
                statistika_btn.setText("Statistika");
                statistika_btn.alignmentProperty();
                statistika_btn.setStyle(style_statistika);

                statistika_btn.setOnMouseClicked((event) -> {
                    try {
                        postaviIdOpreme(artikal.getId());
                        Pane pregled;
                        pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_opreme.fxml"));

                        glavni_prostor.getChildren().setAll(pregled);
                    } catch (IOException ex) {
                        Logger.getLogger(Pregled_opremeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                VBox vbox = new VBox();
                vbox.setStyle(style_vbox);
                vbox.setSpacing(5);

                vbox.setMaxWidth(200);
                vbox.setMinWidth(200);
                vbox.setPrefWidth(200);
                vbox.setMinHeight(370);
                vbox.setPrefHeight(370);
                vbox.setMaxHeight(370);

                slika_img.maxHeight(160);
                slika_img.prefHeight(160);
                slika_img.minHeight(160);
                //
                slika_img.maxWidth(135);
                slika_img.prefWidth(135);
                slika_img.minWidth(135);

                slika_img.setFitWidth(150);
                slika_img.setFitHeight(175);

                slika_img.setPreserveRatio(true);
                vbox.setAlignment(Pos.BOTTOM_CENTER);
                vbox.getChildren().addAll(slika_img, naziv, cijena, btn, brisi_btn, statistika_btn);
                artikli_tile.getChildren().addAll(vbox);

            } catch (IOException ex) {
                Logger.getLogger(Pregled_opremeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    public static void postaviIdOpreme(int id) {
        Funkcije.id_opreme = id;
    }

    public static int dohvatiIdOpreme() {
        return id_opreme;
    }
      
    
    public static void ispisMobitela(TilePane artikli_tile, BorderPane glavni_prostor, ObservableList<Artikal> artikli){
        String style = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #483D8B; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
        String style_brisi = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #DC143C; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
        String style_statistika = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #FF4500; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:30px;";
        String style_slika = "-fx-border-color: #483D8B; -fx-background-color: #FFFFFF; -fx-border-style: solid;-fx-border-width: 1px;-fx-margin-top:20px;";
        String style_scroll = "-fx-padding: 10 5 5 5;margin: auto;";
        String style_vbox = "-fx-background-color: #FFFFFF;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: #272D32; -fx-border-style: solid;-fx-border-width: 1px;-fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-padding: 0 0 20 0;";

        artikli_tile.getChildren().clear();
        artikli_tile.setVgap(15);
        artikli_tile.setHgap(20);
        artikli_tile.setOrientation(Orientation.HORIZONTAL);

        for (Artikal artikal : artikli) {

            try {
                Text naziv = new Text();
                naziv.setText(artikal.getNaziv());
                naziv.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                naziv.setWrappingWidth(200);
                naziv.setStyle("-fx-text-alignment: center;");
                naziv.setFill(Color.STEELBLUE);

                Text cijena = new Text();
                cijena.setText(artikal.getCijena() + " KM");
                cijena.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                cijena.setFill(Color.ROYALBLUE);
                
                byte [] img = artikal.getSlika();
                String s = new String(img);
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] slika_artikla = decoder.decodeBuffer(s);
                
                ImageView slika_img = new ImageView();

                ByteArrayInputStream in = new ByteArrayInputStream(slika_artikla);
                BufferedImage read = ImageIO.read(in);
                slika_img.setImage(SwingFXUtils.toFXImage(read, null));

                slika_img.setStyle(style_slika);
                JFXButton btn = new JFXButton();
                btn.setText("Uredi");
                btn.alignmentProperty();
                btn.setStyle(style);

                btn.setOnMouseClicked((event) -> {
                    try {
                        postaviIdMobitela(artikal.getId());
                        Pane pregled;
                        pregled = FXMLLoader.load(Utils.class.getResource("view/uredi_mobitel.fxml"));

                        glavni_prostor.getChildren().setAll(pregled);
                    } catch (IOException ex) {
                        Logger.getLogger(Pregled_mobitelaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                JFXButton brisi_btn = new JFXButton();
                brisi_btn.setText("Obriši");
                brisi_btn.alignmentProperty();
                brisi_btn.setStyle(style_brisi);

                brisi_btn.setOnMouseClicked((event) -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda brisanja");
                    alert.setContentText("Jeste li sigurni da želite obrisati " + artikal.getNaziv() + " ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ArtikalService.artikalService.brisi_saId(artikal.getId());
                        artikli_tile.getChildren().clear();
                        ispisMobitela(artikli_tile, glavni_prostor, artikli);
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                });

                JFXButton statistika_btn = new JFXButton();
                statistika_btn.setText("Statistika");
                statistika_btn.alignmentProperty();
                statistika_btn.setStyle(style_statistika);

                statistika_btn.setOnMouseClicked((event) -> {
                    try {
                        postaviIdMobitela(artikal.getId());
                        Pane pregled;
                        pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_artikla.fxml"));

                        glavni_prostor.getChildren().setAll(pregled);
                    } catch (IOException ex) {
                        Logger.getLogger(Pregled_mobitelaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                VBox vbox = new VBox();
                vbox.setStyle(style_vbox);
                vbox.setSpacing(5);

                vbox.setMaxWidth(200);
                vbox.setMinWidth(200);
                vbox.setPrefWidth(200);
                vbox.setMinHeight(350);
                vbox.setPrefHeight(350);
                vbox.setMaxHeight(350);

                slika_img.maxHeight(160);
                slika_img.prefHeight(160);
                slika_img.minHeight(160);
                //
                slika_img.maxWidth(135);
                slika_img.prefWidth(135);
                slika_img.minWidth(135);

                slika_img.setFitWidth(150);
                slika_img.setFitHeight(175);

                slika_img.setPreserveRatio(true);
                vbox.setAlignment(Pos.BOTTOM_CENTER);
                slika_img.fitWidthProperty().bind(vbox.widthProperty());
                vbox.getChildren().addAll(slika_img, naziv, cijena, btn, brisi_btn, statistika_btn);

                artikli_tile.getChildren().addAll(vbox);

            } catch (IOException ex) {
                Logger.getLogger(Pregled_mobitelaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    public static void postaviIdMobitela(int id) {
        Funkcije.id_mobitela = id;
    }

    public static int dohvatiIdMobitela() {
        return id_mobitela;
    }
    
    
    public static void ispisTableta(TilePane artikli_tile, BorderPane glavni_prostor, ObservableList<Artikal> artikli){
        String style = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #483D8B; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
        String style_brisi = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #DC143C; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:2px;";
        String style_statistika = "-fx-max-width:200px;-fx-min-width:200px; -fx-background-color: #FF4500; -fx-background-radius:5px; -fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-margin-bottom:30px;";
        String style_slika = "-fx-border-color: #483D8B; -fx-background-color: #FFFFFF; -fx-border-style: solid;-fx-border-width: 1px;-fx-margin-top:20px;";
        String style_scroll = "-fx-padding: 10 5 5 5;margin: auto;";
        String style_vbox = "-fx-background-color: #FFFFFF;-fx-background-radius: 10px;-fx-border-radius: 10px;-fx-border-color: #272D32; -fx-border-style: solid;-fx-border-width: 1px;-fx-text-fill:#FFFFFF; -fx-font-size:14px; -fx-padding: 0 0 20 0;";

        artikli_tile.getChildren().clear();
        artikli_tile.setVgap(15);
        artikli_tile.setHgap(20);
        artikli_tile.setOrientation(Orientation.HORIZONTAL);

        for (Artikal artikal : artikli) {

            try {
                Text naziv = new Text();
                naziv.setText(artikal.getNaziv());
                naziv.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                naziv.setWrappingWidth(200);
                naziv.setStyle("-fx-text-alignment: center;");
                naziv.setFill(Color.STEELBLUE);

                Text cijena = new Text();
                cijena.setText(artikal.getCijena() + " KM");
                cijena.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                cijena.setFill(Color.ROYALBLUE);

                byte[] img = artikal.getSlika();
                String s = new String(img);
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] slika_artikla = decoder.decodeBuffer(s);
                ImageView slika_img = new ImageView();

                ByteArrayInputStream in = new ByteArrayInputStream(slika_artikla);
                BufferedImage read = ImageIO.read(in);
                slika_img.setImage(SwingFXUtils.toFXImage(read, null));

                slika_img.setStyle(style_slika);

                JFXButton btn = new JFXButton();
                btn.setText("Uredi");
                btn.alignmentProperty();
                btn.setStyle(style);
                btn.setCursor(Cursor.CLOSED_HAND);

                btn.setOnMouseClicked((event) -> {
                    try {
                        postaviIdTableta(artikal.getId());
                        Pane pregled;
                        pregled = FXMLLoader.load(Utils.class.getResource("view/uredi_tablet.fxml"));

                        glavni_prostor.getChildren().setAll(pregled);
                    } catch (IOException ex) {
                        Logger.getLogger(Pregled_tabletaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                JFXButton brisi_btn = new JFXButton();
                brisi_btn.setText("Obriši");
                brisi_btn.alignmentProperty();
                brisi_btn.setStyle(style_brisi);

                brisi_btn.setOnMouseClicked((event) -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potvrda brisanja");
                    alert.setContentText("Jeste li sigurni da želite obrisati " + artikal.getNaziv() + " ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ArtikalService.artikalService.brisi_saId(artikal.getId());
                        artikli_tile.getChildren().clear();
                        ispisTableta(artikli_tile,glavni_prostor, artikli);
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                });

                JFXButton statistika_btn = new JFXButton();
                statistika_btn.setText("Statistika");
                statistika_btn.alignmentProperty();
                statistika_btn.setStyle(style_statistika);

                statistika_btn.setOnMouseClicked((event) -> {
                    try {
                        postaviIdTableta(artikal.getId());
                        Pane pregled;
                        pregled = FXMLLoader.load(Utils.class.getResource("view/statistika_tableta.fxml"));

                        glavni_prostor.getChildren().setAll(pregled);
                    } catch (IOException ex) {
                        Logger.getLogger(Pregled_tabletaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                VBox vbox = new VBox();
                vbox.setStyle(style_vbox);
                vbox.setSpacing(5);

                vbox.setMaxWidth(200);
                vbox.setMinWidth(200);
                vbox.setPrefWidth(200);
                vbox.setMinHeight(350);
                vbox.setPrefHeight(350);
                vbox.setMaxHeight(350);

                slika_img.maxHeight(160);
                slika_img.prefHeight(160);
                slika_img.minHeight(160);
                //
                slika_img.maxWidth(135);
                slika_img.prefWidth(135);
                slika_img.minWidth(135);

                slika_img.setFitWidth(150);
                slika_img.setFitHeight(175);

                slika_img.setPreserveRatio(true);
                vbox.setAlignment(Pos.BOTTOM_CENTER);
                slika_img.fitWidthProperty().bind(vbox.widthProperty());
                vbox.getChildren().addAll(slika_img, naziv, cijena, btn, brisi_btn, statistika_btn);

                artikli_tile.getChildren().addAll(vbox);

            } catch (IOException ex) {
                Logger.getLogger(Pregled_tabletaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    
    public static void postaviIdTableta(int id) {
        Funkcije.id_tableta = id;
    }

    public static int dohvatiIdTableta() {
        return id_tableta;
    }
}
