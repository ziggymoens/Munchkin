package domein.repositories;

import domein.kaarten.Kaart;
import persistentie.mappers.PersistentieController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KaartDbKleinRepository {
    private final PersistentieController pc;
    private final List<Kaart> schatkaarten;
    private final List<Kaart> kerkerkaarten;

    public KaartDbKleinRepository() {
        pc = new PersistentieController(true);
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        setSchatKaarten();
        setKerkerKaart();
    }

    public List<Kaart> setSchatKaarten() {
        return pc.getSchatkaarten();
    }

    public List<Kaart> setKerkerKaart(){
        return pc.getKerkerkaarten();
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
