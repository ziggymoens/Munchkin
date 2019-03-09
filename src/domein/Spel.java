package domein;

import domein.kaarten.Kaart;
import domein.repositories.KaartDbRepository;
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
    private final List<Speler> spelers;
    private final List<Kaart> schatkaarten;
    private final List<Kaart> kerkerkaarten;

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
        KaartDbRepository kr = new KaartDbRepository();
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
            this.aantalSpelers = aantalSpelers;
        } else {
            throw new SpelException("exception.spel.players");
        }
    }

    /**
     * Geeft elke speler het startlevel 1
     */
    public void startLevels() {
        for (Speler speler : spelers) {
            speler.setLevel(1);
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
                throw new SpelException("exceptions.spel.namenotunique");
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
    public int getAantalSpelers() {
        return aantalSpelers;
    }

    /**
     *
     */
    public void controleerVolgorde() {
        Speler speler = spelers.get(0);
        for (Speler speler1 : spelers) {
            if (speler.getNaam().toLowerCase().length() >= speler1.getNaam().toLowerCase().length()) {
                if (speler.getNaam().compareToIgnoreCase(speler1.getNaam()) <= 0) {
                    speler = speler1;
                }
            }
        }
        spelers.remove(speler);
        spelers.add(0, speler);
    }

    /**
     *
     * @return
     */
    public String geefWinnaar() {
        String naam = "";
        for (Speler speler : spelers) {
            if (speler.getLevel() == 10) {
                naam = speler.getNaam();
                break;
            }
        }
        return naam;
    }

    /**
     *
     * @param speler
     */
    public void geefLevel(Speler speler) {
        if (speler.getLevel() == 9 && speler.isHeeftMonsterVerslaan()) {
            speler.setLevel(speler.getLevel() + 1);
        } else if (speler.getLevel() >= 1 && speler.getLevel() < 9) {
            speler.setLevel(speler.getLevel() + 1);
        }
    }

    /**
     *
     * @return
     */
    public String geefSpelsituatie() {
        StringBuilder ret  = new StringBuilder();
        for (Speler speler : spelers) {
            ret.append(String.format("%s: %s, %s: %s, %s: %d, %s: %s", LanguageResource.getString("player.name"), speler.getNaam(), LanguageResource.getString("player.sex"), speler.getGeslacht(), LanguageResource.getString("player.level"), speler.getLevel(), LanguageResource.getString("player.items"), speler.kaartenNaarString(speler.getItems())));
        }
        return ret.toString();
    }

    /**
     *
     * @return
     */
    public boolean niemandGewonnen() {
        boolean ret = true;
        for (Speler speler: spelers) {
            if (speler.getLevel() == 10) {
                return false;
            }
        }
        return ret;
    }

    public void maakNieuweSpeler() {
        spelers.add(new Speler());
    }

    public void geefSpelerNaam(int i, String naam) {
        controleSpeler(naam);
        spelers.get(i).setNaam(naam);
    }

    public void geefSpelerGeslacht(int i, String geslacht) {
        spelers.get(i).setGeslacht(geslacht);
    }

    public void geefStartKaarten() {
        for (Speler speler : spelers) {
            geefStartKaarten(speler);
        }
    }
}
