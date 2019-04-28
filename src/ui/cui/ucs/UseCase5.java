package ui.cui.ucs;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Scanner;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import language.LanguageResource;
import java.util.List;

public class UseCase5 {

    private final DomeinController dc;
    private int spelerAanBeurt;
    private final Scanner SCAN = new Scanner(System.in);
    private boolean help;
    private boolean monster;
    private List<Boolean> helptmee;


    public UseCase5(DomeinController dc, int i, String help, boolean monster, List<Boolean> helptmee) {
        this.dc = dc;
        this.spelerAanBeurt = i;
        this.help = help.equalsIgnoreCase(LanguageResource.getString("yes"));
        this.monster = monster;
        this.helptmee = helptmee;
    }

    void speelKaart() {
        System.out.println(dc.toonOverzichtKaartenInHand(spelerAanBeurt));
        int kaart = 0;
        Boolean tryagain = false;
        while(!tryagain){
            try{
                System.out.println(LanguageResource.getString("usecase5.choicecard"));
                kaart = SCAN.nextInt();
                tryagain = true;
            }catch(InputMismatchException e){
                System.err.println("Je moet de juiste input geven");
                tryagain = false;
            }
        }
        System.out.println(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1));
        //De speler mag de kaart spelen
        if(validatieKaartSpeler(kaart) && validatieKaartItems(kaart)){
            Kaart gespeeldeKaart = dc.geefSpeler(spelerAanBeurt).getKaarten().remove(kaart - 1);
            if(gespeeldeKaart instanceof Curse){
                curseKaart(gespeeldeKaart);
            }else{
                if(gespeeldeKaart instanceof Monster){
                    monsterKaart(gespeeldeKaart);
                }
                if(gespeeldeKaart instanceof ConsumablesKerker || gespeeldeKaart instanceof ConsumablesSchat){
                    consumablesKaart(gespeeldeKaart);
                }
                if(gespeeldeKaart instanceof Equipment || gespeeldeKaart instanceof Race){
                    itemsbijvoegen(gespeeldeKaart);
                }
            }

        }//De speler mag de kaart niet spelen
        else{
            System.out.println("Kaart mag niet gespeeld worden");
            speelKaart();
        }
    }

    private Boolean validatieKaartSpeler(int kaart){
        Kaart kr = dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1);
        //Kaarten die de Speler mag spelen
        if(dc.geefSpelerAanBeurt() == spelerAanBeurt){
            if(kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Equipment || kr instanceof Race || kr instanceof Monster){
                if(kr instanceof Monster){
                    if(monster){
                        return true;
                    }else{
                        return false;
                    }
                }
                return true;
            }
            return false;
            //Kaarten die de Tegenspelers mogen spelen
        }else{
            if(kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Monster || kr instanceof Curse){
                //Aanpassen dat als speler geen hulp wou, alleen negatieve ConsumablesKerker gespeeld mag worden
                if(help){
                    if(kr instanceof ConsumablesKerker){
                        if(((ConsumablesKerker) kr).getBonus() < 0){
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private Boolean validatieKaartItems(int kaart){
        int aantalWapens = 0;
        List<Kaart> items = dc.geefSpeler(spelerAanBeurt).getItems();
        Kaart kr = dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1);
        if(items instanceof  Race && kr instanceof Race){
            return false;
        }
        for(int i = 0; i < items.size();i++){
            if(items.get(i) instanceof Equipment){
                Kaart kr2 = items.get(i);
                if(((Equipment) kr2).getType().equals("Head") && ((Equipment) kr).getType().equals("Head")){
                    return false;
                }
                if(((Equipment) kr2).getType().equals("Armor") && ((Equipment) kr).getType().equals("Armor")){
                    return false;
                }
                if(((Equipment) kr2).getType().equals("Foot") && ((Equipment) kr).getType().equals("Foot")){
                    return false;
                }
                if(((Equipment) kr2).getType().equals("Weapon")){
                    aantalWapens += 1;
                }
            }
        }
        if(aantalWapens == 2 && ((Equipment) kr).getType().equals("Weapon")){
            return false;
        }
        return true;
    }

    private void curseKaart(Kaart gespeeldeKaart){


    }

    private void monsterKaart(Kaart gespeeldeKaart){
        if(monster || helptmee.get(spelerAanBeurt)){
            dc.setSpelerBattlePoints(dc.getSpelerBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        }else{
            dc.setMonsterBattlePoints(dc.getMonsterBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        }
    }

    private void consumablesKaart(Kaart gespeeldeKaart){
        if(helptmee.get(spelerAanBeurt)){
            if(gespeeldeKaart instanceof ConsumablesSchat){
                dc.setSpelerBattlePoints(dc.getSpelerBattlePoints() + ((ConsumablesSchat) gespeeldeKaart).getBattleBonus());
            }
            if(gespeeldeKaart instanceof ConsumablesKerker){
                dc.setSpelerBattlePoints(dc.getSpelerBattlePoints() + ((ConsumablesKerker) gespeeldeKaart).getBonus());
            }
        }else{
            if(gespeeldeKaart instanceof ConsumablesSchat){
                dc.setMonsterBattlePoints(dc.getMonsterBattlePoints() + ((ConsumablesSchat) gespeeldeKaart).getBattleBonus());
            }
            if(gespeeldeKaart instanceof ConsumablesKerker){
                dc.setMonsterBattlePoints(dc.getMonsterBattlePoints() + ((ConsumablesKerker) gespeeldeKaart).getBonus());
            }
        }
    }

    private void itemsbijvoegen(Kaart gespeeldeKaart){
        dc.geefSpeler(spelerAanBeurt).getItems().add(gespeeldeKaart);
    }
}