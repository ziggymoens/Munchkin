package domein.kaarten;

import exceptions.kaarten.KaartException;

import java.util.Objects;

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

    @Override
    public abstract String toString();

    /**
     * Setter voor de naam van de kaart met controle
     *
     * @param naam de naam van de kaart
     */
    private void setNaam(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
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
     * Geeft het id van de kaart terug
     * 
     * @return het id van de kaart
     */
    public int getId() {
        return id;
    }

    /**
     * Setter die gebruikt wordt om het id van een kaart toe te wijzen via de db
     * 
     * @param id
     */
    private void setId(int id) {
        if ((id < 1 || id > 110 )&& id != 999) {
            throw new KaartException("exception.kaart.id");
        }
        this.id = id;
    }

    /**
     * equalsmethode om kaart te vergelijken - niet meer nodig om ids te vergelijken
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kaart kaart = (Kaart) o;
        return id == kaart.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
