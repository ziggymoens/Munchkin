package domein;

import domein.repositories.SpelRepository;
import java.util.List;
import language.LanguageResource;

/**
 *
 * @author ziggy
 */
public class DomeinController {

    private Spel spel;
    private final SpelRepository sr;

    /**
     * Constructor DomeinController
     */
    public DomeinController() {
        sr = new SpelRepository();
    }

    /**
     * Methode om het spel te starten adhv het aantal spelers en de gekozen taal
     *
     * @param aantalSpelers Gekozen aantal spelers (3-6)
     */
    public void startSpel(int aantalSpelers) {
        spel = new Spel(aantalSpelers);

    }

//    /**
//     * Methode om een speler toete voegen adhv een naam, geslacht (deze worden
//     * verder gecontroleerd volgens de DR)
//     *
//     * @param naam Naam van de speler
//     * @param geslacht Geslacht van de speler
//     */
//    public void voegSpelerToe(String naam, String geslacht) {
//        spel.voegSpelerToe(naam, geslacht);
//    }

    /**
     * String die info geeft over het spel en de spelers
     *
     * @return String met alle informatie
     */
    public String geefInformatie() {
        String ret = "";
        String[] sInfo = spel.geefInfo();
        int index = 1;
        for (String lijn : sInfo) {
            ret += String.format("%s %d: %s%n", LanguageResource.getString("player"), index, lijn);
            index++;
        }
        return ret;
    }

    /**
     *
     * @return
     */
    public int geefAantalSpelers() {
        return spel.getAantalSpelers();
    }

    /**
     *
     */
    public void controleerVolgorde() {
        spel.controleerVolgorde();
    }

    /**
     *
     * @param i
     * @return
     */
    public String geefNaamSpeler(int i) {
        return spel.getSpelers().get(i).getNaam();
    }

    /**
     *
     * @param i
     * @return
     */
    public int geefLevel(int i) {
        return spel.getSpelers().get(i).getLevel();
    }

    /**
     *
     * @return
     */
    public String geefNaamWinnaar() {
        return spel.geefWinnaar();
    }

    /**
     *
     * @param naam naam van de speler die de beurt gaat spelen
     */
    public void speelBeurt(String naam) {
        //spel.geefLevel(speler);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Spel opslaan
     */
    public void spelOpslaan() {
        sr.spelOpslaan(this.spel);
    }

    public String geefOpgeslagenSpellen() {
        List<Spel> spellen = sr.getSpellen();
        String ret = "";
        int index = 1;
        for (Spel spel : spellen) {
            ret += String.format("Spel %d%n%s", index, spel.toString());
        }
        return ret;
    }

    /**
     * Spel laden
     *
     * @param index
     */
    public void spelLaden(int index) {
        this.spel = sr.getSpellen().get(index - 1);
    }

    public String geefSpelsituatie() {
        return spel.geefSpelsituatie();
    }

    public boolean niemandGewonnen() {
        return spel.niemandGewonnen();
    }

    public void geefSpelerNaam(int i, String naam) {
        spel.geefSpelerNaam(i, naam);
    }

    public void maakSpeler() {
        spel.maakNieuweSpeler();
    }

    public void geefSpelerGeslacht(int i, String geslacht) {
        spel.geefSpelerGeslacht(i, geslacht);
    }

    public void geefStartKaarten() {
        spel.geefStartKaarten();
    }
}
