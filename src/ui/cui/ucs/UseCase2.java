/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cui.ucs;

import domein.DomeinController;
import exceptions.SpelException;
import exceptions.SpelerException;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.Scanner;

/**
 * KLAAR -- NIET
 * CONTROLE KILI --
 * CONTROLE JONA --
 *
 * @author ziggy
 */
class UseCase2 {

    //Declaraties voor gehele usecase.
    private final DomeinController dc;
    private final Scanner SCAN;
    private int aantalSpelers;
    private String naam;

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
        this.aantalSpelers = aantalSpelers;
        try {
            //zet spelers in de volgorde zoals opgegeven in DR
            dc.controleerVolgorde();
            System.out.println(String.format("%s: %n%s%n",LanguageResource.getString("usecase2.volgorde"),dc.geefInformatie()));
        } catch (SpelException | SpelerException e) {
            System.out.print(Printer.exceptionCatch("Spel/SpelerException (UC2)", e));
        } catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC2)", e, false));
        }
        try {
            while (niemandGewonnen()) {
                int x = dc.geefSpelerAanBeurt();
                if(x >= aantalSpelers-1){
                    x = 0;
                }
                for (int i = x; i < aantalSpelers; i++) {
                    if (niemandGewonnen()) {
                        naam = dc.geefNaamSpeler(i);
                        dc.zetSpelerAanBeurt(i);
                        speelBeurt();
                    }
                }
            }
        } catch (SpelException | SpelerException e) {
            System.out.print(Printer.exceptionCatch("Spel/SpelerException (UC2)", e));
        } catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC2)", e, false));
        }
        try {
            System.out.printf("%s: %s", LanguageResource.getString("end.won"), geefNaamWinnaar());
        } catch (SpelException | SpelerException e) {
            System.out.print(Printer.exceptionCatch("Spel/SpelerException (UC2)", e));
        } catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC2)", e, false));
        }

    }

    /**
     * Methode die de beurt laat spelen en de keuze laat maken tussen 3 opties
     */
    private void speelBeurt() {
        int keuze = 0;
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.printf("%s: %s%n", LanguageResource.getString("player.turn"), String.format("%s", ColorsOutput.kleur("blue") + naam + ColorsOutput.reset()));
                printKeuze();
                keuze = SCAN.nextInt();
                if (keuze < 1 || keuze > 3) {
                    throw new Exception();
                }
                tryAgain = false;
            } catch (Exception e) {
                System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase2.choiceinput") + ColorsOutput.reset());
                //System.out.println(Printer.exceptionCatch("Exception", e, false));
                SCAN.nextLine();
            }
        }
        try {
            switch (keuze) {
                case 1:
                    try {
                        UseCase3 uc3 = new UseCase3(this.dc, aantalSpelers);
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
                    boolean tryAgain2 = true;
                    while (tryAgain2) {
                        try {
                            System.out.println(LanguageResource.getString("close") + String.format(" (%s, %s)", LanguageResource.getString("yes"), LanguageResource.getString("no")));
                            String yesno = SCAN.next();
                            if (!yesno.equals(LanguageResource.getString("yes")) && !yesno.equals(LanguageResource.getString("no"))) {
                                throw new Exception();
                            }
                            if (yesno.equals(LanguageResource.getString("yes"))) {
                                System.out.println(Printer.printGreen("gamestop"));
                                System.exit(0);
                            }
                            tryAgain2 = false;
                        } catch (Exception e) {
                            System.out.print(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase2.choiceerror") + ColorsOutput.reset());
                            SCAN.nextLine();
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("usecase2.choiceerror");
            }
        } catch (IllegalArgumentException e) {
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
