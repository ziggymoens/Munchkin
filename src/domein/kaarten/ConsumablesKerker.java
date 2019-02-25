package domein.kaarten;

/**
 *
 * @author ziggy
 */
public class ConsumablesKerker extends Kerkerkaart {

    private final int bonus;
    private final String text = "Play during combat";

    /**
     * Constructor van kerkerkaarten consumables (die superklasse Kerkerkaart
     * gebruikt)
     *
     * @param naam Naam van de kaart
     * @param bonus
     */
    public ConsumablesKerker(String naam, int bonus) {
        super(naam);
        this.bonus = bonus;
    }

    /**
     *
     * @return
     */
    public int getBonus() {
        return bonus;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return text;
    }
}
