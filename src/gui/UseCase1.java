package gui;

import domein.*;
import java.util.*;
import language.LanguageResource;

/**
 * test commit, hallo
 * @author g35
 */
public class UseCase1 {

    //declaraties voor gehele usecase
    private static final Scanner SCAN = new Scanner(System.in);
    private static LanguageResource bundle = new LanguageResource();
    private static DomeinController dc; //static moet weg

    /**
     * constructor voor UseCase 1
     * 
     * @param dc
     */
    public UseCase1(DomeinController dc) {
        UseCase1.dc = dc;
        Welcome();
        //gebruiker vragen of hij een nieuw spel wil starten.
        System.out.println(bundle.getString("newGame"));
        String nieuwSpel = SCAN.next().toLowerCase();
        //antwoord omzetten naar true/false
        boolean startUp = false;
        if (nieuwSpel.equals(bundle.getString("yes"))) {
            startUp = true;
        }
        //indien true spel aanmaken en opstarten
        if (startUp) {
            int aantalSpelers = kiesAantalSpelers();
            dc.startSpel(aantalSpelers, bundle.getLocale());
            voegSpelersToe(aantalSpelers);
            dc.geefStartKaarten();
            System.out.println(dc.geefInformatie());
        }
    }

    /**
     * welcome message in 3 talen
     */
    private static void Welcome() {
        Locale nl = new Locale("nl");
        Locale en = new Locale("en");
        Locale fr = new Locale("fr");
        System.out.printf("%s %s%n", bundle.getStringLanguage("startUp", nl), bundle.getStringLanguage("languageC", nl));
        System.out.printf("%s %s%n", bundle.getStringLanguage("startUp", en), bundle.getStringLanguage("languageC", en));
        System.out.printf("%s %s%n", bundle.getStringLanguage("startUp", fr), bundle.getStringLanguage("languageC", fr));
        char gekozenTaal = SCAN.next().toLowerCase().charAt(0);
        //zolang gekozen taal niet voldoet aan beginletter van frans, nederlands of engels
        while (gekozenTaal != 'n' && gekozenTaal != 'f' && gekozenTaal != 'e') {
            System.out.printf("%s%n", bundle.getStringLanguage("wrong", nl));
            System.out.printf("%s%n", bundle.getStringLanguage("wrong", en));
            System.out.printf("%s%n", bundle.getStringLanguage("wrong", fr));
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
        bundle.setLocale(choice);
        System.out.printf("%s: %s%n", bundle.getString("picked"), bundle.getString("language"));
    }

    /**
     * De gebruiker een aantal spelers laten kiezen, dit tussen 3 en 6 (grenzen
     * incl.)
     *
     * @return gekozen aantal spelers
     */
    private static int kiesAantalSpelers() {
        System.out.println(bundle.getString("amountOfPlayers"));
        int aantalSpelers = SCAN.nextInt();
        while (aantalSpelers < 3 || aantalSpelers > 6) {
            System.out.println(bundle.getString("exception.players"));
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
    private static void voegSpelersToe(int aantalSpelers) {
        for (int i = 0; i < aantalSpelers; i++) {
            System.out.println(String.format("%s %d", LanguageResource.getString("player"), i+1));
            System.out.println(LanguageResource.getString("ask.name"));
            String naam = SCAN.next();
            System.out.println(LanguageResource.getString("ask.sex"));
            String geslacht = SCAN.next();                       
            dc.voegSpelerToe(naam, geslacht);
        }
    }
}
//    /**
//     * geeft informatie over een spelobject
//     * @param spel
//     * @return String met informatie over het spel en de spelers
//     */
//    
//    private static String geefInformatie(Spel spel) {
//        String ret = "";
//        String[] info = spel.geefInfo();
//        for (String i : info) {
//            ret += String.format("%s%n", i);
//        }
//        return ret;
//    }
//}
