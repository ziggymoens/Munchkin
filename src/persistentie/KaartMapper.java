/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.kaarten.kerkerkaarten.BadStuff;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.schatkaarten.Equipment;
import domein.kaarten.Kaart;
import domein.kaarten.Kerkerkaart;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.Schatkaart;
import java.util.*;

/**
 *
 * @author ziggy
 */
public class KaartMapper {

    /**
     * Geef de kaarten in database
     *
     * @return List van Kaart-objecten
     */
    public List<Kaart> geefKaarten() {
        List<Kaart> kaarten = new ArrayList<>();
        Kerkerkaart k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12;
        Schatkaart s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12;
        k1 = new Monster("Wannabee Vampire", 12, 3,1, new BadStuff("wannabe vampire", 3));
        k2 = new Monster("Crabs", 1, 1, 1, new BadStuff("crabs", "all"));
        k3 = new Monster("3,872 Orgs", 10, 3, 1, new Race("dwarf"), 6, "due ancient grudges", new BadStuff("orgs"));
        k4 = new Monster("Shrieking Geek", 6, 2, 1, new Race("dwarf"), 6, "", new BadStuff("geek", false, true));
        k5 = new Monster("Squidzilla", 18, 4, 2, "Slimy!", 4, new BadStuff("squidzilla", false, false, true));
        k6 = new Monster("Potted Plant", 1,1,1, new BadStuff("potted plant"));
        k7 = new Monster("Lawyers", 6, 2, 1, new BadStuff("lawyers"));
        k8 = new Curse("Curse!", "ally");
        k9 = new Curse("Curse!", false, true);
        k10 = new Race("Dwarf");
        k11 = new Race("Elf");
        k12 = new ConsumablesKerker("Enraged", 5);
        s1 = new Equipment("Helm of Courage", 200, "headgear", 1);
        s2 = new Equipment("Pointy hat of Power", 400, "headgear", 3);
        s3 = new Equipment("Bad-Ass Bandana", 400, "headgear", 3, new Race("human"));
        s4 = new Equipment("Horny Helmet", 600, "headgear", 1, 3, new Race("elf"));
        s5 = new Equipment("Flaming Armor", 400, "armor", 2);
        s6 = new Equipment("Boots of Butt-Kicking", 400,"footgear", 2);
        s7 = new Equipment("Boots of Running Really Fast", 400, "footgear", 2, 2);
        s8 = new ConsumablesSchat("Yuppie Water", 100, "Use during any combat. Usable once only. +2 in the battle", 2);
        s9 = new ConsumablesSchat("Magic Missle", 300, "Use during any combat. +5 to either side. Usable once only.",5);
        s10 = new ConsumablesSchat("Pretty Balloons", 0, "Use during any combat, for distraction. +5 to either side. Usable once only.", 5);
        s11 = new ConsumablesSchat("Convenient Addition Error", 1000);
        s12 = new ConsumablesSchat("1,000 Gold Pieces", 1000);

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
