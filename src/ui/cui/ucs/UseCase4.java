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
import java.util.List;
import java.util.Scanner;

/**
 * @author ziggy
 * GEEEEEN STATICS
 */
class UseCase4 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);
    private final int aantalSpelers;
    private String naam;


    //speler aan de beurt ==> dc.geefSpelerAanBeurt(), int van 0 tot aantalSpelers-1

    UseCase4(DomeinController dc, int aantalSpelers, String naam) {
        this.dc = dc;
        this.aantalSpelers = aantalSpelers;
        this.naam = naam;
    }

    // Vragen aan speler of hij hulp wilt
    void bereidSpelVoor() {
        int id = dc.geefIdBovensteKaart();
        int battleBonusMonster = Integer.parseInt(dc.geefMonsterAttribuut(id,"level").toString());;
        int battleBonusSpeler = dc.geefLevel(dc.geefSpelerAanBeurt());

        String help = "";
        //Vragen aan de speler of hij hulp wilt met het bevechten van het monster, zoja, mogen anderen hem helpen, anders niet
        help = hulpVragen(help);
        // Vragen aan speler of hij een bonuskaart wilt spelen.
        vraagKaartSpelen("usecase4.ask.bonuscard", help);
        //initialisatie van de 2 lijsten beurt en helptmee
        List<Boolean> beurt = new ArrayList<>();
        List<Boolean> helptmee = new ArrayList<>();
        for (int i = 0; i < aantalSpelers; i++) {
            beurt.add(false);
            if (i == dc.geefSpelerAanBeurt()) {
                helptmee.add(true);
            } else {
                helptmee.add(false);
            }
        }
        monsterKeuze(help, beurt, helptmee);
        //vragen of de speler nog een extra kaart wilt spelen, zoja, speel een kaart
        vraagKaartSpelen("usecase4.ask.bonuscard", help);
        System.out.println("\n" + dc.bovensteKaartToString());
        //Het overzicht tonen voor het gevecht
        geefOverzichtGevecht(battleBonusMonster, battleBonusSpeler, helptmee);
    }

    private String hulpVragen(String help){
        System.out.println(LanguageResource.getString("usecase4.ask.help"));
        help = SCAN.next().toLowerCase();
        while (!help.equals(LanguageResource.getString("yes")) && !help.equals(LanguageResource.getString("no"))) {
            System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
            System.out.println(LanguageResource.getString("usecase4.ask.help"));
            help = SCAN.next().toLowerCase();
        }
        return help;
    }

    private void monsterKeuze(String help, List<Boolean> beurt, List<Boolean> helptmee){
        int aantal = dc.geefSpelerAanBeurt() + 1 ;
        for (int i = 0; i < aantalSpelers; i++) {
            if (aantal < aantalSpelers) {
                //De spelers blijven vragen welke optie hij of zij wilt nemen, tot de speler optie 3 neemt
                boolean tryAgain = true;
                while (tryAgain){
                    try {
                        while (!beurt.get(i)) {
                            System.out.printf("%s%s%n", String.format("%s", ColorsOutput.kleur("blue") + dc.geefNaamSpeler(aantal) + ColorsOutput.reset()), LanguageResource.getString("usecase4.Monsterhelp"));
                            System.out.println(LanguageResource.getString("usecase4.choices"));
                            int keuze = SCAN.nextInt();
                            if (keuze < 1 || keuze > 3) {
                                throw new Exception();
                            }
                            switch (keuze) {
                                case 1:
                                    //Mag alleen gebeuren als de speler die vecht akkoord is gegaan dat hij hulp wilt
                                    if (help.equals(LanguageResource.getString("yes"))) {
                                        //aanpassen???
                                        helpSpeler(aantal, help);
                                        helptmee.remove(aantal);
                                        helptmee.add(aantal, true);
                                    } else {
                                        System.err.println(LanguageResource.getString("exception.help"));
                                    }
                                    break;
                                case 2:
                                    helpMonster(aantal, help);
                                    break;
                                case 3:
                                    beurt.remove(i);
                                    beurt.add(i, true);
                                    //beurt[i] = true;
                                    break;
                            }
                        }
                        tryAgain = false;
                    } catch (Exception e) {
                        System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase2.choiceerror") + ColorsOutput.reset());
                        SCAN.nextLine();
                    }
                }
            } else {
                aantal = -1;
            }
            aantal++;
        }
    }

    private void geefOverzichtGevecht(int battleBonusMonster, int battleBonusSpeler, List<Boolean> helptmee){
        System.out.printf(LanguageResource.getString("usecase4.battle") + "%n%n", battleBonusMonster, battleBonusSpeler);
        int waar = 0;
        List<String> ret = dc.geefBeknopteSpelsituatie();
        for (String str : ret) {
            System.out.println(String.format("%s, %s",str, helptmee.get(waar) ? "vecht mee" : "vecht niet mee"));
            waar++;
        }
        vechtMonster(battleBonusMonster, battleBonusSpeler, helptmee);
    }

    private void helpSpeler(int i, String help) {
        UseCase5 uc5 = new UseCase5(this.dc, i, help);
        uc5.speelKaart();
    }

    private void helpMonster(int i, String help) {
        UseCase5 uc5 = new UseCase5(this.dc, i, help);
        uc5.speelKaart();
    }

    private void vechtMonster(int battleBonusMonster, int battleBonusSpeler, List<Boolean> helptmee) {
        UseCase6 uc6 = new UseCase6(this.dc);
        uc6.vechtMetMonster(battleBonusMonster, battleBonusSpeler, helptmee, aantalSpelers);
    }

    private void vraagKaartSpelen(String output, String help){
        String kaart;
        do {
            System.out.printf("%s, %s%n", String.format("%s", ColorsOutput.kleur("blue") + naam + ColorsOutput.reset()), LanguageResource.getString(output));
            kaart = SCAN.next().toLowerCase();
            while (!kaart.equals(LanguageResource.getString("yes")) && !kaart.equals(LanguageResource.getString("no"))) {
                System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
                System.out.println(LanguageResource.getString(output));
                kaart = SCAN.next().toLowerCase();
            }
            if (kaart.equals(LanguageResource.getString("yes"))) {
                UseCase5 uc5 = new UseCase5(this.dc, dc.geefSpelerAanBeurt(), help);
                uc5.speelKaart();
            }
        } while (kaart.equals(LanguageResource.getString("yes")));
    }
}
