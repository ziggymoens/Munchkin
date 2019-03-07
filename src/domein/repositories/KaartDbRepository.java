/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.repositories;

import domein.kaarten.Kaart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import persistentie.KaartMapperDb;

/**
 *
 * @author ziggy
 */
public class KaartDbRepository {

    private String[] kaartTypes = {"ConsumablesD", "ConsumablesT", "Curse", "Equipment", "Monster", "Race"};
    private final KaartMapperDb mapper;
    private List<Kaart> kaarten;
    private final List<Kaart> schatkaarten;
    private final List<Kaart> kerkerkaarten;

    public KaartDbRepository() {
        mapper = new KaartMapperDb();
        kaarten = new ArrayList<>();
        kaarten = geefKaarten();
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        sorteerKaarten();

    }

    public List<Kaart> geefKaarten() {
        for (String type : kaartTypes){
            for (Kaart k : mapper.geefKaartenType(type)){
                kaarten.add(k);
            }
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
