package ui.cui.ucs;

import language.LanguageResource;
import printer.Printer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UniversalMethods {

    private static Scanner SCAN = new Scanner(System.in);

    public static Boolean controleJaNee(String text) {
        String antw = null;
        try {
            System.out.println(LanguageResource.getString(text));
            antw = SCAN.next().toLowerCase();
            SCAN.nextLine();
            while (!antw.equals(LanguageResource.getString("yes")) && !antw.equals(LanguageResource.getString("no"))) {
                throw new InputMismatchException("start.yesno");
            }
        } catch (InputMismatchException e) {
            System.out.println(Printer.exceptionCatch("InputException (UM)", e));
            SCAN.nextLine();
        }
        return antw.equals(LanguageResource.getString("yes"));
    }
}