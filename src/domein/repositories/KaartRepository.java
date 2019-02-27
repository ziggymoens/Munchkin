package domein.repositories;

import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.schatkaarten.Equipment;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import java.util.*;
import persistentie.KaartMapper;

/**
 *
 * @author ziggy
 */
public class KaartRepository {

    private final KaartMapper km;
    private final List<Kaart> kaarten;
    private final List<Kaart> schatkaarten;
    private final List<Kaart> kerkerkaarten;

    public KaartRepository() {
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        km = new KaartMapper();
        kaarten = km.geefKaarten();
        sorteerKaarten();
    }

    public List<Kaart> geefKaarten() {
        return kaarten;
    }

    private void sorteerKaarten() {
        kaarten.forEach((kaart) -> {
            if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
                schatkaarten.add(kaart);
            } else if (kaart instanceof Monster || kaart instanceof Curse || kaart instanceof Race || kaart instanceof ConsumablesKerker) {
                kerkerkaarten.add(kaart);
            }
        });
        Collections.shuffle(schatkaarten);
        Collections.shuffle(kerkerkaarten);
    }

    public List<Kaart> getSchatkaarten() {
        
        return schatkaarten;
    }

    public List<Kaart> getKerkerkaarten() {
        return kerkerkaarten;
    }
    
    
}
