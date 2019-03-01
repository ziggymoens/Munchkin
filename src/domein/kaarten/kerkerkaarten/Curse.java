package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.CurseException;
import language.LanguageResource;

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
    private boolean raceLost;
    private boolean sexLost;

    /**
     * Constructor kerkerkaart curse (die superklasse Kerkerkaart gebruikt)
     *
     * @param naam De naam van de kaart
     */
    public Curse(String naam) {
        super(naam);
    }

    /**
     *
     * @param naam
     * @param typeLost
     */
    public Curse(String naam, String typeLost) {
        super(naam);
        setTypeLost(typeLost);
    }

    /**
     *
     * @param naam
     * @param levelLost
     */
    public Curse(String naam, int levelLost) {
        super(naam);
        this.levelLost = levelLost;
    }

    /**
     *
     * @param naam
     * @param raceLost
     * @param sexLost
     */
    public Curse(String naam, boolean raceLost, boolean sexLost) {
        super(naam);
        this.raceLost = raceLost;
        this.sexLost = sexLost;
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

    public final void setTypeLost(String typeLost) {
        if (LOSTTYPES.valueOf(typeLost)!=null) {
            this.typeLost = typeLost;
        } else {
            throw new CurseException(LanguageResource.getString("curse.typeLost"));
        }

    }

    public void setLevelLost(int levelLost) {
        if (levelLost >= 1 && levelLost <= 2) {
            this.levelLost = levelLost;
        } else {
            throw new CurseException(LanguageResource.getString("curse.levelLost"));
        }
    }

    public void setRaceLost(boolean raceLost) {
        this.raceLost = raceLost;
    }

    public void setSexLost(boolean sexLost) {
        this.sexLost = sexLost;
    }

}
