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
public class Korisnik {
    public int id;
    public String ime;
    public String prezime;
    public String email;
    public String lozinka;
    public String adresa;
    public String grad;
    public int postanskibroj;
    public String telefon;

    public Korisnik() {
    }

    public Korisnik(int id, String ime, String prezime, String email, String lozinka, String adresa, String grad, int postanskibroj, String telefon) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.adresa = adresa;
        this.grad = grad;
        this.postanskibroj = postanskibroj;
        this.telefon = telefon;
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

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public int getPostanskibroj() {
        return postanskibroj;
    }

    public void setPostanskibroj(int postanskibroj) {
        this.postanskibroj = postanskibroj;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    
    
}
