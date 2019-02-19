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
        System.out.println("Welkom bij het spel Munchkin - gemaakt door G35\n"
                + "Wenst u een nieuw spel aan te maken?");
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
