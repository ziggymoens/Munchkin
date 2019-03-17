/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ucs;
import java.sql.SQLOutput;
import java.util.*;
import domein.DomeinController;
import exceptions.SpelException;
import language.LanguageResource;
import printer.Printer;

/**
 *
 * @author ziggy
 */
public class UseCase8 {
    private final Scanner SCAN;

    private final DomeinController dc;

    public UseCase8(DomeinController dc) {
        this.dc = dc;
        SCAN = new Scanner(System.in);
    }

    public void spelOpslaan(){
        dc.spelOpslaan();
        boolean tryAgain = true;
        while(tryAgain) {
            try {
                System.out.println(LanguageResource.getString("ask.gamename"));
                String naamSpel = SCAN.nextLine();
                dc.geefSpelNaam(naamSpel);
                tryAgain = false;
            }catch (SpelException e){
                System.out.println(Printer.exceptionCatch("SpelException", e));
            }
        }

        try {
            System.out.println(Printer.printGreen("game.saved"));
            System.exit(0);
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception", e, false));
        }
    }


}
