package domein;

import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.schatkaarten.Equipment;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import exceptions.SpelerException;
import java.util.*;
import language.LanguageResource;

/**
 *
 * @author G35
 */
public class Speler {

    //Declaratie attributen
    private int level, aantalSchatkaarten, aantalKerkerkaarten;
    private String geslacht;
    private String naam;
    private List<Kaart> kaarten;
    private List<Kaart> items;
    private boolean heeftMonsterVerslaan;

    /**
     * Constructor van Speler zonder parameters naam = "onbekend", geslacht =
     * "man", leeftijd = 99, level = 1, taal = "en"
     */
    public Speler() {
        this("onbekend", "man", 1);
    }

    /**
     * Constructor van Speler zonder opgegeven taal, standaardtaal is "en" en
     * standaard level is 1
     *
     * @param naam De naam van de speler
     * @param geslacht Het geslacht van de speler
     */
    public Speler(String naam, String geslacht) {
        this(naam, geslacht, 1);
    }

    /**
     * Constructor van speler
     *
     * @param naam De naam van de speler
     * @param geslacht Het geslacht van de speler
     * @param level Het level van de Speler
     */
    public Speler(String naam, String geslacht, int level) {
        setGeslacht(geslacht);
        setNaam(naam);
        setLevel(level);
        kaarten = new ArrayList<>();
        items = new ArrayList<>();
        aantalKerkerkaarten = 0;
        aantalSchatkaarten = 0;
    }

    /**
     * Setter voor het geslacht van de speler, controle van geslacht in de taal
     * van de speler
     *
     * @param geslacht Het geslacht van de speler in eigen taal
     */
    public final void setGeslacht(String geslacht) {
        String man = LanguageResource.getString("man");
        String vrouw = LanguageResource.getString("woman");
        if (geslacht.toLowerCase().equals(man) || geslacht.toLowerCase().equals(vrouw)) {
            this.geslacht = geslacht;
        } else {
            throw new SpelerException(LanguageResource.getString("exception.sex"));
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
            throw new SpelerException(LanguageResource.getString("exception.name"));
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
            throw new SpelerException(LanguageResource.getString("exception.level"));
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
        if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
            aantalSchatkaarten++;
        } else if (kaart instanceof Monster || kaart instanceof Curse || kaart instanceof Race || kaart instanceof ConsumablesKerker) {
            aantalKerkerkaarten++;
        }
    }

    /**
     * Kaarten van de speler opvragen
     *
     * @return List met Kaart-objecten
     */
    public List<Kaart> getKaarten() {
        return kaarten;
    }

    /**
     * Het aantal kaarten van de speler opvragen
     *
     * @return Aantal kaarten van de speler
     */
    public int geefAantalKaarten() {
        return kaarten.size();
    }

    /**
     * Kaarten omzetten naar String
     *
     * @return String met info over de kaart
     */
    public String kaartenNaarString() {
        String ret = "";
        for (Kaart kaart: kaarten) {
            ret += String.format("%s  ", kaart.getNaam());
        }
        return ret;
    }
    
    public String itemsNaarString(){
        String ret = "";
        for (Kaart item: items) {
            ret += String.format("%s  ", item.getNaam());
        }
        return ret;
    }

    /**
     * Gegevens van Speler naar String omzetten
     *
     * @return String met info over speler
     */
    @Override
    public String toString() {
        return String.format("%s = %s, %s = %s, %s = %d, %s = %d, %s = %d, %s = %s", LanguageResource.getString("player.name"), naam, LanguageResource.getString("player.sex"), geslacht, LanguageResource.getString("player.level"), level, LanguageResource.getString("player.treasurecards"), getAantalSchatkaarten(), LanguageResource.getString("player.dungeoncards"), getAantalKerkerkaarten(), LanguageResource.getString("player.amountOfCards"), kaartenNaarString());
    }

    /**
     * Het aantal schatkaarten van de speler opvragen
     *
     * @return Het aantal schatkaarten
     */
    public int getAantalSchatkaarten() {
        return aantalSchatkaarten;
    }

    /**
     * Het aantal kerkerkaarten van de speler opvragen
     *
     * @return Het aantal kerkerkaarten
     */
    public int getAantalKerkerkaarten() {
        return aantalKerkerkaarten;
    }

    /**
     * Het aantal schatkaarten aanpassen
     *
     * @param aantalSchatkaarten Nieuw aantal schatkaarten
     */
    public void setAantalSchatkaarten(int aantalSchatkaarten) {
        this.aantalSchatkaarten = aantalSchatkaarten;
    }

    /**
     * Het aantal kerkerkaarten aanpassen
     *
     * @param aantalKerkerkaarten Nieuw aantal kerkerkaarten
     */
    public void setAantalKerkerkaarten(int aantalKerkerkaarten) {
        this.aantalKerkerkaarten = aantalKerkerkaarten;
    }

    /**
     *
     * @param heeftMonsterVerslaan
     */
    public void setHeeftMonsterVerslaan(boolean heeftMonsterVerslaan) {
        this.heeftMonsterVerslaan = heeftMonsterVerslaan;
    }

    /**
     *
     * @return
     */
    public boolean isHeeftMonsterVerslaan() {
        return heeftMonsterVerslaan;
    }
}
