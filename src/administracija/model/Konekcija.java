/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author armin
 */
public class Konekcija {

    private String host;
    private String korisnik;
    private String lozinka;
    private String baza;

    protected Connection konekcija;

    public Konekcija(String host, String korisnik, String lozinka, String baza) {
        this.host = host;
        this.korisnik = korisnik;
        this.lozinka = lozinka;
        this.baza = baza;
        this.spoji();
    }

    public Konekcija() {
        /* this.host = "mysql7.000webhost.com";
        this.korisnik = "id1383114_root";
        this.lozinka = "Hobotnica289";
        this.baza = "id1383114_login";*/
        this.host = "localhost";
        this.korisnik = "root";
        this.lozinka = "";
        this.baza = "online_mobile";
        this.spoji();
    }

    public void spoji() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                Properties connectionProperties = new Properties();
//useUnicode=true&
                connectionProperties.put("charSet", "UTF8");
                connectionProperties.put("encoding", "UTF8");
                //this.konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?characterEncoding=utf-8","root", "");
                // this.konekcija = DriverManager.getConnection("jdbc:mysql://sql169.main-hosting.eu.:3306/u274545503_mobi","u274545503_mobi", "Hobotnica289");
                this.konekcija = DriverManager.getConnection("jdbc:mysql://" + host + "/" + baza + "?useUnicode=true&"
                        + "user=" + korisnik + "&password=" + lozinka + "&characterEncoding=utf8");
                Statement s = this.konekcija.createStatement();
                s.execute("SET NAMES UTF8");
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Greška");
                alert.setHeaderText("Pokrenite bazu !");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                    }
                });
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Klasu za povezivanje nije pronađena.");
        }
    }

    public void odspoji() {
        try {
            this.konekcija.close();
        } catch (SQLException ex) {
            System.out.println("Odspajanje nije uspjelo.");
        }
    }
}
