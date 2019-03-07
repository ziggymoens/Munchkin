package domein.kaarten;

import exceptions.kaarten.SchatkaartException;

/**
 *
 * @author ziggy
 */
public abstract class Schatkaart extends Kaart {

    private final int waarde;

    /**
     * Constructor voor klasse schatkaart (die superklasse kaart gebruikt)
     *
     * @param naam de naam van de kaart
     * @param id id van de kaart, uniek nummer
     * @param waarde de waarde van de kaart
     */
    public Schatkaart(String naam, int id, int waarde) {
        super(naam, id);
        controleerWaarde(waarde);
        this.waarde = waarde;
    }

    /**
     * Geeft de waarde van de kaart terug
     *
     * @return waarde kaart
     */
    public int getWaarde() {
        return waarde;
    }

    /**
     *
     * @param waarde
     */
    private void controleerWaarde(int waarde) {
        if (waarde < 0) {
            throw new SchatkaartException("exception.schatkaart.value");
        }
    }

}
