/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;

import javax.swing.*;
import java.util.*;

/**
 * @author ziggy
 */
public class UseCase3 {

    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);

    UseCase3(DomeinController dc) {
        this.dc = dc;
        UseCase4 uc4 = new UseCase4(this.dc);
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
                dc.speelBeurt(naam);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage() + e.toString());
        }
    }
}
