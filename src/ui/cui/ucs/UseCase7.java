package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;

import java.util.Scanner;

/**
 * Beheer kaarten in hand
 * UC7
 */
class UseCase7 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);

    UseCase7(DomeinController dc) {
        this.dc = dc;
    }

    void beheerKaarten(String naam) {
        maakKeuze(naam);
    }

    private void maakKeuze(String naam) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(String.format("%s %s",naam, LanguageResource.getString("usecase8.actions")));
                System.out.println(String.format("1) %s", LanguageResource.getString("usecase8.action1")));
                System.out.println(String.format("2) %s", LanguageResource.getString("usecase8.action2")));
                int keuze = SCAN.nextInt();
                if (keuze != 1 && keuze != 2) {
                    throw new Exception();
                }
                tryAgain = false;
                verwerkKeuze(keuze);
            } catch (Exception e) {
                System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + "Foute keuze, probeer opnieuw" + ColorsOutput.reset());
                SCAN.nextLine();

            }
        }
    }

    private void verwerkKeuze(int keuze) {
        switch (keuze) {
            case 1:
                break;
            case 2:
                break;

        }
    }

    public void overzichtAfleggen() {
    }

    public void overzichtVerkopen() {

    }
}

