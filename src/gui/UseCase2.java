/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.util.*;
import language.LanguageResource;

/**
 *
 * @author ziggy
 */
public class UseCase2 {

    //Declaraties voor gehele usecase.
    private final DomeinController dc;
    private static Scanner SCAN;

    /**
     * Constructor voor Use Case 2.
     *
     * @param dc Domeincontroller van het spel aangemaakt in de StartUp
     */
    public UseCase2(DomeinController dc) {
        this.dc = dc;
        SCAN = new Scanner(System.in);
        speelSpel();
    }

    /**
     *
     */
    private void speelSpel() {
        dc.speelSpel();
        System.out.println(String.format("De volgorde van de spelers is: %n%s%n", dc.geefInformatie()));
        for (int i = 0; i < DomeinController.geefAantalSpelers(); i++) {
            if (niemandGewonnen()) {
                String naam = dc.geefNaamSpeler(i);
                speelBeurt(naam);
            }
        }
        System.out.printf("%s: %s", LanguageResource.getString("end.winner"), geefNaamWinnaar());
    }
/**
 * 
 * @param naam 
 */
    private void speelBeurt(String naam) {
        System.out.printf("%s: %s%n", LanguageResource.getString("player.turn"), naam);
        int keuze = 0;
        do {
            System.out.printf("%s%n"
                    + "1) %s%n"
                    + "2) %s%n"
                    + "3) %s%n", LanguageResource.getString("turn.choice"), LanguageResource.getString("turn.play"), LanguageResource.getString("turn.save"), LanguageResource.getString("turn.stop"));
            keuze = SCAN.nextInt();
        } while (keuze < 1 || keuze > 3);
        
        switch(keuze){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
/**
 * 
 * @return 
 */
    private boolean niemandGewonnen() {
        boolean ret = true;
        for (int i = 0; i < DomeinController.geefAantalSpelers(); i++) {
            if (dc.geefLevel(i) == 10) {
                return false;
            }
        }
        return ret;
    }
    /**
     * 
     * @return 
     */
    private String geefNaamWinnaar(){
        return dc.geefNaamWinnaar();
    }
}
