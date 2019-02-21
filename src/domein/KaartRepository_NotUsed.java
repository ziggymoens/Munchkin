/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.*;
import persistentie.KaartMapper;

/**
 *
 * @author ziggy
 */
public class KaartRepository_NotUsed {
    private final KaartMapper km;
    private final List<Kaart> kaarten;
    
    public KaartRepository_NotUsed(){
        km = new KaartMapper();
        kaarten = km.geefKaarten();
    }

    public List<Kaart> geefKaarten() {
        return kaarten;
    }    
}
