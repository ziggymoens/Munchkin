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
    private Scanner SCAN;
    private UseCase3 uc3;
    private UseCase8 uc8;

    /**
     * Constructor voor Use Case 2.
     *
     * @param dc Domeincontroller van het spel aangemaakt in de StartUp
     */
    public UseCase2(DomeinController dc) {
        this.dc = dc;
        SCAN = new Scanner(System.in);

    }

    /**
     *
     * @param aantalSpelers
     */
    public void speelSpel(int aantalSpelers) {
        dc.controleerVolgorde();
        dc.geefStartKaarten();
        System.out.println(String.format("De volgorde van de spelers is: %n%s%n", dc.geefInformatie()));
        while (niemandGewonnen()) {
            for (int i = 0; i < aantalSpelers; i++) {
                if (niemandGewonnen()) {
                    String naam = dc.geefNaamSpeler(i);
                    speelBeurt(naam);
                }
            }
        }
        System.out.printf("%s: %s", LanguageResource.getString("end.won"), geefNaamWinnaar());
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

        switch (keuze) {
            case 1:
                uc3 = new UseCase3(this.dc);
                uc3.speelBeurt(naam);
            case 2:
                uc8 = new UseCase8(this.dc);
                uc8.spelOpslaan();
                break;
            case 3:
                System.exit(0);
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
        return dc.niemandGewonnen();
    }

    /**
     *
     * @return
     */
    private String geefNaamWinnaar() {
        return dc.geefNaamWinnaar();
    }
}
