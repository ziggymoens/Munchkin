package domein.kaarten.schatkaarten;

import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.Schatkaart;
import exceptions.kaarten.schatkaarten.EquipmentException;

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
    
    private enum TYPES{
        head, armor, foot, weapon //eventueel aanvullen
    }

    /**
     * Constructor schatkaart equipment (die superklasse Schatkaart gebruikt)
     *
     * @param naam = name, De naam van de kaart
     * @param id = id,
     * @param waarde = goldPieces, De waarde van de kaart in het spel
     * @param type = type,
     * @param bonus = bonus,
     * @param race = usableBy,
     * @param runAway = escapeBonus,
     * @param specialBonus = bonusrace,
     * @param specialRace = specialRace,
     */
    public Equipment(String naam, int id, int waarde, String type, int bonus, Race race, int runAway, int specialBonus, Race specialRace) {
        super(naam, id, waarde);
        controleerBonus(bonus);
        this.bonus = bonus;
        controleerType(type);
        this.type = type;
        setUsableBy(race);
        setRunAway(runAway);
        setSpecialBonus(specialBonus);
        setSpecialRace(specialRace);
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

    /**
     *
     * @param usableBy
     */
    private void setUsableBy(Race usableBy) {
        if (usableBy == null) {
            throw new EquipmentException("exception.equipment.usableby");
        }
        this.usableBy = usableBy;
    }


    /**
     *
     * @param runAway
     */
    private void setRunAway(int runAway) {
        if (runAway<0) {
            throw new EquipmentException("exception.equipment.runaway");
        }
        this.runAway = runAway;
    }

    /**
     *
     * @param specialBonus
     */
    private void setSpecialBonus(int specialBonus) {
        if (specialBonus < 0) {
            throw new EquipmentException("exception.equipment.specialbonus");
        }
        this.specialBonus = specialBonus;

    }

    /**
     *
     * @param specialRace
     */
    private void setSpecialRace(Race specialRace) {
        if (specialRace == null) {
            throw new EquipmentException("exception.equipment.specialrace");
        }
        this.specialRace = specialRace;
    }

    /**
     *
     * @param bonus
     */
    private void controleerBonus(int bonus) {
        if (bonus < 0) {
            throw new EquipmentException("exception.equipment.bonus");
        }
    }

    /**
     *
     * @param type
     */
    private void controleerType(String type) {
        if(TYPES.valueOf(type.toLowerCase()) == null){
            throw new EquipmentException("exception.equipment.type");
        }
    }

}
