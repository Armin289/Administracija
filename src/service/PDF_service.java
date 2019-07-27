/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import administracija.controller.Ispis_pdfController;
import administracija.model.Artikal;
import administracija.model.Racun;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

/**
 *
 * @author armin
 */
public class PDF_service {
    
    static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    static String danasnji_datum = dateFormat.format(new Date());
    static DecimalFormat df = new DecimalFormat("#.##");
    static Calendar cal = Calendar.getInstance();
    static SimpleDateFormat stf = new SimpleDateFormat("HH.mm");
    static String trenutno_vrijeme = stf.format(cal.getTime());
    //private static byte[] img;
    
    public static PDF_service pdf_service = new PDF_service();

    public static void svi_artikli_PDF() {
        String naziv_dokumenta = "" + System.getProperty("user.home") + "\\Desktop\\" + "" + danasnji_datum + "_" + trenutno_vrijeme + ".pdf";

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
                byte[] img = artikal.getSlika();
                String s = new String(img);
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] slika_artikla = decoder.decodeBuffer(s);
                ByteArrayInputStream in = new ByteArrayInputStream(slika_artikla);
                BufferedImage slika;

                slika = ImageIO.read(in);

                WritableImage image = SwingFXUtils.toFXImage(slika, null);
                BufferedImage imag = ImageIO.read(in);

                table.addCell(artikal.getNaziv());
                table.addCell(String.valueOf(artikal.getCijena()) + " KM");
                table.addCell(artikal.getVrsta());
                table.addCell(artikal.getGarancija());
                try {
                    table.addCell(Image.getInstance(slika_artikla));
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stvaranje dokumenta završeno je uspješno");
        alert.setHeaderText("Dokument je uspješno zapisan na pozadinu");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
    }

    
    public static void prodani_artikli_PDF() throws DocumentException, IOException {
        String font ="C:\\Users\\armin\\Documents\\NetBeansProjects\\Administracija\\src\\libs\\OpenSans-Regular.ttf";
        BaseFont bf = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        
        String naziv_dokumenta = "" + System.getProperty("user.home") + "\\Desktop\\" + "Prodano" + danasnji_datum + "_" + trenutno_vrijeme + ".pdf";

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
//new Font(bf, 22)
        PdfPTable table = new PdfPTable(5);
        PdfPCell c1 = new PdfPCell(new Phrase("Naziv", new Font(bf, 14)));
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Količina", new Font(bf, 14)));
        table.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase("Ukupna cijena", new Font(bf, 14)));
        table.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("Ime i prezime", new Font(bf, 14)));
        table.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase("Slika"));
        table.addCell(c5);
        table.setHeaderRows(1);

        ObservableList<Racun> racuni = (ObservableList<Racun>) RacunService.racunService.dohvatiSveNarudzbe();
        for (Racun racun : racuni) {

            try {
                byte[] img = racun.getSlika();
                String s = new String(img);
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] slika_artikla = decoder.decodeBuffer(s);
                ByteArrayInputStream in = new ByteArrayInputStream(slika_artikla);
                BufferedImage slika;

                slika = ImageIO.read(in);

                WritableImage image = SwingFXUtils.toFXImage(slika, null);
                BufferedImage imag = ImageIO.read(in);

                //table.addCell(racun.getNaziv());
                PdfPCell p1 = new PdfPCell(new Phrase(racun.getNaziv(), new Font(bf, 14)));
                table.addCell(p1);
                PdfPCell p2 = new PdfPCell(new Phrase(String.valueOf(racun.getKolicina()), new Font(bf, 14)));
                table.addCell(p2);
                PdfPCell p3 = new PdfPCell(new Phrase(String.valueOf(racun.getUkupna_cijena()) + " KM", new Font(bf, 14)));
                table.addCell(p3);
                PdfPCell p4 = new PdfPCell(new Phrase(racun.getIme() +" "+racun.getPrezime(), new Font(bf, 14)));
                table.addCell(p4);
                
                //table.addCell(String.valueOf(racun.getUkupna_cijena()) + " KM");
               // table.addCell(racun.getIme() +" "+racun.getPrezime());
                try {
                    table.addCell(Image.getInstance(slika_artikla));
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stvaranje dokumenta završeno je uspješno");
        alert.setHeaderText("Dokument je uspješno zapisan na pozadinu");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
    }
}
