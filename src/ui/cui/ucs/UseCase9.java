package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.Scanner;

/**
 * Laden van games
 * al ergens in code !!!!!
 */

class UseCase9 {
    private DomeinController dc;
    private Scanner SCAN = new Scanner(System.in);

    UseCase9(DomeinController dc) {
        this.dc = dc;
    }

    void spelLaden() {
        int keuze = maakKeuze();
        th1.start();
        th1.suspend();
        try {
            th1.resume();
            laadSpel(keuze);
            th1.stop();
        }catch (Exception e){
            th1.suspend();
        }
        System.out.println("\n" + Printer.printGreen("usecase9.load"));
        speelSpel();
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
                System.out.println(LanguageResource.getString("usecase2.choiceinput"));
            }
        }
        return keuze;
    }

    private void laadSpel(int id){
        try{
            dc.laadSpel(id);
        }catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception", e, false));
        }
    }
    private void speelSpel(){
        UseCase2 uc2 = new UseCase2(dc);
        uc2.speelSpel(dc.geefAantalSpelers());
    }

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

//    private void toonOverzicht(){
//        String ret = "";
//        List<String> overzicht = dc.geefOverzichtSpelen();
//        int i = 1;
//        for (String lijn : overzicht){
//            ret += String.format("%d: %s", i, lijn);
//        }
//        aantalSpelen = overzicht.size();
//        System.out.println(ret);
//    }