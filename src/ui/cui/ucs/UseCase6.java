package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import java.util.Scanner;
import java.security.SecureRandom;

/**
 * Deze UC nummer is onbekend en zal later worden aangepast
 * deze UC zal "Vecht met monster zijn"
 * UC6
 */

public class UseCase6 {
    private final DomeinController dc;
    private final int monster;
    private final int speler;
    private final Scanner SCAN = new Scanner(System.in);

    public UseCase6(DomeinController dc, int monster, int speler) {
        this.dc = dc;
        this.monster = monster;
        this.speler = speler;
    }

    public void vechtMetMonster() {
        boolean gevecht = dc.gevechtResultaat(monster, speler);
        int spelerAanBeurt = dc.geefSpelerAanBeurt();
        int id = dc.geefIdBovensteKaart();
        String kaart;
        int levelsUp;
        levelsUp = dc.geefMonsterLevelsUp(id);
        //als de speler gewonnen heeft
        if (!gevecht) {
            dc.verhoogLevel(dc.geefNaamSpeler(spelerAanBeurt), levelsUp);
        } else {
            do {
                System.out.printf("%s%s%n", String.format("%s", ColorsOutput.kleur("blue") + dc.geefNaamSpeler(dc.geefSpelerAanBeurt()) + ColorsOutput.reset()), LanguageResource.getString("usecase4.ask.card"));
                kaart = SCAN.next().toLowerCase();
                while (!kaart.equals(LanguageResource.getString("yes")) && !kaart.equals(LanguageResource.getString("no"))) {
                    System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
                    System.out.println(LanguageResource.getString("usecase4.ask.bonuscard"));
                    kaart = SCAN.next().toLowerCase();
                }
                if (kaart.equals(LanguageResource.getString("yes"))) {
                    UseCase5 uc5 = new UseCase5(this.dc);
                    uc5.speelKaart();
                }
            } while (kaart.equals(LanguageResource.getString("yes")));
                //Speler ontsnapt
                if(gooiDobbelsteen() > 4 /*getal moet nog aangepast worden naar een eventueel gespeelde kaart dat +x to run away geeft*/){
                    System.out.println("Je bent ontsnapt");
                }// Speler ontsnapt niet
                else{
                    System.out.println("Je bent niet ontsnapt");
                }
        }
    }

    public int gooiDobbelsteen(){
        SecureRandom ran = new SecureRandom();
        int worp;
        worp = ran.nextInt(5) + 1;
        return worp;
    }
}
