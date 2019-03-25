package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import domein.kaarten.kerkerkaarten.monsterbadstuff.BadStuff;
import exceptions.kaarten.kerkerkaarten.MonsterException;

/**
 * @author ziggy
 */
public class Monster extends Kerkerkaart {

    private final int level;
    private final BadStuff badStuff;
    private final int winstTeasures;
    private final int winstLevels;
    private String text;
    private boolean outRun;
    private int runAway;
    private Race specialRace;
    private int specialRaceExtra;
    private int notPursue;

    /**
     * Constructor van Monster (die superklasse Kerkerkaart gebruikt)
     *
     * GROTE DB
     *
     * @param naam             = name, van de speler
     * @param id               = id,
     * @param level            = level, van de speler
     * @param winstTeasures    = treasures, aantal schatkaarten verkregen door winst
     * @param winstLevels      = levelUp, levels verkregen door
     * @param text             = description,
     * @param outRun           = outRun,
     * @param runAway          = escapeBonus,
     * @param specialRace      = specialRace,
     * @param specialRaceExtra = raceBonus,
     * @param notPursue        = pursueLevel,
     * @param badStuff         = badStuffId, bijhorende aan de kaart
     */
    public Monster(String naam, int id, int level, int winstTeasures, int winstLevels, String text, boolean outRun, int runAway, Race specialRace, int specialRaceExtra, int notPursue, BadStuff badStuff) {
        super(naam, id);
        controleerLevel(level);
        this.level = level;
        controleerTreasures(winstTeasures);
        this.winstTeasures = winstTeasures;
        controleerLevels(winstLevels);
        this.winstLevels = winstLevels;
        setText(text);
        setOutRun(outRun);
        setRunAway(runAway);
        setSpecialRace(specialRace);
        setSpecialRaceExtra(specialRaceExtra);
        setNotPursue(notPursue);
        controleerBadstuff(badStuff);
        this.badStuff = badStuff;
    }

    /**
     * Constructor voor monster
     *
     * KLEINE DB
     *
     * @param name
     * @param id
     * @param level
     * @param tresures
     * @param description
     * @param escapeBonus
     * @param race
     * @param raceBonus
     * @param bs
     */
    public Monster(String name, int id, int level, int tresures, String description, int escapeBonus, Race race, int raceBonus, BadStuff bs) {
        super(name, id);
        controleerLevel(level);
        this.level = level;
        controleerTreasures(tresures);
        this.winstTeasures = tresures;
        this.winstLevels = 1;
        setText(description);
        controleerBadstuff(bs);
        setSpecialRace(race);
        setSpecialRaceExtra(raceBonus);
        setRunAway(escapeBonus);
        this.badStuff=bs;
    }

    /**
     * Getter die level teruggeeft
     *
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Getter die bijhorende badstuff van een kaart geeft
     *
     * @return badStuff
     */
    public BadStuff getBadStuff() {
        return badStuff;
    }

    /**
     * Getter die aantal schatkaarten bij winst teruggeeft
     *
     * @return winstTreasures
     */
    public int getWinstTeasures() {
        return winstTeasures;
    }

    /**
     * Getter die winstLevels van bepaald monster zal weergeven
     *
     * @return winstLevels
     */
    public int getWinstLevels() {
        return winstLevels;
    }

    /**
     * Getter die tekst zal weergeven
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Boolean die aanduidt of weglopen succesvol was
     *
     * @return boolean = true: weglopen gelukt, boolean = false: weglopen niet gelukt
     */
    public boolean isOutRun() {
        return outRun;
    }

    /**
     * @return runAway
     */
    public int getRunAway() {
        return runAway;
    }

    /**
     * @return specialRace
     */
    public Race getSpecialRace() {
        return specialRace;
    }

    /**
     * @return specialRaceExtra
     */
    public int getSpecialRaceExtra() {
        return specialRaceExtra;
    }


    /**
     * @return notPursue
     */
    public int getNotPursue() {
        return notPursue;
    }


    /**
     * Setter die controleert of tekst niet leeg is
     *
     * @param text
     */
    private void setText(String text) {
        if (text == null) {
            throw new MonsterException("exception.monster.text");
        }
        this.text = text;
    }

    /**
     * Setter die beslist of outRun boolean op true of false wordt gezet
     *
     * @param outRun
     */
    private void setOutRun(boolean outRun) {
        this.outRun = outRun;
    }

    /**
     * Setter die runAway controleert en instelt
     *
     * @param runAway
     */
    private void setRunAway(int runAway) {
        if (runAway > 10 || runAway < -10) {
            throw new MonsterException("exception.monster.runawayint");
        }
        this.runAway = runAway;
    }

    /**
     * Setter die specialRace instelt als deze niet leeg is
     *
     * @param specialRace
     */
    private void setSpecialRace(Race specialRace) {
        if (specialRace == null) {
            throw new MonsterException("exception.monster.specialrace");
        }
        this.specialRace = specialRace;
    }

    /**
     * Setter die specialRaceExtra instelt als deze groter is dan 0
     *
     * @param specialRaceExtra
     */
    private void setSpecialRaceExtra(int specialRaceExtra) {
        if (specialRaceExtra < 0) {
            throw new MonsterException("exception.monster.specialraceextra");
        }
        this.specialRaceExtra = specialRaceExtra;
    }

    /**
     * Setter die notPursue instelt als deze groter is dan 0
     *
     * @param notPursue
     */
    private void setNotPursue(int notPursue) {
        if (notPursue < 0) {
            throw new MonsterException("exception.monster.notpursue");
        }
        this.notPursue = notPursue;
    }

    /**
     * Controle op (aantal) level(s) van de speler
     *
     * @param level
     */
    private void controleerLevel(int level) {
        if (level < 0) {
            throw new MonsterException("exception.monster.level");
        }
    }

    /**
     * Controle op aantal schatten
     *
     * @param winstTeasures
     */
    private void controleerTreasures(int winstTeasures) {
        if (winstTeasures < 0) {
            throw new MonsterException("exception.monster.treasures");
        }
    }

    /**
     * Controle op (aantal) levels die men verkrijgt door te winnen
     *
     * @param winstLevels
     */
    private void controleerLevels(int winstLevels) {
        if (winstLevels < 0) {
            throw new MonsterException("exception.monster.levelsw");
        }
    }

    /**
     * Controle op badstuff, exception wordt geworpen wanneer deze leeg is
     *
     * @param badStuff
     */
    private void controleerBadstuff(BadStuff badStuff) {
        if (badStuff == null) {
            throw new MonsterException("exception.monster.badstuff");
        }
    }


    @Override
    public String toString() {
        return String.format("name = %s%s, level = %d%s%s, %s", getNaam(), text.isEmpty() ? "" : ", " + getText(), getLevel(), runAway == 0 ? "" : String.format(", +%d to run away", runAway), specialRaceExtra == 0 ? "" : String.format(", +%d against %s", specialRaceExtra, specialRace.getType()), badStuff.toString());
    }

}
