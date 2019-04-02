package persistentie.mappers;


import domein.DomeinController;
import domein.Spel;
import domein.Speler;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersistentieController {
    private final KaartMapperDb km;
    private final KaartMapperDbKlein kkm;
    private final SpelMapperDb sm;
    private DomeinController dc;

    private Boolean klein = true;
    private List<Kaart> kaarten;
    private List<Kaart> kaartenBib;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;
    private List<Spel> spellen;


    public PersistentieController() {
        km = new KaartMapperDb();
        kkm = new KaartMapperDbKlein();
        sm = new SpelMapperDb();
        kaarten = new ArrayList<>();
        kaartenBib = new ArrayList<>();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        spellen = new ArrayList<>();
        haalKaartenOp(this.klein);
    }

    public PersistentieController(Boolean klein) {
        km = new KaartMapperDb();
        kkm = new KaartMapperDbKlein();
        sm = new SpelMapperDb();
        kaarten = new ArrayList<>();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        this.klein = klein;
        haalKaartenOp(this.klein);
    }

    private void haalKaartenOp(Boolean klein) {
        String[] kaartTypes = {"ConsumablesD", "ConsumablesT", "Curse", "Equipment", "Monster", "Race"};
        if (klein) {
            for (String type : kaartTypes) {
                kaarten.addAll(kkm.geefKaartenType(type));
            }
        } else {
            for (String type : kaartTypes) {
                kaarten.addAll(km.geefKaartenType(type));
            }
        }
        kaartenBib = kaarten;
        sorteerKaarten();
    }

    private void sorteerKaarten() {
        for (Kaart kaart : kaarten) {
            if (kaart instanceof Equipment || kaart instanceof ConsumablesSchat) {
                schatkaarten.add(kaart);
            } else if (kaart instanceof Monster || kaart instanceof Curse || kaart instanceof Race || kaart instanceof ConsumablesKerker) {
                kerkerkaarten.add(kaart);
            }
        }
        Collections.shuffle(schatkaarten);
        Collections.shuffle(kerkerkaarten);
    }

    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }

    public List<Kaart> getKerkerkaarten() {
        return kerkerkaarten;
    }

    public List<Spel> geefSpellen() {
        updateSpellen();
        return spellen;
    }

    public void spelOpslaan(Spel spel) {
        String naam = spel.getNaam();
        int i = spel.getSpelerAanBeurt();
        boolean klein = spel.isKlein();
        List<Integer> volgordeD = spel.getVolgordeD();
        List<Integer> volgordeT = spel.getVolgordeT();
        sm.addSpel(naam, i, klein);
        int spelId = sm.getSpelId(naam);
        schatkaartenSpelOpslaan(spelId, volgordeT);
        kerkerkaartenSpelOpslaan(spelId, volgordeD);
        spelersOpslaan(spelId, spel.getSpelers());

    }

    private void schatkaartenSpelOpslaan(int spelId, List<Integer> volgordeT) {
        for (Integer kaart : volgordeT) {
            sm.kaartSpelOpslaan(spelId, kaart, null, volgordeT.indexOf(kaart));
        }
    }

    private void kerkerkaartenSpelOpslaan(int spelId, List<Integer> volgordeD) {
        for (Integer kaart : volgordeD) {
            sm.kaartSpelOpslaan(spelId, kaart, volgordeD.indexOf(kaart), null);
        }
    }

    private void spelersOpslaan(int spelId, List<Speler> spelers) {
        for (Speler speler : spelers) {
            int spelerId = speler.getSpelerId();
            String naam = speler.getNaam();
            String geslacht = speler.getGeslacht();
            int level = speler.getLevel();

            sm.spelerOpslaan(spelerId, naam, level, geslacht, spelId);
            kaartenSpelerOpslaan(spelerId, speler.getVolgordeKaarten());
            itemsSpelerOpslaan(spelerId, speler.getVolgordeItems());
        }
    }

    private void kaartenSpelerOpslaan(int spelerId, List<Integer> volgordeKaarten) {
        for (Integer id : volgordeKaarten) {
            sm.kaartSpelerOpslaan(spelerId, id, false);
        }
    }

    private void itemsSpelerOpslaan(int spelerId, List<Integer> volgordeItems) {
        for (Integer id : volgordeItems) {
            sm.kaartSpelerOpslaan(spelerId, id, true);
        }
    }

    public List<String> getOverzicht() {
        return sm.getOverzicht();
    }

    public void remove(String naam) {
        sm.remove(naam);
    }

    public void laadSpel(int index) {
        Spel spel = spellen.get(index);

        //verder uitwerken
    }

    private void updateSpellen() {
        spellen.clear();
        spellen = sm.geefSpellen();
    }

    public List<Kaart> getKaartenBib(){
        return kaartenBib;
    }
}
