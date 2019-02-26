package domein.kaarten;

import exceptions.CurseException;
import java.util.Locale;

/**
 *
 * @author ziggy
 */
public class Curse extends Kerkerkaart {

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
        this.typeLost = typeLost;
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

    public void setTypeLost(String typeLost) {
        if (typeLost.toLowerCase().equals("head") || typeLost.toLowerCase().equals("foot")
                || typeLost.toLowerCase().equals("race") || typeLost.toLowerCase().equals("sex")
                || typeLost.toLowerCase().equals("item")) {
            this.typeLost = typeLost;
        }
        else
            throw new CurseException(language.LanguageResource.getStringLanguage("curse.typeLost", getLocale()));

    }

    public void setLevelLost(int levelLost) {
        this.levelLost = levelLost;
    }

    public void setRaceLost(boolean raceLost) {
        this.raceLost = raceLost;
    }

    public void setSexLost(boolean sexLost) {
        this.sexLost = sexLost;
    }

}
