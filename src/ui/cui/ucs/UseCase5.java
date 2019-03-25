package ui.cui.ucs;

import domein.DomeinController;

public class UseCase5 {
    private final DomeinController dc;

    public UseCase5(DomeinController dc) {
        this.dc = dc;
    }

    void speelKaart(){
        System.out.printf("Er wordt een kaart gespeeld %n%n");
    }

}
