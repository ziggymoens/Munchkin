package domein;

import domein.kaarten.Kaart;
import exceptions.SpelException;
import java.util.*;
import language.LanguageResource;

/**
 *
 * @author ziggy
 */
public class Spel {

    //Declaratie attributen
    private int aantalSpelers;
    private List<Speler> spelers;
    private Locale locale;
    private final LanguageResource bundle;

    /**
     * Constructor van Spel zonder parameters spelers = 3, taal = "en"
     */
    public Spel() {
        this(3, new Locale("en"));
    }

    /**
     * Constuctor van Spel zonder taal, standaardtaal is "en"
     *
     * @param aantalSpelers Het gewenste aantal spelers
     */
    public Spel(int aantalSpelers) {
        this(aantalSpelers, new Locale("en"));
    }

    /**
     * Constructor van Spel
     *
     * @param aantalSpelers Het gewenste aantal spelers
     * @param choice De gekozen taal
     */
    public Spel(int aantalSpelers, Locale choice) {
        setAantalSpelers(aantalSpelers);
        bundle = new LanguageResource(choice);
        spelers = new ArrayList<>();
    }

    /**
     * Setter voor aantal spelers met controle op aantal volgens
     * DR_AANTAL_SPELERS
     *
     * @param aantalSpelers Het gekozen aantal spelers
     */
    public final void setAantalSpelers(int aantalSpelers) {
        if (aantalSpelers >= 3 && aantalSpelers <= 6) {
            this.aantalSpelers = aantalSpelers;
        } else {
            throw new SpelException(bundle.getString("exception.players"));
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
     * Geeft het aantal spelers die het spel spelen
     *
     * @return Aantal spelers
     */
    public int getAantalSpelers() {
        return aantalSpelers;
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
     * @param leeftijd De leeftijd van de speler
     */
    public void voegSpelerToe(String naam, String geslacht/*, int leeftijd*/) {
        Speler speler = new Speler(naam, geslacht/*, leeftijd*/, 1, bundle.getLocale());
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
     * Bekijkt hoeveel schatkaarten een bepaalde speler heeft
     *
     * @param index De nummer van de speler in het spel, volgens volgorde van
     * ingeven
     * @return Het aantal schatkaarten van de speler
     */
    public int geefAantalSchatkaartenSpeler(int index) {
        return spelers.get(index).getAantalSchatkaarten();
    }

    /**
     * Bekijkt hoeveel kerkerkaarten een bepaalde speler heeft
     *
     * @param index De nummer van de speler in het spel, volgens volgorde van
     * ingeven
     * @return Het aantal kerkerkaarten van de speler
     */
    public int geefAantalkerkerkaartenSpeler(int index) {
        return spelers.get(index).getAantalKerkerkaarten();
    }

    /**
     * Verhoogt het aantal kerkerkaarten van de speler met 1
     *
     * @param i De nummer van de speler in het spel, volgens volgorde van
     * ingeven
     */
    public void verhoogKerkerkaarten(int i) {
        spelers.get(i).setAantalKerkerkaarten(spelers.get(i).getAantalKerkerkaarten() + 1);
    }

    /**
     * Verhoogt het aantal schatkaarten van de speler met 1
     *
     * @param i De nummer van de speler in het spel, volgens volgorde van
     * ingeven
     */
    public void verhoogSchatkaarten(int i) {
        spelers.get(i).setAantalSchatkaarten(spelers.get(i).getAantalSchatkaarten() + 1);
    }
}
