/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Spel;
import java.util.*;

/**
 *
 * @author ziggy
 */
public class SpelMapper {

    private List<Spel> spellen;

    /**
     * Constructor voor de SpelMapper met daarin de creatie van een test
     * Spel-object
     */
    public SpelMapper() {
        spellen = new ArrayList<>();
        Spel s1 = new Spel(4);
        spellen.add(s1);
    }

    /**
     * Geef de spellen opgeslagen in de database
     *
     * @return List van Spel-objecten
     */
    public List<Spel> geefSpellen() {
        return spellen;
    }

    /**
     * Voeg een spel toe aan de database
     *
     * @param spel een Object van het type Spel
     */
    public void addSpel(Spel spel) {
        spellen.add(spel);
    }
}
