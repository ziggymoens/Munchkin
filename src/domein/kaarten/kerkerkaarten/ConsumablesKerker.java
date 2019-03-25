package domein.kaarten.kerkerkaarten;

import domein.kaarten.Kerkerkaart;
import exceptions.kaarten.kerkerkaarten.ConsumablesKerkerException;

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
     * Getter om de bonus te krijgen van de kerkerkaart
     * 
     * @return bonus
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Getter voor de tekst "play during combat" te tonen
     * 
     * @return "play during combat"
     */
    public String getText() {
        return "Play during combat";
    }
    /**
     * Controle op bonus 
     * 
     * @param bonus 
     */
    private void controleBonus(int bonus){
        if(bonus < -5 || bonus > 10)
            throw new ConsumablesKerkerException("exception.consumablesKerker.bonus"); 
    }


    @Override
    public String toString() {
        return String.format("name: %s, %d to monster, play during combat",getNaam(), bonus);
    }
}
