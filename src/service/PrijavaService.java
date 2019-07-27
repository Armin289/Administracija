/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import administracija.model.Administrator;
import administracija.model.Baza;
import administracija.model.Korisnik;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static service.AdministratorService.administratorService;

/**
 *
 * @author armin
 */
public class PrijavaService {
    private static KorisnikService korisnikService = new KorisnikService();
    static Administrator prijavljeni = null;
    
    public static boolean prijava (String korisnicko_ime, String lozinka) {
        try {
            PreparedStatement upit = Baza.DB.prepare(
                    "SELECT * FROM administrator WHERE korisnicko_ime=? AND lozinka=MD5(?) " );
            upit.setString(1, korisnicko_ime);
            upit.setString(2, lozinka);
            ResultSet rs = upit.executeQuery();
            if (rs.next()) {
                PrijavaService.prijavljeni = administratorService.izBazePremaId(rs.getInt(1));
                return true;
                
            } else {
                return false;
            }
        } catch (SQLException ex) {
           System.out.println("Greska prilikom prijave: " + ex.getMessage());
           return false;
        }
    }
    
    
    public static Administrator logiraniKorisnik () {
        return PrijavaService.prijavljeni;
    }
}


