///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ui.cui.ucs;
//
//import domein.DomeinController;
//import language.LanguageResource;
//import printer.ColorsOutput;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * @author ziggy
// *
// * HERWERKEN UC4
// *
// *
// * GEEEEEN STATICS
// */
//class UseCase4V2 {
//    private final DomeinController dc;
//    private final Scanner SCAN = new Scanner(System.in);
//    private final int aantalSpelers;
//
//
//    //speler aan de beurt ==> dc.geefSpelerAanBeurt(), int van 0 tot aantalSpelers-1
//
//    UseCase4V2(DomeinController dc, int aantalSpelers) {
//        this.dc = dc;
//        this.aantalSpelers = aantalSpelers;
//    }
//
//    // Vragen aan speler of hij hulp wilt
//    void bereidSpelVoor() {
//        int monster = 0;
//        int speler = 0;
//
//        //Vragen aan de speler of hij hulp wilt met het bevechten van het monster, zoja, mogen anderen hem helpen, anders niet
//        System.out.println(LanguageResource.getString("usecase4.ask.help"));
//        String help = SCAN.next().toLowerCase();
//        while (!help.equals(LanguageResource.getString("yes")) && !help.equals(LanguageResource.getString("no"))) {
//            System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
//            System.out.println(LanguageResource.getString("usecase4.ask.help"));
//            help = SCAN.next().toLowerCase();
//        }
//        // Vragen aan speler of hij een bonuskaart wilt spelen.
//        String bonuskaart;
//        do {
//            System.out.println(LanguageResource.getString("usecase4.ask.bonuscard"));
//            bonuskaart = SCAN.next().toLowerCase();
//            while (!bonuskaart.equals(LanguageResource.getString("yes")) && !bonuskaart.equals(LanguageResource.getString("no"))) {
//                System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
//                System.out.println(LanguageResource.getString("usecase4.ask.bonuscard"));
//                bonuskaart = SCAN.next().toLowerCase();
//            }
//            if (bonuskaart.equals(LanguageResource.getString("yes"))) {
//                UseCase5 uc5 = new UseCase5(this.dc);
//                uc5.speelKaart();
//            }
//        } while (bonuskaart.equals(LanguageResource.getString("yes")));
//        /**
//         * Methode die de tegenstanders in volgorde 3 keuze geeft en deze uitvoert.
//         */
//        int aantal = dc.geefSpelerAanBeurt() + 1;
//        List<Boolean> bool = new ArrayList<>();
//        List<Boolean> helptmee = new ArrayList<>();
//        for (int i = 0; i < aantalSpelers; i++) {
//            bool.add(false);
//            if (i == dc.geefSpelerAanBeurt()) {
//                helptmee.add(true);
//            } else {
//                helptmee.add(false);
//            }
//        }
//        //boolean[] bool = new boolean[aantalSpelers];
//        //boolean[] helptmee = new boolean[aantalSpelers];
//        //helptmee[dc.geefSpelerAanBeurt()] = true;
//        //Lus die alle spelers na de speler die vecht afgaat
//        for (int i = 0; i < aantalSpelers; i++) {
//            if (aantal < aantalSpelers) {
//                //De spelers blijven vragen welke optie hij of zij wilt nemen, tot de speler optie 3 neemt
//                boolean tryAgain = true;
//                while (tryAgain) {
//                    try {
//                        while (!bool.get(i)) {
//                            System.out.printf("%s%s%n", String.format("%s", ColorsOutput.kleur("blue") + dc.geefNaamSpeler(aantal) + ColorsOutput.reset()), LanguageResource.getString("usecase4.Monsterhelp"));
//                            System.out.println(LanguageResource.getString("usecase4.choices"));
//                            int keuze = SCAN.nextInt();
//                            if (keuze < 1 || keuze > 3) {
//                                throw new Exception();
//                            }
//                            switch (keuze) {
//                                case 1:
//                                    //Mag alleen gebeuren als de speler die vecht akkoord is gegaan dat hij hulp wilt
//                                    if (help.equals(LanguageResource.getString("yes"))) {
//                                        //aanpassen???
//                                        helpSpeler();
//                                        helptmee.remove(aantal);
//                                        helptmee.add(aantal, true);
//                                    } else {
//                                        System.err.println(LanguageResource.getString("exception.help"));
//                                    }
//                                    break;
//                                case 2:
//                                    helpMonster();
//                                    break;
//                                case 3:
//                                    bool.remove(i);
//                                    bool.add(i, true);
//                                    //bool[i] = true;
//                                    break;
//                            }
//                        }
//                        tryAgain = false;
//                    } catch (Exception e) {
//                        System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase2.choiceerror") + ColorsOutput.reset());
//                        SCAN.nextLine();
//                    }
//                }
//            } else {
//                aantal = -1;
//            }
//            aantal++;
//        }
//        String kaart;
//        //vragen of de speler nog een extra kaart wilt spelen, zoja, speel een kaart
//        do {
//            System.out.printf("%s%s%n", String.format("%s", ColorsOutput.kleur("blue") + dc.geefNaamSpeler(dc.geefSpelerAanBeurt()) + ColorsOutput.reset()), LanguageResource.getString("usecase4.ask.card"));
//            kaart = SCAN.next().toLowerCase();
//            while (!kaart.equals(LanguageResource.getString("yes")) && !kaart.equals(LanguageResource.getString("no"))) {
//                System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
//                System.out.println(LanguageResource.getString("usecase4.ask.bonuscard"));
//                kaart = SCAN.next().toLowerCase();
//            }
//            if (kaart.equals(LanguageResource.getString("yes"))) {
//                UseCase5 uc5 = new UseCase5(this.dc);
//                uc5.speelKaart();
//            }
//            //Het overzicht tonen voor het gevecht(hetgeen dat nog niet in orde is)
//        } while (kaart.equals(LanguageResource.getString("yes")));
//        System.out.println("\n" + dc.bovensteKaartToString());
//
//
//        if (LanguageResource.getLocale().toString().equals("nl")) {
//            System.out.printf("Het monster heeft een sterkte van %d en de speler een sterkte van %d%n%n", monster, speler);
//        }
//        if (LanguageResource.getLocale().toString().equals("en")) {
//            System.out.printf("The monster has level %d and the player has level %d%n%n", monster, speler);
//        }
//        if (LanguageResource.getLocale().toString().equals("fr")) {
//            System.out.printf("Le monstre a le niveau %d et le joueur a le niveau %d%n%n", monster, speler);
//        }
//        int sp = 0;
//        List<String> ret = dc.geefBeknopteSpelsituatie(/*helptmee[i]*/);
//        for (String str : ret) {
//            System.out.println(String.format("%s, %s",str, helptmee.get(sp) ? "vecht mee" : "vecht niet mee"));
//            sp++;
//        }
//
//    }
//
//    private void helpSpeler() {
//        UseCase5 uc5 = new UseCase5(this.dc);
//        uc5.speelKaart();
//    }
//
//    private void helpMonster() {
//        UseCase5 uc5 = new UseCase5(this.dc);
//        uc5.speelKaart();
//    }
//}
