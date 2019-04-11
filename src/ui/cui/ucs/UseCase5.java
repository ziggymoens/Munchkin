package ui.cui.ucs;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    }

    //public String toonOverzichtKaartenInHand() {
        //return dc.toonOverzichtKaartenInHand(naam);
    //}

    public String[] kiesKaart(String type, String naam) {
        String[] keuze = new String[2];
        return keuze;
    }

    public void validatieKaart(String naam) {
        Scanner invoer = new Scanner(System.in);
            if (spelerLooptWeg == false) {
            
            if (dc.geefTegenspelers().contains(naam)) {
                
            }else{
                System.out.printf("%s kan volgende type van kaarten afleggen: ", dc.geefNaamSpeler(dc.geefSpelerAanBeurt()));
                String output = "";
                int keuze = 0;

                output += String.format("1. Consumptie%n2. Uitrusting%n3. Ras%n4. Monster");
                keuze = invoer.nextInt();

                switch (keuze) {
                case 1:
                }

            }

            
    }else if(spelerLooptWeg){
            //consumptiekaart afleggen
            }
            
       
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
