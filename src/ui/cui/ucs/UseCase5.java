package ui.cui.ucs;

import domein.DomeinController;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import exceptions.SpelerException;
import language.LanguageResource;
import printer.ColorsOutput;
import java.util.List;

public class UseCase5 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);
    private int spelerAanBeurt;
    private boolean help;
    private boolean monster;
    private List<Boolean> helptmee;
    private int kaart;


    public UseCase5(DomeinController dc, boolean monster) {
        this.dc = dc;
        this.monster = monster;
    }

    void speelKaart(int spab) {
        this.help = dc.getHelp().equalsIgnoreCase(LanguageResource.getString("yes"));
        this.helptmee = dc.gethelptmee();
        dc.setSpelerAanBeurtGevecht(spab);
        this.spelerAanBeurt = dc.getSpelerAanBeurtGevecht();
        //System.out.println(dc.toonOverzichtKaartenInHand(spelerAanBeurt));
        System.out.println("");
        System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("purple") + "%s%n", dc.toonOverzichtKaartenInHand2(spelerAanBeurt).get(0) + ColorsOutput.reset());
        for(int i = 1; i < dc.toonOverzichtKaartenInHand2(spelerAanBeurt).size(); i++){
            System.out.println(dc.toonOverzichtKaartenInHand2(spelerAanBeurt).get(i));
        }
        System.out.println("");
        boolean tryAgain = true;
        while(tryAgain) {
            try {
                System.out.println(LanguageResource.getString("usecase5.choicecard"));
                kaart = SCAN.nextInt();
                int end = dc.toonOverzichtKaartenInHand2(spelerAanBeurt).size();
                if (kaart <= 0 || kaart > end) {
                    throw new SpelerException("exception.wronginput");
                }
                tryAgain = false;
            } catch (InputMismatchException e) {
                System.err.println(LanguageResource.getString("exception.inputmismatch"));
                SCAN.nextLine();
            } catch (SpelerException e) {
                System.out.println(LanguageResource.getString("exception.wronginput"));
                SCAN.nextLine();
            }
        }

        System.out.println(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1));
        //De speler mag de kaart spelen
        if(dc.validatieKaartSpeler(kaart,monster) && dc.validatieKaartItems(kaart)){
            if(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof Curse){
                curseKaart();
            }else{
                if(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof Monster){
                    //monsterKaart(gespeeldeKaart);
                    dc.speelMonster(kaart, monster);
                }
                if(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof ConsumablesKerker || dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof ConsumablesSchat){
                    //consumablesKaart(gespeeldeKaart);
                    dc.speelConsumable(kaart);
                }
                if(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof Equipment || dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1) instanceof Race){
                    //itemsbijvoegen(gespeeldeKaart);
                    dc.itemsBijvoegen(kaart);
                }
            }
            dc.voegkaartonderaanstapeltoe(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1));
            dc.geefSpeler(spelerAanBeurt).getKaarten().remove(kaart - 1);
            //dc.geefBeknopteSpelsituatie();

        }//De speler mag de kaart niet spelen
        else{
            System.out.println("");
            System.err.println(LanguageResource.getString("usecase5.invalidcard"));
            speelKaart(spab);
        }
    }

    private void curseKaart(){
        int end = overzichthelpendespelers().size();
        for(int i = 0; i < end;i++) {
            System.out.println(overzichthelpendespelers().get(i));
        }
        int speler = 0;
        boolean tryAgain = true;
        while(tryAgain) {
            try {
                System.out.println(LanguageResource.getString("usecase5.chooseplayer"));
                speler = SCAN.nextInt();
                if (speler <= 0 || speler > end) {
                    System.out.println("you made it");
                    throw new SpelerException("exception.wronginput");
                }
                tryAgain = false;
            } catch (InputMismatchException e) {
                System.err.println(LanguageResource.getString("exception.inputmismatch"));
                SCAN.nextLine();
            } catch (SpelerException e) {
                System.out.println(LanguageResource.getString("exception.wronginput"));
                SCAN.nextLine();
            }
        }
        dc.speelCurse(speler, kaart);
    }

    private List<String> overzichthelpendespelers(){
        int aantal = 1;
        List<String> output = new ArrayList<>();
        output.add(LanguageResource.getString("usecase5.summaryhelp"));
        for(int i = 0; i < helptmee.size();i++){
            if(helptmee.get(i)){
                output.add(String.format("%d) %s",aantal, dc.geefNaamSpeler(i)));
                aantal++;
            }
        }
        return output;
    }

    /*private void monsterKaart(Kaart gespeeldeKaart){
        if(!monster || helptmee.get(spelerAanBeurt)){
            dc.setSpelerBattlePoints(dc.getSpelerBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        }else{
            dc.setMonsterBattlePoints(dc.getMonsterBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        }
    }*/

    /*private void consumablesKaart(Kaart gespeeldeKaart){
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
    }*/

    /*private void itemsbijvoegen(Kaart gespeeldeKaart){
        dc.geefSpeler(spelerAanBeurt).getItems().add(gespeeldeKaart);
    }*/

    /*private void curseuitvoeren(int speler, Kaart gespeeldeKaart){
        dc.geefSpeler(speler).setLevel(dc.geefLevel(speler) - ((Curse) gespeeldeKaart).getLevelLost());
    }*/
}