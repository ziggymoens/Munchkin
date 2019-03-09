package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.CurseException;

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
    private boolean raceLost;
    private boolean sexLost;

    public Curse(String name, int id, int loseLevel, String typeLost, String description) {
        super(name, id);
        setLevelLost(loseLevel);
        setTypeLost(typeLost);
        setText(description);
    }

    /**
     *
     * @return
     */
    public String getTypeLost() {
        return typeLost;
    }

    /**
     *
     * @return
     */
    public int getLevelLost() {
        return levelLost;
    }

    /**
     *
     * @return
     */
    public boolean isRaceLost() {
        return raceLost;
    }

    /**
     *
     * @return
     */
    public boolean isSexLost() {
        return sexLost;
    }

    /**
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
     *
     * @param levelLost
     */
    private void setLevelLost(int levelLost) {
        if (levelLost < 0){
            throw new CurseException("exception.curse.levelLost");
        }
        this.levelLost = levelLost;
    }

    /**
     *
     * @param raceLost
     */
    private void setRaceLost(boolean raceLost) {
        this.raceLost = raceLost;
    }

    /**
     *
     * @param sexLost
     */
    private void setSexLost(boolean sexLost) {
        this.sexLost = sexLost;
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
     * @param text
     */
    private void setText(String text) {
        if (text == null /*|| text.isBlank()*/) {
            throw new CurseException("exception.curse.text");
        }
        this.text = text;
    }
}
