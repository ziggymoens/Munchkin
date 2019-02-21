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
public abstract class Kaart {

    private String naam;
    private boolean kaartInGebruik;

    public Kaart(String naam) {
        setNaam(naam);
    }

    public final void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public boolean isKaartInGebruik() {
        return kaartInGebruik;
    }

    public final void setKaartInGebruik(boolean kaartInGebruik) {
        this.kaartInGebruik = kaartInGebruik;
    }
}
