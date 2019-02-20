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
    private static Scanner scan = new Scanner(System.in);
    private static LanguageResource bundle;

    public UseCase1() {
        Welcome();
        System.out.println(bundle.getString("newGame"));
        String nieuwSpel = scan.next().toLowerCase();

        boolean startUp = false;
        if (nieuwSpel.equals(bundle.getString("yes"))) {
            startUp = true;
        }

        if (startUp) {
            int aantalSpelers = kiesAantalSpelers();
            Spel spel = startSpel(aantalSpelers);
            voegSpelersToe(aantalSpelers, spel);
            Kaart[] kerkerkaarten = maakKerkerkaarten();
            Kaart[] schatkaarten = maakSchatkaarten();
            verdeelKaarten(kerkerkaarten, schatkaarten, aantalSpelers, spel);
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
        char gekozenTaal = scan.next().toLowerCase().charAt(0);

        while (gekozenTaal != 'n' && gekozenTaal != 'f' && gekozenTaal != 'e') {
            System.out.printf("%s%n", ResourceBundle.getBundle("ui/i18n", nl).getString("wrong"));
            System.out.printf("%s%n", ResourceBundle.getBundle("ui/i18n", en).getString("wrong"));
            System.out.printf("%s%n", ResourceBundle.getBundle("ui/i18n", fr).getString("wrong"));
            gekozenTaal = scan.next().toLowerCase().charAt(0);
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
        int aantalSpelers = scan.nextInt();
        while (aantalSpelers < 3 || aantalSpelers > 6) {
            System.out.println(bundle.getString("exception.players"));
            aantalSpelers = scan.nextInt();
        }
        return aantalSpelers;
    }

    private static void voegSpelersToe(int aantalSpelers, Spel spel) {
        for (int i = 0; i < aantalSpelers; i++) {
            System.out.println(bundle.getString("ask.name"));
            String naam = scan.next();
            System.out.println(bundle.getString("ask.sex"));
            String geslacht = scan.next();
            System.out.println(bundle.getString("ask.age"));
            int leeftijd = scan.nextInt();

            Speler speler = new Speler(naam, geslacht, leeftijd, choice);
            spel.voegSpelerToe(i, speler);
        }
    }

    private static Kaart[] maakSchatkaarten() {
        Kaart[] schatkaarten = new Kaart[15];
        schatkaarten[0] = new Kaart("kaart1", "schat", "eq");
        schatkaarten[1] = new Kaart("kaart2", "schat", "eq");
        schatkaarten[2] = new Kaart("kaart3", "schat", "eq");
        schatkaarten[3] = new Kaart("kaart4", "schat", "eq");
        schatkaarten[4] = new Kaart("kaart5", "schat", "co");
        schatkaarten[5] = new Kaart("kaart6", "schat", "co");
        schatkaarten[6] = new Kaart("kaart7", "schat", "co");
        schatkaarten[7] = new Kaart("kaart8", "schat", "eq");
        schatkaarten[8] = new Kaart("kaart9", "schat", "eq");
        schatkaarten[9] = new Kaart("kaart10", "schat", "eq");
        schatkaarten[10] = new Kaart("kaart11", "schat", "eq");
        schatkaarten[11] = new Kaart("kaart12", "schat", "co");
        schatkaarten[12] = new Kaart("kaart13", "schat", "co");
        schatkaarten[13] = new Kaart("kaart14", "schat", "co");
        schatkaarten[14] = new Kaart("kaart15", "schat", "co");
        return schatkaarten;
    }

    private static Kaart[] maakKerkerkaarten() {
        Kaart[] kerkerkaarten = new Kaart[15];
        kerkerkaarten[0] = new Kaart("kaart1", "kerker", "monster");
        kerkerkaarten[1] = new Kaart("kaart2", "kerker", "monster");
        kerkerkaarten[2] = new Kaart("kaart3", "kerker", "curse");
        kerkerkaarten[3] = new Kaart("kaart4", "kerker", "curse");
        kerkerkaarten[4] = new Kaart("kaart5", "kerker", "race");
        kerkerkaarten[5] = new Kaart("kaart6", "kerker", "race");
        kerkerkaarten[6] = new Kaart("kaart7", "kerker", "consumables");
        kerkerkaarten[7] = new Kaart("kaart8", "kerker", "monster");
        kerkerkaarten[8] = new Kaart("kaart9", "kerker", "monster");
        kerkerkaarten[9] = new Kaart("kaart10", "kerker", "curse");
        kerkerkaarten[10] = new Kaart("kaart11", "kerker", "curse");
        kerkerkaarten[11] = new Kaart("kaart12", "kerker", "race");
        kerkerkaarten[12] = new Kaart("kaart13", "kerker", "race");
        kerkerkaarten[13] = new Kaart("kaart14", "kerker", "consumables");
        kerkerkaarten[14] = new Kaart("kaart15", "kerker", "consumables");
        return kerkerkaarten;
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
