package domein;

import ongebruikt.SpelRepository;

import java.util.List;

import exceptions.SpelException;
import language.LanguageResource;

/**
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
     * Getter die het aantal spelers returnt
     * 
     * @return aantal spelers
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
     * @param naam naam van de speler die de beurt gaat spelen
     */
    public void speelBeurt(String naam) {
        //String typeOfCard = getType();
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

    public String toonBovensteKk() {
        return String.format("gui/images/%d.png", spel.toonBovensteKk());
    }

    public String geefTypeKaart(int id) {
        return spel.geefTypeKaart(id);
    }

    public void effectKerkerkaart(String naam) {
        spel.speelKerkerkaart(naam);
    }

    public int geefIdBovensteKaart() {
        return spel.toonBovensteKk();
    }

    public void nieuweBovensteKaartK() {
        spel.nieuwBovensteKaartK();
    }

    public void geefKerkerkaartAanSpeler(String naam) {
        spel.geefKerkerkaartAanSpeler(naam);
    }

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

    public void geefSpelNaam(String naam){
        spel.geefSpelNaam(naam);
    }
}
