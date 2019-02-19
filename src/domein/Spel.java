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
public class Spel {

    private int aantalSpelers;
    private int leeftijd;
    private String geslacht;
    private String naam;

    public Spel(int aantalSpelers) {
        setAantalSpelers(aantalSpelers);
    }

    public final void setAantalSpelers(int aantalSpelers) {
        this.aantalSpelers = aantalSpelers;
    }

    public int getAantalSpelers() {
        return aantalSpelers;
    }

    public final void setLeeftijd(int leeftijd) {
        if (leeftijd > 0) {
            this.leeftijd = leeftijd;
        } else {
            throw new IllegalArgumentException("Leeftijd moet positief zijn!");
        }
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public void setGeslacht(String geslacht) {
        if (geslacht.toLowerCase().equals("man") || geslacht.toLowerCase().equals("vrouw")) {
            this.geslacht = geslacht;
        } else {
            throw new IllegalArgumentException("Geef een geldig geslacht in.");
        }
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setNaam(String naam) {
        
        
    }

    public String getNaam() {
        return naam;
    }
    
    

    public String geefInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
