/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author ziggy
 */
//SUPERKLASSE
public abstract class Kaart { 
    //declaratie attributen
    private String naam;
    private boolean kaartInGebruik;
    //constructor kaart (superklasse)
    public Kaart(String naam) {
        setNaam(naam);
    }
    //setter
    public final void setNaam(String naam) {
        this.naam = naam;
    }
    
    public String getNaam() {
        return naam;
    }
    //boolean die waarde true geeft als kaart in gebruik is
    public boolean isKaartInGebruik() {
        return kaartInGebruik;
    }
    //setter die gebruikt wordt om kaartInGebruik op true of false te setten
    public final void setKaartInGebruik(boolean kaartInGebruik) {
        this.kaartInGebruik = kaartInGebruik;
    }
}
