package domein.repositories;

import domein.kaarten.Kaart;
import persistentie.mappers.PersistentieController;

import java.util.List;
import java.util.Map;

/**
 * @author ziggy
 */
@SuppressWarnings("unused")

public class KaartDbRepository {

    private final PersistentieController pc;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;

    /**
     * Costructor voor KaartDbRepository
     */
    public KaartDbRepository() {
        pc = new PersistentieController(false);
        setKerkerKaart();
        setSchatKaarten();
    }

    /**
     * setter voor schatkaarten
     */
    private void setSchatKaarten() {
        schatkaarten = pc.getSchatkaarten();
    }

    /**
     * setter voor kerkerkaarten
     */
    private void setKerkerKaart() {
        kerkerkaarten = pc.getKerkerkaarten();
    }

    /**
     * getter voor schatkaarten
     *
     * @return list met schatkaarten
     */
    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }

    /**
     * getter voor kerkerkaarten
     *
     * @return list met kerkerkaarten
     */
    public List<Kaart> getKerkerkaarten() {
        return kerkerkaarten;
    }

    /**
     * getter voor de kaartenbib
     *
     * @return map met kaarten als value en id als key
     */
    public Map<Integer, Kaart> getKaartenBib() {
        return pc.getKaartenBib();
    }
}
