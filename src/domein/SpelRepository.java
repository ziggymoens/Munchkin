package domein;

import java.util.*;
import persistentie.SpelMapper;

/**
 *
 * @author ziggy
 */
public class SpelRepository {

    private final SpelMapper sm;
    private final List<Spel> spellen;

    /**
     * Constructor voor de SpelRepository
     */
    public SpelRepository() {
        sm = new SpelMapper();
        spellen = new ArrayList<>();
    }

    /**
     * Geeft de spellen terug in de database via de SpelMapper
     *
     * @return List met Spel-objecten
     */
    public List<Spel> getSpellen() {
        return spellen;
    }

    /**
     * Spel opslaan in de database
     *
     * @param spel Spel-object dat u wil opslaan
     */
    public void spelOpslaan(Spel spel) {
        sm.addSpel(spel);
    }

}
