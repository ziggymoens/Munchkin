package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.List;
import java.util.Scanner;

class UniversalMethods {

    private static DomeinController dc;

    private static final Scanner SCAN = new Scanner(System.in);

    /**
     * methode die controleert of je gebruiker ja of nee antwoord in de juiste taal
     *
     * @param text De te vertalen vraag
     * @return boolean ja = true, nee = false
     */
    static Boolean controleJaNee(String text) {
        boolean tryAgain = true;
        String antw = "";
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString(text));
                antw = SCAN.next().toLowerCase();
                SCAN.nextLine();
                if (!antw.equals(LanguageResource.getString("yes")) && !antw.equals(LanguageResource.getString("no"))) {
                    throw new Exception("start.yesno");
                }
                tryAgain = false;
            } catch (Exception e) {
                System.out.println(Printer.exceptionCatch("InputException (UM)", e));
            }
        }
        return antw.equals(LanguageResource.getString("yes"));
    }

    /**
     * Toont de huidige spelsituatie van het spel
     */
    static void toonSituatie() {
        try {
            System.out.println();
            List<String> huidigeSituatie = dc.geefSpelsituatie();
            for (int i = 0; i < dc.geefAantalSpelers(); i++) {
                if (i == dc.geefSpelerAanBeurt()) {
                    System.out.print(ColorsOutput.achtergrond("yellow") + huidigeSituatie.get(i) + ColorsOutput.reset());
                } else {
                    System.out.print(huidigeSituatie.get(i));
                }
            }
            System.out.println();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Ecxeption (UC3)", e, false));
        }
    }

    /**
     * Setter voor de Domeincontroller van de klasse
     *
     * @param dc de gebruikte domeincontroller van het spel
     */
    static void setDc(DomeinController dc) {
        UniversalMethods.dc = dc;
    }
}
