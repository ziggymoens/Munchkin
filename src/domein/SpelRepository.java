/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.*;
import persistentie.SpelMapper;

/**
 *
 * @author ziggy
 */
public class SpelRepository {
    private final SpelMapper sm;
    private final List<Spel> spellen;
    
    public SpelRepository(){
        sm = new SpelMapper();
        spellen = new ArrayList<>();
    }

    public List<Spel> getSpellen() {
        return spellen;
    }
    
    public void spelOpslaan(Spel spel){
        sm.addSpel(spel);
    }
    
}
