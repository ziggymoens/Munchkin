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
     * controleert de volgorde van de spelers adhv domeinregels
     *
     */
    public void controleerVolgorde() {
        spel.controleerVolgorde();
    }

    /**
     * @param i
     * 
     * @return de naam van de speler
     */
    public String geefNaamSpeler(int i) {
        return spel.getSpelers().get(i).getNaam();
    }

    /**
     * @param i
     * 
     * @return level van speler
     */
    public int geefLevel(int i) {
        return spel.getSpelers().get(i).getLevel();
    }

    /**
     * @return naam van de winnaar
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
     *
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
     *
     * @return de huidige spelsituatie
     */
    public String geefSpelsituatie() {
        return spel.geefSpelsituatie();
    }

    /**
     *
     * @return gewonnen of niet
     */
    public boolean niemandGewonnen() {
        return spel.niemandGewonnen();
    }

    /**
     * Geeft de naam van de speler op de index van de ArrayList
     * 
     * @param i
     * @param naam 
     */
    public void geefSpelerNaam(int i, String naam) {
        spel.geefSpelerNaam(i, naam);
    }

    /**
     * speler wordt aangemaakt
     * 
     */
    public void maakSpeler() {
        spel.maakNieuweSpeler();
    }

    /**
     * Geeft de het geslacht van de speler op de index van de Arraylist
     * 
     * @param i
     * @param geslacht     
     */
    public void geefSpelerGeslacht(int i, String geslacht) {
        spel.geefSpelerGeslacht(i, geslacht);
    }

    /**
     *
     * geeft de speler zijn startkaarten
     */
    public void geefStartKaarten() {
        spel.geefStartKaarten();
    }

    /**
     *
     * @return bovenste kerkerkaart
     */
    public String toonBovensteKk() {
        return String.format("gui/images/%d.png", spel.toonBovensteKk());
    }

    /**
     *
     * @param id
     * @return type van de kaart met bepaald id
     */
    public String geefTypeKaart(int id) {
        return spel.geefTypeKaart(id);
    }

    /**
     *
     * @param naam van vloekkaart die gespeeld wordt
     */
    public void curseKaart(String naam) {
        spel.speelCurse(naam);
    }

    /**
     *
     * @return id van de bovenste kaart op de stapel vd kerkerkaarten
     */
    public int geefIdBovensteKaart() {
        return spel.toonBovensteKk();
    }

    /**
     *
     * plaatst een nieuwe kerkerkaart bovenop de stapel
     */
    public void nieuweBovensteKaartK() {
        spel.nieuwBovensteKaartK();
    }

    /**
     * geeft de kerkerkaart die random geselecteerd wordt (shuffle) aan de speler
     * @param naam 
     */
    public void geefKerkerkaartAanSpeler(String naam) {
        spel.geefKerkerkaartAanSpeler(naam);
    }
/**

    public String geefTypeLostCurse() {
        return spel.geefTypeLostCurse().toLowerCase();
    }

    public String toonItemsSpeler(String naam) {
        int i = 0;
        String ret = "";
        for (String line : spel.geefItemsSpeler(naam)){
            ret += String.format("%d) %s%n", i,line);
            i++;
        }
        return ret;
    }

    public int geefAantalItemsSpeler(String naam) {
        if (spel.geefAantalItemsSpeler(naam) == -99){
            throw new SpelException("exception.spel.itemsspeler");
        }
        return spel.geefAantalItemsSpeler(naam);
    }

    public void verwijderItemSpeler(String naam, int keuze) {
        spel.verwijderItemSpeler(naam, keuze-1);
    }
*/

    /**
     * Geeft het spel een naam
     * @param naam 
     */
    public void geefSpelNaam(String naam){
        spel.setNaam(naam);
    }

    /**
     * 
     * @param naam
     * @return boolean op true wanneer speler te veel kaarten heeft
     */
    public boolean spelerTeVeelKaarten(String naam) {
        return spel.spelerTeVeelKaarten(naam);
    }
}
