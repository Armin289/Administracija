/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija.model;

/**
 *
 * @author armin
 */
public class Administrator {
     public int id;
    public String ime;
    public String prezime;
    public String email;
    public String korisnicko_ime;
    public String lozinka;

    public Administrator() {
    }

    public Administrator(int id, String ime, String prezime, String email, String korisnicko_ime, String lozinka) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
    }
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    
    
}
