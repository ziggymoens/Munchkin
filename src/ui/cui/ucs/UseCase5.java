package ui.cui.ucs;

import domein.DomeinController;
import domein.kaarten.Kaart;

public class UseCase5 {
    private final DomeinController dc;

    public UseCase5(DomeinController dc) {
        this.dc = dc;
    }

    void speelKaart(){
        System.out.printf("Er wordt een kaart gespeeld %n%n");
    
    
    // overzicht naam en type van kaarten in hand tonen
    System.out.println("Je speelt de kaart: "+dc.geefTypeKaart(Speler.geefAantalKaarten()));
    
    }
    
    public String[] kiesKaart(String type, String naam){
        String [] keuze = new String [2];
        
    }
}
