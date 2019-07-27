/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

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
public class KorisnikService implements model<Korisnik> {

    public static final KorisnikService korisnikService = new KorisnikService();

    @Override
    public Korisnik spasi(Korisnik korisnik) {
        try {
            PreparedStatement upit = DB.prepare("INSERT INTO korisnici VALUES(null, ?, ?, ?, md5(?), ?, ?, ?, ?)");
            upit.setString(1, korisnik.getIme());
            upit.setString(2, korisnik.getPrezime());
            upit.setString(3, korisnik.getEmail());
            upit.setString(4, korisnik.getLozinka());
            upit.setString(5, korisnik.getAdresa());
            upit.setString(6, korisnik.getGrad());
            upit.setInt(7, korisnik.getPostanskibroj());
            upit.setString(8, korisnik.getTelefon());
            upit.executeUpdate();
            /* Dohvati id korisnika iz baze podataka */
            ResultSet rs = upit.getGeneratedKeys();
            if (rs.next()) {
                /* Postavi id korisnika iz baze podataka objektu korisnik */
                korisnik.setId(rs.getInt(1));
            }
            return korisnik;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }

    }

    @Override
    public Korisnik uredi(Korisnik korisnik) {
        try {
            PreparedStatement upit = DB.prepare("UPDATE korisnici SET ime=?, prezime=?, email=?, lozinka=?, adresa=?, grad=?, postanskibroj=?, telefon=? WHERE id=?");
            upit.setString(1, korisnik.getIme());
            upit.setString(2, korisnik.getPrezime());
            upit.setString(3, korisnik.getEmail());
            upit.setString(4, korisnik.getLozinka());
            upit.setString(5, korisnik.getAdresa());
            upit.setString(6, korisnik.getGrad());
            upit.setInt(7, korisnik.getPostanskibroj());
            upit.setString(8, korisnik.getTelefon());
            upit.setInt(9, korisnik.getId());
            upit.executeUpdate();
            return korisnik;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    public Korisnik uredi_lozinku(Korisnik korisnik) {
        try {
            PreparedStatement upit = DB.prepare("UPDATE korisnici SET ime=?, prezime=?, email=?, lozinka=MD5(?), adresa=?, grad=?, postanskibroj=?, telefon=? WHERE id=?");
            upit.setString(1, korisnik.getIme());
            upit.setString(2, korisnik.getPrezime());
            upit.setString(3, korisnik.getEmail());
            upit.setString(4, korisnik.getLozinka());
            upit.setString(5, korisnik.getAdresa());
            upit.setString(6, korisnik.getGrad());
            upit.setInt(7, korisnik.getPostanskibroj());
            upit.setString(8, korisnik.getTelefon());
            upit.setInt(9, korisnik.getId());
            upit.executeUpdate();
            return korisnik;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean brisi(Korisnik korisnik) {
        try {
            PreparedStatement upit = DB.prepare("DELETE FROM korisnici WHERE id=?");
            upit.setInt(1, korisnik.getId());
            upit.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return false;
        }

    }

    public boolean brisi_saId(int id) {
        try {
            PreparedStatement upit = DB.prepare("DELETE FROM korisnici WHERE id=?");
            upit.setInt(1, id);
            upit.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public ObservableList<Korisnik> sveIzBaze() {
        try {
            ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
            ResultSet rs = DB.select("SELECT * FROM korisnici");
            while (rs.next()) {
                korisnici.add(new Korisnik(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9)
                ));
            }
            return korisnici;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public Korisnik izBazePremaId(int id) {
        try {
            PreparedStatement upit = DB.prepare("SELECT * FROM korisnici WHERE id=?");
            upit.setInt(1, id);
            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return new Korisnik(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9)
                );
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    public ObservableList<Korisnik> pretragaIzBaze(String pojam) {
        try {
            ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
            ResultSet rs = DB.select("SELECT * FROM korisnici WHERE ime LIKE '%" + pojam + "%' OR prezime LIKE '%" + pojam + "%' OR email LIKE '%" + pojam + "%'  ");
            while (rs.next()) {
                korisnici.add(new Korisnik(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9)
                ));
            }
            return korisnici;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    public static int ukupno_korisnika() {
        try {
            PreparedStatement upit = DB.prepare("SELECT COUNT(id)FROM korisnici");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;

    }
}
