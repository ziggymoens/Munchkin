package ui.cui.ucs;

import domein.*;

import java.util.*;

import exceptions.database.KaartDatabaseException;
import javafx.scene.Scene;
import printer.Printer;
import exceptions.SpelException;
import exceptions.SpelerException;
import printer.ColorsOutput;
import language.LanguageResource;

/**
 * @author g35
 */
public class UseCase1 {

    //declaraties voor gehele usecase
    private final Scanner SCAN = new Scanner(System.in);
    private final DomeinController dc;
    private final List<Locale> talen;
    private int aantalSpelers;

    /**
     * constructor voor UseCase 1
     *
     * @param dc Domeincontroller die aangemaakt wordt voor alle use cases in de Main
     */
    public UseCase1(DomeinController dc) {
        this.dc = dc;
        talen = new ArrayList<>();
        talen.add(new Locale("nl"));
        talen.add(new Locale("en"));
        talen.add(new Locale("fr"));
        try {
            welcome();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception", e, false));
        }
//        try {
//            System.out.println(LanguageResource.getString("usecase1.askgui"));
//            char gui = SCAN.next().toLowerCase().charAt(0);
//            if (gui == 'g') {
//                String[] args = {};
//                MainGUI.main(args);
//                System.exit(0);
//            }
//        }catch (Exception e){
//            System.out.println(Printer.exceptionCatch("Exception", e, false));
//        }
        //gebruiker vragen of hij een nieuw spel wil starten.
        System.out.println(LanguageResource.getString("newGame"));
        String nieuwSpel = SCAN.next().toLowerCase();
        SCAN.nextLine();
        while (!nieuwSpel.equals(LanguageResource.getString("yes")) && !nieuwSpel.equals(LanguageResource.getString("no"))) {
            System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
            System.out.println(LanguageResource.getString("newGame"));
            nieuwSpel = SCAN.next().toLowerCase();
            SCAN.nextLine();
        }
        try {
            if (nieuwSpel.equals(LanguageResource.getString("yes"))) {
                //th1.start();
                maakSpel();
                voegSpelersToe();
                System.out.println(Printer.printGreen("spel.playersadded"));
                //verdergaan naar UC2
                UseCase2 uc2 = new UseCase2(this.dc);
                uc2.speelSpel(aantalSpelers);
            } else if (nieuwSpel.equals(LanguageResource.getString("no"))) {
                System.out.println(LanguageResource.getString("usecase1.load"));
                String load = SCAN.next();
                SCAN.nextLine();
                while (!load.equals(LanguageResource.getString("yes")) && !load.equals(LanguageResource.getString("no"))) {
                    System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
                    System.out.println(LanguageResource.getString("usecase1.load"));
                    load = SCAN.next().toLowerCase();
                    SCAN.nextLine();
                }
                if (load.equals(LanguageResource.getString("no"))) {
                    System.out.println(Printer.printGreen("gamestop"));
                }
                UseCase9 uc9 = new UseCase9(this.dc);
                uc9.spelLaden();
            }
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception", e, false));
        }
    }

    /**
     * welcome message in 3 talen
     */
    private void welcome() {
        for (Locale l : talen) {
            System.out.printf("%s %s%n", LanguageResource.getStringLanguage("startUp", l), LanguageResource.getStringLanguage("languageC", l));
        }
        String gekozenTaal = SCAN.next().toLowerCase();
        SCAN.nextLine();
        //zolang gekozen taal niet voldoet aan beginletter van frans, nederlands of engels
        while (!gekozenTaal.equals("nederlands") && !gekozenTaal.equals("français") && !gekozenTaal.equals("english")) {
            for (Locale l : talen) {
                System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n", LanguageResource.getStringLanguage("wrong", l) + ColorsOutput.reset());
            }
            gekozenTaal = SCAN.next().toLowerCase();
            SCAN.nextLine();
        }
        //switch voor de verschillende talen
        Locale choice;
        switch (gekozenTaal) {
            case "english":
            default:
                choice = new Locale("en");
                break;
            case "français":
                choice = new Locale("fr");
                break;
            case "nederlands":
                choice = new Locale("nl");
                break;
        }
        LanguageResource.setLocale(choice);
        System.out.printf("%s: %s%n", LanguageResource.getString("picked"), LanguageResource.getString("language"));
    }

    /**
     * De gebruiker een aantal spelers laten kiezen, dit tussen 3 en 6 en een
     * spel aanmaken(grenzen incl.)
     */
    private void maakSpel() {
        th1.start();
        th1.suspend();
        int as;
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("amountOfPlayers"));
                as = SCAN.nextInt();
                th1.resume();
                dc.startSpel(as);
                th1.stop();
                this.aantalSpelers = as;
                tryAgain = false;
            } catch (SpelException e) {
                th1.suspend();
                System.out.println("\r");
                System.out.print(Printer.exceptionCatch("SpelException", e));
                SCAN.nextLine();
            } catch (KaartDatabaseException e) {
                th1.suspend();
                System.out.print(Printer.exceptionCatch("KaartDatabaseException", e));
                SCAN.nextLine();
            } catch (InputMismatchException e) {
                th1.suspend();
                System.out.println(Printer.exceptionCatch("InputException", e, false));
                SCAN.nextLine();
            } catch (Exception e) {
                th1.suspend();
                System.out.println(Printer.exceptionCatch("Exception", e, false));
                SCAN.nextLine();
            }
        }
        System.out.println("\n" + Printer.printGreen("spel.made"));
    }

    /**
     * Voeg het aantal gekozen aantal spelers toe aan het spel a.d.h.v. naam,
     */
    private void voegSpelersToe() {
        for (int i = 0; i < aantalSpelers; i++) {
            System.out.println(String.format("%s %d", LanguageResource.getString("player"), i + 1));
            dc.maakSpeler();
            kiesNaamSpeler(i);
            kiesGeslachtSpeler(i);
        }
    }

    /**
     * Methode die de gebruiker de naam laat ingeven van de i-de speler
     *
     * @param i hoeveelste speler van het spel
     */
    private void kiesNaamSpeler(int i) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("ask.name"));
                String naam = SCAN.next();
                SCAN.nextLine();
                dc.geefSpelerNaam(i, naam);
                tryAgain = false;
            } catch (SpelerException e) {
                System.out.print(Printer.exceptionCatch("SpelerException", e));
            } catch (SpelException e) {
                System.out.print(Printer.exceptionCatch("SpelException", e));
            }
        }
    }

    /**
     * Methode die de gebruiker het geslacht laat ingeven van de i-de speler
     *
     * @param i hoeveelste speler van het spel
     */
    private void kiesGeslachtSpeler(int i) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("ask.sex"));
                String geslacht = SCAN.next();
                SCAN.nextLine();
                dc.geefSpelerGeslacht(i, geslacht);
                tryAgain = false;
            } catch (SpelerException e) {
                System.out.print(Printer.exceptionCatch("SpelerException", e));
            }
        }
    }

    /**
     * deze werkt niet goed in netbeans
     */
    private Thread th1 = new Thread(() -> {
        System.out.print("\nLoading ");
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
            System.out.print(".");
        }
    });
}
