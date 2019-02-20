/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.*;
import language.LanguageResource;
import persistentie.KaartMapper;

/**
 *
 * @author ziggy
 */
public class DomeinController {
    private final KaartMapper km;
    private final List<Kaart> kaarten;
    private Locale locale;
    private final LanguageResource bundle;
    private Spel spel;
    
    public DomeinController(){
        km = new KaartMapper();
        kaarten = km.geefKaarten();
        bundle = new LanguageResource();
    }
    
    public void startSpel(int aantalSpelers, Locale locale){
        spel = new Spel(aantalSpelers, locale);
        bundle.setLocale(locale);
    }

    public void voegSpelerToe(String naam, String geslacht, int leeftijd) {
        spel.voegSpelerToe(naam, geslacht, leeftijd);
    }
    
    public String geefInformatie(){
        String ret = "";
        
        return ret;
    }
}
