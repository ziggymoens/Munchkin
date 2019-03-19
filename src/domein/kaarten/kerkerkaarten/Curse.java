package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.kerkerkaarten.CurseException;

/**
 *
 * @author ziggy
 */
public class Curse extends Kerkerkaart {

    private enum LOSTTYPES {
        head, foot, item, all, sex, race, none, armor
    }

    private String typeLost;
    private int levelLost;
    private String text;
    //private boolean raceLost;
    //private boolean sexLost;

    /**
     * Constructor van cursekaart (die superklasse kerkerkaart gebruikt)
     * 
     * @param name
     * @param id
     * @param loseLevel
     * @param typeLost
     * @param description 
     */
    public Curse(String name, int id, int loseLevel, String typeLost, String description) {
        super(name, id);
        setLevelLost(loseLevel);
        setTypeLost(typeLost);
        setText(description);
    }

    /**
     * Getter die verloren type teruggeeft
     * 
     * @return typeLost
     */
    public String getTypeLost() {
        return typeLost;
    }

    /**
     * Getter die aantal levels verloren geeft
     * 
     * @return levelLost
     */
    public int getLevelLost() {
        return levelLost;
    }

//    /**
//     *
//     * @return
//     */
//    public boolean isRaceLost() {
//        return raceLost;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public boolean isSexLost() {
//        return sexLost;
//    }

    /**
     * Setter die controleert of typeLost geldig is
     * 
     * @param typeLost
     */
    private void setTypeLost(String typeLost) {
        if (LOSTTYPES.valueOf(typeLost.toLowerCase()) == null) {
            throw new CurseException("exception.curse.typeLost");
        }
        this.typeLost = typeLost;
    }

    /**
     * Setter die controleert of LevelLost geldig is
     * 
     * @param levelLost
     */
    private void setLevelLost(int levelLost) {
        if (levelLost < 0){
            throw new CurseException("exception.curse.levelLost");
        }
        this.levelLost = levelLost;
    }

//    /**
//     *
//     * @param raceLost
//     */
//    private void setRaceLost(boolean raceLost) {
//        this.raceLost = raceLost;
//    }
//
//    /**
//     *
//     * @param sexLost
//     */
//    private void setSexLost(boolean sexLost) {
//        this.sexLost = sexLost;
//    }

    /**
     * Getter die tekst terugkrijgt
     * 
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Setter die tekst controleert
     * 
     * @param text
     */
    private void setText(String text) {
        if (text == null /*|| text.isBlank()*/) {
            throw new CurseException("exception.curse.text");
        }
        this.text = text;
    }
}
