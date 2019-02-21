/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.*;
import language.LanguageResource;

/**
 *
 * @author ziggy
 */
public class Spel {

    private int aantalSpelers;
    private List<Speler> spelers;
    private Locale locale;
    private final LanguageResource bundle;

    public Spel() {
        this(3, new Locale("en"));
    }

    public Spel(int aantalSpelers) {
        this(aantalSpelers, new Locale("en"));
    }

    public Spel(int aantalSpelers, Locale choice) {
        setAantalSpelers(aantalSpelers);
        bundle = new LanguageResource(choice);
        spelers = new ArrayList<>();
    }

    public final void setAantalSpelers(int aantalSpelers) {
        if (aantalSpelers >= 3 && aantalSpelers <= 6) {
            this.aantalSpelers = aantalSpelers;
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.players"));
        }
    }

    public void startLevels() {
        for (int i = 0; i < spelers.size(); i++) {
            spelers.get(i).setLevel(1);
        }
    }

    public int getAantalSpelers() {
        return aantalSpelers;
    }

    public String[] geefInfo() {
        String[] ret = new String[spelers.size()];
        for (int i = 0; i < spelers.size(); i++) {
            ret[i] = spelers.get(i).toString();
        }
        return ret;
    }

    public void voegSpelerToe(String naam, String geslacht, int leeftijd) {
        Speler speler = new Speler(naam, geslacht, leeftijd, 1, bundle.getLocale());
        spelers.add(speler);
    }

    public void voegKaartToe(Kaart kaart, int spelernr) {
        spelers.get(spelernr).voegKaartToe(kaart);
    }

    public List<Speler> getSpelers() {
        return spelers;
    }

    public int geefAantalSchatkaartenSpeler(int index) {
        return spelers.get(index).getAantalSchatkaarten();
    }

    public int geefAantalkerkerkaartenSpeler(int index) {
        return spelers.get(index).getAantalKerkerkaarten();
    }
}
