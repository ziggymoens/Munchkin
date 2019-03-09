package gui;

import domein.*;
import java.util.*;

import exceptions.SpelException;
import exceptions.SpelerException;
import gui.colors.ColorsOutput;
import language.LanguageResource;

/**
 * test commit, hallo
 *
 * @author g35
 */
public class UseCase1 {

    //declaraties voor gehele usecase
    private final Scanner SCAN = new Scanner(System.in);
    private final DomeinController dc;
    private List<Locale> talen;
    private UseCase2 uc2;
    private int aantalSpelers;

    /**
     * constructor voor UseCase 1
     *
     * @param dc
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
            System.out.printf(ColorsOutput.kleur("red") + "%n%s%n%n", e.toString() + ColorsOutput.reset());
        }
        //gebruiker vragen of hij een nieuw spel wil starten.
        System.out.println(LanguageResource.getString("newGame"));
        String nieuwSpel = SCAN.next().toLowerCase();
        while (!nieuwSpel.toLowerCase().equals(LanguageResource.getString("yes")) && !nieuwSpel.toLowerCase().equals(LanguageResource.getString("no"))) {
            System.out.printf(ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
            System.out.println(LanguageResource.getString("newGame"));
            nieuwSpel = SCAN.next().toLowerCase();
        }

        //indien true spel aanmaken en opstarten
        try {
            if (nieuwSpel.equals(LanguageResource.getString("yes"))) {
                maakSpel();
                System.out.printf(ColorsOutput.kleur("green") + "%n%s%n", LanguageResource.getString("spel.made") + ColorsOutput.reset());
                voegSpelersToe(aantalSpelers);
                System.out.printf(ColorsOutput.kleur("green") + "%n%s%n", LanguageResource.getString("spel.playersadded") + ColorsOutput.reset());
                //System.out.println(dc.geefInformatie());
                //verdergaan naar UC2
                uc2 = new UseCase2(this.dc);
                uc2.speelSpel(aantalSpelers);
            }
        } catch (Exception e) {
            try {
                System.out.printf(ColorsOutput.kleur("red") + "Exception: %s%n%n", e.getMessage() + ColorsOutput.reset());
            } catch (Exception ex) {
                System.out.printf("\u001B[31m" + "IllegalArgumentException: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
            }
        }
    }

    /**
     * welcome message in 3 talen
     */
    private void welcome() {
        for (Locale l : talen) {
            System.out.printf("%s %s%n", LanguageResource.getStringLanguage("startUp", l), LanguageResource.getStringLanguage("languageC", l));
        }
        char gekozenTaal = SCAN.next().toLowerCase().charAt(0);
        //zolang gekozen taal niet voldoet aan beginletter van frans, nederlands of engels
        while (gekozenTaal != 'n' && gekozenTaal != 'f' && gekozenTaal != 'e') {
            for (Locale l : talen) {
                System.out.printf(ColorsOutput.kleur("red") + "%s%n", LanguageResource.getStringLanguage("wrong", l) + ColorsOutput.reset());
            }
            gekozenTaal = SCAN.next().toLowerCase().charAt(0);
        }
        //switch voor de verschillende talen
        Locale choice;
        switch (gekozenTaal) {
            case 'e':
            default:
                choice = new Locale("en");
                break;
            case 'f':
                choice = new Locale("fr");
                break;
            case 'n':
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
        int as;
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("amountOfPlayers"));
                as = SCAN.nextInt();
                dc.startSpel(as);
                tryAgain = false;
                this.aantalSpelers = as;
            } catch (SpelException e) {
                try {
                    System.out.printf(ColorsOutput.kleur("red") + "SpelException: %s%n%n", LanguageResource.getString(e.getMessage()) + ColorsOutput.reset());
                } catch (Exception ex) {
                    System.out.printf("\u001B[31m" + "Exception: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
                }
            }
        }
    }

    /**
     * Voeg het aantal gekozen aantal spelers toe aan het spel a.d.h.v. naam,
     *
     * @param aantalSpelers het aantal spelers dat de methode zal toevoegen aan
     * het spel
     */
    private void voegSpelersToe(int aantalSpelers) {
        for (int i = 0; i < aantalSpelers; i++) {
            System.out.println(String.format("%s %d", LanguageResource.getString("player"), i + 1));
            dc.maakSpeler();
            kiesNaamSpeler(i);
            kiesGeslachtSpeler(i);
        }
    }

    private void kiesNaamSpeler(int i) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("ask.name"));
                String naam = SCAN.next();
                dc.geefSpelerNaam(i, naam);
                tryAgain = false;
            } catch (SpelerException e) {
                try {
                    System.out.printf(ColorsOutput.kleur("red") + "SpelerException: %s%n%n" + LanguageResource.getString(e.getMessage()) + ColorsOutput.reset());
                } catch (Exception ex) {
                    System.out.printf("\u001B[31m" + "IllegalArgumentException: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
                }
            } catch (SpelException e) {
                try {
                    System.out.printf(ColorsOutput.kleur("red") + "SpelException: %s%n%n", LanguageResource.getString(e.getMessage()) + ColorsOutput.reset());
                } catch (Exception ex) {
                    System.out.printf("\u001B[31m" + "IllegalArgumentException: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
                }
            }
        }
    }

    private void kiesGeslachtSpeler(int i) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("ask.sex"));
                String geslacht = SCAN.next();
                dc.geefSpelerGeslacht(i, geslacht);
                tryAgain = false;
            } catch (SpelerException e) {
                try {
                    System.out.printf(ColorsOutput.kleur("red") + "SpelerException: %s%n%n", LanguageResource.getString(e.getMessage()) + ColorsOutput.reset());
                } catch (Exception ex) {
                    System.out.printf("\u001B[31m" + "IllegalArgumentException: %s%n%n", LanguageResource.getString(ex.getMessage()) + "\u001B[0m");
                }
            }
        }
    }
}
