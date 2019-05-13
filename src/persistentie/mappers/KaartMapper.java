/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie.mappers;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.kerkerkaarten.monsterbadstuff.BadStuff;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author ziggy
 */
public class KaartMapper {

    private List<Kaart> kaarten;
    private Map<Integer, Kaart> kaartenBib;
    private List<Kaart> kerkerkaarten;
    private List<Kaart> schatkaarten;
    private BufferedReader br = null;
    private File equipment;
    private File consumablesS;
    private File race;
    private File curse;
    private File consumablesK;
    private File monster;
    private File badstuff;

    /**
     * Constructor voor kaartmapper
     */
    public KaartMapper() {
        this.kaarten = new ArrayList<>();
        this.kaartenBib = new HashMap<>();
        this.kerkerkaarten = new ArrayList<>();
        this.schatkaarten = new ArrayList<>();
        equipment = new File("src/offline_data/Equipmen");
        consumablesS = new File("src/offline_data/ConsumablesSchat");
        race = new File("src/offline_data/Race");
        curse = new File("src/offline_data/Curse");
        consumablesK = new File("src/offline_data/ConsumablesKerker");
        monster = new File("src/offline_data/Monster");
        badstuff = new File("src/offline_data/Badstuff");
        addKaarten();
        sorteerKaarten();
        maakKaartenBib();
    }

    /**
     * Geef de kaarten in database
     */
    private void addKaarten() {
        addEquipment();
        addConsumablesS();
        addRace();
        addCurse();
        addConsumablesK();
        addMonster();
    }

    /**
     * cursekaart toevoegen
     */
    private void addCurse() {
        try {
            br = new BufferedReader(new FileReader(curse));
            String line;
            while ((line = br.readLine()) != null) {
                String[] object = line.split(";");
                kaarten.add(new Curse(object[1], Integer.parseInt(object[0]), Integer.parseInt(object[2]), object[3]));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * monsterkaart toevoegen
     */
    private void addMonster() {
        try {
            br = new BufferedReader(new FileReader(monster));
            String line;
            while ((line = br.readLine()) != null) {
                String[] object = line.split(";");
                BadStuff bs = addBadstuff(Integer.parseInt(object[2]));
                kaarten.add(new Monster(object[1], Integer.parseInt(object[0]), Integer.parseInt(object[4]), Integer.parseInt(object[7]), object[6], Integer.parseInt(object[5]), new Race(object[8]), Integer.parseInt(object[3]), bs));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * badstiff toevoegen
     *
     * @param id id van monster
     * @return de badstuff
     */
    private BadStuff addBadstuff(int id) {
        BadStuff bs = null;
        try {
            BufferedReader bbs = new BufferedReader(new FileReader(badstuff));
            String line;
            while ((line = bbs.readLine()) != null) {
                if (line.contains(String.format("%d;", id))) {
                    String[] object = line.split(";");
                    bs = new BadStuff(Integer.parseInt(object[0]), Integer.parseInt(object[2]), object[1]);
                }
            }
            bbs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bs;
    }

    /**
     * consumables kerker toevoegen
     */
    private void addConsumablesK() {
        try {
            br = new BufferedReader(new FileReader(consumablesK));
            String line;
            while ((line = br.readLine()) != null) {
                String[] object = line.split(";");
                kaarten.add(new ConsumablesKerker(object[2], Integer.parseInt(object[0]), Integer.parseInt(object[1])));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * racekaart toevoegen
     */
    private void addRace() {
        try {
            br = new BufferedReader(new FileReader(race));
            String line;
            while ((line = br.readLine()) != null) {
                String[] object = line.split(";");
                kaarten.add(new Race(object[1], Integer.parseInt(object[0]), Integer.parseInt(object[2]), Integer.parseInt(object[3])));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * equipment toevoegen
     */
    private void addEquipment() {
        try {
            br = new BufferedReader(new FileReader(equipment));
            String line;
            while ((line = br.readLine()) != null) {
                String[] object = line.split(";");
                kaarten.add(new Equipment(object[2], Integer.parseInt(object[0]), Integer.parseInt(object[1]), object[8], Integer.parseInt(object[4]), new Race(object[7]), Integer.parseInt(object[4]), Integer.parseInt(object[3]), new Race(object[6])));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * consumables schat toevoegen
     */
    private void addConsumablesS() {
        try {
            br = new BufferedReader(new FileReader(consumablesS));
            String line;
            while ((line = br.readLine()) != null) {
                String[] object = line.split(";");
                kaarten.add(new ConsumablesSchat(object[4], Integer.parseInt(object[0]), Integer.parseInt(object[2]), object[3], Integer.parseInt(object[1])));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode om kaartenbib aan te maken
     */
    private void maakKaartenBib() {
        for (Kaart kaart : kaarten) {
            kaartenBib.put(kaart.getId(), kaart);
        }
    }

    /**
     * Getter voor kaartenbib
     *
     * @return map met kaarten als value en id als key
     */
    public Map<Integer, Kaart> getKaartenBib() {
        return kaartenBib;
    }

    /**
     * methode sorteert de kaarten en zet ze bij de juiste soort
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
     * getter voor alle kaarten
     *
     * @return list met kaarten
     */
    public List<Kaart> getKaarten() {
        return kaarten;
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
     * getter voor schatkaarten
     *
     * @return list met schatkaarten
     */
    public List<Kaart> getSchatkaarten() {
        return schatkaarten;
    }
}
