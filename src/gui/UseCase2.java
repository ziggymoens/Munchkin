/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
public class UseCase2 {

    //Declaraties voor gehele usecase.
    private final DomeinController dc;
    private final Scanner SCAN;

    /**
     * Constructor voor Use Case 2.
     *
     * @param dc Domeincontroller van het spel aangemaakt in de StartUp
     */
    public UseCase2(DomeinController dc) {
        this.dc = dc;
        SCAN = new Scanner(System.in);

    }

    /**
     * Methode die het spel start en de beurten afloopt
     *
     * @param aantalSpelers Het aantal spelers die meespelen
     */
    public void speelSpel(int aantalSpelers) {
        try {
            dc.controleerVolgorde();
            dc.geefStartKaarten();
            System.out.println(String.format("De volgorde van de spelers is: %n%s%n", dc.geefInformatie()));
        } catch (SpelException | SpelerException e) {
            Printer.exceptionCatch("Spel/SpelerException", e);
        }
        try {
            while (niemandGewonnen()) {
                for (int i = 0; i < aantalSpelers; i++) {
                    if (niemandGewonnen()) {
                        String naam = dc.geefNaamSpeler(i);
                        speelBeurt(naam);
                    }
                }
            }
        } catch (SpelException | SpelerException e) {
            Printer.exceptionCatch("Spel/SpelerException", e);
        }
        try {
            System.out.printf("%s: %s", LanguageResource.getString("end.won"), geefNaamWinnaar());
        } catch (SpelException | SpelerException e) {
            Printer.exceptionCatch("Spel/SpelerException", e);
        }

    }

    /**
     * Methode die de beurt laat spelen en de keuze laat maken tussen 3 opties
     *
     * @param naam De naam van de speler die aan beurt is
     */
    private void speelBeurt(String naam) {
        System.out.printf("%s: %s%n", LanguageResource.getString("player.turn"), naam);
        printKeuze();
        int keuze = SCAN.nextInt();
        while (keuze < 1 || keuze > 3) {
            System.out.println(ColorsOutput.kleur("red") + LanguageResource.getString("usecase2.choiceturn") + ColorsOutput.reset());
            printKeuze();
            keuze = SCAN.nextInt();
        }
        switch (keuze) {
            case 1:
                try {
                    UseCase3 uc3 = new UseCase3(this.dc);
                    uc3.speelBeurt(naam);
                } catch (Exception e) {
                    Printer.exceptionCatch("Exception", e);
                }
                break;
            case 2:
                try {
                    UseCase8 uc8 = new UseCase8(this.dc);
                    uc8.spelOpslaan();
                } catch (Exception e) {
                    Printer.exceptionCatch("Exception", e);
                }
                break;
            case 3:
                try{
                Printer.printGreen("usecase2.gamestop");
                System.exit(0);
                }catch (Exception e){
                    Printer.exceptionCatch("Exception", e);
                }
                break;
            default:
                break;
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
