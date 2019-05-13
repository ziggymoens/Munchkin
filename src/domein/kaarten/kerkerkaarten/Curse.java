package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.kerkerkaarten.CurseException;

/**
 * @author ziggy
 */
@SuppressWarnings("ConstantConditions")
public class Curse extends Kerkerkaart {

    @SuppressWarnings("unused")
    private enum LOSTTYPES {
        head, foot, item, all, sex, race, none, armor
    }

    private String typeLost;
    private int levelLost;
    private String text;
    //private boolean raceLost;
    //private boolean sexLost;

    /**
     * constructor voor curse
     * <p>
     * KLEINE DB
     *
     * @param name        naam van de kaart
     * @param id          id van de kaart
     * @param loseLevel   hoeveel levels verloren gaan
     * @param description bijhorende tekst van de kaart
     */
    public Curse(String name, int id, int loseLevel, String description) {
        super(name, id);
        setLevelLost(loseLevel);
        setText(description);
    }


    /**
     * Constructor van cursekaart (die superklasse kerkerkaart gebruikt)
     * <p>
     * GROTE DB
     *
     * @param name        naam van de kaart
     * @param id          id van de kaart
     * @param loseLevel   hoeveel levels verloren gaan
     * @param typeLost    welk type gear verloren gaat
     * @param description bijhorende text
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
     * @param typeLost welk type gear verloren gaat
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
     * @param levelLost hoeveel levels verloren gaan
     */
    private void setLevelLost(int levelLost) {
        if (levelLost < 0) {
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
    @SuppressWarnings("WeakerAccess")
    public String getText() {
        return text;
    }

    /**
     * Setter die tekst controleert
     *
     * @param text bijhorende tekst
     */
    private void setText(String text) {
        if (text == null /*|| text.isBlank()*/) {
            throw new CurseException("exception.curse.text");
        }
        this.text = text;
    }

    /**
     * toString methode
     *
     * @return geformateerde string
     */
    @Override
    public String toString() {
        return String.format("name: %s, %s%s", getNaam(), levelLost == 1 ? "Lose a level" : "lose 2 levels", text.isEmpty() ? "" : " " + getText());
    }
}
