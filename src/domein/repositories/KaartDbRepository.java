/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.repositories;

import domein.kaarten.Kaart;
import persistentie.mappers.PersistentieController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ziggy
 */
public class KaartDbRepository {

    private final PersistentieController pc;
    private final List<Kaart> schatkaarten;
    private final List<Kaart> kerkerkaarten;

    public KaartDbRepository() {
        pc = new PersistentieController(false);
        schatkaarten = new ArrayList<>();
        kerkerkaarten = new ArrayList<>();
        setKerkerKaart();
        setSchatKaarten();
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
}
