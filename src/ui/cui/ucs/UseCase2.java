package ui.cui.ucs;

import connection.Connection;
import domein.DomeinController;
import exceptions.SpelException;
import exceptions.SpelerException;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.*;

/**
 * KLAAR -- 30/4/2019
 * CONTROLE KILI 13/05/2019
 * CONTROLE JONA --
 *
 * @author ziggy
 */
class UseCase2 {

    //Declaraties voor gehele usecase.
    private final DomeinController dc;
    private final Scanner scan;
    private final Map<Integer, Runnable> beurtOpties;
    private String naam;

    /**
     * Constructor voor Use Case 2.
     *
     * @param dc Domeincontroller van het spel aangemaakt in de StartUp
     */
    UseCase2(DomeinController dc) {
        this.dc = dc;
        scan = new Scanner(System.in);
        beurtOpties = new HashMap<>();
        Runnable[] opties = {this::spelen, this::opslaan, this::stoppen};
        for (int i = 0; i < opties.length; i++) {
            beurtOpties.put(i + 1, opties[i]);
        }
    }

    /**
     * Methode die het spel start en de beurten afloopt
     */
    void speelSpel() {
        int aantalSpelers = dc.geefAantalSpelers();
        try {
            //zet spelers in de volgorde zoals opgegeven in DR
            dc.controleerVolgorde();
            System.out.println(String.format("%s: %n%s%n", LanguageResource.getString("usecase2.volgorde"), dc.geefInformatie()));
        } catch (SpelException | SpelerException e) {
            System.out.print(Printer.exceptionCatch("Spel/SpelerException (UC2)", e));
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC2)", e, false));
        }
        try {
            while (niemandGewonnen()) {
                int x = dc.geefSpelerAanBeurt();
                //speler aan beurt bepalen
                if (x >= aantalSpelers - 1) {
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
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC2)", e, false));
        }
        try {
            System.out.printf("%s: %s", LanguageResource.getString("end.won"), geefNaamWinnaar());
        } catch (SpelException | SpelerException e) {
            System.out.print(Printer.exceptionCatch("Spel/SpelerException (UC2)", e));
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC2)", e, false));
        }
    }

    /**
     * Methode die de beurt laat spelen en de keuze laat maken tussen 3 opties
     */
    private void speelBeurt() {
        int keuze = 0;
        boolean tryAgain = true;
        List<Integer> mog = new ArrayList<>();
        while (tryAgain) {
            try {
                System.out.printf("%s: %s%n", LanguageResource.getString("player.turn"), String.format("%s", ColorsOutput.kleur("blue") + naam + ColorsOutput.reset()));
                printKeuze();
                keuze = scan.nextInt();
                mog.add(1);
                mog.add(3);
                if (Connection.isConnected()) {
                    mog.add(2);
                }
                if (!mog.contains(keuze)) {
                    System.err.println("fout");
                    throw new Exception("choiceturn");
                }
                tryAgain = false;
            } catch (InputMismatchException e) {
                //e = new InputMismatchException("usecase2.choiceerror");
                System.out.print(Printer.exceptionCatch("InputMismatch (UC2)", e));
                scan.nextLine();
            } catch (Exception e) {
                //e = new Exception("usecase2.choiceinput");
                System.out.print(Printer.exceptionCatch("Exception (UC2)", e, false));
                scan.nextLine();
            }
        }
        try {
            beurtOpties.get(keuze).run();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC2)", e, false));
        }

    }

    /**
     * methode die de keuze speel beurt opstart
     */
    private void spelen() {
        try {
            UseCase3 uc3 = new UseCase3(this.dc);
            uc3.speelBeurt(naam);
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception", e, false));
        }
    }

    /**
     * methode die de keuze opslaan opstart
     */
    private void opslaan() {
        try {
            UseCase8 uc8 = new UseCase8(this.dc);
            uc8.spelOpslaan();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception", e, false));
        }
    }

    /**
     * methode die het stoppen van het spel regelt
     */
    private void stoppen() {
        boolean tryAgain2 = true;
        while (tryAgain2) {
            try {
                System.out.println(LanguageResource.getString("close") + String.format(" (%s, %s)", LanguageResource.getString("yes"), LanguageResource.getString("no")));
                String yesno = scan.next();
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
                scan.nextLine();
            }
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

    /**
     * methode die de keuzes van de gebruiker print
     */
    private void printKeuze() {
        System.out.printf("%s%n"
                + "1) %s%n"
                + "2) %s%s%n"
                + "3) %s%n", LanguageResource.getString("usecase2.turn.choice"), LanguageResource.getString("usecase2.turn.play"), LanguageResource.getString("usecase2.turn.save"), Connection.isConnected() ? "" : String.format(" ==> %s", LanguageResource.getString("notavailable")), LanguageResource.getString("usecase2.turn.stop"));
    }
}
