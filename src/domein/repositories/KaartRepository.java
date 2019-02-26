package domein.repositories;

import domein.kaarten.Kaart;
import java.util.*;
import persistentie.KaartMapper;

/**
 *
 * @author ziggy
 */
public class KaartRepository {
    private final KaartMapper km;
    private final List<Kaart> kaarten;
    
    public KaartRepository(){
        km = new KaartMapper();
        kaarten = km.geefKaarten();
    }

    public List<Kaart> geefKaarten() {
        return kaarten;
    }    
}
