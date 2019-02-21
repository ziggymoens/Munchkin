/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.security.SecureRandom;
import java.util.*;
import language.LanguageResource;
import persistentie.KaartMapper;

/**
 *
 * @author ziggy
 */
public class DomeinController {

    //declaratie attributen
    private final KaartMapper km;
    private final SpelRepository sr;
    private final List<Kaart> kaarten;
    private Locale locale;
    private final LanguageResource bundle;
    private Spel spel;

    //constructor DomeinController
    public DomeinController() {
        km = new KaartMapper();
        kaarten = km.geefKaarten();
        bundle = new LanguageResource();
        sr = new SpelRepository();
    }

    //methode om het spel te starten adhv het aantal spelers en de gekozen taal
    public void startSpel(int aantalSpelers, Locale locale) {
        spel = new Spel(aantalSpelers, locale);
        bundle.setLocale(locale);
    }

    //methode om een speler toete voegen adhv een naam, geslacht en leeftijd (deze worden verder gecontroleerd volgens de DR)
    public void voegSpelerToe(String naam, String geslacht, int leeftijd) {
        spel.voegSpelerToe(naam, geslacht, leeftijd);
    }

    //methode om elke speler 4 kaarten uit te delen, 2 van elke stapel (dit wordt gedaan adhv een SecureRandom)
    public void geefStartKaarten() {        
        SecureRandom random = new SecureRandom();
        //loop voor elke speler
        for (int i = 0; i < spel.getAantalSpelers(); i++) {
            int j = 0;            
            while (j < 4) {
                //System.out.println(kaarten.get(1).isKaartInGebruik());
                int rKaart;
                Kaart randKaart;
                do {
                    rKaart = random.nextInt(kaarten.size());
                    randKaart = kaarten.get(rKaart);
                } while (randKaart.isKaartInGebruik() == true);
                if (randKaart instanceof Equipment || randKaart instanceof ConsumablesSchat) {
                    if (spel.geefAantalSchatkaartenSpeler(i)<=2) {
                        spel.voegKaartToe(randKaart, i);
                        randKaart.setKaartInGebruik(true);
                        spel.verhoogSchatkaarten(i);
                    }else{
                        continue;
                    }
                } else if (randKaart instanceof Monster||randKaart instanceof Curse||randKaart instanceof Race||randKaart instanceof ConsumablesKerker){
                    if (spel.geefAantalkerkerkaartenSpeler(i)<=2) {
                        spel.voegKaartToe(randKaart, i);
                        randKaart.setKaartInGebruik(true);
                        spel.verhoogKerkerkaarten(i);
                    }else{
                        continue;
                    }   
                }
                j++;
            }
        }
    }

    //String die info geeft over het spel
    public String geefInformatie() {
        String ret = "";
        String[] sInfo = spel.geefInfo();
        for (String lijn : sInfo) {
            ret+= String.format("%s%n", lijn);
        }
        return ret;
    }
}
