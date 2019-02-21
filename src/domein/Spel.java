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

    //declaratie attributen
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

    //constructor Spel
    public Spel(int aantalSpelers, Locale choice) {
        setAantalSpelers(aantalSpelers);
        bundle = new LanguageResource(choice);
        spelers = new ArrayList<>();
    }

    //setter voor aantal spelers met controle op aantal volgens DR_AANTAL_SPELERS
    public final void setAantalSpelers(int aantalSpelers) {
        if (aantalSpelers >= 3 && aantalSpelers <= 6) {
            this.aantalSpelers = aantalSpelers;
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.players"));
        }
    }

    //startlevel
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

    //methode om speler toe te voegen
    public void voegSpelerToe(String naam, String geslacht, int leeftijd) {
        Speler speler = new Speler(naam, geslacht, leeftijd, 1, bundle.getLocale());
        spelers.add(speler);
    }

    //methode om kaart toe te voegen
    public void voegKaartToe(Kaart kaart, int spelernr) {
        spelers.get(spelernr).voegKaartToe(kaart);
    }

    //methode om de arraylist van de spelers terug te krijgen
    public List<Speler> getSpelers() {
        return spelers;
    }

    //methode die speler aantal schatkaarten toekent
    public int geefAantalSchatkaartenSpeler(int index) {
        return spelers.get(index).getAantalSchatkaarten();
    }

    //methode die speler aantal kerkerkaarten toekent
    public int geefAantalkerkerkaartenSpeler(int index) {
        return spelers.get(index).getAantalKerkerkaarten();
    }

    //methode die aantal kerkerkaarten verhoogt
    public void verhoogKerkerkaarten(int i) {
        spelers.get(i).setAantalKerkerkaarten(spelers.get(i).getAantalKerkerkaarten() + 1);
    }

    //methode die aantal schatkaarten verhoogt
    public void verhoogSchatkaarten(int i) {
        spelers.get(i).setAantalSchatkaarten(spelers.get(i).getAantalSchatkaarten() + 1);
    }
}
