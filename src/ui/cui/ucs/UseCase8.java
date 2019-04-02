/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cui.ucs;

import domein.DomeinController;
import exceptions.SpelException;
import exceptions.database.SpelDatabaseException;
import language.LanguageResource;
import printer.Printer;

import java.util.Scanner;

/**
 * @author ziggy
 */
public class UseCase8 {
    private final Scanner SCAN = new Scanner(System.in);
    private final DomeinController dc;

    public UseCase8(DomeinController dc) {
        this.dc = dc;
    }

    public void spelOpslaan() {
        try {
            askName();
            saveGame();
        } catch (Exception e){
            System.out.println(Printer.exceptionCatch("Exception", e, false));
        }
    }

    private void askName() {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("usecase8.ask.gamename"));
                String naamSpel = SCAN.next();
                dc.geefSpelNaam(naamSpel);
                tryAgain = false;
            } catch (SpelException e) {
                System.out.println(Printer.exceptionCatch("SpelException", e));
            } catch (Exception e) {
                System.out.println(Printer.exceptionCatch("Exception", e, false));
            }
        }
    }

    private void saveGame() {
        try {
            dc.spelOpslaan();
            System.out.println(Printer.printGreen("usecase8.game.saved"));
            String ant;
            do {
                System.out.println(LanguageResource.getString("usecase8.exit"));
                ant = SCAN.next();
            } while (!ant.equals(LanguageResource.getString("yes")) && !ant.equals(LanguageResource.getString("no")));
            if (ant.equals(LanguageResource.getString("yes"))) {
                System.exit(0);
            }
        } catch (SpelException e) {
            System.out.println(Printer.exceptionCatch("SpelException", e));
        } catch (SpelDatabaseException e) {
            System.out.println(Printer.exceptionCatch("SpelDatabaseException", e));
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception", e, false));
        }
    }
}