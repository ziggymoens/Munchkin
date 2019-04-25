package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
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
        //toonOverzicht();
        int keuze = maakKeuze();
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

    private int maakKeuze(){
        int keuze = -1;
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                do {
                    System.out.println(ColorsOutput.decoration("bold") + LanguageResource.getString("usecase9.makechoice") + ColorsOutput.reset());
                    for (String lijn : dc.geefOverzichtSpelen()){
                        System.out.println(lijn);
                    }
                    System.out.printf("%n%s ", LanguageResource.getString("usecase9.choice"));
                    keuze = SCAN.nextInt();
                    tryAgain = false;

                } while (!dc.bestaatSpel(keuze));
            } catch (Exception e) {
                SCAN.nextLine();
                System.out.println(Printer.exceptionCatch("Exception", e, false));
            }
        }
        return keuze;
    }

    private void laadSpel(int id){
        th1.start();
        th1.suspend();
        try{
            th1.resume();
            dc.laadSpel(id);
            UseCase2 uc2 = new UseCase2(dc);
            uc2.speelSpel(dc.geefAantalSpelers());
            th1.stop();
        }catch (Exception e){
            th1.suspend();
        }

        //dc.verwijderOpgeslagenSpel(id);


    }
    private final Thread th1 = new Thread(() -> {
        System.out.print("\nLoading ");
        for (int i = 0; i < 17; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.out.println(Printer.exceptionCatch("InterruptedException", ex, false));
            }
            System.out.print(".");
        }
        System.out.println();
        System.out.println(ColorsOutput.kleur("green") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase9.load") + ColorsOutput.reset());
        System.out.println();
    });
}
