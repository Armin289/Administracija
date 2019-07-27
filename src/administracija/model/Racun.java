/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija.model;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

/**
 *
 * @author armin
 */
public class Racun {
    public int id;
    public int korisnik_id;
    public int artikal_id;
    public int kolicina;
    public String placanje;
    public String datum;
    public String vrijeme;
    public String status;
    public byte [] slika;
    public String slika_artikla;
    
    
    public Float ukupna_cijena;
    public String naziv;
    public String ime;
    public String prezime;
    public String ulica;
    public String grad;
    public String postanski_broj;

    public Racun() {
    }
    public Racun(String naziv, int kolicina, float ukupna_cijena, String datum){
        this.naziv = naziv;
        this.kolicina = kolicina;
        this.ukupna_cijena = ukupna_cijena;
        this.datum = datum;
    }
    
    public Racun(int id,int artikal_id, int korisnik_id, String naziv, int kolicina, Float ukupna_cijena, String ime, String prezime, String placanje, String ulica, String grad, String postanski_broj, String datum, String vrijeme){
        this.id = id;
        this.naziv = naziv;
        this.artikal_id = artikal_id;
        this.korisnik_id = korisnik_id;
        this.kolicina = kolicina;
        this.ukupna_cijena = ukupna_cijena;
        this.ime = ime;
        this.prezime = prezime;
        this.placanje = placanje;
        this.ulica = ulica;
        this.grad = grad;
        this.postanski_broj = postanski_broj;
        this.datum = datum;
        this.vrijeme = vrijeme;
        
    }
     public Racun(int id,int artikal_id, int korisnik_id, String naziv, int kolicina, Float ukupna_cijena, String ime, String prezime, String placanje, String ulica, String grad, String postanski_broj, String datum, String vrijeme, byte [] slika){
        this.id = id;
        this.naziv = naziv;
        this.artikal_id = artikal_id;
        this.korisnik_id = korisnik_id;
        this.kolicina = kolicina;
        this.ukupna_cijena = ukupna_cijena;
        this.ime = ime;
        this.prezime = prezime;
        this.placanje = placanje;
        this.ulica = ulica;
        this.grad = grad;
        this.postanski_broj = postanski_broj;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.slika = slika;
        
    }
    
    
    public Racun(int id, int korisnik_id, int artikal_id, int kolicina, String placanje, String datum, String vrijeme, String status) {
        this.id = id;
        this.korisnik_id = korisnik_id;
        this.artikal_id = artikal_id;
        this.kolicina = kolicina;
        this.placanje = placanje;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(int korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    public int getArtikal_id() {
        return artikal_id;
    }

    public void setArtikal_id(int artikal_id) {
        this.artikal_id = artikal_id;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String getPlacanje() {
        return placanje;
    }

    public void setPlacanje(String placanje) {
        this.placanje = placanje;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
//
    public Float getUkupna_cijena() {
        return ukupna_cijena;
    }

    public void setUkupna_cijena(Float ukupna_cijena) {
        this.ukupna_cijena = ukupna_cijena;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getPostanski_broj() {
        return postanski_broj;
    }

    public void setPostanski_broj(String postanski_broj) {
        this.postanski_broj = postanski_broj;
    }

    public byte[] getSlika() {
        return slika;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    public String getSlika_artikla() {
        return slika_artikla;
    }

    public void setSlika_artikla(String slika_artikla) {
        this.slika_artikla = slika_artikla;
    }
    
    
    
    
    
    
}
