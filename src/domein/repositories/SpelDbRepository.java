package domein.repositories;

import domein.Spel;
import persistentie.mappers.SpelMapperDb;

import java.util.List;

public class SpelDbRepository {
    private final SpelMapperDb sm;
    private final List<Spel> spellen;

    public SpelDbRepository() {
        sm = new SpelMapperDb();
        spellen = sm.geefSpellen();
    }

    public void spelOpslaan(Spel spel) {
    }

    public List<Spel> getSpellen() {
        return spellen;
    }
}
