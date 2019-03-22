/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;

import java.util.Scanner;

/**
 * @author ziggy
 */
class UseCase4 {
    private final DomeinController dc;
    private final Scanner SCAN= new Scanner(System.in);


    UseCase4(DomeinController dc) {
        this.dc = dc;
    }

    void bereidSpelVoor() {

        System.out.println(LanguageResource.getString("usecase4.ask.help"));
        String help = SCAN.next().toLowerCase();
        while (!help.equals(LanguageResource.getString("yes")) && !help.equals(LanguageResource.getString("no"))) {
            System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
            System.out.println(LanguageResource.getString("usecase4.ask.help"));
            help = SCAN.next().toLowerCase();
        }
        String help2;
        do{
            System.out.println(LanguageResource.getString("usecase4.ask.bonuscard"));
            help2 = SCAN.next().toLowerCase();
            while (!help2.equals(LanguageResource.getString("yes")) && !help2.equals(LanguageResource.getString("no"))) {
                System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
                System.out.println(LanguageResource.getString("usecase4.ask.bonuscard"));
                help2 = SCAN.next().toLowerCase();
            }
        }while(help2.equals(LanguageResource.getString("yes")));

        int aantal = UseCase2.beurt + 1;
        for(int i = 0; i < UseCase1.aantalSpelers; i++){
            if(aantal < UseCase1.aantalSpelers){
                System.out.println(dc.geefNaamSpeler(aantal) + LanguageResource.getString("usecase4.Monsterhelp"));
            }else{
                aantal = -1;
            }
            aantal ++;
        }
    }
}
