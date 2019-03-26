package main;

import domein.DomeinController;
import ui.cui.ucs.UseCase1;

/**
 *
 * @author g35
 */
public class StartUp {
    public static DomeinController dc;

    /**
     * Methode die UC1 aanroep, UC1 roep de overige UC's aan
     *
     * @param args vooraf meegegeven waarden met de startup, enkel via instellingen ==> NIET GEBRUIKT
     */
    public static void main(String[] args) {
        dc = new DomeinController();
        new UseCase1(dc);
    }
}