package ui.cui.ucs;

import domein.DomeinController;
import exceptions.SpelerException;
import language.LanguageResource;
import printer.ColorsOutput;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
class UseCase5 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);
    private final boolean monster;
    private List<Boolean> helptmee;
    private int kaart;

    /**
     *
     * @param dc
     * @param monster
     */
    UseCase5(DomeinController dc, boolean monster) {
        this.dc = dc;
        this.monster = monster;
    }

    //Methode herschrijven jonathan!!! geen domeinobjecten
    //geen kaarten, spelers en spel!!!

    /**
     *
     * @param spab
     */
    void speelKaart(int spab) {
        boolean help = dc.getHelp().equalsIgnoreCase(LanguageResource.getString("yes"));
        this.helptmee = dc.gethelptmee();
        dc.setSpelerAanBeurtGevecht(spab);
        int spelerAanBeurt = dc.getSpelerAanBeurtGevecht();
        //System.out.println(dc.toonOverzichtKaartenInHand(spelerAanBeurt));
        System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("purple") + "%n%s%n", dc.toonOverzichtKaartenInHand2(spelerAanBeurt).get(0) + ColorsOutput.reset());
        for (int i = 1; i < dc.toonOverzichtKaartenInHand2(spelerAanBeurt).size(); i++) {
            System.out.println(dc.toonOverzichtKaartenInHand2(spelerAanBeurt).get(i));
        }
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println("\n" + LanguageResource.getString("usecase5.choicecard"));
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
        if (dc.validatieKaartSpeler(kaart, monster) && dc.validatieKaartItems(kaart)) {
            if(dc.controleWelkeKaart(kaart, monster).equals("Curse")){
                curseKaart();
            }
            if(dc.controleWelkeKaart(kaart, monster).equals("Monster")){
                dc.speelMonster(kaart, monster);
            }
            if(dc.controleWelkeKaart(kaart, monster).equals("Consumable")){
                dc.speelConsumable(kaart);
            }
            if(dc.controleWelkeKaart(kaart, monster).equals("Race/Weapon")){
                dc.itemsBijvoegen(kaart);
            }
            dc.voegkaartonderaanstapeltoe(kaart);
            dc.geefSpeler(spelerAanBeurt).getKaarten().remove(kaart - 1);

        }//De speler mag de kaart niet spelen
        else {
            //throw new Exception("usecase5.invalidcard");
            System.err.println("\n" + LanguageResource.getString("usecase5.invalidcard"));
            speelKaart(spab);
        }
    }

    /**
     *
     */
    private void curseKaart() {
        int end = overzichthelpendespelers().size();
        for (int i = 0; i < end; i++) {
            System.out.println(overzichthelpendespelers().get(i));
        }
        int speler = 0;
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("usecase5.chooseplayer"));
                speler = SCAN.nextInt();
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
        dc.speelCurse(speler, kaart);
    }

    /**
     *
     * @return
     */
    private List<String> overzichthelpendespelers() {
        int aantal = 1;
        List<String> output = new ArrayList<>();
        output.add(LanguageResource.getString("usecase5.summaryhelp"));
        for (int i = 0; i < helptmee.size(); i++) {
            if (helptmee.get(i)) {
                output.add(String.format("%d) %s", aantal, dc.geefNaamSpeler(i)));
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