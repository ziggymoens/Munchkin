package gui;

import domein.*;
import java.util.*;

import exceptions.SpelException;
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
    private UseCase2 uc2;

    /**
     * constructor voor UseCase 1
     *
     * @param dc
     */
    public UseCase1(DomeinController dc) {
        this.dc = dc;
        try {
            welcome();
        }catch(Exception e){
            System.err.println(e.toString());
        }
        //gebruiker vragen of hij een nieuw spel wil starten.
        System.out.println(LanguageResource.getString("newGame"));
        String nieuwSpel = SCAN.next().toLowerCase();
        //indien true spel aanmaken en opstarten
        if (nieuwSpel.equals(LanguageResource.getString("yes"))){
            int aantalSpelers = kiesAantalSpelers();
            try{
            dc.startSpel(aantalSpelers);}
            catch(SpelException se){
                System.err.printf("SpelException: %s", LanguageResource.getString(se.getMessage()));
            }
            voegSpelersToe(aantalSpelers);
            System.out.println(dc.geefInformatie());
            //verdergaan naar UC2
            uc2 = new UseCase2(this.dc);
            uc2.speelSpel(aantalSpelers);            
        }
    }

    /**
     * welcome message in 3 talen
     */
    private void welcome() {
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
    private int kiesAantalSpelers() {
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
    private void voegSpelersToe(int aantalSpelers) {
        for (int i = 0; i < aantalSpelers; i++) {
            System.out.println(String.format("%s %d", LanguageResource.getString("player"), i + 1));
            System.out.println(LanguageResource.getString("ask.name"));
            String naam = SCAN.next();
            System.out.println(LanguageResource.getString("ask.sex"));
            String geslacht = SCAN.next();
            dc.voegSpelerToe(naam, geslacht);
        }
    }
}
