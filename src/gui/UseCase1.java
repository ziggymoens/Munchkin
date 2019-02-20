package gui;

import domein.*;
import java.security.SecureRandom;
import java.util.*;
import language.LanguageResource;

/**
 *
 * @author g35
 */
public class UseCase1 {

    private static Locale choice;
    private static final Scanner SCAN = new Scanner(System.in);
    private static LanguageResource bundle = new LanguageResource();

    public UseCase1() {
        Welcome();
        System.out.println(bundle.getString("newGame"));
        String nieuwSpel = SCAN.next().toLowerCase();

        boolean startUp = false;
        if (nieuwSpel.equals(bundle.getString("yes"))) {
            startUp = true;
        }

        if (startUp) {
            int aantalSpelers = kiesAantalSpelers();
            Spel spel = startSpel(aantalSpelers);
            voegSpelersToe(aantalSpelers, spel);
//            Kaart[] kerkerkaarten = maakKerkerkaarten();
//            Kaart[] schatkaarten = maakSchatkaarten();
//            verdeelKaarten(kerkerkaarten, schatkaarten, aantalSpelers, spel);
            spel.startLevels();
            System.out.println(geefInformatie(spel));
        }
    }

    private static void Welcome() {
        Locale nl = new Locale("nl");
        Locale en = new Locale("en");
        Locale fr = new Locale("fr");
        System.out.printf("%s %s%n", bundle.getStringLanguage("startUp", nl), bundle.getStringLanguage("languageC", nl));
        System.out.printf("%s %s%n", bundle.getStringLanguage("startUp", en), bundle.getStringLanguage("languageC", en));
        System.out.printf("%s %s%n", bundle.getStringLanguage("startUp", fr), bundle.getStringLanguage("languageC", fr));
        char gekozenTaal = SCAN.next().toLowerCase().charAt(0);

        while (gekozenTaal != 'n' && gekozenTaal != 'f' && gekozenTaal != 'e') {
            System.out.printf("%s%n", ResourceBundle.getBundle("ui/i18n", nl).getString("wrong"));
            System.out.printf("%s%n", ResourceBundle.getBundle("ui/i18n", en).getString("wrong"));
            System.out.printf("%s%n", ResourceBundle.getBundle("ui/i18n", fr).getString("wrong"));
            gekozenTaal = SCAN.next().toLowerCase().charAt(0);
        }

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

    private static Spel startSpel(int aantalSpelers) {
        Spel spel = new Spel(aantalSpelers, choice);
        return spel;
    }

    private static int kiesAantalSpelers() {
        System.out.println(bundle.getString("amountOfPlayers"));
        int aantalSpelers = SCAN.nextInt();
        while (aantalSpelers < 3 || aantalSpelers > 6) {
            System.out.println(bundle.getString("exception.players"));
            aantalSpelers = SCAN.nextInt();
        }
        return aantalSpelers;
    }

    private static void voegSpelersToe(int aantalSpelers, Spel spel) {
        for (int i = 0; i < aantalSpelers; i++) {
            System.out.println(bundle.getString("ask.name"));
            String naam = SCAN.next();
            System.out.println(bundle.getString("ask.sex"));
            String geslacht = SCAN.next();
            System.out.println(bundle.getString("ask.age"));
            int leeftijd = SCAN.nextInt();

            Speler speler = new Speler(naam, geslacht, leeftijd, choice);
            spel.voegSpelerToe(i, speler);
        }
    }

    private static void verdeelKaarten(Kaart[] kerkerkaarten, Kaart[] schatkaarten, int aantalSpelers, Spel spel) {
        SecureRandom rand = new SecureRandom();
        int[] gekozenSchat = new int[schatkaarten.length];
        int[] gekozenKerker = new int[kerkerkaarten.length];
        for (int i = 0; i < aantalSpelers; i++) {
            int teller = 0;
            while (teller < 2) {
                int kSchat = rand.nextInt(15);
                while (controleKaarten(gekozenSchat, kSchat)) {
                    kSchat = rand.nextInt(15);
                }
                int kKerker = rand.nextInt(15);
                while (controleKaarten(gekozenKerker, kKerker)) {
                    kKerker = rand.nextInt(15);
                }
                spel.voegKaartToe(kerkerkaarten[kKerker], i);
                spel.voegKaartToe(schatkaarten[kSchat], i);
                gekozenKerker[i] = kKerker;
                gekozenSchat[i] = kSchat;
                teller++;
            }
        }
    }

    private static boolean controleKaarten(int[] alGekozen, int nrKaart) {
        boolean komtVoor = false;
        for (int i = 0; i < alGekozen.length; i++) {
            if (nrKaart == alGekozen[i]) {
                komtVoor = true;
            }
        }
        return komtVoor;
    }

    private static String geefInformatie(Spel spel) {
        String ret = "";
        String[] info = spel.geefInfo();
        for (int i = 0; i < info.length; i++) {
            ret += String.format("%s%n", info[i]);
        }
        return ret;
    }
}
