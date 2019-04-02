package ui.cui.ucs;

import domein.DomeinController;
import domein.kaarten.kerkerkaarten.monsterbadstuff.BadStuff;
import language.LanguageResource;
import printer.ColorsOutput;
import java.security.SecureRandom;
import java.util.*;

/**
 * Deze UC nummer is onbekend en zal later worden aangepast
 * deze UC zal "Vecht met monster zijn"
 * UC6
 */

public class UseCase6 {
    private final DomeinController dc;

    private final Scanner SCAN = new Scanner(System.in);

    public UseCase6(DomeinController dc) {
        this.dc = dc;

    }

    public void vechtMetMonster(int battleBonusMonster, int battleBonusSpeler, List<Boolean> helptmee) {
        boolean gevecht = dc.gevechtResultaat(battleBonusMonster, battleBonusSpeler);
        int id = dc.geefIdBovensteKaart();
        //int levelMonster = Integer.parseInt(dc.geefMonsterAttribuut(id, "level").toString());

        if (!gevecht) {
            System.out.println(LanguageResource.getString("usecase6.playerwon"));
            verhoogLevelsGewonnen(id, helptmee);

        } else {
            System.out.println(LanguageResource.getString("usecase6.monsterwon"));
            vraagKaartSpelen("usecase6.askplaycard");
            ontsnappen(id);

        }
    }

    private int gooiDobbelsteen(){
        return new SecureRandom().nextInt(5) + 1;
    }

    private void verhoogLevelsGewonnen(int id, List<Boolean> helptmee){
        int levelsUp = Integer.parseInt(dc.geefMonsterAttribuut(id, "levelsUp").toString());
        for(int i = 0; i < helptmee.size(); i++){
            if(helptmee.get(i)){
                dc.verhoogLevel(dc.geefNaamSpeler(i), levelsUp);
            }
        }
    }

    private void vraagKaartSpelen(String output){
        String kaart;
        do {
            System.out.printf("%s%s%n", String.format("%s", ColorsOutput.kleur("blue") + dc.geefNaamSpeler(dc.geefSpelerAanBeurt()) + ColorsOutput.reset()), LanguageResource.getString(output));
            kaart = SCAN.next().toLowerCase();
            while (!kaart.equals(LanguageResource.getString("yes")) && !kaart.equals(LanguageResource.getString("no"))) {
                System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
                System.out.println(LanguageResource.getString(output));
                kaart = SCAN.next().toLowerCase();
            }
            if (kaart.equals(LanguageResource.getString("yes"))) {
                UseCase5 uc5 = new UseCase5(this.dc, dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
                uc5.speelKaart();
            }
        } while (kaart.equals(LanguageResource.getString("yes")));
    }

    private void ontsnappen(int id){
        int runAway = Integer.parseInt(dc.geefMonsterAttribuut(id,"RunAway").toString());
        //Speler ontsnapt
        if(/* Moet nog de methode outRun aangeroepen worden ivm grote databank ||*/gooiDobbelsteen() > 4 - runAway){
            System.out.println(LanguageResource.getString("usecase6.escape1"));
        }// Speler ontsnapt niet
        else{
            System.out.println(LanguageResource.getString("usecase6.escape2"));
            voerBadStuffUit(id);
        }
    }

    private void voerBadStuffUit(int id){

    }
}
