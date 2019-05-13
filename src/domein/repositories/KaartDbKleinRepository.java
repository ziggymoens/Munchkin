package domein.repositories;

import domein.kaarten.Kaart;
import persistentie.mappers.PersistentieController;

import java.util.List;
import java.util.Map;

public class KaartDbKleinRepository {
    private final PersistentieController pc;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;

    /**
     * Constructor voor kaartDbKleinRepository
     */
    public KaartDbKleinRepository() {
        pc = new PersistentieController(true);
        setSchatKaarten();
        setKerkerKaart();
    }

    /**
     * Setter voor de schatkaarten
     */
    private void setSchatKaarten() {
        schatkaarten = pc.getSchatkaarten();
    }

    /**
     * Setter voor de kerkerkaarten
     */
    private void setKerkerKaart() {
        kerkerkaarten = pc.getKerkerkaarten();
    }

    /**
     * getter voor schatkaarten
     *
     * @return List<Kaart> schatkaarten
     */
    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }

    /**
     * getter voor kerkerkaarten
     *
     * @return List<Kaart> kerkerkaarten
     */
    public List<Kaart> getKerkerkaarten() {
        return kerkerkaarten;
    }

    /**
     * getter voor de kaarten bibliotheek
     *
     * @return Map met alle kaarten als value en de id als key
     */
    public Map<Integer, Kaart> getKaartenBib() {
        return pc.getKaartenBib();
    }
}
