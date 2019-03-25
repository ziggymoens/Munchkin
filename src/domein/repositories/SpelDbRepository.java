package domein.repositories;

import domein.Spel;
import persistentie.mappers.SpelMapperDb;

import java.util.ArrayList;
import java.util.List;

public class SpelDbRepository {
    private final SpelMapperDb sm;
    private List<Spel> spellen;

    public SpelDbRepository() {
        sm = new SpelMapperDb();
        spellen = sm.geefSpellen();
    }

    public void spelOpslaan(Spel spel) {
        sm.addSpel();
    }

    public List<Spel> getSpellen() {
        spellen.clear();
        spellen = sm.geefSpellen();
        return spellen;
    }
}
