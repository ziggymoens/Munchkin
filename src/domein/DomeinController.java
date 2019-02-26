package domein;

import domein.kaarten.Equipment;
import domein.kaarten.ConsumablesKerker;
import domein.kaarten.Race;
import domein.kaarten.Monster;
import domein.kaarten.ConsumablesSchat;
import domein.kaarten.Curse;
import domein.kaarten.Kaart;
import java.security.SecureRandom;
import java.util.*;
import language.LanguageResource;
import persistentie.KaartMapper;

/**
 *
 * @author ziggy
 */
public class DomeinController {

    //Declaratie attributen
    private final KaartMapper km;
    private final SpelRepository sr;
    private final List<Kaart> kaarten;
    private Locale locale;
    private final LanguageResource bundle;
    private Spel spel;

    /**
     * Constructor DomeinController
     */
    public DomeinController() {
        km = new KaartMapper();
        kaarten = km.geefKaarten();
        bundle = new LanguageResource();
        sr = new SpelRepository();
    }

    /**
     * Methode om het spel te starten adhv het aantal spelers en de gekozen taal
     *
     * @param aantalSpelers Gekozen aantal spelers (3-6)
     * @param locale Gekozen taal (nl-en-fr)
     */
    public void startSpel(int aantalSpelers, Locale locale) {
        spel = new Spel(aantalSpelers, locale);
        bundle.setLocale(locale);
        for (Kaart kaart:kaarten) {
            kaart.setLocale(locale);
        }
    }

    /**
     * Methode om een speler toete voegen adhv een naam, geslacht
     * (deze worden verder gecontroleerd volgens de DR)
     *
     * @param naam Naam van de speler
     * @param geslacht Geslacht van de speler     
     */
    public void voegSpelerToe(String naam, String geslacht/*, int leeftijd*/) {
        spel.voegSpelerToe(naam, geslacht/*, leeftijd*/);
    }

    /**
     * Methode om elke speler 4 kaarten uit te delen, 2 van elke stapel
     * NAAR SPEL
     */
    public void geefStartKaarten() {
        SecureRandom random = new SecureRandom();
        //Loop voor elke speler
        for (int i = 0; i < spel.getAantalSpelers(); i++) {
            int j = 0;
            for (int k = 0; k < 4; k++) {
                int rKaart;
                Kaart randKaart;
                //Random kaart kiezen en kijken of deze al in iemand zijn hand zit
                do {
                    rKaart = random.nextInt(kaarten.size());
                    randKaart = kaarten.get(rKaart);
                } while (randKaart.isKaartInGebruik() == true);
                //Kijken of kaart een van de schatkaarten is, de speler mag er zo 2 hebben
                if (randKaart instanceof Equipment || randKaart instanceof ConsumablesSchat) {
                    if (spel.geefAantalSchatkaartenSpeler(i) <= 2) {
                        spel.voegKaartToe(randKaart, i);
                        randKaart.setKaartInGebruik(true);
                        spel.verhoogSchatkaarten(i);
                    }
                //Kijken of kaart een van de kerkerkaarten is, de speler mag er zo 2 hebben
                } else if (randKaart instanceof Monster || randKaart instanceof Curse || randKaart instanceof Race || randKaart instanceof ConsumablesKerker) {
                    if (spel.geefAantalkerkerkaartenSpeler(i) <= 2) {
                        spel.voegKaartToe(randKaart, i);
                        randKaart.setKaartInGebruik(true);
                        spel.verhoogKerkerkaarten(i);
                    }
                }
            }
        }
    }

    /**
     * String die info geeft over het spel en de spelers
     *
     * @return String met alle informatie
     */
    public String geefInformatie() {
        String ret = "";
        String[] sInfo = spel.geefInfo();
        for (String lijn : sInfo) {
            ret += String.format("%s%n", lijn);
        }
        return ret;
    }
}
