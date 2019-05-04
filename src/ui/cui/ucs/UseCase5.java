package ui.cui.ucs;

import domein.DomeinController;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import domein.Spel;
import domein.kaarten.Kaart;
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
    private int spelerAanBeurt;
    private final Scanner SCAN = new Scanner(System.in);
    private boolean help;
    private boolean monster;
    private List<Boolean> helptmee;
    private int kaart;


    public UseCase5(DomeinController dc, int i, boolean monster) {
        this.dc = dc;
        this.spelerAanBeurt = i;
        this.monster = monster;
    }

    void speelKaart() {
        this.help = dc.getHelp().equalsIgnoreCase(LanguageResource.getString("yes"));
        this.helptmee = dc.gethelptmee();
        //System.out.println(dc.toonOverzichtKaartenInHand(spelerAanBeurt));
        System.out.println("");
        System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("purple") + "%s%n", dc.toonOverzichtKaartenInHand2(spelerAanBeurt).get(0) + ColorsOutput.reset());
        for(int i = 1; i < dc.toonOverzichtKaartenInHand2(spelerAanBeurt).size(); i++){
            System.out.println(dc.toonOverzichtKaartenInHand2(spelerAanBeurt).get(i));
        }
        System.out.println("");
        Boolean tryAgain = true;
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
            dc.voegkaartonderaanstapeltoe(gespeeldeKaart);
            //dc.geefBeknopteSpelsituatie();
        }//De speler mag de kaart niet spelen
        else{
            System.out.println("");
            System.err.println(LanguageResource.getString("usecase5.invalidcard"));
            speelKaart();
        }
    }

    private Boolean validatieKaartSpeler(int kaart){
        Kaart kr = dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1);
        //Kaarten die de Speler mag spelen
        if(dc.geefSpelerAanBeurt() == spelerAanBeurt){
            if(kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Equipment || kr instanceof Race || kr instanceof Monster){
                if(kr instanceof Monster){
                    return monster;
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
                        return ((ConsumablesKerker) kr).getBonus() >= 0;
                    }
                }else{
                    if(kr instanceof Curse){
                        if(helptmee.get(spelerAanBeurt)){
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
        for(int i = 0; i < items.size();i++){
            if(items.get(i) instanceof Race && kr instanceof Race){
                return false;
            }
            if(items.get(i) instanceof Equipment && kr instanceof Equipment){
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
        for(int i = 0; i < overzichthelpendespelers().size();i++) {
            System.out.println(overzichthelpendespelers().get(i));
        }
        int speler = 0;
        Boolean tryAgain = true;
        while(tryAgain) {
            try {
                System.out.println(LanguageResource.getString("usecase5.chooseplayer"));
                speler = SCAN.nextInt();
                int end = overzichthelpendespelers().size();
                if (speler <= 0 || speler > end) {
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
        curseuitvoeren(speler, gespeeldeKaart);

    }

    private void monsterKaart(Kaart gespeeldeKaart){
        if(!monster || helptmee.get(spelerAanBeurt)){
            dc.setSpelerBattlePoints(dc.getSpelerBattlePoints() + ((Monster) gespeeldeKaart).getLevel());
        }else{
            System.out.println("Er wordt " + ((Monster) gespeeldeKaart).getLevel() + "bij het monster (" + dc.getMonsterBattlePoints() + ") geteld");
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

    private void curseuitvoeren(int speler, Kaart gespeeldeKaart){
        dc.geefSpeler(speler).setLevel(dc.geefLevel(speler) - ((Curse) gespeeldeKaart).getLevelLost());
    }
}