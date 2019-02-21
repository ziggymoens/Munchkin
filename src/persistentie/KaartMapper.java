/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.ConsumablesKerker;
import domein.ConsumablesSchat;
import domein.Curse;
import domein.Equipment;
import domein.Kaart;
import domein.Kerkerkaart;
import domein.Monster;
import domein.Race;
import domein.Schatkaart;
import java.util.*;

/**
 *
 * @author ziggy
 */
public class KaartMapper {
    
    /**
     * Geef de kaarten in database
     * @return List van Kaart-objecten
     */
    public List<Kaart> geefKaarten(){
        List<Kaart>kaarten = new ArrayList<>();
        Kerkerkaart k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12;
        Schatkaart s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12;
        k1 = new Monster("kerkerkaart1");
        k2 = new Monster("kerkerkaart2");
        k3 = new Monster("kerkerkaart3");
        k4 = new Monster("kerkerkaart4");
        k5 = new Monster("kerkerkaart5");
        k6 = new Monster("kerkerkaart6");
        k7 = new Monster("kerkerkaart7");
        k8 = new Curse("kerkerkaart8");
        k9 = new Curse("kerkerkaart9");
        k10 = new Race("kerkerkaart10");
        k11 = new Race("kerkerkaart11");
        k12 = new ConsumablesKerker("kerkerkaart12");
        s1 = new Equipment("schatkaart1", 200);
        s2 = new Equipment("schatkaart2", 300);
        s3 = new Equipment("schatkaart3",150);
        s4 = new Equipment("schatkaart4",250);
        s5 = new Equipment("schatkaart5",350);
        s6 = new Equipment("schatkaart6", 100);
        s7 = new Equipment("schatkaart7", 150);
        s8 = new ConsumablesSchat("schatkaart8", 200);
        s9 = new ConsumablesSchat("schatkaart9", 400);
        s10 = new ConsumablesSchat("schatkaart10", 600);
        s11 = new ConsumablesSchat("schatkaart11", 200);
        s12 = new ConsumablesSchat("schatkaart12", 150);
        
        kaarten.add(k1);
        kaarten.add(k2);
        kaarten.add(k3);
        kaarten.add(k4);
        kaarten.add(k5);
        kaarten.add(k6);
        kaarten.add(k7);
        kaarten.add(k8);
        kaarten.add(k9);
        kaarten.add(k10);
        kaarten.add(k11);
        kaarten.add(k12);
        kaarten.add(s1);
        kaarten.add(s2);
        kaarten.add(s3);
        kaarten.add(s4);
        kaarten.add(s5);
        kaarten.add(s6);
        kaarten.add(s7);
        kaarten.add(s8);
        kaarten.add(s9);
        kaarten.add(s10);
        kaarten.add(s11);
        kaarten.add(s12);
        return kaarten;
    }
}
