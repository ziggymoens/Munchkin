package persistentie.mappers;


import domein.DomeinController;
import domein.Spel;
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
    private List<Kaart> schatkaarten;
    private List<Kaart> kerkerkaarten;
    private List<Spel> spellen;


    public PersistentieController(){
        km = new KaartMapperDb();
        kkm = new KaartMapperDbKlein();
        sm = new SpelMapperDb();
        kaarten = new ArrayList<>();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        spellen = new ArrayList<>();
        haalKaartenOp(this.klein);
    }

    public PersistentieController(Boolean klein){
        km = new KaartMapperDb();
        kkm = new KaartMapperDbKlein();
        sm = new SpelMapperDb();
        kaarten = new ArrayList<>();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        this.klein = klein;
        haalKaartenOp(this.klein);
    }

    private void haalKaartenOp(Boolean klein){
        String[] kaartTypes = {"ConsumablesD", "ConsumablesT", "Curse", "Equipment", "Monster", "Race"};
        if (klein){
            for (String type : kaartTypes){
                kaarten.addAll(kkm.geefKaartenType(type));
            }
        }else{
            for (String type : kaartTypes){
                kaarten.addAll(km.geefKaartenType(type));
            }
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

    public List<Spel> geefSpellen() {
        updateSpellen();
        return spellen;
    }

    public void addSpel(Spel spel) {
        String naam = spel.getNaam();
        int i = spel.spelerAanBeurt;
        List<Integer> volgordeD = new ArrayList<>();
        List<Integer> volgordeT = new ArrayList<>();

        sm.addSpel();
    }

    public List<String> getOverzicht() {
        return sm.getOverzicht();
    }

    public void remove(String naam) {
        sm.remove(naam);
    }

    public void laadSpel(int index) {
        spellen.get(index);
        //verder uitwerken
    }

    private void updateSpellen(){
        spellen.clear();
        spellen = sm.geefSpellen();
    }
}
