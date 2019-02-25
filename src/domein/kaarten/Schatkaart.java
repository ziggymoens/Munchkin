package domein.kaarten;

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
     * @param waarde de waarde van de kaart
     */
    public Schatkaart(String naam, int waarde) {
        super(naam);
        this.waarde = waarde;
    }
    
    /**
     * Geeft de waarde van de kaart terug
     * @return waarde kaart
     */
    public int getWaarde() {
        return waarde;
    }
}
