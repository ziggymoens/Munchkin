package ui.cui.ucs;

import domein.DomeinController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import language.LanguageResource;

public class UseCase5 {

    private final DomeinController dc;
    private int speler;
    private final Scanner SCAN = new Scanner(System.in);
    private boolean runAway;

    public UseCase5(DomeinController dc, String naam, boolean runAway) {
        this.dc = dc;
        this.speler = dc.geefSpelerAanBeurt();
        this.runAway = runAway;
    }

    void speelKaart() {
        System.out.println(dc.toonOverzichtKaartenInHand(speler));
        System.out.println(LanguageResource.getString("usecase5.choicecard"));
        int kaart = SCAN.nextInt();
        System.out.println(dc.geefSpeler(speler).getKaarten().get(kaart - 1));
        //validatieKaart(kaart);
    }

    /*private Boolean validatieKaart(int kaart){
        Kaart kr = dc.geefSpeler(speler).getKaarten().get(kaart - 1);
        if(runAway){
            if(kr instanceof ConsumablesSchat){
                    ((ConsumablesSchat) kr).

            }else{
                return false;
            }
        }else{

        }
    }*/
}