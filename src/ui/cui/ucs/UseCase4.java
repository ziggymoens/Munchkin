package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author ziggy
 */
class UseCase4 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);
    private final int aantalSpelers;
    private String naam;


    //speler aan de beurt ==> dc.geefSpelerAanBeurt(), int van 0 tot aantalSpelers-1

    /**
     *
     * @param dc
     */
    UseCase4(DomeinController dc) {
        this.dc = dc;
        this.aantalSpelers = dc.geefAantalSpelers();
        this.naam = dc.geefNaamSpeler(dc.geefSpelerAanBeurt());
    }

    // Vragen aan speler of hij hulp wilt

    /**
     *
     */
    void bereidSpelVoor() {
        int id = dc.geefIdBovensteKaart();
        dc.setMonsterBattlePoints(Integer.parseInt(dc.geefMonsterAttribuut(id,"level").toString()));
        dc.setSpelerBattlePoints(dc.geefLevel(dc.geefSpelerAanBeurt()));

        //Vragen aan de speler of hij hulp wilt met het bevechten van het monster, zoja, mogen anderen hem helpen, anders niet
        dc.setHelp(hulpVragen());
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
        dc.setHelptmee(helptmee);
        // Vragen aan speler of hij een bonuskaart wilt spelen.
        vraagKaartSpelen(true);
        monsterKeuze(beurt);
        //vragen of de speler nog een extra kaart wilt spelen, zoja, speel een kaart
        vraagKaartSpelen(false);
        System.out.println("\n" + dc.bovensteKaartToString());
        //Het overzicht tonen voor het gevecht
        geefOverzichtGevecht();
    }

    /**
     *
     * @return
     */
    private String hulpVragen(){
        System.out.println(LanguageResource.getString("usecase4.ask.help"));
        String help = SCAN.next().toLowerCase();
        while (!help.equals(LanguageResource.getString("yes")) && !help.equals(LanguageResource.getString("no"))) {
            System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
            System.out.println(LanguageResource.getString("usecase4.ask.help"));
            help = SCAN.next().toLowerCase();
        }
        return help;
    }

    /**
     *
     * @param beurt
     */
    private void monsterKeuze(List<Boolean> beurt){
        List <Boolean> helptmee = dc.gethelptmee();
        String help = dc.getHelp();
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
                                    if(dc.geefIDKaartenInHand(dc.geefNaamSpeler(aantal)).size() == 0){
                                        System.err.println(LanguageResource.getString("usecase4.nocards"));
                                    }else {
                                        if (help.equals(LanguageResource.getString("yes"))) {
                                            //aanpassen???
                                            helptmee.remove(aantal);
                                            helptmee.add(aantal, true);
                                            speelKaart(aantal);
                                        } else {
                                            System.err.println(LanguageResource.getString("exception.help"));
                                        }
                                    }
                                    break;
                                case 2:
                                    if(dc.geefIDKaartenInHand(dc.geefNaamSpeler(aantal)).size() == 0){
                                        System.err.println(LanguageResource.getString("usecase4.nocards"));
                                    }else{
                                        speelKaart(aantal);
                                    }
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
        dc.setHelptmee(helptmee);
    }

    /**
     *
     */
    private void geefOverzichtGevecht(){
        List<Boolean> helptmee = dc.gethelptmee();
        System.out.printf(LanguageResource.getString("usecase4.battle") + "%n%n", dc.getMonsterBattlePoints(), dc.getSpelerBattlePoints());
        int waar = 0;
        List<String> ret = dc.geefBeknopteSpelsituatie();
        for (String str : ret) {
            System.out.println(String.format("%s, %s",str, helptmee.get(waar) ? "vecht mee" : "vecht niet mee"));
            waar++;
        }
        dc.setSpelerBattlePoints(dc.getSpelerBattlePoints() + dc.spelerLevels());
        vechtMonster();
    }

    /**
     *
     * @param i
     */
    private void speelKaart(int i) {
        UseCase5 uc5 = new UseCase5(this.dc, true);
        uc5.speelKaart(i);
    }

    /**
     *
     */
    private void vechtMonster() {
        UseCase6 uc6 = new UseCase6(this.dc);
        uc6.vechtMetMonster(aantalSpelers);
    }

    /**
     *
     * @param monster
     */
    private void vraagKaartSpelen(boolean monster){
        String kaart;
        do {
            System.out.printf("%s, %s%n", String.format("%s", ColorsOutput.kleur("blue") + naam + ColorsOutput.reset()), LanguageResource.getString("usecase4.ask.bonuscard"));
            kaart = SCAN.next().toLowerCase();
            while (!kaart.equals(LanguageResource.getString("yes")) && !kaart.equals(LanguageResource.getString("no"))) {
                System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
                System.out.println(LanguageResource.getString("usecase4.ask.bonuscard"));
                kaart = SCAN.next().toLowerCase();
            }
            if (kaart.equals(LanguageResource.getString("yes"))) {
                UseCase5 uc5 = new UseCase5(this.dc, monster);
                uc5.speelKaart(dc.geefSpelerAanBeurt());
            }
        } while (kaart.equals(LanguageResource.getString("yes")));
    }
}
