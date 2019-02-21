package domein;

import java.util.*;
import language.LanguageResource;

/**
 *
 * @author G35
 */
public class Speler {

    //Declaratie attributen
    private int leeftijd, level, aantalSchatkaarten, aantalKerkerkaarten;
    private String geslacht;
    private String naam;
    private List<Kaart> kaarten;
    private static LanguageResource bundle = new LanguageResource();

    /**
     * Constructor van Speler zonder parameters naam = "onbekend", geslacht =
     * "man", leeftijd = 99, level = 1, taal = "en"
     */
    public Speler() {
        this("onbekend", "man", 99, 1, new Locale("en"));
    }

    /**
     * Constructor van Speler zonder opgegeven taal, standaardtaal is "en" en
     * standaard level is 1
     *
     * @param naam De naam van de speler
     * @param geslacht Het geslacht van de speler
     * @param leeftijd De leeftijd van de speler
     */
    public Speler(String naam, String geslacht, int leeftijd) {
        this(naam, geslacht, leeftijd, 1, new Locale("en"));
    }

    /**
     * Constructor van speler met standaartaal is "en"
     *
     * @param naam De naam van de speler
     * @param geslacht Het geslacht van de speler
     * @param leeftijd De leeftijd van de speler
     * @param level Het level van de speler
     */
    public Speler(String naam, String geslacht, int leeftijd, int level) {
        this(naam, geslacht, leeftijd, level, new Locale("en"));
    }

    /**
     * Constructor van speler
     *
     * @param naam De naam van de speler
     * @param geslacht Het geslacht van de speler
     * @param leeftijd De leeftijd van de speler
     * @param level Het level van de Speler
     * @param choice De gekozen taal van de speler
     */
    public Speler(String naam, String geslacht, int leeftijd, int level, Locale choice) {
        bundle.setLocale(choice);
        setLeeftijd(leeftijd);
        setGeslacht(geslacht);
        setNaam(naam);
        setLevel(level);
        kaarten = new ArrayList<>();
        aantalKerkerkaarten = 0;
        aantalSchatkaarten = 0;
    }

    /**
     * Setter voor leeftijd met controle
     *
     * @param leeftijd De leeftijd van de speler
     */
    public final void setLeeftijd(int leeftijd) {
        if (leeftijd > 0) {
            this.leeftijd = leeftijd;
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.age"));
        }
    }

    /**
     * Leeftijd van de speler opvragen
     *
     * @return De leeftijd van de speler
     */
    public int getLeeftijd() {
        return leeftijd;
    }

    /**
     * Setter voor het geslacht van de speler, controle van geslacht in de taal
     * van de speler
     *
     * @param geslacht Het geslacht van de speler in eigen taal
     */
    public final void setGeslacht(String geslacht) {
        String man = bundle.getString("man");
        String vrouw = bundle.getString("woman");
        if (geslacht.toLowerCase().equals(man) || geslacht.toLowerCase().equals(vrouw)) {
            this.geslacht = geslacht;
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.sex"));
        }
    }

    /**
     * Geslacht van de speler opvragen
     *
     * @return Het geslacht van de speler
     */
    public String getGeslacht() {
        return geslacht;
    }

    /**
     * Setter voor de naam van de speler met controle volgens DR
     *
     * @param naam De naam van de speler
     */
    public final void setNaam(String naam) {
        if (naam.length() >= 6 && naam.length() <= 12) {
            for (int i = 0; i < naam.length(); i++) {
                if (naam.charAt(i) >= 'a' || naam.charAt(i) <= 'z' || naam.charAt(i) >= 'A' || naam.charAt(i) <= 'Z' || naam.charAt(i) == '_' || naam.charAt(i) == '-') {
                    this.naam = naam;
                }
            }
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.name"));
        }
    }

    /**
     * Naam van de speler opvragen
     *
     * @return De naam van de speler
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Setter voor het level van de speler
     *
     * @param level Het level van de speler (groter dan 0)
     */
    public final void setLevel(int level) {
        if (level >= 1) {
            this.level = level;
        } else {
            throw new IllegalArgumentException(bundle.getString("exception.level"));
        }
    }

    /**
     * Het level van de speler opvragen
     *
     * @return Level van de speler
     */
    public int getLevel() {
        return level;
    }

    /**
     * Voeg kaart toe aan hand van speler
     *
     * @param kaart de toegewezen kaart
     */
    public void voegKaartToe(Kaart kaart) {
        this.kaarten.add(kaart);
    }

    public List<Kaart> getKaarten() {
        return kaarten;
    }

    public int geefAantalKaarten() {
        return getAantalKerkerkaarten() + getAantalSchatkaarten();
    }

    //methode die kaarten omzet naar een string zodat deze gelezen kunnen worden
    public String kaartenNaarString() {
        String ret = "";
        for (int i = 0; i < geefAantalKaarten(); i++) {
            ret += String.format("%s  ", kaarten.get(i).getNaam());
        }
        return ret;
    }

    //toString om resultaat van deling te tonen
    @Override
    public String toString() {
        return String.format("naam = %s, geslacht = %s, leeftijd = %d, level = %d, aantal schatkaarten = %d, aantal kerkerkaarten = %d, kaarten = %s", naam, geslacht, leeftijd, level, getAantalSchatkaarten(), getAantalKerkerkaarten(), kaartenNaarString());
    }

    public int getAantalSchatkaarten() {
        return aantalSchatkaarten;
    }

    public int getAantalKerkerkaarten() {
        return aantalKerkerkaarten;
    }

    public void setAantalSchatkaarten(int aantalSchatkaarten) {
        this.aantalSchatkaarten = aantalSchatkaarten;
    }

    public void setAantalKerkerkaarten(int aantalKerkerkaarten) {
        this.aantalKerkerkaarten = aantalKerkerkaarten;
    }

}
