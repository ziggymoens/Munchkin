package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.RaceException;
import language.LanguageResource;

/**
 *
 * @author ziggy
 */
public class Race extends Kerkerkaart {

    private boolean extraWapen;
    private boolean doublePrice;
    private boolean extraRunAway;
    private final String type;

    private enum TYPES {
        elf, dwarf, halfling
    }; // nog?

    /**
     * Constructor voor kerkerkaart ras (die superklasse Kerkerkaart gebruikt)
     *
     * @param type
     */
    public Race(String type) {
        super(type);
        controleerType(type);
        this.type = type;
        setExtras();
    }

    /**
     * switch om te rassen te setten
     * @param naam
     */
    private void setExtras() {
        switch (type.toLowerCase()) {
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
    
    /**
     * 
     * @param type 
     */
    private void controleerType(String type){
        if(TYPES.valueOf(type) == null)
            throw new RaceException(LanguageResource.getString("Race.exception"));
    }
}
