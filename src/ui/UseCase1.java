package ui;

import domein.Spel;
import domein.Speler;
import java.util.*;
/**
 *
 * @author g35
 */

public class UseCase1 {
    private static Locale choice;
    private static Scanner scan = new Scanner(System.in);
    private static ResourceBundle bundle;
    
    public UseCase1(){
        Welcome();       
        System.out.println(bundle.getString("newGame"));
        String nieuwSpel = scan.next().toLowerCase();
        
        boolean startUp = false;
        if(nieuwSpel.equals(bundle.getString("yes"))){
            startUp = true;
        }
        
        if (startUp) {
            int aantalSpelers = kiesAantalSpelers();
            Spel spel = startSpel(aantalSpelers);
            voegSpelersToe(aantalSpelers, spel);
        }
        
    }
    
    private static void Welcome(){
        Locale nl = new Locale("nl");
        Locale en = new Locale("en");
        Locale fr = new Locale("fr");
        System.out.printf("%s %s%n",ResourceBundle.getBundle("ui/i18n", nl).getString("startUp"),ResourceBundle.getBundle("ui/i18n", nl).getString("languageC"));
        System.out.printf("%s %s%n",ResourceBundle.getBundle("ui/i18n", en).getString("startUp"),ResourceBundle.getBundle("ui/i18n", en).getString("languageC"));
        System.out.printf("%s %s%n",ResourceBundle.getBundle("ui/i18n", fr).getString("startUp"),ResourceBundle.getBundle("ui/i18n", fr).getString("languageC"));
        char gekozenTaal = scan.next().toLowerCase().charAt(0);
        
        while(gekozenTaal != 'n' && gekozenTaal != 'f' && gekozenTaal != 'e'){
            System.out.printf("%s%n",ResourceBundle.getBundle("ui/i18n", nl).getString("wrong"));
            System.out.printf("%s%n",ResourceBundle.getBundle("ui/i18n", en).getString("wrong"));
            System.out.printf("%s%n",ResourceBundle.getBundle("ui/i18n", fr).getString("wrong"));
            gekozenTaal = scan.next().toLowerCase().charAt(0);
        }
        
        switch(gekozenTaal){
            case 'e': default:
                choice = new Locale("en");
                break;
            case 'f':
                choice = new Locale("fr");
                break;
            case 'n':
                choice = new Locale("nl");
                break;
        }
        bundle = ResourceBundle.getBundle("ui/i18n", choice);
        System.out.printf("%s: %s%n",bundle.getString("picked"), bundle.getString("language"));
    }
    
    
    
    private static Spel startSpel(int aantalSpelers){
        Spel spel = new Spel(aantalSpelers, choice);
        return spel;
    }
    
    private static int kiesAantalSpelers(){
         System.out.println(bundle.getString("amountOfPlayers"));
            int aantalSpelers = scan.nextInt();
            while(aantalSpelers<3 || aantalSpelers >6){
                System.out.println(bundle.getString("exception.players"));
                aantalSpelers = scan.nextInt();
            }
            return aantalSpelers;
    }
    
    
    private static void voegSpelersToe(int aantalSpelers, Spel spel){
        bundle = ResourceBundle.getBundle("ui/i18n", choice);
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
    
    private static String geefInformatie(Spel spel){
        String info = spel.geefInfo();
        return info;
    }
}
