package domein;

import domein.repositories.SpelRepository;
import domein.kaarten.Kaart;
import java.util.*;
import persistentie.KaartMapper;

/**
 *
 * @author ziggy
 */
public class DomeinController {

    //Declaratie attributen
    private final KaartMapper km;
    private final SpelRepository sr;
    public static List<Kaart> kaarten;
    //private Locale locale;
    //private final LanguageResource bundle;
    private Spel spel;

    /**
     * Constructor DomeinController
     */
    public DomeinController() {
        km = new KaartMapper();
        kaarten = km.geefKaarten();
        /*bundle = new LanguageResource();*/
        sr = new SpelRepository();
    }

    /**
     * Methode om het spel te starten adhv het aantal spelers en de gekozen taal
     *
     * @param aantalSpelers Gekozen aantal spelers (3-6)
     */
    public void startSpel(int aantalSpelers/*, Locale locale*/) {
        spel = new Spel(aantalSpelers/*, locale*/);
        /*bundle.setLocale(locale);
        for (Kaart kaart:kaarten) {
            kaart.setLocale(locale);
        }*/
    }

    /**
     * Methode om een speler toete voegen adhv een naam, geslacht
     * (deze worden verder gecontroleerd volgens de DR)
     *
     * @param naam Naam van de speler
     * @param geslacht Geslacht van de speler     
     */
    public void voegSpelerToe(String naam, String geslacht) {
        spel.voegSpelerToe(naam, geslacht);
    }

    /**
     * Methode om elke speler 4 kaarten uit te delen, 2 van elke stapel
     * NAAR SPEL
     */
    public void geefStartKaarten() {
        spel.geefStartKaarten();
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
