package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.Scanner;

/**
 * @author ziggy
 */

@SuppressWarnings("deprecation")
class UseCase9 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);

    /**
     * Constructor voor UC9
     *
     * @param dc de gebruikte dc van het huidige spel
     */
    UseCase9(DomeinController dc) {
        this.dc = dc;
    }

    /**
     * algemene methode die de andere methodes aanroept
     */
    void spelLaden() {
        int keuze = maakKeuze();
        th1.start();
        th1.suspend();
        try {
            th1.resume();
            laadSpel(keuze);
            th1.stop();
        } catch (Exception e) {
            th1.suspend();
        }
        System.out.println("\n" + Printer.printGreen("usecase9.load"));
        speelSpel();
    }

    /**
     * Methode die de gebruiker een spel laat kiezen om te laden
     *
     * @return het gekozen spel op te laden
     */
    private int maakKeuze() {
        int keuze = -1;
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                overzicht();
                System.out.printf("%n%s ", LanguageResource.getString("usecase9.choice"));
                keuze = SCAN.nextInt();
                if (!dc.bestaatSpel(keuze)) {
                    throw new Exception();
                }
                tryAgain = false;
            } catch (Exception e) {
                System.out.println(Printer.printRed(LanguageResource.getString("usecase2.choiceinput")));
                SCAN.nextLine();
            }
        }
        return keuze;
    }

    /**
     * Methode geeft een overzicht van de spellen in de databank
     */
    private void overzicht() {
        System.out.println(ColorsOutput.decoration("bold") + LanguageResource.getString("usecase9.makechoice") + ColorsOutput.reset());
        for (String lijn : dc.geefOverzichtSpelen()) {

            System.out.println(lijn);
        }
    }

    /**
     * methode die het spel gaat laden
     *
     * @param id id van het spel
     */
    private void laadSpel(int id) {
        try {
            dc.laadSpel(id);
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception", e, false));
        }
    }

    /**
     * methode die na het laden det spel terug verder laat spelen
     */
    private void speelSpel() {
        UseCase2 uc2 = new UseCase2(dc);
        uc2.speelSpel();
    }

    /**
     * de loading thread
     */
    private final Thread th1 = new Thread(() -> {
        System.out.print("\nLoading ");
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.out.println(Printer.exceptionCatch("InterruptedException", ex, false));
            }
            System.out.print(".");
        }
    });
}