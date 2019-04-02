package domein.repositories;

import domein.Spel;
import persistentie.mappers.PersistentieController;

import java.util.List;

public class SpelDbRepository {
    private final PersistentieController pc;
    private List<Spel> spellen;

    public SpelDbRepository() {
        pc = new PersistentieController();
        spellen = pc.geefSpellen();
    }

    public void spelOpslaan(Spel spel) {
        pc.spelOpslaan(spel);
    }

    public List<Spel> getSpellen() {
        spellen.clear();
        spellen = pc.geefSpellen();
        return spellen;
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
        pc.remove(spellen.get(index).getNaam());
    }
}
