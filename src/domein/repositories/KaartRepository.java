package domein.repositories;

import domein.kaarten.Kaart;
import persistentie.mappers.KaartMapper;

import java.util.List;
import java.util.Map;

/**
 * @author ziggy
 */
public class KaartRepository {

    private final KaartMapper km;
    private List<Kaart> kaarten;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;
    private Map<Integer, Kaart> kaartenBib;

    public KaartRepository() {
        km = new KaartMapper();
        kaarten = km.getKaarten();
        schatkaarten = km.getSchatkaarten();
        kerkerkaarten = km.getKerkerkaarten();
        kaartenBib  = km.getKaartenBib();
    }

    public List<Kaart> geefKaarten() {
        return kaarten;
    }

    public void setSchatkaarten(List<Kaart> schatkaarten) {
        this.schatkaarten = schatkaarten;
    }

    public void setKerkerkaarten(List<Kaart> kerkerkaarten) {
        this.kerkerkaarten = kerkerkaarten;
    }

    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }

    public List<Kaart> getKerkerkaarten() {
        return kerkerkaarten;
    }

    public Map<Integer, Kaart> getKaartenBib() {
        return kaartenBib;
    }
}
