/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import administracija.model.Artikal;
import static administracija.model.Baza.DB;
import administracija.model.Korisnik;
import interfaces.model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**     
 * @author armin
 */
public class ArtikalService implements model <Artikal> {
    public static final ArtikalService artikalService = new ArtikalService();

    @Override
    public Artikal spasi(Artikal artikal) {
        try {
            PreparedStatement upit = DB.prepare ("INSERT INTO artikal VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)");
            upit.setString(1, artikal.getNaziv());
            upit.setDouble(2, artikal.getCijena());
            upit.setString(3, artikal.getGarancija());
            upit.setString(4, artikal.getOpis());
            upit.setString(5, artikal.getInterna_memorija());
            upit.setString(6, artikal.getKapacitet_baterije());
            upit.setString(7, artikal.getOperacijski_sustav());
            upit.setString(8, artikal.getGlavna_kamera());
            upit.setString(9, artikal.getEkran());
            upit.setString(10, artikal.getPrednja_kamera());
            upit.setString(11, artikal.getCpu());
            upit.setString(12, artikal.getRam());
            upit.setString(13, artikal.getSlika_string());
            upit.setString(14, artikal.getLink());
            upit.setString(15, artikal.getProizvodjac());
            upit.setString(16, artikal.getVrsta());
            upit.setString(17, artikal.getTip());
            upit.setInt(18, artikal.getLogin_id());
            upit.executeUpdate();
            /* Dohvati id hrane iz baze podataka */
            ResultSet rs = upit.getGeneratedKeys();
            if (rs.next()){
                /* Postavi id hrane iz baze podataka objektu hrana */
                artikal.setId(rs.getInt(1));
            }
            return artikal;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }    
      
    }

    @Override
    public Artikal uredi(Artikal artikal) {
     try {
            PreparedStatement upit = DB.prepare ("UPDATE artikal SET naziv=?, cijena=?, garancija=?, opis=?, imemorija=?, baterija=?, OS=?, gkamera=?, ekran=?, pkamera=?, procesor=?, ram=?, slike=?, link=?, stranica=?, vrsta=?, tip=?, login_id=? WHERE id=?");
            upit.setString(1, artikal.getNaziv());
            upit.setDouble(2, artikal.getCijena());
            upit.setString(3, artikal.getGarancija());
            upit.setString(4, artikal.getOpis());
            upit.setString(5, artikal.getInterna_memorija());
            upit.setString(6, artikal.getKapacitet_baterije());
            upit.setString(7, artikal.getOperacijski_sustav());
            upit.setString(8, artikal.getGlavna_kamera());
            upit.setString(9, artikal.getEkran());
            upit.setString(10, artikal.getPrednja_kamera());
            upit.setString(11, artikal.getCpu());
            upit.setString(12, artikal.getRam());
            upit.setString(13, artikal.getSlika_string());
            upit.setString(14, artikal.getLink());
            upit.setString(15, artikal.getProizvodjac());
            upit.setString(16, artikal.getVrsta());
            upit.setString(17, artikal.getTip());
            upit.setInt(18, artikal.getLogin_id());
            upit.setInt(19, artikal.getId());
            upit.executeUpdate();
            return artikal;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }    
    }

