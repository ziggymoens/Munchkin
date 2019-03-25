/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ziggy
 *
 * GEEEEEN STATICS
 */
class UseCase4 {
    private final DomeinController dc;
    private final Scanner SCAN= new Scanner(System.in);
    private final int aantalSpelers;


    //speler aan de beurt ==> dc.geefSpelerAanBeurt(), int van 0 tot aantalSpelers-1

    UseCase4(DomeinController dc, int aantalSpelers) {
        this.dc = dc;
        this.aantalSpelers = aantalSpelers;
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



        int aantal = dc.geefSpelerAanBeurt()+ 1;
        boolean[] bool = new boolean[aantalSpelers];
        while(!areAllTrue(bool)){
            for(int i = 0; i < aantalSpelers; i++){
                if(aantal < aantalSpelers){
                    System.out.println(dc.geefNaamSpeler(aantal) + LanguageResource.getString("usecase4.Monsterhelp"));
                    System.out.println(LanguageResource.getString("usecase4.choices"));
                    int keuze = SCAN.nextInt();
                    switch(keuze){
                        case 1:
                            System.out.println();
                            break;
                        case 2:
                            System.out.println();
                            break;
                        case 3:
                            bool[aantal] = true;
                    }

                }else{
                    aantal = -1;
                }
                aantal ++;
            }
        }

    }

    public boolean areAllTrue(boolean[] array)
    {
        for(boolean b : array) if(!b) return false;
        return true;
    }
}
