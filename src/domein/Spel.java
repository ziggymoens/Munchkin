/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.*;

/**
 *
 * @author ziggy
 */
public class Spel {
    private Locale choice;
    private int aantalSpelers;
    private Speler[] spelers;
    
    
    public Spel(int aantalSpelers){
        this(aantalSpelers, new Locale("en"));
    }

    public Spel(int aantalSpelers, Locale choice) {
        setAantalSpelers(aantalSpelers);
        this.choice=choice;
        
    }

    public final void setAantalSpelers(int aantalSpelers) {
        if (aantalSpelers >= 3 && aantalSpelers <= 6) {
            this.aantalSpelers = aantalSpelers;
        }else{
            throw new IllegalArgumentException(ResourceBundle.getBundle("domein/i18n",this.choice).getString("exception.players")); // VRAGEN
        }
        spelers = new Speler[aantalSpelers];
    }
    
    public void startLevels(){
        for (int i = 0; i < spelers.length; i++) {
            spelers[i].setLevel(1);
        }
    }

    
    public int getAantalSpelers() {
        return aantalSpelers;
    }

    public String[] geefInfo() {
        String[] ret = new String[spelers.length];
        for (int i = 0; i < spelers.length; i++) {
            ret[i] = spelers[i].toString();
        }
        return ret;
    }

    public void voegSpelerToe(int i, Speler speler) {
        spelers[i] = speler;
    }
    public void voegKaartToe(Kaart kaart, int spelernr){
        spelers[spelernr].voegKaartToe(kaart);
    }
}

