/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import administracija.model.Administrator;
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
 *
 * @author armin
 */
public class AdministratorService implements model <Administrator> {
    public static final AdministratorService administratorService = new AdministratorService();

    @Override
    public Administrator spasi(Administrator administrator) {
     try {
            PreparedStatement upit = DB.prepare ("INSERT INTO administrator VALUES(null, ?, ?, ?, ?, MD5(?))");
            upit.setString(1, administrator.getIme());
            upit.setString(2, administrator.getPrezime());
            upit.setString(3, administrator.getEmail());
            upit.setString(4, administrator.getKorisnicko_ime());
            upit.setString(5, administrator.getLozinka());
            upit.executeUpdate();
            /* Dohvati id korisnika iz baze podataka */
            ResultSet rs = upit.getGeneratedKeys();
            if (rs.next()){
                /* Postavi id korisnika iz baze podataka objektu korisnik */
                administrator.setId(rs.getInt(1));
            }
            return administrator;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        } 
    
    }

    @Override
    public Administrator uredi(Administrator administrator) {
        try {
            PreparedStatement upit = DB.prepare ("UPDATE administrator SET ime=?, prezime=?, email=?, korisnicko_ime=?, lozinka=? WHERE id=?");
            upit.setString(1, administrator.getIme());
            upit.setString(2, administrator.getPrezime());
            upit.setString(3, administrator.getEmail());
            upit.setString(4, administrator.getKorisnicko_ime());
            upit.setString(5, administrator.getLozinka());
            upit.setInt(6, administrator.getId());
            upit.executeUpdate();
            return administrator;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    public Administrator urediLozinku(Administrator administrator) {
        try {
            PreparedStatement upit = DB.prepare ("UPDATE administrator SET  lozinka=MD5(?) WHERE id=?");
            upit.setString(1, administrator.getLozinka());
            upit.setInt(2, administrator.getId());
            upit.executeUpdate();
            return administrator;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean brisi(Administrator administrator) {
        try {
            PreparedStatement upit = DB.prepare ("DELETE FROM administrator WHERE id=?");
            upit.setInt(1, administrator.getId());
            upit.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean brisi_saId(int id) {
        try {
            PreparedStatement upit = DB.prepare("DELETE FROM administrator WHERE id=?");
            upit.setInt(1, id);
            upit.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Administrator> sveIzBaze() {
         try {
            ObservableList <Administrator> administratori = FXCollections.observableArrayList();
        ResultSet rs = DB.select("SELECT * FROM administrator");
            while (rs.next()){
                administratori.add(new Administrator(
                        rs.getInt(1), 
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                            
                ));
            }
            return administratori;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public Administrator izBazePremaId(int id) {
        try {
            PreparedStatement upit = DB.prepare ("SELECT * FROM administrator WHERE id=?");
            upit.setInt(1, id);
            ResultSet rs = upit.executeQuery();
            if (rs.next()){
                return new Administrator(
                         rs.getInt(1), 
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    
    }
    
    public ObservableList<Administrator> pretragaIzBaze(String pojam) {
        try {
            ObservableList<Administrator> administratori = FXCollections.observableArrayList();
            ResultSet rs = DB.select("SELECT * FROM administrator WHERE ime LIKE '%" + pojam + "%' OR prezime LIKE '%" + pojam + "%' OR email LIKE '%" + pojam + "%'  OR korisnicko_ime LIKE '%" + pojam + "%' ");
            while (rs.next()) {
                administratori.add(new Administrator(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                       
                ));
            }
            return administratori;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    
    //provjeri ima li email u bazi
    public static int postoji_li_email(String email) {
        try {
            PreparedStatement upit = DB.prepare ("SELECT count(*) FROM administrator WHERE email='"+email+"'");
           
            
            ResultSet rs = upit.executeQuery();
            if (rs.next()){
                return  rs.getInt(1);
                    
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;
    }
    
    //provjeri ima li email u bazi
    public static int postoji_li_kime(String korisnicko_ime) {
        try {
            PreparedStatement upit = DB.prepare ("SELECT count(*) FROM administrator WHERE korisnicko_ime='"+korisnicko_ime+"'");
           
            
            ResultSet rs = upit.executeQuery();
            if (rs.next()){
                return  rs.getInt(1);
                    
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;
    }
}
