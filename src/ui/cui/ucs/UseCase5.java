package ui.cui.ucs;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import language.LanguageResource;

public class UseCase5 {

    private final DomeinController dc;
    private String naam;
    private boolean spelerLooptWeg;

    public UseCase5(DomeinController dc, String naam) {
        this.dc = dc;
        this.naam = naam;
    }

    void speelKaart() {
        System.out.printf("Er wordt een kaart gespeeld %n%n");
        toonOverzichtKaartenInHand();
        System.out.print("De gewenste kaart: " + Arrays.toString(kiesKaart("",naam)));
        //toString(geefSpelsituatieGevecht(1));
        
    }

    public String toonOverzichtKaartenInHand() {
        return dc.toonOverzichtKaartenInHand(naam);
    }

    public String[] kiesKaart(String type, String naam) {
        Scanner invoer = new Scanner(System.in);
        String[] keuze = new String[2];
       
        System.out.print("Kies de gewenste kaart ");
        System.out.print("Kaartnaam: ");
        naam = invoer.nextLine();
        keuze[0] = naam;
        System.out.print("Kaarttype: ");
        type = invoer.nextLine();
        keuze[1] = type;
        return keuze;
    }

    public void validatieKaart(String naam) {
        int keuze = 0;
        //DR_Speel_Kaart
        Scanner invoer = new Scanner(System.in);
            if (spelerLooptWeg == false) {
                //hier eerst nog met if checken of speler hulp wou
                //wil hulp
                if(spelerLooptWeg == false){
                
                System.out.printf("%s kies een kaart die je wilt afleggen: 1. Consumptie%n2. Uitrusting%n3. Ras%n4. Monster" ,dc.geefSpelerAanBeurt());
                keuze = invoer.nextInt();
                switch (keuze) {
                case 1:
                }
            if (dc.geefTegenspelers().contains(naam)) {
                //System.out.printf("%s kies een kaart die je wilt afleggen: 1. Consumptie%n2. Monster%n3. Vervloeking", dc.geefNaamSpeler(dc.geefTegenspelers.contains(naam)));
                keuze = invoer.nextInt();
                switch (keuze) {
                case 1:
                }
            }
                
            }else{
                //tegenspeler kan enkel negatieve consumptiekaart afleggen
               
                

                
            }

            
    }else if(spelerLooptWeg){
            //consumptiekaart afleggen
            }
            
    
            
    //DR_Afgelegde_Kaart
    
    }

    public void verwijderKaartUitHand() {
        
    }

    public List<String> geefSpelsituatieGevecht(int id, List<Boolean> helptmee) {
        int waar = 0;
        List<String> ret = dc.geefBeknopteSpelsituatie(/*helptmee[i]*/);
        for (String str : ret) {
            ret.add(String.format("%s, %s%n", str, helptmee.get(waar) ? "vecht mee" : "vecht niet mee"));
            waar++;
        }
        return ret;
    }
    
    
}