package gui;

import domein.*;
import java.util.*;
import language.LanguageResource;

/**
 *
 * @author g35
 */
public class UseCase1 {
    //declaraties voor gehele usecase
    private static final Scanner SCAN = new Scanner(System.in);
    private static LanguageResource bundle = new LanguageResource();
    private static DomeinController dc;

    public UseCase1() {
        dc = new DomeinController();
        Welcome();
        System.out.println(bundle.getString("newGame"));
        String nieuwSpel = SCAN.next().toLowerCase();

        boolean startUp = false;
        if (nieuwSpel.equals(bundle.getString("yes"))) {
            startUp = true;
        }

        if (startUp) {
            int aantalSpelers = kiesAantalSpelers();
            dc.startSpel(aantalSpelers, bundle.getLocale());
            voegSpelersToe(aantalSpelers);
            dc.geefStartKaarten();
            System.out.println(dc.geefInformatie());
        }
    }
    //welcome message in 3 talen
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
    //aantal spelers kiezen
    private static int kiesAantalSpelers() {
        System.out.println(bundle.getString("amountOfPlayers"));
        int aantalSpelers = SCAN.nextInt();
        while (aantalSpelers < 3 || aantalSpelers > 6) {
            System.out.println(bundle.getString("exception.players"));
            aantalSpelers = SCAN.nextInt();
        }
        return aantalSpelers;
    }
    //spelers toevoegen (naam, geslacht en leeftijd)
    private static void voegSpelersToe(int aantalSpelers) {
        for (int i = 0; i < aantalSpelers; i++) {
            System.out.println(bundle.getString("ask.name"));
            String naam = SCAN.next();
            System.out.println(bundle.getString("ask.sex"));
            String geslacht = SCAN.next();
            System.out.println(bundle.getString("ask.age"));
            int leeftijd = SCAN.nextInt();
            dc.voegSpelerToe(naam, geslacht, leeftijd);
        }
    }
    //informatie over het spelobject
    private static String geefInformatie(Spel spel) {
        String ret = "";
        String[] info = spel.geefInfo();
        for (int i = 0; i < info.length; i++) {
            ret += String.format("%s%n", info[i]);
        }
        return ret;
    }
}
