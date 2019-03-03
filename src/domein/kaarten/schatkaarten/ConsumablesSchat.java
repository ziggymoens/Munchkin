package domein.kaarten.schatkaarten;

import domein.kaarten.Schatkaart;
import exceptions.kaarten.ConsumablesSchatException;
import language.LanguageResource;

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
        setText(text);
        setBattleBonus(battleBonus);
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

    public final void setText(String text) {
        if(text == null || text.equals(""))
           throw new ConsumablesSchatException(LanguageResource.getString("consumablesSchat.exception"));
        else
            this.text = text;
    }

    public final void setBattleBonus(int battleBonus) {
        if(battleBonus >= 0)
            this.battleBonus = battleBonus;
        else
            throw new ConsumablesSchatException(LanguageResource.getString("consumablesSchat.exception"));
    }
    
}
