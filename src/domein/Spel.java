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

    public Spel(int aantalSpelers) {
        setAantalSpelers(aantalSpelers);
    }

    public final void setAantalSpelers(int aantalSpelers) {
        this.aantalSpelers = aantalSpelers;
    }
    
    public int getAantalSpelers() {
        return aantalSpelers;
    }    

    public String geefInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
