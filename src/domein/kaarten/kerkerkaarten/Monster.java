package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;

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
     * @param naam
     * @param level
     * @param winstTeasures
     * @param winstLevels
     * @param text
     * @param outRun
     * @param runAway
     * @param specialRace
     * @param specialRaceExtra
     * @param specialRaceReason
     * @param notPursue
     * @param levelsLostHigherLevel
     * @param badStuff
     */
    public Monster(String naam, int level, int winstTeasures, int winstLevels, String text, boolean outRun, int runAway, Race specialRace, int specialRaceExtra, String specialRaceReason, int notPursue, int levelsLostHigherLevel, BadStuff badStuff) {
        super(naam);
        this.level = level;
        this.winstTeasures = winstTeasures;
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
     * @param naam
     * @param level
     * @param winstTeasures
     * @param winstLevels
     * @param badStuff
     */
    public Monster(String naam, int level, int winstTeasures, int winstLevels, BadStuff badStuff) {
        super(naam);
        this.level = level;
        this.winstTeasures = winstTeasures;
        this.winstLevels = winstLevels;
        this.badStuff = badStuff;
    }

    /**
     *
     * @param naam
     * @param level
     * @param winstTeasures
     * @param winstLevels
     * @param specialRace
     * @param specialRaceExtra
     * @param specialRaceReason
     * @param badStuff
     */
    public Monster(String naam, int level, int winstTeasures, int winstLevels, Race specialRace, int specialRaceExtra, String specialRaceReason, BadStuff badStuff) {
        super(naam);
        this.level = level;
        this.winstTeasures = winstTeasures;
        this.winstLevels = winstLevels;
        setSpecialRace(specialRace);
        setSpecialRaceExtra(specialRaceExtra);
        setSpecialRaceReason(specialRaceReason);
        this.badStuff = badStuff;
    }

    /**
     *
     * @param naam
     * @param level
     * @param winstTeasures
     * @param winstLevels
     * @param text
     * @param notPursue
     * @param badStuff
     */
    public Monster(String naam, int level, int winstTeasures, int winstLevels, String text, int notPursue, BadStuff badStuff) {
        super(naam);
        this.level = level;
        this.winstTeasures = winstTeasures;
        this.winstLevels = winstLevels;
        setText(text);
        setNotPursue(notPursue);
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

    public final void setText(String text) {
        this.text = text;
    }

    public final void setOutRun(boolean outRun) {
        this.outRun = outRun;
    }

    public final void setRunAway(int runAway) {
        this.runAway = runAway;
    }

    public final void setSpecialRace(Race specialRace) {
        this.specialRace = specialRace;
    }

    public final void setSpecialRaceExtra(int specialRaceExtra) {
        this.specialRaceExtra = specialRaceExtra;
    }

    public final void setSpecialRaceReason(String specialRaceReason) {
        this.specialRaceReason = specialRaceReason;
    }

    public final void setNotPursue(int notPursue) {
        this.notPursue = notPursue;
    }

    public final void setLevelsLostHigherLevel(int levelsLostHigherLevel) {
        this.levelsLostHigherLevel = levelsLostHigherLevel;
    }
    
}
