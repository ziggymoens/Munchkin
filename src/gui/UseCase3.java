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
        try {
            System.out.println(dc.geefSpelsituatie());
            System.out.println(dc.toonBovensteKk());
            System.out.println(LanguageResource.getString("usecase3.confirm"));
            String bev = SCAN.next();
            while (!bev.equals(LanguageResource.getString("yes")) && !bev.equals(LanguageResource.getString("no"))) {
                System.out.printf(ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("usecase3.novalidconfirm") + ColorsOutput.reset());
                System.out.println(LanguageResource.getString("usecase3.confirm"));
                bev = SCAN.next().toLowerCase();
            }
            if (bev.equals(LanguageResource.getString("yes"))) {
                types.get(dc.geefTypeKaart()).run();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage() + e.toString());
        }
    }

    private void monsterKaart() {
        UseCase4 uc4 = new UseCase4(this.dc);
        System.out.println(Printer.printGreen("play.monster"));
    }

    private void consumablesKKaart() {
        System.out.println(Printer.printGreen("play.consumablesk"));
    }

    private void curseKaart() {
        System.out.println(Printer.printGreen("play.curse"));
    }

    private void raceKaart() {
        System.out.println(Printer.printGreen("play.race"));
    }

    private void consumablesSKaart() {
        System.out.println(Printer.printGreen("play.consumabless"));
    }

    private void equipmentKaart() {
        System.out.println(Printer.printGreen("play.equipment"));
    }


}
