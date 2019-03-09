package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.RaceException;

/**
 *
 * @author ziggy
 */
public class Race extends Kerkerkaart {

    private boolean extraWapen = false;
    private boolean doublePrice = false;
    private boolean extraRunAway = false;
    private final String type;
    private String text;

    private enum TYPES {
        elf, dwarf, halfling, human, everyone, none, female, male
    }; //NAKIJKEN

    public Race(String naam){
        this(naam, 999, "...");
    }
    
    /**
     * Constructor voor kerkerkaart ras (die superklasse Kerkerkaart gebruikt)
     *
     * @param type
     * @param id
     * @param text
     */
    public Race(String type, int id, String text) {
        super(type, id);
        controleerType(type);
        this.type = type;
        setExtras();
        setText(text);
    }

    /**
     * switch om te rassen te setten
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
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return text;
    }

    /**
     *
     * @param text
     */
    private void setText(String text) {
        if (text == null || text.isBlank()) {
            throw new RaceException("exception.race.text");
        }
        this.text = text;
    }

    /**
     *
     * @param type
     */
    private void controleerType(String type) {
        if (TYPES.valueOf(type.toLowerCase()) == null) {
            throw new RaceException("exception.race.type");
        }
    }
}
//    public Race(String name, String description) {
//        super(name);
//        controleerType(name);
//        this.description = description;
//        this.type = name;
//        setExtras();
//    }
