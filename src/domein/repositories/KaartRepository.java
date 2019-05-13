package domein.repositories;

import domein.kaarten.Kaart;
import persistentie.mappers.KaartMapper;

import java.util.List;
import java.util.Map;

/**
 * @author ziggy
 */
@SuppressWarnings("unused")

public class KaartRepository {

    private List<Kaart> kaarten;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;
    private Map<Integer, Kaart> kaartenBib;

    /**
     * Constructor voor kaartrepository
     */
    public KaartRepository() {
        KaartMapper km = new KaartMapper();
        kaarten = km.getKaarten();
        schatkaarten = km.getSchatkaarten();
        kerkerkaarten = km.getKerkerkaarten();
        kaartenBib = km.getKaartenBib();
    }

    /**
     * getter voor kaarten
     *
     * @return list met kaarten
     */
    public List<Kaart> geefKaarten() {
        return kaarten;
    }

    /**
     * setter voor schatkaarten
     *
     * @param schatkaarten list met schatkaarten
     */
    public void setSchatkaarten(List<Kaart> schatkaarten) {
        this.schatkaarten = schatkaarten;
    }

    /**
     * setter voor kerkerkaarten
     *
     * @param kerkerkaarten list met kerkerkaarten
     */
    public void setKerkerkaarten(List<Kaart> kerkerkaarten) {
        this.kerkerkaarten = kerkerkaarten;
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
     * getter voor kaartenbib
     *
     * @return map met kaarten als value en id als key
     */
    public Map<Integer, Kaart> getKaartenBib() {
        return kaartenBib;
    }
}
