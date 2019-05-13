package domein.kaarten.schatkaarten;

import domein.kaarten.Schatkaart;
import exceptions.kaarten.schatkaarten.ConsumablesSchatException;

/**
 * @author ziggy
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ConsumablesSchat extends Schatkaart {

    private String text = "";
    private int battleBonus;
    private boolean killsFloatingNose;

    /**
     * Constructor schatkaarten consumable (die superklasse Schatkaart gebruikt)
     *
     * @param naam        De naam van de kaart
     * @param id          de id van de kaart
     * @param waarde      De waarde van de kaart in het spel
     * @param text        de tekst van op de kaart
     * @param battleBonus de bonus van de kaart in battle
     */
    public ConsumablesSchat(String naam, int id, int waarde, String text, int battleBonus) {
        super(naam, id, waarde);
        setText(text);
        setBattleBonus(battleBonus);
        setKillsFloatingNose(id);
    }

    /**
     * geeft bijhorende tekst terug
     *
     * @return string
     */
    public String getText() {
        return text;
    }

    /**
     * geeft battleBonus terug
     *
     * @return int
     */
    public int getBattleBonus() {
        return battleBonus;
    }

    /**
     * getter voor killsFloatingNose
     *
     * @return boolean true is killsFloatingNose
     */
    public boolean isKillsFloatingNose() {
        return killsFloatingNose;
    }

    /**
     * setter voor killsFloatingNose
     *
     * @param id id van de kaart
     */
    private void setKillsFloatingNose(int id) {
        this.killsFloatingNose = id == 33;
    }

    /**
     * setter voor text met controle niet null of leeg
     *
     * @param text bijhorende tekst van kaart
     */
    private void setText(String text) {
        if (text == null || text.equals("")) {
            throw new ConsumablesSchatException("exception.consumablesSchat.text");
        }
        this.text = text;
    }

    /**
     * setter voor battle bonus met controle groter dan 0
     *
     * @param battleBonus bonus vor de kaart
     */
    private void setBattleBonus(int battleBonus) {
        if (battleBonus < 0) {
            throw new ConsumablesSchatException("exception.consumablesSchat.bonus");
        }
        this.battleBonus = battleBonus;
    }

    /**
     * toString methode
     *
     * @return geformateerde string
     */
    @Override
    public String toString() {
        return String.format("naam: %s, id: %d, value = %d, description = %s, battlebonus = %d", getNaam(), getId(), getWaarde(), getText(), getBattleBonus());
    }

}
