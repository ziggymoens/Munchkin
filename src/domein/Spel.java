/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.*;
import ui.*;

/**
 *
 * @author ziggy
 */
public class Spel {
    private Locale choice;
    private int aantalSpelers;

    public Spel(int aantalSpelers, Locale choice) {
        setAantalSpelers(aantalSpelers);
        this.choice=choice;
    }

    public final void setAantalSpelers(int aantalSpelers) {
        if (aantalSpelers >= 3 && aantalSpelers <= 6) {
            this.aantalSpelers = aantalSpelers;
        }else{
            throw new IllegalArgumentException(ResourceBundle.getBundle("/domein/i18n",this.choice).getString("exception.players")); // VRAGEN
        }
        Speler[] spelers = new Speler[aantalSpelers];
    }

    public int getAantalSpelers() {
        return aantalSpelers;
    }

    public String geefInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
