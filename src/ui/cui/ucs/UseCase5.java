package ui.cui.ucs;

import domein.DomeinController;

import java.util.Scanner;

import domein.Speler;
import domein.kaarten.Kaart;
import domein.kaarten.kerkerkaarten.ConsumablesKerker;
import domein.kaarten.kerkerkaarten.Curse;
import domein.kaarten.kerkerkaarten.Monster;
import domein.kaarten.kerkerkaarten.Race;
import domein.kaarten.schatkaarten.ConsumablesSchat;
import domein.kaarten.schatkaarten.Equipment;
import language.LanguageResource;
import java.util.List;

public class UseCase5 {

    private final DomeinController dc;
    private int spelerAanBeurt;
    private final Scanner SCAN = new Scanner(System.in);


    public UseCase5(DomeinController dc, int i) {
        this.dc = dc;
        this.spelerAanBeurt = i;
    }

    void speelKaart() {
        System.out.println(dc.toonOverzichtKaartenInHand(spelerAanBeurt));
        System.out.println(LanguageResource.getString("usecase5.choicecard"));
        int kaart = 0;
        try{
            kaart = SCAN.nextInt();
        }catch(Exception e){
            System.err.println("Je moet de juiste input geven");
        }
        System.out.println(dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1));
        if(validatieKaart(kaart)){

        }else{
            System.out.println("Kaart mag niet gespeeld worden");
        }
    }

    private Boolean validatieKaart(int kaart){
        Kaart kr = dc.geefSpeler(spelerAanBeurt).getKaarten().get(kaart - 1);
        //Kaarten die de Tegenspelers mogen spelen
        if(dc.geefTegenspelers().contains(dc.geefNaamSpeler(dc.geefSpelerAanBeurt()))){
            if(kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Equipment || kr instanceof Race || kr instanceof Monster){
                return true;
            }return false;
            //Kaarten die de Speler mag spelen
        }else{
            if(kr instanceof ConsumablesSchat || kr instanceof ConsumablesKerker || kr instanceof Monster || kr instanceof Curse){
                //Aanpassen dat als speler geen hulp wou, alleen negatieve ConsumablesKerker gespeeld mag worden
                return true;
            }
        }
        return false;
    }
}