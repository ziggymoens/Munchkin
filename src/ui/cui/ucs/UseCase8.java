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
class UseCase8 {
    private final Scanner SCAN = new Scanner(System.in);
    private final DomeinController dc;

    /**
     * @param dc
     */
    UseCase8(DomeinController dc) {
        this.dc = dc;
    }

    /**
     *Algemene methode die andere methoden aanroept
     */
    void spelOpslaan() {
        try {
            askName();
            saveGame();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception", e, false));
        }
    }

    /**
     * methode die de naam vraagt voor het spel
     */
    private void askName() {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(LanguageResource.getString("usecase8.ask.gamename"));
                String naamSpel = SCAN.next();
                dc.geefSpelNaam(naamSpel);
                tryAgain = false;
            } catch (SpelException e) {
                System.out.print(Printer.exceptionCatch("SpelException", e));
            } catch (Exception e) {
                System.out.print(Printer.exceptionCatch("Exception", e, false));
            }
        }
    }

    /**
     * Methode die het spel opslaat en ondertussen een thread laat runnen
     */
    private void saveGame() {
        th1.start();
        th1.suspend();
        try {
            th1.resume();
            dc.spelOpslaan();
            th1.stop();
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
            th1.suspend();
            System.out.print(Printer.exceptionCatch("SpelException", e));
        } catch (SpelDatabaseException e) {
            th1.suspend();
            System.out.print(Printer.exceptionCatch("SpelDatabaseException", e));
        } catch (Exception e) {
            th1.suspend();
            System.out.print(Printer.exceptionCatch("Exception", e, false));
        }
    }

    /**
     * De loading thread
     */
    private final Thread th1 = new Thread(() -> {
        try {
            System.out.print("\nLoading ");
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    System.out.println(Printer.exceptionCatch("InterruptedException", ex, false));
                }
                System.out.print(".");
            }
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC8)", e, false));
        }
    });
}