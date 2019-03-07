package domein.kaarten;

import exceptions.kaarten.KaartException;

/**
 *
 * @author ziggy
 */
//SUPERKLASSE
public abstract class Kaart {

    //Declaratie attributen
    private String naam;
    private int id;
    private boolean kaartInGebruik;

    /**
     * Constructor kaart (superklasse van Schatkaart en Kerkerkaart)
     *
     * @param naam
     * @param id
     */
    public Kaart(String naam, int id) {
        setNaam(naam);
        setId(id);
    }

    /**
     * Setter voor de naam van de kaart
     *
     * @param naam de naam van de kaart
     */
    private void setNaam(String naam) {
        if (naam == null || naam.isBlank()) {
            throw new KaartException("exception.kaart.name");
        }
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

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    private void setId(int id) {
        if ((id < 1 || id > 110 )&& id != 999) {
            throw new KaartException("exception.kaart.id");
        }
        this.id = id;
    }

}
