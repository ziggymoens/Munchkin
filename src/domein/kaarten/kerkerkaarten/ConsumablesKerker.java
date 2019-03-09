package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.ConsumablesKerkerException;

/**
 *
 * @author ziggy
 */
public class ConsumablesKerker extends Kerkerkaart {
    
    private final int bonus;

    /**
     * Constructor van kerkerkaarten consumables (die superklasse Kerkerkaart
     * gebruikt)
     *
     * @param naam Naam van de kaart
     * @param id
     * @param bonus
     */
    public ConsumablesKerker(String naam,int id, int bonus) {
        super(naam, id);
        controleBonus(bonus);
        this.bonus = bonus;
    }
        
    /**
     *
     * @return
     */
    public int getBonus() {
        return bonus;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return "Play during combat";
    }
    /**
     * 
     * @param bonus 
     */
    private void controleBonus(int bonus){
        if(bonus < -5 || bonus > 10)
            throw new ConsumablesKerkerException("exception.consumablesKerker.bonus"); 
    }
    
    
}
