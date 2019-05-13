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

    private Boolean klein = true;
    private List<Kaart> kaarten;
    private List<Integer> ids;
    private Map<Integer, Kaart> kaartenBib;
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;

    /**
     * constructor voor persistentiecontroller
     * grote db
     */
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
        if (Connection.isConnected()) {
            haalKaartenOp(this.klein);
        } else {
            haalKaartenOpOffline();
        }
    }

    /**
     * constructor voor persistentiecontroller
     * kleine db
     *
     * @param klein true = kleine db
     */
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
        if (Connection.isConnected()) {
            haalKaartenOp(this.klein);
        } else {
            haalKaartenOpOffline();
        }

    }

    /**
     * methode haalt kaarten uit db
     */
    public void haalKaartenUitDb() {
        if (Connection.isConnected()) {
            haalKaartenOp(this.klein);
        } else {
            haalKaartenOpOffline();
        }
    }

    /**
     * methode haalt kaarten offline
     */
    private void haalKaartenOpOffline() {
        kaarten.addAll(kmOffline.getKaarten());
        for (Kaart kaart : kaarten) {
            ids.add(kaart.getId());
        }
        kaartenBib = kmOffline.getKaartenBib();
        schatkaarten = kmOffline.getSchatkaarten();
        kerkerkaarten = kmOffline.getKerkerkaarten();
    }

    /**
     * methode die kaarten ophaalt
     *
     * @param klein
     */
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
        for (Kaart kaart : kaarten) {
            kaartenBib.put(kaart.getId(), kaart);
            ids.add(kaart.getId());
        }
        sorteerKaarten();
    }

    /**
     * methode die kaarten sorteert en shuffelt
     */
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
     * methode op een spel op te slaan
     *
     * @param spel spel dat opgeslagen moet worden
     */
    public void spelOpslaan(Spel spel) {
        String naam = spel.getNaam();
        int i = spel.getSpelerAanBeurt();
        boolean klein = spel.isKlein();
        int ls = spel.getSpelerAanBeurt();
        sm.addSpel(naam, i, klein, ls);
        List<Integer> volgordeD = spel.getVolgordeD();
        List<Integer> volgordeT = spel.getVolgordeT();
        int spelId = sm.getSpelId(naam);
        sm.kaartSpelOpslaan(spelId, ids, volgordeD, volgordeT);
        spelersOpslaan(spelId, spel.getSpelers());
    }

    /**
     * methode om spelers op te slaan
     *
     * @param spelId  huidige spel
     * @param spelers lijst met spelers
     */
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

    /**
     * methode die kaarten speler opslaat
     *
     * @param spelerId        huidige speler
     * @param volgordeKaarten volgorde van de kaarten
     * @param spelid          huidige spel
     */
    private void kaartenSpelerOpslaan(int spelerId, List<Integer> volgordeKaarten, int spelid) {
        sm.kaartSpelerOpslaan(spelerId, volgordeKaarten, false, spelid);
    }

    /**
     * methode die kaarten speler opslaat
     *
     * @param spelerId      huidige speler
     * @param volgordeItems volgorde van de items
     * @param spelid        huidige spel
     */
    private void itemsSpelerOpslaan(int spelerId, List<Integer> volgordeItems, int spelid) {
        sm.kaartSpelerOpslaan(spelerId, volgordeItems, true, spelid);
    }

    /**
     * geeft een overzicht van de spellen in de db
     *
     * @return list met string met info
     */
    public List<String> getOverzicht() {
        return sm.getOverzicht();
    }

    /**
     * methode verwijderd een spel uit de db
     *
     * @param id id van te verwijderen spel
     */
    public void remove(int id) {
        sm.remove(id);
    }

    /**
     * methode dat spel laad uit db
     *
     * @param index gekozen spel
     * @return geladen spel
     */
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

    /**
     * voeg kaarten toe aan een speler
     *
     * @param speler gekozen speler
     */
    private void voegKaartenToe(Speler speler) {
        for (Integer id : speler.getVolgordeKaarten()) {
            speler.voegKaartToe(kaartenBib.get(id));
        }
    }

    /**
     * voeg items toe aan speler
     *
     * @param speler gekozen speler
     */
    private void voegItemsToe(Speler speler) {
        for (int id : speler.getVolgordeItems()) {
            speler.voegKaartToe(kaartenBib.get(id));
        }
    }

    /**
     * voeg kerkerkaarten toe aan spel
     *
     * @param spel huidige spel
     */
    private void voegKerkerkaartenToeAanSpel(Spel spel) {
        List<Kaart> kerkerkaarten = new ArrayList<Kaart>(Collections.nCopies(150, null));
        for (int i = 0; i < spel.getVolgordeD().size(); i++) {
            if (spel.getVolgordeD().get(i) != 0) {
                kerkerkaarten.add(i, kaartenBib.get(spel.getVolgordeD().get(i)));
            }
        }
        List<Kaart> verwijderKaarten = new ArrayList<>();
        for (Kaart kaart : kerkerkaarten) {
            if (kaart == null) {
                verwijderKaarten.add(kaart);
            }
        }
        kerkerkaarten.removeAll(verwijderKaarten);
        spel.setKerkerkaarten(kerkerkaarten);
    }

    /**
     * voeg schatkaarten toe aan spel
     *
     * @param spel huidige spel
     */
    private void voegSchatkaartenToeAanSpel(Spel spel) {
        List<Kaart> schatkaarten = new ArrayList<Kaart>(Collections.nCopies(150, null));
        for (int i = 0; i < spel.getVolgordeT().size(); i++) {
            if (spel.getVolgordeT().get(i) != 0) {
                schatkaarten.add(i, kaartenBib.get(spel.getVolgordeT().get(i)));
            }
        }
        List<Kaart> verwijderKaarten = new ArrayList<>();
        for (Kaart kaart : schatkaarten) {
            if (kaart == null) {
                verwijderKaarten.add(kaart);
            }
        }
        schatkaarten.removeAll(verwijderKaarten);
        spel.setSchatkaarten(schatkaarten);
    }

    /**
     * getter voor kaarten bib
     *
     * @return map met kaarten als value en id als key
     */
    public Map<Integer, Kaart> getKaartenBib() {
        return kaartenBib;
    }

    /**
     * methode die kijkt of een gekozen spel bestaat
     *
     * @param id gekozen id
     * @return true = beschikbaar
     */
    public boolean bestaatSpel(int id) {
        List<Integer> spelIds = sm.geefSpelIds();
        return spelIds.contains(id);
    }
}
