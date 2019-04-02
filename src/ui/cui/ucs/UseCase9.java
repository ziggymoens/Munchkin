package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.Printer;

import java.util.List;
import java.util.Scanner;

/**
 * Laden van games
 * al ergens in code !!!!!
 */

public class UseCase9 {
    private DomeinController dc;
    private int aantalSpelen;
    private Scanner SCAN = new Scanner(System.in);

    public UseCase9(DomeinController dc) {
        this.dc = dc;
    }

    public void spelLaden() {
        toonOverzicht();
        String keuze = maakKeuze();
        laadSpel(keuze);
    }

    private void toonOverzicht(){
        String ret = "";
        List<String> overzicht = dc.geefOverzichtSpelen();
        int i = 1;
        for (String lijn : overzicht){
            ret += String.format("%d: %s", i, lijn);
        }
        aantalSpelen = overzicht.size();
        System.out.println(ret);
    }

    private String maakKeuze(){
        String keuze = "";
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                do {
                    System.out.println(LanguageResource.getString("usecase9.makechoice"));
                    System.out.println(dc.geefOverzichtSpelen());
                    keuze = SCAN.next();
                    tryAgain = false;
                    SCAN.nextLine();
                } while (!dc.bestaatSpel(keuze));
            } catch (Exception e) {
                System.out.println(Printer.exceptionCatch("Exception", e, false));
            }
        }
        return keuze;
    }

    private void laadSpel(String naam){
        dc.laadSpel(naam);
        dc.verwijderOpgeslagenSpel(naam);
    }
}
