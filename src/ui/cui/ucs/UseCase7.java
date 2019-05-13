package ui.cui.ucs;

import domein.DomeinController;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.*;

class UseCase7 {
    private final DomeinController dc;
    private final Map<Integer, Runnable> keuzes = new HashMap<>();
    private final Map<String, Runnable> kiezenType = new HashMap<>();
    private final Scanner scan = new Scanner(System.in);
    private final String naam;
    private final List<Integer> gekozenKaarten = new ArrayList<>();
    private int totaleWaarde;

    /**
     * Constructor voor use case 7
     *
     * @param dc de gebruikte domeincontroller
     */
    UseCase7(DomeinController dc) {
        this.dc = dc;
        keuzes.put(1, this::naarItems);
        keuzes.put(2, this::verkopenEnWeggooien);
        keuzes.put(3, this::niksDoen);
        kiezenType.put("V", this::kiesVerkoop);
        kiezenType.put("W", this::kiesWeggooien);
        kiezenType.put("I", this::kiesNaarItems);
        naam = dc.geefNaamSpeler(dc.geefSpelerAanBeurt());
    }

    /**
     * methode die kaarten beheert en gekozen kaarten cleart.
     * vraagt de keuze via de methode maakKeuze en steekt deze keuze in een map.
     * spelsituatie wordt getoond.
     */
    void beheerKaarten() {
        try {
            gekozenKaarten.clear();
            overzichtOpties();
            int keuze = maakKeuze();
            keuzes.get(keuze).run();
            UniversalMethods.toonSituatie();
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }

    /**
     * Methode die de mogelijkheden van de keuzes die in de map zitten overloopt
     */
    private void overzichtOpties() {
        try {
            System.out.println(String.format("%s %s", Printer.printBlue(naam), LanguageResource.getString("usecase7.actions")));
            for (int i = 1; i <= 3; i++) {
                System.out.println(String.format("%d) %s", i, LanguageResource.getString(String.format("usecase7.action%d", i))));
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }

    /**
     * Methode die de speler zijn keuze laat maken, gooit exceptie wanneer keuze niet juist is
     *
     * @return keuze van de speler
     */
    private int maakKeuze() {
        int keuze = 0;
        try {
            keuze = scan.nextInt();
            if (keuze < 1 || keuze > 3) {
                System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase2.choiceerror") + ColorsOutput.reset());
                overzichtOpties();
                maakKeuze();
            }
        } catch (Exception e) {
            e = new Exception("usecase2.choiceinput");
            System.out.println(Printer.exceptionCatch("InputException (UC7)", e));
            scan.nextLine();
        }
        return keuze;
    }

    /**
     * Methode waar letterlijk niks in gedaan wordt
     */
    @SuppressWarnings("EmptyMethod")
    private void niksDoen() {
        //empty
    }

    /**
     * Methode die het overzicht van verkopen & weggooien toon aanroept + methode kiesKaarten aanroept.
     */
    private void verkopenEnWeggooien() {
        try {
            overzichtVerkopenWeggooien();
            kiesKaarten("WI");
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }

    }

    /**
     * Methode die overzichtItems aanroept + kiesKaarten aanroept.
     */
    private void naarItems() {
        try {
            overzichtItems();
            kiesKaarten("I");
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }

    /**
     * Methode die een overzicht print van de mogelijk verkoopbare kaarten en de weg te gooien kaarten.
     * Methode vraagt input van speler omtrent keuze verkopen/weggooien en kijkt of deze overeenkomt met verkopen of weggooien in de juiste taal.
     */
    private void overzichtVerkopenWeggooien() {
        try {
            System.out.printf("%n%s:%n%s%n%n%s:%n%s%n", ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.sellable") + ColorsOutput.reset(), dc.geefVerkoopbareKaarten(naam), ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.throwaway") + ColorsOutput.reset(), dc.geefNietVerkoopbareKaarten(naam));
            boolean verkoopWeg = UniversalMethods.controleJaNee("usecase7.asktosell");
            if (verkoopWeg) {
                String choice;
                do {
                    System.out.println(LanguageResource.getString("usecase7.sellorthrow"));
                    choice = scan.next();
                } while (!choice.equalsIgnoreCase(LanguageResource.getString("usecase7.translationsell")) && !choice.equalsIgnoreCase(LanguageResource.getString("usecase7.translationthrow")));
                scan.nextLine();
                if (choice.equalsIgnoreCase(LanguageResource.getString("usecase7.translationsell"))) {
                    verkopen();
                } else if (choice.equalsIgnoreCase(LanguageResource.getString("usecase7.translationthrow"))) {
                    weggooien();
                }
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
            scan.nextLine();
        }
    }

    /**
     * Methode die kaarten inleest adhv kiesKaarten en vervolgens de waarden van deze kaarten controleert om deze vervolgens te verkopen indien waarde >= 1000 bedraagt.
     * Methode doet dit adhv domeinControllermethode dc.verkoopkaarten(naam, gekozenKaarten).
     */
    private void verkopen() {
        try {
            System.out.printf("%s: %n%s%n", ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.sellable") + ColorsOutput.reset(), dc.geefVerkoopbareKaarten(naam));
            kiesKaarten("V");
            controleerWaarden();
            if (totaleWaarde >= 1000) {
                dc.verkoopKaarten(naam, gekozenKaarten);
                System.out.println(ColorsOutput.kleur("green") + ColorsOutput.decoration("underline") + (String.format(LanguageResource.getString("usecase7.levelup"), totaleWaarde / 1000, totaleWaarde / 1000 > 1 ? "s" : "")) + ColorsOutput.reset());
            } else {
                System.out.println(ColorsOutput.kleur("yellow") + ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.kleinewaarde") + ColorsOutput.reset());
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }

    /**
     * Methode die kaarten inleest adhv kiesKaarten en vervolgens de kaarten weggooit adhv domeinControllermethode dc.gooiKaartenWeg(naam, gekozenKaarten).
     */
    private void weggooien() {
        try {
            System.out.printf("%s: %n%s%n", ColorsOutput.decoration("bold") + LanguageResource.getString("usecase7.throwaway") + ColorsOutput.reset(), dc.geefNietVerkoopbareKaarten(naam));
            kiesKaarten("W");
            dc.gooiKaartenWeg(naam, gekozenKaarten);
            System.out.print(Printer.printGreen("usecase7.succesdelete"));
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }

    /**
     * Methode die overzicht van de kaarten die naar items kunnen, print.
     */
    private void overzichtItems() {
        try {
            System.out.printf("%s%n%s", ColorsOutput.decoration("bold") + String.format("%s:", LanguageResource.getString("usecase7.toitems")) + ColorsOutput.reset(), dc.geefKaartenKunnenNaarItems(naam));
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
            scan.nextLine();
        }
    }

    /**
     * Methode die adhv type de juiste value kiest van de map.
     *
     * @param type V, W, I afh van welke actie de speler wil uitvoeren
     */
    private void kiesKaarten(String type) {
        try {
            kiezenType.get(type).run();
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
            scan.nextLine();
        }
    }

    /**
     * Methode die de mogelijke verkoop kaarten in een lijst steeks en dan vervolgens controle doet adhv doeControle.
     * Indien oke -> dc.verkoopKaarten
     */
    private void kiesVerkoop() {
        try {
            List<Integer> mogelijkheden = dc.geefIdVerkoopbareKaarten(naam);
            doeControle(LanguageResource.getString("usecase7.whattosell"), mogelijkheden);
            dc.verkoopKaarten(naam, gekozenKaarten);
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }

    /**
     * Methode die de mogelijke kaarten die weggegooien kunnen worden in een lijst steekt, de controle doet en vervolgens dc.gooiKaartenWeg doet.
     */
    private void kiesWeggooien() {
        try {
            List<Integer> mogelijkheden = dc.geefIDKaartenInHand(naam);
            doeControle(LanguageResource.getString("usecase7.whattothrow"), mogelijkheden);
            dc.gooiKaartenWeg(naam, gekozenKaarten);
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }

    /**
     * Methode di de mogelijke kaarten die naar items kunnen in een lijst steekt, de controle doet en vervolgens dc.verplaatsNaarItems doet.
     */
    private void kiesNaarItems() {
        try {
            List<Integer> mogelijkheden = dc.geefIdsKunnenNaarItems(naam);
            doeControle(LanguageResource.getString("usecase7.whattoitems"), mogelijkheden);
            dc.verplaatsNaarItems(naam, gekozenKaarten);
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }

    /**
     * Methode die gebruikt wordt om waarde te controleren van de kaarten die verkocht worden. Deze waarde wordt opgeteld en vervolgens gebruikt in de methode verkopen().
     */
    private void controleerWaarden() {
        try {
            totaleWaarde = 0;
            for (Integer id : gekozenKaarten) {
                if (dc.getWaardeSchatkaart(id) == 999) {
                    throw new Exception("Error");
                }
                totaleWaarde += dc.getWaardeSchatkaart(id);
            }
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
            scan.nextLine();
        }
    }

    /**
     * Methode die de print doet van de meegegeven parameter print en kijkt of de keuze van het id in de lijst van mogelijkheden zit.
     * Indien ja -> gekozeenKaarten.add(keuze)
     * Indien nee en keuze zit niet in mogelijkheden -> opnieuw vragen
     * Indien keuze 999 vragen om te stoppen.
     * Dit herhalen zolang de keuze ook niet 999 is.
     *
     * @param print         de string die geprint moet worden
     * @param mogelijkheden de sting met de mogelijke kaarten
     */
    private void doeControle(String print, List<Integer> mogelijkheden) {
        int teller = 0;
        int keuze;
        try {
            do {
                System.out.printf("%s: %n", print);
                keuze = scan.nextInt();
                if (mogelijkheden.contains(keuze) && keuze != 999) {
                    //if(dc.validatieKaartItems(keuze))
                    gekozenKaarten.add(keuze);
                    teller++;
                } else if (keuze == 999) {
                    String stop;
                    do {
                        System.out.println(LanguageResource.getString("usecase7.stop"));
                        stop = scan.next();
                        if (stop.equalsIgnoreCase(LanguageResource.getString("yes"))) {
                            break;
                        } else if (stop.equalsIgnoreCase(LanguageResource.getString("no"))) {
                            niksDoen();
                        } else {
                            System.out.println(LanguageResource.getString("usecase2.choiceerror"));
                        }
                    } while (!stop.equalsIgnoreCase(LanguageResource.getString("yes")) || !stop.equalsIgnoreCase(LanguageResource.getString("no")));
                } else {
                    System.out.println(LanguageResource.getString("usecase7.foutid"));
                }
            } while (keuze != 999 && teller < mogelijkheden.size());
        } catch (Exception e) {
            System.out.println(Printer.exceptionCatch("Exception (UC7)", e, false));
        }
    }
}
