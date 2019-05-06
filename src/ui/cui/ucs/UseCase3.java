/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cui.ucs;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import language.LanguageResource;
import printer.ColorsOutput;
import printer.Printer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author ziggy
 */
public class UseCase3 {

    private final DomeinController dc;
    private static String locatieImg;
    private static String typeImg;
    private final Scanner SCAN = new Scanner(System.in);
    private Map<String, Runnable> types;
    private int huidigeKaart;
    private String naam;
    private final int aantalSpelers;
    private List<String> huidigeSituatie;

    public UseCase3(DomeinController dc, int aantalSpelers) {
        //map die bepaalt hoe kaart afgehandeld wordt
        types = new HashMap<>();
        types.put("ConsumablesKerker", this::geenEffectKaart);
        types.put("Curse", this::curseKaart);
        types.put("Monster", this::monsterKaart);
        types.put("Race", this::geenEffectKaart);
        types.put("ConsumablesSchat", this::geenEffectKaart);
        types.put("Equipment", this::geenEffectKaart);
        this.dc = dc;
        this.aantalSpelers = aantalSpelers;

    }

    /**
     * @param naam
     */
    public void speelBeurt(String naam) {
        this.naam = naam;
        try {
            UniversalMethods.toonSituatie();
            huidigeSituatie = dc.geefSpelsituatie();
            locatieImg = dc.toonBovensteKk();
            typeImg = dc.geefTypeKaart(dc.geefIdBovensteKaart());
            th1.start();
            System.out.println(dc.toonBovensteKk() + " " + dc.geefTypeKaart(dc.geefIdBovensteKaart()));
            System.out.println(dc.bovensteKaartToString());
            huidigeKaart = dc.geefIdBovensteKaart();
            System.out.println(String.format("%s %s", Printer.printBlue(naam), LanguageResource.getString("usecase3.confirm")));
            String bev = SCAN.next();
            SCAN.nextLine();
            while (!bev.equals(LanguageResource.getString("yes"))) {
                System.out.printf(ColorsOutput.kleur("red") + ColorsOutput.decoration("bold") + "%s%n%n", LanguageResource.getString("usecase3.novalidconfirm") + ColorsOutput.reset());
                System.out.println(LanguageResource.getString("usecase3.confirm"));
                bev = SCAN.next().toLowerCase();
                SCAN.nextLine();
            }
            //System.out.println(dc.geefTypeKaart(huidigeKaart));
            speelKaart();
            beheerKaarten();
            boolean verschil = true;
            for (int i = 0; i < aantalSpelers; i++) {
                verschil = huidigeSituatie.get(i).toLowerCase().equals(dc.geefSpelsituatie().get(i).toLowerCase());
            }
            if (verschil) {
                System.out.printf("%s%n", LanguageResource.getString("usecase3.nochanges"));
            } else {
                System.out.printf("%s%n", LanguageResource.getString("usecase3.changedsituation"));
                UniversalMethods.toonSituatie();
            }
            dc.nieuweBovensteKaartK();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }
    }

    public void printImage(){
        App app = new App();
        app.begin();
    }

    private final Thread th1 = new Thread(() -> {
        try {
            printImage();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(Printer.exceptionCatch("Exception (UC1)", e, false));
        }
    });

    public static class App extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            BorderPane bp = new BorderPane();
            bp.setCenter(new ImageView(new Image(locatieImg)));
            Scene scene = new Scene(bp);
            primaryStage.setScene(scene);
            primaryStage.setTitle(String.format("Munchkin - G35 - %s", typeImg));
            primaryStage.show();
            primaryStage.setResizable(false);
        }

        public void begin(){
            launch();
        }
    }
    /**
     *
     */
    private void speelKaart() {
        try {
            String type = dc.geefTypeKaart(huidigeKaart);
            types.get(type).run();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }
    }



    /**
     * Kaarten beheren en doorverwijzen naar UC7
     */
    private void beheerKaarten() {
        try {
            UseCase7 uc7 = new UseCase7(this.dc);
            uc7.beheerKaarten();
            while (dc.getAantalKaarten(naam) > 5) {
                uc7.beheerKaarten();
            }
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }

    }

    /**
     *
     */
    private void geenEffectKaart() {
        try {
            dc.geefKerkerkaartAanSpeler(naam);
            System.out.print(Printer.printGreen(String.format("usecase3.play.%s", dc.geefTypeKaart(huidigeKaart).toLowerCase())));
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }

    }

    /**
     *
     */
    private void monsterKaart() {
        try {
            UseCase4 uc4 = new UseCase4(this.dc, aantalSpelers, naam);
            uc4.bereidSpelVoor();
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }
        System.out.print(Printer.printGreen("usecase3.play.monster"));
    }

    /**
     *
     */
    private void curseKaart() {
        try {
            dc.curseKaart(naam);
            System.out.println(Printer.printGreen("usecase3.play.curse"));
        } catch (Exception e) {
            System.out.print(Printer.exceptionCatch("Exception (UC3)", e, false));
        }
    }
}

