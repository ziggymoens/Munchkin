package domein.repositories;

import domein.DomeinController;
import domein.Spel;
import persistentie.mappers.PersistentieController;

import java.util.List;

public class SpelDbRepository {
    private final PersistentieController pc;

    public SpelDbRepository() {
        pc = new PersistentieController();
    }

    public void spelOpslaan(Spel spel) {
        pc.spelOpslaan(spel);
    }

    public List<String> geefOverzicht() {
        List<String> overzicht;
        overzicht = pc.getOverzicht();
        return overzicht;
    }

    public void laadSpel(int id, DomeinController dc) {
        dc.setSpel(pc.laadSpel(id));
    }

    public void verwijderOpgeslagenSpel(int id) {
        pc.remove(id);
    }

    public boolean bestaatSpel(int id) {
        return pc.bestaatSpel(id);
    }
}
