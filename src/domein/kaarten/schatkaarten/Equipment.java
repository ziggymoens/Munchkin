package domein.kaarten.schatkaarten;

import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.Schatkaart;

/**
 *
 * @author ziggy
 */
public class Equipment extends Schatkaart {

    private final int bonus;
    private Race usableBy;
    private String text;
    private int runAway;
    private final String type;
    private int specialBonus;
    private Race specialRace;

    /**
     * Constructor schatkaart equipment (die superklasse Schatkaart gebruikt)
     *
     * @param naam De naam van de kaart
     * @param waarde De waarde van de kaart in het spel
     * @param type
     * @param bonus
     * @param race
     * @param text
     * @param runAway
     * @param specialBonus
     * @param specialRace
     */
    public Equipment(String naam, int waarde, String type, int bonus, Race race, String text, int runAway, int specialBonus, Race specialRace) {
        super(naam, waarde);
        this.bonus = bonus;
        this.type = type;
        this.usableBy = race;
        this.text = text;
        this.runAway = runAway;
        this.specialBonus = specialBonus;
        this.specialRace = specialRace;
    }

    /**
     *
     * @param naam
     * @param waarde
     * @param type
     * @param bonus
     */
    public Equipment(String naam, int waarde, String type, int bonus) {
        super(naam, waarde);
        this.bonus = bonus;
        this.type = type;
    }

    /**
     *
     * @param naam
     * @param waarde
     * @param type
     * @param bonus
     * @param race
     */
    public Equipment(String naam, int waarde, String type, int bonus, Race race) {
        super(naam, waarde);
        this.bonus = bonus;
        this.type = type;
        this.usableBy = race;
    }

    /**
     *
     * @param naam
     * @param waarde
     * @param type
     * @param bonus
     * @param specialBonus
     * @param specialRace
     */
    public Equipment(String naam, int waarde, String type, int bonus, int specialBonus, Race specialRace) {
        super(naam, waarde);
        this.bonus = bonus;
        this.type = type;
        this.specialBonus = specialBonus;
        this.specialRace = specialRace;
    }

    /**
     *
     * @param naam
     * @param waarde
     * @param type
     * @param bonus
     * @param runAway
     */
    public Equipment(String naam, int waarde, String type, int bonus, int runAway) {
        super(naam, waarde);
        this.bonus = bonus;
        this.runAway = runAway;
        this.type = type;
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
    public Race getUsableBy() {
        return usableBy;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @return
     */
    public int isRunAway() {
        return runAway;
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
    public int getSpecialBonus() {
        return specialBonus;
    }

    /**
     *
     * @return
     */
    public Race getSpecialRace() {
        return specialRace;
    }
}
