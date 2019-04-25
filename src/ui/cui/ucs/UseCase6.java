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

    public void vechtMetMonster(int battleBonusMonster, int battleBonusSpeler, List<Boolean> helptmee, int aantalSpelers) {

        boolean gevecht = dc.gevechtResultaat(battleBonusMonster, battleBonusSpeler);
        int id = dc.geefIdBovensteKaart();
        //int levelMonster = Integer.parseInt(dc.geefMonsterAttribuut(id, "level").toString());
        //Als je het gevecht wint
        if (!gevecht) {
            System.out.println(LanguageResource.getString("usecase6.playerwon"));
            verhoogLevelsGewonnen(id, helptmee);
            geefSchatkaarten(id, aantalSpelers, helptmee);
        //Als je het gevecht verliest
        } else {
            System.out.println(LanguageResource.getString("usecase6.monsterwon"));
            ontsnappen(id);
        }
        System.out.println(dc.geefSpelsituatie().toString());
    }

    private void verhoogLevelsGewonnen(int id, List<Boolean> helptmee){
        int levelsUp = Integer.parseInt(dc.geefMonsterAttribuut(id, "levelsUp").toString());
        for(int i = 0; i < helptmee.size(); i++){
            if(helptmee.get(i)){
                dc.verhoogLevel(dc.geefNaamSpeler(i), levelsUp);
            }
        }
    }

    private void geefSchatkaarten(int id, int aantalSpelers, List<Boolean> helptmee){
        int aantal = dc.geefSpelerAanBeurt();
        int aantalSchatkaarten = Integer.parseInt(dc.geefMonsterAttribuut(id, "schatkaarten").toString());
        for(int i = 0; i < aantalSchatkaarten;i++){
            if(aantal < aantalSpelers){
                if(helptmee.get(aantal)){
                    dc.geefSchatkaart();
                }
            }else{
                aantal = -1;
            }
            aantal++;
        }
    }

    private void ontsnappen(int id){
        int runAway = Integer.parseInt(dc.geefMonsterAttribuut(id,"RunAway").toString());
        int worp = dc.gooiDobbelsteen();
        System.out.printf(LanguageResource.getString("usecase6.diceroll") + "%n", worp);
        //Speler ontsnapt
        if(worp > 4 - runAway){
            System.out.println(LanguageResource.getString("usecase6.escape1"));
        }// Speler ontsnapt niet
        else{
            System.out.println(LanguageResource.getString("usecase6.escape2"));
            voerBadStuffUit(id);
        }
    }

    private void voerBadStuffUit(int id){
        BadStuff bs = dc.geefBadStuff(id);
        //Checkt of het level dat je verliest groter is dan 0 => de badstuff is een level verliezen
        if(bs.getLevelsLost() > 0){
            System.out.printf(LanguageResource.getString("usecase6.loselevels") + "%n", bs.getLevelsLost());
            dc.verhoogLevel(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()), -bs.getLevelsLost());
        }//als je geen level verliest ontsnap je automatisch  (er is maar 1 kaart die geen level verliest in de kleine kaarten set)
        else{
            System.out.println(LanguageResource.getString("usecase6.escape1"));
        }
    }
}
