package domein.repositories;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import persistentie.mappers.KaartMapperDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KaartDbKleinRepository {
    private final String[] kaartTypes = {"ConsumablesD", "ConsumablesT", "Curse", "Equipment", "Monster", "Race"};
    private final KaartMapperDb mapper;
    private List<Kaart> kaarten;
    private final List<Kaart> schatkaarten;
    private final List<Kaart> kerkerkaarten;

    public KaartDbKleinRepository() {
        mapper = new KaartMapperDb();
        kaarten = new ArrayList<>();
        kaarten = geefKaarten();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        sorteerKaarten();

    }

    public List<Kaart> geefKaarten() {
        for (String type : kaartTypes){
            kaarten.addAll(mapper.geefKaartenType(type));
        }
        return kaarten;
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
}
