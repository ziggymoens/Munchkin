package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.kerkerkaarten.RaceException;

/**
 * @author ziggy
 */
public class Race extends Kerkerkaart {

    private boolean extraWapen = false;
    private boolean doublePrice = false;
    private boolean extraRunAway = false;
    private int bonusCombat = 0;
    private int monsterCombat = 0;
    private int runAway = 0;
    private final String type;
    private String text;


    private enum TYPES {
        elf, dwarf, halfling, human, everyone, none, female, male
    } //NAKIJKEN

    public Race(String naam) {
        this(naam, 999, "+1 to Run Away");
    }

    /**
     * Constructor voor kerkerkaart ras
     * <p>
     * KLEINE DB
     *
     * @param type
     * @param id
     * @param combatBonus
     * @param runaway
     */
    public Race(String type, int id, int combatBonus, int runaway) {
        super(type, id);
        this.type = type;
        switch (type) {
            case "dwarf":
                setBonusCombat(combatBonus);
                break;
            case "elf":
                setRunAway(runaway);
                break;
            case "halfling":
                setMonsterCombat(combatBonus);
                break;
        }
    }

    /**
     * Constructor voor kerkerkaart ras (die superklasse Kerkerkaart gebruikt)
     * <p>
     * GROTE DB
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
     * Boolean die beslist of het een extra wapen is of niet
     *
     * @return true = extra wapen, false = geen extra wapen
     */
    public boolean isExtraWapen() {
        return extraWapen;
    }

    /**
     * Boolean die beslist of het dubbele prijs is of niet
     *
     * @return true = doublePrice, false = geen dubblePrice
     */
    public boolean isDoublePrice() {
        return doublePrice;
    }

    /**
     * Boolean die beslist of het extra runaway is of niet
     *
     * @return true = isExtraRunAway, false = geen isExtraRunAway
     */
    public boolean isExtraRunAway() {
        return extraRunAway;
    }

    /**
     * Getter die het type teruggeeft
     *
     * @return String van het type
     */
    public String getType() {
        return type;
    }

    /**
     * Getter die de description teruggeeft
     *
     * @return String van de description
     */
    public String getDescription() {
        return text;
    }

    /**
     * Setter die text initialiseert wanneer deze niet null of leeg is
     *
     * @param text
     */
    private void setText(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new RaceException("exception.race.text");
        }
        this.text = text;
    }

    /**
     * Controle ofdat het type niet null is
     *
     * @param type
     */
    private void controleerType(String type) {
        if (TYPES.valueOf(type.toLowerCase()) == null) {
            throw new RaceException("exception.race.type");
        }
    }

    public void setBonusCombat(int bonusCombat) {
        if (bonusCombat < 0) {
            throw new RaceException("exception.race.bonuscombat");
        }
        this.bonusCombat = bonusCombat;
    }

    public void setMonsterCombat(int monsterCombat) {
        if (monsterCombat < 0) {
            throw new RaceException("exception.race.monstercombat");
        }
        this.monsterCombat = monsterCombat;
    }

    public void setRunAway(int runAway) {
        if (runAway < 0) {
            throw new RaceException("exception.race.runaway");
        }
        this.runAway = runAway;
    }

    public int getBonusCombat(){
        return bonusCombat;
    }

    private String getTekstToString() {
        String ret = "";
        switch (type.toLowerCase()) {
            case "dwarf":
                ret = String.format("+%d bonus during combat", bonusCombat);
                break;
            case "elf":
                ret = String.format("%d to run away", runAway);
                break;
            case "halfling":
                ret = String.format("-%d moster buring combat", monsterCombat);
                break;
            default:
                ret = "error";
        }
        return ret;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", getNaam(), getTekstToString());
    }


}
