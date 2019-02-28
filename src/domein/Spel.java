package domein;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
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
    private int aantalSpelers;
    private final List<Speler> spelers;
    private List<Kaart> kaarten;
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
        kaarten = kr.geefKaarten();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        initialiseerKaarten();
    }

    private void initialiseerKaarten(){
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

// --Commented out by Inspection START (2019-02-27 23:49):
//    /**
//     * Geeft het aantal spelers die het spel spelen
//     *
//     * @return Aantal spelers
//     */
//    public int getAantalSpelers() {
//        return aantalSpelers;
//    }
// --Commented out by Inspection STOP (2019-02-27 23:49)

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

// --Commented out by Inspection START (2019-02-27 23:49):
//    /**
//     * Bekijkt hoeveel schatkaarten een bepaalde speler heeft
//     *
//     * @param index De nummer van de speler in het spel, volgens volgorde van
//     * ingeven
//     * @return Het aantal schatkaarten van de speler
//     */
//    public int geefAantalSchatkaartenSpeler(int index) {
//        return spelers.get(index).getAantalSchatkaarten();
//    }
// --Commented out by Inspection STOP (2019-02-27 23:49)

// --Commented out by Inspection START (2019-02-27 23:49):
//    /**
//     * Bekijkt hoeveel kerkerkaarten een bepaalde speler heeft
//     *
//     * @param index De nummer van de speler in het spel, volgens volgorde van
//     * ingeven
//     * @return Het aantal kerkerkaarten van de speler
//     */
//    public int geefAantalkerkerkaartenSpeler(int index) {
//        return spelers.get(index).getAantalKerkerkaarten();
//    }
// --Commented out by Inspection STOP (2019-02-27 23:49)

// --Commented out by Inspection START (2019-02-27 23:50):
//    /**
//     * Verhoogt het aantal kerkerkaarten van de speler met 1
//     *
//     * @param i De nummer van de speler in het spel, volgens volgorde van
//     * ingeven
//     */
//    public void verhoogKerkerkaarten(int i) {
//        spelers.get(i).setAantalKerkerkaarten(spelers.get(i).getAantalKerkerkaarten() + 1);
//    }
// --Commented out by Inspection STOP (2019-02-27 23:50)

// --Commented out by Inspection START (2019-02-27 23:50):
//    /**
//     * Verhoogt het aantal schatkaarten van de speler met 1
//     *
//     * @param i De nummer van de speler in het spel, volgens volgorde van
//     * ingeven
//     */
//    public void verhoogSchatkaarten(int i) {
//        spelers.get(i).setAantalSchatkaarten(spelers.get(i).getAantalSchatkaarten() + 1);
//    }
// --Commented out by Inspection STOP (2019-02-27 23:50)

    private void controleSpeler(String naam) {
        for (Speler speler : spelers) {
            if (naam.equals(speler.getNaam())) {
                throw new SpelException();
            }
        }
    }

    private void geefStartKaarten(Speler speler) {
        speler.voegKaartToe(schatkaarten.get(0));
        schatkaarten.remove(0);
        speler.voegKaartToe(kerkerkaarten.get(0));
        kerkerkaarten.remove(0);
        speler.voegKaartToe(schatkaarten.get(1));
        schatkaarten.remove(0);
        speler.voegKaartToe(kerkerkaarten.get(1));
        kerkerkaarten.remove(1);

//        SecureRandom random = new SecureRandom();
//        //Loop voor elke speler
//        for (int i = 0; i < getAantalSpelers(); i++) {
//            int j = 0;
//            for (int k = 0; k < 4; k++) {
//                int rKaart;
//                Kaart randKaart;
//                //Random kaart kiezen en kijken of deze al in iemand zijn hand zit
//                do {
//                    rKaart = random.nextInt(DomeinController.kaarten.size());
//                    randKaart = DomeinController.kaarten.get(rKaart);
//                } while (randKaart.isKaartInGebruik() == true);
//                //Kijken of kaart een van de schatkaarten is, de speler mag er zo 2 hebben
//                if (randKaart instanceof Equipment || randKaart instanceof ConsumablesSchat) {
//                    if (geefAantalSchatkaartenSpeler(i) <= 2) {
//                        voegKaartToe(randKaart, i);
//                        randKaart.setKaartInGebruik(true);
//                        verhoogSchatkaarten(i);
//                    }
//                //Kijken of kaart een van de kerkerkaarten is, de speler mag er zo 2 hebben
//                } else if (randKaart instanceof Monster || randKaart instanceof Curse || randKaart instanceof Race || randKaart instanceof ConsumablesKerker) {
//                    if (geefAantalkerkerkaartenSpeler(i) <= 2) {
//                        voegKaartToe(randKaart, i);
//                        randKaart.setKaartInGebruik(true);
//                        verhoogKerkerkaarten(i);
//                    }
//                }
//            }
//        }
    }
}
