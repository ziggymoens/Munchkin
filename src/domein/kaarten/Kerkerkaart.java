package domein.kaarten;

/**
 *
 * @author ziggy
 */
public abstract class Kerkerkaart extends Kaart {

    /**
     * Constructor kerkerkaart (die superklasse Kaart gebruikt)
     *
     * @param naam de naam van de kaart
     * @param id
     */
    public Kerkerkaart(String naam, int id) {
        super(naam, id);
    }

}
