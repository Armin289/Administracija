/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracija.model;

import sun.misc.OSEnvironment;

/**
 *
 * @author armin
 */
public class Artikal {
    public int id;
    public String naziv;
    public Double cijena;
    public String garancija;
    public String opis;
    public String interna_memorija;
    public String kapacitet_baterije;
    public String operacijski_sustav;
    public String glavna_kamera;
    public String prednja_kamera;
    public String ekran;
    public String cpu;
    public String ram;
    public String link;
    public String proizvodjac;
    public byte [] slika;
    public String vrsta;
    public String tip;
    public int login_id;
    
    public String slika_string;

    public Artikal() {
    }

    
    public Artikal(int id, String naziv, Double cijena, String garancija, String opis, String interna_memorija,
            String kapacitet_baterije, String operacijski_sustav, String glavna_kamera,String ekran, String prednja_kamera,
            String cpu, String ram, byte [] slika, String link, String proizvodjac,  String vrsta, String tip, int login_id) {
        this.id = id;
        this.naziv = naziv;
        this.cijena = cijena;
        this.garancija = garancija;
        this.opis = opis;
        this.interna_memorija = interna_memorija;
        this.kapacitet_baterije = kapacitet_baterije;
        this.operacijski_sustav = operacijski_sustav;
        this.glavna_kamera = glavna_kamera;
        this.prednja_kamera = prednja_kamera;
        this.ekran = ekran;
        this.cpu = cpu;
        this.ram = ram;
        this.link = link;
        this.proizvodjac = proizvodjac;
        this.slika = slika;
        this.vrsta = vrsta;
        this.tip = tip;
        this.login_id = login_id;
    }
    
    public Artikal(int id, String naziv, Double cijena, String garancija, String opis, String interna_memorija,
            String kapacitet_baterije, String operacijski_sustav, String glavna_kamera,String ekran, String prednja_kamera,
            String cpu, String ram, String slika_String, String link, String proizvodjac,  String vrsta, String tip, int login_id) {
        this.id = id;
        this.naziv = naziv;
        this.cijena = cijena;
        this.garancija = garancija;
        this.opis = opis;
        this.interna_memorija = interna_memorija;
        this.kapacitet_baterije = kapacitet_baterije;
        this.operacijski_sustav = operacijski_sustav;
        this.glavna_kamera = glavna_kamera;
        this.prednja_kamera = prednja_kamera;
        this.ekran = ekran;
        this.cpu = cpu;
        this.ram = ram;
        this.link = link;
        this.proizvodjac = proizvodjac;
        this.slika_string = slika_String;
        this.vrsta = vrsta;
        this.tip = tip;
        this.login_id = login_id;
    }
    
    public Artikal(int id, String naziv, Double cijena, String garancija, String operacijski_sustav, String proizvodjac){
        this.id = id;
        this.naziv = naziv;
        this.cijena = cijena;
        this.garancija = garancija;
        this.operacijski_sustav = operacijski_sustav;
        this.proizvodjac = proizvodjac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getCijena() {
        return cijena;
    }

    public void setCijena(Double cijena) {
        this.cijena = cijena;
    }

    public String getGarancija() {
        return garancija;
    }

    public void setGarancija(String garancija) {
        this.garancija = garancija;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getInterna_memorija() {
        return interna_memorija;
    }

    public void setInterna_memorija(String interna_memorija) {
        this.interna_memorija = interna_memorija;
    }

    public String getKapacitet_baterije() {
        return kapacitet_baterije;
    }

    public void setKapacitet_baterije(String kapacitet_baterije) {
        this.kapacitet_baterije = kapacitet_baterije;
    }

    public String getOperacijski_sustav() {
        return operacijski_sustav;
    }

    public void setOperacijski_sustav(String operacijski_sustav) {
        this.operacijski_sustav = operacijski_sustav;
    }

    public String getGlavna_kamera() {
        return glavna_kamera;
    }

    public void setGlavna_kamera(String glavna_kamera) {
        this.glavna_kamera = glavna_kamera;
    }

    public String getPrednja_kamera() {
        return prednja_kamera;
    }

    public void setPrednja_kamera(String prednja_kamera) {
        this.prednja_kamera = prednja_kamera;
    }

    public String getEkran() {
        return ekran;
    }

    public void setEkran(String ekran) {
        this.ekran = ekran;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public byte[] getSlika() {
        return slika;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getLogin_id() {
        return login_id;
    }

    public void setLogin_id(int login_id) {
        this.login_id = login_id;
    }

    public String getSlika_string() {
        return slika_string;
    }

    public void setSlika_string(String slika_string) {
        this.slika_string = slika_string;
    }

    

    
    
    
}
