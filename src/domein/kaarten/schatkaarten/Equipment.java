package domein.kaarten.schatkaarten;

import domein.kaarten.Schatkaart;
import domein.kaarten.kerkerkaarten.Race;
import exceptions.kaarten.schatkaarten.EquipmentException;

/**
 * @author ziggy
 */
@SuppressWarnings("unused")
public class Equipment extends Schatkaart {

    private final int bonus;
    private Race usableBy;
    private String text;
    private int runAway;
    private final String type;
    private int specialBonus;
    private Race specialRace;

    private enum TYPES {
        head, armor, foot, weapon
    }

    /**
     * Constructor schatkaart equipment (die superklasse Schatkaart gebruikt)
     *
     * @param naam         = name, De naam van de kaart
     * @param id           = id,
     * @param waarde       = goldPieces, De waarde van de kaart in het spel
     * @param type         = type,
     * @param bonus        = bonus,
     * @param race         = usableBy,
     * @param runAway      = escapeBonus,
     * @param specialBonus = bonusrace,
     * @param specialRace  = specialRace,
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
     * getter
     *
     * @return bonus van equipment
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * getter
     *
     * @return race dat equipment kan gebruiken
     */
    public Race getUsableBy() {
        return usableBy;
    }

    /**
     * getter
     *
     * @return bijhorende tekst
     */
    public String getText() {
        return text;
    }

    /**
     * getter
     *
     * @return kan weglopen
     */
    public int isRunAway() {
        return runAway;
    }

    /**
     * getter
     *
     * @return het type van het equipment
     */
    public String getType() {
        return type;
    }

    /**
     * getter
     *
     * @return de special bonus voor special race
     */
    public int getSpecialBonus() {
        return specialBonus;
    }

    /**
     * getter
     *
     * @return de special race voor de special bonus
     */
    public Race getSpecialRace() {
        return specialRace;
    }

    /**
     * setter usably by met controle niet null
     *
     * @param usableBy race dat equipment kan gebruiken
     */
    private void setUsableBy(Race usableBy) {
        if (usableBy == null) {
            throw new EquipmentException("exception.equipment.usableby");
        }
        this.usableBy = usableBy;
    }


    /**
     * setter voor ranaway met controle groter dan 0
     *
     * @param runAway runaway waarde
     */
    private void setRunAway(int runAway) {
        if (runAway < 0) {
            throw new EquipmentException("exception.equipment.runaway");
        }
        this.runAway = runAway;
    }

    /**
     * setter special bonus met controle groter dan 0
     *
     * @param specialBonus de speciale bonus int
     */
    private void setSpecialBonus(int specialBonus) {
        if (specialBonus < 0) {
            throw new EquipmentException("exception.equipment.specialbonus");
        }
        this.specialBonus = specialBonus;

    }

    /**
     * setter voor special race met conrole != null
     *
     * @param specialRace special race voor special bonus
     */
    private void setSpecialRace(Race specialRace) {
        if (specialRace == null) {
            throw new EquipmentException("exception.equipment.specialrace");
        }
        this.specialRace = specialRace;
    }

    /**
     * contorle voor bonus groter dan 0
     *
     * @param bonus de bonus-waarde
     */
    private void controleerBonus(int bonus) {
        if (bonus < 0) {
            throw new EquipmentException("exception.equipment.bonus");
        }
    }

    /**
     * controle van type
     *
     * @param type het type van de kaart
     */
    @SuppressWarnings("ConstantConditions")
    private void controleerType(String type) {
        if (TYPES.valueOf(type.toLowerCase()) == null) {
            throw new EquipmentException("exception.equipment.type");
        }
    }

    /**
     * tostring methode
     *
     * @return geformateerde string
     */
    @Override
    public String toString() {
        return String.format("name = %s, id = %d, value = %d, type = %s, bonus = %d", getNaam(), getId(), getWaarde(), getType(), getBonus());
    }
}
