package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.*;

class UseCase7V3 {
    private DomeinController dc;
    private final Map<Integer, Runnable> keuzes = new HashMap<>();
    private final Scanner scan = new Scanner(System.in);
    private String naam, stop;
    private final List<Integer> gekozenKaarten = new ArrayList<>();
    private int totaleWaarde, keuze, teller;

    UseCase7V3(DomeinController dc) {
        this.dc = dc;
        keuzes.put(1, this::naarItems);
        keuzes.put(2, this::verkopenEnWeggooien);
        keuzes.put(3, this::niksDoen);
        naam = dc.geefNaamSpeler(dc.geefSpelerAanBeurt());
    }

    void beheerKaarten() {
        gekozenKaarten.clear();
        overzichtOpties();
        int keuze = maakKeuze();
        keuzes.get(keuze).run();
        UniversalMethods.toonSituatie();
    }

    private void overzichtOpties() {
        System.out.println(String.format("%s %s", naam, LanguageResource.getString("usecase7.actions")));
        for (int i = 1; i <= 3; i++) {
            System.out.println(String.format("%d) %s", i, LanguageResource.getString(String.format("usecase7.action%d", i))));
        }
    }

    private int maakKeuze() {
        int keuze = 0;
        try {
            keuze = scan.nextInt();
            if (keuze < 1 || keuze > 3) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println(LanguageResource.getString("usecase2.choiceinput"));
            scan.nextLine();
        }
        return keuze;
    }

    private void niksDoen() {

    }

    private void verkopenEnWeggooien() {
        overzichtVerkopenWeggooien();
        kiesKaarten("WI");
    }

    private void naarItems() {
        overzichtItems();
        kiesKaarten("I");

    }

    private void overzichtVerkopenWeggooien() {
        try {
            System.out.printf("%n%s:%n%s%n%n%s:%n%s%n", ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.sellable") + ColorsOutput.reset(), dc.geefVerkoopbareKaarten(naam), ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.throwaway") + ColorsOutput.reset(), dc.geefNietVerkoopbareKaarten(naam));
            boolean verkoopWeg = UniversalMethods.controleJaNee("usecase7.asktosell");

            if (verkoopWeg) {
                System.out.println(LanguageResource.getString("usecase7.sellorthrow"));
                String keuze = scan.next();
                if (keuze.equalsIgnoreCase(LanguageResource.getString("usecase7.translationsell"))) {
                    System.out.printf("%s: %n%s%n", ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.sellable") + ColorsOutput.reset(), dc.geefVerkoopbareKaarten(naam));
                    kiesKaarten("V");
                    controleerWaarden();
                    if (totaleWaarde >= 1000) {
                        dc.verkoopKaarten(naam, gekozenKaarten);
                        System.out.println(ColorsOutput.kleur("green") + ColorsOutput.decoration("underline") + (String.format(LanguageResource.getString("usecase7.levelup"), totaleWaarde / 1000, totaleWaarde / 1000 > 1 ? "s" : "")) + ColorsOutput.reset());
                    } else
                        System.out.println(ColorsOutput.kleur("yellow") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.kleinewaarde") + ColorsOutput.reset());
                } else if (keuze.equalsIgnoreCase(LanguageResource.getString("usecase7.translationthrow"))) {
                    System.out.printf("%s: %n%s%n", ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.throwaway") + ColorsOutput.reset(), dc.geefNietVerkoopbareKaarten(naam));
                    kiesKaarten("W");
                    dc.gooiKaartenWeg(naam, gekozenKaarten);
                    System.out.print(Printer.printGreen(String.format("usecase7.succesdelete")));
                }
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
            scan.nextLine();
        }
    }

    private void overzichtItems() {
        try{
            System.out.printf("%s%n%s", ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.toitems")) + ColorsOutput.reset(), dc.geefKaartenKunnenNaarItems(naam));
        }catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
            scan.nextLine();
        }
        /*boolean items = UniversalMethods.controleJaNee("usecase7.itemsconfirm");
        if (items){
            kiesKaarten("I");
            dc.verplaatsNaarItems(naam, gekozenKaarten);
        }

         */
    }

    private void kiesKaarten(String type) {
        try{
        List<Integer> mogelijkheden;
        teller = 0;
        if (type.equals("V")) {
            mogelijkheden = dc.geefIdVerkoopbareKaarten(naam);
            keuze = 0;
            doeControle(LanguageResource.getString("usecase7.whattosell"), keuze, mogelijkheden, stop);
            dc.verkoopKaarten(naam, gekozenKaarten);

        } else if (type.equals("W")) {
            mogelijkheden = dc.geefIDKaartenInHand(naam);
            keuze = 0;
            doeControle(LanguageResource.getString("usecase7.whattothrow"), keuze, mogelijkheden, stop);
            dc.gooiKaartenWeg(naam, gekozenKaarten);
        } else if (type.equals("I")) {
            mogelijkheden = dc.geefIdsKunnenNaarItems(naam);
            int keuze = 0;
            doeControle(LanguageResource.getString("usecase7.whattoitems"), keuze, mogelijkheden, stop);
            dc.verplaatsNaarItems(naam, gekozenKaarten);
        }
        }catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
            scan.nextLine();
        }
    }

    private void controleerWaarden() {
        try {
            totaleWaarde = 0;
            for (Integer id : gekozenKaarten) {
                if (dc.getWaardeSchatkaart(id) == 999) {

                    System.out.println("ERROR");
                    break;
                }
                totaleWaarde += dc.getWaardeSchatkaart(id);
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
            scan.nextLine();
        }
    }

    private void doeControle(String print, int keuze, List<Integer> mogelijkheden, String stop) {
        do {
            System.out.printf("%s: %n", print);
            keuze = scan.nextInt();
            if (mogelijkheden.contains(keuze) && keuze != 999) {
                gekozenKaarten.add(keuze);
                teller++;
            } else if (keuze == 999) {
                do {
                    System.out.println(LanguageResource.getString("usecase7.stop"));
                    stop = scan.next();
                    if (stop.equalsIgnoreCase(LanguageResource.getString("yes"))) {
                        break;
                    } else if (stop.equalsIgnoreCase(LanguageResource.getString("no"))) {
                        continue;
                    } else {
                        System.out.println(LanguageResource.getString("usecase2.choiceerror"));
                    }
                } while (!stop.equalsIgnoreCase(LanguageResource.getString("yes")) || !stop.equalsIgnoreCase(LanguageResource.getString("no")));

            } else {
                System.out.println(LanguageResource.getString("usecase7.foutid"));
            }

        } while (keuze != 999 && teller < mogelijkheden.size());
    }
}
