package ui.cui.ucs;

import domein.DomeinController;
import exceptions.SpelerException;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * KLAAR--
 * CONTROLE KILI 13/05/2019
 * CONTROLE JONA--
 *
 * @author Jonathan
 */
class UseCase5 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);
    private List<Boolean> helptmee;
    private int kaart;

    /**
     * Constructor voor uc5
     *
     * @param dc
     */
    UseCase5(DomeinController dc) {
        this.dc = dc;

    }

    /**
     * Methode die een kaart speelt
     *
     * @param spab spelerAanBeurt binnenin het gevecht, hangt los van algemene dc.geefSpelerAanBeurt
     */
    void speelKaart(int spab, boolean monster) {
        try {
            //boolean help = dc.getHelp().equalsIgnoreCase(LanguageResource.getString("yes"));
            this.helptmee = dc.gethelptmee();
            dc.setSpelerAanBeurtGevecht(spab);
            int spelerAanBeurt = dc.getSpelerAanBeurtGevecht();
            //System.out.println(dc.toonOverzichtKaartenInHand(spelerAanBeurt));
            kaartenInhandToString(spelerAanBeurt);
            boolean tryAgain = true;
            while (tryAgain) {
                try {
                    System.out.println("\n" + LanguageResource.getString("usecase5.choicecard"));
                    kaart = SCAN.nextInt();
                    int end = dc.toonOverzichtKaartenInHand2(spelerAanBeurt).size();
                    if (kaart <= 0 || kaart > end) {
                        throw new InputMismatchException();
                    }
                    tryAgain = false;
                } catch (InputMismatchException e) {
                    e = new InputMismatchException(LanguageResource.getString("exception.wronginput"));
                    System.out.println(Printer.exceptionCatch("InputMismatchException (UC5)", e));
                    SCAN.nextLine();
                    kaartenInhandToString(spelerAanBeurt);
                } catch (SpelerException e) {
                    e = new SpelerException(LanguageResource.getString("exception.wronginput"));
                    System.out.println(Printer.exceptionCatch("InputMismatchException (UC5)", e));
                    SCAN.nextLine();
                    kaartenInhandToString(spelerAanBeurt);
                } catch (Exception e) {
                    System.out.println(Printer.exceptionCatch("Exception (UC5)", e, false));
                    SCAN.nextLine();
                    kaartenInhandToString(spelerAanBeurt);
                }
            }
            System.out.println(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1));
            //De speler mag de kaart spelen
            if (dc.validatieKaartSpeler(kaart, monster) && dc.validatieKaartItems(kaart)) {
                if (dc.controleWelkeKaart(kaart, monster).equals("Curse")) {
                    curseKaart();
                }
                if (dc.controleWelkeKaart(kaart, monster).equals("Monster")) {
                    dc.speelMonster(kaart, monster);
                }
                if (dc.controleWelkeKaart(kaart, monster).equals("Consumable")) {
                    dc.speelConsumable(kaart);
                }
                if (dc.controleWelkeKaart(kaart, monster).equals("Race/Weapon")) {
                    dc.itemsBijvoegen(kaart);
                }
                dc.voegKaartOnderaanStapelToe(kaart);
                dc.geefSpeler(spelerAanBeurt).getKaarten().remove(kaart - 1);

            } else {
                //System.out.println(Printer.printRed(LanguageResource.getString("usecase5.invalidcard")));
                speelKaart(spab, monster);
                throw new Exception("usecase5.invalidcard");
            }
        } catch (Exception e) {
            e = new Exception(LanguageResource.getString("somethingWrong"));
            System.out.println(Printer.exceptionCatch("Exception (UC5)", e));
            SCAN.nextLine();
        }
    }

    /**
     * Methode die de kaarten in hand toont
     */

    private void kaartenInhandToString(int spelerAanBeurt) {
        try {
            System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("purple") + "%n%s%n", dc.toonOverzichtKaartenInHand2(spelerAanBeurt).get(0) + ColorsOutput.reset());
            for (int i = 1; i < dc.toonOverzichtKaartenInHand2(spelerAanBeurt).size(); i++) {
                System.out.println(dc.toonOverzichtKaartenInHand2(spelerAanBeurt).get(i));
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC5)", e, false));
        }
    }

    /**
     * Methdoe die een curseKaart speelt
     */
    private void curseKaart() {
        try {
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
                    e = new InputMismatchException(LanguageResource.getString("inputmismatch"));
                    System.out.println(Printer.exceptionCatch("InputMismatchException (UC5)", e));
                    SCAN.nextLine();
                } catch (SpelerException e) {
                    System.out.println(Printer.exceptionCatch(LanguageResource.getString("wronginput"), e));
                    SCAN.nextLine();
                }
            }
            dc.speelCurse(speler, kaart);
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch(LanguageResource.getString("somethingWrong"), e));
            SCAN.nextLine();
        }
    }

    /**
     * Methode die een overzicht van alle helpende spelers geeft
     *
     * @return een List van Strings met namen van helpende spelers
     */
    private List<String> overzichthelpendespelers() {
        int aantal = 1;
        List<String> output = new ArrayList<>();
        try {
            output.add(LanguageResource.getString("usecase5.summaryhelp"));
            for (int i = 0; i < helptmee.size(); i++) {
                if (helptmee.get(i)) {
                    output.add(String.format("%d) %s", aantal, dc.geefNaamSpeler(i)));
                    aantal++;
                }
            }
            return output;
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC5)", e, false));
        }
        return output;
    }
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
