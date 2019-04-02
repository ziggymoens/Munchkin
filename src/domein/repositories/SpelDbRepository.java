package domein.repositories;

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

    public void laadSpel(int index) {
        pc.laadSpel(index);
    }

    public void verwijderOpgeslagenSpel(int index) {
        pc.remove(index);
    }
}
