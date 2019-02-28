package gui;

import domein.*;
import java.util.*;
import language.LanguageResource;

/**
 * test commit, hallo
 *
 * @author g35
 */
public class UseCase1 {

    //declaraties voor gehele usecase
    private static final Scanner SCAN = new Scanner(System.in);
    private static DomeinController dc; //static moet weg

    /**
     * constructor voor UseCase 1
     *
     * @param dc
     */
    public UseCase1(DomeinController dc) {
        UseCase1.dc = dc;
        welcome();
        //gebruiker vragen of hij een nieuw spel wil starten.
        System.out.println(LanguageResource.getString("newGame"));
        String nieuwSpel = SCAN.next().toLowerCase();
        //antwoord omzetten naar true/false
        boolean startUp = false;
        if (nieuwSpel.equals(LanguageResource.getString("yes"))) {
            startUp = true;
        }
        //indien true spel aanmaken en opstarten
        if (startUp) {
            int aantalSpelers = kiesAantalSpelers();
            dc.startSpel(aantalSpelers);
            voegSpelersToe();
            System.out.println(dc.geefInformatie());
        }
    }

    /**
     * welcome message in 3 talen
     */
    private static void welcome() {
        Locale nl = new Locale("nl");
        Locale en = new Locale("en");
        Locale fr = new Locale("fr");
        System.out.printf("%s %s%n", LanguageResource.getStringLanguage("startUp", nl), LanguageResource.getStringLanguage("languageC", nl));
        System.out.printf("%s %s%n", LanguageResource.getStringLanguage("startUp", en), LanguageResource.getStringLanguage("languageC", en));
        System.out.printf("%s %s%n", LanguageResource.getStringLanguage("startUp", fr), LanguageResource.getStringLanguage("languageC", fr));
        char gekozenTaal = SCAN.next().toLowerCase().charAt(0);
        //zolang gekozen taal niet voldoet aan beginletter van frans, nederlands of engels
        while (gekozenTaal != 'n' && gekozenTaal != 'f' && gekozenTaal != 'e') {
            System.out.printf("%s%n", LanguageResource.getStringLanguage("wrong", nl));
            System.out.printf("%s%n", LanguageResource.getStringLanguage("wrong", en));
            System.out.printf("%s%n", LanguageResource.getStringLanguage("wrong", fr));
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
     * De gebruiker een aantal spelers laten kiezen, dit tussen 3 en 6 (grenzen
     * incl.)
     *
     * @return gekozen aantal spelers
     */
    private static int kiesAantalSpelers() {
        System.out.println(LanguageResource.getString("amountOfPlayers"));
        int aantalSpelers = SCAN.nextInt();
        while (aantalSpelers < 3 || aantalSpelers > 6) {
            System.out.println(LanguageResource.getString("exception.players"));
            aantalSpelers = SCAN.nextInt();
        }
        return aantalSpelers;
    }

    /**
     * Voeg het aantal gekozen aantal spelers toe aan het spel a.d.h.v. naam,
     *
     * @param aantalSpelers het aantal spelers dat de methode zal toevoegen aan
     * het spel
     */
    private static void voegSpelersToe() {
        for (int i = 0; i < DomeinController.geefAantalSpelers(); i++) {
            System.out.println(String.format("%s %d", LanguageResource.getString("player"), i + 1));
            System.out.println(LanguageResource.getString("ask.name"));
            String naam = SCAN.next();
            System.out.println(LanguageResource.getString("ask.sex"));
            String geslacht = SCAN.next();
            dc.voegSpelerToe(naam, geslacht);
        }
    }
}
