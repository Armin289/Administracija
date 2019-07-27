/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static administracija.model.Baza.DB;
import administracija.model.Racun;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author armin
 */
public class RacunService {

    public static final RacunService racunService = new RacunService();

    public ObservableList<Racun> dohvatiNepotvrdeneNarudzbe() {
        try {
            ObservableList<Racun> racuni = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT zr.id, rs.artikal_id, zr.kupac_id, a.naziv, rs.kolicina, rs.kolicina*a.cijena, o.ime, o.prezime, zr.placanje, o.adresa, o.grad, o.postanskibroj, zr.datum, zr.vrijeme FROM racun_stavke rs, zaglavlje_racuna zr, korisnici o, artikal a  WHERE rs.status='Na čekanju' AND o.id=zr.kupac_id AND rs.artikal_id=a.id AND zr.id=rs.zaglavlje_id");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                racuni.add(new Racun(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getFloat(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14)
                ));
            }
            return racuni;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    
    public ObservableList<Racun> dohvatiSveNarudzbe() {
        try {
            ObservableList<Racun> racuni = FXCollections.observableArrayList();
            PreparedStatement upit = DB.prepare("SELECT zr.id, rs.artikal_id, zr.kupac_id, a.naziv, rs.kolicina, rs.kolicina*a.cijena, o.ime, o.prezime, zr.placanje, o.adresa, o.grad, o.postanskibroj, zr.datum, zr.vrijeme, a.slike FROM racun_stavke rs, zaglavlje_racuna zr, korisnici o, artikal a  WHERE o.id=zr.kupac_id AND rs.artikal_id=a.id AND zr.id=rs.zaglavlje_id");

            ResultSet rs = upit.executeQuery();
            while (rs.next()) {
                racuni.add(new Racun(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getFloat(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getBytes(15)
                ));
            }
            return racuni;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    public Racun uredi(Racun racun) {
        try {
            PreparedStatement upit = DB.prepare("UPDATE zaglavlje_racuna zr, racun_stavke rs SET zr.kupac_id=?, rs.artikal_id=?, rs.kolicina=?, zr.placanje=?, zr.datum=?, zr.vrijeme=?, rs.status=? WHERE rs.zaglavlje_id=? AND zr.id");
            upit.setInt(1, racun.getKorisnik_id());
            upit.setInt(2, racun.getArtikal_id());
            upit.setInt(3, racun.getKolicina());
            upit.setString(4, racun.getPlacanje());
            upit.setString(5, racun.getDatum());
            upit.setString(6, racun.getVrijeme());
            upit.setString(7, racun.getStatus());
            upit.setInt(8, racun.getId());
            upit.executeUpdate();
            return racun;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }
    
    public Racun prihvati(Racun racun) {
        try {
            PreparedStatement upit = DB.prepare("UPDATE zaglavlje_racuna zr, racun_stavke rs SET  rs.kolicina=?, zr.placanje=?, rs.status=? WHERE zr.id = rs.zaglavlje_id AND rs.zaglavlje_id=? AND rs.artikal_id = ? ");
            upit.setInt(1, racun.getKolicina());
            upit.setString(2, racun.getPlacanje());
            upit.setString(3, racun.getStatus());
            upit.setInt(4, racun.getId());
            upit.setInt(5, racun.getArtikal_id());
            upit.executeUpdate();
            return racun;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }

    public boolean brisi(Racun racun) {
        try {
            PreparedStatement upit = DB.prepare("DELETE rs.* FROM zaglavlje_racuna zr, racun_stavke rs WHERE zr.id = rs.zaglavlje_id AND rs.zaglavlje_id=? AND rs.artikal_id = ? ");
            upit.setInt(1, racun.getId());
            upit.setInt(2, racun.getArtikal_id());
            upit.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return false;
        }

    }
    
     public ObservableList<Racun> sveIzBazePremaKorisnikId(int id) {
    try {
            ObservableList<Racun> racuni = FXCollections.observableArrayList();
        ResultSet rs = DB.select("SELECT a.naziv, rs.kolicina, rs.kolicina*a.cijena, zr.datum FROM racun_stavke rs, zaglavlje_racuna zr, artikal a WHERE a.id=rs.artikal_id AND rs.zaglavlje_id = zr.id AND zr.kupac_id='"+id+"' ORDER BY zr.datum DESC");
            while (rs.next()){
                racuni.add( new Racun(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getFloat(3),
                        rs.getString(4)
                        
                ));
            }
            return racuni;
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
            return null;
        }
    }    

    public static float izracunaj_ukupnu_zaradu() {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(a.cijena*rs.kolicina)FROM racun_stavke rs, zaglavlje_racuna zr, artikal a WHERE a.id=rs.artikal_id AND rs.zaglavlje_id = zr.id");
            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;
    }

    public static float izracunaj_dnevnu_zaradu(String datum) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(a.cijena*rs.kolicina)FROM zaglavlje_racuna zr, racun_stavke rs, artikal a WHERE a.id=rs.artikal_id AND rs.zaglavlje_id=zr.id AND zr.datum = '"+ datum +"'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;

    }

    public static float izracunaj_dnevnu_zaradu_prihvaceno(String datum) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(rs.kolicina*a.cijena) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr, korisnici o WHERE rs.artikal_id = a.id AND zr.kupac_id = o.id AND rs.status='Prihvaćeno' AND zr.datum = '" + datum + "'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;

    }

    public static int broj_notifikacija() {
        try {
            PreparedStatement upit = DB.prepare("SELECT COUNT(id) FROM racun_stavke WHERE status='Na čekanju'");

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

    public static int prodano_danas(String datum) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(rs.kolicina) FROM racun_stavke rs, zaglavlje_racuna zr WHERE rs.zaglavlje_id = zr.id AND zr.datum = '" + datum + "'");

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

    public static int prodano_ukupno() {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(kolicina) FROM racun_stavke");

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

    public static float ukupna_zarada_na_artiklu(int id) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(rs.kolicina*a.cijena) FROM zaglavlje_racuna zr, racun_stavke rs, artikal a WHERE rs.artikal_id='" + id + "' AND a.id='" + id + "' AND rs.zaglavlje_id = zr.id");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;

    }

   

    public static int prodano_artikla(int id) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(kolicina) FROM racun_stavke WHERE artikal_id ='" + id + "'");

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

    public static int broj_kupovina_korisnika(int id) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(rs.kolicina) FROM racun_stavke rs, zaglavlje_racuna zr WHERE rs.zaglavlje_id=zr.id AND zr.kupac_id ='" + id + "'");

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

    public static float zarada_od_korisnika(int id) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(a.cijena * rs.kolicina) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id = zr.id AND zr.kupac_id='" + id + "'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;

    }

    public static String datum_zadnje_kupovine(int id) {
        try {
            PreparedStatement upit = DB.prepare("SELECT MAX(zr.datum) FROM zaglavlje_racuna zr, artikal a WHERE zr.kupac_id ='" + id + "'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getString(1);

            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return null;

    }

    public static String datum_zadnje_kupovine_artikla(int id) {
        try {
            PreparedStatement upit = DB.prepare("SELECT MAX(zr.datum) FROM zaglavlje_racuna zr, racun_stavke rs, artikal a WHERE rs.artikal_id ='" + id + "'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getString(1);

            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return null;

    }

    // ukupan broj artikala
    public static String ukupno_artikala() {
        try {
            PreparedStatement upit = DB.prepare("SELECT COUNT(*) FROM artikal");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getString(1);

            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return null;

    }

    //ukupan broj smartphone-a
    public static String ukupno_smartphone() {
        try {
            PreparedStatement upit = DB.prepare("SELECT COUNT(*) FROM artikal WHERE vrsta='smartphone'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getString(1);

            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return null;

    }

    //ukupan broj tablet-a
    public static String ukupno_tablet() {
        try {
            PreparedStatement upit = DB.prepare("SELECT COUNT(*) FROM artikal WHERE vrsta='tablet'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getString(1);

            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return null;

    }

    //ukupan broj opreme
    public static String ukupno_oprema() {
        try {
            PreparedStatement upit = DB.prepare("SELECT COUNT(*) FROM artikal WHERE vrsta='oprema'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getString(1);

            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return null;

    }
    
    //  dnevna zarada na smartphone uređajima
    public static float zarada_na_smartphone_danas(String datum) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(rs.kolicina*a.cijena) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.datum='"+ datum +"' AND a.vrsta='Smartphone'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;
    }
    public static float zarada_na_tablet_danas(String datum) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(rs.kolicina*a.cijena) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.datum='"+ datum +"' AND a.vrsta='Tablet'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;
    }
    public static float zarada_na_oprema_danas(String datum) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(rs.kolicina*a.cijena) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.datum='"+ datum +"' AND a.vrsta='Oprema'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;

    }
    
    
            
            public static float izracunaj_mjesecnu_zaradu(String datum1, String datum2) {
        try {
            PreparedStatement upit = DB.prepare("SELECT SUM(rs.kolicina*a.cijena) FROM artikal a, racun_stavke rs, zaglavlje_racuna zr WHERE rs.artikal_id=a.id AND rs.zaglavlje_id=zr.id AND zr.datum>='"+datum1+"' AND zr.datum<='"+datum2+"'");

            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);

            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: " + ex.getMessage());
        }
        return 0;

    }

}
