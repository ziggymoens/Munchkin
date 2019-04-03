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

import java.util.*;

public class PersistentieController {
    private final KaartMapperDb km;
    private final KaartMapperDbKlein kkm;
    private final SpelMapperDb sm;
    private DomeinController dc;

    private Boolean klein;
    private List<Kaart> kaarten;
    private List<Integer> ids;
    private Map<Integer, Kaart> kaartenBib;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;


    public PersistentieController() {
        km = new KaartMapperDb();
        kkm = new KaartMapperDbKlein();
        sm = new SpelMapperDb();
        kaarten = new ArrayList<>();
        ids = new ArrayList<>();
        kaartenBib = new HashMap<>();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        haalKaartenOp(true);
    }

    public PersistentieController(Boolean klein) {
        km = new KaartMapperDb();
        kkm = new KaartMapperDbKlein();
        sm = new SpelMapperDb();
        kaartenBib = new HashMap<>();
        ids = new ArrayList<>();
        kaarten = new ArrayList<>();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        this.klein = klein;
        haalKaartenOp(this.klein);
    }

    private void haalKaartenOp(Boolean klein) {
        String[] kaartTypes = {"ConsumablesD", "ConsumablesT", "Curse", "Equipment", "Monster", "Race"};
        if (klein) {
            //for (String type : kaartTypes) {
                kaarten.addAll(kkm.geefKaartenType(kaartTypes));
            //}
        } else {
            for (String type : kaartTypes) {
                kaarten.addAll(km.geefKaartenType(type));
            }
        }
        for(Kaart kaart : kaarten){
            kaartenBib.put(kaart.getId(), kaart);
            ids.add(kaart.getId());
        }
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

    public void spelOpslaan(Spel spel) {
        String naam = spel.getNaam();
        int i = spel.getSpelerAanBeurt();
        boolean klein = spel.isKlein();
        int ls = spel.getSpelerAanBeurt();
        sm.addSpel(naam, i, klein, ls);
        List<Integer> volgordeD = spel.getVolgordeD();
        List<Integer> volgordeT = spel.getVolgordeT();
        int spelId = sm.getSpelId(naam);
        List<Integer> ids = new ArrayList<>();
        sm.kaartSpelOpslaan(spelId, ids, volgordeD, volgordeT);
        //schatkaartenSpelOpslaan(spelId, volgordeT);
        //kerkerkaartenSpelOpslaan(spelId, volgordeD);
        spelersOpslaan(spelId, spel.getSpelers());

    }

    private void schatkaartenSpelOpslaan(int spelId, List<Integer> volgordeT) {
        for (Integer kaart : volgordeT) {
            //sm.kaartSpelOpslaan(spelId, kaart, null, volgordeT.indexOf(kaart));
        }
    }

    private void kerkerkaartenSpelOpslaan(int spelId, List<Integer> volgordeD) {


        //for (Integer kaart : volgordeD) {
        //    sm.kaartSpelOpslaan(spelId, kaart, volgordeD.indexOf(kaart), null);
        //}
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
        sm.kaartSpelerOpslaan(spelerId, volgordeKaarten, false);
    }

    private void itemsSpelerOpslaan(int spelerId, List<Integer> volgordeItems) {
        sm.kaartSpelerOpslaan(spelerId, volgordeItems, true);
    }

    public List<String> getOverzicht() {
        return sm.getOverzicht();
    }

    public void remove(String naam) {
        sm.remove(sm.getSpelId(naam));
    }

    public Spel laadSpel(String naam) {
        int index = sm.getSpelId(naam);
        Spel spel = sm.laadSpel(index);
        for (Speler speler : spel.getSpelers()) {
            voegKaartenToe(speler);
            voegItemsToe(speler);
        }
        voegKerkerkaartenToeAanSpel(spel);
        voegSchatkaartenToeAanSpel(spel);

        return spel;
    }


    private void voegKaartenToe(Speler speler) {
        for (int id : speler.getVolgordeKaarten()) {
            speler.voegKaartToe(kaartenBib.get(id));
        }
    }

    private void voegItemsToe(Speler speler) {
        for (int id : speler.getVolgordeItems()) {
            speler.voegKaartToe(kaartenBib.get(id));
        }
    }

    private void voegKerkerkaartenToeAanSpel(Spel spel) {
        List<Kaart> kerkerkaarten = new ArrayList<>();
        for (Integer id : spel.getVolgordeD()) {
            if(spel.getVolgordeD().get(id)!= -1) {
                kerkerkaarten.add(spel.getVolgordeD().indexOf(id), kaartenBib.get(id));
            }
        }
        spel.setKerkerkaarten(kerkerkaarten);
    }

    private void voegSchatkaartenToeAanSpel(Spel spel) {
        List<Kaart> schatkaarten = new ArrayList<>();
        for (Integer id : spel.getVolgordeT()) {
            if(spel.getVolgordeT().get(id)!= -1) {
                schatkaarten.add(spel.getVolgordeT().indexOf(id),kaartenBib.get(id));
            }
        }
        spel.setSchatkaarten(schatkaarten);
    }

    public Map<Integer, Kaart> getKaartenBib() {
        return kaartenBib;
    }

    public boolean bestaatSpel(String naam) {
        return getOverzicht().contains(naam);
    }
}
