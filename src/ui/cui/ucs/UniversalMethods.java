package ui.cui.ucs;

import language.LanguageResource;
import printer.Printer;

import java.util.Scanner;

class UniversalMethods {

    private static final Scanner SCAN = new Scanner(System.in);

    static Boolean controleJaNee(String text) {
        boolean tryAgain = true;
        String antw = "";
        while(tryAgain) {
            try {
                System.out.println(LanguageResource.getString(text));
                antw = SCAN.next().toLowerCase();
                SCAN.nextLine();
                while (!antw.equals(LanguageResource.getString("yes")) && !antw.equals(LanguageResource.getString("no"))) {
//                    System.out.printf(ColorsOutput.decoration("bold") + ColorsOutput.kleur("red") + "%s%n%n", LanguageResource.getString("start.yesno") + ColorsOutput.reset());
//                    System.out.println(LanguageResource.getString(text));
//                    antw = SCAN.next().toLowerCase();
//                    SCAN.nextLine();
                    throw new Exception("start.yesno");
                }
                tryAgain = false;
            } catch (Exception e) {
                System.out.println(Printer.exceptionCatch("InputException (UM)", e));
            }
        }
        return antw.equals(LanguageResource.getString("yes"));
    }
}