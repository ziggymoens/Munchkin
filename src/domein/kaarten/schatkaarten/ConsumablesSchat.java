package domein.kaarten.schatkaarten;

import domein.kaarten.Schatkaart;
import exceptions.kaarten.ConsumablesSchatException;

/**
 *
 * @author ziggy
 */
public class ConsumablesSchat extends Schatkaart {

    private String text;
    private int battleBonus;
    private boolean killsFloatingNose;

    /**
     * Constructor schatkaarten consumable (die superklasse Schatkaart gebruikt)
     *
     * @param naam De naam van de kaart
     * @param id
     * @param waarde De waarde van de kaart in het spel
     * @param text
     * @param battleBonus
     */
    public ConsumablesSchat(String naam, int id, int waarde, String text, int battleBonus) {
        super(naam, id, waarde);
        setText(text);
        setBattleBonus(battleBonus);
        setKillsFloatingNose(id);
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

    /**
     *
     * @return
     */
    public boolean isKillsFloatingNose() {
        return killsFloatingNose;
    }

    /**
     *
     * @param id
     */
    private void setKillsFloatingNose(int id) {
        if (id == 33) {
            this.killsFloatingNose = true;
        }
    }

    /**
     *
     * @param text
     */
    private void setText(String text) {
        if (text == null || text.equals("")) {
            throw new ConsumablesSchatException("exception.consumablesSchat.text");
        }
        this.text = text;
    }

    /**
     *
     * @param battleBonus
     */
    private void setBattleBonus(int battleBonus) {
        if (battleBonus < 0) {
            throw new ConsumablesSchatException("exception.consumablesSchat.bonus");
        }
        this.battleBonus = battleBonus;
    }

}
/**
 * // * // * @param naam // * @param waarde //
 */
//    public ConsumablesSchat(String naam, int waarde) {
//        super(naam, waarde);
//    }
