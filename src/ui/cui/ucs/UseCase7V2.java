package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.*;

/**
 * Beheer kaarten in hand
 * UC7
 */

public class UseCase7V2 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);
    private int g;
    private int keuze;
    private List<Integer> ids = new ArrayList<>();
    private Map<Integer, Runnable> keuzes = new HashMap<>();
    private String naam;
    private boolean match = true;
    private int totWaarde = 0;

    public UseCase7V2(DomeinController dc) {
        this.dc = dc;
        keuzes.put(1, this::naarItems);
        keuzes.put(2, this::verkopenEnWeggooien);
        keuzes.put(3, this::niksDoen);
    }

    void beheerKaarten(String naam) {
        this.naam = naam;
        ids.clear();
        maakKeuze(naam);
        try {
            keuzes.get(keuze).run();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("usecase2.choiceerror");
        }
    }

    private void maakKeuze(String naam) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                keuze = 0;
                printMenu(naam);
                int k = SCAN.nextInt();
                if (k < 1 || k > 3) {
                    throw new Exception();
                }
                keuze = k;
            } catch (Exception e) {
                //INTERNATIONALISEREN !!!
                System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + "Foute keuze, probeer opnieuw" + ColorsOutput.reset());
                SCAN.nextLine();
            }
        }
    }

    private void printMenu(String naam) {
        System.out.println(String.format("%s %s", naam, LanguageResource.getString("usecase7.actions")));
        for (int i = 1; i <= 3; i++) {
            System.out.println(String.format("%d) %s", i, LanguageResource.getString(String.format("usecase7.action%d", i))));
        }
    }

    private void naarItems() {
        try {
            if (dc.getAantalKaarten(naam) >= 1) {
                System.out.println(ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.toitems")) + ColorsOutput.reset());
                System.out.println(String.format("%s", dc.geefKaartenKunnenNaarItems(naam)));
            } else {
                System.out.println(String.format("%s!", LanguageResource.getString("usecase7.nietgenoeg")));
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception", e, false));
        }
    }

    private void verkopenEnWeggooien() {
        try {
            overzichtVerkopenWeggooien();
            String antw = SCAN.next();
            if (antw.equalsIgnoreCase("yes") || antw.equalsIgnoreCase("oui") || antw.equalsIgnoreCase("ja")) {
                String antwoord;
                String[] keuzeKaart;
                do {
                    System.out.println(LanguageResource.getString("usecase7.sellorthrow"));
                    antwoord = SCAN.next();
                    if (antwoord.equalsIgnoreCase(LanguageResource.getString("usecase7.translationsell"))) {
                        verkopen();
                    } else if (antwoord.equalsIgnoreCase(LanguageResource.getString("usecase7.translationthrow"))) {
                        weggooien();
                    } else
                        match = true;
                } while (match);
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception", e, false));
        }
    }

    private void verkopen() {
        match = false;
        System.out.println(LanguageResource.getString("usecase7.sell"));
        System.out.println(String.format("%s: %n%s", LanguageResource.getString("usecase7.sellable"), dc.geefVerkoopbareKaarten(naam)));
        SCAN.nextLine();
        int teller = 0;

        // do-while waarin ids van de verkoopbare kaarten toegevoegd worden aan een List om die vervolgens uit te lezen om de waardes op te tellen
        // kan verbeterd worden -- OPTIMALISEREN
        int verkoopKaart;
        do {
            System.out.println(LanguageResource.getString("usecase7.whattosell"));
            verkoopKaart = SCAN.nextInt();
            if (dc.geefIdVerkoopbareKaarten(naam).contains(verkoopKaart) && verkoopKaart != 999) {
                ids.add(verkoopKaart);
                teller++;

            } else if (!dc.geefIdVerkoopbareKaarten(naam).contains(verkoopKaart) && verkoopKaart != 999) {
                System.out.println(LanguageResource.getString("usecase7.foutid"));

            }
            match = false;
        } while (verkoopKaart != 999 && teller <= dc.geefIdVerkoopbareKaarten(naam).size() - 1);
        System.out.println(ColorsOutput.kleur("blue") + "Dit zijn de ingegeven ids: " + ids + ColorsOutput.reset());
        System.out.println(ColorsOutput.kleur("blue") + "Dit zijn de ids van de te verkopen kaarten: " + dc.geefIdVerkoopbareKaarten(naam) + ColorsOutput.reset());
        System.out.println();
        for (int i = 0; i <= ids.size() - 1; i++) {
            //System.out.println(dc.getWaardeSchatkaart().get(i));
            totWaarde += dc.getWaardeSchatkaart().get(i);
        }

        //level verhogen adhv opgetelde waarde van de kaarten
        int gedeeldeWaarde = totWaarde / 1000;
        if (gedeeldeWaarde < 1) {
            System.out.println(ColorsOutput.kleur("yellow") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.kleinewaarde") + ColorsOutput.reset());
        } else {
            dc.verhoogLevel(naam, gedeeldeWaarde);
            System.out.println(ColorsOutput.kleur("green") + ColorsOutput.decoration("bold") + String.format(LanguageResource.getString("usecase7.levelup"), gedeeldeWaarde, gedeeldeWaarde > 1 ? "s" : "") + ColorsOutput.reset());
            System.out.println(ColorsOutput.kleur("blue") + ColorsOutput.decoration("bold") + dc.geefInformatie() + ColorsOutput.reset());
        }
        // System.out.println(ColorsOutput.kleur("white") + ColorsOutput.decoration("bold") + ColorsOutput.achtergrond("red") + " *** totale levels stijgen volgens deling: " + totWaarde/1000 + ColorsOutput.reset());
        // System.out.println(" *** Dit is de totale waarde: " + totWaarde);
    }

    private void weggooien() {
        System.out.println(" *** gekozen voor weggooien -- hardcode, nog aanpassen");
        System.out.println(LanguageResource.getString("usecase7.throw"));
        System.out.println(dc.geefNietVerkoopbareKaarten(naam));
        match = false;
    }

    private void overzichtVerkopenWeggooien(){
        System.out.println(ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.sellable") + ColorsOutput.reset()));
        System.out.println(dc.geefVerkoopbareKaarten(naam));
        System.out.println(ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.throwaway")) + ColorsOutput.reset());
        System.out.println(dc.geefNietVerkoopbareKaarten(naam));
        System.out.println(LanguageResource.getString("usecase7.asktosell"));
    }

    private void niksDoen() {
        System.out.println(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + ColorsOutput.decoration("reversed") + LanguageResource.getString("quit") + ColorsOutput.reset() + "\n");
    }
}
