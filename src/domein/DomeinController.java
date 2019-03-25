package domein;

import domein.repositories.SpelDbRepository;

import java.util.*;

import exceptions.SpelException;
import language.LanguageResource;

/**
 * @author ziggy
 */
public class DomeinController {

    private Spel spel;
    private final SpelDbRepository sr;

    /**
     * Constructor DomeinController
     */
    public DomeinController() {
        sr = new SpelDbRepository();
    }


    /**
     * Methode om het spel te starten adhv het aantal spelers en de gekozen taal
     *
     * @param aantalSpelers Gekozen aantal spelers (3-6)
     */
    public void startSpel(int aantalSpelers) {
        if (aantalSpelers < 3 || aantalSpelers > 6) {
            throw new SpelException("exception.spel.players");
        }
        spel = new Spel(aantalSpelers);
    }

    /**
     * String die info geeft over het spel en de spelers
     *
     * @return String met alle informatie
     */
    public String geefInformatie() {
        StringBuilder ret = new StringBuilder();
        String[] sInfo = spel.geefInfo();
        int index = 1;
        for (String lijn : sInfo) {
            ret.append(String.format("%s %d: %s%n", LanguageResource.getString("player"), index, lijn));
            index++;
        }
        return ret.toString();
    }

    /**
     *
     */
    public void controleerVolgorde() {
        spel.controleerVolgorde();
    }

    /**
     * @param i
     * @return
     */
    public String geefNaamSpeler(int i) {
        return spel.getSpelers().get(i).getNaam();
    }

    /**
     * @param i
     * @return
     */
    public int geefLevel(int i) {
        return spel.getSpelers().get(i).getLevel();
    }

    /**
     * @return
     */
    public String geefNaamWinnaar() {
        return spel.geefWinnaar();
    }


    /**
     * Spel opslaan
     */
    public void spelOpslaan() {
        sr.spelOpslaan(this.spel);
    }

    /**
     * @return
     */
    public String geefOpgeslagenSpellen() {
        List<Spel> spellen = sr.getSpellen();
        StringBuilder ret = new StringBuilder();
        int index = 1;
        for (Spel spel : spellen) {
            ret.append(String.format("Spel %d%n%s", index, spel.toString()));
        }
        return ret.toString();
    }

    /**
     * Spel laden
     *
     * @param index
     */
    public void spelLaden(int index) {
        this.spel = sr.getSpellen().get(index - 1);
    }

    /**
     * @return
     */
    public List<String> geefSpelsituatie() {
        return spel.geefSpelsituatie();
    }

    /**
     * @return
     */
    public boolean niemandGewonnen() {
        return spel.niemandGewonnen();
    }

    /**
     * @param i
     * @param naam
     */
    public void geefSpelerNaam(int i, String naam) {
        spel.geefSpelerNaam(i, naam);
    }

    /**
     *
     */
    public void maakSpeler() {
        spel.maakNieuweSpeler();
    }

    /**
     * @param i
     * @param geslacht
     */
    public void geefSpelerGeslacht(int i, String geslacht) {
        spel.geefSpelerGeslacht(i, geslacht);
    }

    /**
     *
     */
    public void geefStartKaarten() {
        spel.geefStartKaarten();
    }

    /**
     * @return
     */
    public String toonBovensteKk() {
        return String.format("ui/images/%d.png", spel.toonBovensteKk());
    }

    /**
     * @param id
     * @return
     */
    public String geefTypeKaart(int id) {
        return spel.geefTypeKaart(id);
    }

    /**
     * @param naam
     */
    public void curseKaart(String naam) {
        spel.speelCurse(naam);
    }

    /**
     * @return
     */
    public int geefIdBovensteKaart() {
        return spel.toonBovensteKk();
    }

    /**
     *
     */
    public void nieuweBovensteKaartK() {
        spel.nieuwBovensteKaartK();
    }

    /**
     * @param naam
     */
    public void geefKerkerkaartAanSpeler(String naam) {
        spel.geefKerkerkaartAanSpeler(naam);
    }

    /**
     * public String geefTypeLostCurse() {
     * return spel.geefTypeLostCurse().toLowerCase();
     * }
     * <p>
     * public String toonItemsSpeler(String naam) {
     * int i = 0;
     * String ret = "";
     * for (String line : spel.geefItemsSpeler(naam)){
     * ret += String.format("%d) %s%n", i,line);
     * i++;
     * }
     * return ret;
     * }
     * <p>
     * public int geefAantalItemsSpeler(String naam) {
     * if (spel.geefAantalItemsSpeler(naam) == -99){
     * throw new SpelException("exception.spel.itemsspeler");
     * }
     * return spel.geefAantalItemsSpeler(naam);
     * }
     * <p>
     * public void verwijderItemSpeler(String naam, int keuze) {
     * spel.verwijderItemSpeler(naam, keuze-1);
     * }
     */

    public void geefSpelNaam(String naam) {
        spel.setNaam(naam);
    }

    public boolean spelerTeVeelKaarten(String naam) {
        return spel.spelerTeVeelKaarten(naam);
    }

    public String bovensteKaartToString() {
        return spel.bovensteKaartToString();
    }

    public void zetSpelerAanBeurt(int i) {
        spel.setSpelerAanBeurt(i);
    }

    public int geefSpelerAanBeurt(){
        return spel.getSpelerAanBeurt();
    }
}
