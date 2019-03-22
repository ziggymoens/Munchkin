/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cui.ucs;

import domein.DomeinController;

import java.util.*;

import exceptions.SpelException;
import exceptions.SpelerException;
import printer.Printer;
import printer.ColorsOutput;
import language.LanguageResource;

/**
 * @author ziggy
 */
class UseCase2 {

    //Declaraties voor gehele usecase.
    private final DomeinController dc;
    private final Scanner SCAN;
    public static int beurt ;

    /**
     * Constructor voor Use Case 2.
     *
     * @param dc Domeincontroller van het spel aangemaakt in de StartUp
     */
    UseCase2(DomeinController dc) {
        this.dc = dc;
        SCAN = new Scanner(System.in);

    }

    /**
     * Methode die het spel start en de beurten afloopt
     *
     * @param aantalSpelers Het aantal spelers die meespelen
     */
    void speelSpel(int aantalSpelers) {
        try {
            dc.controleerVolgorde();
            dc.geefStartKaarten();
            System.out.println(String.format("De volgorde van de spelers is: %n%s%n", dc.geefInformatie()));
        } catch (SpelException | SpelerException e) {
            System.out.print(Printer.exceptionCatch("Spel/SpelerException", e));
        }
        try {
            while (niemandGewonnen()) {
                for (int i = 0; i < aantalSpelers; i++) {
                    if (niemandGewonnen()) {
                        String naam = dc.geefNaamSpeler(i);
                        beurt = i;
                        speelBeurt(naam);
                    }
                }
            }
        } catch (SpelException | SpelerException e) {
            System.out.print(Printer.exceptionCatch("Spel/SpelerException", e));
        }
        try {
            System.out.printf("%s: %s", LanguageResource.getString("end.won"), geefNaamWinnaar());
        } catch (SpelException | SpelerException e) {
            System.out.print(Printer.exceptionCatch("Spel/SpelerException", e));
        }

    }

    /**
     * Methode die de beurt laat spelen en de keuze laat maken tussen 3 opties
     *
     * @param naam De naam van de speler die aan beurt is
     */
    private void speelBeurt(String naam) {
        int keuze = 0;
        boolean tryAgain = true;
        while (tryAgain)
        try {
            System.out.printf("%s: %s%n", LanguageResource.getString("player.turn"), String.format("%s", ColorsOutput.achtergrond("red")+ ColorsOutput.decoration("reversed") + naam + ColorsOutput.reset()));
            printKeuze();
            keuze = SCAN.nextInt();
            if (keuze<1 || keuze>3){
                throw new Exception();
            }
            tryAgain=false;
        } catch (Exception e) {
            System.out.println(ColorsOutput.kleur("red")+LanguageResource.getString("usecase2.choiceinput")+ColorsOutput.reset());
            //System.out.println(Printer.exceptionCatch("Exception", e, false));
            SCAN.nextLine();
        }
        try {
            switch (keuze) {
                case 1:
                    try {
                        UseCase3 uc3 = new UseCase3(this.dc);
                        uc3.speelBeurt(naam);
                    } catch (Exception e) {
                        System.out.print(Printer.exceptionCatch("Exception", e, false));
                    }
                    break;
                case 2:
                    try {
                        UseCase8 uc8 = new UseCase8(this.dc);
                        uc8.spelOpslaan();
                    } catch (Exception e) {
                        System.out.print(Printer.exceptionCatch("Exception", e, false));
                    }
                    break;
                case 3:
                    try {
                        System.out.println(Printer.printGreen("gamestop"));
                        System.exit(0);
                    } catch (Exception e) {
                        System.out.print(Printer.exceptionCatch("Exception", e, false));
                    }
                    break;
                default:
                    throw new IllegalArgumentException("usecase2.choiceerror");
            }
        }catch (IllegalArgumentException e){
            System.out.print(Printer.exceptionCatch("IllegalArgumentException", e));
        }
    }

    /**
     * Methode die vraagt aan het spel of iemand al gewonnen is
     *
     * @return Boolean als iemand gewonnen heeft = true
     */
    private boolean niemandGewonnen() {
        return dc.niemandGewonnen();
    }

    /**
     * Methode die de naam van de winnaar terug geeft
     *
     * @return De naam van de winnaar van het spel
     */
    private String geefNaamWinnaar() {
        return dc.geefNaamWinnaar();
    }

    private void printKeuze() {
        System.out.printf("%s%n"
                + "1) %s%n"
                + "2) %s%n"
                + "3) %s%n", LanguageResource.getString("turn.choice"), LanguageResource.getString("turn.play"), LanguageResource.getString("turn.save"), LanguageResource.getString("turn.stop"));
    }

}
