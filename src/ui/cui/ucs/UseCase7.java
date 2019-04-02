package ui.cui.ucs;

import domein.DomeinController;
import javafx.application.Platform;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import javax.swing.*;
import java.security.spec.ECField;
import java.util.Scanner;

/**
 * Beheer kaarten in hand
 * UC7
 */
class UseCase7 {
    private final DomeinController dc;
    private final Scanner SCAN = new Scanner(System.in);

    UseCase7(DomeinController dc) {
        this.dc = dc;
    }

    void beheerKaarten(String naam) {
        int keuze = 0;
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                System.out.println(String.format("%s %s", naam, LanguageResource.getString("usecase8.actions")));
                System.out.println(String.format("1) %s", LanguageResource.getString("usecase8.action1")));
                System.out.println(String.format("2) %s", LanguageResource.getString("usecase8.action2")));
                keuze = SCAN.nextInt();
                if (keuze < 1 || keuze > 2) {
                    throw new Exception();
                }
                tryAgain = false;
            } catch (Exception e) {
                System.out.println(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + "Foute keuze, probeer opnieuw" + ColorsOutput.reset());
                SCAN.nextLine();

            }
        }
        try {
            switch (keuze) {
                case 1:
                    try {
                        if (dc.getAantalKaarten(naam) >= 1) {
                            System.out.println(String.format("%s:%n", LanguageResource.getString("usecase7.toitems")));
                            System.out.println(String.format("%s:%n", dc.geefKaartenKunnenNaarItems(naam)));
                        }else{
                            System.out.println(String.format("%s!", LanguageResource.getString("usecase7.nietgenoeg")));
                        }
                    } catch (Exception e) {

                    }
                    break;
                case 2:
                    try {
                        System.out.println(String.format("%s: %n%s", LanguageResource.getString("usecase7.sellable"), dc.geefVerkoopbareKaarten(naam)));
                        System.out.println(String.format("%s: %n%s", LanguageResource.getString("usecase7.throwaway"), dc.geefNietVerkoopbareKaarten(naam)));
                        System.out.println(LanguageResource.getString("usecase7.asktosell"));
                        String antw = SCAN.next();

                        if(antw.equals(LanguageResource.getString("yes"))){
                            String antwoord;
                                System.out.println(LanguageResource.getString("usecase7.sellorthrow"));
                                antwoord = SCAN.next();
                                if(!antwoord.equals(LanguageResource.getString("usecase7.translationsell")) || !antwoord.equals(LanguageResource.getString("usecase7.translationthrow")))
                                    System.out.println("OPNIEUW INGEVEN");;
                            if(antwoord.equals(LanguageResource.getString("usecase7.translationsell"))){
                                System.out.println("VERKOPEN"); break;
                            }else if(antwoord.equals(LanguageResource.getString("usecase7.translationthrow"))){
                                System.out.println("WEGGOOIEN"); break;
                            }
                        }
                    } catch (Exception e) {

                    }
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("usecase2.choiceerror");
        }



    }

}

