/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import javax.swing.*;
import java.util.*;

/**
 * @author ziggy
 */
public class UseCase3 {

    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);
    private Map<String, Runnable> types;
    private int huidigeKaart;
    private String naam;

    UseCase3(DomeinController dc) {
        types = new HashMap<>();
        types.put("ConsumablesKerker", this::consumablesKKaart);
        types.put("Curse", this::curseKaart);
        types.put("Monster", this::monsterKaart);
        types.put("Race", this::raceKaart);
        types.put("ConsumablesSchat", this::consumablesSKaart);
        types.put("Equipment", this::equipmentKaart);
        this.dc = dc;

    }

    public void speelBeurt(String naam) {
        this.naam = naam;
        try {
            System.out.println(ColorsOutput.achtergrond("yellow") + dc.geefSpelsituatie() + ColorsOutput.reset());
            System.out.println(dc.toonBovensteKk());
            huidigeKaart = dc.geefIdBovensteKaart();
            System.out.println(LanguageResource.getString("usecase3.confirm"));
            String bev = SCAN.next();
            while (!bev.equals(LanguageResource.getString("yes"))) {
                System.out.printf(ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("usecase3.novalidconfirm") + ColorsOutput.reset());
                System.out.println(LanguageResource.getString("usecase3.confirm"));
                bev = SCAN.next().toLowerCase();
            }
            if (bev.equals(LanguageResource.getString("yes"))) {
                types.get(dc.geefTypeKaart()).run();
            }
            dc.nieuweBovensteKaartK();
        } catch (Exception e) {
            System.err.println(e.getMessage() + e.toString());
        }
    }

    private void monsterKaart() {
        UseCase4 uc4 = new UseCase4(this.dc);
        uc4.bereidSpelVoor();
        System.out.println(Printer.printGreen("play.monster"));
    }

    private void consumablesKKaart() {
        dc.geefKerkerkaartAanSpeler(naam);
        System.out.println(Printer.printGreen("play.consumablesk"));
    }

    private void curseKaart() {
        dc.effectKaart(naam);
        System.out.println(Printer.printGreen("play.curse"));
    }

    private void raceKaart() {
        dc.geefKerkerkaartAanSpeler(naam);
        System.out.println(Printer.printGreen("play.race"));
    }

    private void consumablesSKaart() {
        dc.effectKaart(naam);
        System.out.println(Printer.printGreen("play.consumabless"));
    }

    private void equipmentKaart() {
        dc.effectKaart(naam);
        System.out.println(Printer.printGreen("play.equipment"));
    }


}