    @Override
    public boolean brisi(Artikal artikal) {
        try {
            PreparedStatement upit = DB.prepare ("DELETE FROM artikal WHERE id=?");
            upit.setInt(1, artikal.getId());
            upit.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return false;
        }

    }
    public boolean brisi_saId(int id){
        try {
            PreparedStatement upit = DB.prepare ("DELETE FROM artikal WHERE id=?");
            upit.setInt(1, id);
            upit.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return false;
        }
    }
    @Override
    public ObservableList<Artikal> sveIzBaze() {
    try {
            ObservableList <Artikal> artikli = FXCollections.observableArrayList();
        ResultSet rs = DB.select("SELECT * FROM artikal ORDER BY id DESC");
            while (rs.next()){
                artikli.add(new Artikal (
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10), 
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getBytes(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getInt(19)
                        
                ));
            }
            return artikli;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }    
    
    
    public ObservableList<Artikal> pretragaIzBaze(String pojam, String tip) {
        try {
            ObservableList <Artikal> artikli = FXCollections.observableArrayList();
        ResultSet rs = DB.select("SELECT  * FROM artikal WHERE (naziv LIKE '%"+pojam+"%' || pkamera LIKE '%"+pojam+"%' || gkamera LIKE '%"+pojam+"%' || cijena LIKE '%"+pojam+"%' || ram LIKE '%"+pojam+"%' || stranica LIKE '%"+pojam+"%') && vrsta='"+tip+"'" );
            while (rs.next()){
                artikli.add(new Artikal(
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10), 
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getBytes(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getInt(19)
                ));
            }
            return artikli;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    public ObservableList<Artikal> pretragaIzBazeArtikli(String pojam) {
        try {
            ObservableList <Artikal> artikli = FXCollections.observableArrayList();
        ResultSet rs = DB.select("SELECT  * FROM artikal WHERE naziv LIKE '%"+pojam+"%' || cijena LIKE '%"+pojam+"%' " );
            while (rs.next()){
                artikli.add(new Artikal(
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10), 
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getBytes(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getInt(19)
                ));
            }
            return artikli;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    
    
    public ObservableList<Artikal> sveIzBazeSmartphone() {
    try {
            ObservableList <Artikal> artikli = FXCollections.observableArrayList();
        ResultSet rs = DB.select("SELECT * FROM artikal WHERE vrsta='smartphone' ORDER BY id DESC");
            while (rs.next()){
                artikli.add(new Artikal (
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10), 
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getBytes(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getInt(19)
                        
                ));
            }
            return artikli;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    public ObservableList<Artikal> sveIzBazeTablet() {
    try {
            ObservableList <Artikal> artikli = FXCollections.observableArrayList();
        ResultSet rs = DB.select("SELECT * FROM artikal WHERE vrsta='tablet' ORDER BY id DESC");
            while (rs.next()){
                artikli.add(new Artikal (
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10), 
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getBytes(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getInt(19)
                        
                ));
            }
            return artikli;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    public ObservableList<Artikal> sveIzBazeOprema() {
    try {
            ObservableList <Artikal> artikli = FXCollections.observableArrayList();
        ResultSet rs = DB.select("SELECT * FROM artikal WHERE vrsta='oprema' ORDER BY id DESC");
            while (rs.next()){
                artikli.add(new Artikal (
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10), 
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getBytes(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getInt(19)
                        
                ));
            }
            return artikli;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public Artikal izBazePremaId(int id) {

        try {
            PreparedStatement upit = DB.prepare ("SELECT * FROM artikal WHERE id=?");
            upit.setInt(1, id);
            ResultSet rs = upit.executeQuery();
            if (rs.next()){
                return new Artikal(
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6), 
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10), 
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getBytes(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getInt(19)
                );
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }   
    
    public ObservableList<Artikal> prodaniArtikli() {
        try {
            ObservableList<Artikal> artikli = FXCollections.observableArrayList();
            ResultSet rs = DB.select("SELECT a.id, a.naziv, a.cijena, a.garancija, a.OS, a.stranica  FROM artikal a, zaglavlje_racuna zr, racun_stavke rs WHERE rs.artikal_id = a.id AND zr.id = rs.zaglavlje_id");
            while (rs.next()) {
                artikli.add(new Artikal(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                        
                ));
            }
            return artikli;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    
    public ObservableList<Artikal> prodaniArtikliPretraga(String pojam) {
        try {
            ObservableList<Artikal> artikli = FXCollections.observableArrayList();
            ResultSet rs = DB.select("SELECT a.id, a.naziv, a.cijena, a.garancija, a.OS, a.stranica  FROM artikal a, zaglavlje_racuna zr, racun_stavke rs WHERE (naziv LIKE '%"+pojam+"%' || cijena LIKE '%"+pojam+"%' || stranica LIKE '%"+pojam+"%') AND rs.artikal_id = a.id AND zr.id = rs.zaglavlje_id");
            while (rs.next()) {
                artikli.add(new Artikal(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                        
                ));
            }
            return artikli;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    
}
