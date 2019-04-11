package domein.repositories;

import domein.kaarten.Kaart;
import persistentie.mappers.PersistentieController;

import java.util.List;
import java.util.Map;

public class KaartDbKleinRepository {
    private final PersistentieController pc;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;

    public KaartDbKleinRepository() {
        pc = new PersistentieController(true);
        setSchatKaarten();
        setKerkerKaart();
    }

    public void setSchatKaarten() {
        schatkaarten = pc.getSchatkaarten();
    }

    public void setKerkerKaart(){
        kerkerkaarten = pc.getKerkerkaarten();
    }

    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }

    public List<Kaart> getKerkerkaarten() {
        return kerkerkaarten;
    }

    public Map<Integer, Kaart> getKaartenBib(){
        return pc.getKaartenBib();
    }
}
