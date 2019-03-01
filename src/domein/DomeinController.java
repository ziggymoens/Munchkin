package domein;

/**
 *
 * @author ziggy
 */
public class DomeinController {

    private Spel spel;

    /**
     * Constructor DomeinController
     */
    public DomeinController() {
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
     * Methode om een speler toete voegen adhv een naam, geslacht (deze worden
     * verder gecontroleerd volgens de DR)
     *
     * @param naam Naam van de speler
     * @param geslacht Geslacht van de speler
     */
    public void voegSpelerToe(String naam, String geslacht) {
        spel.voegSpelerToe(naam, geslacht);
    }

    /**
     * String die info geeft over het spel en de spelers
     *
     * @return String met alle informatie
     */
    public String geefInformatie() {
        String ret = "";
        String[] sInfo = spel.geefInfo();
        for (String lijn : sInfo) {
            ret += String.format("%s%n", lijn);
        }
        return ret;
    }

    /**
     *
     * @return
     */
    public static int geefAantalSpelers() {
        return Spel.getAantalSpelers();
    }

    /**
     *
     */
    public void speelSpel() {
        spel.speelSpel();
    }
}
