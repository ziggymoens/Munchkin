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
    
    public SpelMapper(){
        spellen = new ArrayList<>();
        Spel s1 = new Spel(4);
        spellen.add(s1);
    }
    
    public List<Spel> geefSpellen(){
        return spellen;
    }
    
    public void addSpel(Spel spel){
        spellen.add(spel);
    }
}
