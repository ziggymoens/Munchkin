package persistentie.mappers;


import connection.Connection;
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
    private final KaartMapper kmOffline;
    private DomeinController dc;

    private Boolean klein;
    private List<Kaart> kaarten;
    private List<Integer> ids;
    private Map<Integer, Kaart> kaartenBib;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;
    private List<Integer> spelIds;


    public PersistentieController() {
        km = new KaartMapperDb();
        kkm = new KaartMapperDbKlein();
        sm = new SpelMapperDb();
        kmOffline = new KaartMapper();
        kaarten = new ArrayList<>();
        ids = new ArrayList<>();
        kaartenBib = new HashMap<>();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        if (Connection.isConnected()){
            haalKaartenOp(this.klein);
        }else {
            haalKaartenOpOffline();
        }
    }

    public PersistentieController(Boolean klein) {
        km = new KaartMapperDb();
        kkm = new KaartMapperDbKlein();
        sm = new SpelMapperDb();
        kmOffline = new KaartMapper();
        kaartenBib = new HashMap<>();
        ids = new ArrayList<>();
        kaarten = new ArrayList<>();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        this.klein = klein;
        if (Connection.isConnected()){
            haalKaartenOp(this.klein);
        }else {
            haalKaartenOpOffline();
        }

    }

    private void haalKaartenOpOffline(){
        kaarten.addAll(kmOffline.getKaarten());
        for (Kaart kaart: kaarten){
            ids.add(kaart.getId());
        }
        kaartenBib = kmOffline.getKaartenBib();
        schatkaarten = kmOffline.getSchatkaarten();
        kerkerkaarten = kmOffline.getKerkerkaarten();
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
        //List<Integer> ids = new ArrayList<>();
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
            kaartenSpelerOpslaan(spelerId, speler.getVolgordeKaarten(), spelId);
            itemsSpelerOpslaan(spelerId, speler.getVolgordeItems(), spelId);
        }
    }

    private void kaartenSpelerOpslaan(int spelerId, List<Integer> volgordeKaarten, int spelid) {
        sm.kaartSpelerOpslaan(spelerId, volgordeKaarten, false, spelid);
    }

    private void itemsSpelerOpslaan(int spelerId, List<Integer> volgordeItems, int spelid) {
        sm.kaartSpelerOpslaan(spelerId, volgordeItems, true, spelid);
    }

    public List<String> getOverzicht() {
        return sm.getOverzicht();
    }

    public void remove(int id) {
        sm.remove(id);
    }

    public Spel laadSpel(int index) {
        //int index = sm.getSpelId(naam);
        Spel spel = sm.laadSpel(index);
        for (Speler speler : spel.getSpelers()) {
            voegKaartenToe(speler);
            voegItemsToe(speler);
            speler.updateKaarten();
        }
        voegKerkerkaartenToeAanSpel(spel);
        voegSchatkaartenToeAanSpel(spel);
        return spel;
    }


    private void voegKaartenToe(Speler speler) {
        for (Integer id : speler.getVolgordeKaarten()) {
            speler.voegKaartToe(kaartenBib.get(id));
        }
    }

    private void voegItemsToe(Speler speler) {
        for (int id : speler.getVolgordeItems()) {
            speler.voegKaartToe(kaartenBib.get(id));
        }
    }

    private void voegKerkerkaartenToeAanSpel(Spel spel) {
        List<Kaart> kerkerkaarten = new ArrayList<Kaart>(Collections.nCopies(28,new Race("elf")));
        for (Integer id : spel.getVolgordeD()) {
            if(spel.getVolgordeD().get(id)!= -1) {
                kerkerkaarten.add(spel.getVolgordeD().indexOf(id), kaartenBib.get(id));
            }
        }
        spel.setKerkerkaarten(kerkerkaarten);
    }

    private void voegSchatkaartenToeAanSpel(Spel spel) {
        List<Kaart> schatkaarten = new ArrayList<Kaart>(Collections.nCopies(50, new Equipment("test", 1, 1, "head", 1, new Race("elf"), 1,1, new Race("test"))));
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

    public boolean bestaatSpel(int id) {
        spelIds = sm.geefSpelIds();
        return spelIds.contains(id);
    }
}
