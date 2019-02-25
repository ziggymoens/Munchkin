package domein.kaarten;

/**
 *
 * @author ziggy
 */
//SUPERKLASSE
public abstract class Kaart {

    //Declaratie attributen
    private String naam;
    private boolean kaartInGebruik;

    /**
     * Constructor kaart (superklasse van Schatkaart en Kerkerkaart)
     *
     * @param naam
     */
    public Kaart(String naam) {
        setNaam(naam);
    }

    /**
     * Setter voor de naam van de kaart
     *
     * @param naam de naam van de kaart
     */
    public final void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * Geeft de naam van de kaart terug
     *
     * @return de naam van de kaart
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geeft terug of de kaart in iemand zijn hand is
     *
     * @return Boolean true = ingebruik, false = beschikbaar
     */
    public boolean isKaartInGebruik() {
        return kaartInGebruik;
    }

    /**
     * Setter die gebruikt wordt om kaartInGebruik op true of false te setten
     *
     * @param kaartInGebruik true = kaart in gebruik, false = kaart is
     * beschikbaar
     */
    public final void setKaartInGebruik(boolean kaartInGebruik) {
        this.kaartInGebruik = kaartInGebruik;
    }
}
