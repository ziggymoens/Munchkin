package domein;

import domein.kaarten.Kaart;
import domein.repositories.KaartRepository;
import exceptions.SpelException;
import java.util.*;
import language.LanguageResource;

/**
 *
 * @author ziggy
 */
public class Spel {

    //Declaratie attributen
    private static int aantalSpelers;
    private final List<Speler> spelers;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;
    private KaartRepository kr;

    /**
     * Constructor van Spel zonder parameters spelers = 3
     */
    public Spel() {
        this(3);
    }

    /**
     * Constructor van Spel
     *
     * @param aantalSpelers Het gewenste aantal spelers
     */
    public Spel(int aantalSpelers) {
        setAantalSpelers(aantalSpelers);
        spelers = new ArrayList<>();
        kr = new KaartRepository();
        schatkaarten = kr.getSchatkaarten();
        kerkerkaarten = kr.getKerkerkaarten();
    }

    /**
     * Setter voor aantal spelers met controle op aantal volgens
     * DR_AANTAL_SPELERS
     *
     * @param aantalSpelers Het gekozen aantal spelers
     */
    private void setAantalSpelers(int aantalSpelers) {
        if (aantalSpelers >= 3 && aantalSpelers <= 6) {
            Spel.aantalSpelers = aantalSpelers;
        } else {
            throw new SpelException(LanguageResource.getString("exception.players"));
        }
    }

    /**
     * Geeft elke speler het startlevel 1
     */
    public void startLevels() {
        for (int i = 0; i < spelers.size(); i++) {
            spelers.get(i).setLevel(1);
        }
    }

    /**
     * Geeft info over het spel en de spelers
     *
     * @return String Array met de info
     */
    public String[] geefInfo() {
        String[] ret = new String[spelers.size()];
        for (int i = 0; i < spelers.size(); i++) {
            ret[i] = spelers.get(i).toString();
        }
        return ret;
    }

    /**
     * Voeg een speler toe aan het spel
     *
     * @param naam De naam van de speler
     * @param geslacht Het geslacht van de speler
     *
     */
    public void voegSpelerToe(String naam, String geslacht) {
        controleSpeler(naam);
        Speler speler = new Speler(naam, geslacht, 1);
        geefStartKaarten(speler);
        spelers.add(speler);
    }

    /**
     * Voeg een kaart toe aan de hand van een speler
     *
     * @param kaart De kaart voor de speler
     * @param spelernr De nummer van de speler in het spel, volgens volgorde van
     * ingeven
     */
    public void voegKaartToe(Kaart kaart, int spelernr) {
        spelers.get(spelernr).voegKaartToe(kaart);
    }

    /**
     * Geeft de spelers van het spel terug
     *
     * @return List van Speler-objecten
     */
    public List<Speler> getSpelers() {
        return spelers;
    }

    /**
     *
     * @param naam
     */
    private void controleSpeler(String naam) {
        for (Speler speler : spelers) {
            if (naam.equals(speler.getNaam())) {
                throw new SpelException();
            }
        }
    }

    /**
     *
     * @param speler
     */
    private void geefStartKaarten(Speler speler) {
        speler.voegKaartToe(schatkaarten.get(0));
        schatkaarten.remove(0);
        speler.voegKaartToe(kerkerkaarten.get(0));
        kerkerkaarten.remove(0);
        speler.voegKaartToe(schatkaarten.get(0));
        schatkaarten.remove(0);
        speler.voegKaartToe(kerkerkaarten.get(0));
        kerkerkaarten.remove(0);
    }

    /**
     *
     * @return
     */
    public static int getAantalSpelers() {
        return aantalSpelers;
    }

    /**
     *
     */
    public void speelSpel() {
        Speler speler = spelers.get(0);
        for (int i = 0; i < spelers.size(); i++) {
            if (speler.getNaam().toLowerCase().length() >= spelers.get(i).getNaam().toLowerCase().length()) {
                if (speler.getNaam().compareToIgnoreCase(spelers.get(i).getNaam()) <= 0) {
                    speler = spelers.get(i);
                }
            }
        }
        int index = spelers.indexOf(speler);
        spelers.remove(index);
        spelers.add(0, speler);
    }
    
    public String geefWinnaar(){
        String naam = "";
        for (Speler speler: spelers) {
            if (speler.getLevel() == 10) {
                naam = speler.getNaam();
            }
        }
        return naam;
    }
}
