/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija.controller;

import administracija.model.Artikal;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import service.ArtikalService;

/**
 * FXML Controller class
 *
 * @author armin
 */
public class Ispis_pdfController implements Initializable {

    /**
     * Initializes the controller class.
     */
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String danasnji_datum = dateFormat.format(new Date());
    
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat stf = new SimpleDateFormat("HH.mm");
    String trenutno_vrijeme = stf.format(cal.getTime());
    private byte [] img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        String naziv_dokumenta = "C:\\Users\\armin\\Desktop\\" +danasnji_datum+"_"+trenutno_vrijeme+".pdf";

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(naziv_dokumenta));
        
        document.open();
        Paragraph para = new Paragraph("Lista artikala");
        document.add(para);
        Paragraph razmak = new Paragraph("");
        document.add(razmak);
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ispis_pdfController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Ispis_pdfController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PdfPTable table = new PdfPTable(5);
        PdfPCell c1 = new PdfPCell(new Phrase("Naziv"));
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Cijena"));
        table.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase("Vrsta"));
        table.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("Garancija"));
        table.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase("Slika"));
        table.addCell(c5);
        table.setHeaderRows(1);

        ObservableList<Artikal> artikli = (ObservableList<Artikal>) ArtikalService.artikalService.sveIzBaze();
        for (Artikal artikal : artikli) {
                
            
            try {
                img = artikal.getSlika();
                ByteArrayInputStream in = new ByteArrayInputStream(img);
                BufferedImage slika;
                
                slika = ImageIO.read(in);
                
                
                WritableImage image = SwingFXUtils.toFXImage(slika, null);
                BufferedImage imag=ImageIO.read(in);
                
                
                table.addCell(artikal.getNaziv());
                table.addCell(String.valueOf(artikal.getCijena())+" KM");
                table.addCell(artikal.getVrsta());
                table.addCell(artikal.getGarancija());
                try {
                    table.addCell(Image.getInstance(img));
                } catch (BadElementException ex) {
                    Logger.getLogger(Ispis_pdfController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Ispis_pdfController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(Ispis_pdfController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            try {
                document.add(table);
            } catch (DocumentException ex) {
                Logger.getLogger(Ispis_pdfController.class.getName()).log(Level.SEVERE, null, ex);
            }
                document.close();
             
        
        
            

    }
    

}
