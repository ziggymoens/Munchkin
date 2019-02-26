package domein.kaarten;

import exceptions.ConsumablesSchatException;

/**
 *
 * @author ziggy
 */
public class ConsumablesSchat extends Schatkaart {

    private String text;
    private int battleBonus;

    /**
     * Constructor schatkaarten consumable (die superklasse Schatkaart gebruikt)
     *
     * @param naam De naam van de kaart
     * @param waarde De waarde van de kaart in het spel
     * @param text
     * @param battleBonus
     */
    public ConsumablesSchat(String naam, int waarde, String text, int battleBonus) {
        super(naam, waarde);
        this.text = text;
        this.battleBonus = battleBonus;
    }

    /**
     *
     * @param naam
     * @param waarde
     */
    public ConsumablesSchat(String naam, int waarde) {
        super(naam, waarde);
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
    public int getBattleBonus() {
        return battleBonus;
    }

    public void setText(String text) {
        if(text == null || text.equals(""))
           throw new ConsumablesSchatException(language.LanguageResource.getStringLanguage("consumablesSchat.exception", getLocale()));
        else
            this.text = text;
    }

    public void setBattleBonus(int battleBonus) {
        this.battleBonus = battleBonus;
    }
}
