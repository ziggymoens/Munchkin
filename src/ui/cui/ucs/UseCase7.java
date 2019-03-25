package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;

import java.util.Scanner;

/**
 * Beheer kaarten in hand
 * UC7
 */
class UseCase7 {
    private final DomeinController dc;
    private final Scanner scan = new Scanner(System.in);

    UseCase7(DomeinController dc) {
        this.dc = dc;
    }

    public void beheerKaarten(String naam) {
        maakKeuze();
    }

    public void maakKeuze(){
        boolean tryAgain = true;
        while(tryAgain){
            try {
                System.out.println(LanguageResource.getString("usecase8.actions"));
                System.out.println(LanguageResource.getString("usecase8.action1"));
                System.out.println(LanguageResource.getString("usecase8.action2"));
                int keuze = scan.nextInt();
                if(keuze == 1 || keuze == 2) {
                    tryAgain = false;
                }
            }catch(Exception e){
                System.out.println("Foute keuze, probeer opnieuw");
            }
        }
    }

    public void overzichtAfleggen(){
    }

    public void overzichtVerkopen(){

    }
}

