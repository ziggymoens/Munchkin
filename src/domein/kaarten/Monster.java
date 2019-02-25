package domein.kaarten;

/**
 *
 * @author ziggy
 */
public class Monster extends Kerkerkaart {

    private int level;
    private BadStuff badStuff;
    private int winstTeasures;
    private int winstLevels;
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
        this.text = text;
        this.outRun = outRun;
        this.runAway = runAway;
        this.specialRace = specialRace;
        this.specialRaceExtra = specialRaceExtra;
        this.specialRaceReason = specialRaceReason;
        this.notPursue = notPursue;
        this.levelsLostHigherLevel = levelsLostHigherLevel;
        this.badStuff = badStuff;
    }

    public Monster(String wannabee_Vampire, int i, int i0, int i1, BadStuff badStuff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Monster(String _Orgs, int i, int i0, int i1, Race race, int i2, String due_ancient_grudges, BadStuff badStuff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Monster(String squidzilla, int i, int i0, int i1, String slimy, int i2, BadStuff badStuff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
