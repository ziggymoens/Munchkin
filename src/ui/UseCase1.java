package ui;

import domein.Spel;
import java.util.*;
/**
 *
 * @author g35
 */

public class UseCase1 {
    
    public UseCase1(){
        Scanner scan = new Scanner(System.in);
        
        Locale nl = new Locale("nl");
        Locale en = new Locale("en");
        Locale fr = new Locale("fr");
        Locale choice;
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
        ResourceBundle bundle = ResourceBundle.getBundle("ui/i18n", choice);
        
        System.out.printf("%s: %s%n",bundle.getString("picked"), bundle.getString("language"));
        
        System.out.println(bundle.getString("newGame"));
        String nieuwSpel = scan.next();
        
        boolean startUp = false;
        if (nieuwSpel.toLowerCase().charAt(0)=='j' || nieuwSpel.toLowerCase().charAt(0)=='y' || nieuwSpel.toLowerCase().charAt(0)=='o') {
            startUp = true;
        }
        
        if (startUp) {
            int aantalSpelers = 0;
            do {
                System.out.println("Hoveel spelers doen er mee met het spel?");
                aantalSpelers = scan.nextInt();
            } while (aantalSpelers < 3 || aantalSpelers >7);
            Spel huidigSpel = startSpel(aantalSpelers);
        }     
    }
    
    private static Spel startSpel(int aantalSpelers){
        Spel huidigSpel = new Spel(aantalSpelers);
        return huidigSpel;
    }
    
    private static String geefInformatie(Spel huidigSpel){
        String info = huidigSpel.geefInfo();
        return info;
    }
}
