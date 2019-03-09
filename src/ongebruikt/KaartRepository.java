package ongebruikt;

import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.schatkaarten.Equipment;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import java.util.*;
import ongebruikt.KaartMapper;

/**
 *
 * @author ziggy
 */
public class KaartRepository {

    private final List<Kaart> kaarten;
    private final List<Kaart> schatkaarten;
    private final List<Kaart> kerkerkaarten;

    public KaartRepository() {
        KaartMapper km = new KaartMapper();
        kaarten = km.geefKaarten();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        //sorteerKaarten();
    }

    public List<Kaart> geefKaarten() {
        return kaarten;
    }

//    private void sorteerKaarten() {
//        for (Kaart kaart : kaarten) {
//            if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
//                schatkaarten.add(kaart);
//            } else if (kaart instanceof Monster || kaart instanceof Curse || kaart instanceof Race || kaart instanceof ConsumablesKerker) {
//                kerkerkaarten.add(kaart);
//            }
//        }
//        Collections.shuffle(schatkaarten);
//        Collections.shuffle(kerkerkaarten);
//    }

    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }

    public List<Kaart> getKerkerkaarten() {
        return kerkerkaarten;
    }
}
