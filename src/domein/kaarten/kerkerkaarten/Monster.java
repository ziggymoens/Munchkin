package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.MonsterException;
import language.LanguageResource;

/**
 *
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
    private String specialRaceReason;
    private int notPursue;
    private int levelsLostHigherLevel;

    /**
     *
     * @param naam van de speler
     * @param id
     * @param level van de speler
     * @param winstTeasures aantal schatkaarten verkregen door winst
     * @param winstLevels levels verkregen door
     * @param text
     * @param outRun
     * @param runAway
     * @param specialRace
     * @param specialRaceExtra
     * @param specialRaceReason
     * @param notPursue
     * @param levelsLostHigherLevel
     * @param badStuff bijhorende aan de kaart
     */
    public Monster(String naam, int id, int level, int winstTeasures, int winstLevels, String text, boolean outRun, int runAway, Race specialRace, int specialRaceExtra, String specialRaceReason, int notPursue, int levelsLostHigherLevel, BadStuff badStuff) {
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
        setSpecialRaceReason(specialRaceReason);
        setNotPursue(notPursue);
        setLevelsLostHigherLevel(levelsLostHigherLevel);
        this.badStuff = badStuff;
    }

    /**
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return
     */
    public BadStuff getBadStuff() {
        return badStuff;
    }

    /**
     *
     * @return
     */
    public int getWinstTeasures() {
        return winstTeasures;
    }

    /**
     *
     * @return
     */
    public int getWinstLevels() {
        return winstLevels;
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
    public boolean isOutRun() {
        return outRun;
    }

    /**
     *
     * @return
     */
    public int getRunAway() {
        return runAway;
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
     * @return
     */
    public int getSpecialRaceExtra() {
        return specialRaceExtra;
    }

    /**
     *
     * @return
     */
    public String getSpecialRaceReason() {
        return specialRaceReason;
    }

    /**
     *
     * @return
     */
    public int getNotPursue() {
        return notPursue;
    }

    /**
     *
     * @return
     */
    public int getLevelsLostHigherLevel() {
        return levelsLostHigherLevel;
    }

    /**
     *
     * @param text
     */
    private void setText(String text) {
        if (text == null || text.isBlank()) {
            throw new MonsterException("exception.monster.text");
        }
        this.text = text;
    }

    /**
     *
     * @param outRun
     */
    private void setOutRun(boolean outRun) {
        this.outRun = outRun;
    }

    /**
     *
     * @param runAway
     */
    private void setRunAway(int runAway) {
        if (runAway < 0) {
            throw new MonsterException("exception.monster.runawayint");
        }
        this.runAway = runAway;
    }

    /**
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
     *
     * @param specialRaceReason
     */
    private void setSpecialRaceReason(String specialRaceReason) {
        if (specialRaceReason == null || specialRaceReason.isBlank()) {
            throw new MonsterException("exception.monster.specialracereason");
        }
        this.specialRaceReason = specialRaceReason;
    }

    /**
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
     *
     * @param levelsLostHigherLevel
     */
    private void setLevelsLostHigherLevel(int levelsLostHigherLevel) {
        if (levelsLostHigherLevel < 0) {
            throw new MonsterException("exception.monster.levelslosthigherlevel");
        }
        this.levelsLostHigherLevel = levelsLostHigherLevel;
    }

    /**
     *
     * @param level
     */
    private void controleerLevel(int level) {
        if (level < 0) {
            throw new MonsterException("exception.monster.level");
        }
    }

    /**
     *
     * @param winstTeasures
     */
    private void controleerTreasures(int winstTeasures) {
        if (winstTeasures < 0) {
            throw new MonsterException("exception.monster.treasures");
        }
    }

    /**
     *
     * @param winstLevels
     */
    private void controleerLevels(int winstLevels) {
        if (winstLevels < 0) {
            throw new MonsterException("exception.monster.levelsw");
        }
    }

}
/**
 * // * // * @param naam van de speler // * @param level van de speler //
 *
 *
 * @param winstTeasures aantal schatkaarten verkregen door winst // * @param
 * winstLevels levels omhoog door winst // * @param badStuff bijhorende aan de
 * kaart //
 */
//    public Monster(String naam, int level, int winstTeasures, int winstLevels, BadStuff badStuff) {
//        super(naam);
//        this.level = level;
//        this.winstTeasures = winstTeasures;
//        this.winstLevels = winstLevels;
//        this.badStuff = badStuff;
//    }
//
//    /**
//     *
//     * @param naam van de speler
//     * @param level van de speler
//     * @param winstTeasures aantal schatkaarten verkregen door winst
//     * @param winstLevels levels omhoog door winst
//     * @param specialRace ras waarvoor andere voorwaarden gelden
//     * @param specialRaceExtra extra effect voor specialRace
//     * @param specialRaceReason reden voor extra effect
//     * @param badStuff bijhorende aan de kaart
//     */
//    public Monster(String naam, int level, int winstTeasures, int winstLevels, Race specialRace, int specialRaceExtra, String specialRaceReason, BadStuff badStuff) {
//        super(naam);
//        this.level = level;
//        this.winstTeasures = winstTeasures;
//        this.winstLevels = winstLevels;
//        setSpecialRace(specialRace);
//        setSpecialRaceExtra(specialRaceExtra);
//        setSpecialRaceReason(specialRaceReason);
//        this.badStuff = badStuff;
//    }
//
//    /**
//     *
//     * @param naam
//     * @param level
//     * @param winstTeasures
//     * @param winstLevels
//     * @param text
//     * @param notPursue
//     * @param badStuff
//     */
//    public Monster(String naam, int level, int winstTeasures, int winstLevels, String text, int notPursue, BadStuff badStuff) {
//        super(naam);
//        this.level = level;
//        this.winstTeasures = winstTeasures;
//        this.winstLevels = winstLevels;
//        setText(text);
//        setNotPursue(notPursue);
//        this.badStuff = badStuff;
//    }
