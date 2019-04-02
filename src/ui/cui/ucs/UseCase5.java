package ui.cui.ucs;

import domein.DomeinController;
import java.util.List;

public class UseCase5 {

    private final DomeinController dc;
    private String naam;

    public UseCase5(DomeinController dc, String naam) {
        this.dc = dc;
        this.naam = naam;
    }

    void speelKaart() {
        System.out.printf("Er wordt een kaart gespeeld %n%n");
    }

    public String toonOverzichtKaartenInHand() {
        return dc.toonOverzichtKaartenInHand(naam);
    }

    public String[] kiesKaart(String type, String naam) {
        String[] keuze = new String[2];
        return keuze;
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

    /*
    public List<String> geefSpelsituatieGevecht(){
        
    }
     */
}
