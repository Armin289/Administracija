/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author armin
 */
public class Baza extends Konekcija {
 
    public static final Baza DB = new Baza();
    private Statement iskaz;
    
    public Baza() {
        super ();
    }

    public Baza(String host, String korisnik, String lozinka, String baza) {
        super(host, korisnik, lozinka, baza);
    }
    
    public ResultSet select (String sql) {
        try {
            this.iskaz = this.konekcija.createStatement();
            return this.iskaz.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Nisam izvrsio select iskaz, zbog greske.");
            return null;
        }
    }
    
    public PreparedStatement prepare (String sql) {
        try {
            return this.konekcija.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom izvršavanja upita");
        }
        return null;
    }
    
}
