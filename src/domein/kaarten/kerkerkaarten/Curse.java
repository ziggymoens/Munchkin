package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.CurseException;

/**
 *
 * @author ziggy
 */
public class Curse extends Kerkerkaart {

    private enum LOSTTYPES {
        head, foot, race, sex, item, all
    };
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
        if (LOSTTYPES.valueOf(typeLost) != null) {
            this.typeLost = typeLost;
        } else {
            throw new CurseException("exception.curse.typeLost");
        }

    }

    /**
     *
     * @param levelLost
     */
    private void setLevelLost(int levelLost) {
        if (levelLost >= 1 && levelLost <= 2) {
            this.levelLost = levelLost;
        } else {
            throw new CurseException("exception.curse.levelLost");
        }
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
        if (text == null || text.isBlank()) {
            throw new CurseException("exception.curse.text");
        }
        this.text = text;
    }
}

//    /**
//     * Constructor kerkerkaart curse (die superklasse Kerkerkaart gebruikt)
//     *
//     * @param naam De naam van de kaart
//     */
//    public Curse(String naam) {
//        super(naam);
//    }
//
//    /**
//     *
//     * @param naam
//     * @param typeLost
//     */
//    public Curse(String naam, String typeLost) {
//        super(naam);
//        setTypeLost(typeLost);
//    }
//
//    /**
//     *
//     * @param naam
//     * @param levelLost
//     */
//    public Curse(String naam, int levelLost) {
//        super(naam);
//        this.levelLost = levelLost;
//    }
//
//    /**
//     *
//     * @param naam
//     * @param raceLost
//     * @param sexLost
//     */
//    public Curse(String naam, boolean raceLost, boolean sexLost) {
//        super(naam);
//        setRaceLost(raceLost);
//        setSexLost(sexLost);
//    }
