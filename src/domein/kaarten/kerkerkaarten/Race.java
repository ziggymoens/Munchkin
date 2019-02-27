package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;

/**
 *
 * @author ziggy
 */
public class Race extends Kerkerkaart {

    private boolean extraWapen;
    private boolean doublePrice;
    private boolean extraRunAway;

    /**
     * Constructor voor kerkerkaart ras (die superklasse Kerkerkaart gebruikt)
     *
     * @param naam
     */
    public Race(String naam) {
        super(naam);
        setExtras(naam);

    }

    /**
     *
     * @param naam
     */
    private void setExtras(String naam) {
        char n = naam.toLowerCase().charAt(0);
        switch (naam) {
            case "dwarf":
                extraWapen = true;
                break;
            case "halfling":
                doublePrice = true;
                break;
            case "elf":
                extraRunAway = true;
            case "human":
            default:
                break;
        }
    }

    /**
     *
     * @return
     */
    public boolean isExtraWapen() {
        return extraWapen;
    }

    /**
     *
     * @return
     */
    public boolean isDoublePrice() {
        return doublePrice;
    }

    /**
     *
     * @return
     */
    public boolean isExtraRunAway() {
        return extraRunAway;
    }
}
